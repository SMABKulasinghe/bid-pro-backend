package lk.supplierUMS.SupplierUMS_REST.comman;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DbForReports {

	@Value("${spring.datasource.url}")
	String url;
	
	@Value("${spring.datasource.username}")
	String user;
	
	@Value("${spring.datasource.password}")
	String pass;
	
	 Connection c;
	
	  public Connection getCon() throws Exception {

	        if (c == null) {

	            Class.forName("com.mysql.jdbc.Driver");
	         // c = DriverManager.getConnection("jdbc:mysql://localhost:3306/supplier_portal_v1", "root", "Askpi@123");
//	            c = DriverManager.getConnection("jdbc:mysql://192.168.33.40:3306/supplier_portal_v1", "suppler_user", "suppler@123");
	            c = DriverManager.getConnection(url, user, pass);

	        }
	        return c;

	    }
}
