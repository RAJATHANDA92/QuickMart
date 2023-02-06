package com.ecom.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ecom.globle.GlobalData;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.model.Order;
import com.ecom.model.OrderDetails;
import com.ecom.repository.UserRepository;
import com.ecom.service.CategoryService;
import com.ecom.service.OrderDetailsService;
import com.ecom.service.OrderService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;

@Controller
public class HomeController {

	@Autowired
	CategoryService  categoryService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailsService orderDetailsService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;
	

	
	@GetMapping({"/","/home"})
	public String home(Model model,Principal principal) {
		if(principal!=null) {
			String uName=principal.getName();
			Optional<User> currentUsd=this.userRepository.findUserByEmail(uName);
			model.addAttribute("user", currentUsd.get().getFirstName());
		}
		
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProducts());
		return "index";
	}
	
	@GetMapping("/orders")
	public String orderslist(Model model,Principal principal){
		if(principal!=null) {
			String uName=principal.getName();
			Optional<User> currentUsd=this.userRepository.findUserByEmail(uName);
			model.addAttribute("user", currentUsd.get().getFirstName());
		}
		model.addAttribute("orders", orderService.allOrders());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		return "orders";
	}
	
	
	@GetMapping("/details/{id}")
	public String orderDetails(Model model,@PathVariable("id") int orderid,Principal principal) {
		if(principal!=null) {
			String uName=principal.getName();
			Optional<User> currentUsd=this.userRepository.findUserByEmail(uName);
			model.addAttribute("user", currentUsd.get().getFirstName());
		}
		Order order=orderService.getOrderDetails(orderid);
		List<OrderDetails> odlist=orderDetailsService.allItemsinOrder(orderid);
		System.out.println("Total items : "+odlist.size());
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("o", order);
		model.addAttribute("items",odlist);		
		model.addAttribute("cqty", odlist.size());		
		return "orderDetails";
	}
	
	@GetMapping("/payNow")
	public String payNow(Principal principal,Model model){
		if(principal!=null) {
			String uName=principal.getName();
			Optional<User> currentUsd=this.userRepository.findUserByEmail(uName);
			model.addAttribute("user", currentUsd.get().getFirstName());
		}
		return "final";
	}
	
	@GetMapping("/order/delete/{id}")
	public String deleteCat(@PathVariable int id ) {
		orderService.cancelOrder(id);
		return "redirect:/orders";
	}
	
	@GetMapping("/changepwd")
	public String changepasswordpage(Principal principal,Model model) {
		if(principal!=null) {
			String uName=principal.getName();
			Optional<User> currentUsd=this.userRepository.findUserByEmail(uName);
			model.addAttribute("user", currentUsd.get().getFirstName());
		}
		return "changepwd";
	}
	
	@PostMapping("/changepwd")
	public String changepassword(@RequestParam("opwd") String oldPassword,@RequestParam("pwd") String newPassword,Principal principal,HttpSession session,Model model) {
		
		String uName=principal.getName();
		System.out.println(uName);
		Optional<User> currentUsd=this.userRepository.findUserByEmail(uName);
		model.addAttribute("user", currentUsd.get().getFirstName());
		if(this.bCryptPasswordEncoder.matches(oldPassword, currentUsd.get().getPassword()))
		{
			currentUsd.get().setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			userRepository.save(currentUsd.get());
			//session.setAttribute("message", new Message("Your password is successfully changed..","success"));
			model.addAttribute("cartCount", GlobalData.cart.size());
			model.addAttribute("categories", categoryService.getAllCategory());
			model.addAttribute("products", productService.getAllProducts());
			return "index";
		}else {
			
			return "redirect:/changepwd";
		}
		
		
	}
	
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model,@PathVariable int id,Principal principal) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProductsByCategoryId(id));
		model.addAttribute("cartCount", GlobalData.cart.size());
		if(principal!=null) {
			String uName=principal.getName();
			Optional<User> currentUsd=this.userRepository.findUserByEmail(uName);
			model.addAttribute("user", currentUsd.get().getFirstName());
		}
		return "index";
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model,@PathVariable int id,Principal principal) {
		if(principal!=null) {
			String uName=principal.getName();
			Optional<User> currentUsd=this.userRepository.findUserByEmail(uName);
			model.addAttribute("user", currentUsd.get().getFirstName());
		}
		model.addAttribute("product", productService.getProductById(id).get());
		model.addAttribute("cartCount", GlobalData.cart.size());

		return "viewProduct";
	}
	

}
