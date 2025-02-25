package com.base.admin.service.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.base.admin.dto.exception.user.UserNotFoundException;
import com.base.admin.dto.exception.user.WrongPasswordException;
import com.base.admin.entity.personal.Personal;
import com.base.admin.entity.user.User;
import com.base.admin.repository.personal.PersonalRepository;
import com.base.admin.repository.user.UserRepository;
import com.base.admin.service.user.IAuthService;
import com.base.admin.service.user.IUserDirectory;
import com.base.admin.service.user.IUserService;
import com.base.admin.utils.SpringSecurityService;
import com.base.admin.utils.authprovider.JwtProvider;
import com.base.common.dto.user.UserDetailsImpl;
import com.base.common.service.impl.BaseEntityService;
import com.base.common.utils.authprovider.IAuthProvider;
import com.base.common.utils.convert.object.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService extends BaseEntityService<User> implements IUserService, IAuthService {

    static List<IUserDirectory> otherUserDirectories = new ArrayList<>();
    UserRepository userRepository;
    IAuthProvider authProvider;

    @Autowired
    PersonalRepository personalRepository;

    public static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    UserService(@Autowired UserRepository userRepository, @Autowired JwtProvider authProvider) {
        this.entityRepository = userRepository;
        this.userRepository = userRepository;
        this.authProvider = authProvider;
    }

    @Override
    public User get(Long id, Map<String, Object> options) {
        User user = super.get(id, options);
        return user;
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        List<User> users = userRepository.findByUsername(username);
        if (ObjectUtils.isEmpty(users)) {
            return null;
        }

        User user = users.get(0);
        if (!user.getEnabled() || user.getAccountExpired() || user.getAccountLocked() || user.getPasswordExpired()) {
            return null;
        }

        String encyptedPassword = user.getPassword().substring("{bcrypt}".length(), user.getPassword().length());

        Boolean match = compareSync(password, encyptedPassword);

        if (!match) {
            for (IUserDirectory userDirectory : UserService.otherUserDirectories) {
                match |= userDirectory.check(username, password);
            }
        }

        if (!match) return null;

        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.username = user.getUsername();
        userDetails.authorities = user.getRoles().stream().map(it -> new SimpleGrantedAuthority(it.getAuthority()))
                .collect(Collectors.toList());

        return authProvider.createResponsePayload(userDetails);
    }

    @Override
    public Map<String, Object> refreshToken(String refreshToken, UserDetails userDetails) {
        return authProvider.refreshToken(refreshToken, (UserDetailsImpl) userDetails);
    }

    @Override
    public void logout(String accessToken, UserDetails userDetail) {
        authProvider.invokeAccessToken(accessToken, userDetail);
    }

    @Override
    public User findByUsername(String username) {
        List<User> users = userRepository.findByUsername(username);
        
        if (users.isEmpty())
            return null;

        return users.get(0);
    }

    @Override
    public User updateProfile(String username, Map<String, Object> item) throws UserNotFoundException, WrongPasswordException {
        UserDetails userDetail = SpringSecurityService.getPrincipal();

        List<User> users = userRepository.findByUsername(username);
        if (ObjectUtils.isEmpty(users)) {
            throw new UserNotFoundException(username);
        }

        User user = users.get(0);

        Boolean match = compareSync(item.get("oldPassword").toString(),
                user.getPassword().substring("{bcrypt}".length(), user.getPassword().length()));
        if (!match) {
            throw new WrongPasswordException(username);
        }

        if (item.containsKey("personal")) {
            boolean __newPersonal = false;
            Personal personal = user.getPersonal();
            if (personal == null) {
                personal = new Personal();
                __newPersonal = true;
            }
            ObjectUtils.mergeObject(personal, item.get("personal"));
            if (__newPersonal) {
                personal.setUser(user);
                personal.setCreatedBy(userDetail.getUsername());
                personal.setCreatedAt(new Date());
            } else {
                personal.setUpdatedBy(userDetail.getUsername());
                personal.setUpdatedAt(new Date());
            }
            personal = personalRepository.save(personal);
            user.setPersonal(personal);
            item.remove("personal");
        }

        ObjectUtils.mergeObject(user, item);

        if (!ObjectUtils.isEmpty(item.get("newPassword"))) {
            user.setPassword(item.get("newPassword").toString());
        }

        user.setUpdatedBy(userDetail.getUsername());
        user.setUpdatedAt(new Date());
        user = this.userRepository.save(user);

        return user;
    }

    public static void regUserDirectory(IUserDirectory userDirectory) {
        log.info("register new user directory...");
        UserService.otherUserDirectories.add(userDirectory);
    }

    private Boolean compareSync(String data, String encypted) {
        return bCryptPasswordEncoder.matches(data, encypted);
    }
}
