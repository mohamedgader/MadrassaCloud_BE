pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/mohamedgader/MadrassaCloud_BE.git'
            }
        }
        stage('Build') {
            steps {
                sh '.mvnw clean package'  ou la commande de build adaptée à votre projet
            }
        }
        stage('Test') {
            steps {
                sh '.mvnw test'  adapter selon votre outil de test
            }
        }
        stage('Deploy') {
            steps {
                 Exemple de déploiement sur Render avec leur CLI ou via commande curlAPI
                sh 'render deploy --service votre-service --api-key $RENDER_API_KEY'
            }
        }
    }
}