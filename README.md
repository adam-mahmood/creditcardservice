# creditcardservice

- credit card service uses Maven Wrapper `mvnw(.cmd)` to simplify access to `mvn`
- `creditcardservice`  is located here: <https://github.com/adam-mahmood/creditcardservice.git>

- clone the repo by  ``` git clone https://github.com/adam-mahmood/creditcardservice.git```
- Once the repo is clone, can use ``` docker-compose up ``` to spin up the creditcardserver-server, as wells jenkins and a mailserver
- Note:  The jenkins pipeline is fairly basic and requires more stage's such as Code Quality and Integration Testing
- Once the docker services have started, can submit curl requests  (See README.md located in creditcardservice)
- Also, note that these endpoins are not secure, and work is required to secure these endpoints using spring security. Would have used AWS Cognito or Keycloak to setup an authorization server. 