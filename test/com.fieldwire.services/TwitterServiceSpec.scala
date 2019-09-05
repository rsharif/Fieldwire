package com.fieldwire.services

import org.scalatestplus.play._
import org.scalatest.AsyncFlatSpec

import scala.collection.mutable
import com.fieldwire.services.ConstructionProjectService

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class TwitterServiceSpec extends AsyncFlatSpec {

    it should "should return a tweet with timestamp" in {
      val myTweet = Tweet("Fieldwire is great", "Rizwan Sharif", List(Tweet("1", "2", List.empty)))
      val twitterService = new ConstructionProjectService()

      val returnedTweetFut = twitterService.createTweet(myTweet)

      returnedTweetFut.map {tweet => assert(tweet.timestamp.isDefined)}


    }

  it should "should return a tweet with timestamp while getting" in {

    val twitterService = new ConstructionProjectService()

    val tweet = twitterService.getConstructionProjects

    assert(tweet.timestamp.isEmpty)


  }
}