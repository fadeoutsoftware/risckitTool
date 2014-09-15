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
			Criteria oCriteria = oSession.createCriteria(User.class);
			oCriteria.add(Restrictions.eq("m_sUserName", sUserName));
			oCriteria.add(Restrictions.eq("m_sPassword", sPassword));
			oUser = (User) oCriteria.uniqueResult();
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
		return oUser;
	}
	
}
