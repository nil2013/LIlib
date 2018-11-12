package me.nsmr
package lilib
package core

object Court {
  lazy val LEVEL = Array("最高", "高等", "地方", "家庭", "簡易")

  def apply(p: String, l: Int, b: String): Court = SimpleCourt(p, l, b)
}

trait Court {
  import Court._

  def place: String
  def level: Int
  def branch: String

  override def toString(): String = s"$place${LEVEL(level)}裁判所$branch"

  def simpleName: String = s"$place${LEVEL(level)}裁判所"
}
