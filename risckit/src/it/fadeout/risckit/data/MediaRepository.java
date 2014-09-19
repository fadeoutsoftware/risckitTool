package it.fadeout.risckit.data;

import java.io.IOException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.tmatesoft.svn.core.SVNException;

import it.fadeout.risckit.business.Country;
import it.fadeout.risckit.business.Event;
import it.fadeout.risckit.business.Media;
import it.fadeout.risckit.business.SVNUtils;

public class MediaRepository extends Repository<Media>{

	public List<Media> SelectByEvent(Integer iEventId) {

		Session oSession = null;

		List<Media> oList = null;

		try {				
			oSession = HibernateUtils.getSessionFactory().openSession();
			Criteria oCriteria = oSession.createCriteria(Media.class);
			oCriteria.add(Restrictions.eq("m_iEventId", iEventId));
			oList = oCriteria.list();
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
	
	public void DeleteMediaFile(String sUserName, String sSvnUser, String sSvnPwd,
			String sSvnUserDomain, String sRepoFile, String sSvnRepository, String sStartDate, String sLocation) throws SVNException, IOException
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

}
