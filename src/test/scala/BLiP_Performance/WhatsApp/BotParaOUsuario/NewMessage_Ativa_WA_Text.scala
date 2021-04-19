package BLiP_Performance.WhatsApp.BotParaOUsuario

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.util.Random

class NewMessage_Ativa_WA_Text extends Simulation{

  val httpConf = http.baseUrl(url = "https://hmg-http.msging.net/")
    .header(name= "Accept", value= "application/json")
    .authorizationHeader("Key dGVzdGVjYXJnYXdoYXRzYXBwOnIwNFZ3UmF5REpKazdsWGVRV1VS")



  val rnd = new Random()
  var idNumber =  (0 to 100).iterator
  var phoneid =  (0 to 100).iterator
  var idNumber1 =  (0 to 100).iterator
  var phoneid1 =  (0 to 100).iterator


  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

 val customFeeder = Iterator.continually(Map(
   "id" -> ("89d5484d-398c-4912-90a1-55d4ed26225" + idNumber.next()),
   "phoneNumber" -> ("55318703206" + phoneid.next()),
 ))

  val customFeeder1 = Iterator.continually(Map(
    "id" -> ("a456-42665544000-0123e4567-e89b-12d" + idNumber1.next()),
    "uri" -> ("hmg-wa.gw.msging.net/accounts/+55318703206" + phoneid1.next()),
  ))


  def postNewMessage() = {
    repeat(1) {
      feed(customFeeder1).
        exec(http("Post New message1")
          .get("commands")
          .body(ElFileBody("bodies/Mensagem_Ativa/GetContact.json")).asJson
          .check(status.is(200)))
      feed(customFeeder).
        exec(http("Post New message")
          .post("messages")
          .body(ElFileBody("bodies/Mensagem_Ativa/NewMessageAtiva_Text.json")).asJson
          .check(status.is(202)))
    }
  }

  val scn = scenario("Post New message")
    .exec(postNewMessage()
    )

  setUp(
    scn.inject(
      nothingFor(1 seconds),
      atOnceUsers(100),
    ).protocols(httpConf.inferHtmlResources())
  )
}
