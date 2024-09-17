package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="p_glob")
public class GlobalDetails implements Serializable{
	
	@Id
	@Column(name="p_index")
	private String indexNo;
	
	@Column(name="gb_sysdate")
	private Date systemDate;
	
	@Column(name="gb_sysdate_two")
	private Date gbsysdatetwo;
	
	@Column(name="gb_nof_usr")
	private int numberOfUser;
	
	@Column(name="gb_pwd_wxp_yer")
	private int passwordExpYear;
	
	@Column(name="gb_encryp_key")
	private String encryptionKey;
	
	@Column(name="gb_usr_atmpts")
	private int userAttempts;
	
	@Column(name="gb_usr_exp_days")
	private int userExpDays;
	
	@Column(name="gb_company_name")
	private String companyName;
	
	@Column(name="gb_company_adres",length=200)
	private String companyAddres;
	
	@Column(name="gb_domain_name")
	private String domainName;
	
	@Column(name="gb_servr_ipa")
	private String serverIpa;
	
	@Column(name="gb_smsmtp")
	private String smsMtp;
	
	@Column(name="gb_alert_email")
	private String alertMail;
	
	@Column(name="gb_fo_qty_percentge")
	private String foQtyPrecentge;
	
	@Column(name="gb_superuser_roleid")
	private Long gbsuperuserroleid;
	
	@Column(name="gb_companyadmin_roleid")
	private Long gbcompanyadminroleid;
	
	@Column(name="gb_supplieradmin_roleid")
	private Long gbsupplieradminroleid;
	
	@Column(name="gb_onetimadmin_roleid")
	private Long gbonetimadminroleid;
	
	@Column(name="gb_login_url")
	private String loginUrl;
	
	@Column(name="gb_email")
	private String email;
	
	@Column(name="gb_phone_no")
	private String gbphoneno;
	
	
	

	public Long getGbsuperuserroleid() {
		return gbsuperuserroleid;
	}

	public void setGbsuperuserroleid(Long gbsuperuserroleid) {
		this.gbsuperuserroleid = gbsuperuserroleid;
	}

	public Long getGbcompanyadminroleid() {
		return gbcompanyadminroleid;
	}

	public void setGbcompanyadminroleid(Long gbcompanyadminroleid) {
		this.gbcompanyadminroleid = gbcompanyadminroleid;
	}

	public Long getGbsupplieradminroleid() {
		return gbsupplieradminroleid;
	}

	public void setGbsupplieradminroleid(Long gbsupplieradminroleid) {
		this.gbsupplieradminroleid = gbsupplieradminroleid;
	}

	public Long getGbonetimadminroleid() {
		return gbonetimadminroleid;
	}

	public void setGbonetimadminroleid(Long gbonetimadminroleid) {
		this.gbonetimadminroleid = gbonetimadminroleid;
	}

	public String getIndexNo() {
		return indexNo;
	}

	public void setIndexNo(String indexNo) {
		this.indexNo = indexNo;
	}

	public Date getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}

	public int getNumberOfUser() {
		return numberOfUser;
	}

	public void setNumberOfUser(int numberOfUser) {
		this.numberOfUser = numberOfUser;
	}

	public int getPasswordExpYear() {
		return passwordExpYear;
	}

	public void setPasswordExpYear(int passwordExpYear) {
		this.passwordExpYear = passwordExpYear;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	public int getUserAttempts() {
		return userAttempts;
	}

	public void setUserAttempts(int userAttempts) {
		this.userAttempts = userAttempts;
	}

	public int getUserExpDays() {
		return userExpDays;
	}

	public void setUserExpDays(int userExpDays) {
		this.userExpDays = userExpDays;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddres() {
		return companyAddres;
	}

	public void setCompanyAddres(String companyAddres) {
		this.companyAddres = companyAddres;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getServerIpa() {
		return serverIpa;
	}

	public void setServerIpa(String serverIpa) {
		this.serverIpa = serverIpa;
	}

	public String getSmsMtp() {
		return smsMtp;
	}

	public void setSmsMtp(String smsMtp) {
		this.smsMtp = smsMtp;
	}

	public String getAlertMail() {
		return alertMail;
	}

	public void setAlertMail(String alertMail) {
		this.alertMail = alertMail;
	}

	public String getFoQtyPrecentge() {
		return foQtyPrecentge;
	}

	public void setFoQtyPrecentge(String foQtyPrecentge) {
		this.foQtyPrecentge = foQtyPrecentge;
	}

	public Date getGbsysdatetwo() {
		return gbsysdatetwo;
	}

	public void setGbsysdatetwo(Date gbsysdatetwo) {
		this.gbsysdatetwo = gbsysdatetwo;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGbphoneno() {
		return gbphoneno;
	}

	public void setGbphoneno(String gbphoneno) {
		this.gbphoneno = gbphoneno;
	}
	
	
	
	
	
	
	
	
	
}
