package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.text.DateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserActivityLogDemoRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.InvoiceHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.UserActivityLog;
import lk.supplierUMS.SupplierUMS_REST.service.UserActivityLogService;

@Service
public class UserActivityLogServiceImpl implements UserActivityLogService{
	
	@Autowired
	UserActivityLogDemoRepo userActivityLogDemoRepo;

	@Override
	public void createLogEntry(String id, String roleid, HttpServletRequest request, String location, String action) {
		// TODO Auto-generated method stub
		System.out.println("Inside UserActivityLogServiceImpl "+ id);
		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
		System.out.println("CreationTime "+formatter.format(request.getSession().getCreationTime()));;
		System.out.println("LastAccessedTime "+formatter.format(request.getSession().getLastAccessedTime()));;
		
		 
		
		UserActivityLog userActivityLog = new UserActivityLog();
		//userActivityLog.setActUserBelongCompany(id);
		//userActivityLog.setActUserId(id);
		userActivityLog.setActUserRoleId(roleid);
		userActivityLog.setActUserLoggedIPAddress(request.getRemoteAddr());;
		userActivityLog.setActDate(new Date(request.getSession().getCreationTime()));		
		userActivityLog.setActDescription(action);
		userActivityLog.setActOption(location);
		userActivityLog.setLocalAddress(request.getLocalAddr().toString());
		userActivityLog.setRemoteAddress(request.getRemoteAddr());
		userActivityLog.setRemotePort(request.getRemotePort());
		userActivityLog.setLocalPort(request.getLocalPort());
		userActivityLog.setSessionID(request.getSession().getId());
		userActivityLog.setServerName(request.getServerName());
		userActivityLog.setRequestedURI(request.getRequestURI());
		
		
		
		System.out.println("Local Address "+request.getLocalAddr());
		System.out.println("Remote Address "+request.getRemoteAddr());
		System.out.println("getRemotePort "+request.getRemotePort());
		System.out.println("ServerName  "+request.getServerName());
		System.out.println("RequestURI  "+request.getRequestURI());
		System.out.println("RequestURI  "+request.getRequestURI());
		userActivityLog.setRemoteUserAllDetails(request.getHeader("User-Agent"));
		String[] getUserInfo = getUserInfo(request);
		System.out.println("getUserInfo "+getUserInfo[0]+" "+ getUserInfo[1]);
		
		userActivityLog.setRemoteOS(getUserInfo[0]);
		userActivityLog.setRemoteBrowser(getUserInfo[1]);
		
		userActivityLogDemoRepo.save(userActivityLog);
	}
	
	public String[] getUserInfo(HttpServletRequest request){
		String  browserDetails  =   request.getHeader("User-Agent");
	    String  userAgent       =   browserDetails;
	    String  user            =   userAgent.toLowerCase();

	    String os = "";
	    String browser = "";
	    String[] returnData = new String[2];

	    System.out.println("User Agent for the request is===>"+browserDetails);
	    //=================OS=======================
	     if (userAgent.toLowerCase().indexOf("windows") >= 0 )
	     {
	         os = "Windows";
	     } else if(userAgent.toLowerCase().indexOf("mac") >= 0)
	     {
	         os = "Mac";
	     } else if(userAgent.toLowerCase().indexOf("x11") >= 0)
	     {
	         os = "Unix";
	     } else if(userAgent.toLowerCase().indexOf("android") >= 0)
	     {
	         os = "Android";
	     } else if(userAgent.toLowerCase().indexOf("iphone") >= 0)
	     {
	         os = "IPhone";
	     }else{
	         os = "UnKnown, More-Info: "+userAgent;
	     }
	     //===============Browser===========================
	    if (user.contains("msie"))
	    {
	        String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
	        browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
	    } else if (user.contains("safari") && user.contains("version"))
	    {
	        browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
	    } else if ( user.contains("opr") || user.contains("opera"))
	    {
	        if(user.contains("opera"))
	            browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
	        else if(user.contains("opr"))
	            browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
	    } else if (user.contains("chrome"))
	    {
	        browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
	    } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
	    {
	        //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
	        browser = "Netscape-?";

	    } else if (user.contains("firefox"))
	    {
	        browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
	    } else if(user.contains("rv"))
	    {
	        browser="IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
	    } else
	    {
	        browser = "UnKnown, More-Info: "+userAgent;
	    }
	    System.out.println("Operating System======>"+os);
	    System.out.println("Browser Name==========>"+browser);
	    returnData[0] = os;
	    returnData[1] = browser;
		return returnData;
	}

