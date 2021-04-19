import io.gatling.core.Predef._
import io.gatling.http.Predef._

class MyFirstTest  extends Simulation{

  // 1 Http Conf
  val httpConf = http.baseUrl(url = "http://video-game-db.eu-west-2.elasticbeanstalk.com/swagger-ui/index.html#/Video_Games")
    .header(name= "Accept", value= "application/json")

  // 2 Scenario Definition
  val scn = scenario(scenarioName = "My First Test")
    .exec(http(requestName = "Get All Games")
      .get("videogames"))
  // 3 Load Scenario
  setUp(
    scn.inject(atOnceUsers(users = 1))
  ).protocols(httpConf)
}
