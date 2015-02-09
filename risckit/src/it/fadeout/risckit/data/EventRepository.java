package it.fadeout.risckit.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.tmatesoft.svn.core.SVNException;

import it.fadeout.risckit.business.Country;
import it.fadeout.risckit.business.Event;
import it.fadeout.risckit.business.EventsByCountries;
import it.fadeout.risckit.business.Gis;
import it.fadeout.risckit.business.Media;
import it.fadeout.risckit.business.SVNUtils;
import it.fadeout.risckit.business.SocioImpact;

public class EventRepository extends Repository<Event>{

	@Override
	public Event Select(int iId, Class<Event> oClass) {

		Session oSession = null;

		Event oEntity = null;

		try {				
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			oEntity = (Event) oSession.load(oClass, new Integer(iId),LockOptions.UPGRADE);
			oSession.getTransaction().commit();
		}
		catch(Throwable oEx) {
			System.err.println(oEx.toString());
			oEx.printStackTrace();
			try {
				oSession.getTransaction().rollback();
			}
			catch(Throwable oEx2) {
				System.err.println(oEx2.toString());
				oEx2.printStackTrace();					
			}			
		}		
		finally {
			if (oSession!=null) {
				oSession.flush();
				oSession.clear();
				oSession.close();
			}

		}

		return oEntity;
	}


