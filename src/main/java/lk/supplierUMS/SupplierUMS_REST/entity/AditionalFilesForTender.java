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
@Table(name="aditional_files_for_tender")
public class AditionalFilesForTender implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="aditional_details_id")
	private Long aditionalDetailsId;
	
	@Column(name="tender_id")
	private Long tenderId;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="file_name_without_space")
	private String fileNameWithoutSpace;
	
	@Column(name="file_path")
	private String filePath;
	
	@Column(name="status")
	private String status;
	
	@Column(name="created_date_and_time")
	private LocalDateTime createdDateTime;
	
	@Column(name="created_user")
	private String createdUser;
	
	@Column(name="updated_date_and_time")
	private LocalDateTime updatedDateTime;
	
	@Column(name="updated_user")
	private String updatedUser;
	
	@Column(name="lock_status")
	private Long lockStatus;

	public Long getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Long lockStatus) {
		this.lockStatus = lockStatus;
	}

	public Long getAditionalDetailsId() {
		return aditionalDetailsId;
	}

	public Long getTenderId() {
		return tenderId;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileNameWithoutSpace() {
		return fileNameWithoutSpace;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getStatus() {
		return status;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public LocalDateTime getUpdatedDateTime() {
		return updatedDateTime;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setAditionalDetailsId(Long aditionalDetailsId) {
		this.aditionalDetailsId = aditionalDetailsId;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileNameWithoutSpace(String fileNameWithoutSpace) {
		this.fileNameWithoutSpace = fileNameWithoutSpace;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	
	
	
}
