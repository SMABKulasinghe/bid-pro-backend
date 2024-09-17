package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.AppzEligibleCodesRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.AppzTenderSubmissionsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.BoardPaperUploadRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EligibleCategoryRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EvaluationMarksAllRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.FinancialCodesRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.FinancialDetailsPerTenderRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.FinancialResponseRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.FinancialValuesPerTenderRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ProcurementCommitteeRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ProcurementRequestRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.QrCodeDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpDetailsResponseRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TenderDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.comman.Common;
import lk.supplierUMS.SupplierUMS_REST.comman.CommonEmail;
import lk.supplierUMS.SupplierUMS_REST.comman.DeEnCode;
import lk.supplierUMS.SupplierUMS_REST.entity.AppzEligibleCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.AppzTenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.BoardPaperUpload;
import lk.supplierUMS.SupplierUMS_REST.entity.City;
import lk.supplierUMS.SupplierUMS_REST.entity.EPoHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.Province;
import lk.supplierUMS.SupplierUMS_REST.entity.QrCodeDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.RFP;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetailsResponse;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.SupplierDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategoryProducts;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationMarksAll;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationSheetCreate;
import lk.supplierUMS.SupplierUMS_REST.entity.FinacialResponsesVendor;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialDetailsPerTender;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialValuesPerTender;
import lk.supplierUMS.SupplierUMS_REST.entity.ProcurementCommittee;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.SupplierProductDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.TermsAndConditions;
import lk.supplierUMS.SupplierUMS_REST.entity.CompanySupplierMapping;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.Department;
import lk.supplierUMS.SupplierUMS_REST.entity.District;
import lk.supplierUMS.SupplierUMS_REST.entity.EPoHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;

import lk.supplierUMS.SupplierUMS_REST.entity.SupplierProductDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderStatusDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.service.ProcurementService;
import lk.supplierUMS.SupplierUMS_REST.service.QrCodeService;
import lk.supplierUMS.SupplierUMS_REST.service.TenderDetailsService;
import lk.supplierUMS.SupplierUMS_REST.entity.ProcurementRequest;

