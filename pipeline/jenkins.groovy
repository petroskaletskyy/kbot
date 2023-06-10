pipeline {
    agent any
    environment{
        REPOSITORY = "https://github.com/petroskaletskyy/kbot"
        BRANCH = 'main'
        GITHUB_TOKEN = credentials('github')
        APP='kbot'
    }
    parameters {
      choice choices: ['linux', 'darwin', 'windows', 'all'], description: 'Pick OS', name: 'OS'
      choice choices: ['amd64', 'arm64'], description: 'Pick ARCH', name: 'ARCH'
    }

    stages {
        stage('Checkout SCM') {
            steps {
                echo 'Checkout'
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[url: 'https://github.com/petroskaletskyy/kbot.git']]])
            }
        }
        stage('Test') {
            steps{
                echo "Start testing"
                echo "--------------------"
                
                sh 'make test'
                
                echo "===================="
                echo "Finish testing"
            }
        }
        stage('Build') {
            steps{
                echo "Start build"
                echo "--------------------"
                
                sh "make build TARGETOS=${params.OS} TARGETARCH=${params.ARCH}"
                
                echo "===================="
                echo "Finish build"
            }
        }
        stage('Image') {
            steps{
                echo "Start creating image"
                echo "--------------------"
                
                sh "make image APP=$APP TARGETOS=${params.OS} TARGETARCH=${params.ARCH}"
                
                echo "===================="
                echo "Finish creating image"
            }
        }
        stage('Login to CHCR'){
            steps{
                echo "Login to ghcr.io"
                echo "-------------------"
                sh 'echo $GITHUB_TOKEN_PSW | docker login ghcr.io -u $GITHUB_TOKEN_USR --password-stdin'
                echo "====================="
            }
        }
        stage('Push') {
            steps{
                echo "Start pushing image"
                echo "--------------------"
                
                sh "make push APP=$APP TARGETOS=${params.OS} TARGETARCH=${params.ARCH}"
                
                echo "===================="
                echo "Finish pushing image"
            }
        }
    }
    post{
        always{
            sh 'docker logout'
        }
    }
}