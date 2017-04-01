node {
	def mvnHome = tool 'Maven 3.3'
	sh 'env > env.txt'
	stage ('Checkout') {
		git credentialsId: '73534043-e92f-42d2-b0a3-c954b09ebd49', url: 'https://github.com/markoniemi/survey.git'
	}
	stage ('Build') {
		env.JAVA_HOME="${tool 'JDK 1.8'}"
		env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
		sh 'java -version'
		// skip survey-report module in build
		sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore clean package -DskipTests=true -P hsqldb,tomcat"
	}
	stage ('Test') {
		sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore package -DskipITs=true -P hsqldb,tomcat"
		step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
	}
	stage ('Integration test') {
		sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore install -P hsqldb,tomcat -Dwdm.phantomjsDriverVersion=2.1.1"
		step([$class: 'JUnitResultArchiver', testResults: '**/target/failsafe-reports/TEST-*.xml'])
	}
	stage ('Site') {
		sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore -DskipTests=true site"
		publishHTML(target: [
			reportName : 'Maven Site',
			reportDir: 'target/site',
			reportFiles: 'index.html',
			keepAll: true,
			alwaysLinkToLastBuild: true,
			allowMissing: true
		])
	}
	stage ('Sonar') {
        sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore org.sonarsource.scanner.maven:sonar-maven-plugin:3.0.2:sonar -DskipTests=true -Dsonar.host.url=${env.SONAR_URL}"
	}
}
