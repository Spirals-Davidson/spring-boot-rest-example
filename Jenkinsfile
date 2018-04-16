pipeline {

    agent none

    stages {
	
		stage('checkout and reset to branch') {
			agent { label 'master' }
			steps {
				sh 'git checkout $BRANCH_NAME'
				sh 'git reset origin/$BRANCH_NAME --hard'
            }
		}
	
		stage('Build') {
			agent { label 'master' }
			steps {
				sh 'mvn clean install -Dmaven.test.skip=true'
            }
		}
	
        stage('Test') {
			agent { label 'powerapi' }
			steps {
				sh 'mvn test & powerapi modules procfs-cpu-simple monitor --frequency 500 --pids \$! --console duration 20'	
				sh 'echo Le pid: \$!'
			}
        }
	
		stage('Sonar') {
			agent { label 'master' }
			steps {
				sh 'mvn sonar:sonar'
			}
        }
		/*
		stage ('Deploy to development environment') {
			stage("front") {
				agent { label 'master' }
				steps {
					deploy("uptoyoo-front")
				}
			}
		}
		*/
		
    }
}