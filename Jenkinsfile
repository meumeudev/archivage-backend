pipeline {
    agent any

    environment {
        NAME = 'archive'
        VERSION = readMavenPom().getVersion()
        NAME_NETWORK = 'cdc_v2_dev_network'
        PORT = 27401
        VOLUME = '/data/archive'
        CONTEXT_PATH = '/archive/'
        EMAIL = 'modou.diop@afrilins.net'
    }

    options {
      timeout(time: 120, unit: 'MINUTES')
    }

    stages {

        stage('Clean Install') {
            steps {
                sh  "/usr/local/maven/bin/mvn clean install"
                stash includes: '**/target/*', name: 'target'
            }

        }

        stage('[DEV] BUILD DOCKER') {
            when { branch 'develop' }
            steps {
                sh  "docker build -t ${NAME}-dev:${VERSION} ."
            }
        }

        stage('[DEV] RUN DOCKER') {
             when { branch 'develop' }
            steps {
                   sh   "docker run --network ${NAME_NETWORK} --name ${NAME}-prod --restart=always -e  -Dspring.profiles.active=prod -Dserver.servlet.context-path=${CONTEXT_PATH} -Dserver.port=8081 -Xms2g -Xmx2g' --memory-reservation=512M --memory=2048M  -p ${PORT}:8081 -d ${NAME}-dev:${VERSION}"
            }
        }

    }

    post {

       changed {
            emailext attachLog: true, body: '$DEFAULT_CONTENT', subject: '$DEFAULT_SUBJECT',  to: '${EMAIL}'
       }
        failure {
            emailext attachLog: true, body: '$DEFAULT_CONTENT', subject: '$DEFAULT_SUBJECT',  to: '${EMAIL}'
        }

    }
}
