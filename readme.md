# Marlo Spring Boot Account Transaction Demo with Sidecar design pattern

## Development On 

1. Java 18
2. spring boot <u><b><version>3.1.4</version></b></u>
3. spring-cloud-dependencies <u><b><version>2022.0.4</version></b></u>
4. Mysql <b><u>r2dbc-mysql</u></b>
5. flyway-mysql
6. webflux
7. Reactive programming
8. Kafka
9. Docker
   
## Application Flow

1. The user will open account in bank with minimum balance 
2. The user will be able to do (Debit or Credit) transaction
3. Kafka will send or publish the transaction details with current balance on Topic
4. Registered (non-JVM application) will act as sidecar app and will consume the transaction details 
