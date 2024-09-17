package lk.supplierUMS.SupplierUMS_REST.service.impl;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.CompanyDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.CompanySupplierMappingRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRepo;
import lk.supplierUMS.SupplierUMS_REST.comman.Common;
import lk.supplierUMS.SupplierUMS_REST.comman.ConnectAPI;
import lk.supplierUMS.SupplierUMS_REST.entity.CompanyDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.CompanySupplierMapping;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;
import lk.supplierUMS.SupplierUMS_REST.service.CompanyDetailsService;


@Service
public class CompanyDetailsServiceImpl  implements  CompanyDetailsService{

	@Autowired
	CompanyDetailsRepo companydetailsrepo;
	
	@Autowired
	CompanySupplierMappingRepo companyMappingRepo;
	
	@Autowired
	SubCompanyRepo subCompanyRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	Common common;
	
	@Value("${company.data.path}")
	String companyDataPath;

	@Override
	public String addcompany(JSONObject companydata) {
		// TODO Auto-generated method stub
		System.out.println("Inside addcompany Service");
		CompanyDetails companydetails = new CompanyDetails();
		
		try{
			
//			FileUtils.writeByteArrayToFile(new File(companyDataPath+companydata.get("companycode").toString()+"-"+companydata.get("companyname").toString()+"companylogo.jpeg"), inputImage1DataBytes);
//			companydetails.setCcomapanylogo(companyDataPath+companydata.get("companycode").toString()+"-"+companydata.get("companyname").toString()+"companylogo.jpeg");
//			
//			FileUtils.writeByteArrayToFile(new File(companyDataPath+companydata.get("companycode").toString()+"-"+companydata.get("companyname").toString()+"registrationform.jpeg"), inputImage2DataBytes);
//			companydetails.setCcomapanyregform(companyDataPath+companydata.get("companycode").toString()+"-"+companydata.get("companyname").toString()+"registrationform.jpeg");
//			
//			FileUtils.writeByteArrayToFile(new File(companyDataPath+companydata.get("companycode").toString()+"-"+companydata.get("companyname").toString()+"addressproof.jpeg"), inputImage3DataBytes);
//			companydetails.setCcomapanyadressprofe(companyDataPath+companydata.get("companycode").toString()+"-"+companydata.get("companyname").toString()+"addressproof.jpeg");
			

			if (companydata.containsKey("inputImage1")) {
				String[] inputImage1Data = companydata.get("inputImage1").toString().split(",");
				byte[] inputImage1DataBytes = Base64.getDecoder().decode(inputImage1Data[1]);
				String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(inputImage1DataBytes)).split("/")[1];
				FileUtils.writeByteArrayToFile(new File(companyDataPath+companydata.get("companycode").toString()+"-"+companydata.get("companyname").toString()+"/companylogo."+mimeType), inputImage1DataBytes);
				companydetails.setCcomapanylogo(companyDataPath+companydata.get("companycode").toString()+"-"+companydata.get("companyname").toString()+"/companylogo."+mimeType);
			}
			if (companydata.containsKey("inputImage2")) {
				String[] inputImage2Data = companydata.get("inputImage2").toString().split(",");
				byte[] inputImage2DataBytes = Base64.getDecoder().decode(inputImage2Data[1]);
				String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(inputImage2DataBytes)).split("/")[1];
				FileUtils.writeByteArrayToFile(new File(companyDataPath+companydata.get("companycode").toString()+"-"+companydata.get("companyname").toString()+"/registrationform."+mimeType), inputImage2DataBytes);
				companydetails.setCcomapanyregform(companyDataPath+companydata.get("companycode").toString()+"-"+companydata.get("companyname").toString()+"/registrationform."+mimeType);
			}
			if (companydata.containsKey("inputImage3")) {
				String[] inputImage3Data = companydata.get("inputImage3").toString().split(",");
				byte[] inputImage3DataBytes = Base64.getDecoder().decode(inputImage3Data[1]);
				String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(inputImage3DataBytes)).split("/")[1];
				FileUtils.writeByteArrayToFile(new File(companyDataPath+companydata.get("companycode").toString()+"-"+companydata.get("companyname").toString()+"/addressproof."+mimeType), inputImage3DataBytes);
				companydetails.setCcomapanyadressprofe(companyDataPath+companydata.get("companycode").toString()+"-"+companydata.get("companyname").toString()+"/addressproof."+mimeType);
			}
			
			companydetails.setCcompanycode(companydata.get("companycode").toString());
			companydetails.setCname(companydata.get("companyname").toString());
			companydetails.setCtype(companydata.get("companytype").toString());
			companydetails.setCregistrationno(companydata.get("companyregistrationno").toString());
			companydetails.setCregdate(new Date(new Long(companydata.get("registerdatepicker").toString()))); 
			companydetails.setCphoneno1(companydata.get("companyphone1").toString());
			companydetails.setCphoneno2(companydata.get("companyphone2").toString());
			companydetails.setCphoneno3(companydata.get("companyphone3").toString());
			companydetails.setCbankaccountno(companydata.get("companybankaccountno").toString());
			companydetails.setCbankname(companydata.get("companybankname").toString());
			companydetails.setCbankbranchname(companydata.get("companybankbranchname").toString());
			companydetails.setCbankbranchcode(companydata.get("companybankbranchcode").toString());
			companydetails.setProvince(companydata.get("province").toString());
			companydetails.setDistrict(Long.valueOf(companydata.get("district").toString()));
			companydetails.setCity(Long.valueOf(companydata.get("city").toString()));
			companydetails.setCaddress1(companydata.get("companyaddressline1").toString());
			companydetails.setCaddress2(companydata.get("companyaddressline2").toString());
			companydetails.setCemailadmin(companydata.get("companyemailadmin").toString());
			companydetails.setCemailbusinesshead(companydata.get("companyemailbusinesshead").toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return companydetailsrepo.save(companydetails).getCcompanycode().toString();
	}

	@Override
	public JSONObject addPartnership(long company, long supplier) {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			CompanySupplierMapping part = new CompanySupplierMapping();
			
			part.setCompanyID(new Long(user.getCompanyCode()));
			part.setSupplierID(supplier);
			part.setCreatedDateTime(new Date());
			part.setCreater(user.getUserid());
			part.setStatus("P");
			
			companyMappingRepo.save(part);
			
			List<String> usersTokens = userRepo.getFCMUsersByRoleOption(supplier,"Approve Partnership");
			Optional<SubCompany> sub = subCompanyRepo.findById(new Long(user.getCompanyCode()));
			
			JSONObject data = new JSONObject();
			JSONObject body = new JSONObject();
			
			body.put("type", "Partnership Request");
			body.put("company", sub.get().getScname());
			data.put("data", body);
			data.put("registration_ids", usersTokens);
			HttpURLConnection dataConnection = new ConnectAPI().sendFCMRequest("https://fcm.googleapis.com/fcm/send", data);
			

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	public JSONObject getAllCompany(HttpServletRequest request) {
		// TODO Auto-generated method stub
		System.out.println("Inside getAllCompany Service");
		
		JSONObject data =new JSONObject();
		try{
			data.put("draw", request.getParameter("draw"));
			String sorting="";
			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "ccompanycode";
				break;
			case "1":
				sorting = "cname";
				break;
			case "2":
				sorting = "ccompanycode";
				break;
			case "3":
				sorting = "cregistrationno";
				break;
			case "4":
				sorting = "cregdate";
				break;
			case "5":
				sorting = "cphoneno1";
				break;
			case "6":
				sorting = "cemailadmin";
				break;
			}
			Page<CompanyDetails> dataList;
			if (request.getParameter("order[0][dir]").equals("asc")) {
				dataList =companydetailsrepo.getCompanyDetails(request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}else {
				dataList =companydetailsrepo.getCompanyDetails(request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			JSONArray ar = new JSONArray();
//			System.out.println("Size -------------"+dataList.size());
			 for (CompanyDetails companydetails : dataList.getContent()) {
				JSONObject dataset = new JSONObject();
			
				CompanyDetails ph = (CompanyDetails) companydetails;
				dataset.put("ccompanycode", ph.getCcompanycode());
				dataset.put("cname", ph.getCname());
				dataset.put("cregistrationno", ph.getCregistrationno());
				dataset.put("cregdate", ph.getCregdate());
				dataset.put("cphoneno1", ph.getCphoneno1());
				dataset.put("cemailadmin", ph.getCemailadmin());
				ar.add(dataset);
		
			}
			 data.put("recordsTotal",dataList.getTotalElements());
			 data.put("recordsFiltered",dataList.getTotalElements());
			 data.put("data", ar);
			
		}catch (Exception e){
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public JSONObject getCompanyList(String search, Long page) {

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			JSONObject ob = new JSONObject();
			
			int pagevalue;
			if (page == null) {
				pagevalue = 0;
			}else {
				pagevalue = page.intValue();
			}
			Page<SubCompany> suppliers = subCompanyRepo.getMappedSuppliers( search+"%", Long.valueOf(user.getCompanyCode()), PageRequest.of(pagevalue, 30));
			//long count = subCompanyRepo.countByScnameLikeAndIsPrivateFalse(search+"%");
			ob.put("total_count", suppliers.getTotalElements());
			if ((pagevalue*30)<suppliers.getTotalElements()) {
				ob.put("incomplete_results", true);
			}else {
				ob.put("incomplete_results", false);
			}
			ArrayList data = new ArrayList();
			for (SubCompany subCompany : suppliers.getContent()) {
				if (Long.valueOf(user.getCompanyCode()) != subCompany.getSubCompanyID()) {
					JSONObject sup = new JSONObject();
					sup.put("name", subCompany.getScname());
					sup.put("id", subCompany.getSubCompanyID());
					data.add(sup);
				}
				
			}
			ob.put("items", data);
			
			
			return ob;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	
		
		
		
	}

	@Override
	public SubCompany getCompanybyId(long id) {
		try {
			return subCompanyRepo.findById(id).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SubCompany authCompanybyId(long id, String status) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Optional<CompanyDetails> company = companydetailsrepo.findById(id);
			if (company.isPresent()) {
				if (status.equalsIgnoreCase("approve")) {
					
					CompanyDetails com = company.get();
					
					com.setStatus("A");
					com.setCapprovedid(user.getUserid());
					companydetailsrepo.saveAndFlush(com);
					
					SubCompany sc = new SubCompany();
					
					sc.setScompanycode(com.getCcompanycode());
					sc.setScname(com.getCname());
					sc.setScregistrationno(com.getCregistrationno());
					sc.setScregdate(com.getCregdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
					sc.setScphoneno1(com.getCphoneno1());
					sc.setScphoneno2(com.getCphoneno2());
					sc.setScphoneno3(com.getCphoneno3());
					sc.setScdistrict(com.getDistrict());
					sc.setSccity(com.getCity());
					sc.setScaddress1(com.getCaddress1());
					sc.setScaddress2(com.getCaddress2());
					sc.setScaddress3(com.getCaddress3());
					sc.setScemailadmin(com.getCemailadmin());
					sc.setScemailbusinesshead(com.getCemailbusinesshead());
					sc.setScemaillinemanager(com.getCemaillinemanager());
					sc.setScomapanylogo(com.getCcomapanylogo());
					sc.setScRegProof(com.getCcomapanyregform());
					sc.setScAddProof(com.getCcomapanyadressprofe());
					sc.setScstatus("Y");
					sc.setDateformat("yyyyMMdd");
					sc.setTimeformat("hh:mm:ss");
					sc.setIdentity(Boolean.FALSE);
					sc.setPrivate(Boolean.TRUE);
					sc.setScapprovedid(user.getUserid());
					
					subCompanyRepo.saveAndFlush(sc);
					
					User us = new ObjectMapper().readValue(new JSONObject().toJSONString(), User.class);

					us.setUserid(sc.getScname());
					us.setUsername(sc.getScname());
					us.setCompanyCode(String.valueOf(sc.getSubCompanyID()));
					us.setCreatedDateTime(new Date());
					us.setEmail(sc.getScemailadmin());
					LocalDate date = LocalDate.now().plusDays(10);
					us.setExpireDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
					us.setStatus(3);
					us.setStatusFlag("Y");
					
					UserRole ur = new UserRole();
					ur.setUserRoleID(17);
					
					us.setUserRoll(ur);
					
					 String generatedString = new Random().ints(97, 122 + 1)
						      .limit(10)
						      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
						      .toString();

					MessageDigest md = MessageDigest.getInstance("SHA-256");
					md.update(generatedString.getBytes());
					us.setPassowrd(new sun.misc.BASE64Encoder().encode(md.digest()));
					us = userRepo.saveAndFlush(us);
					
					
					String subject = "Welcome Bidpro Portal - Login Credentials";
					String body = "Welcome To Bidpro Portal"
							+ "\n"
							+ "\nUser Login ID  -: " + us.getUserid()
							+ "\nPassword -: "    + generatedString
							+ "\nURL -: https://bidpro.com:8443/supplierums/login"
							+ "\n\nPlease note that this account only active for 10 days. Furthermore you can create your own admin account using this account."
							+ "\nEnjoy your new experience with BidPro Portal System."
							+ "\n\nThank You!,"
							+ "\nTeam BidPro"
							+ "\n\nContact Us."
							+ "\nemail : bidpro.info@gmail.com"
							+ "\nPhone : 0711668739"
							+ "\nPlease do not reply to this email";
					
					 new Thread(new Runnable() {
						
						@Override
						public void run() {
						
							List<String> to = new ArrayList<String>();
							to.add(sc.getScemailadmin());
							
							common.sendEMail(to, subject, body);
						}
					}).start();
					
					
				}else {
					company.get().setStatus("R");
					companydetailsrepo.saveAndFlush(company.get());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject pendingAuthCompanies(HttpServletRequest request) {
		try {
			JSONObject data =new JSONObject();
			data.put("draw", request.getParameter("draw"));
			String sorting="";
			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "ccompanycode";
				break;
			case "1":
				sorting = "cname";
				break;
			case "2":
				sorting = "ccompanycode";
				break;
			case "3":
				sorting = "cregistrationno";
				break;
			case "4":
				sorting = "cregdate";
				break;
			case "5":
				sorting = "cphoneno1";
				break;
			case "6":
				sorting = "cemailadmin";
				break;
			}
			Page<CompanyDetails> dataList;
			if (request.getParameter("order[0][dir]").equals("asc")) {
				dataList =companydetailsrepo.getPendingAuthCompanyDetails(request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}else {
				dataList =companydetailsrepo.getPendingAuthCompanyDetails(request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			
			JSONArray ar = new JSONArray();
//			System.out.println("Size -------------"+dataList.size());
			 for (CompanyDetails companydetails : dataList.getContent()) {
				JSONObject dataset = new JSONObject();
			
				CompanyDetails ph = (CompanyDetails) companydetails;
				dataset.put("code", ph.getCcompanycode());
				dataset.put("name", ph.getCname());
				dataset.put("regNo", ph.getCregistrationno());
				dataset.put("contact", ph.getCphoneno1());
				dataset.put("email", ph.getCemailadmin());
				dataset.put("auth", ph.getCompanyID());
				ar.add(dataset);
		
			}
			 data.put("recordsTotal",dataList.getTotalElements());
			 data.put("recordsFiltered",dataList.getTotalElements());
			 data.put("data", ar);
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}




}
