package me.nsmr
package lilib
package core

import java.util.Date

case class SimpleCaseNumber(
  override val year: CaseYear,
  override val mark: String,
  override val index: Int
) extends CaseNumber
