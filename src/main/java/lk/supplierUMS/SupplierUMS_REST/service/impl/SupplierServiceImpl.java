package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Reader;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import lk.supplierUMS.SupplierUMS_REST.JPARepo.CompanyDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.CompanySupplierMappingRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EligibleCategoryRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.PglogRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SupplierProductDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SupplirDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRoleRepo;
import lk.supplierUMS.SupplierUMS_REST.comman.Common;
import lk.supplierUMS.SupplierUMS_REST.comman.CommonEmail;
import lk.supplierUMS.SupplierUMS_REST.comman.DeEnCode;
import lk.supplierUMS.SupplierUMS_REST.entity.City;
import lk.supplierUMS.SupplierUMS_REST.entity.CompanySupplierMapping;
import lk.supplierUMS.SupplierUMS_REST.entity.District;
import lk.supplierUMS.SupplierUMS_REST.entity.Province;
import lk.supplierUMS.SupplierUMS_REST.entity.RFP;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.SupplierDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SupplierProductDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;
import lk.supplierUMS.SupplierUMS_REST.service.CompanyDetailsService;
import lk.supplierUMS.SupplierUMS_REST.service.SupplierService;
import lk.supplierUMS.SupplierUMS_REST.service.UserService;

import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategoryProducts;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationSheetCreate;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialDetailsPerTender;
import lk.supplierUMS.SupplierUMS_REST.entity.GlobalDetails;


