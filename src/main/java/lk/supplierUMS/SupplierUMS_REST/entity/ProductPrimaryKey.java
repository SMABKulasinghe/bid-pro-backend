package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class ProductPrimaryKey implements Serializable{

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;	
	
	@Column(name="suppliercode")
	String suppliercode;
	
	@Column(name="companycode")
	String companycode;

	public ProductPrimaryKey() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSuppliercode() {
		return suppliercode;
	}

	public void setSuppliercode(String suppliercode) {
		this.suppliercode = suppliercode;
	}

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	
	
}
