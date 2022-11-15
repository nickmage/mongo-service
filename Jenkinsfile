pipeline {
    agent any
    parameters {
        booleanParam (
            defaultValue: false,
            description: 'Apply debug mode',
            name: 'GRADLE_BUILD_DEBUG_MODE')
    }

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
        stage('Build with debug mode') {
            when {
                expression {
                    params.GRADLE_BUILD_DEBUG_MODE
                }
            }
            steps {
                echo 'gradle clean build --debug'
            }
        }
        stage('Build without debug mode') {
            when {
                expression {
                    !params.GRADLE_BUILD_DEBUG_MODE
                }
            }
            steps {
                 echo 'gradle clean build'
            }
        }
    }
}