package me.nsmr
package lilib
package core

import java.util.Date

trait Precedent {
  def number: CaseNumber
  def date: Date
  def court: Court
  def judgeType: Option[JudgeType]

  def content: Option[String]
}
