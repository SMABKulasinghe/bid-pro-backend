package lk.supplierUMS.SupplierUMS_REST.controllers;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRoleOptionsRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRoleOptions;
import lk.supplierUMS.SupplierUMS_REST.service.UserRoleOptionsService;
@CrossOrigin
@RestController
@RequestMapping(value="userroleoptions/")
public class UserRoleOptionsController {
	
	//private static final Logger log = LoggerFactory.getLogger(UserRoleOptionsController.class);
	@Autowired
	UserRoleOptionsService userRoleOptionsService;
	
	@Autowired
	UserRoleOptionsRepo userRoleOptionsRepo;
	
	@PostMapping(value="adduserroleoptions")
	public ResponseEntity addUserRoleOptions(@RequestBody UserRoleOptions userRoleOptions){
		int id = userRoleOptionsService.addUserRoleOptions(userRoleOptions);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(location).build();
	} 
	
	@PostMapping(value="{id}/updateuserrolloptions")
	public ResponseEntity updateuserrolloptions(@RequestBody UserRoleOptions userRoleOptions){
		// review this again
		return null;
	} 
	
	@GetMapping(value="all")
	public List<UserRoleOptions> getUserOptions(){
	//	log.info(" UserRoleOptionsController.class-getUserOptions Method");
		List<UserRoleOptions> ulist = userRoleOptionsRepo.findAll();
	//	log.info(" UserRoleOptionsController.class-getUserOptions Method---"+ulist.size());
		return ulist;
	}
}
