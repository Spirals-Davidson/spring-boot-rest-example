node {
	stage('checkout and reset to branch') {
		sh 'git checkout $BRANCH_NAME'
		sh 'git reset origin/$BRANCH_NAME --hard'
	}
}