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
@Table(name="t_terms_and_conditions_trans")
public class TermsAndConditionsTrans implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="terms_and_con_trans_id")
	private Long termsAndConditionsTransId;
	
	@Column(name="terms_or_conditions_id")
	private Long termsConditionsId;
	
	@Column(name="tender_id")
	private Long tenderId;

	@Column(name="status")
	private Long status;
	
	@Column(name="created_date_and_time")
	private LocalDateTime createdDateTime;
	
	@Column(name="created_user")
	private String createdUser;

	public Long getTermsAndConditionsTransId() {
		return termsAndConditionsTransId;
	}

	

	public Long getTenderId() {
		return tenderId;
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

	public void setTermsAndConditionsTransId(Long termsAndConditionsTransId) {
		this.termsAndConditionsTransId = termsAndConditionsTransId;
	}

	

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
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



	public Long getTermsConditionsId() {
		return termsConditionsId;
	}



	public void setTermsConditionsId(Long termsConditionsId) {
		this.termsConditionsId = termsConditionsId;
	}
	
	
	
}
