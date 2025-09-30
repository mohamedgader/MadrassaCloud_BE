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
                bat '.mvnw clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat '.mvnw test'
            }
        }

        stage('Deploy') {
            steps {
                echo "Déploiement en cours..."
                // Exemple Render (si tu configures RENDER_API_KEY dans Jenkins -> Manage Jenkins > Credentials)
                // bat 'render deploy --service votre-service --api-key %RENDER_API_KEY%'
            }
        }
    }
}
