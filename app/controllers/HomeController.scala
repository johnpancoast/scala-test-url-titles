package controllers

import play.api._
import play.api.mvc._
import javax.inject._
import java.net.{MalformedURLException, SocketTimeoutException}
import org.jsoup.{HttpStatusException, UnsupportedMimeTypeException}
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

    // This blanket covering of all failures works for this test's simple purposes though.
    titleTry match {
      case Success(title) => Ok(title)
      case Failure(exception) => {
        if (exception.isInstanceOf[MalformedURLException]) {
          BadRequest("Malformed URL")
        } else if (exception.isInstanceOf[HttpStatusException]) {
          // just pass on the status code to Play's handling
          Results.Status(exception.asInstanceOf[HttpStatusException].getStatusCode())
        } else if (exception.isInstanceOf[SocketTimeoutException]) {
          RequestTimeout("Request timed out")
        } else if (exception.isInstanceOf[UnsupportedMimeTypeException]) {
          // Note that this is from *our* request so its not appropriate to give a 4** to the user and should instead just be considered a 500 since it's our request that failed and the end-user cannot enter mime type (rename it media type already!)
          InternalServerError("Internal server error")
        } else {
          InternalServerError("Internal server error")
        }
      }
    }
  }
}
