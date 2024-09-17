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
@Table(name="t_rfp_evaluation_marks")
public class RfpEvaluationMarks implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="rfp_evaluation_marks_id")
	private Long rfpEvaMarksID;
	
	@Column(name="tender_id")
	private Long tenderId;
	
	@Column(name="supplier_id")
	private Long supplierId;
	
	@Column(name="marks_given_by_user_id")
	private String marksGivenByUserId;
	
	@Column(name="t_rfp_detail_response_id")
	private Long rfpDetailResId;
	
	@Column(name="marks")
	private Long marks;
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="created_date_and_time")
	private LocalDateTime createdDateTime;
	
	@Column(name="created_user")
	private String createdUser;
	
	@Column(name="evaluator_user_id")
	private String evaluatorUserId;

	@Column(name="rfp_h_id")
	private Long rfpHId;
	
	@Column(name="rfp_d_id")
	private Long rfpDId;
	
	@Column(name="rfp_id")
	private Long rfpID;

	public Long getRfpHId() {
		return rfpHId;
	}

	public Long getRfpDId() {
		return rfpDId;
	}

	public Long getRfpID() {
		return rfpID;
	}

	public void setRfpHId(Long rfpHId) {
		this.rfpHId = rfpHId;
	}

	public void setRfpDId(Long rfpDId) {
		this.rfpDId = rfpDId;
	}

	public void setRfpID(Long rfpID) {
		this.rfpID = rfpID;
	}

	public String getEvaluatorUserId() {
		return evaluatorUserId;
	}

	public void setEvaluatorUserId(String evaluatorUserId) {
		this.evaluatorUserId = evaluatorUserId;
	}

	public Long getRfpEvaMarksID() {
		return rfpEvaMarksID;
	}

	public Long getTenderId() {
		return tenderId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public String getMarksGivenByUserId() {
		return marksGivenByUserId;
	}

	public Long getRfpDetailResId() {
		return rfpDetailResId;
	}

	public Long getMarks() {
		return marks;
	}

	public String getComment() {
		return comment;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setRfpEvaMarksID(Long rfpEvaMarksID) {
		this.rfpEvaMarksID = rfpEvaMarksID;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public void setMarksGivenByUserId(String marksGivenByUserId) {
		this.marksGivenByUserId = marksGivenByUserId;
	}

	public void setRfpDetailResId(Long rfpDetailResId) {
		this.rfpDetailResId = rfpDetailResId;
	}

	public void setMarks(Long marks) {
		this.marks = marks;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	
	
	
	
}
