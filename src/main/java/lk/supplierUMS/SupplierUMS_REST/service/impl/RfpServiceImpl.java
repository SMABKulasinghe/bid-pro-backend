package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.URLConnection;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.exolab.castor.types.DateTime;
import org.hibernate.internal.build.AllowSysOut;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;

import ch.qos.logback.core.joran.conditional.IfAction;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.AppzTenderSubmissionsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.DepartmentDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EligibleCategoryRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EvaluationCommiteeRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ProcurementRequestRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RFPResponseRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpDetailsResponseRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpEvaluationCommiteeRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpEvaluationMarksRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpHeaderRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TenderDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRepo;
import lk.supplierUMS.SupplierUMS_REST.comman.Common;
import lk.supplierUMS.SupplierUMS_REST.comman.DeEnCode;
import lk.supplierUMS.SupplierUMS_REST.entity.Department;
import lk.supplierUMS.SupplierUMS_REST.entity.EPoHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationCommitee;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationMarksAll;
import lk.supplierUMS.SupplierUMS_REST.entity.ProcurementRequest;
import lk.supplierUMS.SupplierUMS_REST.entity.RFP;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetailsResponse;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPResponse;
import lk.supplierUMS.SupplierUMS_REST.entity.RfpEvaluationCommitee;
import lk.supplierUMS.SupplierUMS_REST.entity.RfpEvaluationMarks;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderStatusDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.service.RfpService;

