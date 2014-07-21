package it.fadeout.risckit;

import it.fadeout.risckit.business.Country;
import it.fadeout.risckit.data.CountryRepository;
import it.fadeout.risckit.viewmodels.CountryViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;



@Path("/countries")
public class CountryResource {

	@GET
	@Path("/all")
	@Produces({"application/json"})
	public ArrayList<CountryViewModel> GetAllCountries() {

		try
		{	
			ArrayList<CountryViewModel> oReturnList = new ArrayList<CountryViewModel>();
			CountryRepository oRepo = new CountryRepository();
			List<Country> oEntities = oRepo.SelectAllCountries();

			if (oEntities != null)
			{
				for (Country oCountry : oEntities) {
					CountryViewModel oViewModel = new CountryViewModel();
					oViewModel.setid(oCountry.getId());
					oViewModel.setcountryname(oCountry.getName());
					oViewModel.setCountryCode(oCountry.getCountryCode());
					oReturnList.add(oViewModel);
				}
			}

			return oReturnList;

		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();
			return null;
		}

	}

	@GET
	@Path("/regions/{countrycode}")
	@Produces({"application/json"})
	public ArrayList<CountryViewModel> GetAllRegions(@PathParam("countrycode") String sCountryCode) {

		try
		{	
			ArrayList<CountryViewModel> oReturnList = new ArrayList<CountryViewModel>();
			CountryRepository oRepo = new CountryRepository();
			List<Country> oEntities = oRepo.SelectAllRegionsByCountry(sCountryCode);

			for (Country oCountry : oEntities) {
				CountryViewModel oViewModel = new CountryViewModel();
				oViewModel.setid(oCountry.getId());
				oViewModel.setcountryname(oCountry.getName());
				oViewModel.setCountryCode(oCountry.getCountryCode());
				oReturnList.add(oViewModel);
			}

			return oReturnList;

		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();
			return null;
		}

	}


}
