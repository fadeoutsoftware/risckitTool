package it.fadeout.risckit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import it.fadeout.risckit.business.Country;
import it.fadeout.risckit.business.Event;
import it.fadeout.risckit.business.Media;
import it.fadeout.risckit.business.SocioImpact;
import it.fadeout.risckit.business.User;
import it.fadeout.risckit.data.CountryRepository;
import it.fadeout.risckit.data.EventRepository;
import it.fadeout.risckit.data.MediaRepository;
import it.fadeout.risckit.data.SocioImpactRepository;
import it.fadeout.risckit.data.UserRepository;
import it.fadeout.risckit.viewmodels.MediaViewModel;
import it.fadeout.risckit.viewmodels.SocioImpactViewModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/socioimpact")
public class SocioImpactResource {

	@POST
	@Path("/save")
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})
	public SocioImpactViewModel Save(SocioImpactViewModel oSocioImpactViewModel) {

		SocioImpactRepository oRepo = new SocioImpactRepository();
		try
		{
			if (oSocioImpactViewModel != null)
			{
				SocioImpact oSocio = new SocioImpact();
				oSocio.SetEntity(oSocioImpactViewModel);
				if (oSocioImpactViewModel.getId() == null || oSocioImpactViewModel.getId() == 0)
					oRepo.Save(oSocio);
				else
				{
					oSocio.setId(oSocioImpactViewModel.getId());
					oRepo.Update(oSocio);
				}

				if (oSocio != null)
				{
					oSocio = oRepo.Select(oSocio.getId(), SocioImpact.class);
					oSocioImpactViewModel = oSocio.GetViewModel();
				}
			}
		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();

		}
		finally
		{
			oRepo.CloseSession();
		}

		return oSocioImpactViewModel;
	}

	@GET
	@Path("/event/{idevent}")
	@Produces({"application/json", "application/xml", "text/xml"})
	public List<SocioImpactViewModel> getSocioList(@PathParam("idevent") int iIdEvent) {

		List<SocioImpactViewModel> oReturnList = new ArrayList<SocioImpactViewModel>();
		SocioImpactRepository oRepo = new SocioImpactRepository();
		try
		{
			List<SocioImpact> oSocioList = oRepo.SelectByEvent(iIdEvent);
			for (SocioImpact oSocio : oSocioList) {
				oReturnList.add(oSocio.GetViewModel());
			}
		}
		finally{
			oRepo.CloseSession();
		}

		return oReturnList;
	}

	@GET
	@Path("/{idsocio}")
	@Produces({"application/json", "application/xml", "text/xml"})
	public SocioImpactViewModel getSocioImpact(@PathParam("idsocio") Integer iIdSocio) {

		SocioImpactViewModel oReturnValue = null;
		SocioImpactRepository oRepo = new SocioImpactRepository();
		try{
			SocioImpact oSocioImpact = oRepo.Select(iIdSocio, SocioImpact.class);
			if (oSocioImpact != null)
				oReturnValue = oSocioImpact.GetViewModel();
		}
		finally{
			oRepo.CloseSession();
		}
		return oReturnValue;
	}

	@POST
	@Path("/delete/{idsocio}")
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})
	public List<SocioImpactViewModel> Delete(@PathParam("idsocio") int iIdSocio) {

		List<SocioImpactViewModel> oReturnList = null;
		SocioImpactRepository oRepo = new SocioImpactRepository();
		try
		{
			SocioImpact oSocio = oRepo.Select(iIdSocio, SocioImpact.class);
			if (oSocio != null)
				oRepo.Delete(oSocio);

			if (oSocio != null)
			{
				List<SocioImpact> oSocioList = oRepo.SelectByEvent(oSocio.getIdEvent());
				if (oSocioList != null)
				{
					oReturnList = new ArrayList<SocioImpactViewModel>();
					for (SocioImpact oItem : oSocioList) {
						oReturnList.add(oItem.GetViewModel());
					}
				}
			}
		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();

		}
		finally
		{
			oRepo.CloseSession();
		}

		return oReturnList;
	}

}
