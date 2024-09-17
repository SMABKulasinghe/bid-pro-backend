package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="m_rating")
public class Rating implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "m_rating_id")
	private long ratingId;
	
	@Column(name = "m_tag_mapping_Id")
	private long tagMappingId;
	
	@Column(name = "m_rating_value")
	private Double ratingValue;
	
	@Column(name = "m_user_Id")
	private String userID;

	public long getRatingId() {
		return ratingId;
	}

	public void setRatingId(long ratingId) {
		this.ratingId = ratingId;
	}

	public long getTagMappingId() {
		return tagMappingId;
	}

	public void setTagMappingId(long tagMappingId) {
		this.tagMappingId = tagMappingId;
	}

	public Double getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(Double ratingValue) {
		this.ratingValue = ratingValue;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	



}
