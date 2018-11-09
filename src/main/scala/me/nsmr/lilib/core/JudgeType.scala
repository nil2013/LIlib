package me.nsmr
package lilib
package core

sealed abstract class JudgeType(val name: String) {
  override def toString: String = name

  def ordinal = JudgeType.values.indexOf(this)
}

object JudgeType {

  case object JUDGMENT extends JudgeType("判決")

  case object ORDER extends JudgeType("決定")

  val values = Array(JUDGMENT, ORDER)

  def unapply(x: JudgeType) = Option((x.name))

  def apply(n: String) = n match {
    case JUDGMENT.name => JUDGMENT
    case ORDER.name => ORDER
  }
}
