package lk.supplierUMS.SupplierUMS_REST.controllers;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRepo;
import lk.supplierUMS.SupplierUMS_REST.service.UserService;

@CrossOrigin
@RestController
@RequestMapping(value = "registration/")
public class RegistrationController {
	
	@Autowired
	UserRepo userrepo;
	
	@Autowired
	UserService userService;
	
	
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
			
	    case "company_name": 
	    	System.out.println("company_name Check---- "+ enteredValue);
			 isExists = userService.getWetherUserExistsByCompanyName(enteredValue);
			System.out.println("company_name "+ isExists);
			
			js.put("idtype", "company_name");
			js.put("msg", isExists);
	    	
			break;  	
			
	    default:
	    	
		}
		
		
		
		return js;
		
		
		
		
	}

}
