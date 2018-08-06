package com.orderlist.model;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;



public class OrderListService {
	private OrderList_interface dao;

	public OrderListService() {
		dao = new OrderListDAO();
	}

	public OrderList addOrderList(Integer ordNo, Integer prodNo, Integer proPrice, Integer proQua) {

		OrderList OrderList = new OrderList();

		OrderList.setOrdNo(ordNo);
		OrderList.setProdNo(prodNo);
		OrderList.setProPrice(proPrice);
		OrderList.setProQua(proQua);
		dao.add(OrderList);

		return OrderList;
	}
	
	
	public OrderList updateOrderList(Integer ordNo, Integer prodNo, Integer proPrice, Integer proQua) {

		OrderList orderList = new OrderList();

		orderList.setOrdNo(ordNo);
		orderList.setProdNo(prodNo);
		orderList.setProPrice(proPrice);
		orderList.setProQua(proQua);
		dao.update(orderList);

		return dao.findByPk(ordNo);
	}
	
	
	public void insert2(OrderList ordList, Connection con) {
		dao.insert2(ordList, con);
	}
	public void deleteOrderList(Integer ordNo) {
		dao.delete(ordNo);
	}

	public OrderList getOneOrderList(Integer ordNo) {
		return dao.findByPk(ordNo);
	}

	public List<OrderList> getAll() {
		return dao.getAll();
	}
}
