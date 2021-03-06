package me.nsmr
package lilib
package core

import java.util.Date

case class SimpleCaseNumber(
                             override val year: JapaneseYear,
                             override val mark: CaseMark,
                             override val index: Int
) extends CaseNumber
