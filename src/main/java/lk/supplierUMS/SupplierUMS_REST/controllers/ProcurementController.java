package lk.supplierUMS.SupplierUMS_REST.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.AppzTenderSubmissionsRepo;
import lk.supplierUMS.SupplierUMS_REST.service.AppzTenderSubmissionsService;
import lk.supplierUMS.SupplierUMS_REST.service.ProcurementService;
import lk.supplierUMS.SupplierUMS_REST.service.TenderDetailsService;

import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.ProcurementCommittee;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;

@CrossOrigin
@RestController
@RequestMapping(value ="procurement/")
public class ProcurementController {

	@Autowired
	ProcurementService procurementService;
	
	@Value("${procurementCommittee.doc.path}")
	String procurementCommitteeDataPath;
	
	@Value("${tenderboardpaper.doc.path}")
	String tenderBoardPaper;
	
	@Value("${tendercappexfile.doc.path}")
	String tenderCappexFile;
	
	@Value("${tenderoppexfile.doc.path}")
	String tenderOppexFile;
	
	@Value("${tendermemo.doc.path}")
	String tenderMemo;
	
	@GetMapping(value = "tenderid")
	public ResponseEntity<List<TenderDetails>> getTendersForProcCommitteCreation() {
		try {
			List<TenderDetails> td = procurementService.getTendersForProcCommitteCreation();
			return new ResponseEntity<List<TenderDetails>>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "committeememberId")
	public ResponseEntity<List<User>> getTendersForProcCommitteeMembers() {
		try {
			List<User> us = procurementService.getTendersForProcCommitteeMembers();
			return new ResponseEntity<List<User>>(us, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "supplierId")
	public ResponseEntity<List<SubCompany>> getSuppliersForProcCommitteeMembers() {
		try {
			List<SubCompany> us = procurementService.getSuppliersForProcCommitteeMembers();
			return new ResponseEntity<List<SubCompany>>(us, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping(value="/add-procurement-committee")
	public ResponseEntity<String> addProcurementCommittee (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = procurementService.addProcurementCommittee(data,request);
		
		if(msg != null && msg != "Error") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "voting-details-and-voting/{id}")
	public ResponseEntity <JSONObject> getVotingDetailsForVoting(HttpServletRequest request,@PathVariable Long id) {
		System.out.println(id);
		  try {
		  
			  JSONObject res = procurementService.getVotingDetailsForVoting(request,id);
			  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
			  
		  } catch (Exception e)
		   { 
			  e.printStackTrace(); 
			  return null; 
			}
	}
	
	@PutMapping(value="vote-to-supplier/vote/{supplierId}")
	public ResponseEntity<JSONObject> voteToSupplier(@RequestBody JSONObject data, @PathVariable long supplierId){
		try {	
			JSONObject res = procurementService.voteToSupplier(data,supplierId);
			return new ResponseEntity<JSONObject>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value = "voting-details/{id}")
	public ResponseEntity <JSONObject> getVotingDetails(HttpServletRequest request,@PathVariable Long id) {
		System.out.println(id);
		  try {
		  
			  JSONObject res = procurementService.getVotingDetails(request,id);
			  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
			  
		  } catch (Exception e)
		   { 
			  e.printStackTrace(); 
			  return null; 
			}
	}
	
	@GetMapping(value="/isexists/by/{committeeMember}/{procTenderid}")
	public JSONObject getWetherRfpExists(@PathVariable String committeeMember, @PathVariable Long procTenderid) {
		 
		JSONObject js = new JSONObject();
		Boolean isExists = null;
		isExists = procurementService.getWetherProcComExists(committeeMember,procTenderid);
		js.put("idtype", "procCom");
		js.put("msg", isExists);
		
		return js;
		
	}
	
	@PostMapping(value="/procurement-board-paper-upload/{fileName}")
	public ResponseEntity<String> boardPaperSubmit (@RequestBody JSONObject data, HttpServletRequest request,@PathVariable String fileName) {
		
		String msg = procurementService.boardPaperSubmit(data,request,fileName);
		
		if(msg != null && msg == "Success") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/isexists-board-papers/by/{boardTenderid}")
	public JSONObject getWetherBoardPaperExists(@PathVariable Long boardTenderid) {
		 
		JSONObject js = new JSONObject();
		Boolean isExists = null;
		isExists = procurementService.getWetherBoardPaperExists(boardTenderid);
		js.put("idtype", "boardTenderId");
		js.put("msg", isExists);
		
		return js;
		
	}
	
	@GetMapping(value = "board-paper-download/{tenderid}")
	public  ResponseEntity<Resource> downloadBoardPaper(HttpServletRequest request, @PathVariable String tenderid) {
		
		
		File file = new File(tenderBoardPaper+"/"+tenderid+".pdf");
		
		InputStreamResource resource = null;
		 
		
		 try {
			 resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 HttpHeaders h = new HttpHeaders();
		 h.add("Content-Disposition", "filename=" + tenderid+".pdf");
		 
		    return ResponseEntity.ok()
		            .headers(h)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType("application/pdf"))
		            .body(resource);
		//return null;
	}
	
	@GetMapping(value = "cappex-file-download/{tenderid}")
	public  ResponseEntity<Resource> downloadCappexFile(HttpServletRequest request, @PathVariable String tenderid) {
		
		
		File file = new File(tenderCappexFile+"/"+tenderid+".pdf");
		
		InputStreamResource resource = null;
		 
		
		 try {
			 resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 HttpHeaders h = new HttpHeaders();
		 h.add("Content-Disposition", "filename=" + tenderid+".pdf");
		 
		    return ResponseEntity.ok()
		            .headers(h)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType("application/pdf"))
		            .body(resource);
		//return null;
	}
	
	@GetMapping(value = "oppex-file-download/{tenderid}")
	public  ResponseEntity<Resource> downloadOppex(HttpServletRequest request, @PathVariable String tenderid) {
		
		
		File file = new File(tenderOppexFile+"/"+tenderid+".pdf");
		
		InputStreamResource resource = null;
		 
		
		 try {
			 resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 HttpHeaders h = new HttpHeaders();
		 h.add("Content-Disposition", "filename=" + tenderid+".pdf");
		 
		    return ResponseEntity.ok()
		            .headers(h)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType("application/pdf"))
		            .body(resource);
		//return null;
	}
	
	@GetMapping(value = "memo-file-download/{tenderid}")
	public  ResponseEntity<Resource> downloadMemoFile(HttpServletRequest request, @PathVariable String tenderid) {
		
		
		File file = new File(tenderMemo+"/"+tenderid+".pdf");
		
		InputStreamResource resource = null;
		 
		
		 try {
			 resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 HttpHeaders h = new HttpHeaders();
		 h.add("Content-Disposition", "filename=" + tenderid+".pdf");
		 
		    return ResponseEntity.ok()
		            .headers(h)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType("application/pdf"))
		            .body(resource);
		//return null;
	}
	
	@GetMapping(value = "get-rfp-details-for-view/{id}")
	public ResponseEntity <JSONObject> getRfpDetailsForView(@PathVariable Long id) {
		System.out.println(id);
		  try {
		  
			  JSONObject res = procurementService.getRfpDetailsForView(id);
			  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
			  
		  } catch (Exception e)
		   { 
			  e.printStackTrace(); 
			  return null; 
			}
	}
}