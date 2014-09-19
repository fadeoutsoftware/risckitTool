package it.fadeout.risckit.data;

import java.io.IOException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.tmatesoft.svn.core.SVNException;

import it.fadeout.risckit.business.Gis;
import it.fadeout.risckit.business.Media;
import it.fadeout.risckit.business.SVNUtils;

public class GisRepository extends Repository<Gis>{
	
	public Gis SelectByEvent(Integer iEventId) {

		Session oSession = null;

		Gis oGis = null;

		try {				
			oSession = HibernateUtils.getSessionFactory().openSession();
			Criteria oCriteria = oSession.createCriteria(Gis.class);
			oCriteria.add(Restrictions.eq("m_iEventId", iEventId));
			oGis = (Gis) oCriteria.uniqueResult();
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

		return oGis;
	}
	
	public void DeleteGisFile(String sUserName, String sSvnUser, String sSvnPwd,
			String sSvnUserDomain, String sRepoFile,String sSvnRepository, String sStartDate, String sLocation) throws SVNException, IOException
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
