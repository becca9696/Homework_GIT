package Log;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.revwalk.RevCommit;

public class Labo {
	
	private static Logger logger;
    private static final String PATH = "GitLabRep";
    private static final String URI = "https://github.com/becca9696/Homework_GIT.git";

	public static void main(String[] args) throws InvalidRemoteException, TransportException, GitAPIException, IOException {

		
		try {
			
			//Clone Git Repository
			Git gitRepo = Git.cloneRepository()
						 		.setURI(URI)
						 		.setDirectory(new File(PATH))
						 		.call(); 
	
			//Create a Logger
			logger =  Logger.getLogger(Log.Labo.class.getName());
			
			//Get all commit
			Iterable<RevCommit> commits = gitRepo.log().all().call();
			
			//For all Commit..
			for (RevCommit commit : commits) {
				
				//Check if commits contains "added"
				if (commit.getFullMessage().contains("added")) {
					
					//Print a log message
					logger.log(Level.INFO, "commitID=" + commit.getId() + " " + commit.getFullMessage() + " " + commit.getAuthorIdent().getWhen());
					
				}
				
			}
			
			//Close Git Repository
			gitRepo.close();
			
		}
			
		catch (GitAPIException e) {
			e.printStackTrace();
		}			
		
		try {
			//Delete Local Git Repository
			FileUtils.deleteDirectory(new File(PATH));
		}
		
		catch(IOException ex) {
			ex.printStackTrace();
		}
		
		System.exit(0);
		
	} 

}
