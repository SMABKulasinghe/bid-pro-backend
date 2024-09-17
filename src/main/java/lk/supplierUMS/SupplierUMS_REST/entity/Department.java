package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name="m_department")
public class Department implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="m_dept_id")
	private long deptId;
	
	@NotBlank
	@Size(min=3, max = 50)
	@Column(name="m_dept_name")
	private String departmentName;
	
	@Column(name="m_dept_creator")
	private String deptCreatorID;
	
	@Column(name="m_dept_descript")
	private String description;
	
	@Column(name="m_dept_status")
	private String status;
	
	@Column(name="m_created_datetime")
	private Date createdDateTime;

	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDeptCreatorID() {
		return deptCreatorID;
	}

	public void setDeptCreatorID(String deptCreatorID) {
		this.deptCreatorID = deptCreatorID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
	
	
	
	

}
