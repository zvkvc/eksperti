# eksperti
This is a classic board type website type on which users can ask for professional opinion or give one.

The website is implemented using Spring Boot stack on the backend and Angular 8 on the frontend.
Angular communicates with Spring App via REST endpoints exposed by Spring Controllers which then delegate the request logic to Service layer. 
The data access layer (dao) on the backend is responsible for converting JSON based data from the frontend to entity models on the backend which are then persisted in the local database (implemented using MySQL and Hibernate ORM tool for mapping the entities).
Security (authentication and authorization)  is implemented using JSON Web Token (JWT) which is lightweight and relatively secure Public Key solution for authorizing users and Refresh Token mechanism.
Angular then must provide Jwt token in the security header of each Http request sent to Spring REST endpoint. This is implemented using Angular Interceptors which manage appending Jwt's and Refresh Tokens to the requests. 
These request headers are then filtered by JwtAuthenticationFilter class which can be then used by the Spring Security mechanism to authenticate/authorize users.

Below are some snapshots of the website:

![eksperti_home](https://user-images.githubusercontent.com/71449440/103646402-e5bd3c80-4f59-11eb-970e-a58f3859e2fe.png)

____

![eksperti_prijava](https://user-images.githubusercontent.com/71449440/103646424-efdf3b00-4f59-11eb-94e2-a3d621b087b7.png)

____

![eksperti_registracija](https://user-images.githubusercontent.com/71449440/103646453-fc639380-4f59-11eb-9f52-d354398831f1.png)





