package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.SupplierProductDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SupplierDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.User;

@Component
public interface SupplierProductDetailsRepo extends JpaRepository<SupplierProductDetails, Long> {
	
	
	@Query("SELECT spd, ecat, esubcat, sd, scom FROM SupplierProductDetails spd "
			+ "INNER JOIN EligibleCategory ecat ON spd.categoryID = ecat.eligibleCategortID "
			+ "INNER JOIN EligibleSubCategory esubcat ON spd.subCategoryID = esubcat.eligibleSubcatId "
			+ "INNER JOIN SupplierDetails sd ON spd.supplierID = sd.subCompanyID "
			+ "INNER JOIN SubCompany scom ON scom.subCompanyID = sd.subCompanyID "
			+ "WHERE scom.scstatus='P' AND spd.supplierID = ?2 "
			+ "AND ( CAST( spd.supplierID as string ) LIKE %?1% OR sd.supplierName LIKE %?1% OR ecat.eligibleCategortName LIKE %?1% OR spd.categoryFee LIKE %?1%) GROUP BY spd.supplierProductMapID "
			+"")
	Page<Object[]> categorySuppliersForSupplierID(String parameter, Long supplierID, PageRequest of);

	@Query("SELECT spd, ecat, esubcat, sd, scom FROM SupplierProductDetails spd "
			+ "INNER JOIN EligibleCategory ecat ON spd.categoryID = ecat.eligibleCategortID "
			+ "INNER JOIN EligibleSubCategory esubcat ON spd.subCategoryID = esubcat.eligibleSubcatId "
			+ "INNER JOIN SupplierDetails sd ON spd.supplierID = sd.subCompanyID "
			+ "INNER JOIN SubCompany scom ON scom.subCompanyID = sd.subCompanyID "
			+ "WHERE scom.scstatus='P' AND spd.supplierID = ?2 "
			+ "AND ( CAST( spd.supplierID as string ) LIKE %?1% OR sd.supplierName LIKE %?1% OR ecat.eligibleCategortName LIKE %?1% OR spd.categoryFee LIKE %?1%) GROUP BY spd.supplierProductMapID "
			+"")
	Page<Object[]> viewCategorySuppliersForSupplierID(String parameter, Long supplierID, PageRequest of);

	
	@Query("SELECT spd, ecat, esubcat, sd, scom FROM SupplierProductDetails spd "
			+ "INNER JOIN EligibleCategory ecat ON spd.categoryID = ecat.eligibleCategortID "
			+ "INNER JOIN EligibleSubCategory esubcat ON spd.subCategoryID = esubcat.eligibleSubcatId "
			+ "INNER JOIN SupplierDetails sd ON spd.supplierID = sd.subCompanyID "
			+ "INNER JOIN SubCompany scom ON scom.subCompanyID = sd.subCompanyID "
			+ "WHERE scom.scstatus='P' AND spd.supplierID = ?2 "
			+ "AND ( CAST( spd.supplierID as string ) LIKE %?1% OR sd.supplierName LIKE %?1% OR ecat.eligibleCategortName LIKE %?1% OR spd.categoryFee LIKE %?1%) GROUP BY spd.supplierProductMapID "
			+"")
	Page<Object[]> companyViewCategoryForCompanyID(String parameter, Long companyID, PageRequest of);

	
	@Query("SELECT spd, ecat, sd, scom FROM SupplierProductDetails spd "
			+ "INNER JOIN EligibleCategory ecat ON spd.categoryID = ecat.eligibleCategortID "
			+ "INNER JOIN SupplierDetails sd ON spd.supplierID = sd.subCompanyID "
			+ "INNER JOIN SubCompany scom ON scom.subCompanyID = sd.subCompanyID "
			+ "WHERE scom.scstatus='P' AND spd.supplierID = ?2 "
			+ "AND ( CAST( spd.supplierID as string ) LIKE %?1% OR sd.supplierName LIKE %?1% OR ecat.eligibleCategortName LIKE %?1% OR spd.categoryFee LIKE %?1%) GROUP BY spd.supplierProductMapID "
			+"")
	Page<Object[]> selectAdditionalCategoryForCompanyID(String parameter, Long companyID, PageRequest of);

	
	
	@Query("SELECT spd, ecat, esubcat, sd, scom FROM SupplierProductDetails spd "
			+ "INNER JOIN EligibleCategory ecat ON spd.categoryID = ecat.eligibleCategortID "
			+ "INNER JOIN EligibleSubCategory esubcat ON spd.subCategoryID = esubcat.eligibleSubcatId "
			+ "INNER JOIN SupplierDetails sd ON spd.supplierID = sd.subCompanyID "
			+ "INNER JOIN SubCompany scom ON scom.subCompanyID = sd.subCompanyID "
			+ "WHERE scom.scstatus='P' AND spd.supplierID = ?2 "
			+ "AND ( CAST( spd.supplierID as string ) LIKE %?1% OR sd.supplierName LIKE %?1% OR ecat.eligibleCategortName LIKE %?1% OR spd.categoryFee LIKE %?1%) GROUP BY spd.supplierProductMapID "
			+"")
	Page<Object[]> blockCategorySuppliersForSupplierID(String parameter, Long supplierID, PageRequest of);

	
	@Query("SELECT spd, ecat, esubcat, sd, scom FROM SupplierProductDetails spd "
			+ "INNER JOIN EligibleCategory ecat ON spd.categoryID = ecat.eligibleCategortID "
			+ "INNER JOIN EligibleSubCategory esubcat ON spd.subCategoryID = esubcat.eligibleSubcatId "
			+ "INNER JOIN SupplierDetails sd ON spd.supplierID = sd.subCompanyID "
			+ "INNER JOIN SubCompany scom ON scom.subCompanyID = sd.subCompanyID "
			+ "WHERE scom.scstatus='P' AND spd.supplierID = ?2 "
			+ "AND ( CAST( spd.supplierID as string ) LIKE %?1% OR sd.supplierName LIKE %?1% OR ecat.eligibleCategortName LIKE %?1% OR spd.categoryFee LIKE %?1%) GROUP BY spd.supplierProductMapID "
			+"")
	Page<Object[]> viewBlockCategorySuppliersForSupplierID(String parameter, Long supplierID, PageRequest of);


	@Query("SELECT spd, ecat, esubcat, sd, scom FROM SupplierProductDetails spd "
			+ "INNER JOIN EligibleCategory ecat ON spd.categoryID = ecat.eligibleCategortID "
			+ "INNER JOIN EligibleSubCategory esubcat ON spd.subCategoryID = esubcat.eligibleSubcatId "
			+ "INNER JOIN SupplierDetails sd ON spd.supplierID = sd.subCompanyID "
			+ "INNER JOIN SubCompany scom ON scom.subCompanyID = sd.subCompanyID "
			+ "WHERE scom.scstatus='Y' AND spd.supplierID = ?1 "
			+"")
	List<Object[]> getDetailsBySupplierID(Long supplierID);

}

