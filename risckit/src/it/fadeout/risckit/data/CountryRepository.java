package it.fadeout.risckit.data;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.fadeout.risckit.business.Country;

public class CountryRepository extends Repository<Country> {

	public List<Country> SelectAllCountries() throws NamingException
	{
		Session oSession = HibernateUtils.getSessionFactory().openSession();
		List<Country> aoList = null;

		try {			
			oSession.beginTransaction();
			Query oQuery = oSession.createQuery("from Country where NutsLevel = '0' order by Name");
			System.out.println("Begin query");
			aoList = oQuery.list();
			oSession.getTransaction().commit();
		}
		catch(Exception oEx) {
			System.err.println(oEx.toString());
			oEx.printStackTrace();
			oSession.getTransaction().rollback();
			 
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
			oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(Country.class);
			oCriteria.add(Restrictions.eq("NutsLevel", "2"));
			oCriteria.add(Restrictions.eq("CountryCode", sCountryCode));
			oCriteria.addOrder(Order.asc("Name"));
			aoList = oCriteria.list();
			oSession.getTransaction().commit();
		}
		catch(Throwable oEx) {
			System.err.println(oEx.toString());
			oEx.printStackTrace();
			oSession.getTransaction().rollback();
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
	
	public Country SelectCountryByCountryCode(String sCountryCode)
	{
		Session oSession = null;

		Country oCountry = null;

		try {
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(Country.class);
			oCriteria.add(Restrictions.eq("NutsLevel", "0"));
			oCriteria.add(Restrictions.eq("CountryCode", sCountryCode));
			oCountry = (Country) oCriteria.uniqueResult();
			oSession.getTransaction().commit();
		}
		catch(Throwable oEx) {
			System.err.println(oEx.toString());
			oEx.printStackTrace();
			oSession.getTransaction().rollback();
		}
		finally {
			if (oSession!=null) {
				oSession.flush();
				oSession.clear();
				oSession.close();
			}

		}
		return oCountry;
	}

}
