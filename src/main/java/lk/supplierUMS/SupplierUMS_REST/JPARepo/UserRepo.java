package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;

@Component
public interface UserRepo extends JpaRepository<User, String> {
	
	Optional <User> findByUserid(String username);
	Optional <User> findByUseridAndStatus(String username, Integer status);
	Optional <User> findByUseridAndEmail(String username, String email);
	Optional <User> findByCompanyCodeAndEmail(String companyCode, String email);
	//findByUserRole()
	boolean existsByUserid(String userid);
	boolean existsByEmail(String email);
	boolean existsByuserid(String userid);
	boolean existsByNic(String nic);
	boolean existsByMobileNo(String mobile);
	
	List<User>findByStatus(String selectusertype);
	List<User> findByCompanyCode(String companyCode);

	@Query(value = "SELECT u.m_fcmtoken FROM m_user AS u INNER JOIN m_user_role AS r ON u.m_user_role_id = r.m_user_role_id INNER JOIN m_user_has_user_role_options AS uhop " + 
			"ON r.m_user_role_id = uhop.m_user_role_id INNER JOIN m_user_role_options AS op ON uhop.m_user_role_options_id = op.m_user_role_options_id" + 
			" WHERE u.c_code = ?1 AND op.m_user_role_options_name=?2", nativeQuery = true)
	List<String> getFCMUsersByRoleOption(long companyCode,String  options);
	
	@Query(value = "SELECT u FROM m_user AS u INNER JOIN m_user_role AS r ON u.m_user_role_id = r.m_user_role_id INNER JOIN m_user_has_user_role_options AS uhop " + 
			"ON r.m_user_role_id = uhop.m_user_role_id INNER JOIN m_user_role_options AS op ON uhop.m_user_role_options_id = op.m_user_role_options_id" + 
			" WHERE c_code = ?1 AND op.m_user_role_options_name=?2", nativeQuery = true)
	List<User> getUserByRoleoptionAndCompanyCode(long companyCode, String Option);
	
	@Query(value = "SELECT CASE WHEN  COUNT(u.`m_user_id`)= 1 THEN TRUE ELSE FALSE END FROM m_user AS u INNER JOIN m_user_role AS r ON u.m_user_role_id = r.m_user_role_id " + 
			"INNER JOIN m_user_has_user_role_options AS uhop ON r.m_user_role_id = uhop.m_user_role_id " + 
			"INNER JOIN m_user_role_options AS op ON uhop.m_user_role_options_id = op.m_user_role_options_id " + 
			"WHERE u.`m_user_id` = ?1 AND op.m_user_role_options_name=?2", nativeQuery = true)
	int checkAuth(String userId, String optionName);
	
	// NOT Example
	List<User> findByCompanyCodeAndUserRollUserRoleIDNot(String companyCode, String roleID);
	
	
	@Query("SELECT ur FROM User ur WHERE ur.statusFlag='N'  AND ur.companyCode= ?2 AND "
			+ "(ur.username LIKE %?1% OR  ur.status LIKE %?1% "
			+ "OR ur.createdDateTime LIKE %?1%)") 
	Page<User> getuserListtoAuthorize(String search, String userCompanyID, Pageable page);
	
	
	List<User> findByUsernameLikeAndStatusAndStatusFlagAndCompanyCode(String search, Integer ststus ,String flag ,String ccode,Pageable page);
	
	@Query("SELECT ur FROM User ur WHERE ur.statusFlag='N' AND ur.userid=?3  AND ur.companyCode= ?2 AND "
			+ "(ur.username LIKE %?1% OR  ur.status LIKE %?1% "
			+ "OR ur.createdDateTime LIKE %?1%)") 
	Page<User> getuserWisetoAuthorize(String search, String userCompanyID,String userid, Pageable page);
	
	long countByCompanyCode(String companycode);
	
	@Query("SELECT Count(usr) FROM User usr INNER JOIN SubCompany sub ON usr.companyCode=sub.subCompanyID WHERE sub.identity = ?1 AND NOT sub.subCompanyID =?2")
	long getUserCountsByCompanyType(boolean type, long SuperuserCompany);
	
	@Query("SELECT user,dep,ur FROM User user INNER JOIN Department dep ON user.deptID = dep.deptId INNER JOIN UserRole ur ON user.userRoll = ur.userRoleID WHERE user.companyCode = ?2 AND user.status = '3' AND user.tempUserStatus = '3' AND user.statusFlag = 'Y' AND (user.username LIKE %?1%)")
	Page<Object[]> getAllUsersForEditView(String search, String userCompanyID, Pageable page);
	
	@Query("SELECT user FROM User user WHERE user.userid = '?1'")
	Optional<User> getDetailsFromUser(String userId);
	
	@Query("SELECT ut,dep,ur FROM UserTemporary ut INNER JOIN Department dep ON ut.deptIDTemp = dep.deptId INNER JOIN UserRole ur ON ur.userRoleID = ut.userRoleId WHERE ut.companyCodeTemp = ?2 AND (ut.usernameTemp LIKE %?1%)")
	Page<Object[]> getAllTempUsersForAuth(String parameter, String userCompanyID, PageRequest of);
	
}