import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class QrCodeServiceImpl implements QrCodeService {

	@Autowired
	QrCodeDetailsRepo qrCodeDetailsRepo;
	
	@Value("${qrCode.doc.path}")
	String qrCodeDataPath;
	
	@Autowired
	Common common;
	
	@Override
	public JSONObject getQrCodeTableDetails(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String userId = user.getUserid(); 
		
		LocalDate NowDate = LocalDate.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formatedDate = NowDate.format(myFormatObj);
		LocalDate NowDateDone = LocalDate.parse(formatedDate);
		
		try {

			JSONObject data = new JSONObject();
			
			
			Page<QrCodeDetails> qrCodeDetails = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "1":
				sorting = "name";
				break;
			case "2":
				sorting = "email";
				break;
			case "3":
				sorting = "tpNumber";
				break;
			case "4":
				sorting = "ticketNumber";
				break;

			}
			
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				qrCodeDetails = qrCodeDetailsRepo.getQrCodeTableDetails(request.getParameter("search[value]"),NowDateDone,
						
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
										Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));

				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				qrCodeDetails = qrCodeDetailsRepo.getQrCodeTableDetails(request.getParameter("search[value]"),NowDateDone,
						
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
										Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
				
			}

			JSONArray ar = new JSONArray();
			long count = qrCodeDetails.getSize();
			//lo
			System.out.println("get cn--->" + qrCodeDetails.getContent().size());
			int place = 1;
			
			for (QrCodeDetails objects : qrCodeDetails.getContent()) {
					
					JSONObject qrCode = new JSONObject();
					qrCode.put("name", objects.getName());
					qrCode.put("email",objects.getEmail());
					qrCode.put("phoneNumber",objects.getTpNumber());
					qrCode.put("ticketNumber",objects.getTicketNumber());
					qrCode.put("id", objects.getQrId());
					qrCode.put("status", objects.getStatus());
					qrCode.put("accessedDate", objects.getAccessedDate());
					qrCode.put("accessedTime", objects.getAccessedTime());
					qrCode.put("ticketDate", objects.getTicketDate());
					qrCode.put("index", place);
					 place++;
					ar.add(qrCode);

			}
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			return data;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	@Override
	public JSONObject setQrStatusToPresent(long id, String approve) {
		Optional<QrCodeDetails> qr = qrCodeDetailsRepo.findById(id);
		JSONObject ob = new JSONObject();
		Long active = (long) 2;
		
		try {

			if (qr.isPresent()) {

				if (approve.equals("active")) {

					qr.get().setStatus(active);
					qr.get().setAccessedDate(LocalDate.now());
					qr.get().setAccessedTime(LocalTime.now());
					qrCodeDetailsRepo.save(qr.get());

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ob;
		}
		return ob;
	}

	@Override
	public JSONObject setQrStatusToPresentFromQr(long id, String approve) {
		System.out.println("id----------------"+id);
		String str = Long.toString(id);
		System.out.println("idStr----------------"+str);
		
		LocalDate NowDate = LocalDate.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formatedDate = NowDate.format(myFormatObj);
		LocalDate NowDateDone = LocalDate.parse(formatedDate);
		
		Optional<QrCodeDetails> qr = qrCodeDetailsRepo.findByTicketNumber(id);
		JSONObject ob = new JSONObject();
		Long active = (long) 2;
		
		System.out.println("Name----------------"+qr.get().getName());
		
		LocalDate ticketDate1 = qr.get().getTicketDate(); 
		String ticketDate2 =  ticketDate1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				//
		System.out.println("ticketDate--- "+qr.get().getTicketDate());
		System.out.println("NowDate--- "+NowDateDone);
		
		try {

			if (qr.isPresent()) {

				if  (approve.equals("qrcode")) {
					
					if(qr.get().getStatus()==1 && ticketDate1.isEqual(NowDateDone)) {
						
						qr.get().setStatus(active);
						qr.get().setAccessedDate(LocalDate.now());
						qr.get().setAccessedTime(LocalTime.now());
						qrCodeDetailsRepo.save(qr.get());
						
						ob.put("results", "Success");
						
					}else if(qr.get().getStatus()==2 && ticketDate1.isEqual(NowDateDone)) {
						
						ob.put("results", "AlreadExists");
						
					}else if(!ticketDate1.isEqual(NowDateDone)) {
						
						ob.put("results", "NotTodayTicket");
						
					}
					
					
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ob;
		}
		return ob;
	}

	@Override
	public JSONObject sendEmailAndGenerateQr(HttpServletRequest request) {
		
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		try {
			
			List<QrCodeDetails> qrs = qrCodeDetailsRepo.getAll();
			
			for (QrCodeDetails qr : qrs) {	
				
				Long ticketNumber = qr.getQrId() + 500;
				
				String text = String.valueOf(ticketNumber);
				
				BitMatrix bitMtrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
				MatrixToImageWriter.writeToPath(bitMtrix,"png", FileSystems.getDefault().getPath(qrCodeDataPath+qr.getQrId()+".png"));
				
				//qr.setAccessedDate(LocalDate.now());
				//qr.setAccessedTime(LocalTime.now());
				qr.setTicketNumber(ticketNumber);
				qr.setQrPath(qrCodeDataPath+qr.getQrId()+".png");
				qr.setEmailStatus(2);
				qrCodeDetailsRepo.save(qr);
				
				String subject = "Invitation for 40th National IT Conference 2022";
				String body1 = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"
						+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\r\n"
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
						+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:25px 74px 5px 0px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
						+ "        \r\n"
						+ "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
						+ "  <tr>\r\n"
						+ "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n"
						+ "      \r\n"
						+ "      <!-- <img align=\"center\" border=\"0\" src=\"https://www.ndbbank.com/images/logo.svg\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 72%;max-width: 378.72px;\" width=\"378.72\"/> -->\r\n"
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
						+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
						+ "        \r\n"
						+ "  <h1 style=\"margin: 0px; color: #ffffff; line-height: 140%; text-align: center; word-wrap: break-word; font-weight: normal; font-family: arial,helvetica,sans-serif; font-size: 22px;\">\r\n"
						+ "    Invitation for 40th National IT Conference 2022\r\n"
						+ "  </h1>\r\n"
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
						+ "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
						+ "    \r\n"
						+ "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 13px; line-height: 25.2px; color: #666666;\"></span></p>\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 13px; line-height: 25.2px; color: #666666;\">Participant - "+qr.getName()+"</span></p>\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 13px; line-height: 25.2px; color: #666666;\">Ticket Valid Date - "+qr.getTicketDate()+"</span></p>\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 13px; line-height: 25.2px; color: #666666;\">Ticket No. - "+qr.getTicketNumber()+"</span></p>\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 13px; line-height: 25.2px; color: #666666;\">Location - Shangri-La, Colombo, Sri Lanka.</span></p>\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%; text-align: center;\"><span style=\"font-size: 14px; line-height: 19.6px;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\"><sub>";
				String body2 = "</sub></span></span></p>\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"> </p>\r\n"
						+ "\r\n"
						+ "<!-- <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">I refer to the tender submitted by your company for <strong>{tenderName}</strong></span></p>\r\n"
						+ "\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">I am pleased to inform you that the NDB Bank Corporation has, after prudent consideration of your tender, decided to award the contract to your company.</span></p>\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"> </p>\r\n"
						+ "\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Please contact  Mr/Miss/Mrs<strong> {cordinatorname} </strong>- <strong>{ContactNumber} </strong></span><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">of the NDB Bank Corporation.</span></p>\r\n"
						+ "\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"> </p>\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Thank You!</span></p>\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"> </p> -->\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "  </div>\r\n"
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
						+ "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #888888; font-size: 14px; line-height: 19.6px;\"><em><span style=\"font-size: 16px; line-height: 22.4px;\">Please don't reply to this email.</span></em></span><br /><span style=\"color: #888888; font-size: 14px; line-height: 19.6px;\"><em><span style=\"font-size: 16px; line-height: 22.4px;\"> </span></em></span></p>\r\n"
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
						+ "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">Computer Society Of Sri Lanka,</span></p>\r\n"
						+ "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">275/75,</span></p>\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">Prof. Stanley Wijesundara Mawatha,</span></p>\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">Colombo 7,</span></p>\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">Sri Lanka.</span></p>\r\n"
						+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">(+94) 114 713 290 / info@cssl.lk</span></p>\r\n"
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
						+ "    <p style=\"line-height: 140%; font-size: 14px; text-align: left;\"> </p>\r\n"
						+ "<p style=\"line-height: 140%; font-size: 14px; text-align: left;\"> </p>\r\n"
						+ "<p style=\"line-height: 140%; font-size: 14px; text-align: left;\"> </p>\r\n"
						+ "<p style=\"line-height: 140%; font-size: 14px; text-align: left;\"> </p>\r\n"
						+ "<p style=\"line-height: 140%; font-size: 14px; text-align: left;\"><span style=\"font-size: 14px; line-height: 19.6px;\"><span style=\"color: #ecf0f1; font-size: 14px; line-height: 19.6px;\"><span style=\"line-height: 19.6px; font-size: 14px;\"></span></span></span></p>\r\n"
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
				
				if(qr.getEmail() != null) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							
								List<String> to = new ArrayList<String>();
								to.add(qr.getEmail());
								
								common.sendEMailHtmlWithAttachment(to, subject, body1,body2,qr.getQrPath());
							}
					}).start();
				}
				
					
			}
			
			
		} catch (WriterException | IOException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public JSONObject getQrCount() {
		JSONObject ob = new JSONObject();
		LocalDate NowDate = LocalDate.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formatedDate = NowDate.format(myFormatObj);
		LocalDate NowDateDone = LocalDate.parse(formatedDate);
		try {

			List<QrCodeDetails> qr = qrCodeDetailsRepo.getAttendedParticipants(NowDateDone);
			int attendCount = qr.size();
			List<QrCodeDetails> qr2 = qrCodeDetailsRepo.getPendingParticipants(NowDateDone);
			int pendingCount = qr2.size();
			List<QrCodeDetails> qr3 = qrCodeDetailsRepo.getAttendedPendingParticipants(NowDateDone);
			int attendPendingCount = qr3.size();
			System.out.println("attendCount- "+attendCount);
			System.out.println("pendingCount- "+pendingCount);
			
			ob.put("attendCount", attendCount);
			ob.put("pendingCount", pendingCount);
			ob.put("attendPendingCount", attendPendingCount);

		} catch (Exception e) {
			e.printStackTrace();
			return ob;
		}
		return ob;
	}

}
