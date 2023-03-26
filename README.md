# spring_boot_auth_assignment

1. Setup MySql database and create "assignment" schema. Configure below fields in "spring_boot_auth_assignment/src/main/resources/application.properties" file :
    - `spring.datasource.url(=jdbc:mysql://localhost:3306/assignment` in my case)
    - `spring.datasource.username(=root` in my case)
    - `spring.datasource.password(=password` in my case)

2. Go to "spring_boot_auth_assignment/target/" folder and run - `java -jar demo-0.0.1-SNAPSHOT.jar`

3. Create "ROLE_USER" and "ROLE_ADMIN" rows in "roles" table. 
<br/>like - 
<br/><img width="369" alt="image" src="https://user-images.githubusercontent.com/128787775/227757929-392af9a5-f18e-49af-b220-c111c39df43d.png">

4. Register user using API: `http://localhost:8080/api/auth/register`
<br/> with payload like this
```
{
    "name": "manoj",
    "email": "manoj@gmail.com",
    "username": "manoj",
    "password": "password"
}
```
<br/> <img width="459" alt="image" src="https://user-images.githubusercontent.com/128787775/227758554-4dbc777d-1d6c-4130-bc6f-0cf2d2f660ce.png">

5. By default role will be "ROLE_USER". To give "ROLE_ADMIN" role to user, change it directly from database.
<br/> like - 
<br/> <img width="440" alt="image" src="https://user-images.githubusercontent.com/128787775/227758714-30d9d7b0-fbe7-4aee-aeb9-474650967d7a.png">

6. Login user using API: `http://localhost:8080/api/auth/login`
<br/> with payload like this
```
{
    "usernameOrEmail": "manoj",
    "password": "password"
}
```
<img width="643" alt="image" src="https://user-images.githubusercontent.com/128787775/227758786-a4d458ae-700f-454b-9b83-0ce80b585d3b.png">

7. We get `accessToken` as response from login API. Pass this accessToken in Authorization Header for subsequent requests.
<br/> e.g. 
<br/> <img width="640" alt="image" src="https://user-images.githubusercontent.com/128787775/227759063-a290afa6-141f-4d7c-9de8-2ea2223d0dec.png">

