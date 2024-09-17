package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationSheetCreate;

@Component
public interface EligibleCategoryRepo extends JpaRepository<EligibleCategory, Long>{
	

	Optional<List<EligibleCategory>> findAllByEligibleCategortNameAndEligibleCategortCode(String cateName, String code);

	
	@Query("SELECT ec FROM EligibleCategory ec WHERE ec.status = '3' OR ec.status = '4' AND (ec.eligibleCategortCode LIKE %?1% OR ec.eligibleCategortName LIKE %?1% OR ec.eligibleCategortDes LIKE %?1% OR ec.eligibleCategoryFee LIKE %?1%)")
	Page<EligibleCategory> getCategoryView(String parameter, PageRequest of);


	Boolean existsByEligibleCategortNameAndStatus(String enteredValue, Long valueOf);


	Boolean existsByEligibleCategortDesAndStatus(String enteredValue, Long valueOf);


	Boolean existsByEligibleCategortCodeAndStatus(String enteredValue, Long valueOf);


	Boolean existsByEligibleCategoryFeeAndStatus(String enteredValue, Long valueOf);
	
	@Query("SELECT ecat FROM EligibleCategory ecat WHERE ecat.eligibleCategortID NOT IN (SELECT ecat.eligibleCategortID FROM SupplierProductDetails spm INNER JOIN EligibleCategory ecat ON spm.categoryID = ecat.eligibleCategortID WHERE spm.supplierID = ?1 )")
	List<EligibleCategory> getCategoryForRegistration(Long companyID);
	
	
}