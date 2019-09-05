package com.fieldwire.services

import scala.concurrent.{ExecutionContext, Future, Promise}
import play.api.libs.json._
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global

import org.joda.time.DateTime

case class Tweet(
                  tweet: String,
                  author: String,
                  tweets: List[Tweet],
                  timestamp: Option[DateTime] = Some(DateTime.now)
                )

object Tweet {
  val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
  implicit val dateFormat = Format[DateTime](Reads.jodaDateReads(pattern), Writes.jodaDateWrites(pattern))

  implicit val TweetFormat = Json.format[Tweet]
}

class TweetService {
  private var logger = LoggerFactory.getLogger(getClass)
  def getTweets = {
    Tweet("Fieldwire is great", "Rizwan Sharif", List(Tweet("1", "2", List.empty)))
  }

  def createTweet(tweet: Tweet): Future[Tweet] = Future {
    logger.debug(s"Received Request =>  ${tweet}")
    tweet.copy(timestamp = Some(DateTime.now))
  }
}