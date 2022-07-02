package com.example.wt2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wt2.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private static final transient Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired //injectet das User Repository
	UserRepository userRepo;
}
