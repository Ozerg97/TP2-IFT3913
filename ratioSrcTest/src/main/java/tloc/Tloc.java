package tloc;

import java.io.*;
import java.util.*;

public class Tloc {

    // Votre méthode existante pour calculer le TLOC d'un fichier unique
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

    // Méthode pour répertorier récursivement tous les fichiers d'un dossier et de ses sous-dossiers
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

    // Méthode pour calculer le TLOC de tous les fichiers Java dans un projet (dossier et sous-dossiers)

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
    public static void main(String[] args) {
        // Assurez-vous de gérer les exceptions appropriées ici
        try {
            // Remplacez par votre chemin de projet réel
            String pathSrc = "";
            String pathTest = "";
            Tloc counter = new Tloc();
            int tailleSrc = counter.tlocPourDossier(pathSrc);
            int tailleTest = counter.tlocPourDossier(pathTest);
            double ratio = (double) tailleSrc /tailleTest;
            System.out.println(ratio);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
