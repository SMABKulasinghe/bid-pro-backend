package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="product_introducing")
public class Product implements Serializable{

	@EmbeddedId
	ProductPrimaryKey productpk;
	
	@Column(name="product_code")
	String productcode;
	
	@Column(name="descrip")
	String description;
	
	@Column(name="unit_id")
	String unitid;
	
	@Column(name="unit_id_dec")
	String unitiddec;
	
	@Column(name="price_min")
	BigDecimal pricemin;
	
	@Column(name="price_max")
	BigDecimal pricemax;
	
	@Column(name="type")
	String type;
	
	@Column(name="grade")
	String grade;
	
	@Column(name="pack_size")
	String packsize;
	
	@Column(name="picture")
	String picture;
	
	@Column(name="comments")
	String comments;
	
	@Column(name="createddate")
	Date created_date;
	
	@Column(name="createuserid")
	String create_user_id;
	
	@Column(name="approved_id")
	String approved_id;
	
	@Column(name="approved_date")
	Date approved_date;

	public Product() {
		super();
	}

	public ProductPrimaryKey getProductpk() {
		return productpk;
	}

	public void setProductpk(ProductPrimaryKey productpk) {
		this.productpk = productpk;
	}

	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getUnitiddec() {
		return unitiddec;
	}

	public void setUnitiddec(String unitiddec) {
		this.unitiddec = unitiddec;
	}

	public BigDecimal getPricemin() {
		return pricemin;
	}

	public void setPricemin(BigDecimal pricemin) {
		this.pricemin = pricemin;
	}

	public BigDecimal getPricemax() {
		return pricemax;
	}

	public void setPricemax(BigDecimal pricemax) {
		this.pricemax = pricemax;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPacksize() {
		return packsize;
	}

	public void setPacksize(String packsize) {
		this.packsize = packsize;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getCreate_user_id() {
		return create_user_id;
	}

	public void setCreate_user_id(String create_user_id) {
		this.create_user_id = create_user_id;
	}

	public String getApproved_id() {
		return approved_id;
	}

	public void setApproved_id(String approved_id) {
		this.approved_id = approved_id;
	}

	public Date getApproved_date() {
		return approved_date;
	}

	public void setApproved_date(Date approved_date) {
		this.approved_date = approved_date;
	}
	
	
}
