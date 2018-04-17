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
	
//      stage('Test') {
//			agent { label 'powerapi' }
//			steps {
//				sh '(mvn test & powerapi modules procfs-cpu-simple monitor --frequency 500 --pids \$! --console duration 40) ' 
//				/* | grep \"muid\" */
//			}
//      }

		stage('Test') {
			agent { label 'powerapi' }
			steps {
				script {
					def output = sh (script: 'mvn test & echo $!',returnStdout: true)
					echo "Le PID des tests ${output}"
					sh 'powerapi modules procfs-cpu-simple monitor --frequency 500 --pids ${output} --console duration 40)'
				}
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