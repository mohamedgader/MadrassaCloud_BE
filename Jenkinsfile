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
                dir('ITTrack_BE/ITTrack_BE') {
                    bat '.\\mvnw.cmd clean package -DskipTests'
                }
            }
        }
        stage('Test') {
            steps {
                dir('ITTrack_BE/ITTrack_BE') {
                    bat '.\\mvnw.cmd test'
                }
            }
        }
    }
}
