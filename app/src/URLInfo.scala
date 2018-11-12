package src

import scala.util.Try
import org.jsoup.Jsoup

/**
  * Provides info on URLs
  *
  * TODO Include more than just getting titles (but that's all that's required for test obviously)
  */
class URLInfo (validUrl: String) {
  private val url = validUrl

  def getTitle(): Try[String] = {
    Try(Jsoup.connect(url).get().title())
  }
}
