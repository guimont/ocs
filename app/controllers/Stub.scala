package controllers

import play.api.mvc._
import models.DataS
import play.api.libs.json.JsValue


/**
 * Created with IntelliJ IDEA.
 * User: gmo
 * Date: 18/09/14
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
object Stub extends Controller {

  /**
   * get run list from now - 31 days
   * @return
   */
  def runStub = Action {
   Ok(DataS.generateStubList)
  }


}
