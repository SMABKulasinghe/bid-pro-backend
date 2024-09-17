package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategoryProducts;

public interface EligibleSubCategoryProductsRepo extends JpaRepository<EligibleSubCategoryProducts, Long>{

	List<EligibleSubCategoryProducts> findByEligibleSubcatId(long subId);

}
