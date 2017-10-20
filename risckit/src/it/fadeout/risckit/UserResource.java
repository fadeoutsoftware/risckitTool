package it.fadeout.risckit;

import it.fadeout.risckit.business.ConfigReader;
import it.fadeout.risckit.business.Country;
import it.fadeout.risckit.business.EmailService;
import it.fadeout.risckit.business.StringGenerator;
import it.fadeout.risckit.business.Token;
import it.fadeout.risckit.business.User;
import it.fadeout.risckit.data.CountryRepository;
import it.fadeout.risckit.data.TokenRepository;
import it.fadeout.risckit.data.UserRepository;
import it.fadeout.risckit.viewmodels.CountryViewModel;
import it.fadeout.risckit.viewmodels.HttpRequestResult;
import it.fadeout.risckit.viewmodels.PrimitiveResult;
import it.fadeout.risckit.viewmodels.UserViewModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/users")
public class UserResource {

	@POST
	@Path("/login")
	@Produces({"application/xml", "application/json", "text/xml"})
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public UserViewModel Login(@FormDataParam("username") String sUserName, @FormDataParam("password") String sPassword) {

		try
		{	
			UserViewModel oReturnValue = null;				
			UserRepository oRepo = new UserRepository();
			StringGenerator session = new StringGenerator(20);
			TokenRepository oTokenRepo = new TokenRepository();
			Token oNewUserToken = new Token();
			String sTokenString = session.nextString(); 

			
			if (sUserName != null && !sUserName.equals(""))
			{
				// CHEK USER

				User oUser = oRepo.SelectUser(sUserName, sPassword);
				if ( (oUser != null) && (oUser.getIsConfirmed() == true) ) 
				{
//					List<Token> aoUserToken = oTokenRepo.SelectTokensUser(oUser.getId());
//					List<Token> aoAllTokens = oTokenRepo.SelectAll(Token.class);
//					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//					
//					
//					for (Integer i = 0 ; i < aoAllTokens.size() ; i++) {
//						
//						//CHECK IF TOKEN STRING IS UNIQUE
//						if( aoAllTokens.get(i).getToken().equals(sTokenString))
//						{
//							i = 0;
//							sTokenString = session.nextString();
//						}
//					}
//					
//					/* CHECK  THE TIMESTAMP */
//					Date oDateNow = new Date();
//					if(aoUserToken != null)
//					{
//						for (Token oUserToken : aoUserToken) 
//						{
//							if( oUserToken.getTimestamp() > 0 )
//							{
//								long lMillisTimeStampToken =  oUserToken.getTimestamp();
//								Date oDateTimeStamp = new Date(lMillisTimeStampToken);	        
//								long diff = oDateNow.getTime() - oDateTimeStamp.getTime();
//								long diffHours = diff / (60 * 60 * 1000);
//								//IF THE TOKEN DATE > 24 HOURS REMOVE IT
//								if(diffHours > 24 )
//								{
//									oTokenRepo.Delete(oUserToken);
//								}
//							}
//						}
//					}
//				
//					
//					//CREATE NEW TOKEN
//					oNewUserToken.setToken(sTokenString);
//					oNewUserToken.setUserId(oUser.getId());
//					oNewUserToken.setTimestamp(timestamp.getTime()); 
//					oTokenRepo.Save(oNewUserToken);
					
					
					//RETURN USER
					oReturnValue = new UserViewModel();
					oReturnValue.setId(oUser.getId());
					oReturnValue.setUserName(oUser.getUserName());
//					oReturnValue.setPassword(oUser.getPassword());
					oReturnValue.setToken("Token");
					oReturnValue.setIsAdmin(oUser.getIsAdmin());
				}
				else{
					return null;
				}
			}
			return oReturnValue;
		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();
			return null;
		}

	}

