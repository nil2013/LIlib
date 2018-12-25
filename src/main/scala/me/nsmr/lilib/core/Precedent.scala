package me.nsmr
package lilib
package core

import scala.util.Try
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.chrono.JapaneseChronology

object Precedent {
  def apply( num: CaseNumber, date: LocalDate, court: Court, judgeType: Option[JudgeType], content: Option[String]): Precedent = SimplePrecedent(
    num, date, court, judgeType, content)

  lazy val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("Gy年M月d日").withChronology(JapaneseChronology.INSTANCE)
}

trait Precedent {
  def number: CaseNumber
  def date: LocalDate
  def court: Court
  def judgeType: Option[JudgeType]

  def content: Option[String]

  override def toString(): String = {
    val c = Try { this.court }.toOption
    val cn = Try { this.number }.toOption
    val d = Try { this.date }.toOption
    val splitter = if(cn.isDefined && d.isDefined) {"・"} else {""}
    s"Precedent(${c.map(_.shortName).mkString}${cn.mkString}${ splitter }${d.map(_.format(Precedent.dateFormatter)).mkString}${c.map(_.branch).mkString}${judgeType.mkString}"
  }
}
