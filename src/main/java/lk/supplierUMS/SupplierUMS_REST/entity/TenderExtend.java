package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name="t_tender_extend")
public class TenderExtend implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="t_extend_id")
	private long extendid;
	
	@Column(name="t_id")
	private long trnderid;
	
	@Column(name="t_bidno")
	private String bidno;
	
	@Column(name="t_name")
	private String tendername;
	
	@Column(name="t_closing_date")
	private LocalDate closingdate;
	
	@Column(name="t_closing_time")
	private LocalTime closingtime;
	
	
	
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
	
	
	
	public long getExtendid() {
		return extendid;
	}

	public void setExtendid(long extendid) {
		this.extendid = extendid;
	}

	public long getTrnderid() {
		return trnderid;
	}

	public void setTrnderid(long trnderid) {
		this.trnderid = trnderid;
	}

	public String getBidno() {
		return bidno;
	}

	public void setBidno(String bidno) {
		this.bidno = bidno;
	}

	public LocalDate getClosingdate() {
		return closingdate;
	}

	public void setClosingdate(LocalDate closingdate) {
		this.closingdate = closingdate;
	}

	public LocalTime getClosingtime() {
		return closingtime;
	}

	public void setClosingtime(LocalTime closingtime) {
		this.closingtime = closingtime;
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

	public String getTendername() {
		return tendername;
	}

	public void setTendername(String tendername) {
		this.tendername = tendername;
	}
	
	


    
    
	
	
	
	
	
	

}
