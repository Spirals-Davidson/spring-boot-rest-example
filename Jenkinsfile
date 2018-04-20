pipeline {

    agent none
	
	environment {
        JAVA_HOME='/usr/lib/jvm/java-1.8.0-openjdk-amd64'
    }

    stages {
		
		stage('checkout and reset to branch') {
			agent { label 'master' }
			steps {
				sh "export JAVA_HOME=${JAVA_HOME}"
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
	
		stage('Test with mvn test and powerapi') {
			agent { label 'powerapi' }
			steps {
				sh '(mvn test & powerapi modules procfs-cpu-simple monitor --frequency 500 --pids \$! --console duration 40) ' 
				/* | grep \"muid\" */
			}
		}

		stage('Test only powerapi') {
			agent { label 'powerapi' }
			steps {
				script {
					def output = sh (script: 'mvn test & echo $!',returnStdout: true)
					sh "powerapi duration 40 modules procfs-cpu-simple monitor --frequency 500 --console --pids ${output}"
					/*
					fonction()
					def toto = "blabla"
					def titi = toto.split("a")
					def tata= new Toroto(titi)
					
					for (def var : titi) 
						println("lzvzr : $var")
						
						
					var powerApitools = new PowerApitools()	
					powerApitools.sendToElastic(pid);	
					*/
				}
			}
		}
		
		//stage('test groovy'){
			//agent { label 'powerapi' }
			//steps {
				//script {
					//def testS = new TestScript()
					//testS.fonction()
				//}
			//}	
		//}	
		
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