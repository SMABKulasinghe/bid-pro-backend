package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;

@Component
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {
	@Query("SELECT max(userRoleID) FROM UserRole")
	long getMaxId();

	Boolean existsByUserRoleName(String enteredValue);
	
	UserRole findByUserRoleName(String roleName);
	
	UserRole findByUserRoleID(Long roleID);
	
	@Query("SELECT ur FROM UserRole ur WHERE ur.statusflag='N' AND ur.userRoleStatus = '1'  AND ur.companyCode= ?2 AND "
			+ "(ur.userRoleName LIKE %?1% OR  ur.userRoleStatus LIKE %?1% "
			+ "OR ur.createdAt LIKE %?1%)")
	Page<UserRole> getuserRoletoAuthorize(String search, Long userCompanyID, Pageable page);
	
	@Query("SELECT ur FROM UserRole ur WHERE ur.statusflag='N' AND ur.userRoleStatus = '1' AND ur.userRoleID=?3  AND ur.companyCode= ?2 AND "
			+ "(ur.userRoleName LIKE %?1% OR  ur.userRoleStatus LIKE %?1% "
			+ "OR ur.createdAt LIKE %?1%)")
	Page<UserRole> getuserRoletoAuthorizerolewise(String search, Long userCompanyID,Long userRoleID ,Pageable page);
	
	List<UserRole> findByUserRoleNameLikeAndUserRoleStatusAndStatusflagAndCompanyCode(String search, Integer ststus ,String flag ,long ccode,Pageable page);
	
	List<UserRole> findByCompanyCode(Long userSubCompanyCode);

}
