package it.fadeout.risckit.business;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNErrorCode;
import org.tmatesoft.svn.core.SVNErrorMessage;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.auth.SVNAuthentication;
import org.tmatesoft.svn.core.auth.SVNPasswordAuthentication;
import org.tmatesoft.svn.core.auth.SVNSSLAuthentication;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.diff.SVNDeltaGenerator;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SVNUtils {

	public void Commit(InputStream oInStream,
			String sLogin,
			String sSvnUser, 
			String sSvnPwd,
			String sSvnDomain,
			String sFilePath, 
			String sSvnRepository,
			String sStartDate,
			String sLocation) throws SVNException, IOException {
		/*
		 * URL that points to repository. 
		 */
		SVNURL url = SVNURL.parseURIEncoded(sSvnRepository);
		/*
		 * Credentials to use for authentication.
		 */
		String sUser = "";
		if (sSvnDomain != null && !sSvnDomain.isEmpty())
			sUser += sSvnDomain + "\\";

		sUser += sSvnUser;

		String userName = sSvnUser;
		String userPassword = sSvnPwd;

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = oInStream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();

		byte[] contents = buffer.toByteArray();

		/*
		 * Create an instance of SVNRepository class. This class is the main entry point 
		 * for all "low-level" Subversion operations supported by Subversion protocol. 
		 * 
		 * These operations includes browsing, update and commit operations. See 
		 * SVNRepository methods javadoc for more details.
		 */
		//SVNRepository repository = SVNRepositoryFactory.create(url);
		SVNRepository repository = DAVRepositoryFactory.create(url);

		/*
		 * User's authentication information (name/password) is provided via  an 
		 * ISVNAuthenticationManager  instance.  SVNWCUtil  creates  a   default 
		 * authentication manager given user's name and password.
		 * 
		 * Default authentication manager first attempts to use provided user name 
		 * and password and then falls back to the credentials stored in the 
		 * default Subversion credentials storage that is located in Subversion 
		 * configuration area. If you'd like to use provided user name and password 
		 * only you may use BasicAuthenticationManager class instead of default 
		 * authentication manager:
		 * 
		 *  authManager = new BasicAuthenticationsManager(userName, userPassword);
		 *  
		 * You may also skip this point - anonymous access will be used. 
		 */
		//ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, userPassword);
		SVNAuthentication auth = new SVNPasswordAuthentication(userName, userPassword, false);
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, userPassword);

		//SVNAuthentication authManager = new SVNPasswordAuthentication(userName,userPassword, false);
		repository.setAuthenticationManager(authManager);

		/*
		 * Get type of the node located at URL we used to create SVNRepository.
		 * 
		 * "" (empty string) is path relative to that URL, 
		 * -1 is value that may be used to specify HEAD (latest) revision.
		 */
		SVNNodeKind nodeKind = repository.checkPath("", -1);

		/*
		 * Checks  up  if the current path really corresponds to a directory. If 
		 * it doesn't, the program exits. SVNNodeKind is that one who says  what
		 * is located at a path in a revision. 
		 */
		if (nodeKind == SVNNodeKind.NONE) {
			SVNErrorMessage err = SVNErrorMessage.create(SVNErrorCode.UNKNOWN, "No entry at URL ''{0}''", url);
			throw new SVNException(err);
		} else if (nodeKind == SVNNodeKind.FILE) {
			SVNErrorMessage err = SVNErrorMessage.create(SVNErrorCode.UNKNOWN, "Entry at URL ''{0}'' is a file while directory was expected", url);
			throw new SVNException(err);
		}

		/*
		 * Get exact value of the latest (HEAD) revision.
		 */
		long latestRevision = repository.getLatestRevision();
		System.out.println("Repository latest revision (before committing): " + latestRevision);

		/*
		 * Gets an editor for committing the changes to  the  repository.  NOTE:
		 * you  must not invoke methods of the SVNRepository until you close the
		 * editor with the ISVNEditor.closeEdit() method.
		 * 
		 * commitMessage will be applied as a log message of the commit.
		 * 
		 * ISVNWorkspaceMediator instance will be used to store temporary files, 
		 * when 'null' is passed, then default system temporary directory will be used to
		 * create temporary files.  
		 */
		ISVNEditor editor = repository.getCommitEditor(sLogin + " - directory and file added", null);

		/*
		 * Add a directory and a file within that directory.
		 * 
		 * SVNCommitInfo object contains basic information on the committed revision, i.e.
		 * revision number, author name, commit date and commit message. 
		 */

		SVNCommitInfo commitInfo = addDir(editor, sLogin, sFilePath, contents, sStartDate, sLocation);

		System.out.println("The directory was added: " + commitInfo);

	}

	/*
	 * This method performs commiting an addition of a  directory  containing  a
	 * file.
	 */
	private SVNCommitInfo addDir(ISVNEditor editor, String sLogin,
			String filePath, byte[] data, String sStartDate,
			String sLocation) throws SVNException {
		/*
		 * Always called first. Opens the current root directory. It  means  all
		 * modifications will be applied to this directory until  a  next  entry
		 * (located inside the root) is opened/added.
		 * 
		 * -1 - revision is HEAD (actually, for a comit  editor  this number  is 
		 * irrelevant)
		 */
		try
		{
			editor.openRoot(-1);
		}
		catch (Exception oEx){

		}
		/*
		 * Adds a new directory (in this  case - to the  root  directory  for 
		 * which the SVNRepository was  created). 
		 * Since this moment all changes will be applied to this new  directory.
		 * 
		 * dirPath is relative to the root directory.
		 * 
		 * copyFromPath (the 2nd parameter) is set to null and  copyFromRevision
		 * (the 3rd) parameter is set to  -1  since  the  directory is not added 
		 * with history (is not copied, in other words).
		 */

		try
		{
			editor.addDir(sLogin, null, -1);
		}
		catch (Exception oEx){

		}


		try
		{
			editor.addDir("risckit", null, -1);
		}
		catch (Exception oEx){

		}

		try
		{
			editor.addDir(sStartDate + "_" + sLocation, null, -1);
		}
		catch (Exception oEx){

		}

		try
		{
			editor.addDir("raw", null, -1);
		}
		catch (Exception oEx){

		}


		/*
		 * Adds a new file to the just added  directory. The  file  path is also 
		 * defined as relative to the root directory.
		 *
		 * copyFromPath (the 2nd parameter) is set to null and  copyFromRevision
		 * (the 3rd parameter) is set to -1 since  the file is  not  added  with 
		 * history.
		 */
		editor.addFile(filePath, null, -1);
		/*
		 * The next steps are directed to applying delta to the  file  (that  is 
		 * the full contents of the file in this case).
		 */
		editor.applyTextDelta(filePath, null);
		/*
		 * Use delta generator utility class to generate and send delta
		 * 
		 * Note that you may use only 'target' data to generate delta when there is no 
		 * access to the 'base' (previous) version of the file. However, using 'base' 
		 * data will result in smaller network overhead.
		 * 
		 * SVNDeltaGenerator will call editor.textDeltaChunk(...) method for each generated 
		 * "diff window" and then editor.textDeltaEnd(...) in the end of delta transmission.  
		 * Number of diff windows depends on the file size. 
		 *  
		 */
		SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
		String checksum = deltaGenerator.sendDelta(filePath, new ByteArrayInputStream(data), editor, true);

		/*
		 * Closes the new added file.
		 */
		editor.closeFile(filePath, checksum);
		/*
		 * Closes the new added directory.
		 */
		editor.closeDir();
		/*
		 * Closes the root directory.
		 */
		editor.closeDir();
		/*
		 * This is the final point in all editor handling. Only now all that new
		 * information previously described with the editor's methods is sent to
		 * the server for committing. As a result the server sends the new
		 * commit information.
		 */
		return editor.closeEdit();
	}


	public void Delete(
			String sLogin,
			String sSvnUser, 
			String sSvnPwd,
			String sSvnDomain,
			String sFileName, 
			String sSvnRepository,
			String sStartDate,
			String sLocation) throws SVNException, IOException {
		/*
		 * URL that points to repository. 
		 */
		SVNURL url = SVNURL.parseURIEncoded(sSvnRepository);
		/*
		 * Credentials to use for authentication.
		 */
		String sUser = "";
		if (sSvnDomain != null && !sSvnDomain.isEmpty())
			sUser += sSvnDomain + "\\";

		sUser += sSvnUser;

		String userName = sSvnUser;
		String userPassword = sSvnPwd;

		/*
		 * Create an instance of SVNRepository class. This class is the main entry point 
		 * for all "low-level" Subversion operations supported by Subversion protocol. 
		 * 
		 * These operations includes browsing, update and commit operations. See 
		 * SVNRepository methods javadoc for more details.
		 */
		//SVNRepository repository = SVNRepositoryFactory.create(url);

		SVNRepository repository = DAVRepositoryFactory.create(url);

		/*
		 * User's authentication information (name/password) is provided via  an 
		 * ISVNAuthenticationManager  instance.  SVNWCUtil  creates  a   default 
		 * authentication manager given user's name and password.
		 * 
		 * Default authentication manager first attempts to use provided user name 
		 * and password and then falls back to the credentials stored in the 
		 * default Subversion credentials storage that is located in Subversion 
		 * configuration area. If you'd like to use provided user name and password 
		 * only you may use BasicAuthenticationManager class instead of default 
		 * authentication manager:
		 * 
		 *  authManager = new BasicAuthenticationsManager(userName, userPassword);
		 *  
		 * You may also skip this point - anonymous access will be used. 
		 */
		//ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, userPassword);
		SVNAuthentication auth = new SVNPasswordAuthentication(userName, userPassword, false);
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, userPassword);

		//SVNAuthentication authManager = new SVNPasswordAuthentication(userName,userPassword, false);
		repository.setAuthenticationManager(authManager);

		/*
		 * Get type of the node located at URL we used to create SVNRepository.
		 * 
		 * "" (empty string) is path relative to that URL, 
		 * -1 is value that may be used to specify HEAD (latest) revision.
		 */
		SVNNodeKind nodeKind = repository.checkPath("", -1);

		/*
		 * Checks  up  if the current path really corresponds to a directory. If 
		 * it doesn't, the program exits. SVNNodeKind is that one who says  what
		 * is located at a path in a revision. 
		 */
		if (nodeKind == SVNNodeKind.NONE) {
			SVNErrorMessage err = SVNErrorMessage.create(SVNErrorCode.UNKNOWN, "No entry at URL ''{0}''", url);
			throw new SVNException(err);
		} else if (nodeKind == SVNNodeKind.FILE) {
			SVNErrorMessage err = SVNErrorMessage.create(SVNErrorCode.UNKNOWN, "Entry at URL ''{0}'' is a file while directory was expected", url);
			throw new SVNException(err);
		}

		/*
		 * Get exact value of the latest (HEAD) revision.
		 */
		long latestRevision = repository.getLatestRevision();
		System.out.println("Repository latest revision (before committing): " + latestRevision);

		/*
		 * Gets an editor for committing the changes to  the  repository.  NOTE:
		 * you  must not invoke methods of the SVNRepository until you close the
		 * editor with the ISVNEditor.closeEdit() method.
		 * 
		 * commitMessage will be applied as a log message of the commit.
		 * 
		 * ISVNWorkspaceMediator instance will be used to store temporary files, 
		 * when 'null' is passed, then default system temporary directory will be used to
		 * create temporary files.  
		 */
		ISVNEditor editor = repository.getCommitEditor(sLogin + " directory and file deleted", null, true, null);

		/*
		 * Add a directory and a file within that directory.
		 * 
		 * SVNCommitInfo object contains basic information on the committed revision, i.e.
		 * revision number, author name, commit date and commit message. 
		 */

		sFileName = sFileName.replaceAll(sSvnRepository, "");
		//controllo per impedire la cancellazione della cartella
		if (!sFileName.startsWith("/trunk")){
			try
			{
				SVNCommitInfo commitInfo = DeleteDir(editor, sFileName, latestRevision);
				System.out.println("File Deleted");
			}
			catch(SVNException oEx)
			{
				oEx.printStackTrace();
				System.err.println("Error on deleting dir");
			}
		}
	}



	/*
	 * This method performs commiting an addition of a  directory  containing  a
	 * file.
	 */
	private SVNCommitInfo DeleteDir(ISVNEditor editor,
			String fileName,
			long latestRevision) throws SVNException {
		/*
		 * Always called first. Opens the current root directory. It  means  all
		 * modifications will be applied to this directory until  a  next  entry
		 * (located inside the root) is opened/added.
		 * 
		 * -1 - revision is HEAD (actually, for a comit  editor  this number  is 
		 * irrelevant)
		 */
		editor.openRoot(latestRevision);
		/*
		 * Adds a new directory (in this  case - to the  root  directory  for 
		 * which the SVNRepository was  created). 
		 * Since this moment all changes will be applied to this new  directory.
		 * 
		 * dirPath is relative to the root directory.
		 * 
		 * copyFromPath (the 2nd parameter) is set to null and  copyFromRevision
		 * (the 3rd) parameter is set to  -1  since  the  directory is not added 
		 * with history (is not copied, in other words).
		 */

		editor.deleteEntry(fileName, -1);
		/*
		 * Closes the root directory.
		 */
		editor.closeDir();
		/*
		 * This is the final point in all editor handling. Only now all that new
		 * information previously described with the editor's methods is sent to
		 * the server for committing. As a result the server sends the new
		 * commit information.
		 */
		return editor.closeEdit();
	}

	public long GetFile(
			String sLogin,
			String sSvnUser, 
			String sSvnPwd,
			String sSvnDomain,
			String sPath, 
			String sSvnRepository,
			OutputStream oOutput) throws SVNException, IOException {
		/*
		 * URL that points to repository. 
		 */
		SVNURL url = SVNURL.parseURIEncoded(sSvnRepository);
		/*
		 * Credentials to use for authentication.
		 */
		String sUser = "";
		if (sSvnDomain != null && !sSvnDomain.isEmpty())
			sUser += sSvnDomain + "\\";

		sUser += sSvnUser;

		String userName = sSvnUser;
		String userPassword = sSvnPwd;

		/*
		 * Create an instance of SVNRepository class. This class is the main entry point 
		 * for all "low-level" Subversion operations supported by Subversion protocol. 
		 * 
		 * These operations includes browsing, update and commit operations. See 
		 * SVNRepository methods javadoc for more details.
		 */
		//SVNRepository repository = SVNRepositoryFactory.create(url);

		SVNRepository repository = DAVRepositoryFactory.create(url);


		//ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, userPassword);
		SVNAuthentication auth = new SVNPasswordAuthentication(userName, userPassword, false);
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, userPassword);

		//SVNAuthentication authManager = new SVNPasswordAuthentication(userName,userPassword, false);
		repository.setAuthenticationManager(authManager);

		/*
		 * Get type of the node located at URL we used to create SVNRepository.
		 * 
		 * "" (empty string) is path relative to that URL, 
		 * -1 is value that may be used to specify HEAD (latest) revision.
		 */
		SVNNodeKind nodeKind = repository.checkPath("", -1);

		sPath = sPath.replaceAll(sSvnRepository, "");

		/*
		 * Checks  up  if the current path really corresponds to a directory. If 
		 * it doesn't, the program exits. SVNNodeKind is that one who says  what
		 * is located at a path in a revision. 
		 */

		if (nodeKind == SVNNodeKind.NONE) {
			SVNErrorMessage err = SVNErrorMessage.create(SVNErrorCode.UNKNOWN, "No entry at URL ''{0}''", url);
			throw new SVNException(err);
		} else if (nodeKind == SVNNodeKind.FILE) {
			SVNErrorMessage err = SVNErrorMessage.create(SVNErrorCode.UNKNOWN, "Entry at URL ''{0}'' is a file while directory was expected", url);
			throw new SVNException(err);
		}

		/*
		 * Get exact value of the latest (HEAD) revision.
		 */
		long latestRevision = repository.getLatestRevision();
		System.out.println("Repository latest revision (before committing): " + latestRevision);


		return repository.getFile(sPath, -1, null, oOutput);

	}


}
