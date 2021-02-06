pipeline {
    agent any

    // poll every minute
    triggers { pollSCM('* * * * *') }


    stages {
            stage('Checkout') {
                steps {
                    git url: 'https://github.com/adam-mahmood/creditcardservice.git', branch: 'main'
                    echo "$GIT_BRANCH"
                }
            }
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                // git 'https://github.com/adam-mahmood/creditcardservice.git'

                // Run Maven on a Unix agent.
                sh './mvnw clean package'

                // To run Maven on a Windows agent, use
                // bat "./mvnw clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }

            changed {
                emailext subject: "Job \'${JOB_NAME}\' (build ${BUILD_NUMBER}) ${currentBuild.result}",
                    body: "Please go to ${BUILD_URL} and verify the build",
                    attachLog: true,
                    compressLog: true,
                    to: "test@jenkins",
                    recipientProviders: [upstreamDevelopers(), requestor()]
                }
            }
        }
    }
}