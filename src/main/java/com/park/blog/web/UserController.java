package com.park.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping({"","/"})
	public String home() {
		return "index"; // ViewResolver 발동
	}
}
