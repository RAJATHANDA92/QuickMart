package com.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;


import com.ecom.model.User;
import com.ecom.globle.GlobalData;
import com.ecom.model.Order;
import com.ecom.model.OrderDetails;
import com.ecom.model.Product;
import com.ecom.repository.OrderRepository;
import com.ecom.repository.UserRepository;

@Service
public class OrderService {
	
	@Autowired private OrderRepository orepo;
	
	@Autowired private UserRepository userRepository;
	@Autowired private OrderDetailsService odsrv;
	
	public List<Order> allOrders(){
		return orepo.findAll(Sort.by(Direction.DESC,"id"));
	}
	
	
	
	public List<Order> allUserOrders(String userid){
		return orepo.findByUseridOrderByIdDesc(userid);
	}
	
	public Order getOrderDetails(int orderid) {
		return orepo.findById(orderid).get();
	}
	
	public void cancelOrder(int orderid) {
		odsrv.deleteAllItems(orderid);
		orepo.deleteById(orderid);
	}
	
//	public void confirmOrder(int orderid) {
//		Order order=orepo.getById(orderid);
//		order.setStatus("Confirmed");
//		orepo.save(order);
//	}
	
	public int placeOrder(Order o,int userid) {
		//Optional<User> currentUsd=this.userRepository.findUserByEmail(userid);
		User customer=userRepository.findUserById(userid);
		o.setUserid(userid);
		o.setCustomer(customer);
		Order order=orepo.save(o);
		//GlobalData.cart=cartsrv.findItemsByUserId(userid);
		for(Product c : GlobalData.cart) {
			OrderDetails od=new OrderDetails();
			od.setOrder(order);
			od.setOrderid(order.getId());
			od.setProduct(c);
//		od.setQty(c.getQty());
			od.setProdid(c.getId());
			od.setPrice(c.getPrice());
			odsrv.saveItem(od);
		}		
		GlobalData.cart.clear();
		return o.getId();
	}
}
