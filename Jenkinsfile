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
				sh 'mvn test > env.txt &'
				sh 'powerapi modules procfs-cpu-simple monitor --frequency 500 --pids $! --console'
				/* Print the mvn test */
				for (String i : readFile('env.txt').split("\r?\n")) {
					println i
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