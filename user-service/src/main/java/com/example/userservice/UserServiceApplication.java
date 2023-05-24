package com.example.userservice;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@RequiredArgsConstructor
public class UserServiceApplication implements CommandLineRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		userRepository.save(User.builder().email("email1").password("pass1").build())
//				.subscribe(System.out::println);
		var a = List.of("das", "adswd");
		System.out.println(a);
		var b = a.stream().flatMap(f -> {
			System.out.println(Thread.currentThread().getName());
			return Stream.of("adsd");
		}).toList();
	}
}
