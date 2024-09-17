package lk.supplierUMS.SupplierUMS_REST.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsUtils;

import lk.supplierUMS.SupplierUMS_REST.entity.User;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(basePackageClasses=User.class)
@EnableWebSecurity
@Configuration
public class securityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	customUserDetailService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(auth);
	
		auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				// TODO Auto-generated method stub
				String pass = encode(rawPassword);
				boolean st = false;
				System.out.println(encodedPassword);
				if (pass.equals(encodedPassword)) {
				st=true;
				}
				return st;
				//return true; // in a case of login issue with password uncomment this return and empty both matches() and encode() body
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				// TODO Auto-generated method stub
				String pass="";

				try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(rawPassword.toString().getBytes());
				 pass = new sun.misc.BASE64Encoder().encode(md.digest());
				} catch (NoSuchAlgorithmException e) {
				// _log.error("Failed to encrypt password.", e);
				}
				return pass;
				//return null; // in a case of login issue with password uncomment this return and empty both matches() and encode() body
			}
		});
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		
		  
//		http.authorizeRequests()
////		.antMatchers(HttpMethod.OPTIONS).permitAll()//allow CORS option calls
//		.anyRequest().fullyAuthenticated()
//		.and().csrf().disable()
//		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
/*  ****************** Noted Don't touch this security config **********************     
 *  
 *  This config is permit all OPTIONS request and after that fullyAuthenticate other request (GET,POST,PUT,DELETE) 
 *    
 *  ******************************************************************************** */	
		
		http.authorizeRequests()
		.antMatchers("/poheaderdetails/downloadFile/**").permitAll()
		.antMatchers("/invoiceheaderdetails/downloadInvoiceFile/**").permitAll()
		.antMatchers("/invoice/downloadinvfileformats/**").permitAll()
		.antMatchers("/users/forgotpassword/user/{*}/email/{*}").permitAll()
		.antMatchers("/proceedpayment/paymentadvice/cheque/{*}").permitAll()
		.antMatchers("/pobodydetails/podownload/pono/{*}").permitAll()
		.antMatchers("/grbodydetails/grdownload/grno/{*}").permitAll()
		.antMatchers("/returnbodydetails/rtndownload/rtnno/{*}").permitAll()
		.antMatchers("/proceedpayment/debitnumber/{*}").permitAll()
		.antMatchers("/locationdetails/**").permitAll()
		.antMatchers("/supplier/addsupplier/{*}").permitAll()
		.antMatchers("/supplier/supplierselfreg/{*}").permitAll()
		.antMatchers("/users/forgotpassword/user/**").permitAll()
		.antMatchers("/image/**").permitAll()
		.antMatchers("/registration/**").permitAll()
		.antMatchers("/tender/downloadtenfileformats/**").permitAll()
		.antMatchers("/rfp/download-rfp-csv/**").permitAll()
		.antMatchers("/rfp/delete-rfp-csv/**").permitAll()
		.antMatchers("/tender/download-rfp-tender-files/**").permitAll()
		.antMatchers("/supplier/download-additional-supplier-files/**").permitAll()
		
		.antMatchers("/tender/download-bidded-tender-files/**").permitAll()
		.antMatchers("/tender/tender-auth-file-upload/**").permitAll()
		.antMatchers("/po/download-email-attachment/**").permitAll()
		.antMatchers("/procurement/board-paper-download/**").permitAll()
		
		.antMatchers("/supplier/download-companydetails-view-files/**").permitAll()
		.antMatchers("/supplier/download-additional-view-supplier-files/**").permitAll()
		.antMatchers("/rfp/rfp-file-view/**").permitAll()
		.antMatchers("/supplier/downloadallfiles/**").permitAll()
		
		.antMatchers("/evaluation/download-MITdetails-view-files/**").permitAll()
		.antMatchers("/tender/aditional-files-view/**").permitAll()
		.antMatchers("/tender/download-financial-response-file/**").permitAll()
		.antMatchers("/tender/supplier-additinal-file-download-to-pc/**").permitAll()
		
		.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
		.anyRequest().fullyAuthenticated()
		.and().httpBasic()
		.and().csrf().disable()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
		
		
		
		
		
		
		
//
//		http.authorizeRequests().anyRequest().fullyAuthenticated()
//		.and().httpBasic()
//		.and().csrf().disable()
//		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}

	
	
}
