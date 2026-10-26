# Cinema ticket exercise

A Java implementation for cinema ticket service

## Development approach and TDD
Followed strict TDD principles **Red->Green->Refactor** during the exercise
Captured steps in the TDD Milestone Log.

## Business Rules and Validations
**Validations**
* AccountId has to be a positive number - zero, negative and null are rejected
* TicketTypeRest cannot be null, empty or negative number of tickets

**Business Rules**
* Cannot request for more than max number of tickets i.e 25
* Infant tickets cant be requested without Adult tickets
* Child tickets cant be requested without Adult tickets

**Assumptions**
* Each Infant sits on Adult's lap, assuming 1:1, so number of infant cannot
  be more than Adult tickets
* The AppConfig loads the properties from the application.properties file, 
  In this case I am catching any exception while reading the file and 
  outputting it as a Warning and loading default values, as did not want to 
  increase complexity 

## Build and dependency notes
* Added plug in 'maven-surefire-plugin' (3.2.5) for running the tests from
  cmd-line using 'mvn clean test', tests will run in the IDE "IntelliJ"
  without the plugin as well.
* Run all unit and integration tests from cmd-line
```bash
mvn clean verify
```

## Project Structure
```text
src/
├── main/java/
│   └── uk/gov/dwp/uc/pairtest/
│   ├── config/
│   │   └── AppConfig.java                 #Loads properties from application.properties
│   ├── domain/
│   │   └── TicketTypeRequest.java         #Immutable request object
│   ├── exception/
│   │   └── InvalidPurchaseException.java  #Custom exception 
│   ├── validation/
│   │   ├── TicketCalculatorUtil.java      #Helper class for business rules & calculations
│   │   ├── TicketRequestValidatorService.java
│   │   └── TicketRequestValidatorServiceImpl.java #Validates requests against business rules
│   ├── TicketService.java
│   └── TicketServiceImpl.java             #Performs validation,payment, and seat reservation
│   └──resources
│       ├── application.properties         #Properties file for cost and max tickets 
└── test/java/
    └── uk/gov/dwp/uc/pairtest/
    ├── config/
    │   └── AppConfigTest.java
    ├── validation/
    │   ├── TicketCalculatorUtilTest.java
    │   └── TicketRequestValidatorServiceImplTest.java
    ├── TicketServiceImplTest.java
    └── TicketServiceImplIntegrationTest.java
.github/workflows
    └── ci.yml                              #CI file for automated running unit and integration tests    
```

## TDD Milestone Log
1. * (chore): setting up githup, README.md and .gitignore for the code test
2. * (tests): added tests for validating request parameters accountId and 
   TicketTypeRequest (RED)
   * (chore): added 'maven-surefire-plugin' (3.2.5) to pom.xml to run test 
   from cmd-line
3. * (feat): add implementation in TicketServiceImpl for request parameters
   accountId and TicketTypeRequest (GREEN) 
   * override method in InvalidPurchaseException made the TicketTypeRequest  
     immutable
   * added /resources/application.properties file to contain price and max 
     tickets
   * (feat - skeleton): added AppConfig class to read the application.properties
   * (test): Added test cases for AppConfig class that check for values from 
     application.properties file (RED)
4. * (feat) implementation of the AppConfig to load properties (GREEN)
   * (feat - skeleton): there are many validation rules that need to be 
     implemented created a separate TicketRequestValidatorService
   * (tests): added Business rules tests (RED) - 11 tests that cover the 
     business rules
5. * (refactor): Realising validation rules are spread across two classes 
     refactoring the tests so TicketRequestValidatorServiceImplTest contains 
     all validation tests (RED)
   * (feat): adding implementation logic for the tests in 
      TicketRequestValidatorServiceImpl (GREEN)
   * (refactor): need to separate logic for calculations into utility class 
   * (feat-skeleton): created TicketCalculatorUtil as a Utility/Helper class
   * (tests): added tests for Utility class (RED)
   * (feat): implementation of the TicketCalculatorUtility class (GREEN)
   * (refactor): refactored methods in TicketRequestValidatorServiceImplTest 
     (GREEN) and TicketServiceImpl class (GREEN)
6. * (test): Added tests for testing the TicketService (RED)
   * (test): Added integration tests (RED)
   * (doc): cleaning up the README.md file
7. * (refactor): Added the calls to the payment and seat reservation service 
     in TicketServiceImpl (GREEN)
   * All Tests: run: 34, Failures: 0, Errors: 0, Skipped: 0
   * (chore) Added ci.yml file for automatically running unit and integration 
     tests
   * (doc) Updated README.md file 





