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
import lk.supplierUMS.SupplierUMS_REST.service.QrCodeService;
import lk.supplierUMS.SupplierUMS_REST.service.TenderDetailsService;

import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;

@CrossOrigin
@RestController
@RequestMapping(value ="qr/")
public class QrCodeController {
	@Autowired
	QrCodeService qrCodeService;
	
	@GetMapping(value = "qr-code-table-details")
	public ResponseEntity <JSONObject> getQrCodeTableDetails(HttpServletRequest request) {
		
		  try {
		  
			  JSONObject res = qrCodeService.getQrCodeTableDetails(request);
			  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
			  
		  } catch (Exception e)
		   { 
			  e.printStackTrace(); 
			  return null; 
			}
	}
	
	@GetMapping(value="set-qr-status-to-present/{id}/{approve}")
	public ResponseEntity<JSONObject> setQrStatusToPresent(@PathVariable long id,@PathVariable String approve, HttpServletRequest request){
				try {
					
					JSONObject res = qrCodeService.setQrStatusToPresent(id,approve);
					
					return new ResponseEntity<JSONObject>(res, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
				}
		
	}
	
	@GetMapping(value="set-qr-status-to-present-using-qr/{id}/{approve}")
	public ResponseEntity<JSONObject> setQrStatusToPresentFromQr(@PathVariable long id,@PathVariable String approve, HttpServletRequest request){
				try {
					
					JSONObject res = qrCodeService.setQrStatusToPresentFromQr(id,approve);
					
					return new ResponseEntity<JSONObject>(res, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
				}
		
	}
	
	@GetMapping(value="send-email-and-generate-qr")
	public ResponseEntity<JSONObject> sendEmailAndGenerateQr(HttpServletRequest request){
				try {
					
					JSONObject res = qrCodeService.sendEmailAndGenerateQr(request);
					
					return new ResponseEntity<JSONObject>(res, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
				}
		
	}
	
	@GetMapping(value="get-qr-count")
	public ResponseEntity<JSONObject> getQrCount(HttpServletRequest request){
		try {
					
			JSONObject res = qrCodeService.getQrCount();
			return new ResponseEntity<JSONObject>(res, HttpStatus.OK);
					
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/*@Scheduled(cron = "${cron.expression}")
	public void closeTenderAutomatically() {
		try {
			
			List<TenderDetails> TenderDetail = tenderDetailsRepo.findAll();
			
			for (TenderDetails td : TenderDetail) {	
				
				System.out.println(td.getClosingDateTime());
				LocalDateTime nowDateTime = LocalDateTime.now();
				
				if(td.getClosingDateTime().isBefore(nowDateTime)) {
					td.setStatus("15");
					tenderDetailsRepo.saveAndFlush(td);
					
				}
				 
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}