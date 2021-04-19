package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

class CSVFeeder extends Simulation {
  val httpConf = http.baseUrl(url = "http://video-game-db.eu-west-2.elasticbeanstalk.com/app")
    .header(name= "Accept", value= "application/json")

  val csvFeeder = csv("data/gameCSVFile.csv").circular

  def getSpecificVideoGame(): ChainBuilder = {
    repeat(10){
      feed(csvFeeder)
        .exec(http("Get specif video game")
        .get("/videogames/${gameId}")
        .check(jsonPath("$.name").is("${gameName}"))
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
