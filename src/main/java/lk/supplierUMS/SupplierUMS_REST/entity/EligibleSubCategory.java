package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "p_eligible_sub_category")
public class EligibleSubCategory implements Serializable {

	@Id
	@Column(name = "eligible_sub_cat_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eligibleSubcatId;
	
	@Column(name = "eligible_cat_id")
	private Long eligibleCategortID;

	@Column(name = "eligible_sub_cat_code")
	private String eligibleSubcatcode;

	@Column(name = "eligible_sub_cat_name")
	private String eligibleSubcatname;

	@Column(name = "eligible_sub_cat_description")
	private String eligibleSubcatdescription;

	@Column(name = "eligible_sub_cat_type")
	private String eligibleSubcattype;

	@Column(name = "eligible_sub_cat_prefix")
	private String eligibleSubcatprefix;
	
	@Column(name = "eligible_sub_cat_fee")
	private Double eligibleSubcatFee;

	
	
	public Double getEligibleSubcatFee() {
		return eligibleSubcatFee;
	}

	public void setEligibleSubcatFee(Double eligibleSubcatFee) {
		this.eligibleSubcatFee = eligibleSubcatFee;
	}

	public Long getEligibleSubcatId() {
		return eligibleSubcatId;
	}

	public void setEligibleSubcatId(Long eligibleSubcatId) {
		this.eligibleSubcatId = eligibleSubcatId;
	}

	public Long getEligibleCategortID() {
		return eligibleCategortID;
	}

	public void setEligibleCategortID(Long eligibleCategortID) {
		this.eligibleCategortID = eligibleCategortID;
	}

	public String getEligibleSubcatcode() {
		return eligibleSubcatcode;
	}

	public void setEligibleSubcatcode(String eligibleSubcatcode) {
		this.eligibleSubcatcode = eligibleSubcatcode;
	}

	public String getEligibleSubcatname() {
		return eligibleSubcatname;
	}

	public void setEligibleSubcatname(String eligibleSubcatname) {
		this.eligibleSubcatname = eligibleSubcatname;
	}

	public String getEligibleSubcatdescription() {
		return eligibleSubcatdescription;
	}

	public void setEligibleSubcatdescription(String eligibleSubcatdescription) {
		this.eligibleSubcatdescription = eligibleSubcatdescription;
	}

	public String getEligibleSubcattype() {
		return eligibleSubcattype;
	}

	public void setEligibleSubcattype(String eligibleSubcattype) {
		this.eligibleSubcattype = eligibleSubcattype;
	}

	public String getEligibleSubcatprefix() {
		return eligibleSubcatprefix;
	}

	public void setEligibleSubcatprefix(String eligibleSubcatprefix) {
		this.eligibleSubcatprefix = eligibleSubcatprefix;
	}
	
	

	

}
