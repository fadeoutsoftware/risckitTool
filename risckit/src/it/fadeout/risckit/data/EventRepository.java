package it.fadeout.risckit.data;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.fadeout.risckit.business.Country;
import it.fadeout.risckit.business.Event;

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
			Criteria oCriteria = oSession.createCriteria(Event.class);
			oCriteria.add(Restrictions.eq("m_iUserId", iIdUser));
			oCriteria.addOrder(Order.desc("m_iId"));
			aoList = oCriteria.list();
		}
		catch(Throwable oEx) {
			System.err.println(oEx.toString());
			oEx.printStackTrace();
			
			
			try {
				
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
