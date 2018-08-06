package com.faq.model;

import java.util.List;

public interface FaqDAO_Interface {
	
	public void insert(Faq faq);
	public void update(Faq faq);
	public void delete(int faqNo);
	public Faq findByPrimaryKey(int adNo);
	public List<Faq> findByCategory(String faqCategory);
	public List<Faq> findByTitle(String faqTitle);
	public List<Faq> findByAnswer(String faqAnswer);
	public List<Faq> getAll();	
}