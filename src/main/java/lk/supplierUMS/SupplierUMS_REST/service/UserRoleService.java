package lk.supplierUMS.SupplierUMS_REST.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRoleOptions;

public interface UserRoleService {
	public long addUserRole(UserRole userRole);

	public UserRole getUserRole(UserRole userRoll);

	public List<UserRoleOptions> addUserRollOption(String id, List<UserRoleOptions> optionList);

	public Boolean getWetherUserRoleExists(String enteredValue);

	public UserRole getRoleOptionsByRoleID(String enteredValue);
	
	JSONObject getAlluserRoletoAuthorize(HttpServletRequest request, String para);
	
	String approveUserRoleList(JSONObject data);
	
	public List<UserRole> getUserRole(long roleid);
	
	JSONObject getuserRoleforautho(String search, Long page);

	public List<UserRole> getUserRoleForSubComapny(long usersSubCompanyID);

}
