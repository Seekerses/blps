package ru.laboratory.blps.configuration.security.providers;

import com.sun.security.auth.UserPrincipal;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.laboratory.blps.auth.User;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Map;

@Log4j2
public class BLPSLoginModule implements LoginModule {

    private Subject subject;
    private CallbackHandler callbackHandler;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private boolean loginSucceeded = false;
    private String userID;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.userDetailsService = (UserDetailsService) options.get("userDetailsService");
        this.passwordEncoder = (PasswordEncoder) options.get("passwordEncoder");
    }

    @Override
    public boolean login() throws LoginException {
        NameCallback nameCallback = new NameCallback("username");
        PasswordCallback passwordCallback = new PasswordCallback("password", false);
        try {
            callbackHandler.handle(new Callback[]{nameCallback, passwordCallback});
            String userName = nameCallback.getName();
            this.userID = userName;
            String password = String.valueOf(passwordCallback.getPassword());
            User user = (User) userDetailsService.loadUserByUsername(userName);
            loginSucceeded = passwordEncoder.matches(password, user.getCredentials().getPassword());
        } catch (UsernameNotFoundException | UnsupportedCallbackException | IOException unf) {
            loginSucceeded = false;
        }
        return loginSucceeded;
    }

    @Override
    public boolean commit() throws LoginException {
        if (!loginSucceeded) {
            return false;
        }
        UserPrincipal userPrincipal = new UserPrincipal(userID);
        subject.getPrincipals().add(userPrincipal);
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        return false;
    }
}
