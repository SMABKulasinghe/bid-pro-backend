package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m_com_sup_map")
public class CompanySupplierMapping implements Serializable {
	 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="m_mapping_id")
	private Long mappingID;
	
	@Column(name="m_company_id")
	private Long companyID;
	
	@Column(name="m_supplier_id")
	private Long supplierID;
	
	@Column(name="m_status")
	private String status;
	
	@Column(name="m_creator")
	private String creater;
	
	@Column(name="m_accepter")
	private String accepter;
	
	@Column(name="m_created_datetime")
	private Date createdDateTime;
	
	@Column(name="m_accepted_datetime")
	private Date acceptedDateTime;

	public Long getMappingID() {
		return mappingID;
	}

	public void setMappingID(Long mappingID) {
		this.mappingID = mappingID;
	}

	public Long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}

	public Long getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(Long supplierID) {
		this.supplierID = supplierID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getAccepter() {
		return accepter;
	}

	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}

	public Date getAcceptedDateTime() {
		return acceptedDateTime;
	}

	public void setAcceptedDateTime(Date acceptedDateTime) {
		this.acceptedDateTime = acceptedDateTime;
	}
	
}
