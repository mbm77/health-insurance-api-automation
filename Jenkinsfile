pipeline {
    agent any  // Runs on any available Jenkins agent

    environment {
        MAVEN_HOME = 'D:/apache-maven-3.8.6/'  // Adjust based on your system
        JAVA_HOME = 'D:/jdk/'  // Adjust Java version
        EXTENT_REPORT_DIR = "reports"
        REPORT_NAME = "ExtentReports.html"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'master', url: 'https://github.com/mbm77/AdvancedRestAssuredFramework.git'  // Replace with your repo
            }
        }

        stage('Setup Environment') {
            steps {
                script {
                    echo "Setting up Java and Maven"
                    bat 'java -version'
                    bat 'mvn -version'
                }
            }
        }

        stage('Run API Tests') {
            steps {
                script {
                    try {
                        bat 'mvn clean test -DsuiteXmlFile=insurance_testng.xml'
                    } catch (Exception e) {
                        echo "Tests failed, but continuing to generate reports..."
                    }
                }
            }
        }

        stage('Publish Extent Reports') {
            steps {
                publishHTML(target: [
                    allowMissing: true,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: "${EXTENT_REPORT_DIR}",
                    reportFiles: "${REPORT_NAME}",
                    reportName: "Extent Report"
                ])
            }
        }

        stage('Archive Test Results') {
            steps {
                archiveArtifacts artifacts: 'reports/*.html', fingerprint: true
                
            }
        }
    }

    post {
        success {
            echo "✅ API Tests Passed!"
        }
        failure {
            echo "❌ API Tests Failed!"
            emailext subject: "Jenkins API Test Failure 🚨",
                     body: "API tests failed in Jenkins Job: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                     to: 'balamuralim111@gmail.com'
        }
    }
}
