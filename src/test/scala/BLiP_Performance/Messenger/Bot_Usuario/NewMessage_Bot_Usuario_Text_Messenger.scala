package BLiP_Performance.Messenger.Bot_Usuario

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.util.Random

class NewMessage_Bot_Usuario_Text_Messenger extends Simulation{

  val httpConf = http.baseUrl(url = "https://hmg-messenger.gw.msging.net/")
    .header(name= "Accept", value= "application/json")
    .authorizationHeader("Key bWVzc2VuZ2VyOjk2MnpMU0ZKSHhnUUl4ZVl3bWV4")


  val rnd = new Random()
  var idNumbers =  (0 to 100).iterator


  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  val customFeeder = Iterator.continually(Map(
    "id" -> ("89d5484d-398c-4912-90a1-55d4ed2622" + idNumbers.next()),
  ))


  def postNewTicket() = {
    repeat(1) {
     feed(customFeeder).
        exec(http("Post New Message")
          .post("messages")
          .body(ElFileBody("bodies/Messenger/NewMessage_Bot_Usuario_Text_Messenger.json")).asJson
          .check(status.is(202)))
    }
  }

  val scn = scenario("Post New Message")
    .exec(postNewTicket()
    )

  setUp(
    scn.inject(
      nothingFor(3 seconds),
      atOnceUsers(2),
    ).protocols(httpConf.inferHtmlResources())
  )
}
