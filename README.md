# LeoVegas wallet

Simple wallet microservice. 
It exposes endpoints for reading the account balance, debit transaction, credit transaction and for transactions history.

## Assumptions
1. Players can only use one currency.
2. Neither authorization nor authentication is provided.
3. No player creation API is not provided so player is created when user wants to store money.
4. Simplify process by merging DTO with Entity.

## Tests

1. Unit `mvn -Dtest=*UnitTest test`
2. Integration `mvn -Dtest=*IntegrationTest test`
3. Every test `mvn test`
