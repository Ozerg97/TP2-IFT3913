package cbo;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Cbo {

    private static final Set<String> allExternalTypes = new HashSet<>();
    private static int javaFileCount = 0; // Compteur pour les fichiers Java analysés

    public static void main(String[] args) {
        // Spécifiez le répertoire racine contenant les fichiers .java

        File rootDir = new File("jfreechart-master/src/main/java/org/jfree");
        processDirectory(rootDir);

        // Calculer le CBO moyen en divisant le CBO total par le nombre de fichiers Java
        double averageCBO = (double) allExternalTypes.size() / javaFileCount;
        System.out.println("CBO total pour le projet : " + allExternalTypes.size());
        System.out.println("Nombre de fichiers Java : " + javaFileCount);
        System.out.println("CBO moyen pour le projet : " + averageCBO);
    }

    private static void processDirectory(File dir) {
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.isDirectory()) {
                    // Si c'est un sous-répertoire, continuez la récursion
                    processDirectory(child);
                } else if (child.isFile() && child.getName().endsWith(".java")) {
                    // Si c'est un fichier Java, incrémentez le compteur et calculez le CBO
                    javaFileCount++;
                    try {
                        calculateCBOForFile(child);
                    } catch (IOException e) {
                        System.out.println("Erreur lors de la lecture du fichier: " + child.getName());
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Le dossier n'a pas pu être lu ou il est vide.");
        }
    }

    private static void calculateCBOForFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("import ")) {
                    String importedType = line.substring("import ".length(), line.lastIndexOf(';'));
                    // Ajoutez chaque type externe au Set global (évite les doublons)
                    if (!importedType.startsWith("java.")) { // Exclure les classes java standard
                        allExternalTypes.add(importedType);
                    }
                }
            }
        }
    }
}

