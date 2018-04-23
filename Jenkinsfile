node {
	stage('checkout and reset to branch') {
		sh 'git checkout $BRANCH_NAME'
		sh 'git reset origin/$BRANCH_NAME --hard'
	}
	
	stage('Build') {
		agent { label 'powerapi'}
		sh 'mvn clean install -Dmaven.test.skip=true'
	}
	
	stage('Test only powerapi') {
		def output = sh (script: 'mvn test & echo $!',returnStdout: true)
		sh "powerapi duration 40 modules procfs-cpu-simple monitor --frequency 1000 --console --pids ${output}"
	}
}