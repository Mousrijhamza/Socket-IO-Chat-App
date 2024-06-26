# Serveur de Chat en Java

Ce projet implémente un serveur de chat en Java, permettant la communication entre plusieurs clients via un serveur centralisé. Le serveur transfère les messages des clients vers leurs destinataires, facilitant ainsi une communication peer-to-peer.

## Fonctionnalités

- **Connexion des clients** : Les clients peuvent se connecter au serveur et maintenir une liste de connexions actives.
- **Diffusion de messages** : Les messages peuvent être diffusés à tous les clients connectés, à l'exception de l'expéditeur.
- **Communication ciblée** : Les messages peuvent être envoyés à des clients spécifiques en utilisant leur numéro d'identification.
- **Gestion des connexions** : Le serveur garde une trace des connexions des clients et permet de les gérer efficacement.

## Installation

1. Clonez le dépôt :
    ```sh
    git clone https://github.com/votre-utilisateur/serveur-chat-java.git
    ```
2. Naviguez dans le répertoire du projet :
    
3. Compilez les fichiers Java :
   

## Utilisation

1. Démarrez le serveur de chat :
    ```sh
    java Myserver
    ```
2. Connectez des clients au serveur en démarrant plusieurs instances du client windows telnet:
    ```sh
    telnet localhost port
    ```

## Exemple de Fonctionnement

1. Le serveur démarre et écoute les connexions des clients.
2. Les clients se connectent au serveur et peuvent envoyer des messages.
3. Les messages sont diffusés à tous les clients, sauf à l'expéditeur.
4. Les clients peuvent spécifier un destinataire spécifique pour leurs messages.

## Améliorations Futures

- **Interface Graphique** : Créer une interface graphique pour le client en utilisant JavaFX.
- **Authentification** : Ajouter un système d'authentification pour les utilisateurs.
- **Journalisation** : Implémenter la journalisation des messages pour une meilleure traçabilité.

## Contribution

Les contributions sont les bienvenues ! Veuillez soumettre des demandes de tirage (pull requests) pour toute fonctionnalité ou correction de bug.

