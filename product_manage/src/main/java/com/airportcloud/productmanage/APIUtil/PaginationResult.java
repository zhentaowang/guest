package com.airportcloud.productmanage.APIUtil;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @ClassName: PaginationResult
 * @Description: 首页查询返回数据
 * @author zhangpengfei
 * @date 2016年4月29日 上午9:15:09 
 * @version V1.0
 */
@ApiModel(value="分页查询结果")
@JsonInclude(Include.NON_NULL)
public class PaginationResult<T> {

	@ApiModelProperty(value= "总数", name="total",required = true)
	private int total;
	
	@ApiModelProperty(value ="结果集", name="rows", required = true)
	private List<T> rows;
	
	public PaginationResult(int total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	
	public PaginationResult(int total,List<T> columns,List<T> rows) {
		this.total = total;
		this.rows = rows;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "EasyUIQueryResult [total=" + total + ", rows=" + rows + "]";
	}
}
