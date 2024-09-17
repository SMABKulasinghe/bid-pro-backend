package lk.supplierUMS.SupplierUMS_REST.comman;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.Base64;
import java.util.Base64;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;

public class DeEnCode {
	
	public File decodeMethod(String filePath,String fileName,String FileType,String encodeFile) {
//		byte[] decodedBytes = Base64.getDecoder().decode(encodeFile);
	
//		FileUtils.writeByteArrayToFile(new File(fileName), decodedBytes);
		
		Path path=Paths.get(""+filePath);
		if(Files.exists(path)) {
			System.out.println("Already Exists Dir");
			
			File outputFIle=new File("/"+filePath+"/"+fileName+"."+FileType);
			byte[] decodedBytes = Base64.getDecoder().decode(encodeFile);
			try {
			org.apache.commons.io.FileUtils.writeByteArrayToFile(outputFIle, decodedBytes);
//			        FileUtils.writeByteArrayToFile(outputFIle, decodedBytes);
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}else {
			System.out.println("Make New Dir");
			
			
		new File("/"+filePath).mkdir();	
		File outputFIle=new File(filePath+"/"+fileName+"."+FileType);
		byte[] decodedBytes = Base64.getDecoder().decode(encodeFile);
		try {
		org.apache.commons.io.FileUtils.writeByteArrayToFile(outputFIle, decodedBytes);
//		        FileUtils.writeByteArrayToFile(outputFIle, decodedBytes);
		}catch (Exception e) {
			e.printStackTrace();
		}	
		}
		
		
//		File outputFIle=new File("G:/"+fileName+"."+FileType);
//		byte[] decodedBytes = Base64.getDecoder().decode(encodeFile);
//		try {
//		org.apache.commons.io.FileUtils.writeByteArrayToFile(outputFIle, decodedBytes);
////		        FileUtils.writeByteArrayToFile(outputFIle, decodedBytes);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
		return null;
	}
	
	public File decodeMethodForTemp(String filePath, String fileName, String FileType, String encodeFile) {

		Path path = Paths.get("src//Upload/" + filePath);
		if (Files.exists(path)) {
			System.out.println("Already Exists Dir");

			File outputFIle = new File("src//Upload/" + filePath + "/" + fileName + "." + FileType);
			byte[] decodedBytes = Base64.getDecoder().decode(encodeFile);
			try {
				org.apache.commons.io.FileUtils.writeByteArrayToFile(outputFIle, decodedBytes);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Make New Dir");

			new File("src//Upload/" + filePath).mkdir();
			File outputFIle = new File("src//Upload/" + filePath + "/" + fileName + "." + FileType);
			byte[] decodedBytes = Base64.getDecoder().decode(encodeFile);
			try {
				org.apache.commons.io.FileUtils.writeByteArrayToFile(outputFIle, decodedBytes);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
