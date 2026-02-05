package com.primetrade.ai.authtask.boostrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.primetrade.ai.authtask.constants.Role;
import com.primetrade.ai.authtask.entity.User;
import com.primetrade.ai.authtask.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {

		String adminEmail = "admin@primetrade.ai";

		if (!userRepository.existsByEmail(adminEmail)) {

			User admin = new User();
			admin.setName("Super Admin");
			admin.setEmail(adminEmail);
			admin.setPhoneNumber("9999999999");
			admin.setPassword(passwordEncoder.encode("Admin@123"));
			admin.setRole(Role.ADMIN);

			userRepository.save(admin);
		}
	}
}
