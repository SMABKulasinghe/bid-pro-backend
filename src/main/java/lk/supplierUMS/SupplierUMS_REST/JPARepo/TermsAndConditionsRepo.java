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

@Component
public interface TermsAndConditionsRepo extends JpaRepository<TermsAndConditions, Long>{

	@Query("SELECT tc FROM TermsAndConditions tc WHERE tc.status = 3")
	List<TermsAndConditions> getTermsAndConditions();

	Boolean existsByTermsConditions(String termsAndCon);
	
	@Query("SELECT tc,tct FROM TermsAndConditions tc INNER JOIN TermsAndConditionsTrans tct ON tc.termsAndConditionsId = tct.termsConditionsId WHERE tct.tenderId = ?1 AND tct.status =3")
	List<Object[]> getTermsAndConditionsForIssuePoPage(Long tenderId);

	@Query("SELECT tcp,tc FROM TermsAndConditionsFromPo tcp INNER JOIN TermsAndConditions tc ON tc.termsAndConditionsId = tcp.termsConditionsId WHERE tcp.poId = ?1")
	List<Object[]> getTermsAndConView(long id);
	
}
