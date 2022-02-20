# LeoVegas wallet

Simple wallet microservice. 
It exposes endpoints for reading the account balance, debit transaction, credit transaction and for transactions history.

## Assumptions
1. Players can only use one currency.
2. Neither authorization nor authentication is provided.
3. No player creation API is not provided so player is created when user wants to store money.
4. Simplify process by merging DTO with Entity.

## Tests

To run tests simply run command: `mvn test`

## How to run application
`mvn spring-boot:run` 
* exposes application on http://localhost:8080 
* documentation on http://localhost:8080/swagger-ui.html

## TODO
1. Tests - Of course there could be more unit tests and e2e tests
2. Refactor - maybe with hexagonal architecture. Then Business logic would be inside domain module, 
player and transactions would be two separate adapters, transactions could be stored in different db, 
and controllers would be in rest module for communicating and documenting API.
3. Concurrency - application at this moment is not truly concurrent. 
There could be an issue with account balance lower than 0 
when user provides two or more withdrawals at the same time. 
Probably lock on db per player transaction should be fine
4. Scalability - application is scalable - just run new one on different port and setup load balancer
5. Atomicity - application could be divided into more microservices: the one for creating player, 
one for handling transactions, one for transactions history (which could provide some stats etc.) 
6. Idempotency - there are few idempotent endpoints: 
   1. getAllPlayerTransactions, 
   2. getPlayer, 
   3. getBalance
7. Production - to be production ready we should take care about the points above and set up a build process which 
should check if every dependency is not vulnerable (dependency-check), code is well written (sonarQube), and deploy.
To deploy application on Kubernetes we need to containerize it and provide actuator endpoints.
8. Commands to run and more description and instruction
