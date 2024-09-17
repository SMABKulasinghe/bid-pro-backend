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
@Table(name="t_tender_additional_file_upload")
public class AdditionalFileTenderForSupplier implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="t_tender_additional_file_upload_id")
	private Long tenderAdditionalFileUploadId;
	
	@Column(name="tender_id")
	private Long tenderId;
	
	@Column(name="supplier_id")
	private Long supplierId;
	
	@Column(name="zip_file_path")
	private String zipFilePath;
	
	@Column(name="created_date_and_time")
	private LocalDateTime createdDateTime;
	
	@Column(name="created_user")
	private String createdUser;
	
	@Column(name="updated_user")
	private String updateUser;
	
	@Column(name="update_date_and_time")
	private LocalDateTime updatedDateTime;
	
	@Column(name="status")
	private Long status;
	
	@Column(name="note_and_file_name")
	private String noteAndFileName;
	
	@Column(name="email_status")
	private String emailStatus;
	
	@Column(name="file_uploaded_status")
	private Long fileUploadedStatus;

	public Long getTenderAdditionalFileUploadId() {
		return tenderAdditionalFileUploadId;
	}

	public Long getTenderId() {
		return tenderId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public String getZipFilePath() {
		return zipFilePath;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public LocalDateTime getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setTenderAdditionalFileUploadId(Long tenderAdditionalFileUploadId) {
		this.tenderAdditionalFileUploadId = tenderAdditionalFileUploadId;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public void setZipFilePath(String zipFilePath) {
		this.zipFilePath = zipFilePath;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getNoteAndFileName() {
		return noteAndFileName;
	}

	public void setNoteAndFileName(String noteAndFileName) {
		this.noteAndFileName = noteAndFileName;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public Long getFileUploadedStatus() {
		return fileUploadedStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public void setFileUploadedStatus(Long fileUploadedStatus) {
		this.fileUploadedStatus = fileUploadedStatus;
	}
	
	
	
}
