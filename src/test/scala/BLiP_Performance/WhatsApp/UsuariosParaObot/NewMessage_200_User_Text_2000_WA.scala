package BLiP_Performance.WhatsApp.UsuariosParaObot

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.util.Random

class NewMessage_200_User_Text_2000_WA extends Simulation{

  val httpConf = http.baseUrl(url = "https://hmg-wa.gw.msging.net/")
    .header(name= "Accept", value= "application/json")
    .authorizationHeader("Key dGVzdGVjYXJnYXdoYXRzYXBwOnIwNFZ3UmF5REpKazdsWGVRV1VS")


  val rnd = new Random()
  var idName =  (0 to 100).iterator
  var idWa =  (0 to 100).iterator
  var idTime =  (0 to 100).iterator
  var idBody =  (0 to 100).iterator
  var idNumber =  (0 to 100).iterator


  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

 val customFeeder = Iterator.continually(Map(
   "id" -> ("ABGGFlA5FpafAgo6tHcNmNjXmu" + idNumber.next()),
   "name" -> ("orlando test" + idName.next()),
   "wa_id" -> ("55319112233" + idWa.next()),
   "timestamp" -> ("151869423" + idTime.next()),
   "body" -> ("test" + idBody.next()),
 ))


  def postNewTicket() = {
    repeat(1) {
      feed(customFeeder).
        exec(http("Post New message")
          .post("a1af6fa0-96d2-49e2-8156-9f3750fc06a6")
          .body(ElFileBody("bodies/WhatsApp/NewMessageBotText_WA.json")).asJson
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
