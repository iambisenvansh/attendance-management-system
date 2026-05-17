pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

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

        stage('Run Docker Container') {
            steps {
                bat 'docker stop attendance-container || exit 0'
                bat 'docker rm attendance-container || exit 0'
                bat 'docker run -d -p 8081:8081 --name attendance-container attendance-app'
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }
    }
}
