import git
import os
from collections import Counter

# Chemin vers votre répertoire local de dépôt Git
REPO_PATH = '/Users/ozer/Documents/GitHub/jfreechart'

# Nom du répertoire qui contient les fichiers de test
TEST_DIR_NAME = '/Users/ozer/Documents/GitHub/jfreechart/src/test/java/org/jfree'


def analyze_commits(repo_path, test_dir_name):
    # Charger le dépôt
    repo = git.Repo(repo_path)

    # Compteurs pour les commits de code et de test
    code_commits = 0
    test_commits = 0

    # Parcourir la liste des commits dans la branche principale
    for commit in repo.iter_commits('master'):
        # Dictionnaire pour suivre les fichiers modifiés dans chaque commit
        changes = {
            'code': Counter(),
            'test': Counter(),
        }

        # Vérifier tous les fichiers modifiés dans ce commit
        for diff in commit.diff(commit.parents or [None]):
            file_path = diff.a_path if diff.a_path else diff.b_path

            if file_path:
                # Si le fichier modifié est dans le répertoire de test, il s'agit d'un commit de test
                if file_path.startswith(test_dir_name):
                    changes['test'][file_path] += 1
                else:
                    changes['code'][file_path] += 1

        # Mettre à jour les compteurs de commits en fonction des fichiers modifiés
        if changes['test']:
            test_commits += 1
        if changes['code']:
            code_commits += 1

    return code_commits, test_commits


def main():
    code_commits, test_commits = analyze_commits(REPO_PATH, TEST_DIR_NAME)

    print(f"Commits de code: {code_commits}")
    print(f"Commits de test: {test_commits}")

    # Éviter la division par zéro si test_commits est 0
    ratio = code_commits / test_commits if test_commits else float('inf')

    print(f"Ratio CC/TC: {ratio:.2f}")


if __name__ == "__main__":
    main()
