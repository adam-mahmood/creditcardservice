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

## Starting the Jenkins + Mail Server Container + CreditcardServer using Docker Compose
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
## Confirming   `CreditCardServicerServer`  is Up:
The follwing commands may generate a 401 status code, which implies you are not authorized to send this request. 
See next section on how to generate JWT tokens such that you can produce authorized requests.
1. curl command to add a credit card <br/>
        `curl -X POST \
      http://localhost:8080/1/d290f1ee-6c54-4b01-90e6-d701748f0851/creditcards \
      -H 'Authorization: Basic cGx1cmFsc2lnaHQ6cGx1cmFsc2lnaHQ=' \
      -H 'Content-Type: application/json' \
      -H 'Postman-Token: 3044ebd2-553e-486c-b527-c9356355a174' \
      -H 'cache-control: no-cache' \
      -d '    {
      "name": "JOHN DIGGLES",
      "creditCardNumber": "1234567812345672",
      "creditLimit": "2500",
      "cvd": "123",
      "expiryMonth": "08",
      "expiryYear": "22",
      "cardNetwork": {
      "name": "VISA"
      }
      }'`
 2. curl command to view a credit card <br/>
    `curl -X GET \
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
      `

## Generate JWT Token
Note I am using Okta developer for standards-compliant OIDC JWT authentication.
Okta is a SaaS (software-as-service) authentication and authorization provider. 
They provide free accounts to developers so they can develop OIDC apps with no fuss.
Head over to developer.okta.com and sign up for an account.I registered, and my Okta Domain is `dev-15015140.okta.com`.
After you’ve verified your email, log in and perform the following steps:

- Go to Application > Add Application.
- Select application type Web and click Next.
- Give the app a name. I named mine “Credit card Auth”.
- Under Login redirect URIs change the value to https://oidcdebugger.com/debug. The rest of the default values will work.
- Click Done.
- Leave the page open of take note of the Client ID and Client Secret.


See resources below for information on securing a spring boot application with Okta, which provides guidance on setting up an oAuth2 authorization server in Okta. 
Now we can use Postman to generate a JWT token and send a request. Open up Postamn, under the request section,navigate to Authorization
,for the "Type" checkbox select OAuth 2.0 and press button "Get New Access Token". This should open up a dialog box.
Set the following fields:
- Grant Type = `Authorization code`
- Callback URL = `https://oidcdebugger.com/debug`
- Auth URL = `https://dev-15015140.okta.com/oauth2/default/v1/authorize`
- Access Token UR = `https://dev-15015140.okta.com/oauth2/default/v1/token`
- Client ID = from above
- Client Secret = from above
- Scope = `openid`
- State = `123`
- Client Authentication = `send client credentials in body`


Now click on the button "Request Token". This should populate the `Access Token` text box with a JWT token.
In postamn can now initiate a GET request at url `http://localhost:8080/creditcards` and should generate a response, like:
```aidl
[
    {
        "name": "JOHN DIGGLES",
        "creditCardNumber": "49927398716",
        "creditLimit": "2500",
        "cvd": "123",
        "expiryMonth": "08",
        "expiryYear": "22",
        "cardNetwork": {
            "name": "VISA"
        }
    },
    {
        "name": "JOHN DIGGLES",
        "creditCardNumber": "1234567812345670",
        "creditLimit": "2500",
        "cvd": "123",
        "expiryMonth": "08",
        "expiryYear": "22",
        "cardNetwork": {
            "name": "VISA"
        }
    },
    {
        "name": "\"JOHN DIGGLES\"",
        "creditCardNumber": "79927398713",
        "creditLimit": "2500",
        "cvd": "123",
        "expiryMonth": "08",
        "expiryYear": "22",
        "cardNetwork": {
            "name": "VISA"
        }
    }
]
```
Alternatively we can  send a GET request to the credit server using the JWT token via curl,as follows:
```
curl -X GET \
  http://localhost:8080/creditcards \
  -H 'Authorization: Bearer eyJraWQiOiJZRk45M3VsYXJ5RzNRSlZWWmVSc0lVNTVNNnJROFE2QnI5SFpYSEt0RXBBIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULnZSNnBXeXRaX3JuVXdmNTlsbTdvVW80VDlac3hIYXIyQ0VEcHR2UWxKVDQiLCJpc3MiOiJodHRwczovL2Rldi0xNTAxNTE0MC5va3RhLmNvbS9vYXV0aDIvZGVmYXVsdCIsImF1ZCI6ImFwaTovL2RlZmF1bHQiLCJpYXQiOjE2MTMyNjU1MTksImV4cCI6MTYxMzI2OTExOSwiY2lkIjoiMG9hNXJkOTJodjZtVmlaZW01ZDYiLCJ1aWQiOiIwMHU1cHF0cnBJOTRBSm9icjVkNiIsInNjcCI6WyJvcGVuaWQiXSwic3ViIjoiYWRkczAwNzIwMDRAaG90bWFpbC5jb20ifQ.BpKx3kKa-BGwSGitWCjlei-ePVUfdB-4bxhoYyoSRgxNUgHjhh8YOIGFqev34he1QrlM3sC5G-Kjw79eEr-dVKeplEcDAo1KZmnQSsQsBDgngPuscHy-R01ZKzDl2I1e_Afgwr77F1EMrY02TZX038qUXrhbsLg2uY1o9mP0Vh1EbKpONjKJN8-yxNorylRjZioP7aVHmkeqJc8RswU6TzD3EKKFCY3PmeqGGrZd9L91kj-BwiarXFfHNJ6W3pW7day1oH9d0vxidcnRX4L8QmGQOlVD1A_iBzE373eRCm1QGNaC7PVXUpL-B0zI0hyRWp8kmV0f5tYC9BJg1D--yA' \
  -H 'Postman-Token: 04406e1c-dc39-4b08-8957-c10e013326d4' \
  -H 'cache-control: no-cache'
```

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
- [Okta Spring Boot JWT Authentication](https://developer.okta.com/blog/2020/11/20/spring-data-jpa)
