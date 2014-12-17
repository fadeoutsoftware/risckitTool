package it.fadeout.risckit.data;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.imgscalr.Scalr;
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
			oSession.beginTransaction();
			Criteria oCriteria = oSession.createCriteria(Media.class);
			oCriteria.add(Restrictions.eq("m_iEventId", iEventId));
			oList = oCriteria.list();
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

	public String CreateThumb(InputStream uploadedInputStream, String sProjectPath, Media oMedia, String sFileName)
	{
		// Create Temp Dir
		File oTempDir = null;
		oTempDir = new File(sProjectPath);
		if (!oTempDir.exists())
		{
			try
			{
				if (oTempDir.mkdirs())
					oTempDir.setWritable(true, false);
			}
			catch(Exception oEx)
			{
				return "-1";
			}
		}

		String sRelativePath = oMedia.getId().toString() + "/";
		sProjectPath += sRelativePath;
		oTempDir = new File(sProjectPath);
		try
		{
			if (oTempDir.mkdirs())
				oTempDir.setWritable(true, false);
		}
		catch(Exception oEx)
		{
			return "-1";
		}

		try {

			// save it on local
			String uploadedFileLocation = sProjectPath + sFileName;
			String sExtension = FilenameUtils.getExtension(uploadedFileLocation);
			BufferedImage thumbnail =
					Scalr.resize(ImageIO.read(uploadedInputStream), Scalr.Method.QUALITY, 50, 50, Scalr.OP_ANTIALIAS);

			File outputfile = new File(uploadedFileLocation);
			ImageIO.write(thumbnail, sExtension, outputfile);

		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "-1";
		} catch (ImagingOpException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "-1";
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "-1";
		}

		return sRelativePath + sFileName;


	}
}
