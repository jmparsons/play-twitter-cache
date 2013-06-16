package controllers

import play.api._
import play.api.mvc._
import play.api.Play._
import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.cache._
import play.api.libs.oauth._
import play.api.libs.ws._
import play.api.libs.concurrent.Execution.Implicits._

object Application extends Controller {

  def index = Action {

    Cache.getAs[Seq[JsValue]]("tweets") match {
      case Some(tweets) => {
        println("from cache")
        Ok(Json.toJson(tweets))
      }
      case None => Async {
        WS.url("https://api.twitter.com/1.1/statuses/user_timeline.json?count=20&trim_user=true&exclude_replies=true&include_rts=false")
          .sign(OAuthCalculator(Twitter.KEY, Twitter.TOKEN))
          .get
          .map(
            result => {
              val tweetList = result.json.as[Seq[JsValue]]
              val fiveTweets = tweetList.take(5)
              val remappedTweets = tweetList.take(5).map { i =>
                Json.toJson(
                  Map(
                    "created_at" -> (i \ "created_at").as[String],
                    "id_str" -> (i \ "id_str").as[String],
                    "text" -> (i \ "text").as[String]
                  )
                )
              }
              // val tweets = tweetList
              // val tweets = fiveTweets
              val tweets = remappedTweets
              println("cache not hit")
              Cache.set("tweets", tweets, 10) //caches for 10 seconds
              Ok(Json.toJson(tweets))
            }
          )
      }
    }

  }

}

object Twitter {
  val KEY = ConsumerKey(Play.configuration.getString("tw.consumer_key").get, Play.configuration.getString("tw.consumer_secret").get)
  val TOKEN = RequestToken(Play.configuration.getString("tw.access_token").get, Play.configuration.getString("tw.access_secret").get)
}