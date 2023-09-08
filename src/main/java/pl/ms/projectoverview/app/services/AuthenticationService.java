package pl.ms.projectoverview.app.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.ms.projectoverview.app.configuration.security.TokenService;
import pl.ms.projectoverview.app.configuration.security.UserEntity;

@Service
public class AuthenticationService {
    private static final Logger mLogger = LogManager.getLogger();

    private final TokenService mTokenService;
    private final AuthenticationManager mAuthenticationManager;

    public AuthenticationService(TokenService mTokenService, AuthenticationManager mAuthenticationManager) {
        this.mTokenService = mTokenService;
        this.mAuthenticationManager = mAuthenticationManager;
    }

    public String authenticate(String username, String password) {
        try {
            Authentication auth = mAuthenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            return mTokenService.createAuthenticationToken(((UserEntity) auth.getPrincipal()).getId());
        } catch (BadCredentialsException e) {
            mLogger.error("Attempt of obtaining token with invalid credentials has been made for username " + username);
            throw e;
        }
    }
}
