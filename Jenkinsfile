pipeline {
    agent any

    stages {
        stage('Test PR') {
            when {
                changeRequest() // 這個條件會判斷當前的 build 是否為一個 Pull Request
            }
            steps {
                echo 'Pull Request detected, running tests...'
                sh './gradlew test'
            }
        }
    }
}
