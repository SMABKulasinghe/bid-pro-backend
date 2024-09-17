package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.City;

@Component
public interface CityRepo extends JpaRepository<City, Long>{

	List<City> findByDistrictCode(long DistrictId);
	
}
