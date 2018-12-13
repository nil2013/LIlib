package me.nsmr
package lilib
package core

import java.time.chrono.{ JapaneseChronology, JapaneseDate, JapaneseEra }
import java.time.format.DateTimeFormatter
import java.time.{ LocalDate, Year }

object CaseNumber {
  def apply(year: CaseYear, mark: String, index: Int): CaseNumber = {
    return SimpleCaseNumber(year, mark, index)
  }
  def unapply(obj: CaseNumber): Option[(CaseYear, String, Int)] = return Option((obj.year, obj.mark, obj.index))
}

trait CaseNumber {
  import CaseNumber._

  def year: CaseYear
  def mark: String
  def index: Int

  override def toString(): String = s"${year}($mark)第${index}号"
}

object CaseYear {

  sealed abstract class Era(val name: String, val javaEra: JapaneseEra) {
    override def toString(): String = name
  }

  object Era {
    case object Meiji extends Era("明治", JapaneseEra.MEIJI)
    case object Taisho extends Era("大正", JapaneseEra.TAISHO)
    case object Showa extends Era("昭和", JapaneseEra.SHOWA)
    case object Heisei extends Era("平成", JapaneseEra.HEISEI)

    def M = Meiji
    def T = Taisho
    def S = Showa
    def H = Heisei

    def list: Array[Era] = Array(M, T, S, H)

    def apply(idx: Int): Era = list(idx)
    def apply(str: String): Option[Era] = list.find(_.name == str)
  }

  private lazy val formatter = DateTimeFormatter.ofPattern("Gy年").withChronology(JapaneseChronology.INSTANCE)

}

case class CaseYear(era: CaseYear.Era, year: Int) {

  override def toString = s"${era}${year}年"

  def toJavaYear: Year = Year.from(JapaneseDate.of(era.javaEra, year, 1, 1))
}
