package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import src.URLInfo
import scala.util.{Try, Success, Failure}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  /**
    * Action to receive a URL then find and return the URLs page title
    *
    * TODO Respond with JSON which includes valid HTTP responses etc
    * I wasn't able to get to that unfortunately, but this does safely use Try/Success/Failure to at least give back
    * healthy responses.
    */
  def urlTitle(url: String) = Action { implicit request: Request[AnyContent] =>
    val titleTry = new URLInfo(url).getTitle()

    // TODO Add better checks on the exception to return proper HTTP response/code.
    // This blanket covering of all failures works for this test's simple purposes though.
    titleTry match {
      case Success(title) => Ok(title)
      case Failure(exception) => InternalServerError("Internal Server Error")
    }
  }
}
