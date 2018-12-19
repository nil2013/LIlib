package me.nsmr.lilib
package utils

import scala.util.matching.Regex
import com.typesafe.scalalogging.Logger
import core.{ CaseNumber, CaseYear, Court }

trait Parser[T] { def parse(str: String): Option[T] }

object Parsers {
  lazy val logger = Logger(this.getClass)

  lazy val court: Parser[Court] = new Parser[Court] {

    lazy val PAT_LONG  = new Regex(
      s"""(.{0,10})(${Court.LEVEL.mkString("|")})裁判所(.*支部|.*法廷|)""",
      "place", "level", "branch")

    lazy val PAT_SHORT = new Regex(s"""(.{0,10})(最高|高|地|家|簡)裁""", "place", "level")

    override def parse(str: String): Option[Court] = {
      try{
        str match {
          case PAT_LONG(p, l, b) => Option(Court(p, Court.levelOf(l), b))
          case PAT_SHORT(place, l) => Option(Court(place, Court.levelOf(l), ""))
          case _ => None
        }
      } catch {
        case e: Throwable => None
      }
    }
  }

  lazy val caseNumber: Parser[CaseNumber] = new Parser[CaseNumber] {
    private lazy val pattern = new Regex(
      s"""(平成|昭和)(\\d+)(.+?)(\\d+)""",
      "era", "year", "mark", "index"
    )

    override def parse(str: String): Option[CaseNumber] = {
      pattern.findFirstMatchIn(str).flatMap { m =>
        CaseYear.Era(m.group("era")).map { era =>
          if(m.before.length > 0 || m.after.length > 0) logger.debug(s"not exactly matches pattern of case number: ${str}")
          CaseNumber(CaseYear(era, m.group("year").toInt), m.group("mark"), m.group("index").toInt)
        }
      }
    }
  }

}

class CaseNumberFormatException(mes: String = "") extends IllegalArgumentException(mes)
