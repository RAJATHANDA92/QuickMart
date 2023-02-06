package com.ecom.globle;

import java.util.ArrayList;
import java.util.List;

import com.ecom.model.Product;

public class GlobalData {

	public static List<Product> cart;
	
	static {
		
		cart=new ArrayList<Product>();
		
	}
}
