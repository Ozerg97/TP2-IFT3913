package ratio;

import java.io.*;
import java.util.*;

public class RatioCodeTest {
    public static void main(String[] args) {
        try {
            String pathCode = "//Users/ozer/Documents/GitHub/TP2-IFT3913/ratioSrcTest/src/test/java/jfreechart-master/src/main";
            String pathTest = "/Users/ozer/Documents/GitHub/TP2-IFT3913/ratioSrcTest/src/test/java/jfreechart-master/src/test/java/org/jfree";
            RatioCodeTest counter = new RatioCodeTest();
            int tailleCode = counter.tlocPourDossier(pathCode);
            int tailleTest = counter.tlocPourDossier(pathTest);
            double ratio = (double) tailleCode /tailleTest;
            System.out.println("Le Tloc du code est de: "+tailleCode);
            System.out.println("Le Tloc du test est de: "+tailleTest);
            System.out.println("Le ratio code/test est de: "+ratio+ ". Donc pour "+ ratio+ " lignes de code, il y a une ligne de test" );


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int tloc(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int tloc = 0;

        try {
            String ligne;
            boolean dansCommentaire = false;

            while ((ligne = reader.readLine()) != null) {
                ligne = ligne.trim();


                if (ligne.startsWith("/*") && !ligne.endsWith("*/") && !ligne.contains("*/")) {
                    dansCommentaire = true;
                    continue;
                }

                if (dansCommentaire) { // si je suis dans un commentaire /* */ verifie si je termine
                    if (ligne.endsWith("*/")) {
                        dansCommentaire = false;
                    }
                    continue;
                }
                if (ligne.contains("/*") && ligne.contains("*/") && !ligne.endsWith("*/")){ // si j'ai un commentaire /* */ mais aussi du code il faut compter la ligne de code
                    tloc++;
                }

                if (!ligne.isEmpty() && !ligne.startsWith("//")) { // Si ma ligne est pas vide et contient un commentaire //
                    tloc++;
                }
            }
        } finally {
            reader.close();
        }
        return tloc;
    }

    public List<File> getAllJavaFiles(File folder) {
        List<File> javaFiles = new ArrayList<>();
        if (folder.isDirectory()) {
            File[] subFolders = folder.listFiles();
            if (subFolders != null) {
                for (File file : subFolders) {
                    if (file.isDirectory()) {
                        // Appel récursif pour gérer les sous-dossiers
                        javaFiles.addAll(getAllJavaFiles(file));
                    } else if (file.getName().toLowerCase().endsWith(".java")) {
                        javaFiles.add(file);
                    }
                }
            }
        }
        return javaFiles;
    }


    public int tlocPourDossier(String projectPath) throws IOException {
        File projectDirectory = new File(projectPath);
        List<File> allJavaFiles = getAllJavaFiles(projectDirectory);

        if (allJavaFiles.isEmpty()) {
            System.out.println("Aucun fichier Java trouvé dans le projet.");
            return 0;
        }

        int totalTloc = 0;
        for (File javaFile : allJavaFiles) {
            int fileTloc = tloc(javaFile.getPath());
            totalTloc += fileTloc;
        }

        return totalTloc;
    }

}
