import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TotalCBOCalculator {

    // Utiliser un Set pour éviter les doublons, stockant tous les types uniques à travers les fichiers
    private static final Set<String> allExternalTypes = new HashSet<>();

    public static void main(String[] args) {
        // Spécifiez le répertoire racine contenant les fichiers .java
        File rootDir = new File("/Users/ozer/Documents/GitHub/TP2-IFT3913/CBO/src/test/java/jfreechart-master/src/main");
        processDirectory(rootDir);

        // Après avoir parcouru tous les fichiers, affichez le CBO total
        System.out.println("CBO total pour le projet : " + allExternalTypes.size());
    }

    // Méthode pour traiter récursivement les répertoires et les sous-répertoires
    private static void processDirectory(File dir) {
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.isDirectory()) {
                    // Si c'est un sous-répertoire, continuez la récursion
                    processDirectory(child);
                } else if (child.isFile() && child.getName().endsWith(".java")) {
                    // Si c'est un fichier Java, calculez le CBO
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
