package me.nsmr
package lilib.utils

import scala.util.matching.Regex
import me.nsmr.lilib.core.{ CaseNumber, CaseYear }
import com.typesafe.scalalogging.Logger

object CaseNumberUtil {
  lazy val logger = Logger(this.getClass)

  private lazy val pattern = new Regex(
    s"""(平成|昭和)(\\d+)(.+?)(\\d+)""",
    "era", "year", "mark", "index"
  )

  def parse(str: String): Option[CaseNumber] = {
    pattern.findFirstMatchIn(str).flatMap { m =>
      CaseYear.Era(m.group("era")).map { era =>
        if(m.before.length > 0 || m.after.length > 0) logger.debug(s"not exactly matches pattern of case number: ${str}")
        CaseNumber(CaseYear(era, m.group("year").toInt), m.group("mark"), m.group("index").toInt)
      }
    }
  }
}

class CaseNumberFormatException(mes: String = "") extends IllegalArgumentException(mes)
