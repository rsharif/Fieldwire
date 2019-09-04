package services

import play.api.libs.json._

case class Tweet(
                  tweet: String,
                  author: String,
                  tweets: List[Tweet]
                )

object Tweet {
  implicit val TweetFormat = Json.format[Tweet]
}

class TweetService {
  def getTweets = {
    Tweet("Fieldwire is great", "Rizwan Sharif", List(Tweet("1", "2", List.empty)))
  }
}