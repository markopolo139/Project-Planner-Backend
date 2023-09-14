package pl.ms.projectoverview.app.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.ms.projectoverview.app.exceptions.UserAlreadyExistsException;
import pl.ms.projectoverview.app.exceptions.UserNotFound;
import pl.ms.projectoverview.app.persistence.converters.UserConverter;
import pl.ms.projectoverview.app.persistence.entities.UserEntity;
import pl.ms.projectoverview.app.persistence.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final Logger mLogger = LogManager.getLogger();
    private final UserRepository mUserRepository;
    private final UserConverter mUserConverter;
    private final PasswordEncoder mPasswordEncoder;
    private final Integer userId = AppUtils.getUserId();

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserConverter userConverter) {
        mUserRepository = userRepository;
        mPasswordEncoder = passwordEncoder;
        mUserConverter = userConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            mLogger.error("Username is null");
            throw new UsernameNotFoundException("Username not found");
        }

        try {
            return mUserConverter.convertToApp(mUserRepository.findByUsername(username).orElseThrow());
        } catch (NoSuchElementException ex) {
            mLogger.error("Given username is not present");
            throw new UsernameNotFoundException("Given username is not present");
        }
    }

    public UserDetails loadUserById(Integer id) throws UserNotFound {
        if (id == null) {
            mLogger.error("Id is null");
            throw new UserNotFound();
        }

        try {
            return mUserConverter.convertToApp(mUserRepository.findById(id).orElseThrow());
        } catch (NoSuchElementException ex) {
            mLogger.error("Given username is not present");
            throw new UserNotFound();
        }
    }

    public void createUser(String username, String password, String email) throws UserAlreadyExistsException {
        if (mUserRepository.findByUsername(username).isPresent() || mUserRepository.findByEmail(email).isPresent()) {
            mLogger.error("User with given username or email already exists");
            throw new UserAlreadyExistsException();
        }

        UserEntity newUser = new UserEntity(
                0, username, mPasswordEncoder.encode(password), email, false,
                Set.of("USER"), Collections.emptySet()
        );

        mUserRepository.save(newUser);
    }

    public void deleteUser() {
        mUserRepository.deleteById(userId);
    }
}
