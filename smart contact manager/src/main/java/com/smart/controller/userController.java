package com.smart.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.repository.contactRepository;
import com.smart.repository.userRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class userController {

	@Autowired
	private userRepository userRepository;
	@Autowired
	private contactRepository contactRepository;

	// Method for adding common data to response
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);
		model.addAttribute("user", user);
	}

	// Dashboard-home
	@RequestMapping("/index")
	public String user_Dashboard(Model model) {
		return "user/user_dashboard";
	}

	// Add form handler
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "user/add_contact_form";
	}

	// Processing add contact form
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, Model model, Principal principal,
			HttpSession session) {

		try {
			String name = principal.getName();
			User user = this.userRepository.getUserByUserName(name);

			contact.setUser(user);
			user.getContact().add(contact);
			this.userRepository.save(user);

			System.out.println("Contact Data:" + contact);
			System.out.println("Added To Database");

			// sucess message
			session.setAttribute("message", new Message("Your Contact is added Sucessfully", "success"));
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			// Error Message message
			session.setAttribute("message", new Message("Something Went Wrong!! Try Again Later", "danger"));
		}

		return "user/add_contact_form";
	}

	// show contact handler
	// pagination - per page 5[n] , current page =0[page]
	@GetMapping("/show-contact/{page}")
	public String showContact(@PathVariable("page") Integer page, Model m, Principal principal) {
		m.addAttribute("title", "show contacts");
		// contact list
		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);

		PageRequest pageable = PageRequest.of(page, 5);

		Page<Contact> contacts = this.contactRepository.findContactByUser(user.getId(), pageable);

		m.addAttribute("contacts", contacts);
		m.addAttribute("currentPage", page);

		m.addAttribute("totalpages", contacts.getTotalPages());

		return "/user/show_contacts";
	}

	// delete Contact handler
	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cid , HttpSession session) {
		Optional<Contact> contactOptional = this.contactRepository.findById(cid);
		Contact contact = contactOptional.get();
		// check whether the same user can delete this id by checking wether userId == contactId;
		this.contactRepository.delete(contact);
		
		session.setAttribute("message",new Message("Contact Delete Succesfully..","success"));
		
		return "redirect:/user/show-contact/0";
	}
	
	// update form handler
	@PostMapping("/update-contact/{cid}")
	public String updateForm(@PathVariable("cid") Integer cid,Model m  ) {
		m.addAttribute("title" , "updateContact");
		Contact contact =  this.contactRepository.findById(cid).get();
		m.addAttribute("contact",contact);
		return"user/update_form";
	}
	
	// update contact handler
	@RequestMapping(value="/process-update", method = RequestMethod.POST)
	public String updateHandler(@ModelAttribute Contact contact, Model m, HttpSession session, Principal principal) {
		User user = this.userRepository.getUserByUserName(principal.getName());
        contact.setUser(user);		
		this.contactRepository.save(contact);
		return "redirect:/user/index";
	}
	

}
