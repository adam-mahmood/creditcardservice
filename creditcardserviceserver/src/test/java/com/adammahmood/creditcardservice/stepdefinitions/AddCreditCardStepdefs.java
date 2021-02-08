package com.adammahmood.creditcardservice.stepdefinitions;

import com.adammahmood.creditcardservice.model.CardNetwork;
import com.adammahmood.creditcardservice.model.CreditCard;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AddCreditCardStepdefs extends CreditCardServiceApplicationIntegrationTests {


    public RequestSpecification requestSpecification;
    public Response response;
    private String url;
    private int statusCode;

    @Given("a customer from branch with id of {int} and guid of {string}")
    public void aCustomerFromBranchWithIdOfAndGuidOf(int branchId, String guid) {
        this.url =  branchId + "/" +guid ;
        //RestAssured.port = port;
        RestAssured.baseURI = "http://" + HOST + ":" + port;
    }

    @Then("the client receives status code of {int}")
    public void theClientReceivesStatusCodeOf(int arg0) {
        this.response = this.response.then().using().extract().response();
        log.info(response.toString());
        statusCode = response.statusCode();
        assertThat(statusCode, Matchers.is(arg0));

    }

    @And("the client receives message {string}")
    public void theClientReceivesMessage(String message) {
        if (!String.valueOf(statusCode).startsWith("2")){
            response.then().assertThat().body("errors.message",Matchers.contains(message));
        }else{
            response.then().assertThat().body("message",Matchers.containsString(message));
        }

    }


    @When("the client calls the {string}  endpoint to add a credit card")
    public void theClientCallsTheEndpointEndpointToAddACreditCard(String endpoint) {
        this.url = this.url +  "/" + endpoint;
    }


    @And("with request:")
    public void withRequest(DataTable dataTable) {
        final List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        final Map<String, String> data = rows.get(0);

        CreditCard card = new CreditCard()
                .cardNetwork(new CardNetwork().name(data.get("cardNetwork"))).creditCardNumber(data.get("creditCardNumber"))
                .name(data.get("name")).creditLimit(data.get("creditLimit")).cvd(data.get("cvd"))
                .expiryMonth(data.get("expiryMonth")).expiryYear(data.get("expiryYear"));
        log.info("Payload: {}",card);
        this.requestSpecification = given().contentType(ContentType.JSON).body(card);
        System.out.println(this.url);
        this.response = this.requestSpecification.when().post( "/"+this.url);


    }
}
