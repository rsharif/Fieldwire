package controllers

import java.io.{File, FileInputStream, FileOutputStream}
import java.nio.file.Paths

import javax.inject._
import akka.actor.ActorSystem
import play.api.mvc._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future, Promise}
import play.api.libs.json._
import com.fieldwire.services.ConstructionProjectService
import org.slf4j.LoggerFactory
import com.fieldwire.model._
import com.sksamuel.scrimage.Image
import com.sksamuel.scrimage.nio.{JpegWriter, PngWriter}
import org.apache.commons.io.FilenameUtils
import scalikejdbc._

/**
 * This controller creates an `Action` that demonstrates how to write
 * simple asynchronous code in a controller. It uses a timer to
 * asynchronously delay sending a response for 1 second.
 *
 * @param cc standard controller components
 * @param actorSystem We need the `ActorSystem`'s `Scheduler` to
 * run code after a delay.
 * @param exec We need an `ExecutionContext` to execute our
 * asynchronous code.  When rendering content, you should use Play's
 * default execution context, which is dependency injected.  If you are
 * using blocking operations, such as database or network access, then you should
 * use a different custom execution context that has a thread pool configured for
 * a blocking API.
 */
@Singleton
class ConstructionProjectController @Inject()
(
  cc: ControllerComponents,
  actorSystem: ActorSystem,
  constructionProjectService: ConstructionProjectService)(implicit exec: ExecutionContext)
  extends AbstractController(cc) {

  private var logger = LoggerFactory.getLogger(getClass)

  def projects = Action.async { request =>
    Future {
      if (!authenticate(request)) {
        Forbidden("You are not authenticated to make this request")
      } else {

        Ok(Json.toJson(constructionProjectService.getConstructionProjects))
      }

    }
  }

  def getProjectById(id: String) = Action.async { request =>
    Future {
      if (!authenticate(request)) {
        Forbidden("You are not authenticated to make this request")
      } else {
        val constructionProject = constructionProjectService.getConstructionProjectById(id);
        logger.debug(s"Find project by Id ${id } , ${constructionProject}")
        Ok(Json.toJson(constructionProject))
      }

    }
  }

  def createProject = Action.async(parse.json[ConstructionProject]) { request =>
      logger.info(s"Logging request ${request.body}")
      constructionProjectService.createConstructionProject(request.body).map (constructionProject => {
        Ok(Json.toJson(constructionProject))
      }
      ).recover {

        case ex: Exception => Ok(ex.getMessage)
      }

  }

  def getFloorPlansByProject(id: String) = Action.async { request =>
    Future {
      if (!authenticate(request)) {
        Forbidden("You are not authenticated to make this request")
      } else {
        val constructionProject = constructionProjectService.getFloorPlanByProject(id);
        Ok(Json.toJson(constructionProject))
      }

    }
  }

  def removeProject(id: String) = Action.async { request =>
    Future {
      if (!authenticate(request)) {
        Forbidden("You are not authenticated to make this request")
      } else {
        constructionProjectService.removeProject(id);
        Ok("Deleted")
      }

    }
  }

  def authenticate(request: Request[AnyContent]): Boolean = {
    val userName = request.headers.get("username").fold("")(identity)
    userName == "fieldwire"
  }

  def uploadFile = Action(parse.multipartFormData) { request =>
    request.body
      .file("floorplan")
      .map { picture =>
        // only get the last part of the filename
        // otherwise someone can send a path like ../../home/foo/bar.txt to write to other files on the system
        val filename    = Paths.get(picture.filename).getFileName
        val fileSize    = picture.fileSize
        val contentType = picture.contentType


        picture.ref.copyTo(Paths.get(s"/tmp/floorplans/$filename"), replace = true)

        implicit val writer = PngWriter()
        val in = new FileInputStream(s"/tmp/floorplans/$filename") // input stream
        val fileNameWithoutExtension = FilenameUtils.getBaseName(filename.toString)
        Image.fromStream(in).fit(100, 100).output(new File(s"/tmp/floorplans/thumbnails/${fileNameWithoutExtension}.png"))


        Ok("File uploaded")
      }
      .getOrElse {
        BadRequest("missing file")
      }
  }


}