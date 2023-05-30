pipeline{
    environment{
        awsAccess = 'aws-access-key'
        awsSecret ='aws-secret-key'
        awsRegion = 'aws-region'
    }
    agent any
    tools{
        maven "MAVEN"
        jdk "OracleJDK"
    }
    stages{
        stage('Fetch code'){
            steps{
                git branch: 'master', url: 'https://github.com/Keoffor/mytube-DynamoDB.git'
            }
        }
        stage('Build'){
            steps{
               sh 'mvn install -DSkipTests'
            }
           post {
               success {
                  echo 'Now Archiving it...'
                  archiveArtifacts artifacts: '**/target/*.jar'
               }
            }
        }
    }
}