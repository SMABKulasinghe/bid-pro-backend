package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="t_tender_evaluation_committee")
public class EvaluationCommitee implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long t_tender_evaluation_committee_id;
	
	@Column(name="tender_id")
	private Long tenderID;
	
	@Column(name="vendor_id")
	private Long supplierID;
	
	@Column(name="submited_user")
	private String submitedUserID;

	@Column(name="seq_id")
	private Integer sequenceNo;
	
	@Column(name="isSubmited")
	private Boolean isSubmited; 
	
	@Column(name="position")
	private String position; 
	
	@Column(name="status")
	private Integer status;
	
	@CreationTimestamp
    private LocalDateTime createdAt ;
	

	public Long getT_tender_evaluation_committee_id() {
		return t_tender_evaluation_committee_id;
	}

	public void setT_tender_evaluation_committee_id(Long t_tender_evaluation_committee_id) {
		this.t_tender_evaluation_committee_id = t_tender_evaluation_committee_id;
	}

	public Long getTenderID() {
		return tenderID;
	}

	public void setTenderID(Long tenderID) {
		this.tenderID = tenderID;
	}

	public Long getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(Long supplierID) {
		this.supplierID = supplierID;
	}

	public String getSubmitedUserID() {
		return submitedUserID;
	}

	public void setSubmitedUserID(String submitedUserID) {
		this.submitedUserID = submitedUserID;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Boolean getIsSubmited() {
		return isSubmited;
	}

	public void setIsSubmited(Boolean isSubmited) {
		this.isSubmited = isSubmited;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
