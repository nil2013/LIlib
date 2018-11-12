package me.nsmr
package lilib
package core

import java.util.Date

object CaseNumber {
  lazy val yearFormat = new java.text.SimpleDateFormat("GGGGy", new java.util.Locale("ja", "JP", "JP"))

  def apply(year: Date, mark: String, index: Int): CaseNumber = {
    return SimpleCaseNumber(year, mark, index)
  }
}

trait CaseNumber {
  import CaseNumber._

  def year: Date
  def mark: String
  def index: Int

  override def toString(): String = s"${yearFormat.format(year)}年（$mark）第${index}号"
}
