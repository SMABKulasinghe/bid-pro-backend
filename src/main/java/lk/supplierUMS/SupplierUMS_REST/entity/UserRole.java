package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.JoinColumn;

@Entity
@Table(name="m_user_role")
public class UserRole implements Serializable {
	
	@Id
	@Column(name="m_user_role_id")
	private long userRoleID;
	
	@Column(name="m_user_role_name")
	private String userRoleName;
	
	@Column(name="m_user_role_description")
	private String userRoleDescription;
	
	@Column(name="m_role_in_business")
	private String userRoleInBussiness; // users role in business whether a company or supplier
	
	
	@Column(name="m_role_status")
	private Integer userRoleStatus;
	
	
	@Column(name="m_status_flag")
	private String statusflag;
	
	@CreationTimestamp
    private Date createdAt;

	@UpdateTimestamp
    private Date updatedAt;
    
    @Column(name = "created_by", nullable = false, updatable = false)
    @CreatedBy
    private String createdBy;
    
    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;
    
    @Column(name="c_code")
	private long companyCode;
    
    @Column(name="m_role_apprvreason")
	private String roleApprovalReason;

	
	public String getRoleApprovalReason() {
		return roleApprovalReason;
	}

	public void setRoleApprovalReason(String roleApprovalReason) {
		this.roleApprovalReason = roleApprovalReason;
	}

	public long getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(long companyCode) {
		this.companyCode = companyCode;
	}

	public String getStatusflag() {
		return statusflag;
	}

	public void setStatusflag(String statusflag) {
		this.statusflag = statusflag;
	}




    @PrePersist
    public void setCreatedOn() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		this.createdBy = user.getUserid();
		this.modifiedBy =  user.getUserid();
		
		
		if (authentication.getPrincipal() instanceof User) {
			User us = (User) authentication.getPrincipal();
			
			System.out.println("userID--------- "+ us.getUserid());
			System.out.println("userComapnyCode--------- "+ us.getUserCompanyCode());		
			System.out.println("userRoleInBusiness--------- "+ us.getUserRoleInBussiness());
			
			
	
			System.out.println("userComapnyName--------- "+ us.getMmiddlenname());
			System.out.println("userRoleID--------- "+ us.getUserRoll());
			
		}
    }
    
    @PreUpdate
    public void preUpdate() {
        String modifiedByUser =  SecurityContextHolder.getContext().getAuthentication().getName();
        this.modifiedBy = modifiedByUser;
    }
	
	
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}



	@ManyToMany(cascade = {
	        CascadeType.PERSIST,
	        CascadeType.MERGE
	    })
	    @JoinTable(name = "m_user_has_user_role_options",
	        joinColumns = @JoinColumn(name = "m_user_role_id"),
	        inverseJoinColumns = @JoinColumn(name = "m_user_role_options_id")
	    )
	    private List<UserRoleOptions> options = new ArrayList<>();
	
	public Integer getUserRoleStatus() {
		return userRoleStatus;
	}

	public void setUserRoleStatus(Integer userRoleStatus) {
		this.userRoleStatus = userRoleStatus;
	}

	
	public UserRole(UserRole ur){
		this.userRoleID = ur.getUserRoleID();
		this.userRoleName = ur.getUserRoleName();
	}
	
	public UserRole(){
		
	}


	

	public long getUserRoleID() {
		return userRoleID;
	}

	public void setUserRoleID(long userRoleID) {
		this.userRoleID = userRoleID;
	}

	public String getUserRoleName() {
		return userRoleName;
	}


	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}


	public List<UserRoleOptions> getOptions() {
		return options;
	}


	public void setOptions(List<UserRoleOptions> options) {
		this.options = options;
	}

	public String getUserRoleDescription() {
		return userRoleDescription;
	}

	public void setUserRoleDescription(String userRoleDescription) {
		this.userRoleDescription = userRoleDescription;
	}

	public String getUserRoleInBussiness() {
		return userRoleInBussiness;
	}

	public void setUserRoleInBussiness(String userRoleInBussiness) {
		this.userRoleInBussiness = userRoleInBussiness;
	}
	
	

}
