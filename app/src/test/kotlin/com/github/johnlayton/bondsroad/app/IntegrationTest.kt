package com.github.johnlayton.bondsroad.app

import com.github.johnlayton.bondsroad.app.model.InputResponse
import com.github.jenspiegsa.wiremockextension.ConfigureWireMock
import com.github.jenspiegsa.wiremockextension.InjectServer
import com.github.jenspiegsa.wiremockextension.WireMockExtension
import com.github.jenspiegsa.wiremockextension.WireMockSettings
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.givenThat
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.test.web.reactive.server.*

//@SpringBootTest(
//    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
//)
@WebFluxTest
@ExtendWith(
    WireMockExtension::class,
    SpringExtension::class
)
@WireMockSettings(
    failOnUnmatchedRequests = true
)
//@ContextConfiguration(
//    classes = arrayOf(
//      Application::class,
//      InputHandler::class,
//      InputRouter::class
//    )
//)
class IntegrationTest(@Autowired val client: WebTestClient) {

  object Rest {
    val inputRequest = """
{
    "id"   : 1,
    "name" : "john"
}                    
""".trimIndent()

    val inputResponse = """
{
    "id"   : 1,
    "name" : "hello john"
}                    
""".trimIndent()
  }

  object Soap {
    val demoRequest = """
<soap:Envelope 
  xmlns:soap="http://www.w3.org/2003/05/soap-envelope" 
  xmlns:amb="http://johnlayton.github.com/amberleyway">
   <soap:Header/>
   <soap:Body>
      <amb:sayHello>
         <request>
            <id>1</id>
            <name>John</name>
         </request>
      </amb:sayHello>
   </soap:Body>
</soap:Envelope>
""".trimIndent()

    val demoResponse = """
<soap:Envelope 
  xmlns:soap="http://www.w3.org/2003/05/soap-envelope">
   <soap:Body>
      <ns1:sayHelloResponse xmlns:ns1="http://johnlayton.github.com/amberleyway">
         <response xmlns:ns2="http://johnlayton.github.com/amberleyway">
            <id>1</id>
            <name>Hello John!!!</name>
         </response>
      </ns1:sayHelloResponse>
   </soap:Body>
</soap:Envelope>
""".trimIndent()
  }

  @InjectServer
  var serverMock: WireMockServer? = null

  @ConfigureWireMock
  var options = wireMockConfig()
      .port(8092)
      .notifier(ConsoleNotifier(true))
//            .dynamicPort()

//  @LocalServerPort
//  var randomServerPort: Int = 0
//
//  @Autowired
//  private lateinit var inputHandler: InputHandler
//
//  @Autowired
//  private lateinit var applicationContext: ApplicationContext

//  @Autowired
//  private lateinit var webTestClient: WebTestClient

//  @BeforeEach
//  fun setUp() {
//    webTestClient = WebTestClient.bindToApplicationContext(context).build()
//  }


  @Test
  fun testGetInputResponse() {
//    val client = WebClient.builder()
//        .baseUrl("http://localhost:$randomServerPort").build()

//    val client = WebTestClient.bindToApplicationContext(context)
//        .ba
//        .build()

    val response = client.get()
        .uri("/demo")
        .exchange()
        .expectBody()
        .consumeWith({ response ->
          println(response)
        })
//        .value<String::class.java>() {
//
//
//
//        }

//        .flatMap {
//          //                    it.bodyToMono(InputResponse::class.java)
//          it.bodyToMono(String::class.java)Reactive
//        }
//        .map {
//          println(it)
//          it
//        }
//
//    StepVerifier.create(response)
//        .assertNext {
//          it
//
//          println(it)
//
//          assertNotNull(it)
//        }
//        .verifyComplete()
  }


  @Test
  fun testPostInputRequest() {

    givenThat(post("/service/amberleyway").willReturn(
        aResponse().withBody(Soap.demoResponse)
            .withStatus(200)
            .withHeader("Content-Type", "application/soap+xml")
    ));

//    val client = WebClient.builder()
//        .baseUrl("http://localhost:$randomServerPort").build()

    val response = client.post()
        .uri("/demo")
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(
            Rest.inputRequest))
        .exchange()
        .expectStatus().isOk()
        .expectBody<InputResponse>()
        .isEqualTo(InputResponse(1, "Hello john!!!"))
//    (InputResponse::class.java)
//        .value({ employee1 ->
//          employee.getAge(), equalTo(23)
//        })
//        .flatMap {
//          //                    it.bodyToMono(InputResponse::class.java)
//          it.bodyToMono(String::class.java)
//        }
//        .map {
//          println(it)
//          it
//        }
//                .block()

//        StepVerifier.create(inputHandler.sayHello(mock()))
//                .assertNext {
//                    assertNotNull(it)
//                }
//                .verifyComplete()

//    StepVerifier.create(response)
//        .assertNext {
//          it
//
//          println(it)
//
//          assertNotNull(it)
//        }
//        .verifyComplete()

//        assertNotNull(response)
//
//        assertEquals(1, response!!.id)
//        assertEquals("Hello john!!!", response.name)
  }
}

