package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.CompanyDetails;


@Component
public interface CompanyDetailsRepo extends JpaRepository<CompanyDetails, Long> {
	
	CompanyDetails findByCcompanycode(String companyid);
	
	@Query("SELECT com FROM CompanyDetails com WHERE  com.ccompanycode LIKE %?1% and  com.status='P' ")
	Page<CompanyDetails> getCompanyDetails(String search, Pageable page);

	@Query("SELECT com FROM CompanyDetails com WHERE com.status='P'AND (com.ccompanycode LIKE %?1% OR com.cname LIKE %?1% OR com.cregistrationno LIKE %?1% OR com.cemailadmin LIKE %?1% OR com.cphoneno1 LIKE %?1% )")
	Page<CompanyDetails> getPendingAuthCompanyDetails(String parameter, PageRequest of);

}
