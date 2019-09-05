package com.fieldwire.model

import org.joda.time.DateTime
import play.api.libs.json._

case class FloorPlan
(
  id: String,
  name: String,
  projectId: String,
  image: String
)

object FloorPlan {

  implicit val FloorPlanFormat = Json.format[FloorPlan]

}

case class ConstructionProject
(
  id: String,
  name: String,
  floorPlans: List[FloorPlan] = List.empty,
  dateCreated: DateTime
)

object ConstructionProject {

  val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

  implicit val dateFormat = Format[DateTime](Reads.jodaDateReads(pattern), Writes.jodaDateWrites(pattern))

  implicit val ConstructionProjectFormat = Json.format[ConstructionProject]

}
