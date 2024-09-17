package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


	@Entity
	@Table(name="m_company_details")
	public class CompanyDetails implements Serializable{
		
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name="company_id")
		private long companyID;
		
		@Column(name="c_company_code")
		private String ccompanycode;
		
		@Column(name="c_name")
		private String cname;
		
		@Column(name="c_type")
		private String ctype;
		
		@Column(name="c_registration_no")
		private String cregistrationno;
		
		@Column(name="c_reg_date")
		private Date cregdate;
		
		@Column(name="c_address_1")
		private String caddress1;

		@Column(name="c_address_2")
		private String caddress2;
		
		@Column(name="c_address_3")
		private String caddress3;
		
		@Column(name="c_phone_no1")
		private String cphoneno1;
		
		@Column(name="c_phone_no2")
		private String cphoneno2;
		
		@Column(name="c_phone_no3")
		private String cphoneno3;
		
		@Column(name="c_city")
		private String ccity;
		
		@Column(name="c_district")
		private String cdistrict;
		
		@Column(name="c_email_admin")
		private String cemailadmin;
		
		@Column(name="c_email_business_head")
		private String cemailbusinesshead;
		
		@Column(name="c_email_line_manager")
		private String cemaillinemanager;
		
		@Column(name="c_system_registered_date")
		private Date csystemregistereddate;

		@Column(name="c_comapany_logo")
		private String ccomapanylogo;
		
		@Column(name="c_comapany_reg_form")
		private String ccomapanyregform;
		
		@Column(name="ccomapany_adress_proof")
		private String ccomapanyadressprofe;
		
		@Column(name="c_comapany_prefix")
		private String ccomapanyprefix;
		
		@Column(name="c_created_id")
		private String ccreatedid;
		
		@Column(name="c_approved_id")
		private String capprovedid;
		
		@Column(name="c_status")
		private String cstatus;
		
		@Column(name="c_bank_name")
		private String cbankname;
		
		@Column(name="c_bank_account_no")
		private String cbankaccountno;
		
		@Column(name="c_bank_branch_code")
		private String cbankbranchcode;
		
		@Column(name="c_bank_branch_name")
		private String cbankbranchname;
		
		@Column(name="province")
		private String province;
		
		@Column(name="district")
		private Long district;
		
		@Column(name="city")
		private Long city;
		
		@Column(name="status")
		private String status;

		public long getCompanyID() {
			return companyID;
		}

		public void setCompanyID(long companyID) {
			this.companyID = companyID;
		}

		public String getCcompanycode() {
			return ccompanycode;
		}

		public void setCcompanycode(String ccompanycode) {
			this.ccompanycode = ccompanycode;
		}

		public String getCname() {
			return cname;
		}

		public void setCname(String cname) {
			this.cname = cname;
		}

		
		public String getCtype() {
			return ctype;
		}

		public void setCtype(String ctype) {
			this.ctype = ctype;
		}

		public String getCregistrationno() {
			return cregistrationno;
		}

		public void setCregistrationno(String cregistrationno) {
			this.cregistrationno = cregistrationno;
		}

		

		public Date getCregdate() {
			return cregdate;
		}

		public void setCregdate(Date cregdate) {
			this.cregdate = cregdate;
		}

		public String getCaddress1() {
			return caddress1;
		}

		public void setCaddress1(String caddress1) {
			this.caddress1 = caddress1;
		}

		public String getCaddress2() {
			return caddress2;
		}

		public void setCaddress2(String caddress2) {
			this.caddress2 = caddress2;
		}

		public String getCaddress3() {
			return caddress3;
		}

		public void setCaddress3(String caddress3) {
			this.caddress3 = caddress3;
		}

		public String getCphoneno1() {
			return cphoneno1;
		}

		public void setCphoneno1(String cphoneno1) {
			this.cphoneno1 = cphoneno1;
		}

		public String getCphoneno2() {
			return cphoneno2;
		}

		public void setCphoneno2(String cphoneno2) {
			this.cphoneno2 = cphoneno2;
		}

		public String getCphoneno3() {
			return cphoneno3;
		}

		public void setCphoneno3(String cphoneno3) {
			this.cphoneno3 = cphoneno3;
		}

		public String getCcity() {
			return ccity;
		}

		public void setCcity(String ccity) {
			this.ccity = ccity;
		}

		public String getCdistrict() {
			return cdistrict;
		}

		public void setCdistrict(String cdistrict) {
			this.cdistrict = cdistrict;
		}

		public String getCemailadmin() {
			return cemailadmin;
		}

		public void setCemailadmin(String cemailadmin) {
			this.cemailadmin = cemailadmin;
		}

		public String getCemailbusinesshead() {
			return cemailbusinesshead;
		}

		public void setCemailbusinesshead(String cemailbusinesshead) {
			this.cemailbusinesshead = cemailbusinesshead;
		}

		public String getCemaillinemanager() {
			return cemaillinemanager;
		}

		public void setCemaillinemanager(String cemaillinemanager) {
			this.cemaillinemanager = cemaillinemanager;
		}

		public Date getCsystemregistereddate() {
			return csystemregistereddate;
		}

		public void setCsystemregistereddate(Date csystemregistereddate) {
			this.csystemregistereddate = csystemregistereddate;
		}

		public String getCcomapanylogo() {
			return ccomapanylogo;
		}

		public void setCcomapanylogo(String ccomapanylogo) {
			this.ccomapanylogo = ccomapanylogo;
		}
		
		

		public String getCcomapanyregform() {
			return ccomapanyregform;
		}

		public void setCcomapanyregform(String ccomapanyregform) {
			this.ccomapanyregform = ccomapanyregform;
		}

		public String getCcomapanyadressprofe() {
			return ccomapanyadressprofe;
		}

		public void setCcomapanyadressprofe(String ccomapanyadressprofe) {
			this.ccomapanyadressprofe = ccomapanyadressprofe;
		}

		public String getCcomapanyprefix() {
			return ccomapanyprefix;
		}

		public void setCcomapanyprefix(String ccomapanyprefix) {
			this.ccomapanyprefix = ccomapanyprefix;
		}

		public String getCcreatedid() {
			return ccreatedid;
		}

		public void setCcreatedid(String ccreatedid) {
			this.ccreatedid = ccreatedid;
		}

		public String getCapprovedid() {
			return capprovedid;
		}

		public void setCapprovedid(String capprovedid) {
			this.capprovedid = capprovedid;
		}

		public String getCstatus() {
			return cstatus;
		}

		public void setCstatus(String cstatus) {
			this.cstatus = cstatus;
		}

		

		public String getCbankname() {
			return cbankname;
		}

		public void setCbankname(String cbankname) {
			this.cbankname = cbankname;
		}

		public String getCbankaccountno() {
			return cbankaccountno;
		}

		public void setCbankaccountno(String cbankaccountno) {
			this.cbankaccountno = cbankaccountno;
		}

		public String getCbankbranchcode() {
			return cbankbranchcode;
		}

		public void setCbankbranchcode(String cbankbranchcode) {
			this.cbankbranchcode = cbankbranchcode;
		}

		public String getCbankbranchname() {
			return cbankbranchname;
		}

		public void setCbankbranchname(String cbankbranchname) {
			this.cbankbranchname = cbankbranchname;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public Long getDistrict() {
			return district;
		}

		public void setDistrict(Long string) {
			this.district = string;
		}

		public Long getCity() {
			return city;
		}

		public void setCity(Long string) {
			this.city = string;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
		
		
		

}
