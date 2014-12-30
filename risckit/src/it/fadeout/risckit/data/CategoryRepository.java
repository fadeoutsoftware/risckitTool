package it.fadeout.risckit.data;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.fadeout.risckit.business.Category;

public class CategoryRepository extends Repository<Category>{
	
	@Override
	public List<Category> SelectAll(Class<Category> oClass) {
		Session oSession = null;
		
		List<Category> aoList = new ArrayList<Category>();
		
		try {
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			Query oQuery = oSession.createQuery("from " + oClass.getSimpleName());
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
