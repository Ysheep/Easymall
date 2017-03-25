package cn.tedu.domain;

import java.util.List;

public class Page {
	
	private int thispage;//当前页数
	private int rowperpage;//每页显示的行数
	//查询条件对应的四个属性
	private String name;
	private String category;
	private Double min;//定义为包装类方便无结果时返回null
	private Double max;

	private List<Product> list;
	private int sumpage;//总页数
	private int sumrow;//总行数
	private int backpage;//上一页的页码
	private int nextpage;//下一页的页码
	
	
	public List<Product> getList() {
		return list;
	}
	public void setList(List<Product> list) {
		this.list = list;
	}
	public int getSumpage() {
		return sumpage;
	}
	public void setSumpage(int sumpage) {
		this.sumpage = sumpage;
	}
	public int getThispage() {
		return thispage;
	}
	public void setThispage(int thispage) {
		this.thispage = thispage;
	}
	public int getSumrow() {
		return sumrow;
	}
	public void setSumrow(int sumrow) {
		this.sumrow = sumrow;
	}
	public int getRowperpage() {
		return rowperpage;
	}
	public void setRowperpage(int rowperpage) {
		this.rowperpage = rowperpage;
	}
	public int getBackpage() {
		return backpage;
	}
	public void setBackpage(int backpage) {
		this.backpage = backpage;
	}
	public int getNextpage() {
		return nextpage;
	}
	public void setNextpage(int nextpage) {
		this.nextpage = nextpage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getcategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		this.min = min;
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}
	
	

}
