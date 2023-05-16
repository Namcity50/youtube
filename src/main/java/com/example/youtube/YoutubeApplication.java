package com.example.youtube;

import com.example.youtube.enums.ProfileRole;
import com.example.youtube.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YoutubeApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoutubeApplication.class, args);
		System.out.println(JwtUtil.encode(1, ProfileRole.ROLE_ADMIN));
	}

}
