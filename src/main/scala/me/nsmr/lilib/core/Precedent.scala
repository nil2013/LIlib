package me.nsmr
package lilib
package core

import java.util.Date

object Precedent {
  def apply( num: CaseNumber, date: Date, court: Court, judgeType: Option[JudgeType], content: Option[String]): Precedent = SimplePrecedent(
    num, date, court, judgeType, content)
}

trait Precedent {
  def number: CaseNumber
  def date: Date
  def court: Court
  def judgeType: Option[JudgeType]

  def content: Option[String]
}
