# Projet d'Orchestration DevOps

## Aperçu du Projet
Ce dépôt contient une orchestration Docker Compose pour un écosystème d'applications Spring Boot multi-conteneurs. Le projet démontre les principes DevOps avec la conteneurisation, l'orchestration, le proxy inverse, la gestion de base de données et l'analyse continue de la qualité du code.

## Architecture
L'architecture est composée de plusieurs conteneurs Docker interconnectés qui fonctionnent ensemble pour créer un environnement d'application complet :

### Composants Clés :
- **Applications Spring Boot** (v1 et v2) : Deux versions d'une API REST pour la gestion des personnes
- **Base de données MySQL** : Stocke les données de l'application avec initialisation automatique
- **Proxy Inverse Nginx** : Dirige le trafic vers la version appropriée de l'application
- **PHPMyAdmin** : Interface web pour la gestion de la base de données
- **Service de Sauvegarde** : Solution de sauvegarde automatisée de la base de données
- **SonarQube** : Plateforme d'analyse de la qualité du code

## Configuration du Réseau
Tous les services communiquent via un réseau Docker de type bridge nommé `app-network`, qui :
- Fournit une isolation par rapport aux réseaux externes
- Permet aux conteneurs de se résoudre mutuellement par nom de service
- Expose uniquement les ports nécessaires à la machine hôte

## Configuration du Stockage
Les données persistantes sont maintenues grâce aux volumes Docker :
- `mysql_data` : Persiste les fichiers de la base de données MySQL
- `sonarqube_data`, `sonarqube_logs`, `sonarqube_extensions` : Stockent les données SonarQube
- Les fichiers de sauvegarde sont stockés dans un volume monté sur l'hôte pour un accès facile

## Détails des Services

### Application Spring (v1 & v2)
- **Image** : Construite à partir du Dockerfile dans ce dépôt
- **Ports** : Accessibles via Nginx aux chemins `/v1/` et `/v2/`
- **Variables d'Environnement** : Configurées pour la connexion à la base de données, informations de version
- **Dépendances** : Dépend du service mysql-db

### Base de Données MySQL
- **Image** : mysql:5.7
- **Port** : 3306 (accessible dans le réseau des conteneurs)
- **Initialisation** : Charge automatiquement le schéma et les données d'exemple
- **Volumes** : Persiste les données entre les redémarrages du conteneur

### Proxy Inverse Nginx
- **Image** : nginx:latest
- **Port** : 80 (exposé à l'hôte)
- **Configuration** : Dirige les requêtes vers la version appropriée de l'application

### Service de Sauvegarde de la Base de Données
- **Image** : mysql:5.7
- **Fonction** : Crée des dumps SQL périodiques toutes les 12 heures
- **Stockage** : Dumps stockés dans le répertoire ./backups

### PHPMyAdmin
- **Image** : phpmyadmin/phpmyadmin:latest
- **Port** : 8080 (exposé à l'hôte)
- **Configuration** : Connecté à la base de données MySQL

### SonarQube
- **Image** : sonarqube:latest
- **Port** : 9000 (exposé à l'hôte)
- **Fonction** : Fournit une analyse de la qualité du code

## Mise en Route

### Prérequis
- Docker et Docker Compose installés sur votre système
- Git pour cloner le dépôt

### Installation
1. Cloner le dépôt :
```
git clone https://github.com/OsZa99/DevopsOrchestration.git
cd DevopsOrchestration
```

2. Démarrer les services :
```
docker-compose up -d
```

3. Accéder aux applications :
   - Application Spring v1 : http://localhost/v1/
   - Application Spring v2 : http://localhost/v2/
   - PHPMyAdmin : http://localhost:8080
   - SonarQube : http://localhost:9000

### Exécution des Tests
Pour vérifier les points de terminaison de l'API REST :
```
curl http://localhost/v1/api/persons  # Accès aux données de personnes via v1
curl http://localhost/v2/api/persons  # Accès aux données de personnes via v2
curl http://localhost/v1/api/version  # Vérifier les infos de version pour v1
curl http://localhost/v2/api/version  # Vérifier les infos de version pour v2
```

### Opérations Courantes

#### Afficher l'État des Services
```
docker-compose ps
```

#### Vérifier les Logs des Services
```
docker-compose logs [nom_du_service]
```

#### Arrêter les Services
```
docker-compose down
```

#### Reconstruire les Services
```
docker-compose up --build -d
```

## Intégration SonarQube
L'application Spring Boot inclut des capacités d'analyse SonarQube :

1. Connectez-vous à SonarQube (http://localhost:9000) avec :
   - Nom d'utilisateur : admin
   - Mot de passe : Projetdevops2024@

## Gestion de la Base de Données
- Les données initiales sont chargées depuis `database/monannuaire_mysql.sql`
- Les sauvegardes de la base de données sont créées toutes les 12 heures dans le répertoire `backups`
- Accédez à la base de données via PHPMyAdmin à http://localhost:8080

## Implémentation Technique

### Packaging de l'Application
L'application Spring Boot est packagée pendant la construction de l'image Docker :
- Processus de construction Docker multi-étapes
- Compilation Maven dans un conteneur de construction
- Conteneur d'exécution optimisé avec uniquement les dépendances requises

### Sauvegarde de la Base de Données
La sauvegarde automatique de la base de données est implémentée via :
- Un conteneur MySQL dédié
- Des dumps programmés avec horodatage
- Montage de volume pour un stockage persistant

### Évolution de l'Infrastructure
Le projet prend en charge plusieurs versions d'application grâce à :
- Un routage basé sur les chemins Nginx
- Une configuration basée sur les variables d'environnement
- Une base de données partagée pour un accès transparent aux données

## Auteur
- OSSAMA ZARANI
