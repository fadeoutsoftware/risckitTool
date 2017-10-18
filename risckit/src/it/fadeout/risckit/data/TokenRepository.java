package it.fadeout.risckit.data;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.fadeout.risckit.business.Country;
import it.fadeout.risckit.business.Token;
import it.fadeout.risckit.business.User;

public class TokenRepository  extends Repository<Token>{
	
	public List<Token> SelectTokensUser(Integer sUserId)
	{
		Session oSession = null;

		List<Token> aoList = null;

		try {
			
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(Country.class);
			//oCriteria.add(Restrictions.eq("NutsLevel", "2"));
			oCriteria.add(Restrictions.eq("m_iIdUser", sUserId));
			//oCriteria.addOrder(Order.asc("Name"));
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
	
	public Token SelectToken( Integer sId)
	{
		Session oSession = null;

		Token oToken = null;

		try {
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(Token.class);
			oCriteria.add(Restrictions.eq("m_iId", sId));

			oToken = (Token) oCriteria.uniqueResult();
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
		return oToken;
	}
	public Token SelectTokenByUser( Integer sIdUser)
	{
		Session oSession = null;

		Token oToken = null;

		try {
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(Token.class);
			oCriteria.add(Restrictions.eq("m_iIdUser", sIdUser));

			oToken = (Token) oCriteria.uniqueResult();
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
		return oToken;
	}
	
	public Token SelectTokenByToken( String sToken)
	{
		Session oSession = null;

		Token oToken = null;

		try {
			oSession = HibernateUtils.getSessionFactory().openSession();
			oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(Token.class);
			oCriteria.add(Restrictions.eq("m_iTokenr", sToken));

			oToken = (Token) oCriteria.uniqueResult();
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
		return oToken;
	}
}
