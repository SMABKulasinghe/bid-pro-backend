package lk.supplierUMS.SupplierUMS_REST.controllers;

import java.net.URI;
import java.util.List;

import org.json.simple.JSONArray;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lk.supplierUMS.SupplierUMS_REST.entity.GlobalDetails;
import lk.supplierUMS.SupplierUMS_REST.service.PglobService;

@CrossOrigin
@RestController
@RequestMapping(value ="/glob")
public class PglobController {

	@Autowired
	PglobService pglobservice;
	
	@PostMapping(value="")
	public ResponseEntity saveGlog(@RequestBody GlobalDetails pglob) {
		
		String pid=pglobservice.saveglob(pglob);
		System.out.println(pid);
		
		URI loca=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pid).toUri();
		System.out.println(loca);
		return ResponseEntity.created(loca).build();
	}
	
	@GetMapping(value="{id}")
	public GlobalDetails getGlobalData(@PathVariable String id) {
		System.out.println(id);
	GlobalDetails pgdata=pglobservice.getGlobal(id);
//		System.out.println("inside controlle and this is index---"+pgdata.getIndexNo());	
		return pgdata;
	}
	
	@GetMapping(value="")
	public List<GlobalDetails> getGlobalData() {
		System.out.println("test");
	return null;	
	}
	
	@GetMapping(value="/notifications")
	public ResponseEntity<JSONArray> getNotifications() {
		try {
			JSONArray data = pglobservice.getNotifications();
			return new ResponseEntity<JSONArray>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
