package com.PetYM.SubMem;

import java.util.List;


public interface SubMem_Interface {
	
	public void insert(SubMem subMem);//新增會員
	public void update(SubMem subMem);
	public void delete(SubMem subMem);//刪除該會員所追蹤的某一寵物
	public List<SubMem> findByPrimaryKey(Integer actSubMemNo);//秀出該會員所追蹤所有的寵物
	public List<SubMem> getAll();
	
}
