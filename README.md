# spring_boot_auth_assignment

1. Setup MySql database and create "assignment" schema. Configure below fields in "spring_boot_auth_assignment/src/main/resources/application.properties" file :
    - `spring.datasource.url(=jdbc:mysql://localhost:3306/assignment` in my case)
    - `spring.datasource.username(=root` in my case)
    - `spring.datasource.password(=password` in my case)

2. Create "ROLE_USER" and "ROLE_ADMIN" rows in "roles" table. 
<br/>like - 
<br/><img width="369" alt="image" src="https://user-images.githubusercontent.com/128787775/227757929-392af9a5-f18e-49af-b220-c111c39df43d.png">

3. Go to "spring_boot_auth_assignment/target/" folder and run - `java -jar demo-0.0.1-SNAPSHOT.jar`
