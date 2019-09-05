package com.fieldwire.services

import com.fieldwire.model.ConstructionProject

import scala.concurrent.{ExecutionContext, Future, Promise}
import play.api.libs.json._
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import org.joda.time.DateTime



class ConstructionProjectService {
  private var logger = LoggerFactory.getLogger(getClass)
  def getConstructionProjects: List[ConstructionProject] = {
    List.empty
  }

  def createConstructionProject(tweet: ConstructionProject): Future[Option[ConstructionProject]] = Future {
    logger.debug(s"Received Request =>  ${tweet}")
    None
  }
}