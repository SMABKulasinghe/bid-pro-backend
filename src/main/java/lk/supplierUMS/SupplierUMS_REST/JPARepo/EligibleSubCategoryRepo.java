package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategory;

@Component
public interface EligibleSubCategoryRepo extends JpaRepository<EligibleSubCategory, Long>{

	List<EligibleSubCategory> findByEligibleCategortID(long id);

}
