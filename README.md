# Cinema ticket exercise

A Java implementation for cinema ticket service

## Development approach and TDD
followed strict TDD principles **Red->Green->Refactor** during the exercise

## Business Rules and Validations
**Validations**
* AccountId has to be a positive number - zero, negative and null are rejected
* TicketTypeRest cannot be null, empty or negative number of tickets

**Business Rules**
* Cannot request for more than max number of tickets i.e 25
* Infant tickets cant be requested without Adult tickets
* Child tickets cant be requested without Adult tickets
* Each Infant sits on Adult's lap, assuming 1:1, so number of infant cannot
  be more than Adult tickets


## Commit History
1. * (chore): setting up gitup, README.md and .gitignore for the code test
2. * (tests): added tests for validating request parameters accountId and 
   TicketTypeRequest (RED)
   * (chore): added 'maven-surefire-plugin' (3.2.5) to pom.xml to run test 
   from cmd-line
3. * (feat): add implementation in TicketServiceImpl for request parameters
   accountId and TicketTypeRequest (GREEN) 
   * override method in InvalidPurchaseException
   made the TicketTypeRequest immutable
   * added /resources/application.properties file to contain price and max 
     tickets
   * (feat - skeleton): added AppCon class to read the application.properties
   * (test): Added test cases for AppConfig class that check for values from 
     application.properties file
4. * (feat) implementation of the AppConfig to load properties (GREEN)
   * (feat - skeleton): there are many validation rules that need to be 
     implemented created a separate TicketRequestValidatorService
   * (tests): added Business rules tests (RED) - 11 tests that cover the 
     business rules
   


## Build and dependency notes
* Added plug in 'maven-surefire-plugin' (3.2.5) for running the tests from
  cmd-line using 'mvn clean test', tests will run in the IDE "IntelliJ"
  without the plugin as well.

## Project Structure
