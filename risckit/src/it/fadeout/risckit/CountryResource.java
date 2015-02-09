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
			System.out.println("Query Executed");
			if (oEntities != null)
			{
				for (Country oCountry : oEntities) {
					CountryViewModel oViewModel = new CountryViewModel();
					oViewModel.setid(oCountry.getId());
					oViewModel.setcountryname(oCountry.getName());
					oViewModel.setCountryCode(oCountry.getCountryCode());
					oViewModel.setLat(oCountry.getLat());
					oViewModel.setLon(oCountry.getLon());
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
			if (sCountryCode != null && !sCountryCode.equals(""))
			{

				CountryRepository oRepo = new CountryRepository();
				List<Country> oEntities = oRepo.SelectAllRegionsByCountry(sCountryCode);
				
				if (oEntities != null) {
					for (Country oCountry : oEntities) {
						CountryViewModel oViewModel = new CountryViewModel();
						oViewModel.setid(oCountry.getId());
						oViewModel.setcountryname(oCountry.getName());
						oViewModel.setCountryCode(oCountry.getCountryCode());
						oViewModel.setLat(oCountry.getLat());
						oViewModel.setLon(oCountry.getLon());
						oReturnList.add(oViewModel);
					}					
				}
				else {
					// TODO: METTERCI UN DEFAULT IN QUESTO CASO
					
					CountryViewModel oViewModel = new CountryViewModel();
					//oViewModel.setid(1);
					//oViewModel.setcountryname(oCountry.getName());
					oViewModel.setCountryCode("Default");
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


}
