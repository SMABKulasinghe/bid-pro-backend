package lk.supplierUMS.SupplierUMS_REST.JPARepo;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;


import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationSheetCreate;

import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationAvarageMarkSheet;

@Component
public interface EvaluationSheetCreateRepo extends JpaRepository<EvaluationSheetCreate, Long> {

	@Query("SELECT emc,em,user FROM EvaluationSheetCreate emc INNER JOIN EvaluationMarks em ON emc.evaluationmarksheetID = em.evaluationmarksheetID INNER JOIN User user ON em.createdUser = user.companyCode WHERE em.companyCode = ?2 AND em.tenderId = ?1")
	List<Object[]> getEvaluatedMarks(long tenderId, String venderId);
	
	Optional<List<EvaluationSheetCreate>> findAllByTenderId(Long tenderID);
	
	//List<Object[]> getMarkSheetVals(long tenderId);
	@Query("SELECT emc,eam FROM EvaluationSheetCreate emc INNER JOIN EvaluationAvarageMarkSheet eam ON emc.evaluationmarksheetID = eam.evaluationmarksheetID WHERE emc.tenderId = ?1 AND eam.companyCode = ?2")
	List<Object[]> getMarkSheetVals(long tenderId, String venderId);

	@Query("SELECT emc,em,user FROM EvaluationSheetCreate emc INNER JOIN EvaluationMarks em ON emc.evaluationmarksheetID = em.evaluationmarksheetID INNER JOIN User user ON em.createdUser = user.companyCode WHERE em.companyCode = ?2 AND em.tenderId = ?1 AND em.evaluationmarksheetID = ?3 ORDER BY em.createdUser ASC")
	List<Object[]> getEvaluatedMarksForTable(long tenderId, String venderId, Long evaluationmarksheetID);
	
	@Query("SELECT evamarks FROM EvaluationSheetCreate evamarks WHERE evamarks.tenderId = ?2 AND (evamarks.criteriaName LIKE %?1% OR evamarks.criteriamaxName LIKE %?1%)")
	Page<EvaluationSheetCreate> getMarkSheetForVendor(String parameter, Long tenderID, PageRequest of);

	
	@Query("SELECT evesc, td FROM EvaluationSheetCreate evesc INNER JOIN TenderDetails td ON td.trnderid = evesc.tenderId WHERE evesc.createdUser = ?2 AND td.trnderid =?3 AND (td.bidno LIKE %?1% OR td.tendername LIKE %?1% OR evesc.createdUser LIKE %?1% OR evesc.criteriaName LIKE %?1% OR evesc.criteriamaxName LIKE %?1%) ")
	Page<Object[]> getEvaluationSheetDetails(String parameter, String loggedUserID, Long tenderID, PageRequest of); //	GROUP BY td.trnderid

	
	@Query("SELECT evesc, td FROM EvaluationSheetCreate evesc INNER JOIN TenderDetails td ON td.trnderid = evesc.tenderId WHERE evesc.createdUser = ?2 AND td.trnderid =?3 AND (td.bidno LIKE %?1% OR td.tendername LIKE %?1% OR evesc.createdUser LIKE %?1% OR evesc.criteriaName LIKE %?1% OR evesc.criteriamaxName LIKE %?1%) ")
	Page<Object[]> getEditSheet(String parameter, String loggedUserID, Long tendrID, PageRequest of);

	
	Optional<EvaluationSheetCreate> findByTenderIdAndCriteriaName(Long tenderId, String criteriaName);

	@Query("SELECT evsheet,td FROM EvaluationSheetCreate evsheet INNER JOIN TenderDetails td ON td.trnderid = evsheet.tenderId WHERE evsheet.tenderId = ?2 AND (evsheet.criteriaName LIKE %?1% OR evsheet.criteriamaxName LIKE %?1%) ")
	Page<Object[]> getAuthorizeEvaluation(String parameter, Long tendrID, PageRequest of);
	

	//List<Object[]> getMarkSheetVals(long tenderId, String venderId);
}
