package com.ebenz.Security.service.impl;


import com.ebenz.Security.model.SignInRequest;
import com.ebenz.Security.model.SignUpRequest;
import com.ebenz.Security.service.AuthService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AuthServiceImpl implements AuthService {

    @Value("${security.jwt.key-store}")
    private Resource keyStore;

    @Value("${security.jwt.key-store-password}")
    private String keyStorePassword;

    @Value("${security.jwt.key-pair-alias}")
    private String keyPairAlias;

    @Value("${security.jwt.key-pair-password}")
    private String keyPairPassword;

    @Value("${security.jwt.public-key}")
    private Resource publicKey;

    private int jwtExpirationInMs = 3000000;

    @Override
    public String authenticateUser(SignInRequest signInRequest) {
        return null;
    }

    @Override
    public String createUser(SignUpRequest signUpRequest) {
        return null;
    }

    //jwt token
    private String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        List<String> grantedAuthorityList = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        //code to get private key from keystore
        KeyStore keystore;
        Key key = null;
        try {
           keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(keyStore.getInputStream() ,keyStorePassword.toCharArray());

        }catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        //Generate jwt token
        return Jwts.builder()
                .claim("user_name", user.getUsername())
                .claim("authorities", grantedAuthorityList)

    }

}
