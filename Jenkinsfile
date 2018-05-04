@Library('JenkisFile-PowerAPICI') import com.powerapi.*

pipeline {

    agent none
	
	options {
		buildDiscarder(logRotator(numToKeepStr: '30', artifactNumToKeepStr: '30'))
	}
	
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

		stage('Test only powerapi') {
			agent { label 'powerapi' }
			steps {
				script {
					def esQuery = new ESQuery()
					def output = sh (script: '(mvn -DforkCount=0 test > test.csv) & echo $!',returnStdout: true)
					sh "((powerapi duration 30 modules procfs-cpu-simple monitor --frequency 50 --console --pids ${output}) | grep muid) > data.csv"

                    sh 'cat data.csv'
					def powerapiCSV = sh (script: "cat data.csv | tr '\n' ' '", returnStdout: true)
					
					sh "cat test.csv"
					def testCSV = sh (script: "cat test.csv | grep timestamp= | cut -d ':' -f 4 | tr -d ' '", returnStdout: true) 
					
					def commitName = sh (script: "git describe --always", returnStdout: true)

					//def appName = sh (script: "cat target/surefire-reports/TEST-*  | grep testsuite | cut -d '\"' -f 2 | head -1", returnStdout: true) 
					
					def appNameXML = sh (script: "cat target/surefire-reports/TEST-* | sed '1,1d'", returnStdout: true)
					
					esQuery.sendPowerapiAndTestCSV(powerapiCSV, testCSV, commitName, appNameXML)
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
		