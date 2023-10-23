import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CBOCalculator {

    public static void main(String[] args) {
        // Spécifiez le répertoire racine contenant les fichiers .java
        File rootDir = new File("/Users/ozer/Documents/GitHub/TP2-IFT3913/CBO/src/test/java/jfreechart-master/src/main");
        processDirectory(rootDir);
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

    private static int calculateCBOForFile(File file) throws IOException {
        Set<String> externalTypes = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Cet exemple simplifié suppose que le couplage peut être déterminé par 'import'
                // Ceci est une simplification extrême, le couplage réel est beaucoup plus complexe
                if (line.startsWith("import ")) {
                    String importedType = line.substring("import ".length(), line.lastIndexOf(';'));
                    if (!importedType.startsWith("java.")) { // Filtrer les classes java standard, ajustez selon les besoins
                        externalTypes.add(importedType);
                    }
                }
            }
        }

        // CBO est supposé être le nombre de types uniques importés dans cet exemple simplifié
        System.out.println("CBO for " + file.getName() + " : " + externalTypes.size());
        return externalTypes.size();
    }

}
