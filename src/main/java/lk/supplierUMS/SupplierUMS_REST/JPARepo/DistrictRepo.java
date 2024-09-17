package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.District;

@Component
public interface DistrictRepo extends JpaRepository<District, Long>{

	List<District> findByProvinceCode(long proviceCode);
	
}
