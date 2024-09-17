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
@Table(name="m_mit_details")

public class MITDetails implements Serializable {
	
	@Id
	@Column(name="mit_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mitID;
	
	@Column(name="tender_id")
	private Long tenderID;
	
	@Column(name="uploaded_file")
	private String filePath;
	
	@Column(name="uploaded_image")
	private String picuturePath;
	
	@Column(name="description")
	private String description;
	
	
	
	public Long getMitID() {
		return mitID;
	}

	public void setMitID(Long mitID) {
		this.mitID = mitID;
	}

	public Long getTenderID() {
		return tenderID;
	}

	public void setTenderID(Long tenderID) {
		this.tenderID = tenderID;
	}

	public String getPicuturePath() {
		return picuturePath;
	}

	public void setPicuturePath(String picuturePath) {
		this.picuturePath = picuturePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
    
    
    
    
	
	

}
