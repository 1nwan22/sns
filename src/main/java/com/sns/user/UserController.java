package com.sns.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {
	
	//로그인
	//URL: http://localhost:8080/user/sign-in-view
	@GetMapping("/sign-in-view")
	public String signInView() {
		return "sns/sign-in-view";
	}
	
	//회원가입
	//URL: http://localhost:8080/user/sign-up-view
	@GetMapping("/sign-up-view")
	public String signUpView() {
		return "sns/sign-up-view";
	}
}
