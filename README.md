### About the project-
#### It is a BDD project, I've implemented it using Cucumber with Rest Assured and have used Maven as build management tool, used cucumber reports for the reporting part
#### The test scripts are placed in src/test/resources/featurefiles and the step definition in src/test/java/org/testapi/stepdefinition
#### To execute cucumber feature file, Runner class named "TestRunner" is used which is placed in folder path "src/test/java/org/testapi/runner"

Clone project on your local machine by using any IDE and once all the dependency issues are resolved. 

Test can be executed by either directly running TestRunner.java class 
Or by passing below command in terminal-
#### mvn clean test -Dtest=TestRunner


Reports can be viewed in prettified html format at "target/cucumber-report.html" or can be viewed by hitting "https://reports.cucumber.io/reports/{xyz}"

For the reference I've attached screenshot below
<img width="905" alt="image" src="https://github.com/heena1002/api-automation-test/assets/167614149/182a6261-5a90-4579-a807-f69f262e25e2">
