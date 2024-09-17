package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="t_rfp_details_response")
public class RFPDetailsResponse implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="t_rfp_detail_response_id")
	private Long rfpDetailResId;
	
	@Column(name="rfp_h_id")
	private Long rfpHId;
	
	@Column(name="rfp_id")
	private Long rfpID;
	
	@Column(name="rfp_d_id")
	private Long rfpDId;
	
	@Column(name="tender_id")
	private Long tenderID;
	
	@Column(name="suppiler_id_company_code")
	private Long companyCode;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="vender_response")
	private String venderResponse;
	
	@Column(name="vender_comment")
	private String venderComment;
	
	@Column(name="units")
	private String units;
	
	@Column(name="qty")
	private Long qty;
	
	@Column(name="price")
	private Long price;
	
	public String getUnits() {
		return units;
	}

	public Long getQty() {
		return qty;
	}

	public Long getPrice() {
		return price;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getRfpDetailResId() {
		return rfpDetailResId;
	}

	public Long getRfpHId() {
		return rfpHId;
	}

	public Long getRfpID() {
		return rfpID;
	}

	public Long getRfpDId() {
		return rfpDId;
	}

	public Long getTenderID() {
		return tenderID;
	}

	public Long getCompanyCode() {
		return companyCode;
	}

	public String getUserId() {
		return userId;
	}

	public String getVenderResponse() {
		return venderResponse;
	}

	public String getVenderComment() {
		return venderComment;
	}

	public void setRfpDetailResId(Long rfpDetailResId) {
		this.rfpDetailResId = rfpDetailResId;
	}

	public void setRfpHId(Long rfpHId) {
		this.rfpHId = rfpHId;
	}

	public void setRfpID(Long rfpID) {
		this.rfpID = rfpID;
	}

	public void setRfpDId(Long rfpDId) {
		this.rfpDId = rfpDId;
	}

	public void setTenderID(Long tenderID) {
		this.tenderID = tenderID;
	}

	public void setCompanyCode(Long companyCode) {
		this.companyCode = companyCode;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setVenderResponse(String venderResponse) {
		this.venderResponse = venderResponse;
	}

	public void setVenderComment(String venderComment) {
		this.venderComment = venderComment;
	}

}
