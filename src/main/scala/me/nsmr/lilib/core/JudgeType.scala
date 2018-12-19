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

  def apply(name: String): JudgeType = get(name) match {
    case Some(obj) => obj
    case None => throw new JudgeTypeFormatException(s"it is not appropreate judge type: ${name}")
  }

  def get(n: String): Option[JudgeType] = n match {
    case JUDGMENT.name => Option(JUDGMENT)
    case ORDER.name => Option(ORDER)
    case _ => None
  }
}

class JudgeTypeFormatException(mes: String = "") extends IllegalArgumentException(mes)
