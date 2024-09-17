package lk.supplierUMS.SupplierUMS_REST.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.tools.DocumentationTool.Location;
import javax.websocket.server.PathParam;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

import lk.supplierUMS.SupplierUMS_REST.JPARepo.DepartmentDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.LocationDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRoleOptionsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRoleRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.Department;
import lk.supplierUMS.SupplierUMS_REST.entity.LocationDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRoleOptions;
import lk.supplierUMS.SupplierUMS_REST.service.UserRoleService;

@CrossOrigin
@RestController
@RequestMapping(value = "userroles/")
public class UserRollController {

	@Autowired
	UserRoleService userRollService;

	@Autowired
	UserRoleRepo urr;

	@Autowired
	UserRoleOptionsRepo userRoleOptionsRepo;

	@Autowired
	UserRoleRepo userRollRepo;
	
	@Autowired
	DepartmentDetailsRepo departmentDetailsRepo;
	
	@Autowired
	LocationDetailsRepo locationDetailsRepo;
	
	

	/*
	 * @GetMapping(value = "all") public String getUserRoll() { return
	 * "Hello User Roles"; }
	 */

	@PostMapping(value = "adduserrole")
	public ResponseEntity addUserRole(@RequestBody UserRole userRoll) {
		long id = userRollService.addUserRole(userRoll);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(location).build();
	}

