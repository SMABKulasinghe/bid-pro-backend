package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_evaluation_avarage_marks")
public class EvaluationAvarageMarkSheet implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="t_evaluation_marks_avg_marks_id")
	private Long evaluationMarksAvgID;
	
	@Column(name="m_evaluation_marksheet_id")
	private Long evaluationmarksheetID;
	
	@Column(name="tender_id")
	private Long tenderId;
	
	@Column(name="c_code")
	private String companyCode; // supplierCode
	
	@Column(name="avarage_marks")
	private Double avarageMarks;
	
	@Column(name="total_marks")
	private Double totalMarks;
	
	@Column(name="evaluators_count")
	private Long evaluatorsCount;

	public Long getEvaluationMarksAvgID() {
		return evaluationMarksAvgID;
	}

	public Long getEvaluationmarksheetID() {
		return evaluationmarksheetID;
	}

	public Long getTenderId() {
		return tenderId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public Double getAvarageMarks() {
		return avarageMarks;
	}

	public Double getTotalMarks() {
		return totalMarks;
	}

	public Long getEvaluatorsCount() {
		return evaluatorsCount;
	}

	public void setEvaluationMarksAvgID(Long evaluationMarksAvgID) {
		this.evaluationMarksAvgID = evaluationMarksAvgID;
	}

	public void setEvaluationmarksheetID(Long evaluationmarksheetID) {
		this.evaluationmarksheetID = evaluationmarksheetID;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setAvarageMarks(Double avarageMarks) {
		this.avarageMarks = avarageMarks;
	}

	public void setTotalMarks(Double totalMarks) {
		this.totalMarks = totalMarks;
	}

	public void setEvaluatorsCount(Long evaluatorsCount) {
		this.evaluatorsCount = evaluatorsCount;
	}
	
	

}
