pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-17'
    }

    parameters {
        choice(name: 'BROWSER',  choices: ['chrome', 'firefox', 'edge'], description: 'Target browser')
        choice(name: 'SUITE',    choices: ['regression', 'smoke', 'parallel'], description: 'Maven profile / suite')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run headless')
        string(name: 'TAGS', defaultValue: '@regression', description: 'Cucumber tag filter')
    }

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '15'))
        timeout(time: 60, unit: 'MINUTES')
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Build') {
            steps { sh 'mvn -B clean compile' }
        }

        stage('Test') {
            steps {
                sh """
                   mvn -B test -P ${params.SUITE} \
                       -Dbrowser=${params.BROWSER} \
                       -Dheadless=${params.HEADLESS} \
                       -Dcucumber.filter.tags='${params.TAGS}'
                """
            }
        }
    }

    post {
        always {
            // Allure report
            allure includeProperties: false,
                   jdk: '',
                   results: [[path: 'target/allure-results']]
            // Archive screenshots + logs + cucumber json
            archiveArtifacts artifacts: 'target/screenshots/**, target/logs/**, target/cucumber-reports/**',
                             allowEmptyArchive: true
        }
        failure {
            echo 'Build failed - check the Allure report and archived screenshots.'
        }
    }
}
