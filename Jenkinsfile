pipeline {

    agent none
	
	stages {
			
		stage('checkout and reset to branch') {
			agent { label 'powerapi' }
			steps {
				sh 'git checkout $BRANCH_NAME'
				sh 'git reset origin/$BRANCH_NAME --hard'
            }
		}
	
		stage('Build') {
			agent { label 'powerapi' }
			steps {
				sh 'mvn clean install -Dmaven.test.skip=true'
            }
		}
	
		// stage('Test with mvn test and powerapi') {
			// agent { label 'powerapi' }
			// steps {
				// sh '(mvn test & powerapi modules procfs-cpu-simple monitor --frequency 1000 --pids \$! --console duration 40) ' 
				// /* | grep \"muid\" */
			// }
		// }

		stage('Test only powerapi') {
			agent { label 'powerapi' }
			steps {
				script {
					def output = sh (script: 'mvn test & echo $!',returnStdout: true)
					def powerData = sh (script: "powerapi duration 40 modules procfs-cpu-simple monitor --frequency 1000 --console --pids ${output}", returnStdout: true)
					
					sh "echo ${powerData}"
				}
			}					
		}
		//logstash -f fichierconf.conf
		
		
		//stage('test groovy'){
			//agent { label 'powerapi' }
			//steps {
				//script {
					//def testS = new TestScript()
					//testS.fonction()
				//}
			//}	
		//}	
		/*
		// stage('Sonar') {
			// agent { label 'master' }
			// steps {
				// sh 'mvn sonar:sonar'
			// }
        // }
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
		