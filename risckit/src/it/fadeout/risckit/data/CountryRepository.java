package it.fadeout.risckit.data;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import it.fadeout.risckit.business.Country;

public class CountryRepository extends Repository<Country> {

	public List<Country> SelectAllCountries()
	{
		Session oSession = null;

		List<Country> aoList = null;

		try {
			oSession = HibernateUtils.getSessionFactory().openSession();
			Transaction oTx = oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(Country.class);
			oCriteria.add(Restrictions.eq("NutsLevel", "0"));
			aoList = oCriteria.list();
			oTx.commit();	
		}
		catch(Throwable oEx) {
			System.err.println(oEx.toString());
			oEx.printStackTrace();
		}
		finally {
			if (oSession!=null) {
				oSession.flush();
				oSession.clear();
				oSession.close();
			}

		}
		return aoList;
	}
	
	
	public List<Country> SelectAllRegionsByCountry(String sCountryCode)
	{
		Session oSession = null;

		List<Country> aoList = null;

		try {
			oSession = HibernateUtils.getSessionFactory().openSession();
			Transaction oTx = oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(Country.class);
			oCriteria.add(Restrictions.eq("NutsLevel", "2"));
			oCriteria.add(Restrictions.eq("CountryCode", sCountryCode));
			aoList = oCriteria.list();
			oTx.commit();	
		}
		catch(Throwable oEx) {
			System.err.println(oEx.toString());
			oEx.printStackTrace();
		}
		finally {
			if (oSession!=null) {
				oSession.flush();
				oSession.clear();
				oSession.close();
			}

		}
		return aoList;
	}
	
	
}
