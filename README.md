# Spring-boot With Spring-Security and JSON token
Application built using
1. Springboot
2. Spring security
3. Jwt
4. MySql
5. Spring data jpa

This application makes use of spring security with jwt, below are the  steps to generate a jwt token
1. Signup or register a user
2. Login using the newly created user 
3. A json object with access token, refresh token and token type will be returned by authenticating the user credentials
4. With the access token, user can access the resources in a secure way
5. New users only can be created by the admin
6. Before that we need to insert admin credentials into database
7. Login with admin credentials(A json token will be returned)
8. With the token only admin can create new users
