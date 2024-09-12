package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.repository.userRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class Mycontroller {
	@Autowired
	private userRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home Smart Contact Manager");
		return "home";
	}
	
	@GetMapping("/login")
	public String customlogin(Model model) {
		model.addAttribute("title:","Login Page");
		return "login";
	}

	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About Smart Contact Manager");
		return "about";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register Smart Contact Manager");
		model.addAttribute("user", new User());
		return "signup";
	}

	// handler of registrating user
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session ) {
		
		try {

			user.setRole("USER_ROLE");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("Agreement: " + agreement);
			System.out.println("User: " + user);

			User result = this.userRepository.save(user);
			model.addAttribute("user", new User());
			session.setAttribute("message",new Message("Sucessfully Registered","alert-success"));
			
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message", new Message("Something Went Wrong"+e.getMessage(), "alert-danger"));
			return "signup";	
		}

		
	}
	
	

}
