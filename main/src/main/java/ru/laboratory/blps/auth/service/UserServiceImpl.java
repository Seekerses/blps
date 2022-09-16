package ru.laboratory.blps.auth.service;

import ru.laboratory.blps.auth.User;
import ru.laboratory.blps.auth.dto.UpdatePasswordDTO;
import ru.laboratory.blps.auth.dto.UserRegisterDTO;
import ru.laboratory.blps.auth.factory.UserFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.laboratory.blps.auth.dto.UpdateUsernameDTO;
import ru.laboratory.blps.auth.exceptions.UserNotFound;
import ru.laboratory.blps.auth.repository.UserRepository;

import javax.annotation.Resource;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserFactory userFactory;
    private final PasswordEncoder passwordEncoder;

    @Resource
    private PlatformTransactionManager transactionManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.getUserByCredentials_Username(username).orElseThrow(()
                -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    @SneakyThrows
    @Override
    public void createUser(UserRegisterDTO registerDTO) {
        DefaultTransactionDefinition transaction = new DefaultTransactionDefinition();
        transaction.setTimeout(3);
        TransactionStatus status = transactionManager.getTransaction(transaction);
        User user = userFactory.createUser(registerDTO);
        try {
            repository.saveAndFlush(user);
            transactionManager.commit(status);
        }
        catch (Exception e){
            transactionManager.rollback(status);
        }
    }

    @Override
    public void createAdmin(UserRegisterDTO registerDTO) {
        User user = userFactory.createAdmin(registerDTO);
        repository.saveAndFlush(user);
    }

    @Override
    public void changeUsername(UpdateUsernameDTO updateUsernameDTO, Long userId) throws UserNotFound {
        User user = repository.getUserById(userId).orElseThrow(UserNotFound::new);
        user.getCredentials().setUsername(updateUsernameDTO.getUsername());
        repository.saveAndFlush(user);
    }

    @Override
    public void changePassword(UpdatePasswordDTO updatePasswordDTO, Long userId) throws UserNotFound {
        User user = repository.getUserById(userId).orElseThrow(UserNotFound::new);
        if (passwordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getCredentials().getPassword())){
            user.getCredentials().setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        }
        repository.saveAndFlush(user);
    }

    @Override
    public User getById(Long userId) throws UserNotFound {
        return repository.getUserById(userId).orElseThrow(UserNotFound::new);
    }
}
