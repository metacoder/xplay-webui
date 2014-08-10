package utils

import scala.math.BigDecimal.RoundingMode

trait BigDecimalRounding {
  /**
   * round a big decimal, mode is HALF_UP
   * @param bd the big decimal to round
   * @param scale optional the scale, default is 1
   * @return the rounded big decimal
   */
  def r(bd: BigDecimal, scale: Int = 1) = bd.setScale(scale, RoundingMode.HALF_UP)
}
