import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
//import org.eclipse.jgit.transport.URIish;

import java.io.IOException;
//import java.nio.file.Path;
import java.nio.file.Paths;

public class NCH{

    public static int compteur(String repositoryPath, String className) throws IOException, GitAPIException {
        try (Git git = Git.open(Paths.get(repositoryPath).toFile());
             RevWalk revWalk = new RevWalk(git.getRepository())){
        
            Iterable<RevCommit> commits = git.log().all().call();
            int entier = 0;
            for (RevCommit commit : commits) {
                String commitMessage = commit.getFullMessage();
                if (commitMessage.contains(className)) {
                    entier++;
                }
            }

            return entier;
        }
    }

    public static void main(String[] args) {
        String repositoryPath = "/Users/paulkonan/Documents/GitHub/tp1_3913";
    String className = "tropComp jar";

    try
    {
            int commitCount = compteur(repositoryPath, className);
            System.out.println("Number of commits: " + commitCount);
        }catch(IOException|
    GitAPIException e)
    {
        e.printStackTrace();
    }
}}