	@GET
	@Path("/list")
	public List<UserViewModel> GetUsersList(@HeaderParam("Auth-Token") String sToken) {
		try {

			//CHECK TIMESTAMP 
			
//			TokenRepository oTokenRepo = new TokenRepository();
//			if(oTokenRepo.timestampIsExpiredAndDeleted(sToken) == true)
//				return null;
			
			List<UserViewModel> aoRetList = new ArrayList<>();

			UserRepository oRepo = new UserRepository();
			List<User> aoUsers = oRepo.SelectAll(User.class);

			if (aoUsers!= null) {

				for (int iUsers = 0; iUsers<aoUsers.size(); iUsers++) {
					User oUser = aoUsers.get(iUsers);
					if (oUser != null) {
						UserViewModel oTempUsr = new UserViewModel();
						oTempUsr.setId(oUser.getId());
						oTempUsr.setUserName(oUser.getUserName());
						//oTempUsr.setPassword(oUser.getPassword());
						oTempUsr.setIsAdmin(oUser.getIsAdmin());
						
						oTempUsr.setUserSurname(oUser.getUserSurname());
						oTempUsr.setAddress(oUser.getAddress());
						oTempUsr.setState(oUser.getState());
						oTempUsr.setPhoneNumber(oUser.getPhonenumber());
						oTempUsr.setEmail(oUser.getEmail());
						oTempUsr.setInstitutionName(oUser.getInstitutionName());
						oTempUsr.setRole(oUser.getRole());
						oTempUsr.setReason(oUser.getReason());
						oTempUsr.setFirstName(oUser.getFirstName());
						oTempUsr.setIsConfirmed(oUser.getIsConfirmed());
						oTempUsr.setIsAdmin(oUser.getIsAdmin());

						
						//oRepo.Save(oUser);
						aoRetList.add(oTempUsr);
					}
				}

			}

			return aoRetList;
		}
		catch (Exception oEx) {
			oEx.printStackTrace();
			return null;
		}
	}


//	@POST
//	@Path("/update")
//	@Produces({"application/xml", "application/json", "text/xml"})
//	public PrimitiveResult Update(UserViewModel oUserViewModel) {
//		PrimitiveResult oResult = new PrimitiveResult();
//		try {
//
//			if (oUserViewModel != null) {
//				UserRepository oRepo = new UserRepository();
//
//				// TODO: Qui se hanno cambiato pw non lo prendo!!!
//				User oUser = oRepo.SelectUser(oUserViewModel.getUserName(), oUserViewModel.getPassword());
//				oUser.setIsAdmin(oUserViewModel.getIsAdmin());
//				// TODO: Set Pw
//				oRepo.Save(oUser);
//
//
//				oResult.BoolValue = true;
//			}
//			else {
//				oResult.BoolValue = false;
//			}
//
//
//		}
//		catch (Exception oEx) {
//			oEx.printStackTrace();
//			oResult.BoolValue = false;
//		}
//
//		return oResult;
//	}


