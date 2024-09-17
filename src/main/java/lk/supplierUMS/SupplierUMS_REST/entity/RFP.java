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
@Table(name="p_rfp")
public class RFP implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="rfp_id")
	private Long rfpID;
	
	@Column(name="rfp_no")
	private String rfpNo;

	@Column(name="file_name")
	private String fileName;
	
	@Column(name="status")
	private String status;
	
	@Column(name="loged_user_id")
	private String logedUserId;
	
	@Column(name="tender_id")
	private String tenderId;
	
	@Column(name="eligible_cat_id")
	private Long eligibleCatId;
	
	@Column(name="created_date_and_time")
	private LocalDateTime createdDateTime;
	
	@Column(name="created_user")
	private String createdUser;
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="dept_id")
	private Long deptId;
	
	@Column(name="pr_id")
	private Long prId;
	
	@Column(name="uploaded_file")
	private String uploadedFile;
	
	@Column(name="eligible_sub_cat_id")
	private Long eligibleSubCatId;
	
	public Long getEligibleSubCatId() {
		return eligibleSubCatId;
	}

	public void setEligibleSubCatId(Long eligibleSubCatId) {
		this.eligibleSubCatId = eligibleSubCatId;
	}

	public String getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(String uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Long getPrId() {
		return prId;
	}

	public void setPrId(Long prId) {
		this.prId = prId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Long getEligibleCatId() {
		return eligibleCatId;
	}

	public void setEligibleCatId(Long eligibleCatId) {
		this.eligibleCatId = eligibleCatId;
	}

	public String getTenderId() {
		return tenderId;
	}

	public void setTenderId(String tenderId) {
		this.tenderId = tenderId;
	}

	public String getRfpNo() {
		return rfpNo;
	}

	public void setRfpNo(String rfpNo) {
		this.rfpNo = rfpNo;
	}
	
	public String getLogedUserId() {
		return logedUserId;
	}

	public void setLogedUserId(String logedUserId) {
		this.logedUserId = logedUserId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getRfpID() {
		return rfpID;
	}



	public String getFileName() {
		return fileName;
	}

	public void setRfpID(Long rfpID) {
		this.rfpID = rfpID;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
