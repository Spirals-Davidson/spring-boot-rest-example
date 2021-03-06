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
					def debutMVN = sh (script: "date +'%s' | tr -d '\n' ", returnStdout: true)
					List<String> powerapiCSVList = new ArrayList<>()
					List<String> testCSVList	 = new ArrayList<>()
					for(int i=0; i<50; i++){
						sh "mvn test -DforkCount=0 > test.csv &\n"+ 
						   "testPID=\$(echo \$!)\n"+
						   "powerapi duration 30 modules procfs-cpu-simple monitor --frequency 50 --console --pids \$testPID | grep muid > data.csv \n"
						   "powerapiPID=\$(echo \$!)"
						   //"wait \$testPID\n"+
						   //"sleep 0.100\n"+
						   //"kill -9 \$powerapiPID"
							
						String powerapiCSV = sh (script: "cat data.csv | tr '\n' ' '", returnStdout: true)
						powerapiCSVList.add(powerapiCSV)
					
						String testCSV = sh (script: "cat test.csv | grep timestamp= | cut -d '-' -f 2 | tr -d ' '", returnStdout: true)
						testCSVList.add(testCSV)
					}	
					
					def commitName = sh (script: "git describe --always", returnStdout: true)
					def appMethodsXML = sh (script: "cat target/surefire-reports/TEST-* | sed s/'testsuite>'/'testsuite>\\n'/g | grep 'testsuite\\|testcase'", returnStdout: true)
					 
					def esQuery = new ESQuery() 
					esQuery.sendPowerapiciData(Long.parseLong(debutMVN), scm.branches[0].name, "${env.BUILD_NUMBER}", commitName, "${scm.getUserRemoteConfigs()[0].getUrl()}", powerapiCSVList, testCSVList, appMethodsXML)
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
		
