pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/mohamedgader/MadrassaCloud_BE.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvnw.cmd clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvnw.cmd test'
            }
        }

        stage('Deploy') {
            steps {
                echo "Déploiement en cours..."
                // Exemple : bat 'render deploy --service votre-service --api-key %RENDER_API_KEY%'
            }
        }
    }
}
