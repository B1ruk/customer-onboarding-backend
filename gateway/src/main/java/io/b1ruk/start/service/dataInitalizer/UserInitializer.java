package io.b1ruk.start.service.dataInitalizer;

import com.google.common.collect.Lists;
import io.b1ruk.start.model.entity.UserEntity;
import io.b1ruk.start.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class UserInitializer implements CommandLineRunner {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        var defaultHashedPwd=passwordEncoder.encode("password");
        var users=Lists.newArrayList(
                new UserEntity("approver",defaultHashedPwd,"APPROVER"),
                new UserEntity("processor",defaultHashedPwd,"PROCESSOR"),
                new UserEntity("customer",defaultHashedPwd,"CUSTOMER")
        );

        userRepository.saveAll(users);
        log.info("saving users");
    }
}
