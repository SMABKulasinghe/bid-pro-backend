package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.LocationDetails;

@Component
public interface LocationDetailsRepo extends JpaRepository<LocationDetails, Long>{
	
	LocationDetails findByLocationcode(String locationid);
	
}
