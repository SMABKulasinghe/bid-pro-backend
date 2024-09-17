package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
@Table(name="t_evaluation_committee_auth")
public class EvaluationCommiteeAuth {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long t_evaluation_committee_auth_id;
	
	@Column(name="tender_id")
	private Long tenderID;
	
	/*
	 * @Column(name="committee_id") private Long committeeId;
	 * 
	 * @Column(name="marksheet_id") private Long marksheetID;
	 */
	
	@Column(name="is_approved")
	private Integer isApproved; // approved - 1, rejected - 0
	
	@Column(name="comment")
	private String comment;
	
	
	
	
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
    
    
    

	public Long getT_evaluation_committee_auth_id() {
		return t_evaluation_committee_auth_id;
	}

	public void setT_evaluation_committee_auth_id(Long t_evaluation_committee_auth_id) {
		this.t_evaluation_committee_auth_id = t_evaluation_committee_auth_id;
	}

	public Long getTenderID() {
		return tenderID;
	}

	public void setTenderID(Long tenderID) {
		this.tenderID = tenderID;
	}

	/*
	 * public Long getCommitteeId() { return committeeId; }
	 * 
	 * public void setCommitteeId(Long committeeId) { this.committeeId =
	 * committeeId; }
	 * 
	 * public Long getMarksheetID() { return marksheetID; }
	 * 
	 * public void setMarksheetID(Long marksheetID) { this.marksheetID =
	 * marksheetID; }
	 */

	public Integer getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Integer isApproved) {
		this.isApproved = isApproved;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
    
    
    

}
