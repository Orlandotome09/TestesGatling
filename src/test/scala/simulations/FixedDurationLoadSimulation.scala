package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import scala.concurrent.duration._

class FixedDurationLoadSimulation extends Simulation {
  val httpConf = http.baseUrl(url = "http://video-game-db.eu-west-2.elasticbeanstalk.com/app/")
    .header(name= "Accept", value= "application/json")

  def getAllVideoGames(): ChainBuilder = {
    exec(
      http("Get all videos games")
        .get("videogames")
        .check(status.is(200))
    )
  }

  def getSpecificGame(): ChainBuilder = {
    exec(
      http("Get Specific Game")
        .get("videogames/2")
        .check(status.is(200))
    )
  }

  val scn = scenario("fixed Duration Simulation")
    .forever() {
      exec(getAllVideoGames())
        .pause(5)
        .exec(getSpecificGame())
        .pause(5)
        .exec(getAllVideoGames())
    }

  setUp(
    scn.inject(
      nothingFor(5 seconds),
      atOnceUsers(10),
      rampUsers(50) during(30 seconds)
    ).protocols(httpConf.inferHtmlResources())
  ).maxDuration(1 minute)
}
