package it.fadeout.risckit;

import it.fadeout.risckit.business.Country;
import it.fadeout.risckit.business.EmailService;
import it.fadeout.risckit.business.PasswordGenerator;
import it.fadeout.risckit.business.User;
import it.fadeout.risckit.data.CountryRepository;
import it.fadeout.risckit.data.UserRepository;
import it.fadeout.risckit.viewmodels.CountryViewModel;
import it.fadeout.risckit.viewmodels.PrimitiveResult;
import it.fadeout.risckit.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
			if (sUserName != null && !sUserName.equals(""))
			{
				UserRepository oRepo = new UserRepository();
				User oUser = oRepo.SelectUser(sUserName, sPassword);

				if (oUser != null) {
					oReturnValue = new UserViewModel();
					oReturnValue.setId(oUser.getId());
					oReturnValue.setUserName(oUser.getUserName());
					oReturnValue.setPassword(oUser.getPassword());
					oReturnValue.setIsAdmin(oUser.getIsAdmin());
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
	public List<UserViewModel> GetUsersList() {
		try {

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
						oTempUsr.setPassword(oUser.getPassword());
						oTempUsr.setIsAdmin(oUser.getIsAdmin());
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


	@POST
	@Path("/update")
	@Produces({"application/xml", "application/json", "text/xml"})
	public PrimitiveResult Update(UserViewModel oUserViewModel) {
		PrimitiveResult oResult = new PrimitiveResult();
		try {

			if (oUserViewModel != null) {
				UserRepository oRepo = new UserRepository();

				// TODO: Qui se hanno cambiato pw non lo prendo!!!
				User oUser = oRepo.SelectUser(oUserViewModel.getUserName(), oUserViewModel.getPassword());
				oUser.setIsAdmin(oUserViewModel.getIsAdmin());
				// TODO: Set Pw
				oRepo.Save(oUser);


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


	@POST
	@Path("/requestNewUser")
	//@Produces({"application/xml", "application/json", "text/xml"})
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})

	public PrimitiveResult newUserRequest(UserViewModel oUserViewModel) {
		PrimitiveResult oResult = new PrimitiveResult();
		try {

			if (oUserViewModel != null) {
				
				UserRepository oRepo = new UserRepository();
				User oUser = new User();
				oResult.BoolValue=true;
				
				//check all input data 
				if( (oUserViewModel.getUserName() == null) || (oUserViewModel.getUserName().isEmpty()) )
					oResult.BoolValue = false;
				if( (oUserViewModel.getUserSurname() == null) || (oUserViewModel.getUserSurname().isEmpty()) )
					oResult.BoolValue = false;				
				if( (oUserViewModel.getAddress() == null) || (oUserViewModel.getAddress().isEmpty()) )
					oResult.BoolValue = false;
				if( (oUserViewModel.getState() == null) || (oUserViewModel.getState().isEmpty()) )
					oResult.BoolValue = false;
				if( (oUserViewModel.getPhoneNumber() == null) || (oUserViewModel.getPhoneNumber().isEmpty()) )
					oResult.BoolValue = false;
				if( (oUserViewModel.getEmail() == null) || (oUserViewModel.getEmail().isEmpty()) )
					oResult.BoolValue = false;
				if( (oUserViewModel.getInstitutionName() == null) || (oUserViewModel.getInstitutionName().isEmpty()) )
					oResult.BoolValue = false;
				if( (oUserViewModel.getRole() == null) || (oUserViewModel.getRole().isEmpty()) )
					oResult.BoolValue = false;
				if( (oUserViewModel.getReason() == null) || (oUserViewModel.getReason().isEmpty()) )
					oResult.BoolValue = false;
				if( (oUserViewModel.getFirstName() == null) || (oUserViewModel.getFirstName().isEmpty()) )
					oResult.BoolValue = false;
				
				if(oResult.BoolValue != false)
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
					EmailService oEmailService = new EmailService("mail.smtp.host");
					oEmailService.SendHtmlEmail("a.corrado@fadeout.it", "a.corrado@fadeout.it", "test", "test");
					
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
	
	@POST
	@Path("/addUserByAdmin")
	//@Produces({"application/xml", "application/json", "text/xml"})
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})

	public PrimitiveResult addUserByAdmin(UserViewModel oUserViewModel) {
		PrimitiveResult oResult = new PrimitiveResult();
		try {

			if (oUserViewModel != null) {
				
				UserRepository oRepo = new UserRepository();
				User oUser = new User();
				oResult.BoolValue=true;
				
				//check all input data 

				if( (oUserViewModel.getUserName() == null) || (oUserViewModel.getUserName().isEmpty()) )
					oResult.BoolValue = false;
//				if( (oUserViewModel.getUserSurname() == null) || (oUserViewModel.getUserSurname().isEmpty()) )
//					oResult.BoolValue = false;				
//				if( (oUserViewModel.getAddress() == null) || (oUserViewModel.getAddress().isEmpty()) )
//					oResult.BoolValue = false;
//				if( (oUserViewModel.getState() == null) || (oUserViewModel.getState().isEmpty()) )
//					oResult.BoolValue = false;
//				if( (oUserViewModel.getPhoneNumber() == null) || (oUserViewModel.getPhoneNumber().isEmpty()) )
//					oResult.BoolValue = false;
				if( (oUserViewModel.getEmail() == null) || (oUserViewModel.getEmail().isEmpty()) )
					oResult.BoolValue = false;
//				if( (oUserViewModel.getInstitutionName() == null) || (oUserViewModel.getInstitutionName().isEmpty()) )
//					oResult.BoolValue = false;
//				if( (oUserViewModel.getRole() == null) || (oUserViewModel.getRole().isEmpty()) )
//					oResult.BoolValue = false;
//				if( (oUserViewModel.getReason() == null) || (oUserViewModel.getReason().isEmpty()) )
//					oResult.BoolValue = false;

				if(oResult.BoolValue != false)
				{
					oUser.setUserName(oUserViewModel.getUserName());
//					oUser.setUserSurname(oUserViewModel.getUserSurname());
//					oUser.setAddress(oUserViewModel.getAddress());
//					oUser.setState(oUserViewModel.getState());
//					oUser.setPhonenumber(oUserViewModel.getPhoneNumber());
					oUser.setEmail(oUserViewModel.getEmail());
//					oUser.setInstitutionName(oUserViewModel.getInstitutionName());
//					oUser.setRole(oUserViewModel.getRole());
//					oUser.setReason(oUserViewModel.getReason());
					oUser.setIsConfirmed(true);
					oUser.setIsAdmin(false);
					PasswordGenerator session = new PasswordGenerator();
					oUser.setPassword(session.nextString());
					oRepo.Save(oUser);
					//EmailService oEmailService = new EmailService();
					//oEmailService.SendHtmlEmail("a.corrado@fadeout.it", "a.corrado@fadeout.it", "test", "test");
					
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
	
	@POST
	@Path("/updateUserName")
	//@Produces({"application/xml", "application/json", "text/xml"})
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})

	public PrimitiveResult updateUserName(UserViewModel oUserViewModel) {
		PrimitiveResult oResult = new PrimitiveResult();
		try {

			if (oUserViewModel != null) {
				
				UserRepository oRepo = new UserRepository();
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

	public PrimitiveResult generateNewPassword(UserViewModel oUserViewModel) {
		PrimitiveResult oResult = new PrimitiveResult();
		try {

			if (oUserViewModel != null) {
				
				UserRepository oRepo = new UserRepository();
				User oUser = oRepo.SelectUserById(oUserViewModel.getId());
				PasswordGenerator session = new PasswordGenerator();
				if(	(oUser == null) )
				{
					oResult.BoolValue = false;
				}
				else
				{
					oUser.setPassword(session.nextString());
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
	
	@DELETE
	@Path("/deleteUser/{id}")
	public PrimitiveResult deleteUser(@PathParam("id") Integer iId) 
	{
		PrimitiveResult oResult = new PrimitiveResult();
		oResult.BoolValue = true;
		try {
			
			if(	(iId == null) ) 
			{
				oResult.BoolValue = false;
			}else{
				//TODO: delete user
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

	public PrimitiveResult confirmNewUser(UserViewModel oUserViewModel) {
		PrimitiveResult oResult = new PrimitiveResult();
		try {

			if (oUserViewModel != null) {
				
				UserRepository oRepo = new UserRepository();
				User oUser = oRepo.SelectUserById(oUserViewModel.getId());
				
				PasswordGenerator session = new PasswordGenerator();
				if(	(oUser == null) )
				{
					oResult.BoolValue = false;
				}
				else
				{
					oUser.setIsConfirmed(true);
					oUser.setPassword(session.nextString());
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
	@Path("/newAdmin")
	//@Produces({"application/xml", "application/json", "text/xml"})
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})

	public PrimitiveResult addNewAdmin(UserViewModel oUserViewModel) {
		PrimitiveResult oResult = new PrimitiveResult();
		try {

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
	@Path("/changePassword")
	@Produces({"application/xml", "application/json", "text/xml"})
	public PrimitiveResult changePassword(UserViewModel oUserViewModel) {
		PrimitiveResult oResult = new PrimitiveResult();
		try {

			if (oUserViewModel != null) {
				UserRepository oRepo = new UserRepository();

			//	User oUser = oRepo.SelectUser(oUserViewModel.getUserName(), oUserViewModel.getPassword());
				User oUser = oRepo.SelectUserById(oUserViewModel.getId());
				if(	(oUser == null) )
				{
					oResult.BoolValue = false;
				}
				else
				{
					oUser.setPassword(oUserViewModel.getPassword());
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
