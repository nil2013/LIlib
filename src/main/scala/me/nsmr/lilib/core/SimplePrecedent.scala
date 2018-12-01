package me.nsmr
package lilib
package core

import java.time.LocalDate

case class SimplePrecedent(
  override val number : CaseNumber,
  override val date   : LocalDate,
  override val court  : Court,
  override val judgeType: Option[JudgeType],
  override val content: Option[String]
) extends Precedent
