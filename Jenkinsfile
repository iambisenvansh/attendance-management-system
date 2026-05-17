pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        AWS_REGION = 'eu-north-1'
        ACCOUNT_ID = '235494794622'
        ECR_REPO = 'attendance-app'
        IMAGE_TAG = 'latest'
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

        stage('Login to AWS ECR') {
            steps {

                withCredentials([usernamePassword(
                    credentialsId: 'aws-creds',
                    usernameVariable: 'AWS_ACCESS_KEY_ID',
                    passwordVariable: 'AWS_SECRET_ACCESS_KEY'
                )]) {

                    bat """
                    set AWS_ACCESS_KEY_ID=%AWS_ACCESS_KEY_ID%
                    set AWS_SECRET_ACCESS_KEY=%AWS_SECRET_ACCESS_KEY%
                    set AWS_DEFAULT_REGION=%AWS_REGION%

                    aws ecr get-login-password --region %AWS_REGION% | docker login --username AWS --password-stdin %ACCOUNT_ID%.dkr.ecr.%AWS_REGION%.amazonaws.com
                    """
                }
            }
        }

        stage('Tag Docker Image') {
            steps {
                bat """
                docker tag attendance-app %ACCOUNT_ID%.dkr.ecr.%AWS_REGION%.amazonaws.com/%ECR_REPO%:%IMAGE_TAG%
                """
            }
        }

        stage('Push Docker Image') {
            steps {
                bat """
                docker push %ACCOUNT_ID%.dkr.ecr.%AWS_REGION%.amazonaws.com/%ECR_REPO%:%IMAGE_TAG%
                """
            }
        }

        stage('Stop Old Container') {
            steps {
                bat 'docker stop attendance-container || exit 0'
                bat 'docker rm attendance-container || exit 0'
            }
        }

        stage('Run Docker Container') {
            steps {
                bat """
                docker run -d -p 8081:8081 --name attendance-container attendance-app
                """
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }

        failure {
            echo 'Pipeline failed!'
        }
    }
}
