# TP2-IFT3913

Fait par : Ozer Guney (20193803) et Paul Konan(20175655)

Lien GitHub: https://github.com/Ozerg97/TP2-IFT3913

Guide d'utilisation:

Documentation:


    La classe de la metrique 2 (NCH) elle permet d'avoir le nombre de commits dans l’historique
    d’une classe afin de pour la comparer au nombre de commit de sa classe test afin d'avoir une
    idée sur la couverture goblal des classes et de leur tests au fur à mesure de l'avancement du 
    projet.

    Deroulement de la fonction NCH:
        1- le fichier se trouve dans la Keepa_branch.
            Le fichier s'appel Q2_branch.
            /Users/paulkonan/Documents/GitHub/TP2-IFT3913/Q2_branch/TP2_IFT3913/src/main/java/NCH.java
        2- Il faut ouvrir le fichier NCH.java
        3- remplacer écrire le chemin vers le repository et le nom de la classe qu'on cherche à compter
            les commits
        ps: il faut avoir le repository du projet git clone sur le pc (de façon local).
            le code est fait cette maniere car il doit être dans un projet maven car les imports ne 
            marcherais pas.
        4- Après execution, le programme va imprimer le resultat.

    Pour l'execution:

        Pour NCH :
            ex: repositoryPath : "/Users/user1/Documents/GitHub/tp1_3913";
                className :  "tropCom.java"

        Pour metrique Ratio tloc code/tloc test, Cbo et Dc:
          Des fichiers jar ont été creer et placer dans le fichier zip. Il faut seulement executer sur le terminal les fichiers jar sans mettre d'arguments comme ceci: java -jar <jarfilename.jar>. J'ai mis les dossiers necessaires avec les fichiers jar pour faire les   
          tests.
