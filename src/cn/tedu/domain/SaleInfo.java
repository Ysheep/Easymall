package cn.tedu.domain;


public class SaleInfo {
	@Override
	public String toString() {
		return  prod_id + "," + prod_name+ "," + sale_num;
	}
	String prod_id;
	String prod_name;
	int sale_num;
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public int getSale_num() {
		return sale_num;
	}
	public void setSale_num(int sale_num) {
		this.sale_num = sale_num;
	}
	
	

}
