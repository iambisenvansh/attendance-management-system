pipeline {
    agent any

    stages {

        stage('Build Maven') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t attendance-app .'
            }
        }
    }
}