	@POST
	@Path("/requestNewUser")
	//@Produces({"application/xml", "application/json", "text/xml"})
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})

	public HttpRequestResult newUserRequest(@HeaderParam("Auth-Token") String sToken, UserViewModel oUserViewModel)
	{
		HttpRequestResult oResult = new HttpRequestResult();
		try {
			
//			TokenRepository oTokenRepo = new TokenRepository();
//			if(oTokenRepo.timestampIsExpiredAndDeleted(sToken) == true)
//				return null;
			
			//CHECK TIMESTAMP 
//			Date oDateNow = new Date();
//			Token oUserToken = oTokenRepo.SelectTokenByToken(sToken);
//			if( (oUserToken != null) && (oUserToken.getTimestamp() > 0) )
//			{
//				long lMillisTimeStampToken =  oUserToken.getTimestamp();
//				Date oDateTimeStamp = new Date(lMillisTimeStampToken);	        
//				long diff = oDateNow.getTime() - oDateTimeStamp.getTime();
//				long diffHours = diff / (60 * 60 * 1000);
//				//IF THE TOKEN DATE > 24 HOURS REMOVE IT
//				if(diffHours > 24 )
//				{
//					oTokenRepo.Delete(oUserToken);
//					return null;
//				}
//			}
			
			if (oUserViewModel != null) {
				
				UserRepository oRepo = new UserRepository();
				User oUser = new User();
				oResult.BoolValue = true;
				
				//check all input data 
				if( (oUserViewModel.getUserName() == null) || (oUserViewModel.getUserName().isEmpty()) || ( oRepo.isSavedUserName(oUserViewModel.getUserName()) == true))
				{
					oResult.setError("The Username is in use or it's invalid. Please enter another one");
				}		
				else if( (oUserViewModel.getUserSurname() == null) || (oUserViewModel.getUserSurname().isEmpty()) )
				{
					oResult.setError("Some fields are empty");
				}
							
				else if( (oUserViewModel.getAddress() == null) || (oUserViewModel.getAddress().isEmpty()) )
				{
					oResult.setError("Some fields are empty");
				}
				else if( (oUserViewModel.getState() == null) || (oUserViewModel.getState().isEmpty()) )
				{
					oResult.setError("Some fields are empty");
				}
				else if( (oUserViewModel.getPhoneNumber() == null) || (oUserViewModel.getPhoneNumber().isEmpty()) )
				{
					oResult.setError("Some fields are empty");	
				}
				else if( (oUserViewModel.getEmail() == null) || (oUserViewModel.getEmail().isEmpty()) )
				{
					oResult.setError("Some fields are empty");
				}
				else if( (oUserViewModel.getInstitutionName() == null) || (oUserViewModel.getInstitutionName().isEmpty()) )
				{
					oResult.setError("Some fields are empty");
				}
				else if( (oUserViewModel.getRole() == null) || (oUserViewModel.getRole().isEmpty()) )
				{
					oResult.setError("Some fields are empty");	
				}
				else if( (oUserViewModel.getReason() == null) || (oUserViewModel.getReason().isEmpty()) )
				{
					oResult.setError("Some fields are empty");
				}
				else if( (oUserViewModel.getFirstName() == null) || (oUserViewModel.getFirstName().isEmpty()) )
				{
					oResult.setError("Some fields are empty");
				}
				
				if(oResult.hasError() == false)
				{
					oUser.setUserName(oUserViewModel.getUserName());
					oUser.setUserSurname(oUserViewModel.getUserSurname());
					oUser.setAddress(oUserViewModel.getAddress());
					oUser.setState(oUserViewModel.getState());
					oUser.setPhonenumber(oUserViewModel.getPhoneNumber());
					oUser.setEmail(oUserViewModel.getEmail());
					oUser.setInstitutionName(oUserViewModel.getInstitutionName());
					oUser.setRole(oUserViewModel.getRole());
					oUser.setReason(oUserViewModel.getReason());
					oUser.setFirstName(oUserViewModel.getFirstName());
					oUser.setIsConfirmed(false);
					oUser.setIsAdmin(false);
					oUser.setPassword("");
					oRepo.Save(oUser);
					
					EmailService oEmailService = new EmailService();
					String sTextEmail="<div>There is new account request in Risckit server, User: "+ oUserViewModel.getUserName()+ "Email: "+ oUserViewModel.getEmail() +".<br><br> Risckit Server</div>";
					oEmailService.SendHtmlEmail(ConfigReader.getPropValue("HOST_EMAIL"), ConfigReader.getPropValue("HOST_EMAIL"), "Pending new account",sTextEmail );
					
					oResult.setSuccess();;
				}

				
			}
			else {
				oResult.setError("Error: no registration data received");
			}


		}
		catch (Exception oEx) {
			oEx.printStackTrace();
			oResult.setError("Internal error");
		}

		return oResult;
	}
	
	@POST
	@Path("/addUserByAdmin")
	//@Produces({"application/xml", "application/json", "text/xml"})
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})

	public PrimitiveResult addUserByAdmin(@HeaderParam("Auth-Token") String sToken,UserViewModel oUserViewModel) {
		PrimitiveResult oResult = new PrimitiveResult();
		try {
			
//			TokenRepository oTokenRepo = new TokenRepository();
//			if(oTokenRepo.timestampIsExpiredAndDeleted(sToken) == true)
//				return null;
			
			//CHECK TIMESTAMP 
//			Date oDateNow = new Date();
//			Token oUserToken = oTokenRepo.SelectTokenByToken(sToken);
//			if( (oUserToken != null) && (oUserToken.getTimestamp() > 0) )
//			{
//				long lMillisTimeStampToken =  oUserToken.getTimestamp();
//				Date oDateTimeStamp = new Date(lMillisTimeStampToken);	        
//				long diff = oDateNow.getTime() - oDateTimeStamp.getTime();
//				long diffHours = diff / (60 * 60 * 1000);
//				//IF THE TOKEN DATE > 24 HOURS REMOVE IT
//				if(diffHours > 24 )
//				{
//					oTokenRepo.Delete(oUserToken);
//					return null;
//				}
//			}
			
			if (oUserViewModel != null) {
				
				UserRepository oRepo = new UserRepository();
				User oUser = new User();
				oResult.BoolValue=true;
				
				//check all input data 

				if( (oUserViewModel.getUserName() == null) || (oUserViewModel.getUserName().isEmpty()) || oRepo.isSavedUserName(oUserViewModel.getUserName()) )
					oResult.BoolValue = false;
				if( (oUserViewModel.getEmail() == null) || (oUserViewModel.getEmail().isEmpty()) )
					oResult.BoolValue = false;

				if(oResult.BoolValue != false )
				{
					
					oUser.setUserName(oUserViewModel.getUserName());
					oUser.setEmail(oUserViewModel.getEmail());

					oUser.setIsConfirmed(true);
					oUser.setIsAdmin(false);
					StringGenerator session = new StringGenerator();
					oUser.setPassword(session.nextString());
					oRepo.Save(oUser);
					if(oUserViewModel.getEmail() != null){
						String sText = "<div>Welcome in Risckit,<br><br>Your new account credetials are user Name: "+ oUser.getUserName() + " <br>Password: "+ oUser.getPassword() +" Risckit Link: http://risckit.cloudapp.net/risckit/#/ <br><br> Risckit Team </div>";
						EmailService oEmailService = new EmailService();
						oEmailService.SendHtmlEmail(oUserViewModel.getEmail(), ConfigReader.getPropValue("HOST_EMAIL"), "New account RiscKit", sText );
					}

					oResult.BoolValue = true;
				}

				
			}
			else {
				oResult.BoolValue = false;
			}


		}
		catch (Exception oEx) {
			oEx.printStackTrace();
			oResult.BoolValue = false;
		}

		return oResult;
	}
	
	@POST
	@Path("/updateUserName")
	//@Produces({"application/xml", "application/json", "text/xml"})
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})

	public PrimitiveResult updateUserName(@HeaderParam("Auth-Token") String sToken,UserViewModel oUserViewModel) {
		PrimitiveResult oResult = new PrimitiveResult();
		try {
			
//			TokenRepository oTokenRepo = new TokenRepository();
//			if(oTokenRepo.timestampIsExpiredAndDeleted(sToken) == true)
//				return null;
//			
			UserRepository oRepo = new UserRepository();
			if (oUserViewModel != null && oRepo.isSavedUserName(oUserViewModel.getUserName()) == false) {
				
				
				User oUser = oRepo.SelectUserById(oUserViewModel.getId());
				if(	(oUser == null) )
				{
					oResult.BoolValue = false;
				}
				else
				{
					oUser.setUserName(oUserViewModel.getUserName());
					oRepo.Save(oUser);
					oResult.BoolValue = true;
				}

			}
			else {
				oResult.BoolValue = false;
			}


		}
		catch (Exception oEx) {
			oEx.printStackTrace();
			oResult.BoolValue = false;
		}

		return oResult;
	}
	
	@POST
	@Path("/generateNewPassword")
	//@Produces({"application/xml", "application/json", "text/xml"})
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})

	public PrimitiveResult generateNewPassword(@HeaderParam("Auth-Token") String sToken,UserViewModel oUserViewModel) {
		PrimitiveResult oResult = new PrimitiveResult();
		try {

			
//			TokenRepository oTokenRepo = new TokenRepository();
//			if(oTokenRepo.timestampIsExpiredAndDeleted(sToken) == true)
//				return null;
			
			if (oUserViewModel != null) {
				
				UserRepository oRepo = new UserRepository();
				User oUser = oRepo.SelectUserById(oUserViewModel.getId());
				StringGenerator session = new StringGenerator();
				if(	(oUser == null) )
				{
					oResult.BoolValue = false;
				}
				else
				{
					oUser.setPassword(session.nextString());
					oRepo.Save(oUser);					
					if(oUser.getEmail() != null)
					{
						EmailService oEmailService = new EmailService();
						oEmailService.SendHtmlEmail(oUser.getEmail(), ConfigReader.getPropValue("HOST_EMAIL"), "New Password", "<div>New Password was generated as you request, password: "+ oUser.getPassword() + "<br><br>Risckit Team</div>" );
					}
					
					oResult.BoolValue = true;

				}

				 
				
			}
			else {
				oResult.BoolValue = false;
			}


		}
		catch (Exception oEx) {
			oEx.printStackTrace();
			oResult.BoolValue = false;
		}

		return oResult;
	}
	
	@DELETE
	@Path("/deleteUser/{id}")
	public PrimitiveResult deleteUser(@HeaderParam("Auth-Token") String sToken,@PathParam("id") Integer iId) 
	{
		PrimitiveResult oResult = new PrimitiveResult();
		oResult.BoolValue = true;
		
//		TokenRepository oTokenRepo = new TokenRepository();
//		if(oTokenRepo.timestampIsExpiredAndDeleted(sToken) == true)
//			return null;
		
		try {
			
			if(	(iId == null) ) 
			{
				oResult.BoolValue = false;
			}else{
				UserRepository oRepo = new UserRepository();
				User oUser = oRepo.SelectUserById(iId);
				oRepo.Delete(oUser);
			}
			
		}
		catch (Exception oEx) 
		{
			oEx.printStackTrace();
			oResult.BoolValue = false;
		}
		return oResult;
		
	}
	
	@POST
	@Path("/accept")
	//@Produces({"application/xml", "application/json", "text/xml"})
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})

	public PrimitiveResult confirmNewUser(@HeaderParam("Auth-Token") String sToken,UserViewModel oUserViewModel) {
		PrimitiveResult oResult = new PrimitiveResult();
		try {
			
//			TokenRepository oTokenRepo = new TokenRepository();
//			if(oTokenRepo.timestampIsExpiredAndDeleted(sToken) == true)
//				return null;
			
			if (oUserViewModel != null) {
				
				UserRepository oRepo = new UserRepository();
				//if(oRepo.isSavedUserName(oUserViewModel.getUserName())) 
					
				User oUser = oRepo.SelectUserById(oUserViewModel.getId());
				
					
				StringGenerator session = new StringGenerator();
				if(	(oUser == null) )
				{
					oResult.BoolValue = false;
				}
				else
				{
					oUser.setIsConfirmed(true);
					oUser.setPassword(session.nextString());
					oRepo.Save(oUser);
					if(oUser.getEmail() != null)
					{
						String sText="<div>Welcome in Risckit,<br> We accepted your request, your new credetials are User name: " + oUser.getUserName() + " Password: " + oUser.getPassword() + " Risckit Link: http://risckit.cloudapp.net/risckit/#/ <br><br> RiscKit Team</div>";
						EmailService oEmailService = new EmailService();
						oEmailService.SendHtmlEmail(oUser.getEmail(), ConfigReader.getPropValue("HOST_EMAIL"), "RiscKit New account", sText);
					}

					oResult.BoolValue = true;
				}

				 
				
			}
			else {
				oResult.BoolValue = false;
			}


		}
		catch (Exception oEx) {
			oEx.printStackTrace();
			oResult.BoolValue = false;
		}

		return oResult;
	}
	
	@POST
	@Path("/newAdmin")
	//@Produces({"application/xml", "application/json", "text/xml"})
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})

	public PrimitiveResult addNewAdmin(@HeaderParam("Auth-Token") String sToken,UserViewModel oUserViewModel) {
		PrimitiveResult oResult = new PrimitiveResult();
		try {
//			
//			TokenRepository oTokenRepo = new TokenRepository();
//			if(oTokenRepo.timestampIsExpiredAndDeleted(sToken) == true)
//				return null;
			
			if (oUserViewModel != null) {
				
				UserRepository oRepo = new UserRepository();
				User oUser = oRepo.SelectUserById(oUserViewModel.getId());
				
				if(	(oUser == null) )
				{
					oResult.BoolValue = false;
				}
				else
				{
					oUser.setIsAdmin(true);
					oRepo.Save(oUser);
					oResult.BoolValue = true;
				}

				 
				
			}
			else {
				oResult.BoolValue = false;
			}


		}
		catch (Exception oEx) {
			oEx.printStackTrace();
			oResult.BoolValue = false;
		}

		return oResult;
	}
	
	@POST
	@Path("/editUser")
	//@Produces({"application/xml", "application/json", "text/xml"})
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})

	public PrimitiveResult editPassword(@HeaderParam("Auth-Token") String sToken,UserViewModel[] aoUser) {
		PrimitiveResult oResult = new PrimitiveResult();
		
		try {
			
//			TokenRepository oTokenRepo = new TokenRepository();
//			if(oTokenRepo.timestampIsExpiredAndDeleted(sToken) == true)
//				return null;
			
			UserViewModel oNewUser = aoUser[1]; 
			UserViewModel oOldUser = aoUser[0];
			if (oOldUser != null) {
				UserRepository oRepo = new UserRepository();

			//	User oUser = oRepo.SelectUser(oUserViewModel.getUserName(), oUserViewModel.getPassword());
				User oUser = oRepo.SelectUserById(oOldUser.getId());
				if(	(oUser == null) )
				{
					oResult.BoolValue = false;
				}
				else
				{
					if( (oNewUser.getPassword() != null) && (oNewUser.getPassword().isEmpty() == false) )
						oUser.setPassword(oNewUser.getPassword());

					if( (oNewUser.getUserName() != null) && (oNewUser.getUserName().isEmpty() == false) )
						oUser.setUserName(oNewUser.getUserName());
					
					//EmailService oEmailService = new EmailService();
					//oEmailService.SendHtmlEmail(oUser.getEmail(), "a.corrado@fadeout.it", "test", "The account was edited by admin, password:" + oUser.getPassword() + " User Name:" + oNewUser.getUserName());
					//oUser.setIsAdmin(oUserViewModel.getIsAdmin());
					
					oRepo.Save(oUser);
				}



				oResult.BoolValue = true;
			}
			else {
				oResult.BoolValue = false;
			}


		}
		catch (Exception oEx) {
			oEx.printStackTrace();
			oResult.BoolValue = false;
		}

		return oResult;
	}

}
