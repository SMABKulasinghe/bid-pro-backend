package lk.supplierUMS.SupplierUMS_REST.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.Department;
import lk.supplierUMS.SupplierUMS_REST.entity.LocationDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRoleOptions;
import lk.supplierUMS.SupplierUMS_REST.service.UserService;
import springfox.documentation.spring.web.json.Json;
@CrossOrigin
@RestController
@RequestMapping(value ="users/")
public class UserController {
	
	@Autowired
	UserRepo userrepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SubCompanyRepo subCompanyRepo;
	
	
	@GetMapping(value = "all")
	public List<User> getAllUsers(){
		//Optional<User> u = userrepo.findByUserid("F001");
		//System.out.println(u.get().getUsername());
		//return "Hello Supplier Portal REST";
		
		List<User> userList = userrepo.findAll();
		List<User> returnuserList = new ArrayList<>();
		if(returnuserList != null){
			for(User u : userList){
				 if(/*!u.getStatus().equals("3") || */u.getStatusFlag().equals("N")){
					 returnuserList.add(u);
				 };
			}
			
		}
		
		return returnuserList;
	}
	
	@GetMapping(value = "allforedit")
	public List<User> getAllUsersNoFilters(){
		
		List<User> userList = userrepo.findAll();
		List<User> returnuserList = new ArrayList<>();
		if(returnuserList != null){
			for(User u : userList){
				 returnuserList.add(u);				 
			}
			
		}
		
		return returnuserList;
	}
	
	@PostMapping(value = "adduser")
	public ResponseEntity<String> addUser(@RequestBody User user) {
		System.out.println("User Con--> "+user.getLocationID()+" "+user.getDeptID());
		
	String id = userService.addUser(user);
	String msg = "User creation failed";
	
	if(id!=null){
		msg = "Success";
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}else{
		return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
	}
	
	
	//userrepo.save(user);
	//String id = "";
	
	//URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	//return ResponseEntity.created(location).build();

	}
	
	@GetMapping(value = "{id}")
	public User getUsers(@PathVariable String id){
		User u = userService.getUserFromID(id);
		return u;
	}
	
	@GetMapping(value = "checkuser/{id}")
	public User checkUser(@PathVariable String id){
		User u = userService.checkUser(id);
		return u;
	}
	
