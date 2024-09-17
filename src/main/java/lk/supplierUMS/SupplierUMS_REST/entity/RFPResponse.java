package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="p_rfp_response")
public class RFPResponse implements Serializable{
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="tender_id")
	private String tenderId;
	
	@Column(name = "item_no")
	private long  itemno;
	
	@Column(name="description")
	private String description;
	
	@Column(name="m_user_id")
	private String userId;
	
	@Column(name="c_code")
	private Long companyCode;
	
	@Column(name="rfp_file")
	private String rfpFile;
	
	@Column(name="rfp_id")
	private long rfpId;
	
	@Column(name="updated_at")
	private LocalDate updatedAt;
	
	@Column(name="created_at")
	private LocalDate createdAt;
	
	@Column(name="status")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public long getRfpId() {
		return rfpId;
	}

	public void setRfpId(long rfpId) {
		this.rfpId = rfpId;
	}

	public String getRfpFile() {
		return rfpFile;
	}

	public void setRfpFile(String rfpFile) {
		this.rfpFile = rfpFile;
	}

	public long getId() {
		return id;
	}
	
	public String getUserId() {
		return userId;
	}



	public Long getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(Long companyCode) {
		this.companyCode = companyCode;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	


	public String getTenderId() {
		return tenderId;
	}

	public void setTenderId(String tenderId) {
		this.tenderId = tenderId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getItemno() {
		return itemno;
	}

	public void setItemno(long itemno) {
		this.itemno = itemno;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
