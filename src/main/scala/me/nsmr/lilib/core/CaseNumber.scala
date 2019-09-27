package me.nsmr
package lilib
package core

object CaseNumber {
  def apply(year: JapaneseYear, mark: CaseMark, index: Int): CaseNumber = {
    return SimpleCaseNumber(year, mark, index)
  }

  def unapply(obj: CaseNumber): Option[(JapaneseYear, CaseMark, Int)] = return Option((obj.year, obj.mark, obj.index))
}

trait CaseNumber {

  import CaseNumber._

  def year: JapaneseYear

  def mark: CaseMark

  def index: Int

  override def toString(): String = s"${year}${mark}第${index}号"
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
    if (prefix.length == 1) this.withPrefix(prefix.charAt(0))
    else throw new IllegalArgumentException("prefix must be just 1 character.")
  }

  def withSuffix(suffix: Char): CaseMark = this.copy(suffix = Option(suffix))

  def withSuffix(suffix: String): CaseMark = {
    if (suffix.length == 1) this.withSuffix(suffix.charAt(0))
    else throw new IllegalArgumentException("suffix must be just 1 character.")
  }

}
