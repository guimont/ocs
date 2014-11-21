package controllers
import play.api.mvc._
import models.EventS


/**
 * controller for scala html page
 */
object Application extends Controller {


  /**
   * index page
   * @return
   */
  def customers = Action {
    Ok(views.html.customer(EventS.generateStubList))
  }


  def ping = Action { implicit request =>
    Ok("still alive")
  }

}