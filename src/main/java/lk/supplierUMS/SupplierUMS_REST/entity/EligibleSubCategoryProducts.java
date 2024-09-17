package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "p_eligible_sub_cat_products")
public class EligibleSubCategoryProducts implements Serializable {

	@Id
	@Column(name = "eligible_sub_cat_prod_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eligiblesubcatProdid;
	
	@Column(name = "eligible_sub_cat_id")
	private Long eligibleSubcatId;

	@Column(name = "eligible_sub_cat_prod_code")
	private String eligibleSubcatProdcode;

	@Column(name = "eligible_sub_cat_prod_name")
	private String eligibleSubcatProdname;

	@Column(name = "eligible_sub_cat_prod_description")
	private String eligibleSubcatProddescription;

	@Column(name = "eligible_sub_cat_prod_type")
	private String eligibleSubcatProdtype;

	@Column(name = "eligible_sub_cat_prod_prefix")
	private String eligibleSubcatProdprefix;

	public Long getEligiblesubcatProdid() {
		return eligiblesubcatProdid;
	}

	public void setEligiblesubcatProdid(Long eligiblesubcatProdid) {
		this.eligiblesubcatProdid = eligiblesubcatProdid;
	}

	public Long getEligibleSubcatId() {
		return eligibleSubcatId;
	}

	public void setEligibleSubcatId(Long eligibleSubcatId) {
		this.eligibleSubcatId = eligibleSubcatId;
	}

	public String getEligibleSubcatProdcode() {
		return eligibleSubcatProdcode;
	}

	public void setEligibleSubcatProdcode(String eligibleSubcatProdcode) {
		this.eligibleSubcatProdcode = eligibleSubcatProdcode;
	}

	public String getEligibleSubcatProdname() {
		return eligibleSubcatProdname;
	}

	public void setEligibleSubcatProdname(String eligibleSubcatProdname) {
		this.eligibleSubcatProdname = eligibleSubcatProdname;
	}

	public String getEligibleSubcatProddescription() {
		return eligibleSubcatProddescription;
	}

	public void setEligibleSubcatProddescription(String eligibleSubcatProddescription) {
		this.eligibleSubcatProddescription = eligibleSubcatProddescription;
	}

	public String getEligibleSubcatProdtype() {
		return eligibleSubcatProdtype;
	}

	public void setEligibleSubcatProdtype(String eligibleSubcatProdtype) {
		this.eligibleSubcatProdtype = eligibleSubcatProdtype;
	}

	public String getEligibleSubcatProdprefix() {
		return eligibleSubcatProdprefix;
	}

	public void setEligibleSubcatProdprefix(String eligibleSubcatProdprefix) {
		this.eligibleSubcatProdprefix = eligibleSubcatProdprefix;
	}

	
	

}
