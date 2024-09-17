package lk.supplierUMS.SupplierUMS_REST.comman;

import org.springframework.stereotype.Component;


public class CommonEmail {
	
	private String headingOfEmail;
	private String contentOfEmail;
	private String footerTP = "T: +94 71 16 68 739 | Ext. 31152";
	
	public String getHeadingOfEmail() {
		return headingOfEmail;
	}

	public String getContentOfEmail() {
		return contentOfEmail;
	}
	
	public void setHeadingOfEmail(String headingOfEmail) {
		this.headingOfEmail = headingOfEmail;
	}

	public void setContentOfEmail(String contentOfEmail) {
		this.contentOfEmail = contentOfEmail;
	}
	
	public CommonEmail(String headingOfEmail, String contentOfEmail) {
		super();
		this.headingOfEmail = headingOfEmail;
		this.contentOfEmail = contentOfEmail;
		
		System.out.println(headingOfEmail);
		System.out.println(contentOfEmail);
	}
	
	
	
	//Below you can find all the attributes of this email
	
	//paragraph <p style="font-size: 14px; line-height: 140%;"><span style="font-size: 18px; line-height: 25.2px; color: #666666;">Hello,</span></p>
	//line space <p style="font-size: 14px; line-height: 140%;"> </p>
	//bold <p style="font-size: 14px; line-height: 140%;"><strong><span style="font-size: 18px; line-height: 25.2px; color: #666666;">Bold</span></strong></p>
	//italic <p style="font-size: 14px; line-height: 140%;"><em><span style="font-size: 18px; line-height: 25.2px; color: #666666;">Italic</span></em></p>
	//underline <p style="font-size: 14px; line-height: 140%;"><span style="text-decoration: underline; font-size: 14px; line-height: 19.6px;"><span style="font-size: 18px; line-height: 25.2px; color: #666666; text-decoration: underline;">Underline</span></span></p>
	//sup <p style="font-size: 14px; line-height: 140%;"><span style="font-size: 14px; line-height: 19.6px;"><span style="font-size: 18px; line-height: 25.2px; color: #666666;">C<sup>0 </sup></span></span></p>
	//sub <p style="font-size: 14px; line-height: 140%;"><span style="font-size: 14px; line-height: 19.6px;"><span style="font-size: 18px; line-height: 25.2px; color: #666666;">H<sub>2 </sub></span></span></p>
	//center <p style="font-size: 14px; line-height: 140%; text-align: center;"><span style="font-size: 14px; line-height: 19.6px;"><span style="font-size: 18px; line-height: 25.2px; color: #666666;"><sub>center para</sub></span></span></p>
	//right side <p style="font-size: 14px; line-height: 140%; text-align: right;"><span style="font-size: 14px; line-height: 19.6px;"><span style="font-size: 18px; line-height: 25.2px; color: #666666;"><sub>right side</sub></span></span></p>
	//justify <p style="font-size: 14px; line-height: 140%; text-align: justify;"><span style="font-size: 14px; line-height: 19.6px;"><span style="font-size: 18px; line-height: 25.2px; color: #666666;"><sub>Justify</sub></span></span></p>
	//bullets
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
	//different font <p style="font-size: 14px; line-height: 140%;"><span style="font-family: 'Fredoka One'; font-size: 14px; line-height: 19.6px;">different font</span></p>
	//different color <p style="font-size: 14px; line-height: 140%;"><span style="font-family: 'Fredoka One'; font-size: 14px; line-height: 19.6px; color: #ba372a;">different color</span></p>
	//hightlight <p style="font-size: 14px; line-height: 140%;"><span style="font-family: 'Fredoka One'; font-size: 14px; line-height: 19.6px; color: #ffffff; background-color: #000000;"> different font highlight </span></p>
	
	String firstPart = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"
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
			+ "<!--[if !mso]><!--><link href=\"https://fonts.googleapis.com/css2?family=Fredoka+One&display=swap\" rel=\"stylesheet\" type=\"text/css\"><link href=\"https://fonts.googleapis.com/css?family=Lato:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\r\n"
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
			+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
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
			+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
			+ "  \r\n"
			+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
			+ "  <tbody>\r\n"
			+ "    <tr>\r\n"
			+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:25px 54px 19px 0px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
			+ "        \r\n"
			+ "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
			+ "  <tr>\r\n"
			+ "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n"
			+ "      \r\n"
			+ "      <img align=\"center\" border=\"0\" src=\"https://www.ndbbank.com/images/logo.svg\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 43%;max-width: 234.78px;\" width=\"234.78\"/>\r\n"
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
			+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
			+ "  \r\n"
			+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
			+ "  <tbody>\r\n"
			+ "    <tr>\r\n"
			+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
			+ "        \r\n"
			+ "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
			+ "    <p style=\"font-size: 14px; line-height: 140%; text-align: center;\"> </p>\r\n"
			+ "<p style=\"font-size: 14px; line-height: 140%; text-align: center;\"><span style=\"font-size: 28px; line-height: 39.2px; color: #ffffff; font-family: Lato, sans-serif;\">";
		
	String headingName = headingOfEmail;
	
	String secondPart = "</span></p>\r\n"
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
			+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
			+ "  \r\n"
			+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
			+ "  <tbody>\r\n"
			+ "    <tr>\r\n"
			+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 40px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
			+ "        \r\n"
			+ "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">";
	
	String content = contentOfEmail;
	
	String thirdPart = " </div>\r\n"
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
			+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"height: 100%; padding: 20px 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
			+ "  \r\n"
			+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
			+ "  <tbody>\r\n"
			+ "    <tr>\r\n"
			+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
			+ "        \r\n"
			+ "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
			+ "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 16px; line-height: 22.4px; color: #ecf0f1;\">National Development Bank PLC </span></p>\r\n"
			+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 14px; line-height: 19.6px; color: #ecf0f1;\">40, Nawam Mawatha, Colombo 2, Sri Lanka</span></p>\r\n"
			+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 14px; line-height: 19.6px; color: #ecf0f1;\">"+footerTP+"</span></p>\r\n"
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
			+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"height: 100%; padding: 0px 0px 0px 20px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
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
			+ "<p style=\"line-height: 140%; font-size: 14px;\"><span style=\"font-size: 14px; line-height: 19.6px;\"><span style=\"color: #ecf0f1; font-size: 14px; line-height: 19.6px;\"><span style=\"line-height: 19.6px; font-size: 14px;\">National Development Bank PLC © All Rights Reserved</span></span></span></p>\r\n"
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
			+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
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
			+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
			+ "  \r\n"
			+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
			+ "  <tbody>\r\n"
			+ "    <tr>\r\n"
			+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 40px 30px 20px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
			+ "        \r\n"
			+ "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
			+ "    \r\n"
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


	public String getFooterTP() {
		return footerTP;
	}

	public String getFirstPart() {
		return firstPart;
	}

	public String getHeadingName() {
		return headingName;
	}

	public String getSecondPart() {
		return secondPart;
	}

	public String getContent() {
		return content;
	}

	public String getThirdPart() {
		return thirdPart;
	}

	

	public void setFooterTP(String footerTP) {
		this.footerTP = footerTP;
	}

	public void setFirstPart(String firstPart) {
		this.firstPart = firstPart;
	}

	public void setHeadingName(String headingName) {
		this.headingName = headingName;
	}

	public void setSecondPart(String secondPart) {
		this.secondPart = secondPart;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setThirdPart(String thirdPart) {
		this.thirdPart = thirdPart;
	}
	
	
}
