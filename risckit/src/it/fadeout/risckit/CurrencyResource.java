package it.fadeout.risckit;

import it.fadeout.risckit.business.Currency;
import it.fadeout.risckit.data.Repository;
import it.fadeout.risckit.viewmodels.CurrencyViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/currencies")
public class CurrencyResource {
	
	@GET
	@Path("/")
	@Produces({"application/json"})
	public ArrayList<CurrencyViewModel> getCurrencies() {

		ArrayList<CurrencyViewModel> oReturnValue = null;
		Repository<Currency> oRepo = new Repository<Currency>();

		List<Currency> oList = oRepo.SelectAll(Currency.class);
		for (Currency oCurrency : oList) {
			if (oReturnValue == null)
				oReturnValue = new ArrayList<CurrencyViewModel>();
			CurrencyViewModel oViewModel = new CurrencyViewModel();
			oViewModel.setId(oCurrency.getId());
			oViewModel.setCurrency(oCurrency.getCurrency());
			oReturnValue.add(oViewModel);
		}
		
		oRepo.CloseSession();
		
		return oReturnValue;
	}
}
