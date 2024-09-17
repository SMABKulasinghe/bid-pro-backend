package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="m_user_role_options")
public class UserRoleOptions implements Serializable {
	
	@Id
	@Column(name="m_user_role_options_id")
	private int userRoleOptionsId;
	
	@Column(name="m_user_role_options_name")
	private String userRoleOptionsName;
	
	@Column(name="m_user_role_options_code")
	private String userRoleOptionsCode;
	
	@Column(name="m_user_options_type")
	private String museroptionstype;
	
	@Column(name="status")
	private String status;
	
	@Column(name="m_user_role_owner")
	private String roleOwner;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "options")
    private List<UserRole> userRoles = new ArrayList<>();
	
	public String getUserRoleOptionsCode() {
		return userRoleOptionsCode;
	}

	public void setUserRoleOptionsCode(String userRoleOptionsCode) {
		this.userRoleOptionsCode = userRoleOptionsCode;
	}

	public UserRoleOptions(UserRoleOptions uro) {
		this.userRoleOptionsId = uro.getUserRoleOptionsId();
		this.userRoleOptionsName = uro.getUserRoleOptionsName();
	}
	
	public UserRoleOptions(){
		
	}
	

	public UserRoleOptions(int userRoleOptionsId, String userRoleOptionsName) {
		super();
		this.userRoleOptionsId = userRoleOptionsId;
		this.userRoleOptionsName = userRoleOptionsName;
	}

	public int getUserRoleOptionsId() {
		return userRoleOptionsId;
	}

	public void setUserRoleOptionsId(int userRoleOptionsId) {
		this.userRoleOptionsId = userRoleOptionsId;
	}

	public String getUserRoleOptionsName() {
		return userRoleOptionsName;
	}

	public void setUserRoleOptionsName(String userRoleOptionsName) {
		this.userRoleOptionsName = userRoleOptionsName;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public String getMuseroptionstype() {
		return museroptionstype;
	}

	public void setMuseroptionstype(String museroptionstype) {
		this.museroptionstype = museroptionstype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRoleOwner() {
		return roleOwner;
	}

	public void setRoleOwner(String roleOwner) {
		this.roleOwner = roleOwner;
	}
	
	

}
