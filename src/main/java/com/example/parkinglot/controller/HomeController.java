package com.example.parkinglot.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "✅ Google login successful. Now call /api/... endpoints.";
    }

    @GetMapping("/me")
    public Map<String, Object> user(@AuthenticationPrincipal OidcUser principal,
                                    @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient client) {
        return Map.of(
                "user", principal.getAttributes(),                 // profile info
                "access_token", client.getAccessToken().getTokenValue(),
                "id_token", principal.getIdToken().getTokenValue()); // 👈 raw);
    }
}
