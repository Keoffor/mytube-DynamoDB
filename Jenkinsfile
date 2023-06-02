pipeline{

        agent any
        tools{
            maven "MAVEN"
            jdk "OracleJDK"
        }
        environment {
            registryCredential = 'ecr:us-east-1:awscreds'
            appRegistry = "882702728333.dkr.ecr.us-east-1.amazonaws.com/mytubeimg-ecr"
            mytubeRegistry = "https://882702728333.dkr.ecr.us-east-1.amazonaws.com"
        }
        stages{
            stage('Fetch code'){
                steps{
                    git branch: 'master', url: 'https://github.com/Keoffor/mytube-DynamoDB.git'
                }
            }
            stage('Build'){
                steps{
                    withCredentials([string(credentialsId: 'aws-access-key', variable: 'AWS_ACCESS_KEY_ID'), string(credentialsId: 'aws-secret-key', variable: 'AWS_SECRET_ACCESS_KEY')]) {
                         sh 'mvn install -DSkipTests'
                    }

                }
               post {
                   success {
                      echo 'Now Archiving it...'
                      archiveArtifacts artifacts: '**/target/*.jar'
                   }
               }
            }
            stage('Sonar Analysis'){
                    environment{
                        sonarScanner = tool 'sonarscanner'
                    }
                steps{
                    withSonarQubeEnv('sonar') {
                        sh '''${sonarScanner}/bin/sonar-scanner -Dsonar.projectKey=mytube \
                           -Dsonar.projectName=mytube \
                           -Dsonar.projectVersion=1.0 \
                           -Dsonar.sources=src/ \
                           -Dsonar.java.binaries=target/classes \
                           -Dsonar.junit.reportsPath=target/surefire-reports/'''
                    }
                }
            }
            stage('Build App Image') {
               steps {
                 script {
                        dockerImage = docker.build( appRegistry + ":$BUILD_NUMBER")
                 }
               }
            }

            stage('Upload App Image') {
                steps{
                   script {
                      docker.withRegistry( mytubeRegistry, registryCredential ) {
                        dockerImage.push("$BUILD_NUMBER")
                        dockerImage.push('latest')
                      }
                   }
                }
            }
        }
   }