package com.park.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	
	
	@GetMapping("/user")
	public @ResponseBody String hello() { //@Controller + @ResponseBody = @RestController
		return "user"; // 데이터를 리턴 (MessageConverter 발동)
	}
	
	@GetMapping({"","/"})
	public String home() {
		return "index"; // ViewResolver 발동
	}
}
