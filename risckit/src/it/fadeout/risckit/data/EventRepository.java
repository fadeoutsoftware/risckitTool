package it.fadeout.risckit.data;

import org.hibernate.LockOptions;
import org.hibernate.Session;

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
	
}
