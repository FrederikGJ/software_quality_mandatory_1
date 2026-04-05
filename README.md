# Mandatory assignment 1 - software quality 

## Personal Test Data Generator

For krav specs fra Arturo se: [Kravspecifikationer](requirement_specifications.md)

1. start docker container med MySQL database - så den er klar. 

2. kør backend: 
```
mvn spring-boot:run
```

3. frontend - bare åben 
```
index.html 
```
filen der ligger i frontend mappen.

## Unit test af forretningslogik 
kør 
```
cd backend && mvn test
```
## API test 
- Der ligger en postman collection JSON fil som kan loades ind i Postman og køres samlet. 
- Her er der test af vors endpoints. 