	// @CrossOrigin
	@GetMapping(value = "getuserrole/{roleid}")
	public List<UserRole> getUserRole(@PathVariable long roleid) {
		List<UserRole> rolelist = null;

		try {
			
			rolelist = userRollService.getUserRole(roleid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// urr.findAll();
		return rolelist;
	}
	
	@GetMapping(value = "getalluserroles/{usersSubCompanyID}")
	public List<UserRole> getUserAllUserRolesForSubCompany(@PathVariable long usersSubCompanyID) {
		List<UserRole> rolelist = new ArrayList<>();
		try {
				rolelist = userRollService.getUserRoleForSubComapny(usersSubCompanyID);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return rolelist;
		
	//	return urr.findAll();
		
		
	}
	@GetMapping(value = "getAllUserRolesForSuperUser")
	public List<UserRole> getAllUserRolesForSuperUser() {
			return urr.findAll();
	}
	
	@GetMapping(value = "getLocationCode")
	public List<LocationDetails> getLocationCode() {
			return locationDetailsRepo.findAll();
	}
	
	@GetMapping(value = "getDepartmentCode")
	public List<Department> getDepartmentCode() {
			return departmentDetailsRepo.findAll();
	}
	

	@PutMapping(value = "{id}/userroleoptions")
	public List<UserRoleOptions> addUserRollOption(@PathVariable String id,
			@RequestBody List<UserRoleOptions> optionList) {
		System.out.println(id + " Inside userroleoptions");
		List<UserRoleOptions> userOptionList = userRollService.addUserRollOption(id, optionList);
		return userOptionList;

	}

	@GetMapping(value = "isexists/by/{type}/{enteredValue}")
	public JSONObject getWetherUserExists(@PathVariable String enteredValue, @PathVariable String type) {

		JSONObject js = new JSONObject();
		Boolean isExists = null;
		switch (type) {
		case "user_role_name":
			System.out.println("userRoleName Check---- " + enteredValue);
			isExists = userRollService.getWetherUserRoleExists(enteredValue);
			System.out.println("userRoleName availability---- " + isExists);

			js.put("idtype", "userRoleName");
			js.put("msg", isExists);

			break;

		default:

		}

		return js;
	}

	@GetMapping(value = "getRoleOptionsByRoleID/{enteredValue}")
	public JSONObject getRoleOptionsByRoleID(@PathVariable String enteredValue) {
		/*
		 * // Method One JSONObject js = new JSONObject(); UserRole userRoleWithOptions
		 * = userRollService.getRoleOptionsByRoleID(enteredValue); js.put("UserRole",
		 * userRoleWithOptions); return js;
		 */

		// Method Two
		JSONArray ja = new JSONArray();

		UserRole userRoleWithOptions = userRollService.getRoleOptionsByRoleID(enteredValue);
		Long userRoleID = userRoleWithOptions.getUserRoleID();
		String userRoleName = userRoleWithOptions.getUserRoleName();
		String userRoleDescription = userRoleWithOptions.getUserRoleDescription();
		String userRoleInBusiness = userRoleWithOptions.getUserRoleInBussiness();

		List<UserRoleOptions> userRoleOptionsList = userRoleWithOptions.getOptions();

		List<UserRoleOptions> allUserRoleOptions = userRoleOptionsRepo.findAll();

//		for (UserRoleOptions userRoleOptions : userRoleOptionsList) {
//			System.out.println(userRoleOptions.getUserRoleOptionsName());			
//		}

		// allUserRoleOptions.retainAll(userRoleOptionsList);
		/*
		 * if(allUserRoleOptions.retainAll(userRoleOptionsList)){ js.put("UserRoleID",
		 * userRoleID); js.put("UserRoleDescription", userRoleDescription);
		 * js.put("userRoleInBusiness", userRoleInBusiness); js.put("userRoleOptions",
		 * userRoleOptionsList); js.put("Privilage", true); }else{ js.put("UserRoleID",
		 * userRoleID); js.put("UserRoleDescription", userRoleDescription);
		 * js.put("userRoleInBusiness", userRoleInBusiness); js.put("userRoleOptions",
		 * userRoleOptionsList); js.put("Privilage", false); }
		 */

		JSONObject roleData = new JSONObject();
		roleData.put("userRoleID", userRoleID);
		roleData.put("userRoleName", userRoleName);
		roleData.put("userRoleDescription", userRoleDescription);
		roleData.put("userRoleInBusiness", userRoleInBusiness);

		for (UserRoleOptions userRoleOptions : allUserRoleOptions) {
			System.out.println("All " + userRoleOptions.getUserRoleOptionsName());
			JSONObject js = new JSONObject();
			js.put("UserRoleOptionID", userRoleOptions.getUserRoleOptionsId());
			js.put("UserRoleOptionName", userRoleOptions.getUserRoleOptionsName());
			js.put("userRoleInBusiness", userRoleOptions.getRoleOwner());
//			js.put("userRoleOptions", userRoleOptionsList);
			js.put("privilege", userRoleOptionsList.contains(userRoleOptions));

			ja.add(js);
		}
		roleData.put("Data", ja);
		return roleData;
	}

	@PutMapping(value = "edituserrole/{id}")
	public ResponseEntity<String> updateUserRole(@RequestBody String userRole) {
		System.out.println(userRole);

		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(userRole);
			System.out.println(json.get("UserRoleID_e"));
			System.out.println(json.get("user_role_name_e"));
			System.out.println(json.get("user_role_description_e"));
			System.out.println(json.get("user_role_options_e"));

			ArrayList<Long> arr = (ArrayList<Long>) json.get("user_role_options_e");
//			
			ArrayList<UserRoleOptions> optionList = new ArrayList<>();

			for (Long id : arr) {
				Optional<UserRoleOptions> options = userRoleOptionsRepo.findById(id.intValue());
				if (options.isPresent()) {
					optionList.add(options.get());
				}
			}

			// UserRole userRoleToEdit =
			// userRollService.getRoleOptionsByRoleID(json.get("UserRoleID_e").toString());

			UserRole userRoleToEdit = userRollRepo.findByUserRoleID(new Long(json.get("UserRoleID_e").toString()));
			userRoleToEdit.setUserRoleName(json.get("user_role_name_e").toString());
			userRoleToEdit.setUserRoleDescription(json.get("user_role_description_e").toString());
			userRoleToEdit.setOptions(optionList);

			/*
			 * System.out.println(optionList.size());
			 * 
			 * for (UserRoleOptions userRoleOptions : optionList) {
			 * System.out.println(userRoleOptions.getUserRoleOptionsName()); }
			 */

			UserRole editedUserRole = userRollRepo.save(userRoleToEdit);
			String msg = "User Role edit failed";

			if (editedUserRole != null) {
				msg = "Success";
				return new ResponseEntity<String>(msg, HttpStatus.OK);
			} else {
				System.out.println(msg);
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}

			// System.out.println(arr.get(3));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		/*
		 * System.out.println(user.getUserid()+" "+user.getStatus()); String
		 * editedUserid = userService.updateUserProfile(id, user); String msg =
		 * "User edit failed";
		 * 
		 * if(editedUserid != null){ msg = "Success"; return new
		 * ResponseEntity<String>(msg, HttpStatus.OK); }else{ System.out.println(msg);
		 * return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST); }
		 */
		// return null;
	}

	@GetMapping(value = "allforedit")
	public List<UserRole> getAllUsersNoFilters() {

		List<UserRole> userList = userRollRepo.findAll();
		List<UserRole> returnuserList = new ArrayList<>();
		if (returnuserList != null) {
			for (UserRole u : userList) {
				returnuserList.add(u);
			}

		}

		return returnuserList;
	}

	//@GetMapping(value = "authorolelist")
	@GetMapping(value="{para}")
	public ResponseEntity<JSONObject> getAlluserRoletoAuthorize(@PathVariable String para,HttpServletRequest request) {
		JSONObject data = null;
		System.out.println("Controller method-----------getAlluserRoletoAuthorize "+para);

		try {

			data = userRollService.getAlluserRoletoAuthorize(request,para);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

	}

	@PostMapping(value = "approve")
	public ResponseEntity<String> approveUserRoleList(@RequestBody JSONObject data) {
		System.out.println("UserRollController.approveUserRoleList------------- " + data);

		String returnMsg = userRollService.approveUserRoleList(data);
		String msg = "User Role approval failed";

		if (returnMsg.equals("Success")) {
			msg = "Success";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping(value="getuserRoleforautho")
	public ResponseEntity<JSONObject> getuserRoleforautho(@PathParam(value = "search") String search,@PathParam(value = "page") Long page) {
		try {
			
			JSONObject userole = userRollService.getuserRoleforautho(search, page);
			
			return new ResponseEntity<JSONObject>(userole, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
