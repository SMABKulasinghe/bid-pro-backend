package lk.supplierUMS.SupplierUMS_REST.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "m_global_status")
public class GlobalStatus {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="m_global_status_id")
	private long globalStatusID;
	
	@Column(name = "m_global_status_name")
	private String globalStatusName;
	
	@Column(name = "m_global_status_description")
	private String globalStatusDescription;
	
	

	public long getGlobalStatusID() {
		return globalStatusID;
	}

	public String getGlobalStatusName() {
		return globalStatusName;
	}

	public void setGlobalStatusID(long globalStatusID) {
		this.globalStatusID = globalStatusID;
	}

	public void setGlobalStatusName(String globalStatusName) {
		this.globalStatusName = globalStatusName;
	}

	public String getGlobalStatusDescription() {
		return globalStatusDescription;
	}

	public void setGlobalStatusDescription(String globalStatusDescription) {
		this.globalStatusDescription = globalStatusDescription;
	}
	
	
	

}
