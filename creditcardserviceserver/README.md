# creditcardservice


- Maven requires java as a pre-requisite
- credit card service uses Maven Wrapper `mvnw(.cmd)` to simplify access to `mvn`
- `creditcardservice`  is located here: <https://github.com/adam-mahmood/creditcardservice.git>

```shell
# create a fork of my repo if you want to push changes while following along
# or use mine without the ability to push changes
git clone <https://github.com/adam-mahmood/creditcardservice.git>
cd creditcardservice

# TIP - I cannot emphasize enough, make sure the build works outside of Jenkins (and any other CI tool) before you try to wire it into Jenkins.
# In my younger days I struggled with build tools needlessly because I wouldn't break out of them to find out that the build tools were failing and the failure was obfuscated by my CI/CD tool (Jenkins, TC, etc).
# Use the following to take this example for a spin "manually" outside of Jenkins.

# Windows users, use ./mvnw.cmd
./mvnw --version # ensure maven works via wrapper
# fix any issues before proceeding (resources below for troubleshooting maven wrapper)

./mvnw # no args will give a build failure and prompt you for needed arguments, you can use this to see a failure in Jenkins as you're learning
./mvnw --help

# observe changes before/after, notably the `target` folder
./mvnw compile
./mvnw test
./mvnw package
./mvnw clean

# final command that does everything:
./mvnw clean package

```
## Dockerize Spring Boot Application
- Package creditcardservice-server-0.0.1-SNAPSHOT <br/>
``` ./mvnw package ```<br/>


1. -  ~/workspaceMyWork/workspaceSpringBootMySqlDocker$  <br/>
``` docker build -t spring_boot_credit_service . ``` <br/>

2. - Run Docker and Map the port 8080 to port 8080. <br/>
``` docker run -p 8080:8080 spring_boot_credit_service ```
3. curl command to add a credi carder <br/>
``` curl -X POST \
      http://localhost:8080/1/d290f1ee-6c54-4b01-90e6-d701748f0851/creditcards \
      -H 'Content-Type: application/json' \
      -H 'Postman-Token: a1495484-d0c4-4483-9719-adf9b8190bba' \
      -H 'cache-control: no-cache' \
      -d '    {
            "name": "JOHN DIGGLES",
            "creditCardNumber": "1234567812345670",
            "creditLimit": "2500",
            "cvd": "123",
            "expiryMonth": "08",
            "expiryYear": "22",
            "cardNetwork": {
                "name": "VISA"
            }
        }'

```
4. curl command to view a credi carder <br/>
``` curl -X GET \
  http://localhost:8080/creditcards \
  -H 'Authorization: Basic cGx1cmFsc2lnaHQ6cGx1cmFsc2lnaHQ=' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: ce783264-3e45-4bef-aa9b-f44ded6b84f4' \
  -H 'cache-control: no-cache' \
  -d '    {
        "name": "JOHN DOE",
        "creditCardNumber": "1234567812345670",
        "creditLimit": "2500",
        "cvd": "123",
        "expiryMonth": "08",
        "expiryYear": "22",
        "cardNetwork": {
            "name": "VISA"
        }
    }'

```
## Starting the Jenkins + Mail Server Container using Docker Compose
1. Start the containers
``` docker-compose up -d```

2. Get the initial admin password for Jenkins
``` docker exec my-jenkins cat /var/jenkins_home/secrets/initialAdminPassword ```

3. Confirm the my-jenkins and my-email-server container is running
``` docker ps ```
4. Jenkins Setup:
    - On a browser navigate to ``` http://localhost:9080/ ``` for Jenkins and use password in step 2
    - Select install recommended plugins and set admin credentials
    - Configure Jenkins to connect to Github
        - Once Jenkins is running, Select 'Manage Jenkins' and then 'Configure System'
        - Scroll Down to the Github section and select add Github Server. Select 'Add' drop down box and click on Jenkins.
        - The displayed dialog allows you to set github credentials. Under the 'Kind' dropdown box select 'Secret Text',
         to  allow Jenkins to connect via a secret token to Github
        - Go to your Personal Github account, navigate to Settings | Developer settings to create a Personal access tokens,
         which can be copied and entered into the 'Secret' section in the previous step. 
        - Set an ID and click Add. You should ne navigated to Github Server section.
        - Under Name, click the drop down box and Select the ID set in the previous step.
        - Click Test to test the connectivity
    
## Resources

- [Maven](https://maven.apache.org/)
  - [Build Lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
  - [pom.xml](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html)
  - [Standard Directory Layout](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)
    - *helpful for understanding inputs(`src`) and outputs (`target`) that need to be `linked` into `Jenkins`*
  - [Maven's Core Plugins](https://maven.apache.org/plugins/index.html#supported-by-the-maven-project)
    - Links to source code, for example the [maven-clean-plugin](https://github.com/apache/maven-clean-plugin/tree/master/src/main/java/org/apache/maven/plugins/clean)

- [Maven Wrapper](https://github.com/takari/maven-wrapper)
  - Maven v3.7.0 will include maven wrapper!
  - So long as the wrapper works, I recommend pretending it is the maven `mvn` command itsel