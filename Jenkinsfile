node {
   sh 'env > env.txt'
stage 'Checkout'
   git credentialsId: '73534043-e92f-42d2-b0a3-c954b09ebd49', url: 'https://github.com/markoniemi/survey.git'
   def mvnHome = tool 'Maven 3.3'
stage 'Build'
   env.JAVA_HOME="${tool 'JDK 1.7'}"
   env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
   sh 'java -version'
   sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore clean package -DskipTests=true -P hsqldb"
stage 'Test'
   sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore package -DskipITs=true -P hsqldb"
   step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
stage 'Integration test'
   sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore install -P hsqldb"
   step([$class: 'JUnitResultArchiver', testResults: '**/target/failsafe-reports/TEST-*.xml'])
stage 'Sonar'
   // sonar plugin requires jdk 1.8
   env.JAVA_HOME="${tool 'JDK 1.8'}"
   env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
   sh 'java -version'
   sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore sonar:sonar -DskipTests=true -Dsonar.host.url=${env.SONAR_URL}"
}
