package com.hospital.config;

import com.hospital.entity.User;
import com.hospital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@hospital.com")
                    .fullName("System Administrator")
                    .role(User.Role.ADMIN)
                    .active(true)
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Admin user created: username=admin, password=admin123");
        }

        if (!userRepository.existsByUsername("doctor")) {
            User doctor = User.builder()
                    .username("doctor")
                    .password(passwordEncoder.encode("doctor123"))
                    .email("doctor@hospital.com")
                    .fullName("Dr. John Smith")
                    .role(User.Role.DOCTOR)
                    .active(true)
                    .build();
            userRepository.save(doctor);
        }

        if (!userRepository.existsByUsername("receptionist")) {
            User rec = User.builder()
                    .username("receptionist")
                    .password(passwordEncoder.encode("rec123"))
                    .email("rec@hospital.com")
                    .fullName("Jane Receptionist")
                    .role(User.Role.RECEPTIONIST)
                    .active(true)
                    .build();
            userRepository.save(rec);
        }
    }
}
