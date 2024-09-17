package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.CompanySupplierMappingRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ContractDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ContractInvoiceHeaderRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.CompanySupplierMapping;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractInvoiceHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.service.DashBoardService;

@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
@EnableScheduling
public class DashBoardServiceImpl implements DashBoardService {
	@Autowired
	ContractDetailsRepo contractRepo;

	@Autowired
	ContractInvoiceHeaderRepo contractInvoHead;

	@Autowired
	CompanySupplierMappingRepo comSupMapping;
	
	@Autowired
	SubCompanyRepo subCompanyRepo;
	
	@Autowired
	UserRepo userRepo;

	@Override
	public JSONObject getSupliersCompanyContracts(HttpServletRequest request) {
		try {
			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();

			Page<Object[]> contract_detail = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "subc.scname";
				break;
			case "1":
				sorting = "contractId";
				break;
			case "2":
				sorting = "contractAmount";
				break;
			case "3":
				sorting = "createdAt";
				break;
			case "4":
				sorting = "expiryDate";
				break;
			case "5":
				sorting = "modifiedBy";
				break;
			case "6":
				sorting = "paymentType";
				break;
			case "7":
				sorting = "RenewalDatePeriod";
				break;
			}

//			cod.createdAt LIKE %?1% OR cod.expiryDate LIKE %?1% OR cod.modifiedBy LIKE %?1% OR cod.paymentType LIKE %?1% OR cod.RenewalDatePeriod
			if (request.getParameter("order[0][dir]").equals("asc")) {
//				 suppliers = contractInvoiceHeaderRepo.getContractForSuppliers( id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				contract_detail = contractRepo.getSupliresCompanyContract(request.getParameter("search[value]"),
						Long.valueOf(user.getCompanyCode()),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				contract_detail = contractRepo.getSupliresCompanyContract(request.getParameter("search[value]"),
						Long.valueOf(user.getCompanyCode()),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
//				contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			long count = contract_detail.getTotalElements();
			System.out.println("get cn--->" + contract_detail.getContent().size());
			for (Object[] objects : contract_detail.getContent()) {

				if (objects[1] instanceof CompanySupplierMapping && objects[0] instanceof ContractDetails
						&& objects[2] instanceof SubCompany) {

					JSONObject ob = new JSONObject();
					CompanySupplierMapping mapping = (CompanySupplierMapping) objects[1];
					ContractDetails contract = (ContractDetails) objects[0];
					SubCompany subCom = (SubCompany) objects[2];
//					ContractInvoice invoice = (ContractInvoice) objects[2];

					ob.put("contractNo", contract.getContractNo());
					ob.put("desc", contract.getContractDetails());
					ob.put("category", contract.getCategory());
					ob.put("paymentType", contract.getPaymentType());
					ob.put("amount", contract.getContractAmount());
					ob.put("annualPayment", contract.getAnnualPaymentAmountLKR());

					ob.put("createddate", contract.getCreatedAt());
					ob.put("expireDate", contract.getExpiryDate());
					ob.put("modifiedBy", contract.getModifiedBy());
					ob.put("paymentType", contract.getPaymentType());
					ob.put("renewal", contract.getRenewalDatePeriod());

					ob.put("companyName", subCom.getScname());

					ar.add(ob);

				}

			}
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			return data;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject getCompanysSuplierContracts(HttpServletRequest request) {
		try {
			
			System.out.println("Inside of companyContract");
			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();

			Page<Object[]> contract_detail = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "subc.scname";
				break;
			case "1":
				sorting = "contractId";
				break;
			case "2":
				sorting = "contractAmount";
				break;
			case "3":
				sorting = "createdAt";
				break;
			case "4":
				sorting = "expiryDate";
				break;
			case "5":
				sorting = "amcusd";
				break;
			case "6":
				sorting = "paymentType";
				break;
			case "7":
				sorting = "RenewalDatePeriod";
				break;
			}

//			cod.createdAt LIKE %?1% OR cod.expiryDate LIKE %?1% OR cod.modifiedBy LIKE %?1% OR cod.paymentType LIKE %?1% OR cod.RenewalDatePeriod
			if (request.getParameter("order[0][dir]").equals("asc")) {
//				 suppliers = contractInvoiceHeaderRepo.getContractForSuppliers( id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				contract_detail = contractRepo.getCompanysSuplireContract(request.getParameter("search[value]"),
						Long.valueOf(user.getCompanyCode()),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				contract_detail = contractRepo.getCompanysSuplireContract(request.getParameter("search[value]"),
						Long.valueOf(user.getCompanyCode()),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
//				contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			long count = contract_detail.getTotalElements();
			System.out.println("get cn--->" + contract_detail.getContent().size());
			for (Object[] objects : contract_detail.getContent()) {

				if (objects[1] instanceof CompanySupplierMapping && objects[0] instanceof ContractDetails
						&& objects[2] instanceof SubCompany) {

					JSONObject ob = new JSONObject();
					CompanySupplierMapping mapping = (CompanySupplierMapping) objects[1];
					ContractDetails contract = (ContractDetails) objects[0];
					SubCompany subCom = (SubCompany) objects[2];
//					ContractInvoice invoice = (ContractInvoice) objects[2];

					ob.put("contractNo", contract.getContractNo());
					ob.put("desc", contract.getContractDetails());
					ob.put("category", contract.getCategory());
					ob.put("paymentType", contract.getPaymentType());
					ob.put("amount", contract.getContractAmount());
					ob.put("amount_usd", contract.getAmcusd());
					ob.put("annualPayment", contract.getAnnualPaymentAmountLKR());

					ob.put("createddate", contract.getCreatedAt());
					ob.put("expireDate", contract.getExpiryDate());
					ob.put("modifiedBy", contract.getModifiedBy());
					ob.put("paymentType", contract.getPaymentType());
					ob.put("renewal", contract.getRenewalDatePeriod());

					ob.put("companyName", subCom.getScname());

					ar.add(ob);

				}

			}
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			return data;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject getSummaryBoxValues_sup(HttpServletRequest request) {
		try {
			System.out.println("in summrayBox values---->1");
			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			long suppcode = Long.valueOf(user.getCompanyCode());
			long contractcount = contractRepo.getsuplierContractCount(suppcode);
			long suppInvoCount = contractInvoHead.getsuplierinvocount(suppcode);
			long suppComCount = comSupMapping.getCompanyCountOfSupplier(suppcode);
			long suppPayCount = contractInvoHead.getsuplierpaymentcount(suppcode);
			data.put("contracts", contractcount);
			data.put("invoices", suppInvoCount);
			data.put("companies", suppComCount);
			data.put("payments", suppPayCount);
			System.out.println("in summrayBox values---->" + data);

			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public JSONObject getSummaryBoxValues_com(HttpServletRequest request) {
		try {
			System.out.println("in summrayBox values---->1");
			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			long suppcode = Long.valueOf(user.getCompanyCode());
			long contractcount = contractRepo.getcompanyContractCount(suppcode);
			long suppInvoCount = contractInvoHead.getcompanyinvocount(suppcode);
			long suppComCount = comSupMapping.getSupplierCountOfCompany(suppcode);
			long suppPayCount = contractInvoHead.getcompanypaymentcount(suppcode);
			data.put("contracts", contractcount);
			data.put("invoices", suppInvoCount);
			data.put("companies", suppComCount);
			data.put("payments", suppPayCount);
			System.out.println("in summrayBox values company---->" + data);

			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject getSummaryChartData_sup(HttpServletRequest request) {
		System.out.println("in ds service summerchart");
		JSONObject data_obj = new JSONObject();
		JSONArray data_con_to = new JSONArray();
		JSONArray data_con_mo = new JSONArray();
		JSONArray data_invoto = new JSONArray();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			String comCode = user.getCompanyCode();
			long com_code = Long.valueOf(comCode);
			List<Object[]> chartdata = contractRepo.getSUpplierSummerChart_supplier(com_code);

			for (Object[] objects : chartdata) {
				System.out.println("in the object---->" + objects[0] + " othe val" + objects[1]);
				if (objects[0] instanceof Integer & objects[1] instanceof BigDecimal) {
					System.out.println("in the object in if---->" + objects[0]);

					data_con_mo.add(objects[0]);
					data_con_to.add(objects[1]);
					data_invoto.add(objects[2]);
				}
			}
			data_obj.put("subtotals", data_con_to);
			data_obj.put("months", data_con_mo);
			data_obj.put("inototal", data_invoto);
			data_obj.put("status", "1");
			data_obj.put("des", "OK");
			return data_obj;
		} catch (Exception e) {
			e.printStackTrace();
			data_obj.put("status", "0");
			data_obj.put("des", e);
			return data_obj;
			// TODO: handle exception
		}

	}

	@Override
	public JSONObject getSupplierNextInvoices(HttpServletRequest request) {
		JSONObject data = new JSONObject();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			long supplierid = Long.valueOf(user.getCompanyCode());

			System.out.println("Inside of invoice method");

			Page<Object[]> supp_nextInvo = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

//			subcom.`sc_name`,codt.`created_at`,codt.`contract_id`,codt.`m_reminder_date`,invohd.`i_total`,invohd.`i_date` 

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "subcom.sc_name";
				break;
			case "1":
				sorting = "codt.created_at";
				break;
			case "2":
				sorting = "codt.contract_id";
				break;
			case "3":
				sorting = "codt.m_reminder_date";
				break;
			case "4":
				sorting = "invohd.i_total";
				break;
			case "5":
				sorting = "invohd.i_date";
				break;

			}

			if (request.getParameter("order[0][dir]").equals("asc")) {
//				supp_nextInvo= contractRepo.getSupliresCompanyContract(request.getParameter("search[value]"), Long.valueOf(user.getCompanyCode()), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				supp_nextInvo = contractRepo.getSupplierNextInvoices(request.getParameter("search[value]"), supplierid,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				supp_nextInvo = contractRepo.getSupliresCompanyContract(request.getParameter("search[value]"),
						Long.valueOf(user.getCompanyCode()),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
//				supp_nextInvo=contractRepo.getSupplierNextInvoices(request.getParameter("search[value]"), supplierid, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			long count = supp_nextInvo.getTotalElements();
			System.out.println("get invo data--->" + supp_nextInvo.getContent().size());
			for (Object[] objects : supp_nextInvo.getContent()) {
				System.out.println("indide for loop--->" + objects[0]);
				if (objects[0] instanceof String && objects[1] instanceof Date) {

					JSONObject ob = new JSONObject();
					/*
					 * CompanySupplierMapping mapping = (CompanySupplierMapping) objects[1];
					 * ContractDetails contract = (ContractDetails) objects[0]; SubCompany
					 * subCom=(SubCompany) objects[2];
					 */
//					ContractInvoice invoice = (ContractInvoice) objects[2];

					ob.put("companyName", objects[0]);
					ob.put("createddate", objects[1]);
					ob.put("contractNo", objects[2]);
					ob.put("invoicedate", objects[3]);
					ob.put("amount", objects[4]);
					ob.put("lastinvo", objects[5]);

					ar.add(ob);

				}

			}
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			System.out.println("data sup invo--->" + data);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}

	}

	@Override
	public JSONObject getSuppliersCompanyes(HttpServletRequest request) {
		System.out.println("inside suppliers com");
		JSONObject data = new JSONObject();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			long companyID = Long.valueOf(user.getCompanyCode());

			System.out.println("Inside of invoice method");

			Page<Object[]> supp_company = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "subc.subCompanyID";
				break;
			case "1":
				sorting = "subc.scname";
				break;

			} 

			if (request.getParameter("order[0][dir]").equals("asc")) {
				supp_company = comSupMapping.getSuppliersAllCompanyes(request.getParameter("search[value]"), companyID,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				supp_company = comSupMapping.getSuppliersAllCompanyes(request.getParameter("search[value]"),
						Long.valueOf(user.getCompanyCode()),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			long count = supp_company.getTotalElements();
			System.out.println("get supp com data--->" + supp_company.getContent().size());
			for (Object[] objects : supp_company.getContent()) {
				System.out.println("indide for loop--->" + objects[0]);
				if (objects[0] instanceof SubCompany) {

					SubCompany subc=(SubCompany) objects[0];
					JSONObject ob = new JSONObject();

					ob.put("companyName", subc.getScname());
					ob.put("companycode", subc.getSubCompanyID());
					

					ar.add(ob); 

				}

			}
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
	}

	@Override
	public JSONObject getCompanyesSuppliers(HttpServletRequest request) {
		JSONObject data = new JSONObject();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			long companyID = Long.valueOf(user.getCompanyCode());

			System.out.println("Inside of invoice method");

			Page<Object[]> supp_company = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "subc.subCompanyID";
				break;
			case "1":
				sorting = "subc.scname";
				break;

			} 

			if (request.getParameter("order[0][dir]").equals("asc")) {
				supp_company = comSupMapping.getCompanyesAllSuppliers(request.getParameter("search[value]"), companyID,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				supp_company = comSupMapping.getCompanyesAllSuppliers(request.getParameter("search[value]"),
						Long.valueOf(user.getCompanyCode()),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			long count = supp_company.getTotalElements();
			System.out.println("get supp com data--->" + supp_company.getContent().size());
			for (Object[] objects : supp_company.getContent()) {
				System.out.println("indide for loop--->" + objects[0]);
				if (objects[0] instanceof SubCompany) {

					SubCompany subc=(SubCompany) objects[0];
					JSONObject ob = new JSONObject();

					ob.put("companyName", subc.getScname());
					ob.put("companycode", subc.getSubCompanyID());
					

					ar.add(ob); 

				}

			}
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
	}

	@Override
	public JSONObject getCompanyespendingInvoice(HttpServletRequest request) {
		
		JSONObject data = new JSONObject();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			long companyID = Long.valueOf(user.getCompanyCode());

			System.out.println("Inside of invoice method");

			Page<Object[]> com_pinvo = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "sub.scname";
				break;
			case "1":
				sorting = "co_inhd.contractno";
				break;
			case "2":
				sorting = "co_inhd.total";
				break;
			case "3":
				sorting = "co_inhd.createdate";
				break;
			case "4":
				sorting = "co_inhd.invoicedate";
				break;
			} 

			if (request.getParameter("order[0][dir]").equals("asc")) {
				com_pinvo = contractInvoHead.getCompanyspendingInvoice(request.getParameter("search[value]"), companyID,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				com_pinvo = contractInvoHead.getCompanyspendingInvoice(request.getParameter("search[value]"),
						Long.valueOf(user.getCompanyCode()),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			long count = com_pinvo.getTotalElements();
			System.out.println("get supp com data--->" + com_pinvo.getContent().size());
			
			for (Object[] objects : com_pinvo.getContent()) {
				System.out.println("indide for loop--->" + objects[0]);
				if (objects[0] instanceof ContractInvoiceHeader & objects[1] instanceof CompanySupplierMapping & objects[2] instanceof SubCompany) {

					ContractInvoiceHeader coInvHd=(ContractInvoiceHeader) objects[0];
					CompanySupplierMapping maping=(CompanySupplierMapping) objects[1];
					SubCompany subc=(SubCompany) objects[2];
					
					JSONObject ob = new JSONObject();

					ob.put("companyName", subc.getScname());
					ob.put("companycode", subc.getSubCompanyID());
					ob.put("contractnumber", coInvHd.getContractno());
					ob.put("amount", coInvHd.getTotal());
					ob.put("createDate", coInvHd.getCreatedate());
					ob.put("invoDate", coInvHd.getInvoicedate());
					
					

					ar.add(ob); 

				}

			}
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
	}

	@Override
	public JSONObject getAllCompanies(HttpServletRequest request,boolean identity) {
		try {
			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			
			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "scname";
				break;
			case "1":
				sorting = "scaddress1";
				break;
			case "2":
				sorting = "scemailadmin";
				break;
			case "3":
				sorting = "scsystemregistereddate";
				break;
			} 
			Page<SubCompany> dbData = null;
			System.out.println("test------------------------------------------------------------------------------"+user.getCompanyCode());
			if (request.getParameter("order[0][dir]").equals("asc")) {
				dbData = subCompanyRepo.getAllCompanies(Long.valueOf(user.getCompanyCode()) ,identity, request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")),Sort.by(Sort.Direction.ASC, sorting)));
			}else {
				dbData = subCompanyRepo.getAllCompanies(Long.valueOf(user.getCompanyCode()),identity, request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")),Sort.by(Sort.Direction.DESC, sorting)));
			}
			
			JSONArray arr = new JSONArray();
			
			for (SubCompany sub : dbData.getContent()) {
				
					JSONObject ob = new JSONObject();

					ob.put("companyName", sub.getScname());
					ob.put("contactNo", sub.getScphoneno1());
					ob.put("email", sub.getScemailadmin());
					ob.put("createddate", sub.getScsystemregistereddate());
					ob.put("userCount", userRepo.countByCompanyCode(sub.getSubCompanyID()+""));
					
					arr.add(ob); 
				
			}
			data.put("recordsTotal", dbData.getTotalElements());
			data.put("recordsFiltered", dbData.getTotalElements());
			data.put("data", arr);
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public JSONObject getAllCounts(String type) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			JSONObject data = new JSONObject();
			if (type.equalsIgnoreCase("companies")) {
				data.put("companies", subCompanyRepo.countByIdentityAndSubCompanyIDNot(true,Long.valueOf(user.getCompanyCode())));
				data.put("usrCount", userRepo.getUserCountsByCompanyType(true,Long.valueOf(user.getCompanyCode())));
				
			}else {
				data.put("companies", subCompanyRepo.countByIdentityAndSubCompanyIDNot(false,Long.valueOf(user.getCompanyCode())));
				data.put("usrCount", userRepo.getUserCountsByCompanyType(false,Long.valueOf(user.getCompanyCode())));
			}
			
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
