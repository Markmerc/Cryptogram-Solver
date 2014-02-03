package cryptograms

import scala.io.Source, scala.io.Source._, scala.swing.FileChooser

class Dictionary (val listOfWords: List[String]) {  
  val length = listOfWords.length

  /**
   * Determines if a possible encrypted/decrypted word pair would cause a
   * self-coding. (ex: A coding for the letter A)
   */
  def isSelfCoding(s: String, t: String): Boolean = {
    require(!s.isEmpty && !t.isEmpty)
    require(s.length == t.length)

    var selfCoding = false
    for (i <- 0 to s.length - 1) {
      if (s(i) == t(i) && (s(i) != '\'' && s(i) != '-')) selfCoding = true
    }
    selfCoding
  }

  /**
   * Determines if a possible encrypted/decrypted word pair have the same
   * pattern. (ex: BOOK and XYYZ both have the double letters in the middle)
   */
  def doesPatternMatch(s: String, t: String): Boolean = {
    require(!s.isEmpty && !t.isEmpty)
    var samePattern = true

    if (s.length != t.length) samePattern = false
    else {
      for (i <- 1 to s.length - 1; j <- 0 to i - 1) {
        if ((s(j) == ''') != (t(j) == ''')) samePattern = false
        if ((s(j) == '-') != (t(j) == '-')) samePattern = false
        if ((s(i) == s(j)) != (t(i) == t(j))) samePattern = false
      }
    }
    samePattern
  }

  /**
   * Determines if a possible word is valid given the the current
   * known code
   */
  def isValidWithCode(t: String, s: String, code: String): Boolean = {
    var valid = true
    var encodedT = encode(t.toUpperCase, code)
    var decodedS = decode(s.toUpperCase, code)

    for (i <- 0 to decodedS.length - 1) {
      if (decodedS(i).isLetter && decodedS(i) != t(i)) valid = false
      else if (encodedT(i).isLetter && encodedT(i) != s(i)) valid = false
    }
    valid
  }

  /**
   * Takes a string and encodes it using the code string provided.
   * In the plaintext string, all 'A's are replaced with the first letter of the code string.
   */
  def encode(plainText: String, c: String): String = {
    val code = c.toUpperCase
    // A = 65, Z = 90
    var text = plainText.toUpperCase
    text map ((ch: Char) => {
      if (ch.isLetter) code(ch - 'A').toChar
      else ch
    })
  }

  /**
   * Decodes an encoded text given the encoded text and the code that was used.
   */
  def decode(encodedText: String, c: String): String = {
    var text = encodedText.toUpperCase
    val code = c.toUpperCase
    text map ((ch: Char) => {
      if (ch.isLetter && code.contains(ch)) (code.indexOf(ch) + 'A').toChar
      else if (ch.isLetter) '*'
      else ch
    })
  }

  /**
   * Searches the dictionary for all words that could match
   * a possible encrypted word by checking patterns, and assuming
   * no self-coding. (ex: QXYZ may encode DOGS, CATS, PENS, etc.)
   * Returns a List of Strings
   */
  def search(s: String, code: String): List[String] = {
    for (
      word <- listOfWords if (doesPatternMatch(word, s) && !isSelfCoding(word, s) &&
        isValidWithCode(word, s, code))
    ) yield word
  }
  
}