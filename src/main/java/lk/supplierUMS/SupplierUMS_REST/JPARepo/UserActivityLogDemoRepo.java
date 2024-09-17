package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import lk.supplierUMS.SupplierUMS_REST.entity.UserActivityLog;

@Component
@Repository
public interface UserActivityLogDemoRepo extends JpaRepository<UserActivityLog, Long>{
	
	@Query(" SELECT al FROM UserActivityLog al where al.actUserId = ?1 AND al.createdAt between ?2 AND ?3 AND al.actOption = ?4 AND (al.actOption LIKE %?5% OR"
			+ " al.actUserBelongCompany LIKE %?5% OR al.actOption LIKE %?5% OR actUserId LIKE %?5% OR actDescription LIKE %?5% OR remoteUserAllDetails LIKE %?5%) ")
	Page<UserActivityLog> findActivtyLog(String userID, Date fromDate, Date toDate, String action, String search, Pageable page);
	
	@Query(" SELECT al FROM UserActivityLog al where al.actUserId = ?1 AND al.createdAt between ?2 AND ?3 AND (al.actOption LIKE %?4% OR"
			+ " al.actUserBelongCompany LIKE %?4% OR al.actOption LIKE %?4% OR actUserId LIKE %?4% OR actDescription LIKE %?4% OR remoteUserAllDetails LIKE %?4%) ")
	Page<UserActivityLog> findAllActivtyLog(String userID, Date fromDate, Date toDate, String search, Pageable page);
	
	@Query(" SELECT al FROM UserActivityLog al where al.actOption = ?1 AND al.createdAt between ?2 AND ?3 AND (al.actOption LIKE %?4% OR"
			+ " al.actUserBelongCompany LIKE %?4% OR al.actOption LIKE %?4% OR actUserId LIKE %?4% OR actDescription LIKE %?4% OR remoteUserAllDetails LIKE %?4%) ")
	Page<UserActivityLog> findAllActivtyLogByUsers(String action, Date fromDate, Date toDate, String search, Pageable page);
	
	@Query(" SELECT al FROM UserActivityLog al where al.createdAt between ?1 AND ?2 AND (al.actOption LIKE %?3% OR"
			+ " al.actUserBelongCompany LIKE %?3% OR al.actOption LIKE %?3% OR actUserId LIKE %?3% OR actDescription LIKE %?3% OR remoteUserAllDetails LIKE %?3%) ")
	Page<UserActivityLog> findAllActivtyLogByAllUsers(Date fromDate, Date toDate, String search, Pageable page);
	
	@Query(" SELECT al FROM UserActivityLog al where al.actOption = ?1 AND al.createdAt between ?2 AND ?3 AND (al.actOption LIKE %?4% OR"
			+ " al.actUserBelongCompany LIKE %?4% OR al.actOption LIKE %?4% OR actUserId LIKE %?4% OR actDescription LIKE %?4% OR remoteUserAllDetails LIKE %?4%) ")
	Page<UserActivityLog> findGivenActivtyLogByAllUsers(String action, Date fromDate, Date toDate, String search, Pageable page);
	

}
