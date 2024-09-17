package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="m_user")
public class User implements Serializable{
	
	
	
	@Id
	@Column(name="m_user_id")
	private String userid;
	
	@NotBlank
	@Size(min=3, max = 50)
	@Column(name="m_username")
	private String username;
	
	@JsonIgnore
    @Size(min=4, max = 100)
	@Column(name="m_password")
	private String passowrd;
	
//	@Size(max = 50)
	@Column(name="m_email")
	private String email;
	
//	@Size(max = 10)
	@Column(name="c_code")
	private String companyCode; // Sub company code -- Naveen
	
//	@Size(max = 10)
	@Column(name="m_sup_c_code")
	private String userCompanyCode; // company that user belongs to
	
//	@Size(max = 10)
	@Column(name="m_role_name")
	private String userRoleInBussiness; // users role in business whether a company or supplier
	
//	@Size(max = 20)
	@Column(name="m_nic")
	private String nic;
	
//	@Size(max = 10)
	@Column(name="m_status")
	private Integer status;
	
//	@Size(max = 10)
	@Column(name="m_status_flag")
	private String statusFlag;
	
	@Column(name="m_middlenname")
	private String mmiddlenname;
	
	@Column(name="m_mobileno")
	private String mobileNo;
	
	@Column(name="m_created_datetime")
	private Date createdDateTime;
	
	@Column(name="m_expire_date")
	private Date expireDate;
	
	// added for SupplierUMS Update
	
	@Column(name="m_fcmtoken")
	private String FCMToken;
	
	@Column(name="m_deptid")
	private Long deptID;
	
	@Column(name="m_locid")
	private Long locationID;
	
	@Column(name="m_user_apprvreason")
	private String apprvreason;
	
	
	@Column(name="m_is_first_login")
	private Boolean isFirstLogin;
	
	@Column(name="m_temp_user_status")
	private Long tempUserStatus;
	
	public Long getTempUserStatus() {
		return tempUserStatus;
	}

	public void setTempUserStatus(Long tempUserStatus) {
		this.tempUserStatus = tempUserStatus;
	}

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "m_user_role_id", nullable = true)
	private UserRole userRoll;
	
	public User(User u) {
		this.userid = u.getUserid();
		this.username = u.getUsername();
		this.passowrd = u.getPassowrd();
		this.email = u.getEmail();
		this.status = u.getStatus();
		this.statusFlag = u.getStatusFlag();
		this.nic = u.getNic();
		this.userRoleInBussiness = u.getUserRoleInBussiness();			
		this.companyCode = u.getCompanyCode();
		this.deptID = u.getDeptID();
		this.locationID = u.getLocationID();
	
	}
	
	public String getMmiddlenname() {
		return mmiddlenname;
	}

	public void setMmiddlenname(String mmiddlenname) {
		this.mmiddlenname = mmiddlenname;
	}

	private User(){
		
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassowrd() {
		return passowrd;
	}

	public void setPassowrd(String passowrd) {
		this.passowrd = passowrd;
	}

	public UserRole getUserRoll() {
		return userRoll;
	}

	public void setUserRoll(UserRole userRoll) {
		this.userRoll = userRoll;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public String getUserCompanyCode() {
		return userCompanyCode;
	}

	public void setUserCompanyCode(String userCompanyCode) {
		this.userCompanyCode = userCompanyCode;
	}

	public String getUserRoleInBussiness() {
		return userRoleInBussiness;
	}

	public void setUserRoleInBussiness(String userRoleInBussiness) {
		this.userRoleInBussiness = userRoleInBussiness;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getFCMToken() {
		return FCMToken;
	}

	public void setFCMToken(String fCMToken) {
		FCMToken = fCMToken;
	}

	public String getApprvreason() {
		return apprvreason;
	}

	public void setApprvreason(String apprvreason) {
		this.apprvreason = apprvreason;
	}

	public Long getDeptID() {
		return deptID;
	}

	public void setDeptID(Long deptID) {
		this.deptID = deptID;
	}

	public Long getLocationID() {
		return locationID;
	}

	public void setLocationID(Long locationID) {
		this.locationID = locationID;
	}

	public Boolean getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(Boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	/*
	 * public void setUserRoll(Long valueOf) { this.userRoll = userRoll;
	 * 
	 * }
	 */
	
}
