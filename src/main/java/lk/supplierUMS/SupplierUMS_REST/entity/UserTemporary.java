package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name="m_user_temporary")
public class UserTemporary implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="temp_user_id")
	private long tempUserId;
	
	@Column(name="temp_m_user_id")
	private String useridTemp;
	
	@NotBlank
	@Size(min=3, max = 50)
	@Column(name="temp_m_username")
	private String usernameTemp;
	
	@JsonIgnore
    @Size(min=4, max = 100)
	@Column(name="temp_m_password")
	private String passowrdTemp;
	
//	@Size(max = 50)
	@Column(name="temp_m_email")
	private String emailTemp;
	
//	@Size(max = 10)
	@Column(name="temp_c_code")
	private String companyCodeTemp; // Sub company code -- Naveen
	
//	@Size(max = 10)
	@Column(name="temp_m_sup_c_code")
	private String userCompanyCodeTemp; // company that user belongs to
	
//	@Size(max = 10)
	@Column(name="temp_m_role_name")
	private String userRoleInBussinessTemp; // users role in business whether a company or supplier
	
//	@Size(max = 20)
	@Column(name="temp_m_nic")
	private String nicTemp;
	
//	@Size(max = 10)
	@Column(name="temp_m_status")
	private Integer statusTemp;
	
//	@Size(max = 10)
	@Column(name="temp_m_status_flag")
	private String statusFlagTemp;
	
	@Column(name="temp_m_middlenname")
	private String mmiddlennameTemp;
	
	@Column(name="temp_m_mobileno")
	private String mobileNoTemp;
	
	@Column(name="temp_m_created_datetime")
	private LocalDateTime createdDateTimeTemp;
	
	@Column(name="temp_m_expire_date")
	private LocalDate expireDateTemp;
	
	// added for SupplierUMS Update
	
	@Column(name="temp_m_fcmtoken")
	private String FCMTokenTemp;
	
	@Column(name="temp_m_deptid")
	private Long deptIDTemp;
	
	@Column(name="temp_m_locid")
	private Long locationIDTemp;
	
	@Column(name="temp_m_user_apprvreason")
	private String apprvreasonTemp;
	
	
	@Column(name="temp_m_is_first_login")
	private Boolean isFirstLoginTemp;
	
	@Column(name="temp_m_user_role_id")
	private Long userRoleId;
	
	@Column(name="temp_m_user_created_user")
	private String createdUserTemp;
	
	@Column(name="temp_m_user_created_date_time")
	private LocalDateTime createdDateTime;

	public String getCreatedUserTemp() {
		return createdUserTemp;
	}


	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}


	public void setCreatedUserTemp(String createdUserTemp) {
		this.createdUserTemp = createdUserTemp;
	}


	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}


	public Long getUserRoleId() {
		return userRoleId;
	}


	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}


	public long getTempUserId() {
		return tempUserId;
	}


	public String getUseridTemp() {
		return useridTemp;
	}


	public String getUsernameTemp() {
		return usernameTemp;
	}


	public String getPassowrdTemp() {
		return passowrdTemp;
	}


	public String getEmailTemp() {
		return emailTemp;
	}


	public String getCompanyCodeTemp() {
		return companyCodeTemp;
	}


	public String getUserCompanyCodeTemp() {
		return userCompanyCodeTemp;
	}


	public String getUserRoleInBussinessTemp() {
		return userRoleInBussinessTemp;
	}


	public String getNicTemp() {
		return nicTemp;
	}


	public Integer getStatusTemp() {
		return statusTemp;
	}


	public String getStatusFlagTemp() {
		return statusFlagTemp;
	}


	public String getMmiddlennameTemp() {
		return mmiddlennameTemp;
	}


	public String getMobileNoTemp() {
		return mobileNoTemp;
	}


	public LocalDateTime getCreatedDateTimeTemp() {
		return createdDateTimeTemp;
	}


	public LocalDate getExpireDateTemp() {
		return expireDateTemp;
	}


	public String getFCMTokenTemp() {
		return FCMTokenTemp;
	}


	public Long getDeptIDTemp() {
		return deptIDTemp;
	}


	public Long getLocationIDTemp() {
		return locationIDTemp;
	}


	public String getApprvreasonTemp() {
		return apprvreasonTemp;
	}


	public Boolean getIsFirstLoginTemp() {
		return isFirstLoginTemp;
	}


	public void setTempUserId(long tempUserId) {
		this.tempUserId = tempUserId;
	}


	public void setUseridTemp(String useridTemp) {
		this.useridTemp = useridTemp;
	}


	public void setUsernameTemp(String usernameTemp) {
		this.usernameTemp = usernameTemp;
	}


	public void setPassowrdTemp(String passowrdTemp) {
		this.passowrdTemp = passowrdTemp;
	}


	public void setEmailTemp(String emailTemp) {
		this.emailTemp = emailTemp;
	}


	public void setCompanyCodeTemp(String companyCodeTemp) {
		this.companyCodeTemp = companyCodeTemp;
	}


	public void setUserCompanyCodeTemp(String userCompanyCodeTemp) {
		this.userCompanyCodeTemp = userCompanyCodeTemp;
	}


	public void setUserRoleInBussinessTemp(String userRoleInBussinessTemp) {
		this.userRoleInBussinessTemp = userRoleInBussinessTemp;
	}


	public void setNicTemp(String nicTemp) {
		this.nicTemp = nicTemp;
	}


	public void setStatusTemp(Integer statusTemp) {
		this.statusTemp = statusTemp;
	}


	public void setStatusFlagTemp(String statusFlagTemp) {
		this.statusFlagTemp = statusFlagTemp;
	}


	public void setMmiddlennameTemp(String mmiddlennameTemp) {
		this.mmiddlennameTemp = mmiddlennameTemp;
	}


	public void setMobileNoTemp(String mobileNoTemp) {
		this.mobileNoTemp = mobileNoTemp;
	}


	public void setCreatedDateTimeTemp(LocalDateTime localDateTime) {
		this.createdDateTimeTemp = localDateTime;
	}


	public void setExpireDateTemp(LocalDate expireDateTemp) {
		this.expireDateTemp = expireDateTemp;
	}


	public void setFCMTokenTemp(String fCMTokenTemp) {
		FCMTokenTemp = fCMTokenTemp;
	}


	public void setDeptIDTemp(Long deptIDTemp) {
		this.deptIDTemp = deptIDTemp;
	}


	public void setLocationIDTemp(Long locationIDTemp) {
		this.locationIDTemp = locationIDTemp;
	}


	public void setApprvreasonTemp(String apprvreasonTemp) {
		this.apprvreasonTemp = apprvreasonTemp;
	}


	public void setIsFirstLoginTemp(Boolean isFirstLoginTemp) {
		this.isFirstLoginTemp = isFirstLoginTemp;
	}
	
	
}
