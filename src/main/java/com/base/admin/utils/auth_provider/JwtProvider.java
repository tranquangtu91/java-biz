package com.base.admin.utils.auth_provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.base.common.dto.user.UserDetailsImpl;
import com.base.common.utils.auth_provider.IAuthProvider;

@SuppressWarnings("unchecked")
@Slf4j
@Component
public class JwtProvider implements IAuthProvider {

    @Value("${jwt.algorithm:HS256}")
    String algorithm;

    @Value("${jwt.expire-time:3600000}")
    Long expireTime;

    @Value("${jwt.issuer:xxx}")
    String issuer;

    @Value("${jwt.secret:bqhojfXbjJunxDL3*WBF!.DvcPmwyEkq}")
    String secret;

    @Value("${jwt.token-type:Bearer}")
    String tokenType;

    @Override
    public String createAccessToken(UserDetailsImpl userDetail) {
        Long currentTime = System.currentTimeMillis();
        JwtBuilder jwtBuilder = Jwts.builder();
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        jwtBuilder.setSubject(userDetail.username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTime + expireTime))
                .setIssuer(issuer)
                .claim("roles", userDetail.authorities.stream().map(GrantedAuthority::getAuthority).toArray());

        return jwtBuilder.signWith(key).compact();
    }

    @Override
    public Map<String, Object> verifyAccessToken(String accessToken)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException,
            IllegalArgumentException {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        Jws<Claims> jws = jwtParser.parseClaimsJws(accessToken);

        return jws.getBody();
    }

    @Override
    public Map<String, Object> createResponsePayload(UserDetailsImpl userDetails) {
        String accessToken = createAccessToken(userDetails);

        Map<String, Object> payload = new HashMap<>();
        payload.put("access_token", accessToken);
        payload.put("roles", userDetails.authorities.stream().map(GrantedAuthority::getAuthority));
        payload.put("username", userDetails.username);
        payload.put("token_type", tokenType);
        payload.put("expires_in", expireTime);

        return payload;
    }

    @Override
    public UserDetailsImpl getUserDetail(String accessToken) {
        Map<String, Object> claims = verifyAccessToken(accessToken);
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.username = (String) claims.get("sub");
        userDetails.authorities = ((ArrayList<String>) claims.get("roles")).stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        userDetails.exp = (Integer) claims.get("exp");
        userDetails.iat = (Integer) claims.get("iat");
        userDetails.payload = claims;
        userDetails.src = this.getClass().getName();

        return userDetails;
    }

}
