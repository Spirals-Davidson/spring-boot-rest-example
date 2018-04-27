@Library('JenkisFile-PowerAPICI') import com.powerapi.ESQuery

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
			agent { label 'powerapi' }
			steps {
				sh 'mvn clean install -Dmaven.test.skip=true'
            }
		}

		stage('Test only powerapi') {
			agent { label 'powerapi' }
			steps {
				script {
					def esQuery = new ESQuery()
					def output = sh (script: '(mvn -DforkCount=0 test > test.csv) & echo $!',returnStdout: true)
					sh "((powerapi duration 30 modules procfs-cpu-simple monitor --frequency 50 --console --pids ${output}) | grep muid) > data.csv"

					def csvLine = sh (script: "cat data.csv | tr '\n' ' '", returnStdout: true)	
					esQuery.sendPowerapiCSV2ES(csvLine)

					sh "cat test.csv"
				
					/*
					def csvTest = sh (script: "cat test.csv | grep timestamp=", returnStdout: true)
                    */
					def csvTest = sh (script: "cat test.csv | grep timestamp | cut -d ':' -f 2", returnStdout: true)
					esQuery.sendTestCSV2ES(csvTest)
				}
			}					
		}
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
		