package dc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dc {
    public static void main(String[] args) {
        try {
            String pathTest = "jfreechart-master/src/test";
            Dc counter = new Dc();
            double moyenneDc = counter.dcMoyennePourDossier(pathTest);
            System.out.println("La moyenne dc pour le dossier test est de: "+moyenneDc);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public double dc(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int loc = 0;
        int cloc = 0;

        try {
            String ligne;
            boolean dansCommentaire = false;

            while ((ligne = reader.readLine()) != null) {
                ligne = ligne.trim();
                if (!ligne.isEmpty() && !ligne.contains("//") && !ligne.contains("*") && !ligne.contains("/*")) { // Si ma ligne est pas vide et ne contient pas un commentaire //
                    loc++;
                    continue;
                }
                if (ligne.startsWith("*")){
                    cloc++;
                    loc++;
                    continue;
                }
                if (ligne.startsWith("/*") && !ligne.endsWith("*/") && !ligne.contains("*/")) {
                    dansCommentaire = true;
                    cloc++;
                    loc++;
                    continue;
                }

                if (dansCommentaire) { // si je suis dans un commentaire /* */ verifie si je termine
                    if (ligne.endsWith("*/") ) {
                        dansCommentaire = false;
                        loc++;
                        cloc++;
                        continue;
                    }

                }
                if (ligne.contains("/*") && ligne.contains("*/") && !ligne.endsWith("*/")){ // si j'ai un commentaire /* */ mais aussi du code, il faut compter la ligne de code
                    cloc++;
                    loc++;
                }


            }
        } finally {
            reader.close();
        }
        File myfile = new File(filePath);

        double dc = (double) cloc /loc;
        System.out.println("Le DC de "+myfile.getName()+" "+ dc );
        return dc;
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

    public double dcMoyennePourDossier(String projectPath) throws IOException {
        File projectDirectory = new File(projectPath);
        List<File> allJavaFiles = getAllJavaFiles(projectDirectory);
        int sizeFiles = allJavaFiles.size();
        if (allJavaFiles.isEmpty()) {
            System.out.println("Aucun fichier Java trouvé dans le projet.");
            return 0;
        }

        double totalDc = 0;
        for (File javaFile : allJavaFiles) {
            double fileDc = dc(javaFile.getPath());
            totalDc += fileDc;
        }

        return totalDc/sizeFiles;
    }



}
