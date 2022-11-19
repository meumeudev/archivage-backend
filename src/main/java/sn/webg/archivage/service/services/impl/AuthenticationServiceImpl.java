package sn.webg.archivage.service.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sn.webg.archivage.service.models.request.AuthUsername;
import sn.webg.archivage.service.models.response.SignInAuthentication;
import sn.webg.archivage.service.security.jwt.TokenProvider;
import sn.webg.archivage.service.services.AuthenticationService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    final TokenProvider tokenProvider;

    static final String TOKEN_TYPE ="Bearer";

    @Override
    public SignInAuthentication authenticate(AuthUsername authUsername) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authUsername.getUsername(),
                authUsername.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProvider.createToken(authentication, authUsername.isRememberMe());
        return SignInAuthentication
                .builder()
                .tokenType(TOKEN_TYPE)
                .accessToken(accessToken)
                .build();
    }
}
