package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.RFP;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TermsAndConditions;
import lk.supplierUMS.SupplierUMS_REST.entity.TermsAndConditionsFromPo;
import lk.supplierUMS.SupplierUMS_REST.entity.TermsAndConditionsTrans;

@Component
public interface TermsAndConditionsFromPoRepo extends JpaRepository<TermsAndConditionsFromPo, Long>{

	
	
}
