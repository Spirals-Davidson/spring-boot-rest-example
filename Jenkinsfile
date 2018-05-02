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
					def testPid = sh (script: "(mvn -DforkCount=0 test > test.csv) & echo $!", returnStdout: true)
					
					/*def powerPid = sh (script: "(((powerapi duration 30 modules procfs-cpu-simple monitor --frequency 50 --console --pids ${testPid}) | grep muid) > data.csv) & echo $!", returnStdout: true)
					
					sh "wait ${testPid}"
					sh "kill -SIGTERM ${powerPid}"
					
                    sh 'cat data.csv'
 
					def csvLine = sh (script: "cat data.csv | tr '\n' ' '", returnStdout: true)
					esQuery.sendPowerapiCSV2ES(csvLine)

					sh "cat test.csv" 

					def csvTest = sh (script: "cat test.csv | grep timestamp= | cut -d ':' -f 4 | tr -d ' '", returnStdout: true) 
					esQuery.sendTestCSV2ES(csvTest)*/
				}
			}					
		}
		/*
		stage('Sonar') {
             agent { label 'master' }
             steps {
				 sh 'mvn sonar:sonar'
			 }
        }
        */
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
		