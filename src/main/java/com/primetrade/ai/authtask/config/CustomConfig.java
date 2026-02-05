package com.primetrade.ai.authtask.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class CustomConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {

		log.info("Initializing BCryptPasswordEncoder bean");

		return new BCryptPasswordEncoder();
	}

	// define ModelMapper bean
	@Bean
	public ModelMapper modelMapper() {

		log.info("Initializing ModelMapper bean");

		ModelMapper mapper = new ModelMapper();

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);

		log.info("ModelMapper bean initialized successfully with STRICT matching strategy and skip null enabled");

		return mapper;
	}
}