	public List<Event> SelectByUser(int iIdUser) {

		Session oSession = null;

		List<Event> aoList = null;

		try {				
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(Event.class);
			oCriteria.add(Restrictions.eq("m_iUserId", iIdUser));
			oCriteria.addOrder(Order.desc("m_iId"));
			aoList = oCriteria.list();
			oSession.getTransaction().commit();
		}
		catch(Throwable oEx) {
			System.err.println(oEx.toString());
			oEx.printStackTrace();
			try {
				oSession.getTransaction().rollback();
			}
			catch(Exception oEx2) {
				System.err.println(oEx.toString());
				oEx.printStackTrace();				
			}
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

	public List<Event> SelectByCountries(String sCountryCode) {

		Session oSession = null;

		List<Event> aoList = null;

		try {				
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			aoList = oSession.createQuery("select e from Event e join e.m_oCountry c where c.CountryCode = '" + sCountryCode + "'").list();
			oSession.getTransaction().commit();
		}
		catch(Throwable oEx) {
			System.err.println(oEx.toString());
			oEx.printStackTrace();
			try {
				oSession.getTransaction().rollback();
			}
			catch(Exception oEx2) {
				System.err.println(oEx.toString());
				oEx.printStackTrace();				
			}
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


	public List<Event> SelectByRegion(Integer iIdRegion) {

		Session oSession = null;

		List<Event> aoList = null;

		try {				
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			aoList = oSession.createQuery("select e from Event e where e.m_iCountryId = " + iIdRegion + "order by e.m_oStartDate desc").list();
			oSession.getTransaction().commit();
		}
		catch(Throwable oEx) {
			System.err.println(oEx.toString());
			oEx.printStackTrace();
			
			try {
				oSession.getTransaction().rollback();
			}
			catch(Exception oEx2) {
				System.err.println(oEx.toString());
				oEx.printStackTrace();				
			}
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



	public void DeleteEventFile(String sUserName, String sSvnUser, String sSvnPwd, String sSvnUserDomain, String sRepoFile, String sSvnRepository, String sStartDate, String sLocation) throws SVNException, IOException
	{
		//Delete File if present
		SVNUtils oSvnUtils = new SVNUtils();

		if (sRepoFile != null)
		{
			oSvnUtils.Delete(
					sUserName,
					sSvnUser, 
					sSvnPwd, 
					sSvnUserDomain, 
					sRepoFile, 
					sSvnRepository,
					sStartDate,
					sLocation);
		}
	}

	public int Delete(Event oEntity, String sUserName, String sSvnUser,
			String sSvnPwd, 
			String sSvnUserDomain, 
			String sSvnRepository, 
			String sStartDate, 
			String sLocation) {

		boolean bError = false;

		MediaRepository oMediaRepository = new MediaRepository();
		GisRepository oGisRepository = new GisRepository();
		SocioImpactRepository oSocioRepository = new SocioImpactRepository(); 

		Session oSession = null;
		try {				
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();

			//Delete Media
			List<Media> oMediaList = oMediaRepository.SelectByEvent(oEntity.getId());
			try
			{
				for (Media media : oMediaList) {
					try
					{
						oMediaRepository.DeleteMediaFile(sUserName, sSvnUser, sSvnPwd, sSvnUserDomain, media.getFile(), sSvnRepository, sStartDate, sLocation);
					}
					catch(SVNException oEx)
					{
						oEx.printStackTrace();
					}
					oSession.delete(media);
				}
			}
			catch(Exception oEx)
			{
				oEx.printStackTrace();
				bError = true;
				
				try {
					oSession.getTransaction().rollback();
				}
				catch(Throwable oEx2) {
					System.err.println(oEx2.toString());
					oEx2.printStackTrace();					
				}
			}

			if (bError) return -1; 

			//delete gis
			Gis oGis = oGisRepository.SelectByEvent(oEntity.getId());
			
			if (oGis != null)
			{
				try
				{
					if (oGis.getInspireFile() != null){
						//Delete Inspire GIS Metadata
						oGisRepository.DeleteGisFile(sUserName, sSvnUser, sSvnPwd, sSvnUserDomain, oGis.getInspireFile(), sSvnRepository, sStartDate, sLocation);						
					}
					if (oGis.getGisFile() != null) {
						//Delete Gis File
						oGisRepository.DeleteGisFile(sUserName, sSvnUser, sSvnPwd, sSvnUserDomain, oGis.getGisFile(), sSvnRepository, sStartDate, sLocation);
					}
				}
				catch(SVNException oEx)
				{
					oEx.printStackTrace();
				}
				
				try
				{
					oSession.delete(oGis);
				}
				catch(Exception oEx)
				{
					oEx.printStackTrace();
					bError = true;
					
					try {
						oSession.getTransaction().rollback();
					}
					catch(Throwable oEx2) {
						System.err.println(oEx2.toString());
						oEx2.printStackTrace();					
					}
				}
			}
			
			if (bError) return -1;

			//Delete socio impact
			List<SocioImpact> oImpacts = oSocioRepository.SelectByEvent(oEntity.getId());
			if (oImpacts != null)
			{
				try
				{
					for (SocioImpact socioImpact : oImpacts) {
						oSocioRepository.Delete(socioImpact);
					}
				}
				catch(Exception oEx)
				{
					oEx.printStackTrace();
					bError = true;
					
					try {
						oSession.getTransaction().rollback();
					}
					catch(Throwable oEx2) {
						System.err.println(oEx2.toString());
						oEx2.printStackTrace();					
					}
				}
				
			}

			if (bError) return -1;

			oSession.delete(oEntity);
			oSession.getTransaction().commit();
			
			return 0;
		}
		catch(Throwable oEx) {
			System.err.println(oEx.toString());
			oEx.printStackTrace();
			
			try {
				oSession.getTransaction().rollback();
			}
			catch(Throwable oEx2) {
				System.err.println(oEx2.toString());
				oEx2.printStackTrace();					
			}			
		}	
		finally {
			if (oSession!=null) {
				oSession.flush();
				oSession.clear();
				oSession.close();
			}
		}

		return -1;
	}
	
	@SuppressWarnings("unchecked")
	public List<EventsByCountries> Search(Class<EventsByCountries> oClass, Integer iFromYear, Integer iToYear, Boolean bHasImpacts) {
		Session oSession = null;

		List<EventsByCountries> aoList = new ArrayList<EventsByCountries>();

		try {
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			String sQuery = "from " + oClass.getSimpleName();
			if ((iToYear != null && iToYear != 0) || (iFromYear != null && iFromYear != 0) || (bHasImpacts != null && bHasImpacts))
				sQuery += " WHERE ";
			
			boolean bAddAnd = false;
			
			if (iToYear != null && iToYear != 0)
			{
				sQuery += "EXTRACT(YEAR FROM startdate) <= " +  iToYear;
				bAddAnd = true;
			}
			if (iFromYear != null && iFromYear != 0)
			{
				if (bAddAnd)
					sQuery += " AND ";
				else
					bAddAnd = true;
				sQuery += "EXTRACT(YEAR FROM startdate) >= " +  iFromYear;
				
			}
			if (bHasImpacts != null)
			{
				if (bHasImpacts)
				{
					if (bAddAnd)
						sQuery += " AND ";
					sQuery += "impacts > 0";
				}
			}
			
			Query oQuery = oSession.createQuery(sQuery);
			aoList = oQuery.list();
			oSession.getTransaction().commit();			
		}
		catch(Throwable oEx) {
			System.err.println(oEx.toString());
			oEx.printStackTrace();


			try {
				oSession.getTransaction().rollback();
			}
			catch(Throwable oEx2) {
				System.err.println(oEx2.toString());
				oEx2.printStackTrace();					
			}			
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
