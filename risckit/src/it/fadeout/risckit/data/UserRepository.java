package it.fadeout.risckit.data;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import it.fadeout.risckit.business.User;

public class UserRepository extends Repository<User>{


	public User SelectUser(String sUserName, String sPassword)
	{
		Session oSession = null;

		User oUser = null;

		try {
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(User.class);
			oCriteria.add(Restrictions.eq("m_sUserName", sUserName));
			oCriteria.add(Restrictions.eq("m_sPassword", sPassword));
			oUser = (User) oCriteria.uniqueResult();
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
		return oUser;
	}
	public boolean isSavedUserName(String sUserName)
	{
		Session oSession = null;

		User oUser = null;
		boolean bReturn = false;
		
		try {
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(User.class);
			oCriteria.add(Restrictions.eq("m_sUserName", sUserName));
			oUser = (User) oCriteria.uniqueResult();
			oSession.getTransaction().commit();
			
			if(oUser != null)
				bReturn = true;
		}
		catch(Throwable oEx) {
			System.err.println(oEx.toString());
			oEx.printStackTrace();
			bReturn = true;
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
		return bReturn;
	}
	public User SelectUserByEmail( String sEmail)
	{
		Session oSession = null;

		User oUser = null;

		try {
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(User.class);
			//oCriteria.add(Restrictions.eq("m_sUserName", sUserName));
			oCriteria.add(Restrictions.eq("m_sEmail", sEmail));
			oUser = (User) oCriteria.uniqueResult();
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
		return oUser;
	}
	public User SelectUserById( Integer sId)
	{
		Session oSession = null;

		User oUser = null;

		try {
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(User.class);
			//oCriteria.add(Restrictions.eq("m_sUserName", sUserName));
			oCriteria.add(Restrictions.eq("m_iId", sId));
			oUser = (User) oCriteria.uniqueResult();
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
		return oUser;
	}
}
