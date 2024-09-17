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
@Table(name="p_terms_and_conditions")
public class TermsAndConditions implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="terms_and_con_id")
	private Long termsAndConditionsId;
	
	@Column(name="terms_or_conditions")
	private String termsConditions;
	
	@Column(name="terms_or_conditions_without_space")
	private String termsOrConditionsWithoutSpace;

	@Column(name="status")
	private Long status;
	
	@Column(name="created_date_and_time")
	private LocalDateTime createdDateTime;
	
	@Column(name="created_user")
	private String createdUser;

	public Long getTermsAndConditionsId() {
		return termsAndConditionsId;
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

	public void setTermsAndConditionsId(Long termsAndConditionsId) {
		this.termsAndConditionsId = termsAndConditionsId;
	}

	
	public String getTermsConditions() {
		return termsConditions;
	}



	public void setTermsConditions(String termsConditions) {
		this.termsConditions = termsConditions;
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

	public String getTermsOrConditionsWithoutSpace() {
		return termsOrConditionsWithoutSpace;
	}

	public void setTermsOrConditionsWithoutSpace(String termsOrConditionsWithoutSpace) {
		this.termsOrConditionsWithoutSpace = termsOrConditionsWithoutSpace;
	}
	
}
