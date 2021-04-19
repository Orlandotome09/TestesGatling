package BLiP_Performance.GBM.Bot_Usuario

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.util.Random

class NewMessage_Bot_Usuario_Text_GBM extends Simulation{

  val httpConf = http.baseUrl(url = "https://hmg-businessmessages.gw.msging.net/")
    .header(name= "Accept", value= "application/json")
    .header(name= "X-Goog-Signature", value= "JIk76SgJqsBXE2jtAc5KLReLCuOAKWBF+FOeXR/tiWQ9AOhAvv6vO0v25P7+FkPy7zp4wJWXCon0Ahh+x4Duqw==")
    .authorizationHeader("Key Z2JtOkhnSHZTZElwV3l0Nktvdjljenl5")


  val rnd = new Random()

  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

 //val customFeeder = Iterator.continually(Map("text" ->
  // ("TestePerformance" + randomString(2))))

  def postNewTicket() = {
    repeat(2) {
    //  feed(customFeeder).
        exec(http("Post New message")
          .post("messages")
          .body(ElFileBody("bodies/GBM/NewMessage_Bot_Usuario_Text_GBM.json")).asJson
          .check(status.is(202)))
    }
  }

  val scn = scenario("Post New message")
    .exec(postNewTicket()
    )

  setUp(
    scn.inject(
      nothingFor(3 seconds),
      atOnceUsers(40),
    ).protocols(httpConf.inferHtmlResources())
  )
}
