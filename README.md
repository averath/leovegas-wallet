# LeoVegas wallet

Simple wallet microservice. 
It exposes endpoints for reading the account balance, debit transaction, credit transaction and for transactions history.

## Assumptions
1. Players can only use one currency.
2. Neither authorization nor authentication are provided.

## Tests

1. Unit `mvn -Dtest=*UnitTest test`
2. Integration `mvn -Dtest=*IntegrationTest test`
4. Every test `mvn test`
