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

        AWS_ACCESS_KEY_ID = 'AKIATNVEVMV7EU5AB34V'
        AWS_SECRET_ACCESS_KEY = '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'
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

        stage('Configure AWS Credentials') {
            steps {
                bat '''
                aws configure set aws_access_key_id %AWS_ACCESS_KEY_ID%
                aws configure set aws_secret_access_key %AWS_SECRET_ACCESS_KEY%
                aws configure set default.region %AWS_REGION%
                '''
            }
        }

        stage('Login to AWS ECR') {
            steps {
                bat '''
                aws ecr get-login-password --region %AWS_REGION% | docker login --username AWS --password-stdin %ACCOUNT_ID%.dkr.ecr.%AWS_REGION%.amazonaws.com
                '''
            }
        }

        stage('Tag Docker Image') {
            steps {
                bat '''
                docker tag attendance-app %ACCOUNT_ID%.dkr.ecr.%AWS_REGION%.amazonaws.com/%ECR_REPO%:%IMAGE_TAG%
                '''
            }
        }

        stage('Push Docker Image') {
            steps {
                bat '''
                docker push %ACCOUNT_ID%.dkr.ecr.%AWS_REGION%.amazonaws.com/%ECR_REPO%:%IMAGE_TAG%
                '''
            }
        }
    }
}
