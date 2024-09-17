package lk.supplierUMS.SupplierUMS_REST.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.ContractDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.MITDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TenderDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.MITDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SupplirDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.SupplierDetails;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TenderDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.CrudRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;


@CrossOrigin
@RestController
@RequestMapping(value = "/image")
public class ImageController {
	
	
	@Value("${company.data.path}")
	String companyDataPath;
	
	@Autowired
	SubCompanyRepo subCompanyRepo;
	
	@Autowired
	ContractDetailsRepo contractRepo;
	
	@Autowired
	TenderDetailsRepo tenderRepo;

	@Autowired
	SupplirDetailsRepo supplirRepo;
	
	@Autowired
	TenderDetailsRepo tenderDetailsRepo;
	
	@Autowired
	MITDetailsRepo mitDetailsRepo;
	
	
	File imgFile;
	
	@GetMapping(value="Invoice/{invoiceid}/image", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity getinvoiceImage(HttpServletResponse response, @PathVariable String invoiceid)throws IOException {
//		File imgFile = new ClassPathResource("image/sid.jpg");
		ResponseEntity re=null;
		imgFile=new File("G:\\suplierportal\\Invoice\\"+invoiceid+"\\"+invoiceid+".jpeg");
		if(imgFile.exists()) {
			byte[] bytes =Files.readAllBytes(imgFile.toPath());
			re=ResponseEntity
	                .ok()
	                .contentType(MediaType.IMAGE_JPEG)
	                .body(bytes);
		}else {
			imgFile=new File("G:\\suplierportal\\Invoice\\"+invoiceid+"\\"+invoiceid+".jpg");
			if(imgFile.exists()) {
				byte[] bytes =Files.readAllBytes(imgFile.toPath());
				re=ResponseEntity
		                .ok()
		                .contentType(MediaType.IMAGE_JPEG)
		                .body(bytes);
			}else {
				
				imgFile=new File("G:\\suplierportal\\Invoice\\"+invoiceid+"\\"+invoiceid+".png");
				if(imgFile.exists()) {
					byte[] bytes =Files.readAllBytes(imgFile.toPath());
					re=ResponseEntity
			                .ok()
			                .contentType(MediaType.IMAGE_PNG)
			                .body(bytes);
			}
		}
	
		
		
		//		byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
		
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
//        return ResponseEntity
//                .ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(bytes);
//        

	}
		return re;
}
	
	
	
	@GetMapping(value="company/{id}/logo", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity companyLogo(@PathVariable long id)throws IOException {
//		File imgFile = new ClassPathResource("image/sid.jpg");
		ResponseEntity re=null;
		
		Optional<SubCompany> data = subCompanyRepo.findById(id);
		
		if (data.isPresent()) {
			imgFile=new File(data.get().getScomapanylogo());
			if(imgFile.exists()) {
				byte[] bytes =Files.readAllBytes(imgFile.toPath());
				re=ResponseEntity
		                .ok()
		                .contentType(MediaType.IMAGE_JPEG)
		                .body(bytes);
			}
		}
		
	
		return re;
}
	
	@GetMapping(value="contract/{id}/{type}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity contractImage(@PathVariable long id, @PathVariable String type)throws IOException {
//		File imgFile = new ClassPathResource("image/sid.jpg");
		ResponseEntity re=null;
		
		Optional<ContractDetails> data = contractRepo.findById(id);
		
		if (data.isPresent()) {
			if (type.equalsIgnoreCase("con")) {
				imgFile=new File(data.get().getUploadContract());
			}else if (type.equalsIgnoreCase("ba")) {
				imgFile=new File(data.get().getUploadBoardApproval());
			}else{
				imgFile=new File(data.get().getUploadPurchaseOrder());
			}
			//imgFile=new File(data.get().getScomapanylogo());
			if(imgFile.exists()) {
				byte[] bytes =Files.readAllBytes(imgFile.toPath());
				re=ResponseEntity
		                .ok()
		                .contentType(MediaType.IMAGE_JPEG)
		                .body(bytes);
			}
		}
		
	
		return re;
}
	
	@GetMapping(value="tender/{id}/{type}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity tenderImage(@PathVariable long id, @PathVariable String type)throws IOException {
		
		ResponseEntity re=null;
		
		
		Optional<TenderDetails> data = tenderDetailsRepo.findById(id);
		
		if (data.isPresent()) {
			if (type.equalsIgnoreCase("ur")) {
				imgFile=new File(data.get().getRfpfilename());
			}else if (type.equalsIgnoreCase("sd1")) {
				imgFile=new File(data.get().getSupportdoc1name());
			}else{
				imgFile=new File(data.get().getSupportdoc2name());
			}
			
			if(imgFile.exists()) {
				byte[] bytes =Files.readAllBytes(imgFile.toPath());
				re=ResponseEntity
		                .ok()
		                .contentType(MediaType.IMAGE_JPEG)
		                .body(bytes);
			}
		}
		
	
		return re;
}
	
	@GetMapping(value="supplier/{id}/{type}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity supplierImage(@PathVariable long id, @PathVariable String type)throws IOException {
//		File imgFile = new ClassPathResource("image/sid.jpg");
		ResponseEntity re=null;
		
		Optional<SupplierDetails> data = supplirRepo.findById(id);
		
		if (data.isPresent()) {
		/*	if (type.equalsIgnoreCase("gp")) {
				imgFile=new File(data.get().getGreenPolicy());
			}else if (type.equalsIgnoreCase("ictad")) {
				imgFile=new File(data.get().getIctadRegistration());
			}else if (type.equalsIgnoreCase("kycf")) {
				imgFile=new File(data.get().getKycForm());
			}else if (type.equalsIgnoreCase("los")) {
				imgFile=new File(data.get().getListOfServices());
			}else if (type.equalsIgnoreCase("cp")) {
				imgFile=new File(data.get().getCompanyProfile());
			}else if (type.equalsIgnoreCase("cals")) {
				imgFile=new File(data.get().getLastSixMonths());
			}else if (type.equalsIgnoreCase("lotc")) {
				imgFile=new File(data.get().getListofTopFifteenClients());
			}else if (type.equalsIgnoreCase("cot")) {
				imgFile=new File(data.get().getCertificationOfIncorp());
			}else if (type.equalsIgnoreCase("cld")) {
				imgFile=new File(data.get().getListofDirectors());
			}else if (type.equalsIgnoreCase("aoa")) {
				imgFile=new File(data.get().getArticalofAssociation());
			}else if (type.equalsIgnoreCase("lafa")) {
				imgFile=new File(data.get().getLastAuditedFinAcc());
			}else if (type.equalsIgnoreCase("cl")) {
				imgFile=new File(data.get().getComapanyLogo());
			}else if (type.equalsIgnoreCase("rf")) {
				imgFile=new File(data.get().getRegitrationUploaded());
			}else{
				imgFile=new File(data.get().getAddressProofUploaded());
			}*/
			
			if (type.equalsIgnoreCase("cl")) {
				imgFile=new File(data.get().getComapanyLogo());
			}
			//imgFile=new File(data.get().getScomapanylogo());
			if(imgFile.exists()) {
				byte[] bytes =Files.readAllBytes(imgFile.toPath());
				re=ResponseEntity
		                .ok()
		                .contentType(MediaType.IMAGE_JPEG)
		                .body(bytes);
			}
		}			
		return re;
}
	
	
	
	@GetMapping(value="viewsupplier/{id}/{type}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity viewSupplierImage(@PathVariable long id, @PathVariable String type) throws IOException {
//		File imgFile = new ClassPathResource("image/sid.jpg");
		ResponseEntity re=null;
		
		Optional<SupplierDetails> data = supplirRepo.findById(id);
		
		if (data.isPresent()) {
			if (type.equalsIgnoreCase("cl")) {
				imgFile=new File(data.get().getComapanyLogo());
			}
			//imgFile=new File(data.get().getScomapanylogo());
			if(imgFile.exists()) {
				byte[] bytes =Files.readAllBytes(imgFile.toPath());
				re=ResponseEntity
		                .ok()
		                .contentType(MediaType.IMAGE_JPEG)
		                .body(bytes);
			}
		}			
		return re;
}
	
	
	@GetMapping(value="companysupplier/{id}/{type}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity companySupplierImage(@PathVariable long id, @PathVariable String type) throws IOException {
//		File imgFile = new ClassPathResource("image/sid.jpg");
		ResponseEntity re=null;
		
		Optional<SubCompany> data = subCompanyRepo.findById(id);
		
		if (data.isPresent()) {
			if (type.equalsIgnoreCase("cl")) {
				imgFile=new File(data.get().getScomapanylogo());
			}
			//imgFile=new File(data.get().getScomapanylogo());
			if(imgFile.exists()) {
				byte[] bytes =Files.readAllBytes(imgFile.toPath());
				re=ResponseEntity
		                .ok()
		                .contentType(MediaType.IMAGE_JPEG)
		                .body(bytes);
			}
		}			
		return re;
}
	
	
	@GetMapping(value="blocksupplier/{id}/{type}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity blockSupplierImage(@PathVariable long id, @PathVariable String type) throws IOException {
//		File imgFile = new ClassPathResource("image/sid.jpg");
		ResponseEntity re=null;
		
		Optional<SupplierDetails> data = supplirRepo.findById(id);
		
		if (data.isPresent()) {
			if (type.equalsIgnoreCase("cl")) {
				imgFile=new File(data.get().getComapanyLogo());
			}
			//imgFile=new File(data.get().getScomapanylogo());
			if(imgFile.exists()) {
				byte[] bytes =Files.readAllBytes(imgFile.toPath());
				re=ResponseEntity
		                .ok()
		                .contentType(MediaType.IMAGE_JPEG)
		                .body(bytes);
			}
		}			
		return re;
}
	
	
	@GetMapping(value="viewblocksupplier/{id}/{type}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity viewBlockSupplierImage(@PathVariable long id, @PathVariable String type) throws IOException {
//		File imgFile = new ClassPathResource("image/sid.jpg");
		ResponseEntity re=null;
		
		Optional<SupplierDetails> data = supplirRepo.findById(id);
		
		if (data.isPresent()) {
			if (type.equalsIgnoreCase("cl")) {
				imgFile=new File(data.get().getComapanyLogo());
			}
			//imgFile=new File(data.get().getScomapanylogo());
			if(imgFile.exists()) {
				byte[] bytes =Files.readAllBytes(imgFile.toPath());
				re=ResponseEntity
		                .ok()
		                .contentType(MediaType.IMAGE_JPEG)
		                .body(bytes);
			}
		}			
		return re;
}
	
	
	@GetMapping(value="viewmitpicture/{id}/{type}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity viewMITImage(@PathVariable long id, @PathVariable String type)throws IOException {
//		File imgFile = new ClassPathResource("image/sid.jpg");
		ResponseEntity re=null;
		
		Optional<MITDetails> data = mitDetailsRepo.findById(id);
		
		if (data.isPresent()) {
			System.out.println("INN ");
			if (type.equals("mit")) {
				imgFile=new File(data.get().getPicuturePath());
				System.out.println("IN "+data.get().getPicuturePath());
			}
			//imgFile=new File(data.get().getScomapanylogo());
			if(imgFile.exists()) {
				System.out.println("NEXT "+data.get().getPicuturePath());
				byte[] bytes =Files.readAllBytes(imgFile.toPath());
				re=ResponseEntity
		                .ok()
		                .contentType(MediaType.IMAGE_JPEG)
		                .body(bytes);
			}
		}			
		return re;
}
	
	
	
}