@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class SupplierServiceImpl implements SupplierService{

	@Value("${company.data.path}")
	String companyDataPath;
	
	@Value("${supplierregistration.doc.path}")
	String supplierRegistrationDataPath;
	
	@Autowired
	SubCompanyRepo subCompanyRepo;
	
	@Autowired
	CompanySupplierMappingRepo companyMappingRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CompanyDetailsService companyDetailsService;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	UserRoleRepo userRoleRepo;
	
	@Autowired
	Common common;
	
	@Autowired
	SupplirDetailsRepo supplirDetailsRepo;
	
	@Autowired
	SupplierProductDetailsRepo supplierProductDetailsRepo;
	
	@Autowired
	EligibleCategoryRepo eligibleCategoryRepo;
	
	@Autowired
	PglogRepo pglogRepo;
	
	
	@Override
	public JSONObject addSupplier(JSONObject data, String param) {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			
			
//			SupplierProductDetails supProductDetails = new SupplierProductDetails();
//			supProductDetails.setSupplierID(Long.valueOf(user.getCompanyCode().toString()));
			
//			user.setCompanyCode(data.get("companyCode").toString());
			
			
			SubCompany subComp = new SubCompany();
			subComp.setScprovince(Long.valueOf(data.get("province").toString()));
			subComp.setScdistrict(Long.valueOf(data.get("district").toString()));
			subComp.setSccity(Long.valueOf(data.get("city").toString()));
			subComp.setScaddress1(data.get("addressLine1").toString());
			subComp.setScaddress2(data.get("addressLine2").toString());
			subComp.setScaddress3(data.get("addressLine3").toString());
			
			subComp.setScname(data.get("supName").toString());
			subComp.setSccategoryType(data.get("supType").toString());
			subComp.setScphoneno1(data.get("supPhone1").toString());
			subComp.setScemailadmin(data.get("supMailAdmin").toString());
			subComp.setScname(data.get("RegNo").toString());
//			subComp.setIsAdditionalDataSubmited(true);
			
//			subComp.setSubCompanyID(Long.valueOf(user.getCompanyCode().toString()));
			
			
			SupplierDetails supDeatils = new SupplierDetails();
			supDeatils.setBankName(data.get("Bank").toString());
			supDeatils.setBankBranch(data.get("Branch").toString());
			supDeatils.setBankAddressLines(data.get("Address").toString());
			supDeatils.setPhoneNo1(data.get("Phone").toString());
			supDeatils.setOneTimeFee(data.get("oneTimeFeeLKR").toString());
			supDeatils.setCategoryFee(data.get("categoryFeeLKR").toString());
			supDeatils.setTotalPaymentDue(data.get("totalPaymentdue").toString());
			
			
			//supplier.setIdentity(false);
			//supplier.setScregdate(LocalDate.now());
			//supplier.setScdistrict(data.get("doYouAdaptedAGreenPolicy").toString());
			//supplier.setScemailadmin(data.get("doYouEmployPersonsUnderTheAgeOf18Years").toString());
			
		
			
			subComp.setScstatus("P");
			subComp.setPrivate(true);
			
			/*
			 * if(!param.equalsIgnoreCase("notSelf")){ subComp.setCreated("SuperUser");
			 * subComp.setUpdated("SuperUser"); }else{ if
			 * (data.get("regType").toString().equalsIgnoreCase("company_check")) {
			 * subComp.setCreated("self"); subComp.setUpdated("self");
			 * subComp.setIdentity(true); }else{ subComp.setCreated("self");
			 * subComp.setUpdated("self"); subComp.setIdentity(false); } }
			 */
			
//			Reader reader1 = null;
			
			//PDF Uploding
			if (data.containsKey("inputImage_logo")) {
				String[] logoData = data.get("inputImage_logo").toString().split(",");
				byte[] logodecodedBytes = Base64.getDecoder().decode(logoData[1]);
				String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(logodecodedBytes)).split("/")[1];
				FileUtils.writeByteArrayToFile(new File(companyDataPath+data.get("supplierID").toString()+"/logo."+mimeType), logodecodedBytes);
				supDeatils.setComapanyLogo(companyDataPath+data.get("supplierID").toString()+"/logo."+mimeType);
			}
			if (data.containsKey("inputImage_logo")) {
				String[] logoData = data.get("inputImage_logo").toString().split(",");
				byte[] logodecodedBytes = Base64.getDecoder().decode(logoData[1]);
				String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(logodecodedBytes)).split("/")[1];
				FileUtils.writeByteArrayToFile(new File(companyDataPath+data.get("supplierID").toString()+"/logo."+mimeType), logodecodedBytes);
				subComp.setScomapanylogo(companyDataPath+data.get("supplierID").toString()+"/logo."+mimeType);
			}
			
			
			if (data.containsKey("inputImage_Registration")) {
				String headerData[]=data.get("inputImage_Registration").toString().split("base64,");
				String extention[]=data.get("inputImage_Registration").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/registrationForm",exten, head);
				
				supDeatils.setRegitrationUploaded(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/registrationForm." + exten);
			}
			
			
			if (data.containsKey("inputImage_Address")) {
				String headerData[]=data.get("inputImage_Address").toString().split("base64,");
				String extention[]=data.get("inputImage_Address").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/addressProof",exten, head);
				
				supDeatils.setAddressProofUploaded(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/addressProof." + exten);
			}
			
			
			if (data.containsKey("input_GreenPolicy")) {
				String headerData[]=data.get("input_GreenPolicy").toString().split("base64,");
				String extention[]=data.get("input_GreenPolicy").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/GreenPolicy",exten, head);
				
				supDeatils.setGreenPolicy(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/GreenPolicy." + exten);
			}
			
			
			if (data.containsKey("input_ICTAD")) {
				String headerData[]=data.get("input_ICTAD").toString().split("base64,");
				String extention[]=data.get("input_ICTAD").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/iCTAD",exten, head);
				
				supDeatils.setIctadRegistration(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/iCTAD." + exten);
			}
			
			
			if (data.containsKey("input_KYC")) {
				String headerData[]=data.get("input_KYC").toString().split("base64,");
				String extention[]=data.get("input_KYC").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/kycForm",exten, head);
				
				supDeatils.setKycForm(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/kycForm." + exten);
			}
			
			
			if (data.containsKey("input_listOfServices")) {
				String headerData[]=data.get("input_listOfServices").toString().split("base64,");
				String extention[]=data.get("input_listOfServices").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/listOfServices",exten, head);
				
				supDeatils.setListOfServices(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/listOfServices." + exten);
			}
			
			
			if (data.containsKey("input_CompanyProfile")) {
				String headerData[]=data.get("input_CompanyProfile").toString().split("base64,");
				String extention[]=data.get("input_CompanyProfile").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/CompanyProfile",exten, head);
				
				supDeatils.setCompanyProfile(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/CompanyProfile." + exten);
			}
			
			
			if (data.containsKey("input_lastsixmonths")) {
				String headerData[]=data.get("input_lastsixmonths").toString().split("base64,");
				String extention[]=data.get("input_lastsixmonths").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/lastsixmonths",exten, head);
				
				supDeatils.setLastSixMonths(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/lastsixmonths." + exten);
			}
			
			
			if (data.containsKey("input_listoftopfifteenclients")) {
				String headerData[]=data.get("input_listoftopfifteenclients").toString().split("base64,");
				String extention[]=data.get("input_listoftopfifteenclients").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/listoftopfifteenclients",exten, head);
				
				supDeatils.setListofTopFifteenClients(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/listoftopfifteenclients." + exten);
			}
			
			
			if (data.containsKey("input_certificationOfIncorp")) {
				String headerData[]=data.get("input_certificationOfIncorp").toString().split("base64,");
				String extention[]=data.get("input_certificationOfIncorp").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/certificationOfIncorp",exten, head);
				
				supDeatils.setCertificationOfIncorp(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/certificationOfIncorp." + exten);
			}
			
			
			if (data.containsKey("input_listofdirectors")) {
				String headerData[]=data.get("input_listofdirectors").toString().split("base64,");
				String extention[]=data.get("input_listofdirectors").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/listofdirectors",exten, head);
				
				supDeatils.setListofDirectors(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/listofdirectors." + exten);
			}
			
			
			if (data.containsKey("input_articalofassociation")) {
				String headerData[]=data.get("input_articalofassociation").toString().split("base64,");
				String extention[]=data.get("input_articalofassociation").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/articalofassociation",exten, head);
				
				supDeatils.setArticalofAssociation(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/articalofassociation." + exten);
			}
			
			
			if (data.containsKey("input_lastAuditedFinAcc")) {
				String headerData[]=data.get("input_lastAuditedFinAcc").toString().split("base64,");
				String extention[]=data.get("input_lastAuditedFinAcc").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/lastAuditedFinAcc",exten, head);
				
				supDeatils.setLastAuditedFinAcc(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/lastAuditedFinAcc." + exten);
			}
		
			
			
//			supDeatils.setApprovedID("3");
//			supDeatils.setSupplierStatus("3");
			
			subCompanyRepo.save(subComp);
			supplirDetailsRepo.save(supDeatils);
//			supplierProductDetailsRepo.save(supProductDetails);
			
/*			reader1 = Files.newBufferedReader(
			Paths.get(supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/rfpFile" + ".csv"));
			CSVParser csvParserHead = new CSVParser(reader1,
					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());*/
			
			
			JSONObject returnObj =  new JSONObject();
			returnObj.put("code", "00");
			returnObj.put("msg", "success");
			
			return returnObj;
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject returnObj =  new JSONObject();
			returnObj.put("code", "01");
			returnObj.put("msg", "failed");
			return returnObj;
		}
		
	}
	
	


	@Override
	public JSONObject grtSupplierList(String search, Long page) {
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
			List<SubCompany> subComp = subCompanyRepo.findByScnameLikeAndIsPrivateFalse( search+"%",PageRequest.of(pagevalue, 30));
			long count = subCompanyRepo.countByScnameLikeAndIsPrivateFalse(search+"%");
			ob.put("total_count", count);
			if ((pagevalue*30)<count) {
				ob.put("incomplete_results", true);
			}else {
				ob.put("incomplete_results", false);
			}
			ArrayList data = new ArrayList();
			for (SubCompany subCompany : subComp) {
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
	public JSONObject grtSupplierById(long id) {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			Optional<SubCompany> subComp = subCompanyRepo.findById(id);
			
			Optional<CompanySupplierMapping> map = companyMappingRepo.findTopByCompanyIDAndSupplierID(new Long(user.getCompanyCode()), id);
			
			if (subComp.isPresent()) {
				JSONObject data = new JSONObject();
				data.put("name", subComp.get().getScname());
				data.put("add1", subComp.get().getScaddress1());
				data.put("add2", subComp.get().getScaddress2());
				data.put("add3", subComp.get().getScaddress3());
				data.put("code", subComp.get().getScompanycode());
				data.put("contact", subComp.get().getScphoneno1());
				data.put("email", subComp.get().getScemailadmin());
				data.put("logo", subComp.get().getScomapanylogo());		
				if (map.isPresent()) {
					data.put("status", map.get().getStatus());
					data.put("date", map.get().getCreatedDateTime());
				}else {
					data.put("status", "noRecord");
				}
				
				return data;
			}else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject grtSupplierList(HttpServletRequest request) {
		JSONObject data= new JSONObject();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			Page<Object[]> subComp= null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "com.scompanycode";
			    break;
			  case "1":
				sorting = "com.scname";
			    break;
			  case "2":
				sorting = "com.scregistrationno";
			    break;
			  case "3":
				sorting = "com.scemailadmin";
			    break;
			  case "4":
				sorting = "com.acceptedDateTime";
			    break;
			  case "5":
				sorting = "com.scprovince";
				break;
			}
			
			if (request.getParameter("order[0][dir]").equals("asc")) {
				subComp = companyMappingRepo.getAllSuppliersForCompanyView( Long.valueOf(user.getCompanyCode()),request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}else {
				subComp = companyMappingRepo.getAllSuppliersForCompanyView( Long.valueOf(user.getCompanyCode()),request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			
			
			
			JSONArray ar = new JSONArray();
			
			for (Object[] objects : subComp.getContent()) {
				
				if (objects[0] instanceof CompanySupplierMapping && objects[1] instanceof SubCompany) {
					
					JSONObject ob = new JSONObject();
					CompanySupplierMapping mapping = (CompanySupplierMapping) objects[0];
					SubCompany company = (SubCompany) objects[1];
					ob.put("companyCode", company.getScompanycode());
					ob.put("name", company.getScname());
					ob.put("regNumber", company.getScregistrationno());
					ob.put("email", company.getScemailadmin());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					ob.put("province", company.getScprovince());
					
					ob.put("partnershipDate",mapping.getAcceptedDateTime() == null?"": sdf.format(mapping.getAcceptedDateTime()));
					
					ar.add(ob);
					
			    }
				
			}
			data.put("recordsTotal",  subComp.getTotalElements());
			data.put("recordsFiltered", subComp.getTotalElements());
			data.put("data", ar);
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public JSONObject grtPartnerSupplierList(String search, Long page) {
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
			Page<Object[]> suppliers = companyMappingRepo.findByNamePartnershipSuppliers("A",search+"%",Long.valueOf(user.getCompanyCode()),PageRequest.of(pagevalue, 30));
//			long count = companyMappingRepo.countByScnameLikeAndIsPrivateFalse(search+"%");
			ob.put("total_count", suppliers.getTotalElements());
			if ((pagevalue*30)<suppliers.getTotalElements()) {
				ob.put("incomplete_results", true);
			}else {
				ob.put("incomplete_results", false);
			}
			ArrayList data = new ArrayList();
			for (Object[] obj : suppliers.getContent()) {
				JSONObject sup = new JSONObject();
				if (obj[0] instanceof CompanySupplierMapping && obj[1] instanceof SubCompany) {
					SubCompany com = (SubCompany) obj[1];
					sup.put("name", com.getScname());
					sup.put("id", com.getSubCompanyID());
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
	public JSONObject getSupplierUnAutherized(HttpServletRequest req) {
		try {
			JSONObject data= new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			data.put("draw", req.getParameter("draw"));
			String sorting = "";
			
			switch (req.getParameter("order[0][column]")) {
			  case "0":
				sorting = "scompanycode";
			    break;
			  case "1":
				sorting = "scname";
			    break;
			  case "2":
				sorting = "sccategoryType";
				break;
			  case "3":
				sorting = "scregistrationno";
			    break;
			  case "4":
				sorting = "scsystemregistereddate";
				break;
			  case "5":
				sorting = "scemailadmin";
			    break;
			  case "6":
				sorting = "scdistrict";
				break;
			  case "7":
				sorting = "sccity";
				break;
			}
			Page<Object[]> subComp= null;
			if (req.getParameter("order[0][dir]").equals("asc")) {
				subComp =subCompanyRepo.getCompanyForAutherization(req.getParameter("search[value]"),user.getUserid(), PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}
			else {
				subComp =subCompanyRepo.getCompanyForAutherization(req.getParameter("search[value]"),user.getUserid(), PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			JSONArray ar =  new JSONArray();
			int i =0;
			for (Object[] objects : subComp.getContent()) {
				
				if (objects[0] instanceof SubCompany && objects[1] instanceof Province && objects[2] instanceof District && objects[3] instanceof City && objects[4] instanceof SupplierDetails) {
					SubCompany subCompany = (SubCompany) objects[0];
					Province pro = (Province) objects[1];
					District dis = (District) objects[2];
					City cit = (City) objects[3];
					SupplierDetails supplierDetails = (SupplierDetails) objects[4];
					String companyType = "";
					switch(subCompany.getSccategoryType()){
					case "I":
						companyType = "Individual";
						break;
					case "SPP":
						companyType = "Sole Proprietor/Partnership";
						break;
					case "LLC":
						companyType = "Limited Liability Company";
					    break;
					case "PQC":
						companyType = "Public Quoted Company";
					    break;
					case "OC":
						companyType = "Offshore Company";
					    break;
					}
			
					JSONObject ob = new JSONObject();
					
					ob.put("index", ++i);
					ob.put("supplierID",supplierDetails.getSupplierID());
					ob.put("code", subCompany.getScompanycode());
					ob.put("supName", subCompany.getScname());
					ob.put("companyType", companyType);
					ob.put("RegNo", subCompany.getScregistrationno());
					ob.put("createdDateTime", subCompany.getScsystemregistereddate());
					ob.put("supMailAdmin", subCompany.getScemailadmin());
					ob.put("supPhone1", subCompany.getScphoneno1());
					ob.put("subCompanyID", subCompany.getSubCompanyID());
					
					ob.put("province", pro.getEnglishName());
					ob.put("district", dis.getEnglishName());
					ob.put("city", cit.getEnglishName());
					ob.put("address1", subCompany.getScaddress1());
					ob.put("address2", subCompany.getScaddress2());
					ob.put("address3", subCompany.getScaddress3());
					
					ob.put("onetimeFee", supplierDetails.getOneTimeFee());
					ob.put("bank", supplierDetails.getBankName());
					ob.put("branch", supplierDetails.getBankBranch());
					ob.put("accountNo", supplierDetails.getBankAcctNo());
					ob.put("bankCode", supplierDetails.getBankBranchCode());
										
					ob.put("name", subCompany.getScname1());
					ob.put("designation", subCompany.getScdesignation());
					ob.put("contactNo", subCompany.getSccontactno());		
					ob.put("email", subCompany.getScemail());
					ob.put("blockSupplierReason", subCompany.getBlockcomment());
					ob.put("unblockSupplierReason", subCompany.getUnblockcomment());  
					
					ob.put("view", supplierDetails.getSupplierID());
					ob.put("view", subCompany.getScname());
					
					ob.put("brForm", supplierDetails.getBrForm());
					ob.put("kycForm", supplierDetails.getKycForm());
					ob.put("bciForm", supplierDetails.getBciForm());
					ob.put("auditedReports", supplierDetails.getLastAuditedFinAcc());
					ob.put("directorDetails", supplierDetails.getListofDirectors());
					ob.put("CompanyProfile", supplierDetails.getCompanyProfile());
					ob.put("mainApplication", supplierDetails.getMainApplication());
					ob.put("supCodeofConduct", supplierDetails.getSupplierCodeofConduct());
					ob.put("supEsQuestions", supplierDetails.getSupplierESQuestions());
					
			//		ob.put("status",supplierDetails.getApprovedID());
					ob.put("status",subCompany.getScstatus());
					
					ar.add(ob);
					System.out.println("ob--- "+ob);
				}				
			}
			 data.put("recordsTotal",subComp.getTotalElements());
			 data.put("recordsFiltered",subComp.getTotalElements());
			 data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
	@Override
	public JSONObject SupplierApprove(long id, String approve) {
		JSONObject ob = new JSONObject();
		try {
			/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User us = (User) authentication.getPrincipal();
			*/
			Optional<SubCompany> sup = subCompanyRepo.findById(id);
//			Optional<SubCompany> com = subCompanyRepo.findById(Long.valueOf(us.getCompanyCode()));
		
//			Optional<SubCompany> com =	subCompanyRepo.findBysubCompanyID(Long.valueOf(data.get("subCompanyID").toString()));
			
			
			
			if (sup.isPresent()) {
				if (approve.equals("approve")) {
					
					sup.get().setScstatus("Y");
					sup.get().setIsAdditionalDataSubmited(false);
					subCompanyRepo.save(sup.get());
					
					User user = new ObjectMapper().readValue(new JSONObject().toJSONString(), User.class);

					user.setUserid(sup.get().getScname());
					user.setUsername(sup.get().getScname());
					user.setCompanyCode(String.valueOf(sup.get().getSubCompanyID()));
					user.setCreatedDateTime(new Date());
					user.setEmail(sup.get().getScemailadmin());
					LocalDate date = LocalDate.now().plusDays(10);
					user.setExpireDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
					user.setStatus(3);
					user.setStatusFlag("Y");
					user.setIsFirstLogin(false);
					
					UserRole ur = new UserRole();
					ur.setUserRoleID(17);
					
					user.setUserRoll(ur);
					
					 String generatedString = new Random().ints(97, 122 + 1)
						      .limit(10)
						      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
						      .toString();

					MessageDigest md = MessageDigest.getInstance("SHA-256");
					md.update(generatedString.getBytes());
					user.setPassowrd(new sun.misc.BASE64Encoder().encode(md.digest()));
					user = userRepo.saveAndFlush(user);
					
					// As business process changes -- Self Registration
					// Uncomment for when company creates and authorize suppliers
					
				//	companyDetailsService.addPartnership(Long.valueOf(us.getCompanyCode()), sup.get().getSubCompanyID());
					
					String subject = "Welcome BidPro Portal - Login Credentials";
					String body = "Welcome BidPro Portal"
							/*+ "\nYou have been introduced by " + com.get().getScname()*/
							+ "\n"
							+ "\nUser Login ID  -: " + user.getUserid()
							+ "\nPassword -: "    + generatedString
							+ "\nURL -: https://BidPro.com:8443/supplierums/login"
							+ "\n\nPlease note that this account only active for 10 days. Furthermore you can create your own admin account using this account"
							+ "\nEnjoy your new experience with BidPro Portal System."
							+ "\nThank You!,"
							+ "\nTeam BidPro"
							+ "\n\nContact Us."
							+ "\nemail : bidpro.info@gmail.com"
							+ "\nPhone : 0711668739"
							+ "\nPlease do not reply to this email";
					
					 new Thread(new Runnable() {
						
						@Override
						public void run() {
						
							List<String> to = new ArrayList<String>();
							to.add(sup.get().getScemailadmin());
							
							common.sendEMail(to, subject, body);
						}
					}).start();
					 ob.put("result", true);
					 ob.put("clientID", id);
					
				}else {
					sup.get().setScstatus("R");
					subCompanyRepo.save(sup.get());
					 ob.put("result", false);
					 ob.put("clientID", id);
					 
				}
			}
			 ob.put("code", "01");
			 ob.put("msg", "Success");
			
		} catch (Exception e) {
			e.printStackTrace();
			ob.put("code", "00");
			ob.put("msg", "failed");
			return ob;
		}
		return ob;
	}

	@Override
	public JSONObject getSuppliersForPayment(String search, Long page) {
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
			List<SubCompany> subComp = subCompanyRepo.findByScnameLike( search+"%",PageRequest.of(pagevalue, 30));
			long count = subCompanyRepo.countByScnameLikeAndIsPrivateFalse(search+"%");
			ob.put("total_count", count);
			if ((pagevalue*30)<count) {
				ob.put("incomplete_results", true);
			}else {
				ob.put("incomplete_results", false);
			}
			ArrayList data = new ArrayList();
			for (SubCompany subCompany : subComp) {
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
	public JSONObject getCompanyForAutherization(HttpServletRequest req) {
		try {
			JSONObject data= new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			data.put("draw", req.getParameter("draw"));
			String sorting = "";
			
			switch (req.getParameter("order[0][column]")) {
			  case "0":
				sorting = "scompanycode";
			    break;
			  case "1":
				sorting = "scname";
			    break;
			  case "2":
				sorting = "scregistrationno";
			    break;
			  case "3":
				sorting = "scemailadmin";
			    break;
			  case "4":
				sorting = "scphoneno1";
			    break;
			  case "5":
			    sorting = "scprovince";
				break;
			}
			Page<SubCompany> subComp= null;
			if (req.getParameter("order[0][dir]").equals("asc")) {
				subComp =subCompanyRepo.getCompanyForAutherizationD(req.getParameter("search[value]"),user.getUserid(), PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}
			else {
				subComp =subCompanyRepo.getCompanyForAutherizationD(req.getParameter("search[value]"),user.getUserid(), PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			JSONArray ar =  new JSONArray();
			for (SubCompany subCompany : subComp.getContent()) {
				JSONObject ob = new JSONObject();
				
				ob.put("code", subCompany.getScompanycode());
				ob.put("name", subCompany.getScname());
				ob.put("regNo", subCompany.getScregistrationno());
				ob.put("email", subCompany.getScemailadmin());
				ob.put("contact", subCompany.getScphoneno1());
				ob.put("province", subCompany.getScprovince());
				ob.put("district", subCompany.getScdistrict());
				ob.put("auth", subCompany.getSubCompanyID());
				
				ar.add(ob);
			}
			 data.put("recordsTotal",subComp.getTotalElements());
			 data.put("recordsFiltered",subComp.getTotalElements());
			 data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject getSubCompanyById(long id) {
		// TODO Auto-generated method stub
		System.out.println("FIND IN SERVICEIMPL-------- "+id);
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Optional<SubCompany> subComp = subCompanyRepo.findById(id);
			
			if (subComp.isPresent() && user.getCompanyCode().equals(String.valueOf(id))) {
				
				JSONObject data = new JSONObject();
				data.put("companyName", subComp.get().getScname());
				data.put("RegNo", subComp.get().getScregistrationno());
				data.put("addressLine1", subComp.get().getScaddress1());
				data.put("addressLine2", subComp.get().getScaddress2());
				data.put("addressLine3", subComp.get().getScaddress3());
				data.put("superComapnyCode", subComp.get().getScompanycode());
				data.put("contact", subComp.get().getScphoneno1());
				data.put("email", subComp.get().getScemailadmin());
				data.put("province", subComp.get().getScprovince());
				data.put("district", subComp.get().getScprovince());
				data.put("city", subComp.get().getScprovince());
				data.put("logo", subComp.get().getScomapanylogo());		
				data.put("companyIdentity", subComp.get().isIdentity());		
				
				return data;
			}else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
		
	}

	@Override
	public Long supplierSelfReg(JSONObject data, String param) {
		try {
			
			if(param.equalsIgnoreCase("self")){
				SubCompany subComp = new SubCompany();
				
				subComp.setScname(data.get("supName").toString());
				subComp.setSccategoryType(data.get("supType").toString());
				subComp.setIdentity(false);
				subComp.setScregdate(LocalDate.now());
				subComp.setScphoneno1(data.get("supPhone1").toString());
				subComp.setScemailadmin(data.get("supMailAdmin").toString());
				
				
				
				if (data.get("regType").toString().equalsIgnoreCase("company_check")) {
					subComp.setCreated("self");
					subComp.setUpdated("self");
					subComp.setIdentity(true);
				}else{
					subComp.setCreated("self");
					subComp.setUpdated("self");
					subComp.setIdentity(false);
				}
				
				subCompanyRepo.save(subComp);
				
				return subComp.getSubCompanyID();
			
			}else{
				return Long.valueOf(0);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return Long.valueOf(0);
		}
		
	}

	@Override
	public JSONObject addProductObject(JSONObject data, Long param) {
		// TODO Auto-generated method stub
		
		
		return null;
	}




	@Override
	public JSONObject updateSupplier(JSONObject data) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		try {
		System.out.println(data);
		System.out.println("supCom-- "+data.get("supplierID").toString());
		
	//	Optional<SubCompany> optSub =	subCompanyRepo.findById(Long.valueOf(data.get("supplierID").toString()));
//		Optional<SubCompany> optSub =	subCompanyRepo.findById(Long.valueOf("537"));
		Optional<SubCompany> optSub =	subCompanyRepo.findById(Long.valueOf(user.getCompanyCode()));

		
		HashMap<String, Object> table = (HashMap<String, Object>) data.get("table");
		System.out.println(table);

		if (optSub.isPresent()) {
			SubCompany subComp = optSub.get();
			System.out.println("sub company -"+subComp.getCreated());
			
			
				Long userCompanyID = new Long(user.getCompanyCode());
					
				subComp.setScprovince(Long.valueOf(data.get("province").toString()));
				subComp.setScdistrict(Long.valueOf(data.get("district").toString()));
				subComp.setSccity(Long.valueOf(data.get("city").toString()));
    			subComp.setScaddress1(data.get("addressLine1").toString());
				subComp.setScaddress2(data.get("addressLine2").toString());
				subComp.setScaddress3(data.get("addressLine3").toString());
				subComp.setScregistrationno(data.get("RegNo").toString()); 
				
				subComp.setScname1(data.get("name").toString());
				subComp.setScdesignation(data.get("designation").toString());
				subComp.setSccontactno(data.get("contactno").toString());
				subComp.setScemail(data.get("email").toString()); 
				
				subComp.setIsAdditionalDataSubmited(true);
//				subComp.setIsAdditionalDataSubmited(false);
				
				SupplierDetails supDeatils = new SupplierDetails();
				supDeatils.setBankName(data.get("Bank").toString());
				supDeatils.setBankBranch(data.get("Branch").toString());
				supDeatils.setBankBranchCode(data.get("bankCode").toString());
				supDeatils.setBankAcctNo(Integer.valueOf(data.get("bankAccountNo").toString()));
			//	supDeatils.setPhoneNo1(data.get("Phone").toString());
				
				
			/*	SupplierDetails supDeatils = new SupplierDetails();
				supDeatils.setBankName(data.get("Bank").toString());
				supDeatils.setBankBranchCode(data.get("bankCode").toString());
				supDeatils.setBankAcctNo(Integer.valueOf(data.get("bankAccountNo").toString()));
				supDeatils.setPhoneNo1(data.get("Phone").toString());*/
				
				
				ArrayList<JSONObject> productList = (ArrayList) data.get("catArray");
				
				for (Iterator iterator = productList.iterator(); iterator.hasNext();) {
					HashMap<String, Object> items = (HashMap) iterator.next();
					LinkedHashMap costingChemicalDetail = (LinkedHashMap) items;

					SupplierProductDetails supProductDetails = new SupplierProductDetails();					
					supProductDetails.setCategoryID(Long.valueOf(costingChemicalDetail.get("catId").toString()));
					supProductDetails.setSubCategoryID(Long.valueOf(costingChemicalDetail.get("subcatId").toString()));
					
					supProductDetails.setCategoryFee(Long.valueOf(data.get("catfee").toString()));
					supProductDetails.setSubcategoryFee(Long.valueOf(data.get("subcatfee").toString()));
					supProductDetails.setGrandTotal(Long.valueOf(data.get("grandTottoSend").toString()));

					supProductDetails.setSupplierID(Long.valueOf(user.getCompanyCode().toString()));					
					supplierProductDetailsRepo.save(supProductDetails);
				}

				//supplier.setIdentity(false);
				//supplier.setScregdate(LocalDate.now());
				//supplier.setScdistrict(data.get("doYouAdaptedAGreenPolicy").toString());
				//supplier.setScemailadmin(data.get("doYouEmployPersonsUnderTheAgeOf18Years").toString());
				
				subComp.setScstatus("P");
				subComp.setPrivate(true);
				
				/*
				 * if(!param.equalsIgnoreCase("notSelf")){ subComp.setCreated("SuperUser");
				 * subComp.setUpdated("SuperUser"); }else{ if
				 * (data.get("regType").toString().equalsIgnoreCase("company_check")) {
				 * subComp.setCreated("self"); subComp.setUpdated("self");
				 * subComp.setIdentity(true); }else{ subComp.setCreated("self");
				 * subComp.setUpdated("self"); subComp.setIdentity(false); } }
				 */
				
/*				Reader reader1 = null;
				
				if (data.containsKey("inputImage_logo")) {
					String[] logoData = data.get("inputImage_logo").toString().split(",");
					byte[] logodecodedBytes = Base64.getDecoder().decode(logoData[1]);
					String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(logodecodedBytes)).split("/")[1];
					FileUtils.writeByteArrayToFile(new File(companyDataPath+data.get("supplierID").toString()+"/logo."+mimeType), logodecodedBytes);
					supDeatils.setComapanyLogo(companyDataPath+data.get("supplierID").toString()+"/logo."+mimeType);
				}
				if (data.containsKey("inputImage_logo")) {
					String[] logoData = data.get("inputImage_logo").toString().split(",");
					byte[] logodecodedBytes = Base64.getDecoder().decode(logoData[1]);
					String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(logodecodedBytes)).split("/")[1];
					FileUtils.writeByteArrayToFile(new File(companyDataPath+data.get("supplierID").toString()+"/logo."+mimeType), logodecodedBytes);
					subComp.setScomapanylogo(companyDataPath+data.get("supplierID").toString()+"/logo."+mimeType);
				}
				
			
				//PDF Uploding
				
				if (data.containsKey("input_BR")) {
					String headerData[]=data.get("input_BR").toString().split("base64,");
					String extention[]=data.get("input_BR").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/BRForm",exten, head);
					
					supDeatils.setBrForm(
							supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/BRForm." + exten);
				}

				
				if (data.containsKey("input_KYC")) {
					String headerData[]=data.get("input_KYC").toString().split("base64,");
					String extention[]=data.get("input_KYC").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/KYCForm",exten, head);
					
					supDeatils.setKycForm(
							supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/KYCForm." + exten);
				}
				
				
				if (data.containsKey("input_BCI")) {
					String headerData[]=data.get("input_BCI").toString().split("base64,");
					String extention[]=data.get("input_BCI").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/BCIForm",exten, head);
					
					supDeatils.setBciForm(
							supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/BCIForm." + exten);
				}
				
				
				if (data.containsKey("input_Audited_Reports")) {
					String headerData[]=data.get("input_Audited_Reports").toString().split("base64,");
					String extention[]=data.get("input_Audited_Reports").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/lastAuditedReports",exten, head);
					
					supDeatils.setLastAuditedFinAcc(
							supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/lastAuditedReports." + exten);
				}
				
				
				if (data.containsKey("input_directordetails")) {
					String headerData[]=data.get("input_directordetails").toString().split("base64,");
					String extention[]=data.get("input_directordetails").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/DirectorDetails",exten, head);
					
					supDeatils.setListofDirectors(
							supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/DirectorDetails." + exten);
				}

				
				if (data.containsKey("input_CompanyProfile")) {
					String headerData[]=data.get("input_CompanyProfile").toString().split("base64,");
					String extention[]=data.get("input_CompanyProfile").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/CompanyProfile",exten, head);
					
					supDeatils.setCompanyProfile(
							supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/CompanyProfile." + exten);
				}
				
				
				if (data.containsKey("input_Application")) {
					String headerData[]=data.get("input_Application").toString().split("base64,");
					String extention[]=data.get("input_Application").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/Application",exten, head);
					
					supDeatils.setMainApplication(
							supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/Application." + exten);
				}
				
				
				if (data.containsKey("input_CodeConduct")) {
					String headerData[]=data.get("input_CodeConduct").toString().split("base64,");
					String extention[]=data.get("input_CodeConduct").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/CodeConduct",exten, head);
					
					supDeatils.setSupplierCodeofConduct(
							supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/CodeConduct." + exten);
				}
				
				
				if (data.containsKey("input_ESQuestions")) {
					String headerData[]=data.get("input_ESQuestions").toString().split("base64,");
					String extention[]=data.get("input_ESQuestions").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/ESQuestions",exten, head);
					
					supDeatils.setSupplierESQuestions(
							supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/ESQuestions." + exten);
				}*/
				
	
				subCompanyRepo.save(subComp);
				supDeatils.setSubCompanyID(subComp.getSubCompanyID());
				
				supplirDetailsRepo.save(supDeatils);
				//supplierProductDetailsRepo.save(supProductDetails);
				
				/*
				 * reader1 = Files.newBufferedReader( Paths.get(supplierRegistrationDataPath +
				 * data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() +
				 * "/rfpFile" + ".csv")); CSVParser csvParserHead = new CSVParser(reader1,
				 * CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
				 * );
				 */
				Optional<SupplierDetails> optTs = supplirDetailsRepo.findBySubCompanyID(Long.valueOf(user.getCompanyCode()));
				SupplierDetails supD =  optTs.get();
				
				optSub.get().setScomapanylogo(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/logo." + "pdf");
				supD.setComapanyLogo(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/logo." + "pdf");
				supD.setBrForm(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/BRForm ." + "pdf");
				supD.setKycForm(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/KYCForm ." + "pdf");
				supD.setBciForm(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/BCIForm ." + "pdf");
				supD.setLastAuditedFinAcc(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/lastAuditedReports." + "pdf");
				supD.setListofDirectors(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/DirectorDetails." + "pdf");
				supD.setCompanyProfile(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/CompanyProfile." + "pdf");
				supD.setMainApplication(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/Application." + "pdf");
				supD.setSupplierCodeofConduct(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/CodeConduct." + "pdf");
				supD.setSupplierESQuestions(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/ESQuestions." + "pdf");

				supD.setSubCompanyID(optSub.get().getSubCompanyID());
				supplirDetailsRepo.saveAndFlush(supD);
				
				JSONObject returnObj =  new JSONObject();
				returnObj.put("code", "00");
				returnObj.put("msg", "Success");
				return returnObj;
			} 
		    }catch (Exception e) {
				e.printStackTrace();
				JSONObject returnObj =  new JSONObject();
				returnObj.put("code", "01");
				returnObj.put("msg", "failed");
				return returnObj;
			}
		return null;
}




	@Override
	public JSONObject categorySuppliers(HttpServletRequest req, Long supplierID) {
		System.out.println("Inside ServiceImpl");
		
		JSONObject data = new JSONObject();
		try {			
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long companyID = new Long(user.getCompanyCode());
		System.out.println("Long----- "+companyID);
		
		data.put("draw", req.getParameter("draw"));
		String sorting = "";
		switch (req.getParameter("order[0][column]")) {
			case "0":
			sorting = "sd.supplierName";
			break;
		case "1":
			sorting = "ecat.eligibleCategortName";
			break;
		case "2":
			sorting = "supplierProductDetails.categoryFee";
			break;
		}
		    
		Page<Object[]> subPro= null;
		
		if (req.getParameter("order[0][dir]").equals("asc")) {
			subPro =supplierProductDetailsRepo.categorySuppliersForSupplierID(req.getParameter("search[value]"), supplierID, PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		} 
		else {
			subPro =supplierProductDetailsRepo.categorySuppliersForSupplierID(req.getParameter("search[value]"), supplierID, PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		}
				
		JSONArray arr = new JSONArray();
		int i =0;

		long count = subPro.getTotalElements();
		JSONObject prices = new JSONObject(); 
		System.out.println("get cn--->" + subPro.getContent().size());
		for (Object[] objects : subPro.getContent()) {
			
			  if (objects[0] instanceof SupplierProductDetails) {
			  System.out.println("Inside for --->" + data);
			  
			  SupplierProductDetails supplierProductDetails = (SupplierProductDetails)objects[0]; 
			  EligibleCategory ecat = (EligibleCategory) objects[1];
			  EligibleSubCategory esubcat = (EligibleSubCategory) objects[2]; 
			  SupplierDetails supplierDetails = (SupplierDetails) objects[3];
			  SubCompany subCom = (SubCompany) objects[4];
			  			  
			  JSONObject ob = new JSONObject(); 
			  ob.put("index", ++i);
			  ob.put("categoryID",ecat.getEligibleCategortID());
			  ob.put("supplierID",supplierDetails.getSupplierID());
			  
			  ob.put("supplierName",subCom.getScname());
			  ob.put("eligibleSubCategoryName",esubcat.getEligibleSubcatname());
			  ob.put("eligibleCategortName",ecat.getEligibleCategortName());
			  ob.put("categoryFee",supplierProductDetails.getCategoryFee());
			  
			  prices.put("grandTotal", supplierProductDetails.getGrandTotal());
			  prices.put("categoryTotal", supplierProductDetails.getCategoryFee());
			  prices.put("subCategoryTotal", supplierProductDetails.getSubcategoryFee());
			  prices.put("grandTotal",supplierProductDetails.getGrandTotal());
			  
		//    arr.add(prices); 
			  arr.add(ob); 
			  }
			 }
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", arr);
			data.put("prices", prices);
			System.out.println("data ds11111--->" + data);
			return data;

		}catch (Exception e) {
		 e.printStackTrace();
		 return null;
	   }
}




	@Override
	public JSONObject getSupplierDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long userCompanyID = new Long(user.getCompanyCode());
		JSONObject data = new JSONObject();
		try {
			JSONObject ob = new JSONObject();
			Optional<SubCompany> sc = subCompanyRepo.findById(userCompanyID);
			if(sc.isPresent()) {
				data.put("categoryID",sc.get().getIsAdditionalDataSubmited());
			}
			
			//data.put("data", ob);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return data;
		
	}




	@Override
	public JSONObject supplierApprove(long id, String approve) {
		JSONObject ob = new JSONObject();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User us = (User) authentication.getPrincipal();
		String userId = (us.getUserid());
		try {
			/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User us = (User) authentication.getPrincipal();
			*/
			Optional<SubCompany> sup = subCompanyRepo.findById(id);
//			Optional<SubCompany> com = subCompanyRepo.findById(Long.valueOf(us.getCompanyCode()));
		
//			Optional<SubCompany> com =	subCompanyRepo.findBysubCompanyID(Long.valueOf(data.get("subCompanyID").toString()));
			
			//Optional<User> uss = userRepo.findById(userId)
			
			if (sup.isPresent()) {
				if (approve.equals("approve")) {
					
					sup.get().setScstatus("Y");
					sup.get().setIsAdditionalDataSubmited(false);
					subCompanyRepo.save(sup.get());
					
					User user = new ObjectMapper().readValue(new JSONObject().toJSONString(), User.class);

					user.setUserid(sup.get().getScname());
					user.setUsername(sup.get().getScname());
					user.setCompanyCode(String.valueOf(sup.get().getSubCompanyID()));
					user.setCreatedDateTime(new Date());
					user.setEmail(sup.get().getScemailadmin());
					LocalDate date = LocalDate.now().plusDays(10);
					user.setExpireDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
					user.setStatus(3);
					user.setStatusFlag("Y");
					
					UserRole ur = new UserRole();
					ur.setUserRoleID(3);
					
					user.setUserRoll(ur);
					
					// String generatedString = new Random().ints(97, 122 + 1)
					//	      .limit(10)
					//	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
					//	      .toString();

					//MessageDigest md = MessageDigest.getInstance("SHA-256");
					//md.update(generatedString.getBytes());
					//user.setPassowrd(new sun.misc.BASE64Encoder().encode(md.digest()));
					user = userRepo.saveAndFlush(user);
					
					
					 ob.put("result", true);
					 ob.put("clientID", id);
					
				}else {
					sup.get().setScstatus("R");
					subCompanyRepo.save(sup.get());
					 ob.put("result", false);
					 ob.put("clientID", id);
					 
				}
			}
			 ob.put("code", "01");
			 ob.put("msg", "Success");
			

			
		} catch (Exception e) {
			e.printStackTrace();
			ob.put("code", "00");
			ob.put("msg", "failed");
			return ob;
		}
		return ob;
	}




	@Override
	public String createcategory(JSONObject data, HttpServletRequest request) {
		String msg = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long userCompanyID = new Long(user.getCompanyCode());
		
		try {
			
		Optional<List<EligibleCategory>> categoryList = eligibleCategoryRepo.findAllByEligibleCategortNameAndEligibleCategortCode(data.get("categoryname").toString(), data.get("categorycode").toString());
		
		if(categoryList.isPresent()) {
			msg = "Already Exist";
			
		}else {
				EligibleCategory eligibleCat = new EligibleCategory();
			
				eligibleCat.setEligibleCategortName(data.get("categoryname").toString());
				eligibleCat.setEligibleCategortDes(data.get("categorydescription").toString());
				eligibleCat.setEligibleCategortCode(data.get("categorycode").toString());
				eligibleCat.setEligibleCategoryFee(Double.valueOf(data.get("categoryfee").toString()));
				eligibleCat.setStatus(Long.valueOf("3"));
				eligibleCategoryRepo.save(eligibleCat);

			msg = "Success";
			}
		} catch (Exception e) {
			  e.printStackTrace();
			  msg = "Error";
		}
		return msg;
	}




	@Override
	public JSONObject categoryView(HttpServletRequest request) {
		try {

			JSONObject data = new JSONObject();
	//		Page<EligibleCategory> get_Category_View = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "eligibleCategortCode";
				break;
			case "1":
				sorting = "eligibleCategortCode";
				break;
			case "2":
				sorting = "eligibleCategortName";
				break;
			case "3":
				sorting = "eligibleCategortDes";
				break;
			case "4":
				sorting = "eligibleCategoryFee";
				break;
			}
			
			Page<EligibleCategory> get_Category_View = null;
			
			if (request.getParameter("order[0][dir]").equals("asc")) {
				get_Category_View = eligibleCategoryRepo.getCategoryView(request.getParameter("search[value]"),	PageRequest.of(Integer.parseInt(request.getParameter("start"))/ Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")),Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				get_Category_View = eligibleCategoryRepo.getCategoryView(request.getParameter("search[value]"),	PageRequest.of(Integer.parseInt(request.getParameter("start"))/ Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")),Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			long count = get_Category_View.getTotalElements();
			int i =0;
			for (EligibleCategory objects : get_Category_View.getContent()) {

					JSONObject ob = new JSONObject();

					EligibleCategory ecat = (EligibleCategory) objects;

					ob.put("index", ++i); 
					ob.put("categoryCode", ecat.getEligibleCategortCode());
					ob.put("categoryName", ecat.getEligibleCategortName());
					ob.put("categoryDescription", ecat.getEligibleCategortDes());
					ob.put("categoryFee", ecat.getEligibleCategoryFee());
					ob.put("eligiblecatId", ecat.getEligibleCategortID());
					ob.put("status", ecat.getStatus());
					ar.add(ob);					
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
	public JSONObject categoryAuth(long id, String approve) {
		Optional<EligibleCategory> ecat = eligibleCategoryRepo.findById(id);
		JSONObject ob = new JSONObject();
		Long active = (long) 3;
		Long deactive = (long) 4;
		Long disable = (long) 6;
		try {
			if (ecat.isPresent()) {

				if (approve.equals("active")) {
					ecat.get().setStatus(active);
					eligibleCategoryRepo.save(ecat.get());
				}else if(approve.equals("deactive")) {
					ecat.get().setStatus(deactive);
					eligibleCategoryRepo.save(ecat.get());
				}else {
					ecat.get().setStatus(disable);
					eligibleCategoryRepo.save(ecat.get());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ob;
		}
		return ob;		
	}




	@Override
	public Boolean getWetherCategoryExists(String enteredValue) {
		return eligibleCategoryRepo.existsByEligibleCategortNameAndStatus(enteredValue,Long.valueOf(3));
	}


	@Override
	public Boolean getWetherCategoryDesExists(String enteredValue) {
		return eligibleCategoryRepo.existsByEligibleCategortDesAndStatus(enteredValue,Long.valueOf(3));
	}


	@Override
	public Boolean getWetherCategoryCodeExists(String enteredValue) {
		return eligibleCategoryRepo.existsByEligibleCategortCodeAndStatus(enteredValue,Long.valueOf(3));
	}


	@Override
	public JSONObject editCategoryView(JSONObject data) {
		JSONObject ob = new JSONObject();
		String msg = null;
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long eligiblecatId = Long.valueOf(data.get("eligiblecatId").toString());

			Optional<EligibleCategory> ec = eligibleCategoryRepo.findById(eligiblecatId);
			if (ec.isPresent()) {

				ec.get().setEligibleCategortName(data.get("nameVal").toString());
				ec.get().setEligibleCategortDes(data.get("descriptionVal").toString());
				ec.get().setEligibleCategortCode(data.get("codeVal").toString());
				ec.get().setEligibleCategoryFee(Double.valueOf(data.get("feeVal").toString()));
				eligibleCategoryRepo.save(ec.get());
			}
			ob.put("responseText", "Success");
		}catch(Exception e) {
		 e.printStackTrace();
		 return null;
		}
		return ob;
	}


	@Override
	public Boolean getWetherCategoryFeeExists(String enteredValue) {
		return eligibleCategoryRepo.existsByEligibleCategoryFeeAndStatus(enteredValue,Long.valueOf(3));
	}



	@Override
	public JSONObject getSupplierForView(HttpServletRequest req) {
		try {
			JSONObject data= new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			data.put("draw", req.getParameter("draw"));
			String sorting = "";
			
			switch (req.getParameter("order[0][column]")) {
			  case "0":
				sorting = "scompanycode";
			    break;
			  case "1":
				sorting = "scname";
			    break;
			  case "2":
				sorting = "sccategoryType";
				break;
			  case "3":
				sorting = "scregistrationno";
			    break;
			  case "4":
				sorting = "scsystemregistereddate";
				break;
			  case "5":
				sorting = "scemailadmin";
			    break;
			  case "6":
				sorting = "scdistrict";
				break;
			  case "7":
				sorting = "sccity";
				break;
			}
			Page<Object[]> subComp= null;
			if (req.getParameter("order[0][dir]").equals("asc")) {
				subComp =subCompanyRepo.getSuppliersView(req.getParameter("search[value]"),user.getUserid(), PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}
			else {
				subComp =subCompanyRepo.getSuppliersView(req.getParameter("search[value]"),user.getUserid(), PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			JSONArray ar =  new JSONArray();
			int i =0;
			for (Object[] objects : subComp.getContent()) {
				
				if (objects[0] instanceof SubCompany && objects[1] instanceof Province && objects[2] instanceof District && objects[3] instanceof City && objects[4] instanceof SupplierDetails) {
					SubCompany subCompany = (SubCompany) objects[0];
					Province pro = (Province) objects[1];
					District dis = (District) objects[2];
					City cit = (City) objects[3];
					SupplierDetails supplierDetails = (SupplierDetails) objects[4];
					String companyType = "";
					switch(subCompany.getSccategoryType()){
					case "I":
						companyType = "Individual";
						break;
					case "SPP":
						companyType = "Sole Proprietor/Partnership";
						break;
					case "LLC":
						companyType = "Limited Liability Company";
					    break;
					case "PQC":
						companyType = "Public Quoted Company";
					    break;
					case "OC":
						companyType = "Offshore Company";
					    break;
					}
					
					JSONObject ob = new JSONObject();
					
					ob.put("index", ++i);
					ob.put("supplierID",supplierDetails.getSupplierID());
					ob.put("code", subCompany.getScompanycode());
					ob.put("supName", subCompany.getScname());
					ob.put("companyType", companyType);
					ob.put("RegNo", subCompany.getScregistrationno());
					ob.put("createdDateTime", subCompany.getScsystemregistereddate());
					ob.put("supMailAdmin", subCompany.getScemailadmin());
					ob.put("supPhone1", subCompany.getScphoneno1());
					ob.put("subCompanyID", subCompany.getSubCompanyID());
			//		ob.put("amount", subCompany.getAmount());
					if(subCompany.getIspaid() == null) {
						ob.put("amount", "Pending");
					}else {
						ob.put("amount", subCompany.getAmount());
					}
					
					
					if(subCompany.getIspaid() == null) {
						ob.put("isPaid", "Pending");
					}else {
						ob.put("isPaid", subCompany.getIspaid() == true ? "Paid" : "Pending");
					}
					
					
					ob.put("province", pro.getEnglishName());
					ob.put("district", dis.getEnglishName());
					ob.put("city", cit.getEnglishName());
					ob.put("address1", subCompany.getScaddress1());
					ob.put("address2", subCompany.getScaddress2());
					ob.put("address3", subCompany.getScaddress3());
					
					ob.put("onetimeFee", supplierDetails.getOneTimeFee());
					ob.put("bank", supplierDetails.getBankBranch());
					ob.put("branch", supplierDetails.getBankBranch());
					ob.put("accountNo", supplierDetails.getBankAcctNo());
					ob.put("bankCode", supplierDetails.getBankBranchCode());
					
					ob.put("name", subCompany.getScname1());
					ob.put("designation", subCompany.getScdesignation());
					ob.put("contactNo", subCompany.getSccontactno());		
					ob.put("email", subCompany.getScemail());
					ob.put("blockSupReason", subCompany.getBlockcomment());
					ob.put("unblockSupReason", subCompany.getUnblockcomment());  
					
					ob.put("view", supplierDetails.getSupplierID());
					ob.put("view", subCompany.getScname());
					
					ob.put("brForm", supplierDetails.getBrForm());
					ob.put("kycForm", supplierDetails.getKycForm());
					ob.put("bciForm", supplierDetails.getBciForm());
					ob.put("auditedReports", supplierDetails.getLastAuditedFinAcc());
					ob.put("directorDetails", supplierDetails.getListofDirectors());
					ob.put("CompanyProfile", supplierDetails.getCompanyProfile());
					ob.put("mainApplication", supplierDetails.getMainApplication());
					ob.put("supCodeofConduct", supplierDetails.getSupplierCodeofConduct());
					ob.put("supEsQuestions", supplierDetails.getSupplierESQuestions());
					
		//			ob.put("status",supplierDetails.getSupplierStatus());
					ob.put("status",subCompany.getScstatus());
					
					ar.add(ob);
					System.out.println("ob--- "+ob);
				}				
			}
			 data.put("recordsTotal",subComp.getTotalElements());
			 data.put("recordsFiltered",subComp.getTotalElements());
			 data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}




	@Override
	public JSONObject viewCategorySuppliers(HttpServletRequest req, Long supplierID) {
		JSONObject data = new JSONObject();
		try {			
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long companyID = new Long(user.getCompanyCode());
		System.out.println("Long----- "+companyID);
		
		data.put("draw", req.getParameter("draw"));
		String sorting = "";
		switch (req.getParameter("order[0][column]")) {
			case "0":
			sorting = "sd.supplierName";
			break;
		}
		    
		Page<Object[]> subPro= null;
		
		if (req.getParameter("order[0][dir]").equals("asc")) {
			subPro =supplierProductDetailsRepo.viewCategorySuppliersForSupplierID(req.getParameter("search[value]"), supplierID, PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		} 
		else {
			subPro =supplierProductDetailsRepo.viewCategorySuppliersForSupplierID(req.getParameter("search[value]"), supplierID, PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		}
				
		JSONArray arr = new JSONArray();
		int i =0;

		long count = subPro.getTotalElements();
		JSONObject prices = new JSONObject(); 
		System.out.println("get cn--->" + subPro.getContent().size());
		for (Object[] objects : subPro.getContent()) {
			
			  if (objects[0] instanceof SupplierProductDetails) {
			  System.out.println("Inside for --->" + data);
			  
			  SupplierProductDetails supplierProductDetails = (SupplierProductDetails)objects[0]; 
			  EligibleCategory ecat = (EligibleCategory) objects[1];
			  EligibleSubCategory esubcat = (EligibleSubCategory) objects[2];
			  SupplierDetails supplierDetails = (SupplierDetails) objects[3];
			  SubCompany subCom = (SubCompany) objects[4];
			  			  
			  JSONObject ob = new JSONObject(); 
			  ob.put("index", ++i);
			  ob.put("categoryID",ecat.getEligibleCategortID());
			  ob.put("supplierID",supplierDetails.getSupplierID());
			  
			  ob.put("supplierName",subCom.getScname());
			  ob.put("eligibleSubCategoryName",esubcat.getEligibleSubcatname());
			  ob.put("eligibleCategortName",ecat.getEligibleCategortName());
			  ob.put("categoryFee",supplierProductDetails.getCategoryFee());

			  prices.put("grandTotal", supplierProductDetails.getGrandTotal());
			  prices.put("categoryTotal", supplierProductDetails.getCategoryFee());
			  prices.put("subCategoryTotal", supplierProductDetails.getSubcategoryFee());
			  prices.put("grandTotal",supplierProductDetails.getGrandTotal());
			  
			  arr.add(ob); 
			  }
			 }
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", arr);
			data.put("prices", prices);
			System.out.println("data ds11111--->" + data);
			return data;

		}catch (Exception e) {
		 e.printStackTrace();
		 return null;
	   }
	}




	@Override
	public JSONObject companyProfile(HttpServletRequest request, Long companyID) {		
		JSONObject data = new JSONObject();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long companyID1 = new Long(user.getCompanyCode());
			System.out.println("Long----- " + companyID1);
			
			List<Object[]> subCom = subCompanyRepo.getCompanyProfileDetailsForRelatedSupplierName(companyID);
			
			JSONObject ob = new JSONObject();
									
			for (Object[] objects : subCom) {
			if (objects[0] instanceof SubCompany && objects[1] instanceof Province && objects[2] instanceof District && objects[3] instanceof City && objects[4] instanceof SupplierDetails) {
						SubCompany scom = (SubCompany) objects[0];
						Province pro = (Province) objects[1];
						District dis = (District) objects[2];
						City cit = (City) objects[3];
						SupplierDetails sd = (SupplierDetails) objects[4];
				
					String companyType = "";
					switch(scom.getSccategoryType()){
					case "I":
						companyType = "Individual";
						break;
					case "SPP":
						companyType = "Sole Proprietor/Partnership";
						break;
					case "LLC":
						companyType = "Limited Liability Company";
					    break;
					case "PQC":
						companyType = "Public Quoted Company";
					    break;
					case "OC":
						companyType = "Offshore Company";
					    break;
					}		
					
					ob.put("subCompanyID",scom.getSubCompanyID());
					ob.put("supplierID",sd.getSupplierID());
					ob.put("supplierName", scom.getScname());
					ob.put("companyType", companyType);
					ob.put("registrationNo", scom.getScregistrationno());
					ob.put("contactNo", scom.getScphoneno1());
					
					ob.put("district", dis.getEnglishName());
					ob.put("city", cit.getEnglishName());
					ob.put("province", pro.getEnglishName());
					
					ob.put("address1", scom.getScaddress1());
					ob.put("address2", scom.getScaddress2());
					ob.put("address3", scom.getScaddress3());
//					ob.put("oneTimeFee", sd.getOneTimeFee());
					ob.put("emailAdmin", scom.getScemailadmin());
					
					ob.put("name", scom.getScname1());
					ob.put("designation", scom.getScdesignation());
					ob.put("contactNumber", scom.getSccontactno());
					ob.put("email", scom.getScemail());
					
					ob.put("bank", sd.getBankName());
					ob.put("branch", sd.getBankBranch());
					ob.put("accountNo", sd.getBankAcctNo());
					ob.put("bankCode", sd.getBankBranchCode());
					
					ob.put("BRForm", sd.getBrForm());
					ob.put("kycForm", sd.getKycForm());
					ob.put("bciForm", sd.getBciForm());
					ob.put("auditedReports", sd.getLastAuditedFinAcc());
					ob.put("directorDetails", sd.getListofDirectors());
					ob.put("companyProfile", sd.getCompanyProfile());
					ob.put("mainApplication", sd.getMainApplication());
					ob.put("supCodeofConduct", sd.getSupplierCodeofConduct());
					ob.put("supEsQuestions", sd.getSupplierESQuestions());
				}
			}
			data.put("data", ob);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;		
	}




	@Override
	public JSONObject companyViewCategory(HttpServletRequest req, Long companyID) {
		JSONObject data = new JSONObject();
		try {			
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
	//	Long companyID = new Long(user.getCompanyCode());
		System.out.println("Long----- "+companyID);
		
		data.put("draw", req.getParameter("draw"));
		String sorting = "";
		switch (req.getParameter("order[0][column]")) {
			case "0":
			sorting = "sd.supplierName";
			break;
		case "1":
			sorting = "ecat.eligibleCategortName";
			break;
		case "2":
			sorting = "supplierProductDetails.categoryFee";
			break;
		}
		Page<Object[]> subPro= null;
		
		if (req.getParameter("order[0][dir]").equals("asc")) {
			subPro = supplierProductDetailsRepo.companyViewCategoryForCompanyID(req.getParameter("search[value]"), companyID, PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		} 
		else {
			subPro = supplierProductDetailsRepo.companyViewCategoryForCompanyID(req.getParameter("search[value]"), companyID, PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		}
				
		JSONArray arr = new JSONArray();
		int i =0;

		long count = subPro.getTotalElements();
		JSONObject prices = new JSONObject();
		System.out.println("get cn--->" + subPro.getContent().size());
		
		for (Object[] objects : subPro.getContent()) {			
			  if (objects[0] instanceof SupplierProductDetails) {
			  System.out.println("Inside for --->" + data);		  
			  
			  SupplierProductDetails supplierProductDetails = (SupplierProductDetails)objects[0]; 
			  EligibleCategory ecat = (EligibleCategory) objects[1];
			  EligibleSubCategory esubcat = (EligibleSubCategory) objects[2];
			  SupplierDetails supplierDetails = (SupplierDetails) objects[3];
			  SubCompany subCom = (SubCompany) objects[4];
			  			  
			  JSONObject ob = new JSONObject();
			  
			  ob.put("index", ++i);
			  ob.put("categoryID",ecat.getEligibleCategortID());
	//		  ob.put("supplierID",supplierDetails.getSupplierID());
			  ob.put("companyID",subCom.getSubCompanyID());	
			  ob.put("eligibleSubCategoryName",esubcat.getEligibleSubcatname());
			  ob.put("eligibleCategortName",ecat.getEligibleCategortName());
			  
			  prices.put("grandTotal", supplierProductDetails.getGrandTotal());
			  prices.put("categoryTotal", supplierProductDetails.getCategoryFee());
			  prices.put("subCategoryTotal", supplierProductDetails.getSubcategoryFee());
			  prices.put("grandTotal",supplierProductDetails.getGrandTotal());
			  
			  arr.add(ob); 
			  	}
			 }
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", arr);
			data.put("prices", prices);
			System.out.println("data ds11111--->" + data);
			return data;
		}catch (Exception e) {
		 e.printStackTrace();
		 return null;
	   }
	}




	@Override
	public JSONObject selectAdditionalCategory(HttpServletRequest req, Long companyID) {
		JSONObject data = new JSONObject();
		try {			
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
	//	Long companyID = new Long(user.getCompanyCode());
		System.out.println("Long----- "+companyID);
		
		data.put("draw", req.getParameter("draw"));
		String sorting = "";
		switch (req.getParameter("order[0][column]")) {
			case "0":
			sorting = "sd.supplierName";
			break;
		case "1":
			sorting = "ecat.eligibleCategortName";
			break;
		case "2":
			sorting = "supplierProductDetails.categoryFee";
			break;
		}
		Page<Object[]> subPro= null;
		
		if (req.getParameter("order[0][dir]").equals("asc")) {
			subPro = supplierProductDetailsRepo.selectAdditionalCategoryForCompanyID(req.getParameter("search[value]"), companyID, PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		} 
		else {
			subPro = supplierProductDetailsRepo.selectAdditionalCategoryForCompanyID(req.getParameter("search[value]"), companyID, PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		}
				
		JSONArray arr = new JSONArray();
		int i =0;

		long count = subPro.getTotalElements();
		System.out.println("get cn--->" + subPro.getContent().size());
		for (Object[] objects : subPro.getContent()) {
			
			  if (objects[0] instanceof SupplierProductDetails) {
			  System.out.println("Inside for --->" + data);		  
			  
			  SupplierProductDetails supplierProductDetails = (SupplierProductDetails)objects[0]; 
			  EligibleCategory ecat = (EligibleCategory) objects[1]; 
			  SupplierDetails supplierDetails = (SupplierDetails) objects[2];
			  SubCompany subCom = (SubCompany) objects[3];
			  			  
			  JSONObject ob = new JSONObject(); 
			  ob.put("index", ++i);
			  
			  ob.put("categoryID",ecat.getEligibleCategortID());
			  
			  ob.put("companyID",subCom.getSubCompanyID());
			  ob.put("category",subCom.getSubCompanyID());
			  ob.put("categoryFee",supplierProductDetails.getCategoryFee());
			  
			  arr.add(ob); 
			  }
			 }
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", arr);
			System.out.println("data ds11111--->" + data);
			return data;

		}catch (Exception e) {
		 e.printStackTrace();
		 return null;
		}		
	}




	@Override
	public JSONObject submitNewCategory(HttpServletRequest req, JSONObject data) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long userCompanyID = new Long(user.getCompanyCode());
		
		try {
			Optional<SubCompany> optSub =	subCompanyRepo.findById(Long.valueOf(user.getCompanyCode().toString()));
			
		if (optSub.isPresent()) {
			SubCompany subComp = optSub.get();
			System.out.println("sub company -"+subComp.getCreated());	
		
		ArrayList<JSONObject> productList = (ArrayList) data.get("addcatArray");
		
		for (Iterator iterator = productList.iterator(); iterator.hasNext();) {
			HashMap<String, Object> items = (HashMap) iterator.next();
			LinkedHashMap costingChemicalDetail = (LinkedHashMap) items;

			SupplierProductDetails supProductDetails = new SupplierProductDetails();	
			
			supProductDetails.setCategoryID(Long.valueOf(costingChemicalDetail.get("catId").toString()));
			supProductDetails.setSubCategoryID(Long.valueOf(costingChemicalDetail.get("subCatId").toString()));
			
			supProductDetails.setCategoryFee(Long.valueOf(data.get("catfee").toString()));
			supProductDetails.setSubcategoryFee(Long.valueOf(data.get("subcatfee").toString()));
			supProductDetails.setGrandTotal(Long.valueOf(data.get("grandTottoSend").toString()));
			
			supProductDetails.setSupplierID(Long.valueOf(user.getCompanyCode().toString()));			
			supplierProductDetailsRepo.save(supProductDetails);
		}

			JSONObject returnObj =  new JSONObject();
			returnObj.put("code", "00");
			returnObj.put("msg", "success");
			return returnObj;
			} 
    	}catch (Exception e) {
			e.printStackTrace();
			JSONObject returnObj =  new JSONObject();
			returnObj.put("code", "01");
			returnObj.put("msg", "failed");
			return returnObj;
		}
    	return null;
	}




	@Override
	public JSONObject getSupplierForBlock(HttpServletRequest req) {
		try {
			JSONObject data= new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			data.put("draw", req.getParameter("draw"));
			String sorting = "";
			
			switch (req.getParameter("order[0][column]")) {
			  case "0":
				sorting = "scompanycode";
			    break;
			  case "1":
				sorting = "scname";
			    break;
			  case "2":
				sorting = "sccategoryType";
				break;
			  case "3":
				sorting = "scregistrationno";
			    break;
			  case "4":
				sorting = "scsystemregistereddate";
				break;
			  case "5":
				sorting = "scemailadmin";
			    break;
			  case "6":
				sorting = "scdistrict";
				break;
			  case "7":
				sorting = "sccity";
				break;
			}
			Page<Object[]> subComp= null;
			if (req.getParameter("order[0][dir]").equals("asc")) {
				subComp =subCompanyRepo.getSuppliersBlock(req.getParameter("search[value]"),user.getUserid(), PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}
			else {
				subComp =subCompanyRepo.getSuppliersBlock(req.getParameter("search[value]"),user.getUserid(), PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			JSONArray ar =  new JSONArray();
			int i =0;
			for (Object[] objects : subComp.getContent()) {
				
				if (objects[0] instanceof SubCompany && objects[1] instanceof Province && objects[2] instanceof District && objects[3] instanceof City && objects[4] instanceof SupplierDetails) {
					SubCompany subCompany = (SubCompany) objects[0];
					Province pro = (Province) objects[1];
					District dis = (District) objects[2];
					City cit = (City) objects[3];
					SupplierDetails supplierDetails = (SupplierDetails) objects[4];
					String companyType = "";
					switch(subCompany.getSccategoryType()){
					case "I":
						companyType = "Individual";
						break;
					case "SPP":
						companyType = "Sole Proprietor/Partnership";
						break;
					case "LLC":
						companyType = "Limited Liability Company";
					    break;
					case "PQC":
						companyType = "Public Quoted Company";
					    break;
					case "OC":
						companyType = "Offshore Company";
					    break;
					}
					
					JSONObject ob = new JSONObject();
					
					ob.put("index", ++i);
					ob.put("supplierID",supplierDetails.getSupplierID());
					ob.put("code", subCompany.getScompanycode());
					ob.put("supName", subCompany.getScname());
					ob.put("companyType", companyType);
					ob.put("RegNo", subCompany.getScregistrationno());
					ob.put("createdDateTime", subCompany.getScsystemregistereddate());
					ob.put("supMailAdmin", subCompany.getScemailadmin());
					ob.put("supPhone1", subCompany.getScphoneno1());
					ob.put("subCompanyID", subCompany.getSubCompanyID());
					if(subCompany.getIspaid() == null) {
						ob.put("amount", "Pending");
					}else {
						ob.put("amount", subCompany.getAmount());
					}
					
					if(subCompany.getIspaid() == null) {
						ob.put("isPaid", "Pending");
					}else {
						ob.put("isPaid", subCompany.getIspaid() == true ? "Paid" : "Pending");
					}
					
					ob.put("province", pro.getEnglishName());
					ob.put("district", dis.getEnglishName());
					ob.put("city", cit.getEnglishName());
					ob.put("address1", subCompany.getScaddress1());
					ob.put("address2", subCompany.getScaddress2());
					ob.put("address3", subCompany.getScaddress3());
					
					ob.put("onetimeFee", supplierDetails.getOneTimeFee());
					ob.put("bank", supplierDetails.getBankBranch());
					ob.put("branch", supplierDetails.getBankBranch());
					ob.put("accountNo", supplierDetails.getBankAcctNo());
					ob.put("bankCode", supplierDetails.getBankBranchCode());
					
					ob.put("view", supplierDetails.getSupplierID());
					ob.put("view", subCompany.getScname());
					
					ob.put("status",subCompany.getScstatus());
					ar.add(ob);
					System.out.println("ob--- "+ob);
				}				
			}
			 data.put("recordsTotal",subComp.getTotalElements());
			 data.put("recordsFiltered",subComp.getTotalElements());
			 data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}




	@Override
	public JSONObject blockCategorySuppliers(HttpServletRequest req, Long supplierID) {
		JSONObject data = new JSONObject();
		try {			
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long companyID = new Long(user.getCompanyCode());
		System.out.println("Long----- "+companyID);
		
		data.put("draw", req.getParameter("draw"));
		String sorting = "";
		switch (req.getParameter("order[0][column]")) {
			case "0":
			sorting = "sd.supplierName";
			break;
		}
		    
		Page<Object[]> subPro= null;
		
		if (req.getParameter("order[0][dir]").equals("asc")) {
			subPro =supplierProductDetailsRepo.blockCategorySuppliersForSupplierID(req.getParameter("search[value]"), supplierID, PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		} 
		else {
			subPro =supplierProductDetailsRepo.blockCategorySuppliersForSupplierID(req.getParameter("search[value]"), supplierID, PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		}
				
		JSONArray arr = new JSONArray();
		int i =0;

		long count = subPro.getTotalElements();
		JSONObject prices = new JSONObject(); 
		System.out.println("get cn--->" + subPro.getContent().size());
		for (Object[] objects : subPro.getContent()) {
			
			  if (objects[0] instanceof SupplierProductDetails) {
			  System.out.println("Inside for --->" + data);
			  
			  SupplierProductDetails supplierProductDetails = (SupplierProductDetails)objects[0]; 
			  EligibleCategory ecat = (EligibleCategory) objects[1];
			  EligibleSubCategory esubcat = (EligibleSubCategory) objects[2];
			  SupplierDetails supplierDetails = (SupplierDetails) objects[3];
			  SubCompany subCom = (SubCompany) objects[4];
			  			  
			  JSONObject ob = new JSONObject(); 
			  ob.put("index", ++i);
			  ob.put("categoryID",ecat.getEligibleCategortID());
			  ob.put("supplierID",supplierDetails.getSupplierID());
			  
			  ob.put("supplierName",subCom.getScname());
			  ob.put("eligibleSubCategoryName",esubcat.getEligibleSubcatname());
			  ob.put("eligibleCategortName",ecat.getEligibleCategortName());
			  ob.put("categoryFee",supplierProductDetails.getCategoryFee());
			  
			  prices.put("grandTotal", supplierProductDetails.getGrandTotal());
			  prices.put("categoryTotal", supplierProductDetails.getCategoryFee());
			  prices.put("subCategoryTotal", supplierProductDetails.getSubcategoryFee());
			  prices.put("grandTotal",supplierProductDetails.getGrandTotal());
			  
			  arr.add(ob); 
			  }
			 }
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", arr);
			data.put("prices", prices);
			System.out.println("data ds11111--->" + data);
			return data;
		}catch (Exception e) {
		 e.printStackTrace();
		 return null;
	   }
	}




	@Override
	public JSONObject blockSupplier(JSONObject data) {
		JSONObject ob = new JSONObject();
		String msg = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User us = (User) authentication.getPrincipal();
		String userId = (us.getUserid());
		
		try {
			Long supplierID = Long.valueOf(data.get("selectSupplierID").toString());
			
			List<User> userList =	userRepo.findByCompanyCode(data.get("selectSupplierID").toString());
			if (userList != null) {
				for (User user : userList) {
					  user.setStatus(Integer.valueOf("21"));
					  userRepo.saveAndFlush(user);
				}
			} 
			
			List<UserRole> userRoleList =	userRoleRepo.findByCompanyCode(Long.valueOf(data.get("selectSupplierID").toString()));
			if (userRoleList != null) {
				for (UserRole userrole : userRoleList) {
					userrole.setUserRoleStatus(Integer.valueOf("21"));
					  userRoleRepo.saveAndFlush(userrole);
				}
			} 
			
			Optional<SubCompany> subcom = subCompanyRepo.findById(supplierID);
			if(subcom.isPresent()) {
				
				subcom.get().setBlockcomment(data.get("reason").toString());
				subcom.get().setScstatus("B");
				subcom.get().setApprovedBy(us.getUserid());
				subcom.get().setApproveDateTime(LocalDateTime.now());
				subCompanyRepo.save(subcom.get());
			}	
				
			Optional<SupplierDetails> supplierDetailsOptional = supplirDetailsRepo.findBySubCompanyID(supplierID);
			if(supplierDetailsOptional.isPresent()) {
				SupplierDetails supplierDetails =supplierDetailsOptional.get();
				supplierDetails.setSupplierStatus("21");
				supplirDetailsRepo.saveAndFlush(supplierDetails);
			}
				 
				ob.put("responseText", "Success");	
				ob.put("msg", "Success");
				ob.put("code", "01");
				
		}catch (Exception e) {
		    e.printStackTrace();
			ob.put("msg", "falied");
			ob.put("code", "02");
			return ob;
		}
		return ob;
	}
	
	
	
	
	public JSONObject approveSupplierWhiteList(JSONObject data) {
		JSONObject ob = new JSONObject();
		String msg = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User us = (User) authentication.getPrincipal();
		String userId = (us.getUserid());
		
		try {
			Long supplierID = Long.valueOf(data.get("selectSupplierID").toString());
			
			List<User> userList =	userRepo.findByCompanyCode(data.get("selectSupplierID").toString());
			if (userList != null) {
				for (User user : userList) {
					  user.setStatus(Integer.valueOf("3"));
					  userRepo.saveAndFlush(user);
				}
			} 
			
			List<UserRole> userRoleList =	userRoleRepo.findByCompanyCode(Long.valueOf(data.get("selectSupplierID").toString()));
			if (userRoleList != null) {
				for (UserRole userrole : userRoleList) {
					userrole.setUserRoleStatus(Integer.valueOf("3"));
					  userRoleRepo.saveAndFlush(userrole);
				}
			} 
			
			Optional<SubCompany> subcom = subCompanyRepo.findById(supplierID);
			if(subcom.isPresent()) {
				
				subcom.get().setBlockcomment(data.get("reason").toString());

				subcom.get().setScstatus("Y");
				subcom.get().setApprovedBy(us.getUserid());
				subcom.get().setApproveDateTime(LocalDateTime.now());
				subCompanyRepo.save(subcom.get());
			}	
				
			Optional<SupplierDetails> supplierDetailsOptional = supplirDetailsRepo.findBySubCompanyID(supplierID);
			if(supplierDetailsOptional.isPresent()) {
				SupplierDetails supplierDetails =supplierDetailsOptional.get();
				supplierDetails.setSupplierStatus("3");
				supplirDetailsRepo.saveAndFlush(supplierDetails);
			}
				ob.put("responseText", "Success");	
				ob.put("msg", "Success");
				ob.put("code", "01");
				
		}catch (Exception e) {
		    e.printStackTrace();
			ob.put("msg", "falied");
			ob.put("code", "02");
			return ob;
		}
		return ob;
	}




	@Override
	public JSONObject viewBlockSuppliers(HttpServletRequest req) {
		try {
			JSONObject data= new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			data.put("draw", req.getParameter("draw"));
			String sorting = "";
			
			switch (req.getParameter("order[0][column]")) {
			  case "0":
				sorting = "scompanycode";
			    break;
			  case "1":
				sorting = "scname";
			    break;
			  case "2":
				sorting = "sccategoryType";
				break;
			  case "3":
				sorting = "scregistrationno";
			    break;
			  case "4":
				sorting = "scsystemregistereddate";
				break;
			  case "5":
				sorting = "scemailadmin";
			    break;
			  case "6":
				sorting = "scdistrict";
				break;
			  case "7":
				sorting = "sccity";
				break;
			}
			Page<Object[]> subComp= null;
			if (req.getParameter("order[0][dir]").equals("asc")) {
				subComp =subCompanyRepo.viewSuppliersBlock(req.getParameter("search[value]"),user.getUserid(), PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}
			else {
				subComp =subCompanyRepo.viewSuppliersBlock(req.getParameter("search[value]"),user.getUserid(), PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			JSONArray ar =  new JSONArray();
			int i =0;
			for (Object[] objects : subComp.getContent()) {
				
				if (objects[0] instanceof SubCompany && objects[1] instanceof Province && objects[2] instanceof District && objects[3] instanceof City && objects[4] instanceof SupplierDetails) {
					SubCompany subCompany = (SubCompany) objects[0];
					Province pro = (Province) objects[1];
					District dis = (District) objects[2];
					City cit = (City) objects[3];
					SupplierDetails supplierDetails = (SupplierDetails) objects[4];
					String companyType = "";
					switch(subCompany.getSccategoryType()){
					case "I":
						companyType = "Individual";
						break;
					case "SPP":
						companyType = "Sole Proprietor/Partnership";
						break;
					case "LLC":
						companyType = "Limited Liability Company";
					    break;
					case "PQC":
						companyType = "Public Quoted Company";
					    break;
					case "OC":
						companyType = "Offshore Company";
					    break;
					}
					
					JSONObject ob = new JSONObject();
					
					ob.put("index", ++i);
					ob.put("supplierID",supplierDetails.getSupplierID());
					ob.put("code", subCompany.getScompanycode());
					ob.put("supName", subCompany.getScname());
					ob.put("companyType", companyType);
					ob.put("RegNo", subCompany.getScregistrationno());
					ob.put("createdDateTime", subCompany.getScsystemregistereddate());
					ob.put("supMailAdmin", subCompany.getScemailadmin());
					ob.put("supPhone1", subCompany.getScphoneno1());
					ob.put("subCompanyID", subCompany.getSubCompanyID());
					if(subCompany.getIspaid() == null) {
						ob.put("amount", "Pending");
					}else {
						ob.put("amount", subCompany.getAmount());
					}
					
					if(subCompany.getIspaid() == null) {
						ob.put("isPaid", "Pending");
					}else {
						ob.put("isPaid", subCompany.getIspaid() == true ? "Paid" : "Pending");
					}
					
					ob.put("province", pro.getEnglishName());
					ob.put("district", dis.getEnglishName());
					ob.put("city", cit.getEnglishName());
					ob.put("address1", subCompany.getScaddress1());
					ob.put("address2", subCompany.getScaddress2());
					ob.put("address3", subCompany.getScaddress3());
					
					ob.put("onetimeFee", supplierDetails.getOneTimeFee());
					ob.put("bank", supplierDetails.getBankBranch());
					ob.put("branch", supplierDetails.getBankBranch());
					ob.put("accountNo", supplierDetails.getBankAcctNo());
					ob.put("bankCode", supplierDetails.getBankBranchCode());
					ob.put("blockReason", subCompany.getBlockcomment());
					
					ob.put("view", supplierDetails.getSupplierID());
					ob.put("view", subCompany.getScname());
					
					ob.put("status",subCompany.getScstatus());
					ar.add(ob);
					System.out.println("ob--- "+ob);
				}				
			}
			 data.put("recordsTotal",subComp.getTotalElements());
			 data.put("recordsFiltered",subComp.getTotalElements());
			 data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}




	@Override
	public JSONObject viewBlockCategorySuppliers(HttpServletRequest req, Long supplierID) {
		JSONObject data = new JSONObject();
		try {			
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long companyID = new Long(user.getCompanyCode());
		System.out.println("Long----- "+companyID);
		
		data.put("draw", req.getParameter("draw"));
		String sorting = "";
		switch (req.getParameter("order[0][column]")) {
			case "0":
			sorting = "sd.supplierName";
			break;
		}
		    
		Page<Object[]> subPro= null;
		
		if (req.getParameter("order[0][dir]").equals("asc")) {
			subPro =supplierProductDetailsRepo.viewBlockCategorySuppliersForSupplierID(req.getParameter("search[value]"), supplierID, PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		} 
		else {
			subPro =supplierProductDetailsRepo.viewBlockCategorySuppliersForSupplierID(req.getParameter("search[value]"), supplierID, PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		}
				
		JSONArray arr = new JSONArray();
		int i =0;

		long count = subPro.getTotalElements();
		JSONObject prices = new JSONObject(); 
		System.out.println("get cn--->" + subPro.getContent().size());
		for (Object[] objects : subPro.getContent()) {
			
			  if (objects[0] instanceof SupplierProductDetails) {
			  System.out.println("Inside for --->" + data);
			  
			  SupplierProductDetails supplierProductDetails = (SupplierProductDetails)objects[0]; 
			  EligibleCategory ecat = (EligibleCategory) objects[1];
			  EligibleSubCategory esubcat = (EligibleSubCategory) objects[2];
			  SupplierDetails supplierDetails = (SupplierDetails) objects[3];
			  SubCompany subCom = (SubCompany) objects[4];
			  			  
			  JSONObject ob = new JSONObject(); 
			  ob.put("index", ++i);
			  ob.put("categoryID",ecat.getEligibleCategortID());
			  ob.put("supplierID",supplierDetails.getSupplierID());
			  
			  ob.put("supplierName",subCom.getScname());
			  ob.put("eligibleSubCategoryName",esubcat.getEligibleSubcatname());
			  ob.put("eligibleCategortName",ecat.getEligibleCategortName());
			  ob.put("categoryFee",supplierProductDetails.getCategoryFee());
			  
			  prices.put("grandTotal", supplierProductDetails.getGrandTotal());
			  prices.put("categoryTotal", supplierProductDetails.getCategoryFee());
			  prices.put("subCategoryTotal", supplierProductDetails.getSubcategoryFee());
			  prices.put("grandTotal",supplierProductDetails.getGrandTotal());
			  
			  arr.add(ob); 
			  }
			 }
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", arr);
			data.put("prices", prices);
			System.out.println("data ds11111--->" + data);
			return data;
		}catch (Exception e) {
		 e.printStackTrace();
		 return null;
	   }
	}




	@Override
	public JSONObject unblockSupplier(JSONObject data) {
		JSONObject ob = new JSONObject();
		String msg = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User us = (User) authentication.getPrincipal();
		String userId = (us.getUserid());
		
		try {
			Long supplierID = Long.valueOf(data.get("selectSupplierID").toString());
			
			List<User> userList =	userRepo.findByCompanyCode(data.get("selectSupplierID").toString());
			if (userList != null) {
				for (User user : userList) {
					  user.setStatus(Integer.valueOf("22"));
					  userRepo.saveAndFlush(user);
				}
			} 
			
			List<UserRole> userRoleList =	userRoleRepo.findByCompanyCode(Long.valueOf(data.get("selectSupplierID").toString()));
			if (userRoleList != null) {
				for (UserRole userrole : userRoleList) {
					userrole.setUserRoleStatus(Integer.valueOf("22"));
					  userRoleRepo.saveAndFlush(userrole);
				}
			} 
			
			Optional<SubCompany> subcom = subCompanyRepo.findById(supplierID);
			if(subcom.isPresent()) {
				
				subcom.get().setUnblockcomment(data.get("unblockReason").toString());

				subcom.get().setScstatus("RW");
				subcom.get().setApprovedBy(us.getUserid());
				subcom.get().setApproveDateTime(LocalDateTime.now());
				subCompanyRepo.save(subcom.get());
			}	
				
			Optional<SupplierDetails> supplierDetailsOptional = supplirDetailsRepo.findBySubCompanyID(supplierID);
			if(supplierDetailsOptional.isPresent()) {
				SupplierDetails supplierDetails =supplierDetailsOptional.get();
				supplierDetails.setSupplierStatus("22");
				supplirDetailsRepo.saveAndFlush(supplierDetails);
			}
		
			ob.put("responseText", "Success");	
			ob.put("code", "01");
				
		}catch (Exception e) {
		    e.printStackTrace();
			ob.put("msg", "falied");
			ob.put("code", "02");
			return ob;
		}
		return ob;
	}




	@Override
	public JSONObject addSupplierComment(JSONObject data, String parameter) {
		JSONObject ob = new JSONObject();
		String msg = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User us = (User) authentication.getPrincipal();
		String userId = (us.getUserid());
		
		try {
			System.out.println(data.get("selectedSupplierID").toString());
			Long supplierID = Long.valueOf(data.get("selectedSupplierID").toString());
			System.out.println("Test---> "+data.get("selectedSupplierID").toString()+" "+data.get("selectedSupplierAdminEmail").toString());
			Optional<User> optionalUser =	userRepo.findByCompanyCodeAndEmail(data.get("selectedSupplierID").toString(), data.get("selectedSupplierAdminEmail").toString());
			if (optionalUser.isPresent()) {
				User adminUser = optionalUser.get();
				UserRole newUserRole = new UserRole();
				newUserRole.setUserRoleID(3);
				adminUser.setUserRoll(newUserRole);
				userRepo.save(adminUser);
			}
			
			Optional<SubCompany> subcom = subCompanyRepo.findById(supplierID);
			if(subcom.isPresent()) {
				System.out.println("Add supplier comment");
				subcom.get().setComment(data.get("comment").toString());
				subcom.get().setAmount(Double.valueOf(data.get("amount").toString()));
				subcom.get().setIspaid(Boolean.valueOf(data.get("payment").toString()));
				
				if(parameter.equals("approve")) {
					subcom.get().setScstatus("Y");
					JSONObject item = new JSONObject();
					item.put("selectSupplierID", supplierID);
					item.put("reason", data.get("comment").toString());
					JSONObject returnJO = approveSupplierWhiteList(item);
					if(returnJO.get("msg").equals("failed")) {
						throw new Exception();
					}
					
				}else  if(parameter.equals("reject")){
					subcom.get().setScstatus("R");
					JSONObject item = new JSONObject();
					item.put("selectSupplierID", supplierID);
					item.put("reason", data.get("comment").toString());
					JSONObject returnJO = blockSupplier(item);
					System.out.println("returnJO-- "+returnJO);
					if(returnJO.get("msg").equals("failed")) {
						throw new Exception();
					}
					
				}else {
					System.out.println("Other value--> "+parameter);
				}
				
				subcom.get().setApprovedBy(us.getUserid());
				subcom.get().setApproveDateTime(LocalDateTime.now());
				subCompanyRepo.save(subcom.get());
				
				
				GlobalDetails gloDetails = pglogRepo.findByIndexNo("1");			
				ArrayList<String> catList = new ArrayList<>();
				ArrayList<String> subList = new ArrayList<>();
				System.out.println("test value--> "+parameter);
				List<Object[]> supProductList = supplierProductDetailsRepo.getDetailsBySupplierID(Long.valueOf(data.get("selectedSupplierID").toString()));
				for (Object[] objects : supProductList) {
					
					  if (objects[1] instanceof EligibleCategory && objects[2] instanceof EligibleSubCategory) {
					  System.out.println("Inside for --->" + data);
					  JSONObject oba = new JSONObject();
					  EligibleCategory ecat = (EligibleCategory) objects[1];
					  EligibleSubCategory esubcat = (EligibleSubCategory) objects[2];
					  subList.add(esubcat.getEligibleSubcatname());
					  catList.add(ecat.getEligibleCategortName());
					  }
				}
				 
				
				 String subject = "BidPro Portal";

					
					String content0 = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Dear Sir / Madam,</span></p>";
					String LineSpace ="<p style=\"font-size: 14px; line-height: 140%;\"> </p>";
					String content1 = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Supplier Registration Process is Completed.</span></p>";
					String content2 = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Please use bellow URL to login</span></p>";
					String content3 = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">"+gloDetails.getLoginUrl()+"</span></p>"; 
					String regisNumber = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Registration Number : "+subcom.get().getScregistrationno()+"</span></p>";
					
					/*<ul>
					<li style="font-size: 14px; line-height: 19.6px; text-align: left;"><span style="font-size: 14px; line-height: 19.6px;"><span style="font-size: 18px; line-height: 25.2px; color: #666666;"><sub>bullets</sub></span></span></li>
					<li style="font-size: 14px; line-height: 19.6px; text-align: left;"><span style="font-size: 14px; line-height: 19.6px;"><span style="font-size: 18px; line-height: 25.2px; color: #666666;"><sub>bullets</sub></span></span></li>
					</ul>*/
					//ordered list
					/*<ol>
					<li style="font-size: 14px; line-height: 19.6px;">number</li>
					<li style="font-size: 14px; line-height: 19.6px;">number</li>
					<li style="font-size: 14px; line-height: 19.6px;">number</li>
					</ol>*/
					
					String registedCats = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Registered Categories : " +catList.toString() +"</span></p>";
					String registedSubCats = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Registered Sub Categories : " +subList.toString() +"</span></p>";
					
					String regisDate = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Registration Date : "+subcom.get().getScregdate()+"</span></p>";
					String approvalDate = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Approval Date : "+subcom.get().getApproveDateTime()+"</span></p>";
					String content4 = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Your Supplier Registration with NDB Bank has been Successfully Approved.</span></p>";
					String content5 = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Now you can participate in Tender Process.</span></p>";
					String content6 = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Thank you,</span></p>";
					String content7 = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Team BidPro</span></p>";
				
					String content = content0 + LineSpace+ content1 + LineSpace + content2 + content3 + LineSpace + regisNumber + registedCats + registedSubCats + regisDate + approvalDate + LineSpace + content4 + LineSpace + content5 + LineSpace + content6 + content7;
					CommonEmail comEmail = new CommonEmail("Welcome BidPro Portal - Registration Approved", content);
					
					System.out.println(comEmail.getHeadingName());
					System.out.println(comEmail.getContent());
					System.out.println("getContentOfEmail"+comEmail.getContentOfEmail());
					System.out.println("getHeadingOfEmail"+comEmail.getHeadingOfEmail());
					
					
					String body = comEmail.getFirstPart()+comEmail.getHeadingOfEmail()+comEmail.getSecondPart()+comEmail.getContentOfEmail()+comEmail.getThirdPart();
					
					 new Thread(new Runnable() {
						
						@Override
						public void run() {
							
							List<String> to = new ArrayList<String>();
							to.add(subcom.get().getScemailadmin());
							to.add(subcom.get().getScemail());							
							common.sendEMailHtml(to, subject, body);
						}
					}).start();
				 
				ob.put("responseText", "Success");	
				ob.put("code", "01");
			}else {
				System.out.println("No Such Value");
			}
		}catch (Exception e) {
		     e.printStackTrace();
		     return null;
		}
		return ob;
	}




	@Override
	public Boolean getWetherSupplierExistsByregno(String enteredValue) {
		// TODO Auto-generated method stub
		return subCompanyRepo.existsByScregistrationno(enteredValue);
	}




	@Override
	public String supplierFileSubmit(JSONObject data, HttpServletRequest request, String filename) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String msg = null;
		String code = null;

		try { 
			
			Optional<SubCompany> optSub = subCompanyRepo.findById(Long.valueOf(data.get("supplierID").toString()));			
		//	Optional<SupplierDetails> optTs = supplirDetailsRepo.findBySubCompanyID(Long.valueOf(user.getCompanyCode()));
				
			
			System.out.println("out filename "+filename);
			
			if (optSub.isPresent()) {
				System.out.println("optTs.isPresent()");
			//	SubCompany newOb =  optTs.get();
		//		SupplierDetails supD =  optTs.get();
				System.out.println("optTs filename "+filename);
				
				if (filename.equals("imagelogo")) {
					System.out.println("inside filename "+filename);
					
					//Optional<SubCompany> imagelogoSc = subCompanyRepo.findById(Long.valueOf(user.getCompanyCode()));
					
					System.out.println("imagelogo getCompanyCode"+user.getCompanyCode().toString());
					System.out.println("imagelogo supplierID"+data.get("supplierID").toString());
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/logo",exten, head);
//					System.out.println("setScomapanylogo");
					msg = "Success";
				}
				
				
			/*	if (filename.equals("imagelogo")) {
					
					//Optional<SupplierDetails> imagelogoSd = supplirDetailsRepo.findBySubCompanyID(Long.valueOf(Long.valueOf(user.getCompanyCode())));
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/logo",exten, head);
//					System.out.println("setComapanyLogo");
					msg = "Success";
				}*/

				
				if (filename.equals("br")) {
					
					//Optional<SupplierDetails> brSd = supplirDetailsRepo.findBySubCompanyID(Long.valueOf(Long.valueOf(user.getCompanyCode())));
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/BRForm",exten, head);
//					System.out.println("setBrForm");
					msg = "Success";
				}
				
				
				if (filename.equals("kyc")) {
					
					//Optional<SupplierDetails> kycSd = supplirDetailsRepo.findBySubCompanyID(Long.valueOf(Long.valueOf(user.getCompanyCode())));
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/KYCForm",exten, head);
//					System.out.println("setKycForm");
					msg = "Success";
				}
				
				
				if (filename.equals("bci")) {
					
					//Optional<SupplierDetails> bciSd = supplirDetailsRepo.findBySubCompanyID(Long.valueOf(Long.valueOf(user.getCompanyCode())));
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/BCIForm",exten, head);
//					System.out.println("setBciForm");
					msg = "Success";
				}
				
				
				if (filename.equals("auditedreports")) {
					
				//	Optional<SupplierDetails> auditedreportsSd = supplirDetailsRepo.findBySubCompanyID(Long.valueOf(Long.valueOf(user.getCompanyCode())));
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/lastAuditedReports",exten, head);
//					System.out.println("setLastAuditedFinAcc");
					msg = "Success";
				}
				
			
				if (filename.equals("directordetails")) {
					
				//	Optional<SupplierDetails> directordetailsSd = supplirDetailsRepo.findBySubCompanyID(Long.valueOf(Long.valueOf(user.getCompanyCode())));

					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/DirectorDetails",exten, head);
//					System.out.println("setListofDirectors");
					msg = "Success";
				}
				
				
				if (filename.equals("companyprofile")) {
					
					//Optional<SupplierDetails> companyprofileSd = supplirDetailsRepo.findBySubCompanyID(Long.valueOf(Long.valueOf(user.getCompanyCode())));
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/CompanyProfile",exten, head);
//					System.out.println("setCompanyProfile");
					msg = "Success";
				}
				
				
				if (filename.equals("application")) {
					
					//Optional<SupplierDetails> applicationSd = supplirDetailsRepo.findBySubCompanyID(Long.valueOf(Long.valueOf(user.getCompanyCode())));
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/Application",exten, head);
//					System.out.println("setMainApplication");
					msg = "Success";
				}
				
				
				if (filename.equals("codeconduct")) {
					
				//	Optional<SupplierDetails> codeconductSd = supplirDetailsRepo.findBySubCompanyID(Long.valueOf(Long.valueOf(user.getCompanyCode())));
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/CodeConduct",exten, head);
//					System.out.println("setSupplierCodeofConduct");
					msg = "Success";
				}
				
				
				if (filename.equals("esquestions")) {
					
					//Optional<SupplierDetails> esquestionsSd = supplirDetailsRepo.findBySubCompanyID(Long.valueOf(Long.valueOf(user.getCompanyCode())));
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/ESQuestions",exten, head);
//					System.out.println("setSupplierESQuestions");
					msg = "Success";
				}

				
				optSub.get().setScomapanylogo(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/logo." + "pdf");
				
				
				subCompanyRepo.saveAndFlush(optSub.get());
				
				//msg = "Success";
			}else{								
			}
		} catch (Exception e) {
			e.printStackTrace();
			code = "01";
			msg = "failed";
		}
		return msg;
	}




	@Override
	public Boolean getWetherSupplierExistsBycontactno(String enteredValue) {
		// TODO Auto-generated method stub
		return subCompanyRepo.existsBySccontactno(enteredValue);
	}




	@Override
	public Boolean getWetherSupplierExistsByconemail(String enteredValue) {
		// TODO Auto-generated method stub
		return subCompanyRepo.existsByScemail(enteredValue);
	}



}
