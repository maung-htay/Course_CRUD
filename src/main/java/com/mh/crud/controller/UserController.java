package com.mh.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mh.crud.model.User;
import com.mh.crud.repository.UserRepo;

@Controller
public class UserController {

	@Autowired
	private UserRepo myUserRepo;

	@RequestMapping("/hello")
	public String helloPage() {

		return "hello";

	}

//	Register Page , Get
	@RequestMapping("/register")
	public String registerPage(Model model) {

		User user = new User();

		model.addAttribute("user", user);
		model.addAttribute("error", true);
		

		return "register";

	}

//	Post Method
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPagePost(Model model, @ModelAttribute User user) {

		User db_user = myUserRepo.checkEmail(user.getEmail());
		
		
		if (db_user == null) {
			myUserRepo.save(user);
			//model.addAttribute("error", true);
			
			model.addAttribute("user", new User());
			model.addAttribute("loginError", true);
			
            return "login";
			// go to login page
		} else {
			model.addAttribute("error", false);
			

		}
		model.addAttribute("user", user);

		return "register";

	}
	
	@RequestMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("loginError", true);
		return "login";
	}
	
	// login Submit Button
	@RequestMapping(value="/login" , method = RequestMethod.POST)
	public String submitLoginPage(Model model, @ModelAttribute User user) {
		
		User db_user = myUserRepo.checkLogin(user.getEmail(), user.getPassword());
		
		if(db_user == null) { // no register
			//show error

			model.addAttribute("loginError", false);
			
		}else {// register 
			// go to main Page
			
			List<User> users = myUserRepo.findAll();
			model.addAttribute("users", users);
			return "main";
		}
		
		model.addAttribute("user", user);
		
		return "login";
		
	}
	
//	Edit Page 
	@RequestMapping("/edit/{id}")
	public String editPage(Model model, @PathVariable("id") long id) {
		User user = myUserRepo.findById(id).orElseThrow();
		model.addAttribute("user", user);
		
		return "edit";
		
	}
	// Edit Submit
	@RequestMapping("/edit")
	public String editSubmit(Model model, @ModelAttribute User user) {
		myUserRepo.save(user);
		
		List<User> users = myUserRepo.findAll();
		model.addAttribute("users", users);
		return "main";
	}
	
	//DeleteEdit Page 
	@RequestMapping("/delete/{id}")
	public String DeletePage(Model model, @PathVariable("id") long id) {
		
		myUserRepo.deleteById(id);
		List<User> users = myUserRepo.findAll();
		model.addAttribute("users", users);
		
		return "main";
		
	}
  
	
}
