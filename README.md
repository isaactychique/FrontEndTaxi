# FrontEndTaxi

Prototype d'application de réservation de taxi réalisé avec JavaFX. Le projet comprend des parcours passager et conducteur, une interface d'administration, une carte et des échanges avec deux serveurs locaux. Son fonctionnement complet reste à vérifier avant toute utilisation réelle.

## Se repérer

- `src/main/java/org/example/frontendtaxi` : application JavaFX et contrôleurs des écrans. `MainApp` est le point d'entrée principal ; `AdminApp` ouvre l'administration et démarre les serveurs.
- `src/main/resources/org/example/frontendtaxi` : vues FXML et images.
- `src/main/java/Metier` : modèles (passager, conducteur, réservation, paiement) et accès à la base avec JPA/Hibernate.
- `src/main/java/Metier/Serveur` : serveur de mise en relation et serveur de réception des objets.
- `src/main/resources/META-INF/persistence.xml` : configuration de la base MySQL.
- `Passager.txt` et `Conducteur.txt` : objets Java sérialisés utilisés localement, malgré leur extension. Les inscriptions peuvent les écraser : sauvegardez-les avant vos essais si leur contenu vous intéresse.
- `jar_files` et `lib` : bibliothèques présentes dans cette copie ; le build Maven utilise les dépendances déclarées dans `pom.xml`.

## Préparer le lancement

Le projet cible Java 17 et utilise Maven. Installez un JDK 17 et vérifiez `java -version`. Le Maven Wrapper fournit Maven 3.9.11 automatiquement : Maven n'a donc pas besoin d'être installé séparément.

Depuis la racine du projet, avec accès aux dépôts Maven pour télécharger les dépendances :

```bash
./mvnw compile
./mvnw javafx:run
```

Sous Windows, utilisez `mvnw.cmd` à la place de `./mvnw`. La première utilisation nécessite une connexion Internet pour télécharger Maven et les dépendances. La configuration JavaFX Maven démarre `MainApp`.

La compilation a réussi sur cette copie avec le JDK 25 disponible ; le JDK 17 ciblé reste à vérifier. Un lancement temporaire a atteint JavaFX sans erreur de démarrage dans la sortie, mais les écrans et parcours n'ont pas encore été contrôlés visuellement. Des avertissements apparaissent avec le JDK 25 et les dépendances anciennes.

## Base et serveurs

La configuration actuelle vise une base MySQL locale `DonneesTaxi` sur le port 3306, avec l'utilisateur `root` et un mot de passe vide. Adaptez `persistence.xml` à une base de test avant de démarrer les fonctions qui l'utilisent. Le paramètre `hibernate.hbm2ddl.auto=update` peut modifier le schéma : évitez de pointer vers une base contenant des données importantes.

`AdminApp` démarre le serveur de mise en relation sur le port 12345 et le serveur de réception des objets sur le port 12346. Les clients utilisent `localhost` ; ces services et la base sont nécessaires à certains parcours. La connexion administrateur depuis l'écran principal charge l'écran d'administration, mais ne démarre pas ces serveurs elle-même. Les cartes utilisent GMapsFX et leur fonctionnement ainsi que leur configuration Google Maps restent à vérifier.

### Google Maps

Les vues de carte lisent la clé depuis la variable d'environnement `GOOGLE_MAPS_API_KEY` au démarrage. Configurez une clé de votre propre projet Google Cloud, activez Maps JavaScript API et la facturation selon les [instructions Google](https://developers.google.com/maps/documentation/javascript/get-api-key). Avec la clé définie dans votre terminal, lancez `./mvnw javafx:run` depuis ce même terminal (ou ajoutez la variable à la configuration d'exécution de l'IDE). Ne placez pas votre clé dans les fichiers du projet et ne la partagez pas dans le chat.

Sous Linux/macOS, pour saisir la clé sans l'afficher :

```bash
read -rs -p "Google Maps API key: " GOOGLE_MAPS_API_KEY; echo
export GOOGLE_MAPS_API_KEY
./mvnw javafx:run
```

La variable n'est valable que dans ce terminal et les programmes qu'il lance. Dans IntelliJ IDEA, ouvrez **Run > Edit Configurations**, sélectionnez la configuration utilisée pour démarrer l'application, puis ajoutez `GOOGLE_MAPS_API_KEY` dans **Environment variables**. Relancez ensuite l'application. Une variable définie dans un terminal séparé n'est pas automatiquement transmise à un IDE déjà ouvert.

Une carte assombrie avec « for development purposes only » peut signaler une clé absente ou invalide, des restrictions ou un problème de facturation : voir le [guide de diagnostic Google](https://developers.google.com/maps/documentation/javascript/troubleshooting). La sélection d'itinéraire utilise également l'ancien service Directions de Google ; selon le projet Google Cloud, sa disponibilité et son activation doivent être vérifiées séparément. La configuration n'a pas été validée avec une clé réelle. GMapsFX utilise une WebView locale : vérifiez la compatibilité des restrictions de clé avec ce mode de chargement avant de les appliquer.

## État et pistes d'amélioration

Le code est un prototype : les identifiants administrateur sont codés en dur, les serveurs acceptent des objets Java sérialisés et la gestion des erreurs et des ressources est incomplète. Ne l'exposez pas sur un réseau non fiable et n'utilisez pas de données personnelles réelles. Il n'y a pas de suite de tests dans `src/test`.

Pour reprendre le projet progressivement :

1. Obtenir une compilation propre avec Java 17 et Maven, puis vérifier les écrans et les deux serveurs en local.
2. Clarifier le parcours de démarrage et fiabiliser la configuration de la base et des cartes.
3. Sécuriser l'authentification, les échanges et le stockage des données avant tout usage réel.
4. Ajouter des tests sur les parcours de réservation et la persistance, puis améliorer l'interface selon les priorités du projet.
