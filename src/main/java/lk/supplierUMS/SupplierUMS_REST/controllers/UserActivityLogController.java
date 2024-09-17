package lk.supplierUMS.SupplierUMS_REST.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.supplierUMS.SupplierUMS_REST.service.UserActivityLogService;

@CrossOrigin
@RestController
@EntityListeners(AuditingEntityListener.class)
@RequestMapping(value = "useractivity/")
public class UserActivityLogController {
	
	@Autowired
	UserActivityLogService userActivityLogService;
	
	@GetMapping(value = "addlogentry/{id}/roleid/{roleid}/location/{location}/action/{action}")
	public ResponseEntity<Boolean> addActivityLogEntry(@PathVariable String id, HttpServletRequest request, 
			@PathVariable String location, @PathVariable String action,@PathVariable String roleid) {
		System.out.println("Inside addActivityLogEntryController "+ id);
		boolean data = false;
		userActivityLogService.createLogEntry(id, roleid, request, location, action);
		System.out.println(request.getLocalAddr()+" "+request.getSession().getCreationTime());
		 DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
		 System.out.println("CreationTime "+formatter.format(request.getSession().getCreationTime()));;
		 System.out.println("LastAccessedTime "+formatter.format(request.getSession().getLastAccessedTime()));;
		 System.out.println("Remote Port "+request.getRemotePort());;
		 System.out.println("Remote Host "+request.getRemoteHost());;
		 System.out.println("Remote Address "+request.getRemoteAddr());;
		 System.out.println("getRemotePort "+request.getRemotePort());;
		 System.out.println("Local Port "+request.getLocalPort());
		 System.out.println("RequestedSessionId "+request.getRequestedSessionId());
		 System.out.println("Locale "+request.getSession().getId());
		 
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println("currentPrincipalName "+currentPrincipalName);
		
		
		return new ResponseEntity<Boolean>(data,HttpStatus.OK);
		
	}
	
	@GetMapping(value = "getlogentry/from/{fromdate}/to/{todate}/type/{type}/user/{userid}/action/{action}")
	public ResponseEntity<JSONObject> getActivityLogs(@PathVariable String fromdate, @PathVariable String todate, 
			@PathVariable String type,@PathVariable String userid, @PathVariable String action, HttpServletRequest request) {
		
		JSONObject data = new JSONObject();
		Date fromDate=null;
		Date toDate=null;
		
		System.out.println("getlogentry "+ fromdate +" "+todate+" "+type+ " "+userid+" "+action);
		try {
			fromDate = new SimpleDateFormat("MM-dd-yyyy").parse(fromdate);		
			toDate = new SimpleDateFormat("MM-dd-yyyy").parse(todate);
			
			 data = userActivityLogService.getlogentry(fromDate, toDate, type, userid, action, request);
			// System.out.println("Inside getPaymentSchedule return data "+data.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
	}

}
