package com.fieldwire.model

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json._


case class FloorPlanDetail
(
  originalImage: String,
  thumbnail: String,
  large : String
)

object FloorPlanDetail {
  implicit val FloorPlanDetailFormat = Json.format[FloorPlanDetail]
}

case class FloorPlan
(
  id: Option[UUID] = Some(UUID.randomUUID()),
  name: String,
  projectId: String,
  details: FloorPlanDetail
)

object FloorPlan {


  implicit val FloorPlanFormat = Json.format[FloorPlan]
}

case class ConstructionProject
(
  id: Option[UUID] = Some(UUID.randomUUID()),
  name: String,
  floorPlans: List[FloorPlan] = List.empty,
  dateCreated: DateTime
)

object ConstructionProject {

  val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

  implicit val dateFormat = Format[DateTime](Reads.jodaDateReads(pattern), Writes.jodaDateWrites(pattern))

  implicit val ConstructionProjectFormat = Json.format[ConstructionProject]

}
