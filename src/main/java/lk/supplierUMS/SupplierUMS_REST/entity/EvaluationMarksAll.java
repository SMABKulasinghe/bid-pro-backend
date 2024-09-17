package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_evaluation_all_marks")
public class EvaluationMarksAll implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="t_evaluation_all_marks_id")
	private Long evaluationAllMarksID;
	//ok
	
	@Column(name="tender_id")
	private Long tenderId;
	//ok
	
	@Column(name="ave_marks")
	private Double aveMarks;
	//ok
	
	@Column(name="c_code")
	private String CompanyCode;
	
	@Column(name="status")
	private String status;
	
	@Column(name="offering_supplier")
	private String offeringSupplier;
	
	@Column(name="logged_user")
	private String loggedUser;

	@Column(name="reason")
	private String reason;
	
	@Column(name="created_datetime")
	private LocalDateTime createdDateTime;
	
	@Column(name="updated_datetime")
	private LocalDateTime updatedDateTime;
	
	@Column(name="vote_from_procurement")
	private Long voteFromProcument;

	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getOfferingSupplier() {
		return offeringSupplier;
	}

	public String getReason() {
		return reason;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public LocalDateTime getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setOfferingSupplier(String offeringSupplier) {
		this.offeringSupplier = offeringSupplier;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setCreatedDateTime(LocalDateTime localDateTime) {
		this.createdDateTime = localDateTime;
	}

	public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getEvaluationAllMarksID() {
		return evaluationAllMarksID;
	}

	public Long getTenderId() {
		return tenderId;
	}

	public Double getAveMarks() {
		return aveMarks;
	}

	public String getCompanyCode() {
		return CompanyCode;
	}

	public void setEvaluationAllMarksID(Long evaluationAllMarksID) {
		this.evaluationAllMarksID = evaluationAllMarksID;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}

	public void setAveMarks(Double aveMarks) {
		this.aveMarks = aveMarks;
	}

	public void setCompanyCode(String companyCode) {
		CompanyCode = companyCode;
	}

	public Long getVoteFromProcument() {
		return voteFromProcument;
	}

	public void setVoteFromProcument(Long voteFromProcument) {
		this.voteFromProcument = voteFromProcument;
	}
	
	
	
	
}
