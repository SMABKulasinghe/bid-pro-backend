package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
@Table(name="p_eligible_category")
public class EligibleCategory implements Serializable {
	
	@Id
	@Column(name="eligible_cat_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eligibleCategortID;

	@Column(name="eligible_cat_code")
	private String eligibleCategortCode;
	
	@Column(name="eligible_cat_name")
	private String eligibleCategortName;
	
	@Column(name="eligible_cat_description")
	private String eligibleCategortDes;
	
	@Column(name="eligible_cat_type")
	private String eligibleCategortType;
	
	@Column(name="eligible_category_fee")
	private Double eligibleCategoryFee;
	
	@Column(name="eligible_status")
	private Long status;
	
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

			// Audits
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
	
	

	public Double getEligibleCategoryFee() {
		return eligibleCategoryFee;
	}

	public void setEligibleCategoryFee(Double eligibleCategoryFee) {
		this.eligibleCategoryFee = eligibleCategoryFee;
	}

	public Long getEligibleCategortID() {
		return eligibleCategortID;
	}

	public void setEligibleCategortID(Long eligibleCategortID) {
		this.eligibleCategortID = eligibleCategortID;
	}

	public String getEligibleCategortCode() {
		return eligibleCategortCode;
	}

	public void setEligibleCategortCode(String eligibleCategortCode) {
		this.eligibleCategortCode = eligibleCategortCode;
	}

	public String getEligibleCategortName() {
		return eligibleCategortName;
	}

	public void setEligibleCategortName(String eligibleCategortName) {
		this.eligibleCategortName = eligibleCategortName;
	}

	public String getEligibleCategortDes() {
		return eligibleCategortDes;
	}

	public void setEligibleCategortDes(String eligibleCategortDes) {
		this.eligibleCategortDes = eligibleCategortDes;
	}

	public String getEligibleCategortType() {
		return eligibleCategortType;
	}

	public void setEligibleCategortType(String eligibleCategortType) {
		this.eligibleCategortType = eligibleCategortType;
	}
	
	
	
	
	
}
