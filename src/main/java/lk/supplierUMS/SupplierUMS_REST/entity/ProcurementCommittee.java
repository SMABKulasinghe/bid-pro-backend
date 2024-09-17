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
@Table(name="m_procurement_committee")
public class ProcurementCommittee implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="proc_com_id")
	private long procComId;
	
	@Column(name="tender_id")
	private long tenderId;
	
	@Column(name="supplier_id")
	private long supplierId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="validate_or_not")
	private long validateOrNot;
	
	@Column(name="comment")
	private String comment;

	@Column(name="created_user")
	private String createdUser;
	
	@Column(name="created_date_and_time")
	private LocalDateTime createdDateTime;
	
	@Column(name="t_supportdoc1_name")
	private String supportdoc1;
	
	@Column(name="t_supportdoc2_name")
	private String supportdoc2;
	
	@Column(name="t_supportdoc3_name")
	private String supportdoc3;

	public String getSupportdoc1() {
		return supportdoc1;
	}

	public void setSupportdoc1(String supportdoc1) {
		this.supportdoc1 = supportdoc1;
	}

	public String getSupportdoc2() {
		return supportdoc2;
	}

	public void setSupportdoc2(String supportdoc2) {
		this.supportdoc2 = supportdoc2;
	}

	public String getSupportdoc3() {
		return supportdoc3;
	}

	public void setSupportdoc3(String supportdoc3) {
		this.supportdoc3 = supportdoc3;
	}

	public long getProcComId() {
		return procComId;
	}

	public long getTenderId() {
		return tenderId;
	}

	public String getUserId() {
		return userId;
	}

	public long getValidateOrNot() {
		return validateOrNot;
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

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setProcComId(long procComId) {
		this.procComId = procComId;
	}

	public void setTenderId(long tenderId) {
		this.tenderId = tenderId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setValidateOrNot(long validateOrNot) {
		this.validateOrNot = validateOrNot;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	
	
}
