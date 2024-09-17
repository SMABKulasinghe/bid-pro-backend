package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m_divisional_secretariat")
public class City implements Serializable{

	
	@Id
	@Column(name="id")
	protected long id;
	
	@Column(name="version")
	protected long version;
	
	@Column(name="district_id")
	protected long districtCode;
	
	@Column(name="divisional_secretariat_code")
	protected long dsCode;
	
	@Column(name="name_english")
	protected String englishName;
	
	@Column(name="name_sinhala")
	protected String sinhalaName;
	
	@Column(name="name_tamil")
	protected String tamilName;
	
	@Column(name="lifecode")
	protected String lifeCode;
	
	@Column(name="approved")
	protected String approved;
	
	@Column(name="last_modified")
	protected LocalDateTime lastModified;

	public long getId() {
		return id;
	}

	public long getVersion() {
		return version;
	}

	public long getDistrictCode() {
		return districtCode;
	}

	public long getDsCode() {
		return dsCode;
	}

	public String getEnglishName() {
		return englishName;
	}

	public String getSinhalaName() {
		return sinhalaName;
	}

	public String getTamilName() {
		return tamilName;
	}

	public String getLifeCode() {
		return lifeCode;
	}

	public String getApproved() {
		return approved;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}
	
	
}
