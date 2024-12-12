pipeline {
    agent any
    environment {
        DOCKER_IMAGE_BACKEND = "medukoju/extracredit-backend:latest"
        DOCKER_IMAGE_FRONTEND = "medukoju/extracredit-frontend:latest"
        DOCKER_CREDENTIALS_ID = 'docker_id'  // Use the correct credential ID
        GIT_REPO = 'https://github.com/edukojumanikumar/Extracredit.git'  // Updated GitHub repo URL
        KUBECONFIG_CREDENTIALS_ID = 'kubeconfig_id' // Kubernetes config credential ID
        AWS_CREDENTIALS_ID = 'aws-credentials-id' // AWS credentials ID for the profile
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
                    withCredentials([file(credentialsId: AWS_CREDENTIALS_ID, variable: 'AWS_CREDENTIALS')]) {
                        // Set the AWS profile and update the kubeconfig
                        sh 'export AWS_PROFILE=Amartya'  // Specify the correct AWS profile
                        sh 'aws eks update-kubeconfig --name my-cluster --region us-east-2'  // Update kubeconfig with the profile
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
