package com.order.model;

import java.util.List;
import java.util.Set;
import com.orderlist.model.OrderList;

public interface Ord_interface {
	void add(Ord order);
	void update(Ord order);
	void delete(int ordNo);
	Ord findByPk(int ordNo);
	List<Ord> getAll();
	
// ---------------------------------
	List<Ord> findByFk(int memNo);
// ---------------------------------	
	public Set<OrderList> getOrderListByOrdno(Integer Ordno);
	// ---------------------------------	
	public void insertWithOrderLists(Ord ord , List<OrderList> list);
}
