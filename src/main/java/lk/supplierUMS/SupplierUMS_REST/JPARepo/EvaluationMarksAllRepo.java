package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationSheetCreate;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationAvarageMarkSheet;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationMarksAll;

@Component
public interface EvaluationMarksAllRepo extends JpaRepository<EvaluationMarksAll, Long> {

	/*
	 * @Query("SELECT td,ema,user from TenderDetails td INNER JOIN EvaluationMarksAll ema ON td.trnderid = ema.tenderId INNER JOIN User user ON user.companyCode = ema.CompanyCode WHERE ema.tenderId =?1 ORDER BY ema.aveMarks DESC"
	 * ) List<Object[]> getEvaluationAllMarks(Long id);
	 */
	
	@Query("SELECT td,ema,sc from TenderDetails td INNER JOIN EvaluationMarksAll ema ON td.trnderid = ema.tenderId INNER JOIN SubCompany sc ON sc.subCompanyID = ema.CompanyCode WHERE ema.tenderId =?2 AND (sc.scname LIKE %?1% OR ema.aveMarks LIKE %?1%) ORDER BY ema.aveMarks DESC")
	Page<Object[]> getEvaluationAllMarks(String parameter, Long id, PageRequest of);

	
	@Query("SELECT eam FROM EvaluationMarksAll eam WHERE eam.tenderId = ?1")
	List<EvaluationMarksAll> submitEvaluationAllMarks(Long tenderId);

	@Query("SELECT eam FROM EvaluationMarksAll eam WHERE eam.tenderId = ?1")
	List<EvaluationMarksAll> submitEvaluationAllMarksAfterSubmit(Long tenderId);

	//@Query("SELECT user FROM User user WHERE user.companyCode = ?1")
	//Optional<User> getUserDetails(String ccodeString);

	@Query("SELECT td FROM TenderDetails td WHERE td.trnderid = ?1")
	Optional<TenderDetails> getTenderDetail(Long tenderId);

	@Query("SELECT sc FROM SubCompany sc WHERE sc.subCompanyID = ?1")
	Optional<SubCompany> getSubCompany(Long ccodeString);

	//@Query("SELECT eam FROM TenderSubmissions ts WHERE ts.tenderNo = ?1")
	//List<TenderSubmissions> tenderSubmissionDetailsForDesqulified(Long tenderId);

	
	

	
	//List<Object[]> getEvaluationAllMarks(Long id);
	
}
