# Cinema ticket exercise

A Java implementation for cinema ticket service

## Development approach and TDD
followed strict TDD principles **Red->Green->Refactor** during the exercise

## Business Rules and Validations

## Build and dependency notes

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


## Build and dependency notes
* Added plug in 'maven-surefire-plugin' (3.2.5) for running the tests from
  cmd-line using 'mvn clean test', tests will run in the IDE "IntelliJ"
  without the plugin as well.

## Project Structure
