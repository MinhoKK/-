package com.ssafy.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.user.domain.User;
import com.ssafy.user.service.UserService;
import com.ssafy.user.service.dto.UserDto;

@RequestMapping("/admin")
@Controller
public class AdminController {

private final UserService userService;
	
	public AdminController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/userList")
	public ModelAndView userList(@RequestParam Map<String, String> map) throws Exception {
		System.out.println("key, word = " + map.get("key") + "," + map.get("word"));
		List<User> userList = userService.findAll(map);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("userList", userList);
		mav.addObject("key", map.get("key"));
		mav.addObject("word", map.get("word"));
		mav.setViewName("/admin/list");
		
		return mav;
	}
	
	@GetMapping("/regist/user")
	public String registUser() {
		return "/admin/join";
	}
	
	@PostMapping("/regist/user")
	public String registUser(User user, Model model) {
		try {
			userService.join(user);
			return "redirect:/admin/userList";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "회원등록 에러 발생!");
			return "error/error";
		}
	}
	
	@GetMapping("/update/user/{userId}")
	public ModelAndView updateUser(@PathVariable String userId) throws Exception {
		User user = userService.findByUserId(userId);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", user);
		mav.setViewName("/admin/modifyInfo");
		
		return mav;
	}
	
	@PostMapping("/update/user")
	public String updateUser(User user) throws Exception {
		userService.modifyByAdmin(user);
		return "redirect:/admin/userList";
	}
	
	
	@GetMapping("/delete/user/{userId}")
	public String deleteUser(@PathVariable String userId) throws Exception {
		userService.delete(userId);
		return "redirect:/admin/userList";
	}
	
}
