package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.CompanySupplierMapping;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;

@Component
public interface CompanySupplierMappingRepo extends JpaRepository<CompanySupplierMapping, Long> {

	Optional<CompanySupplierMapping> findTopByCompanyIDAndSupplierID(long company, long supplier);
	
	List<CompanySupplierMapping> findBySupplierIDAndStatus(long supplier, String status);
	
	@Query("SELECT map,com FROM CompanySupplierMapping map INNER JOIN SubCompany com ON map.supplierID = com.subCompanyID WHERE map.companyID = ?1 AND (com.scompanycode Like %?2% OR com.scname Like %?2% OR com.scregistrationno Like %?2% OR com.scemailadmin Like %?2% )")
	Page<Object[]> getAllSuppliersForCompanyView(long companyId, String search, Pageable page);
	
	@Query("SELECT map,com FROM CompanySupplierMapping map INNER JOIN SubCompany com ON map.companyID = com.subCompanyID WHERE map.supplierID = ?3 AND map.status = ?1 AND com.scname Like %?2% ")
	Page<Object[]> findByNamePartnershipSuppliers(String status,String search,long supplierId , Pageable page);
	
// **************** kanishka 2019/12/23 **** dashboard **** start **********
	@Query("SELECT COUNT(map) FROM CompanySupplierMapping map  WHERE map.supplierID=?1")
	long getCompanyCountOfSupplier(long supplierId);
	
	@Query("SELECT COUNT(map) FROM CompanySupplierMapping map  WHERE map.companyID=?1")
	long getSupplierCountOfCompany(long supplierId);
	
	@Query("SELECT subc,map FROM  CompanySupplierMapping map JOIN SubCompany subc ON map.companyID = subc.subCompanyID  WHERE map.supplierID=?2 AND (subc.scname LIKE %?1%)")
	Page<Object[]> getSuppliersAllCompanyes(String search,long supplierId , Pageable page);
	
	@Query("SELECT subc,map FROM  CompanySupplierMapping map JOIN SubCompany subc ON map.supplierID = subc.subCompanyID  WHERE map.companyID=?2 AND (subc.scname LIKE %?1%)")
	Page<Object[]> getCompanyesAllSuppliers(String search,long CompanyId , Pageable page);
// ************* dashboard end *********************************************
} 