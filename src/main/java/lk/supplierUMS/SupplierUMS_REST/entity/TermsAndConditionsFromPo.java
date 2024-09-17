package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name="m_terms_n_conditions_from_po")
public class TermsAndConditionsFromPo implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="terms_n_conditions_from_po_id")
	private Long termsnConditonsFromPoId;
	
	@Column(name="terms_n_conditions_trans_id")
	private Long termsAndConditionsTransId;
	
	@Column(name="terms_n_conditions_id")
	private Long termsConditionsId;
	
	@Column(name="terms_n_conditions_id_string")
	private String termsConditionsIdString;
	
	@Column(name="terms_n_conditions_val")
	private String termsConditionsVal;
	
	@Column(name="tender_id")
	private Long tenderId;
	
	@Column(name="rfp_id")
	private Long rfpId;
	
	@Column(name="po_id")
	private Long poId;

	@Column(name="status")
	private Long status;
	
	@Column(name="created_date_and_time")
	private LocalDateTime createdDateTime;
	
	@Column(name="created_user")
	private String createdUser;

	public Long getTermsnConditonsFromPoId() {
		return termsnConditonsFromPoId;
	}

	public Long getTermsAndConditionsTransId() {
		return termsAndConditionsTransId;
	}

	public Long getTermsConditionsId() {
		return termsConditionsId;
	}

	public String getTermsConditionsIdString() {
		return termsConditionsIdString;
	}

	public String getTermsConditionsVal() {
		return termsConditionsVal;
	}

	public Long getTenderId() {
		return tenderId;
	}

	public Long getRfpId() {
		return rfpId;
	}

	public Long getPoId() {
		return poId;
	}

	public Long getStatus() {
		return status;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setTermsnConditonsFromPoId(Long termsnConditonsFromPoId) {
		this.termsnConditonsFromPoId = termsnConditonsFromPoId;
	}

	public void setTermsAndConditionsTransId(Long termsAndConditionsTransId) {
		this.termsAndConditionsTransId = termsAndConditionsTransId;
	}

	public void setTermsConditionsId(Long termsConditionsId) {
		this.termsConditionsId = termsConditionsId;
	}

	public void setTermsConditionsIdString(String termsConditionsIdString) {
		this.termsConditionsIdString = termsConditionsIdString;
	}

	public void setTermsConditionsVal(String termsConditionsVal) {
		this.termsConditionsVal = termsConditionsVal;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}

	public void setRfpId(Long rfpId) {
		this.rfpId = rfpId;
	}

	public void setPoId(Long poId) {
		this.poId = poId;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	
	
}
