package cryptograms

import scala.util.Random

object FillCode {

  def isSelfCode(code: String): Boolean = {
    var isSelfCode = false
    for (ch <- code) {
      if (ch - 'A' == code.indexOf(ch))
        isSelfCode = true
    }
    isSelfCode
  }

  def isFull(code: String): Boolean = {
    var isFull = true
    for (ch <- code) {
      if (!ch.isLetter) isFull = false
    }
    isFull
  }

  def fillCode(code: String): String = {
    if (isFull(code)) code
    else {
      var codeResult = code.toArray
      var all = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
      var left = all.filterNot(ch => code.contains(ch)).toList
      left = Random.shuffle(left)

      var k = 0
      for (i <- 0 to code.length - 1 if !codeResult(i).isLetter) {
        codeResult(i) = left(k)
        k += 1
      }

      var result = codeResult.mkString
      if (isSelfCode(result))
        fillCode(result)
      else result
    }
  }

}