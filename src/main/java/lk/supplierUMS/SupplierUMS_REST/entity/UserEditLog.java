package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="m_user_edit_log")
public class UserEditLog implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="m_user_edit_log_id")
	private Long userEditLogId; 

	@Column(name="user_id")
	private String userId; 
	
	@Column(name="company_Code")
	private Long companyCode; 

	@Column(name="updated_user")
	private String updatedUser; 
	
	@Column(name="updated_date_time")
	private LocalDateTime updatedDateTime; 
	
	@Column(name="created_user")
	private String createdUser; 
	
	@Column(name="created_date_time")
	private LocalDateTime createdDateTime;
	
	@Column(name="status")
	private Long status;
	
	@Column(name="comment")
	private String comment;

	public Long getUserEditLogId() {
		return userEditLogId;
	}

	public String getUserId() {
		return userId;
	}

	public Long getCompanyCode() {
		return companyCode;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public LocalDateTime getUpdatedDateTime() {
		return updatedDateTime;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public Long getStatus() {
		return status;
	}

	public String getComment() {
		return comment;
	}

	public void setUserEditLogId(Long userEditLogId) {
		this.userEditLogId = userEditLogId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setCompanyCode(Long companyCode) {
		this.companyCode = companyCode;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
