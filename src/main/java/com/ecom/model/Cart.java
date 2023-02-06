package com.ecom.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Cart {
	public void setProdid(long prodid) {
		this.prodid = prodid;
	}
	@Override
	public String toString() {
		return "Cart [id=" + id + ", customer=" + customer + ", product=" + product + ", prodid=" + prodid + ", userid="
				+ userid + "]";
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "id", referencedColumnName = "id",insertable = false,updatable = false)
	private User customer;
	@OneToOne
	@JoinColumn(name = "id", referencedColumnName = "id",insertable = false,updatable = false)
	private Product product;
	
	private long prodid;
	private int userid;
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getProdid() {
		return prodid;
	}
	public void setProdid(int prodid) {
		this.prodid = prodid;
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
	
	
	
}
