package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.exolab.castor.types.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.DepartmentDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.GlobalStatusRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.LocationDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserEditLogRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRoleOptionsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRoleRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserTemporaryRepo;
import lk.supplierUMS.SupplierUMS_REST.comman.Common;
import lk.supplierUMS.SupplierUMS_REST.entity.Asset;
import lk.supplierUMS.SupplierUMS_REST.entity.GlobalStatus;
import lk.supplierUMS.SupplierUMS_REST.entity.LocationDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.PoDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.RFP;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.entity.UserEditLog;
import lk.supplierUMS.SupplierUMS_REST.entity.Department;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRoleOptions;
import lk.supplierUMS.SupplierUMS_REST.entity.UserTemporary;
import lk.supplierUMS.SupplierUMS_REST.entity.CompanyDetails;
import lk.supplierUMS.SupplierUMS_REST.model.UserDao;
import lk.supplierUMS.SupplierUMS_REST.model.UserRoleDao;
import lk.supplierUMS.SupplierUMS_REST.service.UserService;

@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	UserRepo userRepo;

	@Autowired
	UserRoleDao userRollDao;

	@Autowired
	UserRoleOptionsRepo uro;
	@Autowired
	GlobalStatusRepo globalStatusRepo;

	@Autowired
	Common common;
	@Autowired
	SubCompanyRepo subCompanyRepo;
	
	@Autowired
	UserRoleRepo userRollRepo;
	
	@Autowired
	LocationDetailsRepo locationDetailsRepo;
	
	@Autowired
	DepartmentDetailsRepo departmentDetailsRepo;
	
	@Autowired
	UserTemporaryRepo userTemporaryRepo;
	
	@Autowired
	UserEditLogRepo userEditLogRepo;
	
	@Override
	public String addUser(User user) {
		// TODO Auto-generated method stub
		System.out.println("User Created");
		user.setStatus(1);
		user.setStatusFlag("N");
		user.setPassowrd("fakepassword");
		//globle status table
		Long tempStatus = (long) 3;
		user.setTempUserStatus(tempStatus);
		userDao.addUser(user);
		return user.getUserid();
	}

	@Override
	public User getUserFromID(String id) {
		System.out.println(id);
		// TODO Auto-generated method stub
		User u = userRepo.findByUserid(id).get();
		return u;

	}

	@Override
	public List<UserRoleOptions> getUserRollOptions(String id) {
		// TODO Auto-generated method stub
		User u = userRepo.findByUserid(id).get();
		UserRole ur = u.getUserRoll();
		List<UserRoleOptions> l = ur.getOptions();
		return l;
	}

	@Override
	public User updateUserStatus(String id, User u) {
		// TODO Auto-generated method stub
		User user = userRepo.findByUserid(id).get();
		try {
			System.out.println("Status>> " + u.getStatus() == u.getStatusFlag());
			System.out.println("Get Status>> " + u.getStatus());
			System.out.println("Get Status Flag>> " + u.getStatusFlag());

			// new User - approval, send password
			if (user.getStatus().equals("1") && user.getStatusFlag().equals("N")) {
				System.out.println("New User getting approval send mail");

				user.setStatus(u.getStatus() == null ? user.getStatus() : u.getStatus());
				user.setStatusFlag(u.getStatusFlag() == null ? user.getStatusFlag() : u.getStatusFlag());
				String randomPassword = generateRandomPassword();
				String encryptedpassword = getEncryptedPassword(randomPassword);
				user.setPassowrd(encryptedpassword);

				// send mail
				String from = "Piumal";
				String to = user.getEmail();
				// String subject = "Your New Password";
				// String body = "Password "+randomPassword;

				String subject = "Cargills Supplier Portal - Login Credentials";
				String body1 = "User Login ID  -: " + user.getUserid() + "  " + "\n" + "Password -: " + randomPassword
						+ "\n\n <br>" + "Please change the password in first login."
						+ "\n\n <br> Thank You, \nCargills User Administration Dept."
						+ "\n <br> email : portal.admin@cargillsceylon.com" + "\n <br> Phone : 0112785452"
						+ "\n\n\n <br> Please do not reply to this email";

				String body = "<h4>User Login ID  -: " + user.getUserid() + "</h4>";
				body += "<h4>Password -: " + randomPassword + "</h4>";
				body += "<p>Thank You</p>";
				body += "<p>Cargills User Administration Dept.</p>";
				body += "<p>email : portal.admin@cargillsceylon.com</p>";
				body += "<p>Phone : 0112785452</p>";
				body += "<p>Please do not reply to this email</p>";

				// Test mail
				// sendMail(from, to, subject, body);

				// Cargills Mail Server
				sendMailCargills(from, to, subject, body);

			} else {
				System.out.println("Existing user Update No Email No New Password");
				user.setStatus(u.getStatus() == null ? user.getStatus() : u.getStatus());
				user.setStatusFlag(u.getStatusFlag() == null ? user.getStatusFlag() : u.getStatusFlag());

			}

			userRepo.save(user);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return user;
	}

	@Override
	public String generateRandomPassword() {

		final Random RANDOM = new SecureRandom();
		/** Length of password. @see #generateRandomPassword() */
		final int PASSWORD_LENGTH = 8;
		// Pick from some letters that won't be easily mistaken for each
		// other. So, for example, omit o O and 0, 1 l and L.
		String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

		String randompw = "";
		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int index = (int) (RANDOM.nextDouble() * letters.length());
			randompw += letters.substring(index, index + 1);
		}
		System.out.println("Gen Ran Pass " + randompw);
		System.out.println("Gen Ran Pass and send mail here" + randompw);

		String encryptedpassword = getEncryptedPassword(randompw);
		System.out.println("encrpted " + encryptedpassword);
		return randompw;
	}

	@SuppressWarnings("restriction")
	public static String getEncryptedPassword(String clearTextPassword) {

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(clearTextPassword.getBytes());
			return new sun.misc.BASE64Encoder().encode(md.digest());
		} catch (NoSuchAlgorithmException e) {
			// _log.error("Failed to encrypt password.", e);
		}
		return "";
	}

	@Override
	public int sendMail(String from, String to, String subject, String body) {
		System.out.println("inside mail send");

		System.out.println("from----------" + from);
		System.out.println("to----------" + to);
		System.out.println("subject----------" + subject);
		System.out.println("body----------" + body);

		int stat;
		try {
			Properties properties = System.getProperties();

			// gmail settings #start#-------------------------

			String email = "anonymoususercfc@gmail.com";
			String Password = "vmJrZcuA89";
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

			Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email, Password);
				}
			});
			// gmail settings #end#-------------------------
			Message message = new MimeMessage(session);

			// gmail settings #start#-------------------------
			message.addFrom(new InternetAddress[] { new InternetAddress("anonymoususercfc@gmail.com") });
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			// gmail settings #end#---------------------------

			message.setSubject(subject);
			message.setContent(body, "text/html; charset=utf-8");

			Transport.send(message);

			System.out.println("Done");

			stat = 1;

		} catch (Exception e) {
			System.out.println("message Sending fails----" + e.getMessage());
			stat = 0;
		}

		return stat;
	}

	@Override
	public String changePassword(String loggedUser, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		String msg = "";

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(oldPassword.toString().getBytes());
			String encodedPassword = new sun.misc.BASE64Encoder().encode(md.digest());
			System.out.println("encodedPassword " + encodedPassword);
			Optional<User> userN = userRepo.findByUserid(loggedUser);

			if (userN.isPresent()) {
				User u = userN.get();
				if (u.getPassowrd().equals(encodedPassword)) {
					System.out.println("Inside Confirmed " + newPassword);
					u.setPassowrd(getEncryptedPassword(newPassword));
					userRepo.save(u);
					msg = "Password Changed Successfully";
				} else {
					msg = "Current password is incorrect";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			msg = "Something went wrong, Please try again";
			// _log.error("Failed to encrypt password.", e);
		}
		return msg;
	}

	@Override
	public String forgotPassword(String userid, String useremail) {
		// TODO Auto-generated method stub
		Optional<User> userN = userRepo.findByUseridAndEmail(userid, useremail);
		String msg = "";

		if (userN.isPresent()) {
			User u = userN.get();
			updateUserStatus(userid, u);
			msg = "Password Changed";
		} else {
			msg = "Error in change";
		}

		return msg;
	}

	@Override
	public Boolean getWetherUserExists(String enteredValue) {
		// TODO Auto-generated method stub

		return userRepo.existsByUserid(enteredValue);
	}

	@Override
	public Boolean getWetherUserExistsByNIC(String enteredValue) {
		// TODO Auto-generated method stub
		return userRepo.existsByNic(enteredValue);
	}

	@Override
	public Boolean getWetherUserExistsByMobile(String enteredValue) {
		// TODO Auto-generated method stub
		return userRepo.existsByMobileNo(enteredValue);
	}

	@Override
	public Boolean getWetherUserExistsByEmail(String enteredValue) {
		// TODO Auto-generated method stub
		return userRepo.existsByEmail(enteredValue);
	}
	
	@Override
	public Boolean getWetherUserExistsByCompanyName(String enteredValue) {
		// TODO Auto-generated method stub
		return userRepo.existsByuserid(enteredValue);
	}

	@Override
	public int sendMailCargills(String from, String to, String subject, String body) {
		// TODO Auto-generated method stub

		System.out.println("inside Cargills mail send");

		System.out.println("from----------" + from);
		System.out.println("to----------" + to);
		System.out.println("subject----------" + subject);
		System.out.println("body----------" + body);

		int stat;
		try {
			Properties properties = System.getProperties();

			// gmail settings #start#-------------------------

			String host = "10.1.0.20";
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.starttls.enable", "false");
			properties.put("mail.smtp.port", 25);
			properties.put("mail.smtp.socketFactory.port", 25);

			Session session = Session.getDefaultInstance(properties);

			// gmail settings #end#-------------------------
			Message message = new MimeMessage(session);

			// gmail settings #start#-------------------------
			message.addFrom(new InternetAddress[] { new InternetAddress("portal.admin@cargillsceylon.com") });
			message.addRecipient(RecipientType.TO, new InternetAddress(to));
			// gmail settings #end#---------------------------

			message.setSubject(subject);
			message.setContent(body, "text/html; charset=utf-8");

			Transport.send(message);

			System.out.println("Done");

			stat = 1;

		} catch (Exception e) {
			System.out.println("message Sending fails----" + e.getMessage());
			stat = 0;
		}

		return stat;

	}

	@Override
	public String updateUserProfile(String userid, User user) {
		// TODO Auto-generated method stub
		// String id = "";
		try {
			User us = userRepo.findByUserid(userid).get();
			us.setUsername(user.getUsername());
			us.setCompanyCode(user.getCompanyCode());
			us.setNic(user.getNic());
			us.setEmail(user.getEmail());
			us.setMobileNo(user.getMobileNo());
			us.setCreatedDateTime(user.getCreatedDateTime());
			us.setExpireDate(user.getExpireDate());
			us.setUserCompanyCode(user.getUserCompanyCode());
			us.setStatus(user.getStatus());
			us.setStatusFlag("N");
			us.setUserRoll(user.getUserRoll());
			us.setUserRoleInBussiness(user.getUserRoleInBussiness());
			us.setMmiddlenname(user.getMmiddlenname());

			us = userRepo.save(us);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userid;

	}

	@Override
	public List<User> updateUserByCompanyCode(String companyID) {
		// TODO Auto-generated method stub
		List<User> userList = userRepo.findByCompanyCode(companyID);
		// List<String> returnuserList = new ArrayList();
		/*
		 * for(User user : userList){ returnuserList.add(user.getUserid()+" - "+
		 * user.getUsername()); System.out.println("Users "+(user.getUserid()+" - "+
		 * user.getUsername())); }
		 */

		return userList;
	}

	@Override
	public void addFcmToken(String id, JSONObject data) {
		try {
			Optional<User> u = userRepo.findByUserid(id);
			if (u.isPresent()) {
				u.get().setFCMToken(data.get("token").toString());
				userRepo.save(u.get());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public JSONObject getAlluserListtoAuthorize(HttpServletRequest request,String para) {
		System.out.println("Inside ServiceImpl  getAlluserListtoAuthorize ------"+para);

		JSONObject data = new JSONObject();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String userCompanyID = user.getCompanyCode();
		System.out.println("Long----- " + userCompanyID);
		try {
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "";
				break;
			case "1":
				sorting = "userid";
				break;
			case "2":
				sorting = "username";
				break;
			case "3":

				sorting = "createdDateTime";
				break;

			}
			Page<User> dataList= null;
			
			if(para.equals("all")) {
			if (request.getParameter("order[0][dir]").equals("asc")) {
				dataList = userRepo.getuserListtoAuthorize(request.getParameter("search[value]"), userCompanyID,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				dataList = userRepo.getuserListtoAuthorize(request.getParameter("search[value]"), userCompanyID,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
			}
			}else {
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList = userRepo.getuserWisetoAuthorize(request.getParameter("search[value]"), userCompanyID,para,
							PageRequest.of(
									Integer.parseInt(request.getParameter("start"))
											/ Integer.parseInt(request.getParameter("length")),
									Integer.parseInt(request.getParameter("length")),
									Sort.by(Sort.Direction.ASC, sorting)));
				} else {
					dataList = userRepo.getuserWisetoAuthorize(request.getParameter("search[value]"), userCompanyID,para,
							PageRequest.of(
									Integer.parseInt(request.getParameter("start"))
											/ Integer.parseInt(request.getParameter("length")),
									Integer.parseInt(request.getParameter("length")),
									Sort.by(Sort.Direction.DESC, sorting)));
				}
			}
			JSONArray ar = new JSONArray();

			for (User userDetails : dataList.getContent()) {

				JSONObject header = new JSONObject();
				System.out.println("userDetails.getStatus().toString()----- " + userDetails.getStatus().toString());

				GlobalStatus stsdec = globalStatusRepo.findById(new Long(userDetails.getStatus().toString())).get();

				header.put("userid", userDetails.getUserid());
				header.put("username", userDetails.getUsername());
				header.put("companyCode", userDetails.getCompanyCode());
				header.put("userRoleInBussiness", userDetails.getUserRoleInBussiness());
				header.put("status", stsdec.getGlobalStatusDescription());
				header.put("createdDateTime", userDetails.getCreatedDateTime());
				header.put("statusForButtonDisable", userDetails.getStatus());
				ar.add(header);

			}

			data.put("recordsTotal", dataList.getTotalElements());
			data.put("recordsFiltered", dataList.getTotalElements());
			data.put("data", ar);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public String approveUserIDList(JSONObject data) {
		System.out.println("UserServiceImpl.approveUserIDList------------- " + data);
		String returnMsg = "Nothing";
		try {

			Set<String> keys = data.keySet();

			for (String key : keys) {
				System.out.println(key);
				System.out.println(data.get(key));

				Optional<User> us = userRepo.findById(key);

//				User user = userRepo.getOne(key.toString());
				Map address = ((Map) data.get(key));
				Iterator<Map.Entry> itr1 = address.entrySet().iterator();
				while (itr1.hasNext()) {
					Map.Entry pair = itr1.next();
					System.out.println("test-----------" + pair.getKey() + " : " + pair.getValue());

					System.out.println("11--userOfuserid------------- " + pair.getKey().equals("userOfuserid"));
					if (pair.getKey().equals("userOfuserid")) {
						System.out.println("Action-- pair.getValue()------------- " + pair.getValue());
						if (pair.getValue().equals("Accept")) {

							String generatedString = new Random().ints(97, 122 + 1).limit(10)
									.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
									.toString();

							MessageDigest md = MessageDigest.getInstance("SHA-256");
							md.update(generatedString.getBytes());

							String subject = "Welcome To Vendor Management System - Login Credentials";
							String body = "<h1>Welcome To Vendor Management System</h1>"

									+ "\nUser Login ID  -: " + us.get().getUserid() + "\nPassword -: " + generatedString
									+ "\nURL -: http://localhost:9098/supplierums/login"
									+ "\n\nEnjoy your new experience with Vendor Management System." + "\nThank You!,"
									+ "\nTeam VMS" + "\n\nContact Us." + "\nemail : portal.admin@VMS.com"
									+ "\nPhone : 0112785452" + "\nPlease do not reply to this email";

							new Thread(new Runnable() {

								@Override
								public void run() {
									System.out.println("email -------------------- " + us.get().getEmail());
									List<String> to = new ArrayList<String>();
									to.add(us.get().getEmail());

									common.sendEMail(to, subject, body);
								}
							}).start();

							us.get().setPassowrd(new sun.misc.BASE64Encoder().encode(md.digest()));
							us.get().setStatus(3);
							us.get().setStatusFlag("Y");

						} else {
							us.get().setStatus(8);
							us.get().setStatusFlag("Y");
						}

					}

					if (pair.getKey().equals("statusReason")) {
						System.out.println("22------------- ");
						us.get().setApprvreason(pair.getValue().toString());
					}

				}
				userRepo.save(us.get());
				returnMsg = "Success";
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			returnMsg = "Error";
		}
		return returnMsg;
	}

	@Override
	public JSONObject getUserDetailstoResetPassword(String id) {

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User us = (User) authentication.getPrincipal();

			Optional<User> userlist = userRepo.findById(id);

			if (userlist.isPresent()) {
				JSONObject data = new JSONObject();

				data.put("userid", userlist.get().getUserid());
				data.put("role", userlist.get().getUserRoleInBussiness());
				data.put("nic", userlist.get().getNic());
				data.put("name", userlist.get().getUsername());
				data.put("phone", userlist.get().getMobileNo());
				data.put("email", userlist.get().getEmail());
				return data;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String resetUserPassword(String id) {
		System.out.println("11--resetUserPassword------------- "+id);
		String returnMsg = "Nothing";
		try {
			if(id.length()!=0) {
				Optional<User> us = userRepo.findById(id);
				System.out.println("13------------- "+id);
				us.get().setStatus(7);
				us.get().setStatusFlag("N");
				userRepo.save(us.get());
				returnMsg = "Success";
			}else {
				returnMsg = "Error";
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			returnMsg = "Error";
		}
		return returnMsg;
	}

	@Override
	public JSONObject forgotPassword(String userid, JSONObject data) {
		try {
			JSONObject json = new JSONObject();
			Optional<User> ob = userRepo.findByUseridAndEmail(userid, data.get("email").toString());
			if (ob.isPresent()) {
				User user = ob.get();
				
				 String generatedString = new Random().ints(97, 122 + 1)
					      .limit(10)
					      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
					      .toString();

				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(generatedString.getBytes());
				user.setPassowrd(new sun.misc.BASE64Encoder().encode(md.digest()));
				user = userRepo.saveAndFlush(user);
				
				String subject = "BidPro Portal - Account Recovery";
				String body = "\n"
						+ "\nWe received an password recovery request on appaiaz for "+user.getEmail()
						+ "\n"
						+ "\nNew Password -: "    + generatedString
						+ "\nURL -: https://BidPro.com:8443/supplierums/login"
						
						+ "\nEnjoy your new experience with BidPro Portal System."
						+ "\nThank You!,"
						+ "\nTeam BidPro"
						+ "\n\nContact Us."
						+ "\nemail : bidpro.info@gmail.com"
						+ "\nPhone : 0711668739"
						+ "\nPlease do not reply to this email";
				
				List<String> to = new ArrayList<String>();
				to.add(user.getEmail());
				System.out.println(user.getEmail());
				 new Thread(new Runnable() {
					
					@Override
					public void run() {
						common.sendEMail(to, subject, body);
					}
				}).start();
				
			}else {
				json.put("msg", "User not found");
			}
			
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject getUserforautho(String search, Long page) {
		System.out.println("getUserforautho------------  "+search);
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
		//	Long userCompanyID = new Long(user.getCompanyCode());
			String userCompanyID = user.getCompanyCode();
			JSONObject ob = new JSONObject();
			
			System.out.println("userCompanyID--------------------------------  "+userCompanyID);
			
			int pagevalue;
			if (page == null) {
				pagevalue = 0;
			}else {
				pagevalue = page.intValue();
			}
			Integer stst=1;
			String flag ="N";
			List<User> userlist = userRepo.findByUsernameLikeAndStatusAndStatusFlagAndCompanyCode(search+"%",stst,flag,userCompanyID,PageRequest.of(pagevalue, 30));
			long count = subCompanyRepo.countByScnameLikeAndIsPrivateFalse(search+"%");
			ob.put("total_count", count);
			if ((pagevalue*30)<count) {
				ob.put("incomplete_results", true);
			}else {
				ob.put("incomplete_results", false);
			}
			ArrayList data = new ArrayList();
			for (User us : userlist) {
				
					JSONObject sup = new JSONObject();
					sup.put("name", us.getUsername());
					sup.put("id", us.getUserid());
					data.add(sup);
				
				
			}
			ob.put("items", data);
			
			
			return ob;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject getAllUsersForEditView(HttpServletRequest request, String parameter) {
		// TODO Auto-generated method stub

		
		System.out.println("Inside ServiceImpl "+parameter);
		// TODO Auto-generated method stub
		JSONObject data = new JSONObject();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		//Long userCompanyID = new Long(user.getCompanyCode());
		String userCompanyID = user.getCompanyCode();
		
		System.out.println("Long----- "+userCompanyID);
		try {
			data.put("draw", request.getParameter("draw"));
			String sorting="";
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "userid";
			    break;
			  case "1":
				sorting = "userid";
			    break;
			  case "2":
				sorting = "username";
			    break;
			  case "3":
				sorting = "nic";
			    break;
			  case "4":
				sorting = "mmiddlenname";
			    break;
			  case "5":
				  sorting = "mobileNo";
				  break;
			  case "6":
				  sorting = "createdDateTime";
				  break;
			  case "7":
				  sorting = "expireDate";
				  break;
			}
			Page<Object[]> dataList = null;
			
			if(parameter.equals("viewall")){
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =userRepo.getAllUsersForEditView(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =userRepo.getAllUsersForEditView(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			}
			
			JSONArray ar = new JSONArray();
//			System.out.println("Size -------------"+dataList.size());
			 for (Object[] UserDetails : dataList.getContent()) {
				 if(UserDetails[0] instanceof User && UserDetails[1] instanceof Department && UserDetails[2] instanceof UserRole){
					 User ph = (User) UserDetails[0];
					 Department dep = (Department) UserDetails[1];
					 UserRole ur = (UserRole) UserDetails[2];
					 //SubCompany sc = (SubCompany) UserDetails[3];
				
					 JSONObject header = new JSONObject();
					 
					
					 	header.put("userID", ph.getUserid());
					 	header.put("username", ph.getUsername());
					 	header.put("NIC", ph.getNic());
					 	header.put("subCompanyCode", ph.getCompanyCode());
					 	header.put("companyName", ph.getMmiddlenname());
						header.put("email", ph.getEmail());
						header.put("userRole", ph.getUserRoll().getUserRoleName());
						header.put("mobileNumber", ph.getMobileNo());
						Date createDate = ph.getCreatedDateTime();
						SimpleDateFormat sm1 = new SimpleDateFormat("yyyy-MM-dd");
						String createDate2 = sm1.format(createDate);
						header.put("createdDate", createDate2);
						Date datee = ph.getExpireDate();
						SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
						String strDate = sm.format(datee);
						header.put("expireDate", strDate);
						header.put("roleName", ur.getUserRoleName());
						header.put("department", dep.getDepartmentName());
						header.put("approveReason", user.getApprvreason());
						header.put("status", user.getStatus());
						header.put("userRoleId", ph.getUserRoll().getUserRoleID());
						header.put("locationCode", ph.getLocationID());
						header.put("depId", ph.getDeptID());
						header.put("companyCode", ph.getCompanyCode());
						System.out.println("userRoleId"+ph.getUserRoll().getUserRoleID());
						
						JSONArray view = new JSONArray();
						view.add(ph.getEmail());
						view.add(ph.getEmail());
						view.add(ph.getEmail());
						view.add(ph.getEmail());
						view.add(ph.getEmail());
						header.put("details", view);
						ar.add(header);
				 }
				
				
				
			}
			 data.put("recordsTotal",dataList.getTotalElements());
			 data.put("recordsFiltered",dataList.getTotalElements());
			 data.put("data", ar);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	
		
		
	}

	@Override
	public JSONObject updateFirstLogin(JSONObject data, HttpServletRequest request) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			if (user != null && user.getUsername().equals(data.get("loggeduserID"))) {
				Optional<User> usreoptional = userRepo.findByUserid(user.getUsername());
				if (usreoptional.isPresent()) {
					User loggedUser = usreoptional.get();
					loggedUser.setIsFirstLogin(true);
					userRepo.save(loggedUser);
					json.put("msg", "Success");
					json.put("code", "00");
					return json;
				}
				//userRepo.save(null);
				json.put("msg", "Falied");
				json.put("code", "01");
				return json;
			}else {
				json.put("msg", "Falied");
				json.put("code", "03");
				return json;
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("msg", "Error");
			json.put("code", "02");
			return json;
		}
	}

	@Override
	public JSONObject changePasswordAtFirstLogin(JSONObject data, HttpServletRequest request) {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		 System.out.println("User--> "+user.getUsername()+" CP--> "+ data.get("currentPassword").toString());
		 

		 JSONObject json = new JSONObject();

			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(data.get("currentPassword").toString().getBytes());
				String encodedPassword = new sun.misc.BASE64Encoder().encode(md.digest());
				System.out.println("encodedPassword " + encodedPassword);
				Optional<User> userN = userRepo.findByUserid(user.getUsername());

				if (userN.isPresent()) {
					User u = userN.get();
					if (u.getPassowrd().equals(encodedPassword)) {
						System.out.println("Inside Confirmed " + data.get("newPassword").toString());
						u.setPassowrd(getEncryptedPassword(data.get("newPassword").toString()));
						userRepo.save(u);
						json.put("msg", "Success");
						json.put("code", "00");
						json.put("userID", u.getUsername());
						return json;
					} else {
						json.put("msg", "Invalid current password");
						json.put("code", "01");
						return json;
					}
				}else {
					json.put("msg", "no such user");
					json.put("code", "03");
					return json;
				}

			} catch (Exception e) {
				e.printStackTrace();
				json.put("msg", "Error");
				json.put("code", "02");
				return json;
			}
		
		 
	}

	@Override
	public String authUser(JSONObject data) {
		try {
			
			Optional<User> us = userRepo.findById(data.get("userid").toString());
			String auth = data.get("auth").toString();
			
			if (us.isPresent()) {
				if(auth.equals("approve")) {
					
					String generatedString = new Random().ints(97, 122 + 1).limit(10)
							.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
							.toString();

					MessageDigest md = MessageDigest.getInstance("SHA-256");
					md.update(generatedString.getBytes());

					String subject = "Welcome To Vendor Management System - Login Credentials";
					String body = "<h1>Welcome To Vendor Management System</h1>"

							+ "\nUser Login ID  -: " + us.get().getUserid() + "\nPassword -: " + generatedString
							+ "\nURL -: http://localhost:9098/supplierums/login"
							+ "\n\nEnjoy your new experience with Vendor Management System." + "\nThank You!,"
							+ "\nTeam VMS" + "\n\nContact Us." + "\nemail : portal.admin@VMS.com"
							+ "\nPhone : 0112785452" + "\nPlease do not reply to this email";

					new Thread(new Runnable() {

						@Override
						public void run() {
							System.out.println("email -------------------- " + us.get().getEmail());
							List<String> to = new ArrayList<String>();
							to.add(us.get().getEmail());

							common.sendEMail(to, subject, body);
						}
					}).start();
					
					us.get().setStatus(3);
					us.get().setApprvreason(data.get("statusReason").toString());
					us.get().setPassowrd(new sun.misc.BASE64Encoder().encode(md.digest()));
					us.get().setStatusFlag("Y");
					userRepo.save(us.get());
					
				}else {
					us.get().setStatus(8);
					us.get().setApprvreason(data.get("statusReason").toString());
					us.get().setStatusFlag("Y");
					userRepo.save(us.get());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		String returnMsg = "Success";
		return returnMsg;
	}

	@Override
	public List<UserRole> getUserRolesForEdit() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long userCompanyID = new Long(user.getCompanyCode());
		
		try {
			List<UserRole> next = userRollRepo.findByCompanyCode(userCompanyID);
			System.out.println(next);
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject getUserRolesForEditFindById(Long userRoleId) {
		try {
			JSONObject json = new JSONObject();
			Optional<UserRole> next = userRollRepo.findById(userRoleId);
			String roleName = next.get().getUserRoleName();
			Long roleId = next.get().getUserRoleID();
			json.put("roleName", roleName);
			json.put("roleId", roleId);
			System.out.println(next);
			return json;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject getLocationCodeForEditFindById(Long locationCode) {
		try {
			JSONObject json = new JSONObject();
			Optional<LocationDetails> next = locationDetailsRepo.findById(locationCode);
			String locName = next.get().getLocationname();
			Long locId = next.get().getId();
			json.put("locId", locId);
			json.put("locName", locName);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<LocationDetails> getLocationCodeForEdit() {
		try {
			List<LocationDetails> next = locationDetailsRepo.findAll();
			System.out.println(next);
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject getDepartmentCodeForEditFindById(Long depId) {
		try {
			JSONObject json = new JSONObject();
			Optional<Department> next = departmentDetailsRepo.findById(depId);
			String depName = next.get().getDepartmentName();
			Long depId1 = next.get().getDeptId();
			json.put("depId1", depId1);
			json.put("depName", depName);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Department> getDepartmentCodeForEdit() {
		try {
			List<Department> next = departmentDetailsRepo.findAll();
			System.out.println(next);
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String addTemporaryUser(JSONObject data, HttpServletRequest request) {
		String msg = "Success";
		UserTemporary tempUser = new UserTemporary();
		System.out.println("userId"+data.get("userId").toString());
		String userId1 = data.get("userId").toString();
		Optional<User> user = userRepo.findByUserid(userId1);
		//Optional<User> user = userRepo.getDetailsFromUser(userId);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User userr = (User) authentication.getPrincipal();
		String userId = userr.getUserid();
		
		try {
			tempUser.setUseridTemp(data.get("userId").toString());
			tempUser.setUserRoleId(Long.valueOf(data.get("userRole").toString()));
			tempUser.setLocationIDTemp(Long.valueOf(data.get("locatioCode").toString()));
			tempUser.setDeptIDTemp(Long.valueOf(data.get("departmentCode").toString()));
			tempUser.setNicTemp(data.get("nicNo").toString());
			tempUser.setUsernameTemp(data.get("dispalyName").toString());
			tempUser.setMobileNoTemp(data.get("mobileNo").toString());
			tempUser.setEmailTemp(data.get("userEmail").toString());
			tempUser.setCompanyCodeTemp(data.get("compCode").toString());
			
			LocalDate localDate = LocalDate.parse((data.get("dateEnd").toString()));

			
		//	LocalDate date1 = LocalDate.parse(data.get("dateEnd").toString());
			
			tempUser.setExpireDateTemp(localDate);
			
			tempUser.setCreatedUserTemp(userId);
			tempUser.setCreatedDateTimeTemp(LocalDateTime.now());
			userTemporaryRepo.save(tempUser);
			
			System.out.println("Userid from find"+user.get().getUserid());
			if(user.isPresent()) {
				//globle status table
				
				Long tempStatus = (long) 1;
				user.get().setTempUserStatus(tempStatus);
				userRepo.save(user.get());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error";
		}
		return msg;
	}

	@Override
	public JSONObject getAllTempUsersForAuth(HttpServletRequest request) {
		JSONObject data = new JSONObject();
		String parameter = "viewall";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		//Long userCompanyID = new Long(user.getCompanyCode());
		String userCompanyID = user.getCompanyCode();
		
		System.out.println("Long----- "+userCompanyID);
		try {
			data.put("draw", request.getParameter("draw"));
			String sorting="";
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "useridTemp";
			    break;
			  case "1":
				sorting = "usernameTemp";
			    break;
			  case "2":
				sorting = "nicTemp";
			    break;
			  case "3":
				sorting = "emailTemp";
			    break;
			  case "4":
				sorting = "ur.userRoleName";
			    break;
			  case "5":
				sorting = "dep.departmentName";
				break;
			  case "6":
				sorting = "mobileNoTemp";
				break;
			  case "7":
				sorting = "expireDateTemp";
				break;
			}
			Page<Object[]> dataList = null;
			
			if(parameter.equals("viewall")){
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =userRepo.getAllTempUsersForAuth(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =userRepo.getAllTempUsersForAuth(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			}
			
			JSONArray ar = new JSONArray();
//			System.out.println("Size -------------"+dataList.size());
			 for (Object[] UserDetails : dataList.getContent()) {
				 if(UserDetails[0] instanceof UserTemporary && UserDetails[1] instanceof Department && UserDetails[2] instanceof UserRole){
					 UserTemporary ut = (UserTemporary) UserDetails[0];
					 Department dep = (Department) UserDetails[1];
					 UserRole ur = (UserRole) UserDetails[2];
					 //SubCompany sc = (SubCompany) UserDetails[3];
				
					 JSONObject header = new JSONObject();
					 	
					 	header.put("primaryUserId", ut.getTempUserId());
					 	header.put("userID", ut.getUseridTemp());
					 	header.put("displayName", ut.getUsernameTemp());
					 	header.put("nic", ut.getNicTemp());
					 	header.put("email", ut.getEmailTemp());
					 	header.put("userRole", ur.getUserRoleName());
					 	header.put("departmentName", dep.getDepartmentName());
					 	header.put("tp", ut.getMobileNoTemp());
					 	header.put("expireDate", ut.getExpireDateTemp());
					 	
						JSONArray view = new JSONArray();
						
						header.put("details", view);
						ar.add(header);
				 }
				
				
				
			}
			 data.put("recordsTotal",dataList.getTotalElements());
			 data.put("recordsFiltered",dataList.getTotalElements());
			 data.put("data", ar);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	
		
		
	}

	@Override
	public String addTemporaryDataToUserTable(JSONObject data, HttpServletRequest request) {
		String msg = "Success";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User userAuth = (User) authentication.getPrincipal();
		String userAuthedId = userAuth.getUserid();
		
		Optional<User> user = userRepo.findByUserid(data.get("userId").toString());
		Optional<UserTemporary> userTemp = userTemporaryRepo.findById(Long.valueOf(data.get("primaryKeyOfTemp").toString()));
		UserEditLog userEditLog = new UserEditLog();
		
		try {
			
				userEditLog.setComment(data.get("comment").toString());
				userEditLog.setCompanyCode(Long.valueOf(user.get().getCompanyCode()));
				userEditLog.setCreatedDateTime(userTemp.get().getCreatedDateTimeTemp());
				userEditLog.setCreatedUser(userTemp.get().getCreatedUserTemp());
				userEditLog.setStatus((long) 3);
				userEditLog.setUpdatedDateTime(LocalDateTime.now());
				userEditLog.setUpdatedUser(userAuthedId);
				userEditLog.setUserId(data.get("userId").toString());
				userEditLogRepo.save(userEditLog);
				
				
				user.get().setDeptID(userTemp.get().getDeptIDTemp());
				user.get().setEmail(userTemp.get().getEmailTemp());
				user.get().setLocationID(userTemp.get().getLocationIDTemp());
				user.get().setMobileNo(userTemp.get().getMobileNoTemp());
				user.get().setNic(userTemp.get().getNicTemp());
				user.get().setUsername(userTemp.get().getUsernameTemp());
				
				//user.get().setUserRoll(Long.valueOf(userTemp.get().getUserRoleId()));
				
				UserRole newRole = new UserRole();
				newRole.setUserRoleID(userTemp.get().getUserRoleId());			
				user.get().setUserRoll(newRole);
				
				user.get().setTempUserStatus((long) 3);
				Date datee = Date.from(userTemp.get().getExpireDateTemp().atStartOfDay(ZoneId.systemDefault()).toInstant());
				user.get().setExpireDate(datee);
				userRepo.save(user.get());
				
				userTemporaryRepo.deleteById(Long.valueOf(data.get("primaryKeyOfTemp").toString()));
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error";
		}
		return msg;
	}

	@Override
	public String addTemporaryDataToUserTableRej(JSONObject data, HttpServletRequest request) {
		String msg = "Success";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User userAuth = (User) authentication.getPrincipal();
		String userAuthedId = userAuth.getUserid();
		
		Optional<User> user = userRepo.findByUserid(data.get("userId").toString());
		Optional<UserTemporary> userTemp = userTemporaryRepo.findById(Long.valueOf(data.get("primaryKeyOfTemp").toString()));
		UserEditLog userEditLog = new UserEditLog();
		
		try {
			
				userEditLog.setComment(data.get("comment").toString());
				userEditLog.setCompanyCode(Long.valueOf(user.get().getCompanyCode()));
				userEditLog.setCreatedDateTime(userTemp.get().getCreatedDateTimeTemp());
				userEditLog.setCreatedUser(userTemp.get().getCreatedUserTemp());
				userEditLog.setStatus((long) 4);
				userEditLog.setUpdatedDateTime(LocalDateTime.now());
				userEditLog.setUpdatedUser(userAuthedId);
				userEditLog.setUserId(data.get("userId").toString());
				userEditLogRepo.save(userEditLog);
				
				user.get().setTempUserStatus((long) 3);
				userRepo.save(user.get());
				
				userTemporaryRepo.deleteById(Long.valueOf(data.get("primaryKeyOfTemp").toString()));
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error";
		}
		return msg;
	}

	@Override
	public User checkUser(String id) {
		// TODO Auto-generated method stub
		User user = null;
		try {
			Optional<User> u = userRepo.findByUseridAndStatus(id, Integer.valueOf("3"));
			if (u.isPresent()) {
				user = u.get();
			} else {

			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return user;
	}


}
