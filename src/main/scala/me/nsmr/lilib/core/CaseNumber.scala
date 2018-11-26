package me.nsmr
package lilib
package core

import java.util.{ Date, Calendar, TimeZone, Locale }

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

  sealed abstract class Era(val name: String, val calendarEra: Int) {
    override def toString(): String = name
  }

  object Era {
    case object Meiji extends Era("明治", 1)
    case object Taisho extends Era("大正", 2)
    case object Showa extends Era("昭和", 3)
    case object Heisei extends Era("平成", 4)

    def M = Meiji
    def T = Taisho
    def S = Showa
    def H = Heisei

    def list: Array[Era] = Array(M, T, S, H)

    def apply(idx: Int): Era = list(idx)
    def apply(str: String): Option[Era] = list.find(_.name == str)
  }

  lazy val timezone: TimeZone = TimeZone.getTimeZone("Asia/Tokyo")

  lazy val locale: Locale = new Locale("ja", "JP", "JP")

  private final lazy val format = new java.text.SimpleDateFormat("GGGGy年", locale)

}

case class CaseYear(era: CaseYear.Era, year: Int) {

  override def toString = s"${era}${year}年"

  def toDate: Date = {
    val cal = Calendar.getInstance(CaseYear.locale)
    cal.clear
    cal.setLenient(false)
    cal.set(Calendar.ERA, era.calendarEra)
    cal.set(Calendar.YEAR, year)
    cal.getTime
  }
}
