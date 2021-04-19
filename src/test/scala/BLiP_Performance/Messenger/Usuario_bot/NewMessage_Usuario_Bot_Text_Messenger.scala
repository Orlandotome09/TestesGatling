package BLiP_Performance.Messenger.Usuario_bot

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.util.Random

class NewMessage_Usuario_Bot_Text_Messenger extends Simulation{

  val httpConf = http.baseUrl(url = "https://hmg-messenger.gw.msging.net/")
    .header(name= "Accept", value= "application/json")
    .authorizationHeader("Key bWVzc2VuZ2VyOjk2MnpMU0ZKSHhnUUl4ZVl3bWV4")


  val rnd = new Random()
  var idNumbers =  (0 to 100).iterator
  var idTitleNumbers =  (0 to 100).iterator

  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  val customFeeder = Iterator.continually(Map(
    "object" -> ("testOrlando" + randomString(1)),
    "url" -> ("https://www.take.net" + idNumbers.next()),
    "id" -> ("362866356383266" + randomString(1)),
    "text" -> ("testOrlando" + randomString(1)),
    "title" -> ("test" + idTitleNumbers.next()),
    "mid" -> ("EAANgWCZCLVtgBAEhd0LnYQ5KLNT6jrCBbQyASP36ThutnpJVgshfSuxEvQxUHuxZBJRB1VpB3VZAHh9FJiS9fHjTwcaN2O8ryZAJ7st0vkxUlcZB8YTKpZABXZCKJYODXKPEAlBXl1aNAtLNbn8NPbANrHA8kCGOFoefbwQLCWuJwZDZ"
        + randomString(1))
  ))


  def postNewTicket() = {
    repeat(1) {
     feed(customFeeder).
        exec(http("Post New Message")
          .post("messages")
          .body(ElFileBody("bodies/Messenger/NewMessage_Usuario_Bot_Text_Messenger.json")).asJson
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
