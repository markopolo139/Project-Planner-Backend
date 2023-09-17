package pl.ms.projectoverview.app.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.ms.projectoverview.app.exceptions.UserAlreadyExistsException;
import pl.ms.projectoverview.app.exceptions.UserNotFoundException;
import pl.ms.projectoverview.app.converters.UserConverter;
import pl.ms.projectoverview.app.persistence.entities.UserEntity;
import pl.ms.projectoverview.app.persistence.repositories.UserRepository;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;

import static pl.ms.projectoverview.app.converters.UserConverter.convertEntityToApp;

@Service
public class UserService implements UserDetailsService {

    private final Logger mLogger = LogManager.getLogger();

    private final UserRepository mUserRepository;

    private final PasswordEncoder mPasswordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        mUserRepository = userRepository;
        mPasswordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            mLogger.error("Username is null");
            throw new UsernameNotFoundException("Username not found");
        }

        try {
            return convertEntityToApp(mUserRepository.findByUsername(username).orElseThrow());
        } catch (NoSuchElementException ex) {
            mLogger.error("Given username is not present");
            throw new UsernameNotFoundException("Given username is not present");
        }
    }

    public UserDetails loadUserById(Integer id) throws UserNotFoundException {
        if (id == null) {
            mLogger.error("Id is null");
            throw new UserNotFoundException();
        }

        try {
            return convertEntityToApp(mUserRepository.findById(id).orElseThrow());
        } catch (NoSuchElementException ex) {
            mLogger.error("Given username is not present");
            throw new UserNotFoundException();
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
        mUserRepository.deleteById(AppUtils.getUserId());
    }
}
