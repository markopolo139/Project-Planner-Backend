package pl.ms.projectoverview.app.password.recovery;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.ms.projectoverview.app.configuration.security.TokenService;
import pl.ms.projectoverview.app.exceptions.InvalidJwtTokenException;
import pl.ms.projectoverview.app.exceptions.UserNotFoundException;
import pl.ms.projectoverview.app.persistence.entities.UserEntity;
import pl.ms.projectoverview.app.persistence.repositories.UserRepository;

import java.io.File;
import java.util.Optional;

@Service
public class PasswordRecoveryService {
    private final Logger mLogger = LogManager.getLogger();
    private final String FROM = "spring assistant";
    private final String PATH = "/change/password?token=";

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    private PasswordEncoder mPasswordEncoder;

    @Autowired
    private TokenService mTokenService;

    @Autowired
    private TemplateEngine mTemplateEngine;

    @Autowired
    private JavaMailSender mJavaMailSender;

    @Autowired
    private HttpServletRequest mRequest;

    public void sendMessage(String email) throws UserNotFoundException, MessagingException {
        Optional<UserEntity> user = mUserRepository.findByEmail(email);

        if(user.isEmpty()) {
            mLogger.error("User with given email does not exists");
            throw new UserNotFoundException();
        }

        prepareMessage(
                email, getServerAddress() + PATH + mTokenService.createPasswordRecoveryToken(user.get().getUserId())
        );
    }

    private void prepareMessage(String email, String recoveryPath) throws MessagingException {

        Context context = new Context();
        context.setVariable("sendPath", recoveryPath);

        MimeMessage mimeMessage = mJavaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(FROM);
        helper.setTo(email);
        helper.addInline("image", new File("/src/main/kotlin/resources/static/forgotPasswordImage.png"));
        helper.setSubject("Password recovery email");
        helper.setText(mTemplateEngine.process("email",context), true);

        try {
            mJavaMailSender.send(mimeMessage);
        } catch (MailException ex) {
            mLogger.warn("Email could not be sent, error (" + ex.getMessage() + ")");
            throw ex;
        }
    }

    public void changePassword(String newPassword) throws InvalidJwtTokenException, UserNotFoundException {
        if (!mTokenService.isPasswordRecoveryToken()) {
            mLogger.error("No password recovery token");
            throw new InvalidJwtTokenException("Given token is not for password recovery");
        }

        UserEntity user = mUserRepository.findById(mTokenService.extractIdFromToken()).orElseThrow(UserNotFoundException::new);
        user.setPassword(mPasswordEncoder.encode(newPassword));

        mUserRepository.save(user);
    }

    private String getServerAddress() {
        return mRequest.getRequestURL().toString().replace(mRequest.getRequestURI(),"");
    }

}
