package pl.ms.projectoverview.app.configuration.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtConf {

    public static final long DEFAULT_VALIDITY = 900000L;
    public static final long DEFAULT_PASSWORD_RECOVERY_VALIDITY = 180000L;
    private final Logger mLogger = LogManager.getLogger();

    @Value("${api.auth.token.issuer}")
    private String issuer;

    @Value("${api.auth.token.audience}")
    private String audience;

    @Value("${api.auth.token.secret}")
    private String secret;

    private SecretKey key = null;

    @Value("${api.auth.token.validity}")
    private String validityValue;

    private Long validity = null;

    @Value("${api.auth.token.password.recovery.validity}")
    private String passwordRecoveryValidityValue;

    private Long passwordRecoveryValidity = null;

    public String getIssuer() {
        return issuer;
    }

    public String getAudience() {
        return audience;
    }

    private void initKey() {
        if (secret.isBlank()) {
            key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        }
        else {
            key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        }
    }

    public SecretKey getKey() {
        if (key == null) {
            initKey();
        }
        return key;
    }

    private void initValidity() {
        try {
            validity = Long.parseLong(validityValue);
            return;
        } catch (Exception e) {
            mLogger.warn("Validity value not found, using default");
        }
        validity = DEFAULT_VALIDITY;
    }


    public Long getValidity() {
        if (validity == null) {
            initValidity();
        }
        return validity;
    }

    private void initPasswordRecoveryValidity() {
        try {
            passwordRecoveryValidity = Long.parseLong(passwordRecoveryValidityValue);
            return;
        } catch (Exception e) {
            mLogger.warn("Password recovery validity value not found, using default");
        }
        passwordRecoveryValidity = DEFAULT_PASSWORD_RECOVERY_VALIDITY;
    }


    public Long getPasswordRecoveryValidity() {
        if (passwordRecoveryValidity == null) {
            initPasswordRecoveryValidity();
        }
        return passwordRecoveryValidity;
    }

}