	@Override
	public JSONObject getlogentry(Date fromDate, Date toDate, String type, String userid, String action,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		JSONObject data= new JSONObject();
		
		try {
			
			data.put("draw", request.getParameter("draw"));
			String sorting="";
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				 sorting = "actUserBelongCompany";
			    break;
			  case "1":
				sorting = "actUserId";
			    break;
			  case "2":
				sorting = "createdBy";
			    break;
			  case "3":
				  sorting = "createdAt";
				  break;
			  case "4":
				sorting = "actOption";
			    break;
			  case "5":
				sorting = "actDescription";
			    break;			  
			  case "6":
				  sorting = "actUserLoggedIPAddress";
				  break;			  
			  case "7":
				  sorting = "serverName";
				  break;			  
			  case "8":
				  sorting = "remoteOS";
				  break;			  
			  case "9":
				  sorting = "remoteBrowser";
				  break;			  
			  case "10":
				  sorting = "remoteUserAllDetails";
				  break;			  
			  case "11":
				  sorting = "remoteUserAllDetails";
				  break;			  
			}
			
			Page<UserActivityLog> userActivityLog = null;
			
			 if(userid.equals("All") && action.equals("All")){
					System.out.println("findAllActivtyLogByAllUsers");
					if (request.getParameter("order[0][dir]").equals("asc")) {
						userActivityLog = userActivityLogDemoRepo.findAllActivtyLogByAllUsers(fromDate, toDate, request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
					}else if(request.getParameter("order[0][dir]").equals("desc")){
						userActivityLog = userActivityLogDemoRepo.findAllActivtyLogByAllUsers(fromDate, toDate, request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
					}
			
			
				
			}else if(userid.equals("All")){
				System.out.println("findAllActivtyLogByUsers");
				if (request.getParameter("order[0][dir]").equals("asc")) {
					userActivityLog = userActivityLogDemoRepo.findAllActivtyLogByUsers(action, fromDate, toDate, request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else if(request.getParameter("order[0][dir]").equals("desc")){
					userActivityLog = userActivityLogDemoRepo.findAllActivtyLogByUsers(action, fromDate, toDate, request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
				
				
			}else if(action.equals("All")){
				System.out.println("findAllActivtyLog");
				if (request.getParameter("order[0][dir]").equals("asc")) {
					userActivityLog = userActivityLogDemoRepo.findAllActivtyLog(userid, fromDate, toDate, request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else if(request.getParameter("order[0][dir]").equals("desc")){
					userActivityLog = userActivityLogDemoRepo.findAllActivtyLog(userid, fromDate, toDate, request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
				
				
			}else if(userid.equals("All")){
				System.out.println("findGivenActivtyLogByAllUsers");
				if (request.getParameter("order[0][dir]").equals("asc")) {
					userActivityLog = userActivityLogDemoRepo.findGivenActivtyLogByAllUsers(action, fromDate, toDate, request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else if(request.getParameter("order[0][dir]").equals("desc")){
					userActivityLog = userActivityLogDemoRepo.findGivenActivtyLogByAllUsers(action, fromDate, toDate, request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
				
			}else{
				System.out.println("findActivtyLog");
				if (request.getParameter("order[0][dir]").equals("asc")) {
					userActivityLog = userActivityLogDemoRepo.findActivtyLog(userid, fromDate, toDate, action, request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else if(request.getParameter("order[0][dir]").equals("desc")){
					userActivityLog = userActivityLogDemoRepo.findActivtyLog(userid, fromDate, toDate, action, request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			}
			
			
			

			JSONArray ar = new JSONArray();
			for (UserActivityLog userActivityLogItem : userActivityLog.getContent()) {
				System.out.println("userActivityLogItem "+userActivityLogItem.getActUserId());
				JSONObject userActData = new JSONObject();
				
				userActData.put("company", userActivityLogItem.getActUserBelongCompany());
				userActData.put("userid", userActivityLogItem.getActUserId());
				userActData.put("username", userActivityLogItem.getCreatedBy());
				userActData.put("dateandtime", userActivityLogItem.getCreatedAt());
				userActData.put("area", userActivityLogItem.getActOption());
				userActData.put("description", userActivityLogItem.getActDescription());
				userActData.put("ipaddress", userActivityLogItem.getActUserLoggedIPAddress());
				userActData.put("servername", userActivityLogItem.getServerName());
				userActData.put("accessos", userActivityLogItem.getRemoteOS());
				userActData.put("accessbrowser", userActivityLogItem.getRemoteBrowser());			
				userActData.put("more", userActivityLogItem.getRemoteUserAllDetails());
				
				ar.add(userActData);
			}
			
			data.put("recordsTotal", userActivityLog.getTotalElements() );
			data.put("recordsFiltered", userActivityLog.getTotalElements());
			data.put("data", ar);
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		//Page<List<InvoiceHeader>> pageListInvoices = paymentBodyRepo.getPaymentSchedule(companyId, fromdate, fromdate2, PageRequest.of(page, size, direction, properties));
		
		
		return data;
		
		
		
		
	}
	
}


