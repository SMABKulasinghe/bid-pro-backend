package lk.supplierUMS.SupplierUMS_REST.controllers;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.supplierUMS.SupplierUMS_REST.service.ContractService;

@CrossOrigin
@RestController
@RequestMapping(value ="contract/")
public class ContractController {
	
	@Autowired
	ContractService contractService;
	
	@PostMapping(value = "addcontract")
	public ResponseEntity<String> addContract(@RequestBody JSONObject data) {
		
		String id = contractService.addContract(data);
		String msg = "Contract creation failed";
		
		if(id!=null){
			msg = "Success";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping(value="{parameter}")
	public ResponseEntity<JSONObject> getAllContracts(HttpServletRequest request, @PathVariable String parameter) {
		JSONObject data = null;
		System.out.println("Controller parameter "+parameter);
		
		try {
			
			data = contractService.getAllContracts(request, parameter);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		
		
	}
	
	@GetMapping(value="partnership/{id}")
	public ResponseEntity<JSONObject> getPartnershipDetails(@PathVariable long id) {
		try {
			JSONObject data = contractService.getPartnership(id);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@GetMapping(value="partnership/{id}/accept/{status}")
	public ResponseEntity<JSONObject> acceptPartnership(@PathVariable long id, @PathVariable String status) {
		try {
			JSONObject data = contractService.acceptPartnership(id,status);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping(value = "approve")
	public ResponseEntity<String> approveContract(@RequestBody JSONObject data) {
		System.out.println("ApproveContract "+data);
		
		String returnMsg = contractService.approveContract(data);
		String msg = "Contract approval failed";
		
		if(returnMsg.equals("Success")){
			msg = "Success";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping(value="company/{id}/contracts")
	public ResponseEntity<JSONObject> getCompanyContracts(@PathVariable long id,HttpServletRequest request) {
		try {
			
			JSONObject data = contractService.getCompanyContracts(id, request);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
	}
	
	@GetMapping(value="contractlist")
	public ResponseEntity<JSONObject> getcontractlist(@PathParam(value = "search") String search,@PathParam(value = "page") Long page) {
		try {
			System.out.println("getcontractlist");
			JSONObject contracts = contractService.getcontractlist(search, page);
			
			return new ResponseEntity<JSONObject>(contracts, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
