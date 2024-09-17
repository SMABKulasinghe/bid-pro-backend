package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;

@Component
public interface SubCompanyRepo extends JpaRepository<SubCompany, Long> {
	
//	SubCompany findByCcompanycode(String companyCode);

	List<SubCompany> findByScnameLikeAndIsPrivateFalse(String search,Pageable page);
	
	List<SubCompany> findByScnameLike(String search,Pageable page);
	
	@Query("SELECT sc FROM SubCompany sc INNER JOIN CompanySupplierMapping csm ON sc.subCompanyID = csm.supplierID WHERE csm.status IN ('A') AND csm.companyID = ?2 AND (sc.scname LIKE %?1% )")
	Page<SubCompany> getMappedSuppliers(String search, long companyID, Pageable page);
	
	Long countByScnameLikeAndIsPrivateFalse(String search);
	
	@Query("SELECT sc, pro, dis, cit, sd FROM SubCompany sc INNER JOIN Province pro ON sc.scprovince = pro.id INNER JOIN District dis ON sc.scdistrict = dis.id INNER JOIN City cit ON sc.sccity = cit.id INNER JOIN SupplierDetails sd ON sc.subCompanyID = sd.subCompanyID WHERE sc.scstatus IN ('P', 'RW') AND sc.identity='0'  AND (sc.scompanycode LIKE %?1% OR sc.scname LIKE %?1% OR sc.sccategoryType LIKE %?1% OR sc.scregistrationno LIKE %?1% OR sc.scsystemregistereddate LIKE %?1% OR sc.scdistrict LIKE %?1% OR sc.sccity LIKE %?1%)")
	Page<Object[]> getCompanyForAutherization(String parameter, String userid, PageRequest of);
	
	@Query("SELECT sc FROM SubCompany sc WHERE sc.scstatus='P' AND sc.identity='1'  AND (sc.scompanycode LIKE %?1% OR sc.scname LIKE %?1% OR sc.scregistrationno LIKE %?1% OR sc.scemailadmin LIKE %?1% OR sc.scaddress1 LIKE %?1%)")
	Page<SubCompany> getCompanyForAutherizationD(String parameter, String userid, PageRequest of);
	
	SubCompany findBySubCompanyIDAndIdentityFalse(Long subCompanyID);
	
	@Query("SELECT sc FROM SubCompany sc INNER JOIN User m ON  m.companyCode =sc.subCompanyID WHERE sc.subCompanyID= ?1 ")
	SubCompany getSubCompanyIdentity(Long parameter);
	
	@Query("SELECT sc FROM SubCompany sc WHERE NOT sc.subCompanyID = ?1 AND identity=?2 AND (sc.scname LIKE %?3% OR sc.scphoneno1 LIKE %?3% OR sc.scemailadmin LIKE %?3% OR sc.scsystemregistereddate LIKE %?3%)")
	Page<SubCompany> getAllCompanies(long superuserCompany,boolean identity, String search,Pageable page);
	
	long countByIdentityAndSubCompanyIDNot(boolean type,long companyId);

	@Query("SELECT sc FROM SubCompany sc WHERE sc.subCompanyID = ?1")
	Optional<SubCompany> getEmailsForSubCompany(Long ccode);
	
	@Query("SELECT sc FROM SubCompany sc WHERE sc.subCompanyID = ?1")
	Optional<SubCompany> getCompanyDetails(Long companyCode);

	@Query("SELECT sc, pro, dis, cit, sd FROM SubCompany sc INNER JOIN Province pro ON sc.scprovince = pro.id INNER JOIN District dis ON sc.scdistrict = dis.id INNER JOIN City cit ON sc.sccity = cit.id INNER JOIN SupplierDetails sd ON sc.subCompanyID = sd.subCompanyID AND sc.identity='0'  AND (sc.scompanycode LIKE %?1% OR sc.scname LIKE %?1% OR sc.sccategoryType LIKE %?1% OR sc.scregistrationno LIKE %?1% OR sc.scsystemregistereddate LIKE %?1% OR sc.scdistrict LIKE %?1% OR sc.sccity LIKE %?1%)")
	Page<Object[]> getSuppliersView(String parameter, String userid, PageRequest of);
	
	
	@Query("SELECT scom, pro, dis, cit, sd FROM SubCompany scom "
			+ "INNER JOIN Province pro ON scom.scprovince = pro.id "
			+ "INNER JOIN District dis ON scom.scdistrict = dis.id "
			+ "INNER JOIN City cit ON scom.sccity = cit.id "
			+ "INNER JOIN SupplierDetails sd ON scom.subCompanyID = sd.subCompanyID "
			+ "WHERE scom.scstatus='P' AND scom.subCompanyID = ?1 "
			+"")
	List<Object[]> getCompanyProfileDetailsForRelatedSupplierName(Long companyID);
	
	@Query("SELECT sc, pro, dis, cit, sd FROM SubCompany sc INNER JOIN Province pro ON sc.scprovince = pro.id INNER JOIN District dis ON sc.scdistrict = dis.id INNER JOIN City cit ON sc.sccity = cit.id INNER JOIN SupplierDetails sd ON sc.subCompanyID = sd.subCompanyID WHERE sc.scstatus='Y' AND sc.identity='0'  AND (sc.scompanycode LIKE %?1% OR sc.scname LIKE %?1% OR sc.sccategoryType LIKE %?1% OR sc.scregistrationno LIKE %?1% OR sc.scsystemregistereddate LIKE %?1% OR sc.scdistrict LIKE %?1% OR sc.sccity LIKE %?1%)")
	Page<Object[]> getSuppliersBlock(String parameter, String userid, PageRequest of);

	@Query("SELECT sc, pro, dis, cit, sd FROM SubCompany sc INNER JOIN Province pro ON sc.scprovince = pro.id INNER JOIN District dis ON sc.scdistrict = dis.id INNER JOIN City cit ON sc.sccity = cit.id INNER JOIN SupplierDetails sd ON sc.subCompanyID = sd.subCompanyID WHERE sc.scstatus='B' AND sc.identity='0'  AND (sc.scompanycode LIKE %?1% OR sc.scname LIKE %?1% OR sc.sccategoryType LIKE %?1% OR sc.scregistrationno LIKE %?1% OR sc.scsystemregistereddate LIKE %?1% OR sc.scdistrict LIKE %?1% OR sc.sccity LIKE %?1%)")
	Page<Object[]> viewSuppliersBlock(String parameter, String userid, PageRequest of);

	Boolean existsByScregistrationno(String scregistrationno);

	Boolean existsBySccontactno(String sccontactno);

	Boolean existsByScemail(String scemail);
	
	

	
	
}
