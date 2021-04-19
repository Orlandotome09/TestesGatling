package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CheckResponseBodyAndExtract extends Simulation{

  val httpConf = http.baseUrl(url = "http://video-game-db.eu-west-2.elasticbeanstalk.com/app")
    .header(name= "Accept", value= "application/json")

  val scn = scenario(scenarioName = "Check JSON Path")

    .exec(http(requestName = "Get specifc game")
    .get("/videogames/1")
    .check(jsonPath(path="$.name").is(expected="Resident Evil 4")))

    .exec(http(requestName = "Get all video games")
    .get("/videogames")
    .check(jsonPath("$[2].id").saveAs("gameId")))
    .exec{ session => println(session); session }

    .exec(http("Get specific game")
    .get("/videogames/${gameId}")
    .check(jsonPath("$.name").is("Gran Turismo 3"))
    .check(bodyString.saveAs("responseBody")))
    .exec{ session => println(session("responseBody").as[String]); session }

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
}
