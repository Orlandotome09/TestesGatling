package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

class csvFeederCustom extends Simulation{

  val httpConf = http.baseUrl(url = "http://video-game-db.eu-west-2.elasticbeanstalk.com/app")
    .header(name= "Accept", value= "application/json")

  var idNumbers = (1 to 10).iterator

  val  customFeeder = Iterator.continually(Map("gameId" -> idNumbers.next()))

  def getSpecificVideoGame(): ChainBuilder = {
    repeat(10){
      feed(customFeeder)
        .exec(http("Get specif video game")
          .get("/videogames/${gameId}")
          .check(status.is(200)))
        .pause(1)
    }
  }

  val scn = scenario("Csv Feeder test")
    .exec(getSpecificVideoGame())

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
}
