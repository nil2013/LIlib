package me.nsmr
package lilib
package core

import java.time.chrono.{ JapaneseChronology, JapaneseDate, JapaneseEra }
import java.time.format.DateTimeFormatter
import java.time.{ LocalDate, Year }

object CaseNumber {
  def apply(year: CaseYear, mark: CaseMark, index: Int): CaseNumber = {
    return SimpleCaseNumber(year, mark, index)
  }
  def unapply(obj: CaseNumber): Option[(CaseYear, CaseMark, Int)] = return Option((obj.year, obj.mark, obj.index))
}

trait CaseNumber {
  import CaseNumber._

  def year: CaseYear
  def mark: CaseMark
  def index: Int

  override def toString(): String = s"${year}${mark}第${index}号"
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
    def apply(javaEra: JapaneseEra): Era = javaEra match {
      case JapaneseEra.HEISEI => this.Heisei
      case JapaneseEra.SHOWA => this.Showa
      case JapaneseEra.TAISHO => this.Taisho
      case JapaneseEra.MEIJI => this.Meiji
    }
    
    def apply(str: String): Option[Era] = list.find(_.name == str)
  }

  private lazy val formatter = DateTimeFormatter.ofPattern("Gy年").withChronology(JapaneseChronology.INSTANCE)

}

case class CaseYear(era: CaseYear.Era, year: Int) {

  override def toString = year match {
    case 1 => s"${era}元年"
    case _ => s"${era}${year}年"
  }

  def toJavaYear: Year = Year.of(JapaneseChronology.INSTANCE.prolepticYear(era.javaEra, year))
}

object CaseMark {

  def apply(mark: String): CaseMark = {
    this.apply(None, mark, None)
  }

  def apply(prefix: Char, mark: String): CaseMark = {
    this.apply(Option(prefix), mark, None)
  }

  def apply(mark: String, suffix: Char): CaseMark = {
    this.apply(None, mark, Option(suffix))
  }

  def apply(prefix: Char, mark: String, suffix: Char): CaseMark = {
    this.apply(Option(prefix), mark, Option(suffix))
  }

}

case class CaseMark(prefix: Option[Char], mark: String, suffix: Option[Char]) {

  override def toString = s"${prefix.mkString}(${mark})${suffix.mkString}"
  // override def toString = s"CaseMark(prefix = '${prefix.mkString}', mark = '${mark}', suffix = '${suffix.mkString}')"

  def hasPrefix: Boolean = this.prefix.isDefined

  def hasSuffix: Boolean = this.suffix.isDefined

  def withPrefix(prefix: Char): CaseMark = this.copy(prefix = Option(prefix))

  def withPrefix(prefix: String): CaseMark = {
    if(prefix.length == 1) this.withPrefix(prefix.charAt(0))
    else throw new IllegalArgumentException("prefix must be just 1 character.")
  }

  def withSuffix(suffix: Char): CaseMark = this.copy(suffix = Option(suffix))

  def withSuffix(suffix: String): CaseMark = {
    if(suffix.length == 1) this.withSuffix(suffix.charAt(0))
    else throw new IllegalArgumentException("suffix must be just 1 character.")
  }

}
