package me.nsmr.lilib.core

import java.time.Year
import java.time.chrono.{JapaneseChronology, JapaneseEra}
import java.time.format.DateTimeFormatter

object JapaneseYear {

  sealed abstract class Era(val name: String, val javaEra: JapaneseEra, val abbreviation: String) {
    override def toString(): String = name
  }

  object Era {

    case object Meiji extends Era("明治", JapaneseEra.MEIJI, "M")

    case object Taisho extends Era("大正", JapaneseEra.TAISHO, "T")

    case object Showa extends Era("昭和", JapaneseEra.SHOWA, "S")

    case object Heisei extends Era("平成", JapaneseEra.HEISEI, "H")

    /** JapaneseDate.Era は9月まで実装されない予定。 */
    case object Reiwa extends Era("令和", null, "R")

    def M = Meiji

    def T = Taisho

    def S = Showa

    def H = Heisei

    def R = Reiwa

    def list = Array[Era](M, T, S, H, R)

    def apply(idx: Int): Era = list(idx)

    def apply(javaEra: JapaneseEra): Era = javaEra match {
      case JapaneseEra.HEISEI => this.Heisei
      case JapaneseEra.SHOWA => this.Showa
      case JapaneseEra.TAISHO => this.Taisho
      case JapaneseEra.MEIJI => this.Meiji
    }

    def apply(str: String): Option[Era] = list.find {
      entity => (entity.name == str || entity.abbreviation == str || entity.name.startsWith(str))
    }
  }

  private lazy val formatter = DateTimeFormatter.ofPattern("Gy年").withChronology(JapaneseChronology.INSTANCE)

}

case class JapaneseYear(era: JapaneseYear.Era, year: Int) {

  override def toString = {
    year match {
      case 1 => s"${era}元年"
      case _ => s"${era}${year}年"
    }
  }

  /** JapaneseEraにおいて令和が実装されるまでの応急処置 */
  def toJavaYear: Year = {
    import JapaneseYear.Era
    val (era, year) = this.era match {
      case Era.Reiwa => (Era.Heisei, this.year + 30)
      case _ => (this.era, this.year)
    }
    Year.of(JapaneseChronology.INSTANCE.prolepticYear(era.javaEra, year))
  }
}
