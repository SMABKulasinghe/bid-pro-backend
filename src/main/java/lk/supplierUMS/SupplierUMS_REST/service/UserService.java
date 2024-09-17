package lk.supplierUMS.SupplierUMS_REST.service;


import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import lk.supplierUMS.SupplierUMS_REST.entity.Department;
import lk.supplierUMS.SupplierUMS_REST.entity.LocationDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRoleOptions;


public interface UserService {
	public String addUser(User user);

	public User getUserFromID(String id);

	public List<UserRoleOptions> getUserRollOptions(String id);

	public User updateUserStatus(String id, User u);

	public String generateRandomPassword();
	
	public int sendMail(String from,String to, String subject, String body);
	
	public int sendMailCargills(String from,String to, String subject, String body);

	public String changePassword(String loggedUser, String oldPassword, String newPassword);

	public String forgotPassword(String userid, String useremail);

	public Boolean getWetherUserExists(String enteredValue);

	public Boolean getWetherUserExistsByNIC(String enteredValue);

	public Boolean getWetherUserExistsByMobile(String enteredValue);

	public Boolean getWetherUserExistsByEmail(String enteredValue);

	public String updateUserProfile(String userid, User user);

	public List<User> updateUserByCompanyCode(String companyID);

	public void addFcmToken(String id, JSONObject data);
	
	
	String approveUserIDList(JSONObject data);	
	
	JSONObject getUserDetailstoResetPassword(String id);
	
	public String resetUserPassword(String id);

	public JSONObject forgotPassword(String userid, JSONObject data);
	
	JSONObject getUserforautho(String search, Long page);
	
	JSONObject getAlluserListtoAuthorize(HttpServletRequest request, String para);

	public JSONObject getAllUsersForEditView(HttpServletRequest request, String parameter);

	public JSONObject updateFirstLogin(JSONObject data, HttpServletRequest request);

	public JSONObject changePasswordAtFirstLogin(JSONObject data, HttpServletRequest request);

	public String authUser(JSONObject data);

	
	public Boolean getWetherUserExistsByCompanyName(String enteredValue);

	public List<UserRole> getUserRolesForEdit();

	public JSONObject getUserRolesForEditFindById(Long userRoleId);

	public JSONObject getLocationCodeForEditFindById(Long locationCode);

	public List<LocationDetails> getLocationCodeForEdit();

	public JSONObject getDepartmentCodeForEditFindById(Long depId);

	public List<Department> getDepartmentCodeForEdit();

	public String addTemporaryUser(JSONObject data, HttpServletRequest request);

	public JSONObject getAllTempUsersForAuth(HttpServletRequest request);

	public String addTemporaryDataToUserTable(JSONObject data, HttpServletRequest request);

	public String addTemporaryDataToUserTableRej(JSONObject data, HttpServletRequest request);

	public User checkUser(String id);

	

	
}
