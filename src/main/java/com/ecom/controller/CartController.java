package com.ecom.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecom.globle.GlobalData;
import com.ecom.model.Order;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.model.Cart;
import com.ecom.repository.UserRepository;

import com.ecom.service.CategoryService;
import com.ecom.service.OrderService;
import com.ecom.service.ProductService;

@Controller
public class CartController {

	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService  categoryService;
	
	@Autowired
	OrderService orderService;
	
	
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable long id,Model model,Principal principal,Cart c) {
		if(principal!=null) {
		String uName=principal.getName();
		Optional<User> currentUsd=this.userRepository.findUserByEmail(uName);
		model.addAttribute("user", currentUsd.get().getFirstName());
	}
		
		GlobalData.cart.add(productService.getProductById(id).get());
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProducts());
		return "index";
	}
	
	@GetMapping("/cart")
	public String cartGet(Model model,Principal principal) {
		
		model.addAttribute("cartCount",GlobalData.cart.size() );
		
	if(principal!=null) {
		String uName=principal.getName();
		Optional<User> currentUsd=this.userRepository.findUserByEmail(uName);
		model.addAttribute("user", currentUsd.get().getFirstName());
	}
		
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		//model.addAttribute("cart", GlobalData.cart);
		model.addAttribute("cart", GlobalData.cart);
		return "cart";
	}
	
	@GetMapping("/cart/removeItem/{index}")
	public String cartItemRemove(@PathVariable int index)
	{
		GlobalData.cart.remove(index);
		return "redirect:/cart";
	}
	
	
	@GetMapping("/checkout")
	public String checkout(Model model,Principal principal,Order order)
	{
		String currentUser=principal.getName();
		Optional<User> currentUsd=this.userRepository.findUserByEmail(currentUser);
		model.addAttribute("user", currentUsd.get().getFirstName());
		int id=orderService.placeOrder(order,currentUsd.get().getId());
		//model.addAttribute("total", total);

		return "checkout";
	}
}
