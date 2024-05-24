## Test task for Test Automation Engineer.

##Tech stack of the project:
- Java 17
- Gradle
- RestAssured
- JUnit5
- Lombok
- Allure
- Jackson


### As mentioned in the task, here is an instruction on how to run tests and see the report:
- Clone the repository and go to the root directory
- To run the tests use gradle command: **_./gradlew clean test_**
- Wait for tests completion and then run allure command to generate a report: **_allure generate --clean build/allure-results_**
- Report will be generated at **_allure-report_** folder. You can open index.html manually OR use command: **_allure open_**