@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class RfpServiceImpl implements RfpService {

	@Autowired
	RfpRepo rfpRepo;

	@Autowired
	RfpHeaderRepo rfpHeaderRepo;

	@Autowired
	RfpDetailsRepo rfpDetailsRepo;
	
	@Autowired
	RFPResponseRepo rfpResponseRepo;
	
	@Autowired
	DepartmentDetailsRepo departmentDetailsRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	EligibleCategoryRepo eligibleCategoryRepo;
	
	@Autowired
	ProcurementRequestRepo procurementRequestRepo;
	
	@Autowired
	TenderDetailsRepo tenderDetailsRepo;
	
	@Autowired
	AppzTenderSubmissionsRepo appzTenderSubmissionsRepo;
	
	@Autowired
	EvaluationCommiteeRepo evaluationCommiteeRepo;
	
	@Autowired
	RfpEvaluationCommiteeRepo rfpEvaluationCommiteeRepo;
	
	@Autowired
	RfpDetailsResponseRepo rfpDetailsResponseRepo;
	
	@Autowired
	RfpEvaluationMarksRepo rfpEvaluationMarksRepo;
	
	@Autowired
	SubCompanyRepo subCompanyRepo;
	
	@Autowired
	Common common;
	
	@Value("${rfpSubmit.doc.path}")
	String rfpSubmitDataPath;
	
	@Value("${rfpDownloads.doc.path}")
	String rfpDownloadRfp;

	@Override
	public String addRfp(JSONObject data, HttpServletRequest request) {
		RFP rfp = new RFP();
		ProcurementRequest proReq =  new ProcurementRequest();
		String msg = null;
		String status = "2";
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String userId = user.getUserid();
		Long deptID = user.getDeptID();
		
		try {

			/* rfp.setRfpNo(data.get("rfpNo").toString()); */
			rfp.setFileName(data.get("rfpFileName").toString());
			
			String eligibleCat = data.get("eligibleCat").toString();
			String proRequest = data.get("proRequest").toString();
			rfp.setEligibleCatId(new Long(eligibleCat).longValue());
			rfp.setEligibleSubCatId(Long.valueOf(data.get("eligibleSubCat").toString()));
			//proReq.setPrID(new Long(proRequest).longValue());
			
			rfp.setStatus(status);
			rfp.setCreatedDateTime(LocalDateTime.now());
			rfp.setCreatedUser(userId);
			rfp.setDeptId(deptID);
			rfp.setPrId(Long.valueOf(data.get("proRequest").toString()));
			rfp = rfpRepo.save(rfp);
			
			// R/IT/Cat/205/Date --> RFP CODE Format --> Issue after RFP created
			
			if(data.containsKey("upload_file")) {
				String headerData[]=data.get("upload_file").toString().split("base64,");
				String head=headerData[1].substring(0, (headerData[1].length()));
				String extention[]=data.get("upload_file").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				new DeEnCode().decodeMethod(rfpSubmitDataPath,rfp.getRfpID().toString(),"zip", head);
				rfp.setUploadedFile(rfpSubmitDataPath+rfp.getRfpID().toString()+".zip");
			}else {
				System.out.println("No File------------------->");
			}
			
			
			rfpRepo.save(rfp);
			
			Long rfpId = rfp.getRfpID();
			Long deptIdForFind = rfp.getDeptId();
			Long catIdForFind = rfp.getEligibleCatId();
			
			Optional<RFP> rfpIdForUser = rfpRepo.findById(rfpId);
			Optional<Department> deptIdForUser = departmentDetailsRepo.findById(deptIdForFind);
			Optional<EligibleCategory> catIdForUser = eligibleCategoryRepo.findById(catIdForFind);
			
			if (rfpIdForUser.isPresent()) {
				LocalDateTime createdDateTime = rfpIdForUser.get().getCreatedDateTime();
				int year = createdDateTime.getYear();
				
				//rfpIdForUser.get().setRfpNo("R/"+deptIdForUser.get().getDepartmentName()+"/"+catIdForUser.get().getEligibleCategortType()+"/"+rfpIdForUser.get().getRfpID()+"/"+year);
				rfpIdForUser.get().setRfpNo(catIdForUser.get().getEligibleCategortCode()+"/"+year+"/"+rfpIdForUser.get().getRfpID());			
				rfpIdForUser.get().setLogedUserId(user.getUserid());
				rfpRepo.save(rfpIdForUser.get());	
			}

			ArrayList<Object> rfpArray = (ArrayList<Object>) data.get("rfpArray");
			ArrayList<HashMap<String, String>> rfpArray2 = (ArrayList<HashMap<String, String>>) data.get("rfpArray");	

			for (Object rfps : rfpArray) {

				String headingName = (String) (((HashMap) rfps).get("headingNames"));
				String fieldName = (String) (((HashMap) rfps).get("fieldsNames"));
				
				RFPHeader rfpHeader = new RFPHeader();
				
				if(headingName != null) {
					rfpHeader.setRfpId(rfp.getRfpID());
					rfpHeader.setRfpHeaderName(headingName.trim());
					rfpHeaderRepo.save(rfpHeader);
				}
				if(fieldName != null) {
					String[] myAR = fieldName.split(",");
		            for (int i = 0; i < myAR.length; i++) {
		            	RFPDetails rfpDetails = new RFPDetails();
						System.out.println(myAR[i]);
						rfpDetails.setRfpDFiledNmae(myAR[i].trim());
						rfpDetails.setRfpHId(rfpHeader.getRfpHId());
						rfpDetails.setRfpId(rfpHeader.getRfpId()); 
						
						rfpDetailsRepo.save(rfpDetails);
					}
				}
			}
			Optional<RFP> rfpNo = rfpRepo.findById(rfpId);
			msg = rfpNo.get().getRfpNo();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msg = "Error";
		}
		return msg;
	}

	
	@Override
	public Boolean getWetherRfpExists(String enteredValue) {
		// TODO Auto-generated method stub
		//return rfpRepo.existsByTenderNo(enteredValue);
		return rfpRepo.existsByRfpNo(enteredValue);
	}

	@Override
	public JSONObject getRfpResponse(HttpServletRequest request) {
		try {
			
			JSONObject data = new JSONObject();
			Page<Object[]> rfp_responses_details = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			
			switch (request.getParameter("order[0][column]")) {
			case "1":
				sorting = "rfpNo";
				break;
			case "2":
				sorting = "createdDateTime";
				break;
			case "3":
				sorting = "fileName";
				break;
			case "4":
				sorting = "createdUser";
				break;
			case "5":
				sorting = "ec.eligibleCategortName";
				break;
			case "6":
				sorting = "dep.description";
				break;
			
			}
			
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				rfp_responses_details = rfpRepo.getRfpResponses(request.getParameter("search[value]"),						
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
				
				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				rfp_responses_details = rfpRepo.getRfpResponses(request.getParameter("search[value]"),						
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
//				contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			
			JSONArray ar = new JSONArray();
			long count = rfp_responses_details.getTotalElements();
			long n = 1;
			for (Object[] objects : rfp_responses_details.getContent()) {
				
				if (objects[0] instanceof RFP && objects[1] instanceof EligibleCategory && objects[2] instanceof Department) {
					
					JSONObject ob = new JSONObject();
					
					RFP rfp = (RFP) objects[0];
					EligibleCategory ec = (EligibleCategory) objects[1];
					Department dep = (Department) objects[2];
					
					LocalDateTime datetime = rfp.getCreatedDateTime();
					String datetimeval = String.valueOf(datetime);
					String[] parts = datetimeval.split("T");
					
					String date = parts[0];
					String time = parts[1];
					
					String dateTime = date+" "+time;
					
					ob.put("index", n);
					ob.put("rfpNumber", rfp.getRfpNo());
					ob.put("fileName", rfp.getFileName());
					ob.put("rfpID", rfp.getRfpID());
					ob.put("rfpCreatedDateTime", dateTime);
					ob.put("createdUser", rfp.getCreatedUser());
					ob.put("category", ec.getEligibleCategortName());
					ob.put("department", dep.getDescription());
					ob.put("prID", rfp.getPrId());
					ar.add(ob);
					n++;
				}
			}


			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			return data;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	@Override
	public JSONObject getRfpDetails(long id) {
		JSONObject data = new JSONObject();
		try {
			JSONArray ar = new JSONArray();
			List<Object[]> rfp = null;
			rfp = rfpHeaderRepo.getHeaderDetails(id);
			String arrDetails[];
			for (Object[] object : rfp) {
				if (object[0] instanceof RFPHeader && object[1] instanceof RFPDetails) {
					JSONObject ob = new JSONObject();
					RFPHeader rfpHeader = (RFPHeader) object[0];
					RFPDetails rfpDetails = (RFPDetails) object[1];
					
					ob.put("rfpHeaderName", rfpHeader.getRfpHeaderName());
					String headerName = rfpHeader.getRfpHeaderName();
					//System.out.println("Contains key " +(ob.containsKey(headerName)));
					//System.out.println("Contains key " + ob.containsKey(headerName));
					/*
					 * String rfpDetailss = null; if(rfpHeader.getRfpHId() ==
					 * rfpDetails.getRfpHId()) { System.out.println("rfp Details "+
					 * rfpDetails.getRfpDFiledNmae()); rfpDetailss = rfpDetails.getRfpDFiledNmae()+
					 * "," }
					 */
					ob.put("rfpHeaderId", rfpDetails.getRfpHId());  
					ob.put("rfpDetailsName", rfpDetails.getRfpDFiledNmae());
					
					ar.add(ob);
					
					}
				
			}
		//	stackItems(ar);
				
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			return data;
			
		}catch(Exception e) {
			e.printStackTrace();
			return data;
		}
	}
	
	/*
	 * public JSONObject stackItems(JSONArray ar) { for (Iterator iterator =
	 * ar.iterator(); iterator.hasNext();) { JSONObject object = (JSONObject)
	 * iterator.next();
	 * 
	 * }
	 * 
	 * 
	 * 
	 * return null;
	 * 
	 * }
	 */

	@Override
	public JSONObject RfpAuthorization(long id, String approve) {
		JSONObject ob = new JSONObject();
		try {
			String email1Approved = "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\r\n"
					+ "<head>\r\n"
					+ "<!--[if gte mso 9]>\r\n"
					+ "<xml>\r\n"
					+ "  <o:OfficeDocumentSettings>\r\n"
					+ "    <o:AllowPNG/>\r\n"
					+ "    <o:PixelsPerInch>96</o:PixelsPerInch>\r\n"
					+ "  </o:OfficeDocumentSettings>\r\n"
					+ "</xml>\r\n"
					+ "<![endif]-->\r\n"
					+ "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
					+ "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
					+ "  <meta name=\"x-apple-disable-message-reformatting\">\r\n"
					+ "  <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\r\n"
					+ "  <title></title>\r\n"
					+ "  \r\n"
					+ "    <style type=\"text/css\">\r\n"
					+ "      @media only screen and (min-width: 620px) {\r\n"
					+ "  .u-row {\r\n"
					+ "    width: 600px !important;\r\n"
					+ "  }\r\n"
					+ "  .u-row .u-col {\r\n"
					+ "    vertical-align: top;\r\n"
					+ "  }\r\n"
					+ "\r\n"
					+ "  .u-row .u-col-50 {\r\n"
					+ "    width: 300px !important;\r\n"
					+ "  }\r\n"
					+ "\r\n"
					+ "  .u-row .u-col-100 {\r\n"
					+ "    width: 600px !important;\r\n"
					+ "  }\r\n"
					+ "\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "@media (max-width: 620px) {\r\n"
					+ "  .u-row-container {\r\n"
					+ "    max-width: 100% !important;\r\n"
					+ "    padding-left: 0px !important;\r\n"
					+ "    padding-right: 0px !important;\r\n"
					+ "  }\r\n"
					+ "  .u-row .u-col {\r\n"
					+ "    min-width: 320px !important;\r\n"
					+ "    max-width: 100% !important;\r\n"
					+ "    display: block !important;\r\n"
					+ "  }\r\n"
					+ "  .u-row {\r\n"
					+ "    width: calc(100% - 40px) !important;\r\n"
					+ "  }\r\n"
					+ "  .u-col {\r\n"
					+ "    width: 100% !important;\r\n"
					+ "  }\r\n"
					+ "  .u-col > div {\r\n"
					+ "    margin: 0 auto;\r\n"
					+ "  }\r\n"
					+ "}\r\n"
					+ "body {\r\n"
					+ "  margin: 0;\r\n"
					+ "  padding: 0;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "table,\r\n"
					+ "tr,\r\n"
					+ "td {\r\n"
					+ "  vertical-align: top;\r\n"
					+ "  border-collapse: collapse;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "p {\r\n"
					+ "  margin: 0;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ ".ie-container table,\r\n"
					+ ".mso-container table {\r\n"
					+ "  table-layout: fixed;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "* {\r\n"
					+ "  line-height: inherit;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "a[x-apple-data-detectors='true'] {\r\n"
					+ "  color: inherit !important;\r\n"
					+ "  text-decoration: none !important;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "table, td { color: #000000; } </style>\r\n"
					+ "  \r\n"
					+ "  \r\n"
					+ "\r\n"
					+ "<!--[if !mso]><!--><link href=\"https://fonts.googleapis.com/css?family=Lato:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\r\n"
					+ "\r\n"
					+ "</head>\r\n"
					+ "\r\n"
					+ "<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f9f9f9;color: #000000\">\r\n"
					+ "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\r\n"
					+ "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\r\n"
					+ "  <table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f9f9f9;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
					+ "  <tbody>\r\n"
					+ "  <tr style=\"vertical-align: top\">\r\n"
					+ "    <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n"
					+ "    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f9f9f9;\"><![endif]-->\r\n"
					+ "    \r\n"
					+ "\r\n"
					+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: #f9f9f9\">\r\n"
					+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #f9f9f9;\">\r\n"
					+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
					+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #f9f9f9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #f9f9f9;\"><![endif]-->\r\n"
					+ "      \r\n"
					+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
					+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
					+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
					+ "  \r\n"
					+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
					+ "  <tbody>\r\n"
					+ "    <tr>\r\n"
					+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:15px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
					+ "        \r\n"
					+ "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #f9f9f9;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
					+ "    <tbody>\r\n"
					+ "      <tr style=\"vertical-align: top\">\r\n"
					+ "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
					+ "          <span>&#160;</span>\r\n"
					+ "        </td>\r\n"
					+ "      </tr>\r\n"
					+ "    </tbody>\r\n"
					+ "  </table>\r\n"
					+ "\r\n"
					+ "      </td>\r\n"
					+ "    </tr>\r\n"
					+ "  </tbody>\r\n"
					+ "</table>\r\n"
					+ "\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
					+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
					+ "    </div>\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
					+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n"
					+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
					+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n"
					+ "      \r\n"
					+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
					+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
					+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
					+ "  \r\n"
					+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
					+ "  <tbody>\r\n"
					+ "    <tr>\r\n"
					+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:25px 69px 25px 0px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
					+ "        \r\n"
					+ "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
					+ "  <tr>\r\n"
					+ "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n"
					+ "      \r\n"
					+ "      <img align=\"center\" border=\"0\" src=\"https://www.ndbbank.com/images/logo.svg\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 60%;max-width: 318.6px;\" width=\"318.6\"/>\r\n"
					+ "      \r\n"
					+ "    </td>\r\n"
					+ "  </tr>\r\n"
					+ "</table>\r\n"
					+ "\r\n"
					+ "      </td>\r\n"
					+ "    </tr>\r\n"
					+ "  </tbody>\r\n"
					+ "</table>\r\n"
					+ "\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
					+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
					+ "    </div>\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
					+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #161a39;\">\r\n"
					+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
					+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #161a39;\"><![endif]-->\r\n"
					+ "      \r\n"
					+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
					+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
					+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
					+ "  \r\n"
					+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
					+ "  <tbody>\r\n"
					+ "    <tr>\r\n"
					+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
					+ "        \r\n"
					+ "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
					+ "    <p style=\"font-size: 14px; line-height: 140%; text-align: center;\"> </p>\r\n"
					+ "<p style=\"font-size: 14px; line-height: 140%; text-align: center;\"><span style=\"font-size: 28px; line-height: 39.2px; color: #ffffff; font-family: Lato, sans-serif;\">RFP Approved</span></p>\r\n"
					+ "  </div>\r\n"
					+ "\r\n"
					+ "      </td>\r\n"
					+ "    </tr>\r\n"
					+ "  </tbody>\r\n"
					+ "</table>\r\n"
					+ "\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
					+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
					+ "    </div>\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
					+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n"
					+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
					+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n"
					+ "      \r\n"
					+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
					+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
					+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
					+ "  \r\n"
					+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
					+ "  <tbody>\r\n"
					+ "    <tr>\r\n"
					+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 40px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
					+ "        \r\n"
					+ "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n";
					//+ "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Hello, </span></p>";
			
			String email1Rejected = "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\r\n"
					+ "<head>\r\n"
					+ "<!--[if gte mso 9]>\r\n"
					+ "<xml>\r\n"
					+ "  <o:OfficeDocumentSettings>\r\n"
					+ "    <o:AllowPNG/>\r\n"
					+ "    <o:PixelsPerInch>96</o:PixelsPerInch>\r\n"
					+ "  </o:OfficeDocumentSettings>\r\n"
					+ "</xml>\r\n"
					+ "<![endif]-->\r\n"
					+ "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
					+ "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
					+ "  <meta name=\"x-apple-disable-message-reformatting\">\r\n"
					+ "  <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\r\n"
					+ "  <title></title>\r\n"
					+ "  \r\n"
					+ "    <style type=\"text/css\">\r\n"
					+ "      @media only screen and (min-width: 620px) {\r\n"
					+ "  .u-row {\r\n"
					+ "    width: 600px !important;\r\n"
					+ "  }\r\n"
					+ "  .u-row .u-col {\r\n"
					+ "    vertical-align: top;\r\n"
					+ "  }\r\n"
					+ "\r\n"
					+ "  .u-row .u-col-50 {\r\n"
					+ "    width: 300px !important;\r\n"
					+ "  }\r\n"
					+ "\r\n"
					+ "  .u-row .u-col-100 {\r\n"
					+ "    width: 600px !important;\r\n"
					+ "  }\r\n"
					+ "\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "@media (max-width: 620px) {\r\n"
					+ "  .u-row-container {\r\n"
					+ "    max-width: 100% !important;\r\n"
					+ "    padding-left: 0px !important;\r\n"
					+ "    padding-right: 0px !important;\r\n"
					+ "  }\r\n"
					+ "  .u-row .u-col {\r\n"
					+ "    min-width: 320px !important;\r\n"
					+ "    max-width: 100% !important;\r\n"
					+ "    display: block !important;\r\n"
					+ "  }\r\n"
					+ "  .u-row {\r\n"
					+ "    width: calc(100% - 40px) !important;\r\n"
					+ "  }\r\n"
					+ "  .u-col {\r\n"
					+ "    width: 100% !important;\r\n"
					+ "  }\r\n"
					+ "  .u-col > div {\r\n"
					+ "    margin: 0 auto;\r\n"
					+ "  }\r\n"
					+ "}\r\n"
					+ "body {\r\n"
					+ "  margin: 0;\r\n"
					+ "  padding: 0;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "table,\r\n"
					+ "tr,\r\n"
					+ "td {\r\n"
					+ "  vertical-align: top;\r\n"
					+ "  border-collapse: collapse;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "p {\r\n"
					+ "  margin: 0;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ ".ie-container table,\r\n"
					+ ".mso-container table {\r\n"
					+ "  table-layout: fixed;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "* {\r\n"
					+ "  line-height: inherit;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "a[x-apple-data-detectors='true'] {\r\n"
					+ "  color: inherit !important;\r\n"
					+ "  text-decoration: none !important;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "table, td { color: #000000; } </style>\r\n"
					+ "  \r\n"
					+ "  \r\n"
					+ "\r\n"
					+ "<!--[if !mso]><!--><link href=\"https://fonts.googleapis.com/css?family=Lato:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\r\n"
					+ "\r\n"
					+ "</head>\r\n"
					+ "\r\n"
					+ "<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f9f9f9;color: #000000\">\r\n"
					+ "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\r\n"
					+ "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\r\n"
					+ "  <table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f9f9f9;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
					+ "  <tbody>\r\n"
					+ "  <tr style=\"vertical-align: top\">\r\n"
					+ "    <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n"
					+ "    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f9f9f9;\"><![endif]-->\r\n"
					+ "    \r\n"
					+ "\r\n"
					+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: #f9f9f9\">\r\n"
					+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #f9f9f9;\">\r\n"
					+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
					+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #f9f9f9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #f9f9f9;\"><![endif]-->\r\n"
					+ "      \r\n"
					+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
					+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
					+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
					+ "  \r\n"
					+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
					+ "  <tbody>\r\n"
					+ "    <tr>\r\n"
					+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:15px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
					+ "        \r\n"
					+ "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #f9f9f9;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
					+ "    <tbody>\r\n"
					+ "      <tr style=\"vertical-align: top\">\r\n"
					+ "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
					+ "          <span>&#160;</span>\r\n"
					+ "        </td>\r\n"
					+ "      </tr>\r\n"
					+ "    </tbody>\r\n"
					+ "  </table>\r\n"
					+ "\r\n"
					+ "      </td>\r\n"
					+ "    </tr>\r\n"
					+ "  </tbody>\r\n"
					+ "</table>\r\n"
					+ "\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
					+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
					+ "    </div>\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
					+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n"
					+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
					+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n"
					+ "      \r\n"
					+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
					+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
					+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
					+ "  \r\n"
					+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
					+ "  <tbody>\r\n"
					+ "    <tr>\r\n"
					+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:25px 69px 25px 0px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
					+ "        \r\n"
					+ "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
					+ "  <tr>\r\n"
					+ "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n"
					+ "      \r\n"
					+ "      <img align=\"center\" border=\"0\" src=\"https://www.ndbbank.com/images/logo.svg\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 60%;max-width: 318.6px;\" width=\"318.6\"/>\r\n"
					+ "      \r\n"
					+ "    </td>\r\n"
					+ "  </tr>\r\n"
					+ "</table>\r\n"
					+ "\r\n"
					+ "      </td>\r\n"
					+ "    </tr>\r\n"
					+ "  </tbody>\r\n"
					+ "</table>\r\n"
					+ "\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
					+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
					+ "    </div>\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
					+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #161a39;\">\r\n"
					+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
					+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #161a39;\"><![endif]-->\r\n"
					+ "      \r\n"
					+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
					+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
					+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
					+ "  \r\n"
					+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
					+ "  <tbody>\r\n"
					+ "    <tr>\r\n"
					+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
					+ "        \r\n"
					+ "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
					+ "    <p style=\"font-size: 14px; line-height: 140%; text-align: center;\"> </p>\r\n"
					+ "<p style=\"font-size: 14px; line-height: 140%; text-align: center;\"><span style=\"font-size: 28px; line-height: 39.2px; color: #ffffff; font-family: Lato, sans-serif;\">RFP Rejected</span></p>\r\n"
					+ "  </div>\r\n"
					+ "\r\n"
					+ "      </td>\r\n"
					+ "    </tr>\r\n"
					+ "  </tbody>\r\n"
					+ "</table>\r\n"
					+ "\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
					+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
					+ "    </div>\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
					+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n"
					+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
					+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n"
					+ "      \r\n"
					+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
					+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
					+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
					+ "  \r\n"
					+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
					+ "  <tbody>\r\n"
					+ "    <tr>\r\n"
					+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 40px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
					+ "        \r\n"
					+ "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n";
			
			String email2 = "</div>\r\n"
					+ "\r\n"
					+ "      </td>\r\n"
					+ "    </tr>\r\n"
					+ "  </tbody>\r\n"
					+ "</table>\r\n"
					+ "\r\n"
					+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
					+ "  <tbody>\r\n"
					+ "    <tr>\r\n"
					+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 40px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
					+ "        \r\n"
					+ "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
					+ "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #888888; font-size: 14px; line-height: 19.6px;\"><em><span style=\"font-size: 16px; line-height: 22.4px;\">Don't reply to this email.</span></em></span><br /><span style=\"color: #888888; font-size: 14px; line-height: 19.6px;\"><em><span style=\"font-size: 16px; line-height: 22.4px;\"> </span></em></span></p>\r\n"
					+ "  </div>\r\n"
					+ "\r\n"
					+ "      </td>\r\n"
					+ "    </tr>\r\n"
					+ "  </tbody>\r\n"
					+ "</table>\r\n"
					+ "\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
					+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
					+ "    </div>\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
					+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #18163a;\">\r\n"
					+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
					+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #18163a;\"><![endif]-->\r\n"
					+ "      \r\n"
					+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 20px 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
					+ "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n"
					+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 20px 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
					+ "  \r\n"
					+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
					+ "  <tbody>\r\n"
					+ "    <tr>\r\n"
					+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
					+ "        \r\n"
					+ "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
					+ "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">National Development Bank PLC </span></p>\r\n"
					+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">40, Nawam Mawatha, Colombo 2, Sri Lanka</span></p>\r\n"
					+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">T: +94 11 244 8448 | Ext. 31152</span></p>\r\n"
					+ "  </div>\r\n"
					+ "\r\n"
					+ "      </td>\r\n"
					+ "    </tr>\r\n"
					+ "  </tbody>\r\n"
					+ "</table>\r\n"
					+ "\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
					+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px 0px 0px 20px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
					+ "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n"
					+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px 0px 0px 20px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
					+ "  \r\n"
					+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
					+ "  <tbody>\r\n"
					+ "    <tr>\r\n"
					+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:5px 10px 10px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
					+ "        \r\n"
					+ "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
					+ "    <p style=\"line-height: 140%; font-size: 14px;\"> </p>\r\n"
					+ "<p style=\"line-height: 140%; font-size: 14px;\"> </p>\r\n"
					+ "<p style=\"line-height: 140%; font-size: 14px;\"> </p>\r\n"
					+ "<p style=\"line-height: 140%; font-size: 14px;\"> </p>\r\n"
					+ "<p style=\"line-height: 140%; font-size: 14px;\"><span style=\"font-size: 14px; line-height: 19.6px;\"><span style=\"color: #ecf0f1; font-size: 14px; line-height: 19.6px;\"><span style=\"line-height: 19.6px; font-size: 14px;\">\r\n"
					+ "National Development Bank PLC © All Rights Reserved</span></span></span></p>\r\n"
					+ "  </div>\r\n"
					+ "\r\n"
					+ "      </td>\r\n"
					+ "    </tr>\r\n"
					+ "  </tbody>\r\n"
					+ "</table>\r\n"
					+ "\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
					+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
					+ "    </div>\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: #f9f9f9\">\r\n"
					+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #1c103b;\">\r\n"
					+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
					+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #f9f9f9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #1c103b;\"><![endif]-->\r\n"
					+ "      \r\n"
					+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
					+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
					+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
					+ "  \r\n"
					+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
					+ "  <tbody>\r\n"
					+ "    <tr>\r\n"
					+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:15px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
					+ "        \r\n"
					+ "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #1c103b;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
					+ "    <tbody>\r\n"
					+ "      <tr style=\"vertical-align: top\">\r\n"
					+ "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
					+ "          <span>&#160;</span>\r\n"
					+ "        </td>\r\n"
					+ "      </tr>\r\n"
					+ "    </tbody>\r\n"
					+ "  </table>\r\n"
					+ "\r\n"
					+ "      </td>\r\n"
					+ "    </tr>\r\n"
					+ "  </tbody>\r\n"
					+ "</table>\r\n"
					+ "\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
					+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
					+ "    </div>\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
					+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #f9f9f9;\">\r\n"
					+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
					+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #f9f9f9;\"><![endif]-->\r\n"
					+ "      \r\n"
					+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
					+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
					+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
					+ "  \r\n"
					+ "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
					+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
					+ "    </div>\r\n"
					+ "  </div>\r\n"
					+ "</div>\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "    <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n"
					+ "    </td>\r\n"
					+ "  </tr>\r\n"
					+ "  </tbody>\r\n"
					+ "  </table>\r\n"
					+ "  <!--[if mso]></div><![endif]-->\r\n"
					+ "  <!--[if IE]></div><![endif]-->\r\n"
					+ "</body>\r\n"
					+ "\r\n"
					+ "</html>";
			
			String statusApproved = "3";
			String statusRejected = "1";
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			Optional<RFP> rfp = rfpRepo.findById(id);
			
			String userId = rfp.get().getCreatedUser();	
			Optional<User> createdUserDetails = userRepo.findById(userId);
			
			String reciverName = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Dear "+createdUserDetails.get().getUsername()+",</span></p>";
			String date = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">"+LocalDate.now()+",</span></p>";
			String space = "<p style=\"font-size: 14px; line-height: 140%;\"> </p>";
			String rfpNumber = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">RFP Number: "+rfp.get().getRfpNo()+" </span></p>";
			String space2 = "<p style=\"font-size: 14px; line-height: 140%;\"> </p>";
			String rfpFileName = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">RFP File name: "+rfp.get().getFileName()+" </span></p>";
			String space3 = "<p style=\"font-size: 14px; line-height: 140%;\"> </p>";
			String descriptionApproved = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">The <strong>RFP file</strong> you prepared has been approved by <strong>"+user.getUsername()+"</strong> .</span></p>";
			String descriptionRejected = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">The <strong>RFP file</strong> you prepared has been rejected by <strong>"+user.getUsername()+"</strong> .</span></p>";
			String space4 = "<p style=\"font-size: 14px; line-height: 140%;\"> </p>";
			String thankyou = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Thank you</span></p>";
			if (rfp.isPresent()) {
				if (approve.equals("approve")) {
					
					//Email start
					String subject = "RFP Details";
					String body = email1Approved+reciverName+date+space+rfpNumber+space2+rfpFileName+space3+descriptionApproved+space4+thankyou+email2;
					
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							
								List<String> to = new ArrayList<String>();
								to.add(createdUserDetails.get().getEmail());
								
								common.sendEMailHtml(to, subject, body);
							}
						}).start();
					//Email end
					rfp.get().setLogedUserId(user.getUserid());
					rfp.get().setStatus(statusApproved);
					rfpRepo.save(rfp.get());
					
				}else {
					
					//Email start
					String subject = "RFP Details";
					String body = email1Rejected+reciverName+date+space+rfpNumber+space2+rfpFileName+space3+descriptionRejected+space4+thankyou+email2;
					
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							
								List<String> to = new ArrayList<String>();
								to.add(createdUserDetails.get().getEmail());
								
								common.sendEMailHtml(to, subject, body);
							}
						}).start();
					//Email end
					
					rfp.get().setLogedUserId(user.getUserid());
					rfp.get().setStatus(statusRejected);
					rfpRepo.save(rfp.get());
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return ob;
		}
		return ob;
	}

	@Override
	public JSONObject getRfpUpdate(HttpServletRequest request) {
		try {
				
				JSONObject data = new JSONObject();
				Page<Object[]> rfp_update_details = null;
				data.put("draw", request.getParameter("draw"));
				String sorting = "";
				
				switch (request.getParameter("order[0][column]")) {
				case "0":
					sorting = "rfpNo";
					break;
				case "1":
					sorting = "fileName";
					break;
				}
				
				if (request.getParameter("order[0][dir]").equals("asc")) {
					System.out.println("ASC ");
					rfp_update_details = rfpRepo.getRfpUpdates(request.getParameter("search[value]"),						
							PageRequest.of(
									Integer.parseInt(request.getParameter("start"))
											/ Integer.parseInt(request.getParameter("length")),
									Integer.parseInt(request.getParameter("length")),
									Sort.by(Sort.Direction.ASC, sorting)));
					
					System.out.println("end ");
				} else {
					System.out.println("DESC ");
					rfp_update_details = rfpRepo.getRfpUpdates(request.getParameter("search[value]"),						
							PageRequest.of(
									Integer.parseInt(request.getParameter("start"))
											/ Integer.parseInt(request.getParameter("length")),
									Integer.parseInt(request.getParameter("length")),
									Sort.by(Sort.Direction.DESC, sorting)));
	//				contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
				
				JSONArray ar = new JSONArray();
				long count = rfp_update_details.getTotalElements();
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				User user = (User) authentication.getPrincipal();
				Long CompanyCode = Long.valueOf(user.getCompanyCode());
				String UserId = user.getUserid().toString();
				
				for (Object[] Objects : rfp_update_details.getContent()) {
					if(Objects[1] instanceof TenderDetails && Objects[0] instanceof RFP) {
						TenderDetails td = (TenderDetails) Objects[1];
						RFP rfp = (RFP) Objects[0];
						JSONObject ob = new JSONObject();
						Long tenderId = (Long) null;
						String tenderIdString = null;
						Long rfpId = null;
						//String rfpIdString = null;
						
						ob.put("rfpNumber", rfp.getRfpNo());
						ob.put("tenderNo", td.getBidno());
						//ob.put("fileName", rfp.getFileName());
						ob.put("tenderDescription", td.getTenderdescription());
						ob.put("openingDate", td.getClosingdate());
						ob.put("closingTime", td.getClosingtime());
						ob.put("tenderId", td.getTrnderid());
						ob.put("rfpID", rfp.getRfpID());
						ob.put("fileName", rfp.getFileName());
						tenderId = td.getTrnderid();
						tenderIdString = Long.toString(tenderId);
						
						rfpId = rfp.getRfpID();
						//rfpIdString = Long.toString(rfpId);
						ob.put("tenderSubmissions", rfpResponseRepo.existsBytenderIdAndUserIdAndCompanyCodeAndRfpId(tenderIdString,UserId,CompanyCode,rfpId));
						ar.add(ob);
						
					}
	
				}
	
				data.put("recordsTotal", count);
				data.put("recordsFiltered", count);
				data.put("data", ar);
				System.out.println("data ds--->" + data);
				return data;
				
				
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public Boolean getWetherTenderExists(String enteredValue) {
		// TODO Auto-generated method stub
		//return rfpRepo.existsByTenderNo(enteredValue);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long CompanyCode = Long.valueOf(user.getCompanyCode());
		String UserId = user.getUserid().toString();
		
		return rfpResponseRepo.existsByTenderIdAndUserIdAndCompanyCode(enteredValue,UserId,CompanyCode);
		
		//return null;
	}

	@Override
	public String updateSubmitRfp(JSONObject data, HttpServletRequest request) {
		String msg = null;
		RFPResponse rfpResponses = new RFPResponse();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			if (data.containsKey("upload_doc")) {
				String[] RFPData = data.get("upload_doc").toString().split(",");
				byte[] RFPDataBytes = Base64.getDecoder().decode(RFPData[1]);
				String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(RFPDataBytes))
						.split("/")[1];
				FileUtils.writeByteArrayToFile(
						new File(rfpSubmitDataPath + data.get("tenderId").toString() + user.getCompanyCode().toString() + user.getUserid().toString() + "/uploadRpf." + mimeType),
						RFPDataBytes);
				rfpResponses
						.setRfpFile(rfpSubmitDataPath + data.get("tenderId").toString() + user.getCompanyCode().toString() + user.getUserid().toString() + "/uploadRpf." + mimeType);
			}
			//tenderDetails.setBidno(tender.get("bidno").toString());
			rfpResponses.setTenderId(data.get("tenderId").toString());
			rfpResponses.setRfpId(Long.valueOf(data.get("rfpId").toString()));		
			rfpResponses.setDescription(data.get("description").toString());
			rfpResponses.setCompanyCode(Long.valueOf(user.getCompanyCode()));
			rfpResponses.setUserId(user.getUserid());
			rfpResponses.setCreatedAt(LocalDate.now());
			rfpResponses.setStatus("Submitted");
			//System.out.println(rfpSubmitDataPath + data.get("tenderId").toString() + "/uploadRpf." );
			rfpResponseRepo.save(rfpResponses);
			
			msg = "Success";
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = "Error";
		}
		return msg;
	}

	@Override
	public JSONObject getRfpView(HttpServletRequest request) {
		try {
			
			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long CompanyCode = Long.valueOf(user.getCompanyCode());
			//Long CompCode = Long.valueOf(CompanyCode);
			String UserId = user.getUserid().toString();
			
			Page<Object[]> rfp_update_details = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			
			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "rfpNo";
				break;
			case "1":
				sorting = "td.bidno";
				break;
			case "2":
				sorting = "fileName";
				break;
			case "3":
				sorting = "td.tenderdescription";
				break;
			case "4":
				sorting = "td.closingdate";
				break;
			}
			
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				rfp_update_details = rfpRepo.getRfpView(request.getParameter("search[value]"),CompanyCode,						
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
				
				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				rfp_update_details = rfpRepo.getRfpView(request.getParameter("search[value]"),CompanyCode,						
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
//				contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			
			JSONArray ar = new JSONArray();
			long count = rfp_update_details.getTotalElements();
			//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			//User user = (User) authentication.getPrincipal();
			
			
			for (Object[] Objects : rfp_update_details.getContent()) {
				//System.out.println("all details ------>"+Objects);
				if(Objects[1] instanceof TenderDetails && Objects[0] instanceof RFP && Objects[2] instanceof RFPResponse) {
					TenderDetails td = (TenderDetails) Objects[1];
					RFP rfp = (RFP) Objects[0];
					RFPResponse rfpRes = (RFPResponse) Objects[2];
					JSONObject ob = new JSONObject();
					Long tenderId = (Long) null;
					String tenderIdString = null;
					Long rfpId = null;
					//String rfpIdString = null;
					
					ob.put("rfpNumber", rfp.getRfpNo());
					ob.put("tenderNo", td.getBidno());
					//ob.put("fileName", rfp.getFileName());
					ob.put("tenderDescription", td.getTenderdescription());
					ob.put("openingDate", td.getClosingdate());
					ob.put("closingTime", td.getClosingtime());
					ob.put("tenderId", td.getTrnderid());
					ob.put("rfpID", rfp.getRfpID());
					ob.put("fileName", rfp.getFileName());
					tenderId = td.getTrnderid();
					tenderIdString = Long.toString(tenderId);
					
					rfpId = rfp.getRfpID();
					//rfpIdString = Long.toString(rfpId);
					ob.put("tenderSubmissions", rfpResponseRepo.existsBytenderIdAndUserIdAndCompanyCodeAndRfpId(tenderIdString,UserId,CompanyCode,rfpId));
					ar.add(ob);
					
				}

			}

			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			return data;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject getDownloadRfpData(long id) {
		JSONObject data = new JSONObject();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long CompanyCode = Long.valueOf(user.getCompanyCode());
		String userId = user.getUserid();
		int n = 0;
		try {
			//JSONArray ar = new JSONArray();
			List<Object[]> rfp = null;
			rfp = rfpHeaderRepo.getHeaderDetails(id);
			CSVWriter write = new CSVWriter(new FileWriter(rfpDownloadRfp+"download"+id+userId+CompanyCode+".csv"));
			List rfpList = new ArrayList();
			for (Object[] object : rfp) {
				if (object[0] instanceof RFPHeader && object[1] instanceof RFPDetails) {
					RFPHeader rfpHeader = (RFPHeader) object[0];
					RFPDetails rfpDetails = (RFPDetails) object[1];
					
					String rfpHeaderName = rfpHeader.getRfpHeaderName();
					String rfpDetailsName = rfpDetails.getRfpDFiledNmae();
					//String dataSet[] = null;
					String dataSet[] = { ""+ ++n, rfpHeaderName , rfpDetailsName };
					rfpList.add(dataSet);
					
					}
				
			}
			String columns[] = {"Index","Header Name","Field Name","Y/N","Comment","Unit","Quantity","Price"};
			
			write.writeNext(columns);
			write.writeAll(rfpList);
			write.flush();
			write.close();
		//	stackItems(ar);
			String fileName = 	"download"+id+userId+CompanyCode+".csv";
			
			data.put("data", fileName);
			System.out.println("data ds--->" + data);
		
			return data;
			
		}catch(Exception e) {
			e.printStackTrace();
			return data;
		}
	}

	@Override
	public List<RFP> getFileName() {
		try {
			Long status = (long) 3;
			
			List<RFP> data = rfpRepo.findByStatus(status);
			
			return data;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RFP> getRFPForCategory(Long categoryID, Long subCatId) {
		// TODO Auto-generated method stub
		
		try {
			List<RFP> list = rfpRepo.findByEligibleCatIdAndStatusAndEligibleSubCatId(categoryID, "3",subCatId);
			if (list!= null) {
				return list;
				
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public JSONObject addRfpComment(JSONObject data) {
		JSONObject ob = new JSONObject();
		String msg = null;
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			System.out.println(data.get("rfpId").toString());
			Long rfpId = Long.valueOf(data.get("rfpId").toString());
			
			Optional<RFP> rfp = rfpRepo.findById(rfpId);
			if (rfp.isPresent()) {
				System.out.println("Add rfp comment");
				rfp.get().setComment(data.get("rfpComment").toString());
				rfpRepo.save(rfp.get());
					
			}
			ob.put("responseText", "Success");
		}catch(Exception e) {
			e.printStackTrace();
			
			return null;
		}
		return ob;
	}

	@Override
	public JSONObject getRfpViewForComputer(HttpServletRequest request) {
		try {
			
			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long CompanyCode = Long.valueOf(user.getCompanyCode());
			//Long CompCode = Long.valueOf(CompanyCode);
			String UserId = user.getUserid().toString();
			
			Page<RFP> rfp_update_details = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			
			switch (request.getParameter("order[0][column]")) {
			case "1":
				sorting = "rfpNo";
				break;
			case "2":
				sorting = "fileName";
				break;
			case "3":
				sorting = "createdDateTime";
				break;
			case "4":
				sorting = "createdUser";
				break;
			}
			
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				rfp_update_details = rfpRepo.getRfpViewForComputer(request.getParameter("search[value]"),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
				
				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				rfp_update_details = rfpRepo.getRfpViewForComputer(request.getParameter("search[value]"),				
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
//				contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			
			JSONArray ar = new JSONArray();
			long count = rfp_update_details.getTotalElements();
			//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			//User user = (User) authentication.getPrincipal();
			
			int n = 1;
			for (RFP Objects : rfp_update_details.getContent()) {
				//System.out.println("all details ------>"+Objects);
				
					
					JSONObject ob = new JSONObject();
					
					
					LocalDateTime datetime = Objects.getCreatedDateTime();
					String datetimeval = String.valueOf(datetime);
					String[] parts = datetimeval.split("T");
					
					String date = parts[0];
					String time = parts[1];
					
					String dateTime = date+" "+time;
					
					ob.put("index", n);
					ob.put("rfpNumber", Objects.getRfpNo());
					ob.put("fileName", Objects.getFileName());
					ob.put("createdDateTime", dateTime);
					ob.put("createdUser", Objects.getCreatedUser());
					ob.put("status", Objects.getStatus());
					ob.put("rfpId", Objects.getRfpID());
					
					
					//ob.put("tenderSubmissions", rfpResponseRepo.existsBytenderIdAndUserIdAndCompanyCodeAndRfpId(tenderIdString,UserId,CompanyCode,rfpId));
					ar.add(ob);
					n++;
				}

			

			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			return data;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public JSONObject procurementRequestDetails(JSONObject data) {		
		ProcurementRequest proRequest = new ProcurementRequest();
		
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			System.out.println("Long----- " + user.getCompanyCode());	
			
				proRequest.setStatus(Long.valueOf("4"));
			
				proRequest.setDepartment(data.get("department").toString());
				proRequest.setRequester(data.get("requester").toString());
				proRequest.setHeadApproval(data.get("proApproval").toString());
				proRequest.setName(data.get("proName").toString());
				proRequest.setDesignation(data.get("designation").toString());
				proRequest.setProjectCost(Long.valueOf(data.get("projectCost").toString()));
				proRequest.setTechnicalEvolutionTeam(data.get("technicalEvolutionTeam").toString());
						
				proRequest.setExistingSupplier(data.get("proSupplier").toString());
				proRequest.setExistingPrices(Long.valueOf(data.get("proPrices").toString()));
				proRequest.setBiddingPeriod(data.get("biddingPeriod").toString());
				proRequest.setTechnicalClarifications(data.get("clarifications").toString());
				proRequest.setPaymentTerms(data.get("paymentTerms").toString());
				proRequest.setQuotationValidity(data.get("quotation").toString());				
				proRequest.setExpectedValidity(data.get("deliveryPeriod").toString());
				proRequest.setWarrantyPeriod(data.get("warrantyPeriod").toString());
				proRequest.setOtherVendors(data.get("marketContact").toString());
				proRequest.setNotedConsider(data.get("importantNoted").toString());
				proRequest.setServiceCategory(data.get("serviceCategory").toString());
				
				proRequest.setBudgeted(data.get("budgeted").toString());
				proRequest.setExpenditure(data.get("expenditure").toString());
				proRequest.setType(data.get("type").toString());
				proRequest.setSampleRequirement(data.get("sample_equirement").toString());
				proRequest.setMeetingRequirement(data.get("bid_meeting").toString());				

				
				//LocalDateTime createdDATE = Instant.ofEpochMilli(new Long(data.get("proDate").toString())).atZone(ZoneId.systemDefault()).toLocalDateTime();
				//LocalDateTime createdDate = Instant.ofEpochMilli(new Long(data.get("purchaseDate").toString())).atZone(ZoneId.systemDefault()).toLocalDateTime();
			
				//LocalTime createdTime = LocalTime.parse("00:00:00");
				
//				 LocalDate createdDATE = Instant.ofEpochMilli(new Long(data.get("proDate").toString()))
//							.atZone(ZoneId.systemDefault()).toLocalDate();
				 LocalDate approveDate = Instant.ofEpochMilli(new Long(data.get("purchaseDate").toString()))
						 .atZone(ZoneId.systemDefault()).toLocalDate();
				
//				proRequest.setDate(createdDATE);
				proRequest.setApprovedDate(approveDate);
				proRequest = procurementRequestRepo.save(proRequest);
				
				JSONObject returnObj =  new JSONObject();
				returnObj.put("code", "00");
				returnObj.put("msg", "success");
				returnObj.put("prID", proRequest.getPrID());
				return returnObj;
					
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject returnObj =  new JSONObject();
			returnObj.put("code", "01");
			returnObj.put("msg", "failed");
			return returnObj= null;
		}					
	}

	
	@Override
	public JSONObject getAllTenderDetails(HttpServletRequest request, Long rfpId) {
		JSONObject data = new JSONObject();
		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			System.out.println("Long----- " + userCompanyID);

			Page<Object[]> dataList = null;

			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "trnderid";
				break;
			case "1":
				sorting = "bidno";
				break;
			case "2":
				sorting = "tendername";
				break;
			case "3":
				sorting = "tenderdescription";
				break;
			case "4":
				sorting = "closingdate";
				break;
			case "5":
				sorting = "esub.eligibleSubcatname";
				break;
			case "6":
				sorting = "tsd.tender_status_name";
				break;
				

			}

			if (request.getParameter("order[0][dir]").equals("asc")) {
				dataList = tenderDetailsRepo.getAllTenderDetails(request.getParameter("search[value]"),rfpId,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				dataList = tenderDetailsRepo.getAllTenderDetails(request.getParameter("search[value]"),rfpId,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
//				System.out.println("Size -------------"+dataList.size());
			for (Object[] tenderDetails : dataList.getContent()) {
				if (tenderDetails[0] instanceof TenderDetails 
						&& tenderDetails[1] instanceof RFP
						&& tenderDetails[2] instanceof EligibleCategory
						&& tenderDetails[3] instanceof EligibleSubCategory
						&& tenderDetails[4] instanceof TenderStatusDetails) {

					TenderDetails ph = (TenderDetails) tenderDetails[0];
					RFP rfp = (RFP) tenderDetails[1];
					EligibleCategory eligibleCategory = (EligibleCategory) tenderDetails[2];
					EligibleSubCategory eligiblesubcategory = (EligibleSubCategory) tenderDetails[3];
					TenderStatusDetails tenderstatusdetails = (TenderStatusDetails) tenderDetails[4];

					JSONObject header = new JSONObject();

					header.put("tenderNumber", ph.getTrnderid());
					header.put("Tender_status_name", tenderstatusdetails.getTender_status_name());
					header.put("bidno", ph.getBidno());
					header.put("tendername", ph.getTendername());
					header.put("tenderdescription", ph.getTenderdescription());
					//header.put("closingdate", ph.getClosingdate());

					// header.put("eligibleCategortID", ph.getEligibleCategortID());
					// header.put("eligibleSubcatId", ph.getEligibleSubcatId());
					// header.put("eligiblesubcatProdid", ph.getEligiblesubcatProdid());
					header.put("status", ph.getStatus());
					header.put("tenderID", ph.getTrnderid());
					
					header.put("rfp", rfp.getFileName());
					header.put("reason", ph.getApproveReason());
					header.put("created_date", ph.getCreatedAt());
					header.put("closingdate", ph.getClosingdate());
					header.put("closingtime", ph.getClosingtime());
					header.put("authorizedby", ph.getApprovedBy());
					header.put("authorizedate", ph.getApproveDateTime());


					header.put("cordinator1name", ph.getCordinator1name());
					header.put("cordinator1designation", ph.getCordinator1designation());
					header.put("cordinator1email", ph.getCordinator1email());
					header.put("cordinator1contactno", ph.getCordinator1contactno());

					header.put("cordinator2name", ph.getCordinator2name());
					header.put("cordinator2designation", ph.getCordinator2designation());
					header.put("cordinator2email", ph.getCordinator2email());
					header.put("cordinator2contactno", ph.getCordinator2contactno());

					header.put("CategortName", eligibleCategory.getEligibleCategortName());
					header.put("eligibleSubcatName", eligiblesubcategory.getEligibleSubcatname());
					// header.put("eligiblesubcatProdName",
					// eligiblesubcategoryproducts.getEligibleSubcatProdname());

					// header.put("reason", ph.getContractApprovalReason());
					// header.put("status", ph.getContractApprovalStatus());

					// header.put("renewalDate", ph.getRenewalDate());
					header.put("supportdoc1", ph.getSupportdoc1name());
					header.put("supportdoc2", ph.getSupportdoc2name());
					
					JSONArray view = new JSONArray();
					view.add(ph.getTrnderid());
					view.add(ph.getTrnderid());
					view.add(ph.getTrnderid());
					view.add(ph.getTrnderid());
					view.add(ph.getTrnderid());
					header.put("view", view);

					ar.add(header);
				}
			}

//			 Optional<Long> count  = poHeaderRepo.getPoDetailsSupplierCount(request.getParameter("search[value]"), code);
			data.put("recordsTotal", dataList.getTotalElements());
			data.put("recordsFiltered", dataList.getTotalElements());
			data.put("data", ar);
			System.out.println("ar " + ar);
			System.out.println(data);
			return data;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	public JSONObject viewProcurementDetails(long id) {
		JSONObject data = new JSONObject();
		try {
			
			JSONArray ar = new JSONArray();
			List<Object[]> rfp = null;
			rfp = procurementRequestRepo.getProcurementDetails(id);
			
			String arrDetails[];
			
			for (Object[] object : rfp) {
				if (object[0] instanceof ProcurementRequest && object[1] instanceof RFP && object[2] instanceof EligibleCategory) { 
					JSONObject ob = new JSONObject();
					ProcurementRequest proRequest = (ProcurementRequest) object[0];
					RFP rfpDetails = (RFP) object[1];
					EligibleCategory ecat = (EligibleCategory) object[2];
					
					ob.put("department", proRequest.getDepartment());
					ob.put("requester", proRequest.getRequester());		
					ob.put("approval", proRequest.getHeadApproval());		
					
					ob.put("datePro", proRequest.getCreatedAt());
					ob.put("purchaseDate", proRequest.getApprovedDate());	
					ob.put("evolutionTeam", proRequest.getTechnicalEvolutionTeam());
					ob.put("designation", proRequest.getDesignation());
					ob.put("name", proRequest.getName());		
					ob.put("projectCost", proRequest.getProjectCost());		
					ob.put("prices", proRequest.getExistingPrices());
					ob.put("biddingPeriod", proRequest.getBiddingPeriod());
					ob.put("deliveryPeriod", proRequest.getExpectedValidity());		
					ob.put("importantNoted", proRequest.getNotedConsider());		
					ob.put("supplier", proRequest.getExistingSupplier());
					ob.put("clarifications", proRequest.getTechnicalClarifications());
					ob.put("paymentTerms", proRequest.getPaymentTerms());		
					ob.put("quotationValidity", proRequest.getQuotationValidity());		
					ob.put("warrantyPeriod", proRequest.getWarrantyPeriod());
					ob.put("marketContact", proRequest.getOtherVendors());
					
					ob.put("serviceCategory", ecat.getEligibleCategortName());
					
					ob.put("budgeted", proRequest.getBudgeted());		
					ob.put("type", proRequest.getType());
					ob.put("sampleEquirement", proRequest.getSampleRequirement());	
					ob.put("bidMeeting", proRequest.getMeetingRequirement());	
					ob.put("expenditure", proRequest.getExpenditure());	
					ar.add(ob);
					}				
			}			
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			return data;			
		}catch(Exception e) {
			e.printStackTrace();
			return data;
		}
	}

	@Override
	public List<RFP> rfpIdForRevisedRfpSubmittion() {
		try {
			List<RFP> rfp = rfpRepo.rfpIdForRevisedRfpSubmittion();

			return rfp;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject getRfpDetailsForEdit(Long id) {
		JSONObject getRfpDetails = new JSONObject();
		try {
			
			JSONObject getRfpHeaderAndDetails = new JSONObject();
			
			Optional<RFP> rfp = rfpRepo.findById(id);
			getRfpDetails.put("rfpId", rfp.get().getRfpID());
			getRfpDetails.put("rfpNo", rfp.get().getRfpNo());
			getRfpDetails.put("fileName", rfp.get().getFileName());
			getRfpDetails.put("uploadedFile", rfp.get().getUploadedFile());
			getRfpDetails.put("prId", rfp.get().getPrId());
			getRfpDetails.put("eligibleSubCatId", rfp.get().getEligibleSubCatId());
			
			Optional<EligibleCategory> cat = eligibleCategoryRepo.findById(rfp.get().getEligibleCatId());
			getRfpDetails.put("categoryName", cat.get().getEligibleCategortName());
			getRfpDetails.put("catId", cat.get().getEligibleCategortID());
			
			List<RFPHeader> rfpHeader = rfpHeaderRepo.findByRfpId(id);
			
			
			//long tenderId=Long.parseLong(rfp.get().getTenderId());  
			//Optional<TenderDetails> tenderDetails = tenderDetailsRepo.findById(tenderId);
			
			
		} catch (Exception e) {
			e.printStackTrace(); 
			return null;
		}
		
		return getRfpDetails;
	}

	@Override
	public JSONObject getRfpDetailsForEditTable(HttpServletRequest request,long id) {
try {
			
			JSONObject data = new JSONObject();
			Page<Object[]> rfp_details = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			
			switch (request.getParameter("order[0][column]")) {
			case "1":
				sorting = "rfpHeaderName";
				break;
			case "2":
				sorting = "rfpD.rfpDFiledNmae";
				break;
			
			
			}
			
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				rfp_details = rfpRepo.getRfpDetailsForEditTable(request.getParameter("search[value]"),id,						
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
				
				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				rfp_details = rfpRepo.getRfpDetailsForEditTable(request.getParameter("search[value]"),id,						
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
//				contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			
			JSONArray ar = new JSONArray();
			long count = rfp_details.getTotalElements();
			long n = 1;
			for (Object[] objects : rfp_details.getContent()) {
				
				if (objects[0] instanceof RFPHeader && objects[1] instanceof RFPDetails) {
					
					JSONObject ob = new JSONObject();
					
					RFPHeader rfpH = (RFPHeader) objects[0];
					RFPDetails rfpD = (RFPDetails) objects[1];
					
					ob.put("index", n);
					ob.put("rfpHeaderName", rfpH.getRfpHeaderName());
					ob.put("rfpDetailsName", rfpD.getRfpDFiledNmae());
					
					ar.add(ob);
					n++;
				}
			}


			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			return data;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<TenderDetails> getTenderIdForRfpChangeId() {
		try {
			List<TenderDetails> TenderDetails = tenderDetailsRepo.getTenderIdForRfpChangeId();

			return TenderDetails;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String tenderRfpChange(JSONObject data, HttpServletRequest request) {
		String msg = null;
		try {
			long tenderId = Long.valueOf(data.get("tenderId").toString());
			Optional<TenderDetails> td = tenderDetailsRepo.findById(tenderId);
			
			if (td.isPresent()) {
				td.get().setRfpId(Long.valueOf(data.get("rfpId").toString()));
				tenderDetailsRepo.save(td.get());
			}
			
			
			msg = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error";
		}
		return null;
	}

	
	@Override
	public JSONObject getRfpForSelect(long tenderId) {
		JSONObject getRfpId = new JSONObject();
		try {
			
			Optional<TenderDetails> td = tenderDetailsRepo.findById(tenderId);
			Optional<RFP> rfp = rfpRepo.findById(td.get().getRfpId());
			
			getRfpId.put("rfpId", rfp.get().getRfpID());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getRfpId;
	}

	
	@Override
	public List<ProcurementRequest> getprID() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		try {
			List<ProcurementRequest> next = procurementRequestRepo.getprID();
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public JSONObject addRfpEveCommittee(JSONObject data, HttpServletRequest request) {
JSONObject returnData = new JSONObject();
		
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			Long tenderID = Long.valueOf(data.get("tenderID").toString());
			String committeemember = data.get("committeeMember").toString();
			
			Optional<List<TenderSubmissions>> optionalList = appzTenderSubmissionsRepo.findAllByTenderNoAndTenderResponse(Long.valueOf(data.get("tenderID").toString()), "8");
			if(optionalList.isPresent()) {
				System.out.println("Submited Vendors111111--> "+optionalList.get());
				List<TenderSubmissions> tenderSubmitList = optionalList.get();
				for (TenderSubmissions tenderSubmissions : tenderSubmitList) {
					System.out.println("Submited Vendors--> "+tenderSubmissions.getVendorId());
					
					
					
					Boolean evaCommite = rfpEvaluationCommiteeRepo.existsBySubmitedUserIDAndTenderIDAndSupplierID(
							committeemember, tenderID, tenderSubmissions.getSupplierId());
					System.out.println("IS ---- " + evaCommite);
					if (!evaCommite) { // create

						RfpEvaluationCommitee rfpEveCommittee = new RfpEvaluationCommitee();
						rfpEveCommittee.setTenderID(Long.valueOf(data.get("tenderID").toString()));
						rfpEveCommittee.setSubmitedUserID(committeemember);
						rfpEveCommittee.setSupplierID(tenderSubmissions.getSupplierId());
						rfpEveCommittee.setCreatedAt(LocalDateTime.now());
						rfpEveCommittee.setIsSubmited(false);
						rfpEveCommittee.setStatus(3);
						rfpEvaluationCommiteeRepo.save(rfpEveCommittee);

						List<RFPDetailsResponse> rfpDetailsResponses = rfpDetailsResponseRepo.findAllByTenderIDAndCompanyCode(tenderID,tenderSubmissions.getSupplierId());
						for (RFPDetailsResponse rfpDetailResponse : rfpDetailsResponses) {
							
							RfpEvaluationMarks rfpEvaMarks = new RfpEvaluationMarks();
							rfpEvaMarks.setCreatedUser(user.getUserid());
							rfpEvaMarks.setRfpDetailResId(rfpDetailResponse.getRfpDetailResId());
							rfpEvaMarks.setSupplierId(tenderSubmissions.getSupplierId());
							rfpEvaMarks.setTenderId(tenderID);
							rfpEvaMarks.setEvaluatorUserId(committeemember);
							rfpEvaMarks.setRfpID(rfpDetailResponse.getRfpID());
							rfpEvaMarks.setRfpHId(rfpDetailResponse.getRfpHId());
							rfpEvaMarks.setRfpDId(rfpDetailResponse.getRfpDId());
							rfpEvaluationMarksRepo.save(rfpEvaMarks);
						}
						
						returnData.put("msg", "Success"); 
						  returnData.put("code", "00");// return
						 // returnData;
						 

					} else { // msg = "already exisits" returnData.put("msg", "already exists");
						returnData.put("code", "01");
					//	return returnData;
					}
					
				}
				
				
				
			}else {
				
			}
			
			
			
		} catch (Exception e) {
		  e.printStackTrace();
		  return returnData;
		}
		return returnData;
	}


	@Override
	public JSONObject getTenderForRfpEvaComCreation() {
		LocalDateTime DateTime = LocalDateTime.now();
		JSONObject data = new JSONObject();
		try {
			List<Object[]> tenderDetails = rfpEvaluationCommiteeRepo.getTenderForRfpEvaComCreation(DateTime);
			JSONArray ar = new JSONArray();
			for (Object[] objects : tenderDetails) {
				if (objects[1] instanceof TenderDetails && objects[0] instanceof TenderSubmissions) {
					TenderSubmissions ts = (TenderSubmissions) objects[0];
					TenderDetails td = (TenderDetails) objects[1];
					
					JSONObject tenderIDs = new JSONObject();
					
					tenderIDs.put("tenderId", ts.getTenderNo());	
					tenderIDs.put("tenderName", td.getTendername());
					tenderIDs.put("tBidNumber", td.getBidno());
					
					ar.add(tenderIDs);
				}
			}
			data.put("data", ar);
			return data;
			//return tenderDetails;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public JSONObject rfpCommitteeView(HttpServletRequest request, Long tendrID) {
		try {
			JSONObject data= new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			
			switch (request.getParameter("order[0][column]")) {
		/*	  case "0":
				sorting = "index";
				break;*/
			  case "0":
				sorting = "submitedUserID";
			    break;
			  case "1":
				sorting = "tendername";
				break;
			  case "2":
				sorting = "bidno";
				break;
			  
			}
			Page<Object[]> evaCom= null;
			if (request.getParameter("order[0][dir]").equals("asc")) {
				evaCom =rfpEvaluationCommiteeRepo.getrfpCommitteeView(request.getParameter("search[value]"), tendrID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}
			else {
				evaCom =rfpEvaluationCommiteeRepo.getrfpCommitteeView(request.getParameter("search[value]"), tendrID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			JSONArray ar =  new JSONArray();
			int i =0;
			System.out.println("evaCom.getContent()"+evaCom.getContent().size());
			for (Object[] objects : evaCom.getContent()) {
				System.out.println("index--- "+i+" "+evaCom.getSize());
				if (objects[0] instanceof RfpEvaluationCommitee && objects[1] instanceof TenderDetails ) {
					RfpEvaluationCommitee evaCommittee = (RfpEvaluationCommitee) objects[0];
					TenderDetails tenderDetails = (TenderDetails) objects[1];
					
					JSONObject ob = new JSONObject();
					
					ob.put("index", ++i);
					ob.put("tenderNo",tenderDetails.getBidno());
					ob.put("committeMember", evaCommittee.getSubmitedUserID());
					ob.put("tenderID",tenderDetails.getTrnderid());
					ob.put("tenderName",tenderDetails.getTendername());
					
					ob.put("status",evaCommittee.getStatus());
					
					ar.add(ob);
					System.out.println("ob--- "+ob);
					System.out.println("index--- "+i+" "+tenderDetails.getBidno());
				}
			}
			 data.put("recordsTotal",evaCom.getTotalElements());
			 data.put("recordsFiltered",evaCom.getTotalElements());
			 data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public JSONObject getTenderForRfpEvaMaking() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		LocalDateTime DateTime = LocalDateTime.now();
		JSONObject data = new JSONObject();
		try {
			System.out.println("user id---"+user.getUserid());
			String userId= user.getUserid();
			List<Object[]> tenderDetails = rfpEvaluationCommiteeRepo.getTenderForRfpEvaMaking(userId);
			JSONArray ar = new JSONArray();
			for (Object[] objects : tenderDetails) {
				if (objects[1] instanceof TenderDetails && objects[0] instanceof RfpEvaluationCommitee) {
					RfpEvaluationCommitee ts = (RfpEvaluationCommitee) objects[0];
					TenderDetails td = (TenderDetails) objects[1];
					
					JSONObject tenderIDs = new JSONObject();
					
					tenderIDs.put("tenderId", ts.getTenderID());	
					tenderIDs.put("tenderName", td.getTendername());
					tenderIDs.put("tBidNumber", td.getBidno());
					
					ar.add(tenderIDs);
				}
			}
			data.put("data", ar);
			return data;
			//return tenderDetails;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public JSONObject rfpResponseDetails(HttpServletRequest request, Long tendrID, Long supplierID) {
		try {
			JSONObject data= new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			
			Page<Object[]> rfpResDetails= null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "rfph.rfpHeaderName";
				break;
			  case "1":
				sorting = "rfph.rfpHeaderName";
			    break;
			  case "2":
				sorting = "rfpd.rfpDFiledNmae";
				break;
			  
			  
			}
			
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("tendrID--1 "+tendrID);
				System.out.println("supplierID-- 1 "+supplierID);
				rfpResDetails =rfpDetailsResponseRepo.rfpResponseDetails(request.getParameter("search[value]"),tendrID,supplierID,user.getUserid(),			
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			}
			else {
				System.out.println("tendrID--2 "+tendrID);
				System.out.println("supplierID-- 2 "+supplierID);
				rfpResDetails =rfpDetailsResponseRepo.rfpResponseDetails(request.getParameter("search[value]"),tendrID,supplierID,user.getUserid(),							
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
			}
			JSONArray ar =  new JSONArray();
			int i =0;
			System.out.println("evaCom.getContent()"+rfpResDetails.getContent().size());
			for (Object[] objects : rfpResDetails.getContent()) {
				System.out.println("index--- "+i+" "+rfpResDetails.getSize());
				
				if (objects[0] instanceof RFPDetailsResponse && objects[1] instanceof RFPHeader && objects[2] instanceof RFPDetails && objects[3] instanceof RfpEvaluationMarks) {
					RFPDetailsResponse rr = (RFPDetailsResponse) objects[0];
					RFPHeader rfph = (RFPHeader) objects[1];
					RFPDetails rfpd = (RFPDetails) objects[2];
					//&& objects[3] instanceof RfpEvaluationMarks
					RfpEvaluationMarks rfpem = (RfpEvaluationMarks) objects[3];
					
					JSONObject ob = new JSONObject();
					
					ob.put("index", ++i);
					ob.put("headerName", rfph.getRfpHeaderName());
					ob.put("fieldName", rfpd.getRfpDFiledNmae());
					ob.put("venderResponse", rr.getVenderResponse());
					ob.put("venderComment", rr.getVenderComment());
					ob.put("price", rr.getPrice());
					ob.put("qty", rr.getQty());
					ob.put("unit", rr.getUnits());
					ob.put("rfpResId", rr.getRfpDetailResId());
					ob.put("givenMarks", rfpem.getMarks());
					ob.put("comment", rfpem.getComment());
					
					ar.add(ob);
					System.out.println("ob--- "+ob);
					//System.out.println("index--- "+i+" "+tenderDetails.getBidno());
				}
			}
			 data.put("recordsTotal",rfpResDetails.getTotalElements());
			 data.put("recordsFiltered",rfpResDetails.getTotalElements());
			 data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public String addRfpMarks(JSONObject data, HttpServletRequest request) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String userId = user.getUserid();
		
		
		
		try {
			
			ArrayList<Object> rfpArray = (ArrayList<Object>) data.get("rfpArray");
			for (Object rfps : rfpArray) {
				RfpEvaluationMarks rfpEvaMarks = new RfpEvaluationMarks();
				Long marks = Long.valueOf((String) (((HashMap) rfps).get("marks")));
				Long ids = Long.valueOf((String) (((HashMap) rfps).get("ids")));
				String comment =((String) (((HashMap) rfps).get("comment")));
				Long tenderId = Long.valueOf(data.get("tenderID").toString());
				Long supplerId = Long.valueOf(data.get("supplierList").toString());
				
				Optional<RfpEvaluationMarks> rfpMarks = rfpEvaluationMarksRepo.findByRfpDetailResIdAndTenderIdAndSupplierIdAndEvaluatorUserId(ids,tenderId,supplerId,userId);
				
				if (rfpMarks.isPresent()) {
					rfpMarks.get().setMarks(marks);
					rfpMarks.get().setMarksGivenByUserId(userId);
					rfpMarks.get().setComment(comment);
					rfpMarks.get().setCreatedDateTime(LocalDateTime.now());
					rfpEvaluationMarksRepo.save(rfpMarks.get());
				}
				/*
				 * if(marks != null) { rfpEvaMarks.setRfpDetailResId(ids);
				 * rfpEvaMarks.setMarks(marks);
				 * rfpEvaMarks.setTenderId(Long.valueOf(data.get("tenderID").toString()));
				 * rfpEvaMarks.setSupplierId(Long.valueOf(data.get("supplierList").toString()));
				 * rfpEvaMarks.setMarksGivenByUserId(userId);
				 * rfpEvaMarks.setCreatedUser(userId);
				 * rfpEvaMarks.setCreatedDateTime(LocalDateTime.now());
				 * rfpEvaluationMarksRepo.save(rfpEvaMarks); }
				 */
				
			}
			
			Optional<RfpEvaluationCommitee> rfpEveCom = rfpEvaluationCommiteeRepo.findByTenderIDAndSupplierIDAndSubmitedUserID(Long.valueOf(data.get("tenderID").toString()),Long.valueOf(data.get("supplierList").toString()),userId);
			rfpEveCom.get().setIsSubmited(true);
			rfpEvaluationCommiteeRepo.save(rfpEveCom.get());
			
			String msg = "00";
			return msg;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}


	@Override
	public JSONObject getRfpDetailsForSubmitButton(Long tender_ID, Long supplier_ID) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String userId = user.getUserid();
		
		JSONObject rfpDetailsForSubmitButton = new JSONObject();
		
		try {
			
			Optional<RfpEvaluationCommitee> rfpEvaCom = rfpEvaluationCommiteeRepo.findByTenderIDAndSupplierIDAndSubmitedUserID(tender_ID,supplier_ID,userId);
			rfpDetailsForSubmitButton.put("is_Submitted", rfpEvaCom.get().getIsSubmited());
			
			return rfpDetailsForSubmitButton;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
		
	}


	@Override
	public JSONObject getRfpEvaluationResults(long tenderId) {
		try {
			
			JSONObject data = new JSONObject();
			List<RfpEvaluationCommitee> recs = rfpEvaluationCommiteeRepo.findByTenderID(tenderId);
			Optional<TenderDetails> td = tenderDetailsRepo.findById(tenderId);
			Long rfpid = td.get().getRfpId();
			System.out.println("rfpid"+rfpid);
			
			/*List<RFPDetails> rfpdetails = rfpDetailsRepo.findByRfpId(rfpid);
			for (RFPDetails rfpdetail : rfpdetails) {
				List<RfpEvaluationMarks> rfpEvaMarks2 = rfpEvaluationMarksRepo.findByRfpDId(rfpdetail.getRfpDId());
				
				for (RfpEvaluationMarks rfpEvaMark2 : rfpEvaMarks2) {
					
					JSONObject tblDetails = new JSONObject();
					
					Optional<RFPHeader> header = rfpHeaderRepo.findById(rfpEvaMark2.getRfpHId());
					Optional<RFPDetails> details = rfpDetailsRepo.findById(rfpEvaMark2.getRfpDId());
					tblDetails.put("titleHeader", header.get().getRfpHeaderName());
					tblDetails.put("descriptionDetails", details.get().getRfpDFiledNmae());
					
					
				}
				
			}*/
			
			JSONArray ar = new JSONArray();
			List<Object[]> rfp = null;
			rfp = rfpHeaderRepo.getHeaderDetails(rfpid);
			String arrDetails[];
			for (Object[] object : rfp) {
				if (object[0] instanceof RFPHeader && object[1] instanceof RFPDetails) {
					
					JSONObject ob = new JSONObject();
					RFPHeader rfpHeader = (RFPHeader) object[0];
					RFPDetails rfpDetails = (RFPDetails) object[1];
					
					ob.put("rfpHeaderName", rfpHeader.getRfpHeaderName());
					String headerName = rfpHeader.getRfpHeaderName();
					
					ob.put("rfpHeaderId", rfpDetails.getRfpHId());  
					ob.put("rfpDetailsName", rfpDetails.getRfpDFiledNmae());
					
					JSONArray arrMarks = new JSONArray();
					List<RfpEvaluationMarks> rfpEvaMarks = rfpEvaluationMarksRepo.findByRfpDIdAndTenderId(rfpDetails.getRfpDId(),tenderId);
					for (RfpEvaluationMarks rfpEvaMark : rfpEvaMarks) {
						JSONObject marksDetails = new JSONObject();
						
						Optional<SubCompany> subCom = subCompanyRepo.findById(rfpEvaMark.getSupplierId());
						marksDetails.put("supplierName", subCom.get().getScname());
						marksDetails.put("evaluatorName", rfpEvaMark.getMarksGivenByUserId());
						marksDetails.put("mark", rfpEvaMark.getMarks());
						marksDetails.put("comment", rfpEvaMark.getComment());
						arrMarks.add(marksDetails);
					}
					
					ob.put("marksArray", arrMarks);
					
					ar.add(ob);
					
					}
				
			}
		//	stackItems(ar);
				
			data.put("dataOfRfp", ar);
			
			JSONArray ar1 = new JSONArray();
			for (RfpEvaluationCommitee rec : recs) {
				Long SupplierId = rec.getSupplierID();
				String evaluatorId = rec.getSubmitedUserID();
				
				Optional<SubCompany> subCom = subCompanyRepo.findById(SupplierId);
				
				JSONObject tableColumns = new JSONObject();
				tableColumns.put("column", evaluatorId+"/"+subCom.get().getScname());
				
				/*
				 * JSONArray ar2 = new JSONArray(); List<RfpEvaluationMarks> rfpEvaMarks =
				 * rfpEvaluationMarksRepo.findByTenderIdAndMarksGivenByUserIdAndSupplierId(
				 * tenderId,evaluatorId,SupplierId); for(RfpEvaluationMarks rfpEvaMark:
				 * rfpEvaMarks) {
				 * 
				 * JSONObject tableMarkRows = new JSONObject(); tableMarkRows.put("mark",
				 * rfpEvaMark.getMarks()); ar2.add(tableMarkRows);
				 * 
				 * }
				 * 
				 * tableColumns.put("marks", ar2);
				 */
				
				ar1.add(tableColumns);
				
			}
			
			data.put("columns", ar1);
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	
}
