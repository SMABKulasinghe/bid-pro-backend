package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lk.supplierUMS.SupplierUMS_REST.config.Auditable;

@Entity
@Table(name = "m_user_activity_log")
@EntityListeners(AuditingEntityListener.class)
public class UserActivityLog /*extends Auditable<String> */ implements Serializable  {

	@Id
	@Column(name = "m_act_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long actId;

	@Column(name = "m_act_userid")
	private String actUserId;

	@Column(name = "m_act_user_roleid")
	private String actUserRoleId;

	@Column(name = "m_act_user_role_in_business")
	private String actUserRoleInBusiness;

	@Column(name = "m_act_user_company")
	private String actUserBelongCompany;

	@Column(name = "m_act_user_logged_IP")
	private String actUserLoggedIPAddress;

	@Column(name = "m_act_option")
	private String actOption;

	@Column(name = "m_act_description")
	private String actDescription;

	@Temporal(TemporalType.DATE)
	@Column(name = "m_act_date")
	private Date actDate;
	
	@Column(name = "m_act_remote_address")
	private String remoteAddress;
	
	@Column(name = "m_act_local_address")
	private String localAddress;
	
	@Column(name = "m_act_remote_port")
	private Integer remotePort;
	
	@Column(name = "m_act_local_port")
	private Integer localPort;
	
	@Column(name = "m_sessionID")
	private String sessionID;
	
	@Column(name = "m_servername")
	private String serverName;

	@Column(name = "m_requestedURI")
	private String requestedURI;
	
	@Column(name = "m_remoteOS")
	private String remoteOS;
	
	@Column(name = "m_remoteBrowser")
	private String remoteBrowser;
	
	@Column(name = "m_remoteUserAllDetails")
	private String remoteUserAllDetails;
	
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
		String currentPrincipalName = authentication.getName();
		this.createdBy = currentPrincipalName;
		this.modifiedBy = currentPrincipalName;
		
		
		if (authentication.getPrincipal() instanceof User) {
			User us = (User) authentication.getPrincipal();
			
			System.out.println("userID--------- "+ us.getUserid());
			System.out.println("userComapnyCode--------- "+ us.getUserCompanyCode());		
			System.out.println("userRoleInBusiness--------- "+ us.getUserRoleInBussiness());
			
			this.setActUserId(us.getUserid());
			this.setActUserBelongCompany(us.getUserCompanyCode());
			this.setActUserRoleInBusiness(us.getUserRoleInBussiness());
			
			System.out.println("userComapnyName--------- "+ us.getMmiddlenname());
			System.out.println("userRoleID--------- "+ us.getUserRoll());
			
		}
    }
    
    @PreUpdate
    public void preUpdate() {
        String modifiedByUser =  SecurityContextHolder.getContext().getAuthentication().getName();
        this.modifiedBy = modifiedByUser;
    }
    
    

	public Long getActId() {
		return actId;
	}

	public void setActId(Long actId) {
		this.actId = actId;
	}

	public String getActUserId() {
		return actUserId;
	}

	public void setActUserId(String actUserId) {
		this.actUserId = actUserId;
	}

	public String getActUserRoleId() {
		return actUserRoleId;
	}

	public void setActUserRoleId(String actUserRoleId) {
		this.actUserRoleId = actUserRoleId;
	}

	public String getActUserRoleInBusiness() {
		return actUserRoleInBusiness;
	}

	public void setActUserRoleInBusiness(String actUserRoleInBusiness) {
		this.actUserRoleInBusiness = actUserRoleInBusiness;
	}

	public String getActUserBelongCompany() {
		return actUserBelongCompany;
	}

	public void setActUserBelongCompany(String actUserBelongCompany) {
		this.actUserBelongCompany = actUserBelongCompany;
	}

	public String getActUserLoggedIPAddress() {
		return actUserLoggedIPAddress;
	}

	public void setActUserLoggedIPAddress(String actUserLoggedIPAddress) {
		this.actUserLoggedIPAddress = actUserLoggedIPAddress;
	}

	public String getActOption() {
		return actOption;
	}

	public void setActOption(String actOption) {
		this.actOption = actOption;
	}

	public String getActDescription() {
		return actDescription;
	}

	public void setActDescription(String actDescription) {
		this.actDescription = actDescription;
	}

	public Date getActDate() {
		return actDate;
	}

	public void setActDate(Date actDate) {
		this.actDate = actDate;
	}

	public String getRequestedURI() {
		return requestedURI;
	}

	public void setRequestedURI(String requestedURI) {
		this.requestedURI = requestedURI;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public String getLocalAddress() {
		return localAddress;
	}

	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}

	public Integer getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(Integer remotePort) {
		this.remotePort = remotePort;
	}

	public Integer getLocalPort() {
		return localPort;
	}

	public void setLocalPort(Integer localPort) {
		this.localPort = localPort;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
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

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getRemoteOS() {
		return remoteOS;
	}

	public void setRemoteOS(String remoteOS) {
		this.remoteOS = remoteOS;
	}

	public String getRemoteBrowser() {
		return remoteBrowser;
	}

	public void setRemoteBrowser(String remoteBrowser) {
		this.remoteBrowser = remoteBrowser;
	}

	public String getRemoteUserAllDetails() {
		return remoteUserAllDetails;
	}

	public void setRemoteUserAllDetails(String remoteUserAllDetails) {
		this.remoteUserAllDetails = remoteUserAllDetails;
	}

}
