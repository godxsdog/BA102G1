package com.PetYM.Charges;

import java.util.List;



public interface Charges_interface {
	void add(Charges charges);
	void update(Charges charges);
	void delete(int chargeNo);
	Charges findByPk(int chargeNo);
	List<Charges> getAll();
}
