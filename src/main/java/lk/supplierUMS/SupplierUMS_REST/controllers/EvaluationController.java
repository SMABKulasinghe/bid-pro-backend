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

import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationSheetCreate;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.service.EvaluationSheetCreateService;


@CrossOrigin
@RestController
@RequestMapping(value = "evaluation/")
public class EvaluationController {

	@Autowired
	EvaluationSheetCreateService evaluationService;
	
	@Value("${mitDetails.doc.path}")
	String mitDetailsDataPath;
	
	
	// Evaluation Sheet Create - Tender No
	@GetMapping(value = "trnderid")
	public ResponseEntity<List<TenderDetails>> getTenderDetails() {
		try {
			List<TenderDetails> td = evaluationService.getTenderId();
			return new ResponseEntity<List<TenderDetails>>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@PostMapping(value="/addevaluation")
	public ResponseEntity<String> addevaluation (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = evaluationService.addevaluation(data,request);
		
		if(msg != null && msg == "Success" || msg != null && msg == "Already created" ) {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value="/addmarksheet")
	public ResponseEntity<String> addmarksheet (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = evaluationService.addmarksheet(data,request);
		
		if(msg != null && msg == "Success") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "getsubmitedcounts/tenderID/{tenderID}/vendorID/{vendorID}")
	public ResponseEntity<String> getsubmitedcounts(@PathVariable Long tenderID, @PathVariable Long vendorID) {
		try {
			String td = evaluationService.getsubmitedcounts(tenderID, vendorID);
			return new ResponseEntity<String>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@GetMapping(value = "get-evaluatioed-marks/{tenderId}/{venderId}")
	public ResponseEntity<JSONObject> getEvaluatioedMarks(@PathVariable long tenderId, @PathVariable String venderId) {
		try {
			JSONObject em = evaluationService.getEvaluatioedMarks(tenderId,venderId);
			return new ResponseEntity<JSONObject>(em, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "get-tender-ids")
	public ResponseEntity<JSONObject> getTenderDetailsIds() {
		try {
			JSONObject td = evaluationService.getTenderIds();
			return new ResponseEntity<JSONObject>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	@GetMapping(value = "get-evaluation-all-marks/{id}")
	public ResponseEntity <JSONObject> getEvaluationAllMarks(HttpServletRequest request,@PathVariable Long id) {
		System.out.println(id);
		  try {
		  
			  JSONObject res = evaluationService.getEvaluationAllMarks(request,id);
			  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
			  
		  } catch (Exception e)
		   { 
			  e.printStackTrace(); 
			  return null; 
			}
	}
	
	
	@GetMapping(value = "tenderId")
	public ResponseEntity<List<TenderDetails>> getTenderDetailsForMarks() {
		try {
			List<TenderDetails> td = evaluationService.getTenderDetailsForMarks();
			return new ResponseEntity<List<TenderDetails>>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@GetMapping(value = "committeememberId")
	public ResponseEntity<List<User>> getTenderEvaluationForCommittee() {
		try {
			List<User> td = evaluationService.getTenderEvaluationForCommittee();
			return new ResponseEntity<List<User>>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	@PostMapping(value="/addevaluationcommittee")
	public ResponseEntity<JSONObject> addevaluationcommittee (@RequestBody JSONObject data, HttpServletRequest request) {
		try {
			JSONObject msg = evaluationService.addevaluationcommittee(data,request);
			return new ResponseEntity<JSONObject>(msg, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<JSONObject>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping(value="submit-evaluation-all-marks")
	public ResponseEntity<JSONObject> submitEvaluationAllMarks(@RequestBody JSONObject data) {
		
		try {
			
			JSONObject res = evaluationService.submitEvaluationAllMarks(data);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(value = "/committeeview/tendrID/{tendrID}")
	public ResponseEntity <JSONObject> committeeview(HttpServletRequest request, @PathVariable Long tendrID) {
		  try {
		  
			  JSONObject res = evaluationService.committeeview(request, tendrID);
			  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
			  
		  } catch (Exception e)
		   { 
			  e.printStackTrace(); 
			  return null; 
			}
	}
	
	
	@GetMapping("/viewmarks") 
	public ResponseEntity<JSONObject> viewMarksheetsForEvaluator(HttpServletRequest request){
		try {

			JSONObject data=evaluationService.viewMarksheetsForEvaluator(request);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	
	@GetMapping("/viewcreatedSheets/{tender_ID}") 
	public ResponseEntity<JSONObject> viewEvaluationSheetDetails(HttpServletRequest request, @PathVariable  Long tender_ID){
		try {

			JSONObject data=evaluationService.viewEvaluationSheetDetails(request, tender_ID);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(value = "tenderid")
	public ResponseEntity<List<TenderDetails>> getViewForTenderDetails() {
		try {
			List<TenderDetails> td = evaluationService.getViewForTenderDetails();
			return new ResponseEntity<List<TenderDetails>>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	//Evaluation Sheet Edit - Tender No
	@GetMapping(value = "trnderId")
	public ResponseEntity<List<TenderDetails>> getViewEveluationEditSheet() {
		try {
			List<TenderDetails> td = evaluationService.getViewEveluationEditSheet();
			return new ResponseEntity<List<TenderDetails>>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	@GetMapping(value = "/editsheet/tendrID/{tendrID}")
	public ResponseEntity <JSONObject> editsheet(HttpServletRequest request, @PathVariable Long tendrID) {
		  try {
			  JSONObject res = evaluationService.editsheet(request, tendrID);
			  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
		  } catch (Exception e)
		   { 
			  e.printStackTrace(); 
			  return null; 
			}
	}
	
	
	
	@PostMapping(value = "/{selectedMarkSheetID}/editsheet")
	public ResponseEntity<JSONObject> confirmEditSheet(@PathVariable Long selectedMarkSheetID, @RequestBody JSONObject data) {
		
	System.out.println("bidno--"+selectedMarkSheetID);
	try {
		JSONObject res = evaluationService.confirmEditSheet(selectedMarkSheetID, data);
		return new ResponseEntity<JSONObject>(res, HttpStatus.OK);
	} catch (Exception e) {
		e.printStackTrace();
		return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
		
	
	@PostMapping(value = "/{selectedTID}/newcriteria")
	public ResponseEntity<JSONObject>addNewCriteria(@PathVariable Long selectedTID, @RequestBody JSONObject data) {
		
	System.out.println("bidno--"+selectedTID);
	try {
		JSONObject res = evaluationService.addNewCriteria(selectedTID, data);
		return new ResponseEntity<JSONObject>(res, HttpStatus.OK);
	} catch (Exception e) {
		e.printStackTrace();
		return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	// MIT Details - Tender No
	@GetMapping(value = "trnderID")
	public ResponseEntity<List<TenderDetails>> getMITDetailsForTenderDetails() {
		try {
			List<TenderDetails> td = evaluationService.getMITDetailsForTenderDetails();
			return new ResponseEntity<List<TenderDetails>>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@PostMapping(value="/enterMitDetails")
	public ResponseEntity<JSONObject>enterMitDetails(@RequestBody JSONObject data, HttpServletRequest request) {
		try {
			JSONObject msg = evaluationService.enterMitDetails(data,request);
			return new ResponseEntity<JSONObject>(msg, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<JSONObject>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//PDF Uploading
	@GetMapping(value = "mit-details-files/{id}/{fileName}")
	public  ResponseEntity<Resource> downloadMITDetailsFiles(HttpServletRequest request, @PathVariable String id,@PathVariable String fileName) {
		
		System.out.println("File id------------>"+id);
		System.out.println("File name------------>"+fileName);
	//	  File iFolder = new File(fileTenderFormatInPath); if (!iFolder.exists()) {
	//	  iFolder.mkdirs(); }
		 
		String fileNameWithExtension = fileName+".pdf";
		System.out.println("fileName With Extension------------>"+fileName);
	//	String filename = fileTenderFormatFileName;
		File file = new File(mitDetailsDataPath+"/"+id+"/"+fileName+".pdf");
		System.out.println("Link------------------>"+mitDetailsDataPath+id+"/"+fileName+".pdf");
		InputStreamResource resource = null;
		 
		
		 try {
			 resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 HttpHeaders h = new HttpHeaders();
		 h.add("Content-Disposition", "filename=" + fileName+".pdf");
		 
		    return ResponseEntity.ok()
		            .headers(h)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType("application/pdf"))
		            .body(resource);
	}
	
	
	// View MIT Details - Tender No
		@GetMapping(value = "trnderiD")
		public ResponseEntity<List<TenderDetails>> getViewMITDetailsForTenderDetails() {
			try {
				List<TenderDetails> td = evaluationService.getViewMITDetailsForTenderDetails();
				return new ResponseEntity<List<TenderDetails>>(td, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	
		
		@GetMapping(value="view-mit-details/mitID/{tendrID}")
		public ResponseEntity<JSONObject> getSupplierForAuth(HttpServletRequest req, @PathVariable Long tendrID) {
			try {
				JSONObject suppliers = evaluationService.viewMitDetails(req, tendrID);
				return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
		
		
		//PDF Uploading ---- View MIT
		@GetMapping(value = "download-MITdetails-view-files/{id}/{fileName}")
		public  ResponseEntity<Resource> downloadMITDetailsViewFiles(HttpServletRequest request, @PathVariable String id,@PathVariable String fileName) {
			
			System.out.println("File id------------>"+id);
			System.out.println("File name------------>"+fileName);
		//	  File iFolder = new File(fileTenderFormatInPath); if (!iFolder.exists()) {
		//	  iFolder.mkdirs(); }
			 
			String fileNameWithExtension = fileName+".pdf";
			System.out.println("fileName With Extension------------>"+fileName);
		//	String filename = fileTenderFormatFileName;
			File file = new File(mitDetailsDataPath+"/"+id+"/"+fileName+".pdf");
			System.out.println("Link------------------>"+mitDetailsDataPath+id+"/"+fileName+".pdf");
			InputStreamResource resource = null;
			 
			
			 try {
				 resource = new InputStreamResource(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
			 HttpHeaders h = new HttpHeaders();
			 h.add("Content-Disposition", "filename=" + fileName+".pdf");
			 
			    return ResponseEntity.ok()
			            .headers(h)
			            .contentLength(file.length())
			            .contentType(MediaType.parseMediaType("application/pdf"))
			            .body(resource);
		}
		
		
		// Authorize Evaluation - Tender No
		@GetMapping(value = "tenderID")
		public ResponseEntity<List<TenderDetails>> getTenderDetailsForAuthorize() {
			try {
				List<TenderDetails> td = evaluationService.getTenderDetailsForAuthorize();
				return new ResponseEntity<List<TenderDetails>>(td, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		
		@GetMapping(value = "/authorizeevaluation/tendrID/{tendrID}")
		public ResponseEntity <JSONObject> authorizeevaluation (HttpServletRequest request, @PathVariable Long tendrID) {
			  try {
				  JSONObject res = evaluationService.authorizeevaluation(request, tendrID);
				  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
			  } catch (Exception e)
			   { 
				  e.printStackTrace(); 
				  return null; 
				}
		}
		
		
		@GetMapping(value = "/authorizeevcommittee/tendrID/{tendrID}")
		public ResponseEntity <JSONObject> authorizeevcommittee (HttpServletRequest request, @PathVariable Long tendrID) {
			  try {
				  JSONObject res = evaluationService.authorizeevcommittee(request, tendrID);
				  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
			  } catch (Exception e)
			   { 
				  e.printStackTrace(); 
				  return null; 
				}
		}
		
		
		@PutMapping(value="add-authorize-comment/{parameter}")
		public ResponseEntity<JSONObject> addAuthorizeComment(@RequestBody JSONObject data, @PathVariable String parameter) {
			
			try {
				JSONObject res = evaluationService.addAuthorizeComment(data, parameter);
				  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}


	
	
}