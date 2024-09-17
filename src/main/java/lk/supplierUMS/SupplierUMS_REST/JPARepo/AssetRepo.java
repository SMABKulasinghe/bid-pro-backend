package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.Asset;

@Component
public interface AssetRepo extends JpaRepository<Asset, Long>{

	@Query("SELECT ast FROM Asset ast WHERE ast.subCompanyID = ?2 AND ast.status = 'P' AND (ast.supplierName LIKE %?1%)")
	Page<Object> getAssetDetails(String search, Long userCompanyID, Pageable page);
	
	
	@Query("SELECT ast FROM Asset ast WHERE ast.subCompanyID = ?2 AND ast.status = 'P' AND ast.category = ?3 AND ast.subCategory = ?4 AND (ast.supplierName LIKE %?1%)")
	Page<Object> getAssetDetailsForCatWithSubCat(String search, Long userCompanyID, Long category, Long subCategory, Pageable page);
	
	
}
