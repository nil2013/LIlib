package me.nsmr
package lilib
package core

case class SimpleCourt(
  override val place  : String,
  override val level  : Int,
  override val branch : String
) extends Court
