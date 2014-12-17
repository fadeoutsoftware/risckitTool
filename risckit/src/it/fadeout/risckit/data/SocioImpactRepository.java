package it.fadeout.risckit.data;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.fadeout.risckit.business.Currency;
import it.fadeout.risckit.business.SocioImpact;
import it.fadeout.risckit.business.SubCategory;

public class SocioImpactRepository extends Repository<SocioImpact>
{

	public List<SocioImpact> SelectByEvent(Integer iIdEvent)
	{
		Session oSession = null;

		List<SocioImpact> oList = null;

		try {				
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(SocioImpact.class);
			oCriteria.add(Restrictions.eq("IdEvent", iIdEvent));
			oList = oCriteria.list();
			for (SocioImpact socioImpact : oList) {
				socioImpact.setSubCategory((SubCategory)oSession.get(SubCategory.class, socioImpact.getIdSubcategory()));
				if (socioImpact.getIdCurrency() != null)
					socioImpact.setCurrency((Currency)oSession.get(Currency.class, socioImpact.getIdCurrency()));
			}
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

		return oList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SocioImpact Select(int iId, Class<SocioImpact> oClass) {

		Session oSession = null;

		SocioImpact oEntity = null;

		try {				
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			oEntity = (SocioImpact) oSession.get(oClass, iId);
			if (oEntity != null)
			{
				oEntity.setSubCategory((SubCategory)oSession.get(SubCategory.class, oEntity.getIdSubcategory()));
				if (oEntity.getIdCurrency() != null)
					oEntity.setCurrency((Currency)oSession.get(Currency.class, oEntity.getIdCurrency()));
			}
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

	@SuppressWarnings("unchecked")
	public long SelectCount(int iIdEvent) {

		Session oSession = null;

		long iCount = 0;

		try {				
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(SocioImpact.class);
			oCriteria = oCriteria.add(Restrictions.eq("IdEvent", iIdEvent));
			iCount = (long)oCriteria.setProjection(Projections.rowCount()).uniqueResult();
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

		return iCount;
	}


}
