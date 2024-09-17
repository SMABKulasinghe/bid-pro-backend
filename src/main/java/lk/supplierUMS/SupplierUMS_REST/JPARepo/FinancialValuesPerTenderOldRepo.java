package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialValuesPerTender;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialValuesPerTenderOld;


@Component
public interface FinancialValuesPerTenderOldRepo extends JpaRepository<FinancialValuesPerTenderOld,Long>{

	
}

