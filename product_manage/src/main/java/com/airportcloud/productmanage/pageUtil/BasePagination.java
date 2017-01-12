package com.airportcloud.productmanage.pageUtil;


import org.springframework.context.annotation.Configuration;


@Configuration
public class BasePagination<T> {
	private T criteria;
	private PageModel page;
	
	public BasePagination(){
		
	}
	
	public BasePagination(T t,PageModel page){
		this.criteria = t;
		this.page = page;
	}
	public T getCriteria() {
		return criteria;
	}
	public void setCriteria(T criteria) {
		this.criteria = criteria;
	}
	public PageModel getPage() {
		return page;
	}
	public void setPage(PageModel page) {
		this.page = page;
	}
	
	
}
