pipeline {
    agent any
    environment {
        DOCKER_IMAGE_BACKEND = "medukoju/extracredit-backend:latest"
        DOCKER_IMAGE_FRONTEND = "medukoju/extracredit-frontend:latest"
        DOCKER_CREDENTIALS_ID = 'docker_id'  // Use the correct credential ID
        GIT_REPO = 'https://github.com/edukojumanikumar/Extracredit.git'  // Updated GitHub repo URL
        KUBECONFIG_CREDENTIALS_ID = 'kubeconfig_id' // Kubernetes config credential ID
        
        // Hardcoding AWS credentials (for demonstration purposes)
        AWS_ACCESS_KEY_ID = 'AKIA4MTWMBAGEJAEEVO6'  // AWS Access Key
        AWS_SECRET_ACCESS_KEY = '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'  // AWS Secret Key
        AWS_DEFAULT_REGION = 'us-east-2'  // AWS Region
        AWS_PROFILE = 'Amartya' // AWS Profile
    }

    stages {
        stage('Clone Git Repository') {
            steps {
                script {
                    sh 'rm -rf Extracredit'
                    sh 'git clone ${GIT_REPO}'
                }
            }
        }

        stage('Build Backend Docker Image') {
            steps {
                script {
                    // Build the backend Docker image using the correct directory (swe folder)
                    sh 'docker build -t ${DOCKER_IMAGE_BACKEND} ./Extracredit/swe'
                }
            }
        }

        stage('Build Frontend Docker Image') {
            steps {
                script {
                    // Build the frontend Docker image using the correct directory (vue-frontend folder)
                    sh 'docker build -t ${DOCKER_IMAGE_FRONTEND} ./Extracredit/vue-frontend'
                }
            }
        }

        stage('Push Backend Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        // Authenticate and push the backend image to Docker Hub
                        sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                        sh "docker push ${DOCKER_IMAGE_BACKEND}"
                    }
                }
            }
        }

        stage('Push Frontend Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        // Authenticate and push the frontend image to Docker Hub
                        sh "docker push ${DOCKER_IMAGE_FRONTEND}"
                    }
                }
            }
        }

        stage('Configure AWS and Update Kubernetes Context') {
            steps {
                script {
                    // Set AWS credentials as environment variables
                    sh 'export AWS_ACCESS_KEY_ID=AKIA4MTWMBAGEJAEEVO6'
                    sh 'export AWS_SECRET_ACCESS_KEY=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'
                    sh 'export AWS_DEFAULT_REGION=us-east-2'
                    sh 'export AWS_PROFILE=Amartya'  // Set AWS Profile

                    // Update kubeconfig using AWS CLI
                    sh 'aws eks update-kubeconfig --name my-cluster --region us-east-2'  // Update kubeconfig with the profile
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    withCredentials([file(credentialsId: KUBECONFIG_CREDENTIALS_ID, variable: 'KUBECONFIG')]) {
                        // Deploy backend deployment and service
                        sh 'kubectl delete -f Extracredit/back-end-deployment.yml || true'
                        sh 'kubectl apply -f Extracredit/back-end-deployment.yml'
                        sh 'kubectl delete -f Extracredit/backendend-service.yml || true'
                        sh 'kubectl apply -f Extracredit/backendend-service.yml'

                        // Deploy frontend deployment and service
                        sh 'kubectl delete -f Extracredit/front-end-deployment.yml || true'
                        sh 'kubectl apply -f Extracredit/front-end-deployment.yml'
                        sh 'kubectl delete -f Extracredit/frontend-service.yml || true'
                        sh 'kubectl apply -f Extracredit/frontend-service.yml'
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline execution failed. Check the logs for details.'
        }
    }
}
