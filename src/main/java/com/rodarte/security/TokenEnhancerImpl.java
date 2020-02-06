package com.rodarte.security;

import com.rodarte.models.entity.User;
import com.rodarte.models.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// TokenEnhancer allows to inject additional information into our generated tokens
@Component
public class TokenEnhancerImpl implements TokenEnhancer {

    @Autowired
    private UserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        User user = userService.findByUsername(oAuth2Authentication.getName());

        Map<String, Object> info = new HashMap<>();

        info.put("user", user);
        info.put("refresh_token_expires_in", 604800);

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);

        return oAuth2AccessToken;

    }

}
