package com.scaler.BookMyShow;

import com.scaler.BookMyShow.controllers.UserAuthController;
import com.scaler.BookMyShow.dtos.SignUpRequestDto;
import com.scaler.BookMyShow.dtos.SignUpResponseDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class  BookMyShowApplication {

//	 private final UserController userController;
//
//	public BookMyShowApplication(UserController userController){
//		this.userController = userController;
//	}

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
//		signUpRequestDto.setEmail("kishore@gmail.com");
//		signUpRequestDto.setPassword("kishore@123");
//
//		SignUpResponseDto signUpResponseDto = userController.signUp(signUpRequestDto);
//		System.out.println("DEBUG");
//	}
}
