package com.fieldwire.services

import java.util.UUID

import com.fieldwire.model.{ConstructionProject, FloorPlan, FloorPlanDetail}

import scala.concurrent.{ExecutionContext, Future, Promise}
import play.api.libs.json._
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import org.joda.time.DateTime

import scala.collection.mutable



class ConstructionProjectService {
  private var logger = LoggerFactory.getLogger(getClass)

  var constructionProjects: mutable.MutableList[ConstructionProject] = mutable.MutableList.empty

  def getConstructionProjects: mutable.MutableList[ConstructionProject] = {

    constructionProjects
  }

  def createConstructionProject(constructionProject:  ConstructionProject): Future[ConstructionProject] = Future {
    logger.debug(s"Received Request =>  ${constructionProject}")
    var projectToSave = if(constructionProject.id.isDefined) constructionProject else constructionProject.copy(id = Some(UUID.randomUUID()))
    constructionProjects += projectToSave
    projectToSave
  }

  def getConstructionProjectById(id: String): Option[ConstructionProject] = {
    logger.debug(s"Finding project with id = ${id}")
    constructionProjects.find(_.id.getOrElse("").toString == id)
  }

  def getFloorPlanByProject(id: String): List[FloorPlan] = {
    val project = constructionProjects.find(_.id.getOrElse("").toString == id)
    project.map(_.floorPlans).getOrElse(List.empty)
  }

  def removeProject(id: String) =  {
    val newList: mutable.MutableList[ConstructionProject] = mutable.MutableList.empty

    constructionProjects.foreach { constructionProject =>
      if(constructionProject.id.getOrElse("").toString != id) newList += constructionProject
    }
    constructionProjects = newList
  }
}