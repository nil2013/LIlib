package me.nsmr
package lilib
package core

import java.util.Date

case class SimplePrecedent(
  override val number : CaseNumber,
  override val date   : Date,
  override val court  : Court,
  override val judgeType: Option[JudgeType],
  override val content: Option[String]
) extends Precedent
