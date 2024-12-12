pipeline {
    agent any
    environment {
        DOCKER_IMAGE_BACKEND = "medukoju/extracredit-backend:latest"
        DOCKER_IMAGE_FRONTEND = "medukoju/extracredit-frontend:latest"
        DOCKER_CREDENTIALS_ID = 'docker_id'  // Use the correct credential ID
        GIT_REPO = 'https://github.com/edukojumanikumar/Extracredit.git'  // Updated GitHub repo URL
        KUBECONFIG_CREDENTIALS_ID = 'kubeconfig_id' // Kubernetes config credential ID
    }

    stages {
        stage('Clone Git Repository') {
            steps {
                script {
                    // Remove any existing repo and clone the GitHub repository to fetch the Dockerfiles and other resources
                    sh 'rm -rf Extracredit'
                    sh 'git clone ${GIT_REPO}'
                }
            }
        }

        stage('Build Backend Docker Image') {
            steps {
                script {
                    // Navigate into the backend directory and build the Docker image using the Dockerfile
                    sh 'docker build -t ${DOCKER_IMAGE_BACKEND} ./Extracredit/swe'
                }
            }
        }

        stage('Build Frontend Docker Image') {
            steps {
                script {
                    // Navigate into the frontend directory and build the Docker image using the Dockerfile
                    sh 'docker build -t ${DOCKER_IMAGE_FRONTEND} ./Extracredit/vue-frontend'
                }
            }
        }

        stage('Push Backend Docker Image') {
            steps {
                script {
                    // Authenticate with Docker Hub and push the backend image
                    withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                        sh "docker push ${DOCKER_IMAGE_BACKEND}"
                    }
                }
            }
        }

        stage('Push Frontend Docker Image') {
            steps {
                script {
                    // Authenticate with Docker Hub and push the frontend image
                    withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh "docker push ${DOCKER_IMAGE_FRONTEND}"
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Use kubeconfig for Kubernetes authentication
                    withCredentials([file(credentialsId: KUBECONFIG_CREDENTIALS_ID, variable: 'KUBECONFIG')]) {
                        // Apply Kubernetes deployment and service YAML files
                        sh 'kubectl delete -f Extracredit/k8s/backend-deployment.yaml || true'
                        sh 'kubectl apply -f Extracredit/k8s/backend-deployment.yaml'
                        sh 'kubectl delete -f Extracredit/k8s/frontend-deployment.yaml || true'
                        sh 'kubectl apply -f Extracredit/k8s/frontend-deployment.yaml'
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
