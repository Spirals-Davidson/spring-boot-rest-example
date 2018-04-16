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
	/*
        stage('Test') {
			agent { label 'powerapi' }
			steps {
				sh 'mvn test & && powerapi modules procfs-cpu-simple monitor --frequency 500 --pids \$! --console'	
			}
        }
	*/
		stage('Test with groovy'){
			agent { label 'powerapi' }
			steps {
				thread = Thread.start( { pid = "mvn test".execute() } )
				thread = Thread.start( { while (pid != null) sleep(200); "powerapi modules procfs-cpu-simple monitor --frequency 500 --pids $pid --console".execute() } )
				thread1.run()
				thread2.run()
				thread1.wait()
				thread2.wait()
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