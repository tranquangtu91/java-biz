package com.base.admin.controller.user.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.admin.controller.user.IAuthControler;
import com.base.admin.controller.user.IUserController;
import com.base.admin.dto.exception.user.UserNotFoundException;
import com.base.admin.dto.exception.user.WrongPasswordException;
import com.base.admin.dto.user.LoginRequestDto;
import com.base.admin.dto.user.RefreshTokenRequestDto;
import com.base.admin.entity.user.User;
import com.base.admin.service.user.impl.UserService;
import com.base.admin.utils.SpringSecurityService;
import com.base.common.controller.entity.impl.BaseEntityController;
import com.base.common.dto.formfield.FormField;
import com.base.common.dto.generalresponse.GeneralResponse;
import com.base.common.dto.generalresponse.ResponseCode;
import com.base.common.utils.convert.object.ObjectUtils;
import com.base.common.utils.validator.ValidatorUtils;

@RestController()
@RequestMapping(path = "/api/v1/user")
public class UserController extends BaseEntityController<User> implements IUserController, IAuthControler {
    UserService userService;

    @Autowired
    SpringSecurityService springSecurityService;

    @Autowired
    ValidatorUtils validatorUtils;

    public static final String UPDATE_PROFILE_CONSTRAINT_CODE = "/api/v1/user/update-profile";

    UserController(@Autowired UserService entityService) {
        this.entityService = entityService;

        this.userService = entityService;
        getExcludeFields.addAll(Arrays.asList("updatedBy", "updatedAt", "deleted", "password"));
        updateExcludeFields.addAll(Arrays.asList("username", "accountExpired", "accountLocked", "deleted", "passwordExpired"));

        List<FormField> updateProfileConstraint = new ArrayList<FormField>() {
            {
                add(new FormField() {
                    {
                        code = "oldPassword";
                        required = true;
                    }
                });
                add(new FormField() {
                    {
                        code = "personal.fullName";
                        required = true;
                    }
                });
            }
        };
        ValidatorUtils.registryConstraint(UPDATE_PROFILE_CONSTRAINT_CODE, updateProfileConstraint);
    }

    @Override
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, Object> body) throws UserNotFoundException, WrongPasswordException {
        GeneralResponse gr = validatorUtils.validate(UPDATE_PROFILE_CONSTRAINT_CODE, body);
        if (gr.code != ResponseCode.SUCCESS) {
            return new ResponseEntity<GeneralResponse>(gr, HttpStatus.OK);
        }

        String[] __updateExcludeFields = updateExcludeFields.toArray(new String[0]);
        body = ObjectUtils.modifyData(body, __updateExcludeFields);

        UserDetails userDetail = SpringSecurityService.getPrincipal();

        if (userDetail == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userService.updateProfile(userDetail.getUsername(), body);

        gr = new GeneralResponse();
        gr.value = user;

        String[] __getExcludeFields = getExcludeFields.toArray(new String[0]);
        return new ResponseEntity<>(ObjectUtils.modifyData(gr, __getExcludeFields), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GeneralResponse> currentUser() {
        UserDetails userDetail = SpringSecurityService.getPrincipal();

        if (userDetail == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userService.findByUsername(userDetail.getUsername());

        GeneralResponse gr = new GeneralResponse();
        String[] __getExcludeFields = Arrays.copyOf(getExcludeFields.toArray(), getExcludeFields.size(), String[].class);
        gr.value = ObjectUtils.modifyData(user, __getExcludeFields);

        return new ResponseEntity<>(gr, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> login(LoginRequestDto body) {
        Object rtn = userService.login(body.getUsername(), body.getPassword());
        if (rtn == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(rtn, HttpStatus.OK);
    }    

    @Override
    public ResponseEntity<GeneralResponse> logout(HttpServletRequest request) {
        UserDetails userDetail = SpringSecurityService.getPrincipal();
        if (userDetail == null || userDetail.getUsername().equals("anonymousUser")) {
            return new ResponseEntity<>(GeneralResponse.error(null, "Unauthorized"), HttpStatus.UNAUTHORIZED);
        }

        //Get request header
        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.substring(7);
        userService.logout(accessToken, userDetail);
        return new ResponseEntity<>(GeneralResponse.success(null, "Logout successfully"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> refreshToken(RefreshTokenRequestDto body) {
        UserDetails userDetail = SpringSecurityService.getPrincipal();
        if (userDetail == null || userDetail.getUsername().equals("anonymousUser")) {
            return new ResponseEntity<>(GeneralResponse.error(null, "Unauthorized"), HttpStatus.UNAUTHORIZED);
        }

        Object rtn = userService.refreshToken(body.getRefreshToken(), userDetail);
        return new ResponseEntity<>(rtn, HttpStatus.OK);
    }
}
