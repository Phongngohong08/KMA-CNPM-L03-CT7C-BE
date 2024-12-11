package com.project.shopapp.configurations;

import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class RoleConfig {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public void initRoleUserAndAdminIfNotExists() {
        if(roleRepository.findById(1L).isEmpty()) {
            roleRepository.save(new Role(1L, Role.USER));
        }
        if(roleRepository.findById(2L).isEmpty()) {
            roleRepository.save(new Role(2L, Role.ADMIN));
        }

        if(userRepository.existsByPhoneNumber("admin")) {
            return;
        }

        String password = "admin";
        String encodedPassword = passwordEncoder.encode(password);
        Role role = roleRepository.findById(2L).get();
        userRepository.save(User.builder()
                .fullName("admin")
                .phoneNumber("admin")
                .password(encodedPassword)
                .address("admin")
                .active(true)
                .role(role)
                .build());
    }
}
