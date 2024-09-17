package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.AssetSubCategory;

@Component
public interface AssetSubCategoryRepo extends JpaRepository<AssetSubCategory, Long>{
	
	List<AssetSubCategory> findByCategoryID(Long categoryID);

	
	
}
