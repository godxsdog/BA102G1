package com.PetYM.Withdraw;

import java.util.List;



public interface Withdraw_interface {
	void add(Withdraw withdraw);
	void update(Withdraw withdraw);
	void delete(int withdrawNo);
	Withdraw findByPk(int withdrawNo);
	List<Withdraw> getAll();
}
