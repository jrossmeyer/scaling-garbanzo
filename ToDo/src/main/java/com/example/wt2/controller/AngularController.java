package com.example.wt2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AngularController {
	@RequestMapping({
		"/login",
		"/api",
		"/todos",
		"/todos/{id}"
	}) public String fwdAngular() {
		return "forward:/index.html";
	}
}
