package com.ecom.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date orderdate=new Date();
	private int userid;
//	
	
	@OneToOne
	@JoinColumn(name = "id", referencedColumnName = "id",insertable = false,updatable = false)
	private User customer;
	
	@OneToMany
	@JoinColumn(name = "orderid")
	private Set<OrderDetails> orderdetails=new HashSet<OrderDetails>();
	
//	
	
	public Set<OrderDetails> getOrderdetails() {
		return orderdetails;
	}
	public void setOrderdetails(Set<OrderDetails> orderdetails) {
		this.orderdetails = orderdetails;
	}
//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public User getCustomer() {
		return customer;
	}
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	
	
	
}
