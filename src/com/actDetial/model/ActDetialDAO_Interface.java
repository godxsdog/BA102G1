package com.actDetial.model;

import java.util.List;
import java.util.Map;

public interface ActDetialDAO_Interface {
	void add(ActDetial actDetial);
	void update(ActDetial actDetial);
	void delete(Integer actNo,Integer memNo);
	ActDetial findByPK(Integer actNo,Integer memNo);
	List<ActDetial> getAll();
	Map<Integer,Integer> findByPK(Integer memNo);
}