	@PutMapping(value = "updateFirstLogin")
	public ResponseEntity<JSONObject> updateFirstLogin(@RequestBody JSONObject data, HttpServletRequest request){
		try {
			JSONObject em = userService.updateFirstLogin(data, request);
			return new ResponseEntity<JSONObject>(em, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "finduser/{id}")
	public JSONObject getUser(@PathVariable String id){
		User u = userService.getUserFromID(id);
		JSONObject js = new JSONObject();
		js.put("data", u);
		return js;
	}
	
	@GetMapping(value="{id}/useroptions")
	public List<UserRoleOptions> getUserOptions(@PathVariable String id){
		List<UserRoleOptions> ulist = userService.getUserRollOptions(id);
		return ulist;
	}
	
	@PutMapping(value ="{id}")
	public User updateUserStatus(@PathVariable String id, @RequestBody User user){		
			User u = userService.updateUserStatus(id, user);
		return u;
	}
	
	@PutMapping(value ="authorize")
	public User authorizeUsers(@RequestBody List<User> userList){
		System.out.println(userList.size());
		for(User u : userList){
			System.out.println(u.getUserid()+" "+u.getStatus());
			userService.updateUserStatus(u.getUserid(), u);
		}
		return null;
	}
	
	@GetMapping(value = "testpassword")
	public String testPassword(){
		String randomPassword = userService.generateRandomPassword();
		
		System.out.println("randomPW password--" + randomPassword);
		return randomPassword;
	}
	
	
	@GetMapping(value = "changepassword/user/{loggedUser}/oldps/{oldpassword}/newps/{newpassword}")
	public JSONObject changePassword(@PathVariable String oldpassword, @PathVariable String newpassword, @PathVariable String loggedUser){
		System.out.println("change password--" + oldpassword+" "+newpassword);
		
		String msg = userService.changePassword(loggedUser, oldpassword, newpassword);
		
		JSONObject js = new JSONObject();
		js.put("msg", msg);
		return js;
	}
	
	@PutMapping(value = "changepassword")
	public ResponseEntity<JSONObject> changePasswordAtFirstLogin(@RequestBody JSONObject data,HttpServletRequest request){
		
		try {
			
			JSONObject msg = userService.changePasswordAtFirstLogin(data, request);
			//userService.updateFirstLogin(data, request);
			return new ResponseEntity<JSONObject>(msg, HttpStatus.OK);
			
			//return new ResponseEntity<Long>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping(value = "forgotpassword/user/{userid}")
	public ResponseEntity<JSONObject> forgotPassword(@PathVariable String userid, @RequestBody JSONObject data){
		try {
			JSONObject msg = userService.forgotPassword(userid, data);

			return new ResponseEntity<JSONObject>(msg, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<JSONObject>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(value = "isexists/by/{type}/{enteredValue}")
	public JSONObject getWetherUserExists(@PathVariable String enteredValue, @PathVariable String type){
		
		JSONObject js = new JSONObject();
		Boolean isExists = null;
		switch(type){  
	    case "user_id": 
	    	System.out.println("UserID Check---- "+ enteredValue);
			 isExists = userService.getWetherUserExists(enteredValue);
			System.out.println("UserID availability---- "+ isExists);
			
			js.put("idtype", "userId");
			js.put("msg", isExists);

	    	break;  
	    	
	    case "NIC_no": 
	    	System.out.println("NIC Check---- "+ enteredValue);
			 isExists = userService.getWetherUserExistsByNIC(enteredValue);
			System.out.println("NIC availability---- "+ isExists);
			
			js.put("idtype", "NIC_no");
			js.put("msg", isExists);
	    	
			break;  
			
	    case "mobile_no": 
	    	System.out.println("mobile_no Check---- "+ enteredValue);
			 isExists = userService.getWetherUserExistsByMobile(enteredValue);
			System.out.println("mobile_no "+ isExists);
			
			js.put("idtype", "mobile_no");
			js.put("msg", isExists);
	    	
			break;  
			
			
	    case "user_email": 
	    	System.out.println("user_email Check---- "+ enteredValue);
			 isExists = userService.getWetherUserExistsByEmail(enteredValue);
			System.out.println("user_email "+ isExists);
			
			js.put("idtype", "user_email");
			js.put("msg", isExists);
	    	
			break;  
			
	    default:
	    	
		}
		
		
		
		return js;
		
		
		
		
	}
	
	@PutMapping(value ="edituser/{id}")
	public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable String id){
		System.out.println(user.getUserid());
		
			System.out.println(user.getUserid()+" "+user.getStatus());
			String editedUserid = userService.updateUserProfile(id, user);
			String msg = "User edit failed";
			
			if(editedUserid != null){
				msg = "Success";
				return new ResponseEntity<String>(msg, HttpStatus.OK);
			}else{
				System.out.println(msg);
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
			
	}
	
	@GetMapping(value = "finduserbycompanyid/{companyID}")
	public JSONObject getUserByCompanyCode(@PathVariable String companyID){
		JSONObject js = new JSONObject();
		List<User> listOfUserIDs = userService.updateUserByCompanyCode(companyID);
		
		js.put("userIDList", listOfUserIDs);
		
		return js;
		
	
	}
	
	@PostMapping(value = "{id}/token")
	public JSONObject addFcmToken(@PathVariable String id, @RequestBody JSONObject data){
		
		userService.addFcmToken(id, data);
		
		return null;
	}
	
	/*
	 * //-----------------Kusal 2020-01-09-----------------------------//
	 * 
	 * @GetMapping(value="authouserlist") public ResponseEntity<JSONObject>
	 * getAlluserListtoAuthorize(HttpServletRequest request) { JSONObject data =
	 * null; System.out.
	 * println("User Controller method-----------getAlluserListtoAuthorize ");
	 * 
	 * try {
	 * 
	 * data = userService.getAlluserListtoAuthorize(request);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
	 * 
	 * 
	 * }
	 */
	@PostMapping(value = "approve")
	public ResponseEntity<String> approveUserIDList(@RequestBody JSONObject data) {
		System.out.println("UserController.approveUserIDList------------- "+data);
		
		String returnMsg = userService.approveUserIDList(data);
		String msg = "User ID approval failed";
		
		if(returnMsg.equals("Success")){
			msg = "Success";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@PostMapping(value = "authorize")
	public ResponseEntity<String> authUser(@RequestBody JSONObject data) {
		System.out.println("UserController.approveUserIDList------------- "+data);
		
		String returnMsg = userService.authUser(data);
		String msg = "User ID approval failed";
		
		if(returnMsg.equals("Success")){
			msg = "Success";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping(value="users/{id}")
	public ResponseEntity<JSONObject> getUserDetailstoResetPassword(@PathVariable String id) {
		
		System.out.println("User Controller method-----------getUserDetailstoResetPassword ");
		
		
		try {
			System.out.println(id);
			JSONObject supplierData = userService.getUserDetailstoResetPassword(id);
			
			return new ResponseEntity<JSONObject>(supplierData, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PostMapping(value = "pwdreset/{useridtemp}")
	public ResponseEntity<String> resetUserPassword(@PathVariable String useridtemp) {
		System.out.println("UserController.resetPassword------- "+useridtemp);
		
		String returnMsg = userService.resetUserPassword(useridtemp);
		String msg = "User password reset failed";
		
		if(returnMsg.equals("Success")){
			msg = "Success";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	@GetMapping(value="statusCheckForCS/{userCompanyID}")
	public ResponseEntity<JSONObject> getstatusCheckForCS(@PathVariable Long userCompanyID) {
		
		System.out.println("User Controller method-----------statusCheckForCS "+userCompanyID);
		
		
		try {
			SubCompany subCompany = subCompanyRepo.findById(userCompanyID).get();
			//subCompany.isIdentity();
			
			JSONObject subCompanyData = new JSONObject();
			if(subCompany.isIdentity()){
				subCompanyData.put("subCompanyBool", subCompany.isIdentity());
			}else{
				subCompanyData.put("subCompanyBool", subCompany.isIdentity());
			}
			
			
			
			return new ResponseEntity<JSONObject>(subCompanyData, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@GetMapping(value="getuserforautho")
	public ResponseEntity<JSONObject> getUserforautho(@PathParam(value = "search") String search,@PathParam(value = "page") Long page) {
		try {
			
			JSONObject userole = userService.getUserforautho(search, page);
			
			return new ResponseEntity<JSONObject>(userole, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="authlist/{para}")
	public ResponseEntity<JSONObject> getAllusertoAuthorize(@PathVariable String para,HttpServletRequest request) {
		JSONObject data = null;
		System.out.println("Controller method-----------getAllusertoAuthorize "+para);

		try {

			data = userService.getAlluserListtoAuthorize(request,para);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

	}
	
	
	@GetMapping(value="view/{parameter}")
	public ResponseEntity<JSONObject> getAllUsersForEditView(HttpServletRequest request, @PathVariable String parameter) {
		JSONObject data = null;
		System.out.println("Controller parameter "+parameter);
		
		try {
			
			data = userService.getAllUsersForEditView(request, parameter);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		
		
	}
	
	@GetMapping(value="view-all-for-edit-auth")
	public ResponseEntity<JSONObject> getAllTempUsersForAuth(HttpServletRequest request) {
		JSONObject data = null;
		
		try {
			
			data = userService.getAllTempUsersForAuth(request);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		
		
	}

	@GetMapping(value = "/user-role-for-edit")
	public ResponseEntity<List<UserRole>> getUserRolesForEdit() {
		try {
			
			List<UserRole> userRole = userService.getUserRolesForEdit();
			return new ResponseEntity<List<UserRole>>(userRole, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/user-role-for-edit-find-by-id/{userRoleId}")
	public ResponseEntity<JSONObject> getUserRolesForEditFindById(@PathVariable Long userRoleId) {
		try {
			
			JSONObject userRole = userService.getUserRolesForEditFindById(userRoleId);
			return new ResponseEntity<JSONObject>(userRole, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/location-code-for-edit")
	public ResponseEntity<List<LocationDetails>> getLocationCodeForEdit() {
		try {
			
			List<LocationDetails> userRole = userService.getLocationCodeForEdit();
			return new ResponseEntity<List<LocationDetails>>(userRole, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/location-code-for-edit-find-by-id/{locationCode}")
	public ResponseEntity<JSONObject> getLocationCodeForEditFindById(@PathVariable Long locationCode) {
		try {
			
			JSONObject locCode = userService.getLocationCodeForEditFindById(locationCode);
			return new ResponseEntity<JSONObject>(locCode, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/department-code-for-edit-find-by-id/{depId}")
	public ResponseEntity<JSONObject> getDepartmentCodeForEditFindById(@PathVariable Long depId) {
		try {
			
			JSONObject depCode = userService.getDepartmentCodeForEditFindById(depId);
			return new ResponseEntity<JSONObject>(depCode, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/department-code-for-edit")
	public ResponseEntity<List<Department>> getDepartmentCodeForEdit() {
		try {
			
			List<Department> userDep = userService.getDepartmentCodeForEdit();
			return new ResponseEntity<List<Department>>(userDep, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping(value="/add-temporary")
	public ResponseEntity<String> addTemporaryUser (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = userService.addTemporaryUser(data,request);
		
		if(msg != null && msg != "Error") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value="/add-temporary-data-to-user")
	public ResponseEntity<String> addTemporaryDataToUserTable (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = userService.addTemporaryDataToUserTable(data,request);
		
		if(msg != null && msg != "Error") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value="/add-temporary-data-to-user/rejected")
	public ResponseEntity<String> addTemporaryDataToUserTableRej (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = userService.addTemporaryDataToUserTableRej(data,request);
		
		if(msg != null && msg != "Error") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
