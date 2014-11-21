import actors.RefreshPageAuto
import play.api._


object Global extends GlobalSettings {

  RefreshPageAuto.start
}
