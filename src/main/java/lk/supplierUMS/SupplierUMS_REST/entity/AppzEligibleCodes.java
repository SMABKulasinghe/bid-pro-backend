package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Appz_Eligible_Codes")
public class AppzEligibleCodes implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_eligablecoe")
	private Long producteligablecoe;
	
	@Column(name="tender_no")
	private String tenderNo;
	
	@Column(name="eligible_category_codes")
	private String eligiblecategorycodes;

	@Column(name="sub_category")
	private String subcategory;

	@Column(name="product_category")
	private String productcategory;

	@Column(name="comment")
	private String comment;

	public Long getProducteligablecoe() {
		return producteligablecoe;
	}

	public void setProducteligablecoe(Long producteligablecoe) {
		this.producteligablecoe = producteligablecoe;
	}

	public String getTenderNo() {
		return tenderNo;
	}

	public void setTenderNo(String tenderNo) {
		this.tenderNo = tenderNo;
	}

	public String getEligiblecategorycodes() {
		return eligiblecategorycodes;
	}

	public void setEligiblecategorycodes(String eligiblecategorycodes) {
		this.eligiblecategorycodes = eligiblecategorycodes;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getProductcategory() {
		return productcategory;
	}

	public void setProductcategory(String productcategory) {
		this.productcategory = productcategory;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
