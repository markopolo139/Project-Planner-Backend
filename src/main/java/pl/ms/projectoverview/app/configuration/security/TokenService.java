package pl.ms.projectoverview.app.configuration.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.Objects;

@Service
public class TokenService {

    private final Logger mLogger = LogManager.getLogger();
    private final JwtConf mJwtConf;

    public TokenService(JwtConf mJwtConf) {
        this.mJwtConf = mJwtConf;
    }

    enum Claims {
        PASSWORD_RECOVERY("pwr");
        private final String value;

        Claims(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public String createAuthenticationToken(int userId) {
        return configure(Jwts.builder(), mJwtConf.getValidity()).setSubject(String.valueOf(userId)).compact();
    }

    public String createPasswordRecoveryToken(int userId) {
        return configure(Jwts.builder(), mJwtConf.getPasswordRecoveryValidity())
                .setSubject(String.valueOf(userId))
                .claim(Claims.PASSWORD_RECOVERY.value, true)
                .compact();
    }

    public Integer extractIdFromToken() {
        try {
            String token = extractTokenFromRequest(getCurrentRequest());

            return Integer.valueOf(
                    configure(Jwts.parserBuilder()).build().parseClaimsJws(token).getBody().getSubject()
            );

        } catch (NullPointerException e) {
            mLogger.warn("Not current request found");
        } catch (ExpiredJwtException e) {
            mLogger.debug("Token authentication failed due to expired token");
        } catch (SecurityException e) {
            mLogger.warn("Token authentication failed due invalid signature");
        } catch (JwtException e) {
            mLogger.debug("Token authentication failed due to exception: $e");
        }

        return null;
    }

    public Integer extractIdFromToken(String token) {
        if (token == null) return null;

        try {
            return Integer.valueOf(
                    configure(Jwts.parserBuilder()).build().parseClaimsJws(token).getBody().getSubject()
            );

        } catch (ExpiredJwtException e) {
            mLogger.debug("Token authentication failed due to expired token");
        } catch (SecurityException e) {
            mLogger.warn("Token authentication failed due invalid signature");
        } catch (JwtException e) {
            mLogger.debug("Token authentication failed due to exception: $e");
        }

        return null;
    }

    public boolean isPasswordRecoveryToken() {
        try {
            String token = extractTokenFromRequest(getCurrentRequest());

            return Objects.requireNonNull(
                    configure(Jwts.parserBuilder())
                            .build()
                            .parseClaimsJws(token)
                            .getBody()
                            .get(Claims.PASSWORD_RECOVERY.value, Boolean.class)
            );

        } catch (NullPointerException e) {
            mLogger.warn("Not current request found or invalid claim");
        } catch (ExpiredJwtException e) {
            mLogger.debug("Token authentication failed due to expired token");
        } catch (SecurityException e) {
            mLogger.warn("Token authentication failed due invalid signature");
        } catch (JwtException e) {
            mLogger.debug("Token authentication failed due to exception: $e");
        }

        return false;
    }

    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null) return null;

        if (authHeader.startsWith("Bearer "))
            return authHeader.substring(7);
        else
            return null;
    }

    private JwtBuilder configure(JwtBuilder builder, long validity) {
        builder
                .setIssuer(mJwtConf.getIssuer())
                .setAudience(mJwtConf.getAudience())
                .setExpiration(new Date(new Date().getTime() + validity))
                .signWith(mJwtConf.getKey());
        return builder;
    }

    private JwtParserBuilder configure(JwtParserBuilder parser) {
        parser
                .requireIssuer(mJwtConf.getIssuer())
                .requireAudience(mJwtConf.getAudience())
                .setSigningKey(mJwtConf.getKey());
        return parser;
    }
}
