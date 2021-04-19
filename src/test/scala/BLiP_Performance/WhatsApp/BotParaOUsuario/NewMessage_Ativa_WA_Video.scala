package BLiP_Performance.WhatsApp.BotParaOUsuario

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.util.Random

class NewMessage_Ativa_WA_Video extends Simulation{

  val httpConf = http.baseUrl(url = "https://hmg-http.msging.net/")
    .header(name= "Accept", value= "application/json")
    .authorizationHeader("Key d2hhdHNhcHBib3QxOlNvQVJpY1pDemo4dGhhN3F3aU43")


  val rnd = new Random()
  var idNumber =  (0 to 100).iterator
  var phoneid =  (0 to 100).iterator


  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

 val customFeeder = Iterator.continually(Map(
   "id" -> ("89d5484d-398c-4912-90a1-55d4ed26225" + idNumber.next()),
   "phoneNumber" -> ("55318703206" + phoneid.next()),
 ))


  def postNewTicket() = {
    repeat(1) {
      feed(customFeeder).
        exec(http("Post New message")
          .post("messages")
          .body(ElFileBody("bodies/Mensagem_Ativa/NewMessageAtiva_video.json")).asJson
          .check(status.is(202)))
    }
  }

  val scn = scenario("Post New message")
    .exec(postNewTicket()
    )

  setUp(
    scn.inject(
      nothingFor(1 seconds),
      atOnceUsers(50),
    ).protocols(httpConf.inferHtmlResources())
  )
}
