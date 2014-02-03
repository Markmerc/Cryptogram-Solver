package cryptograms
/**
 * An objects which can decode, encode, or decipher a code.
 */
object Decoder {

  var bestSolution = new PossibleSolution("**************************", -1)
  var decoded = false

  //initialize a dummy quote
  var quote = new Quote("a")

  /**
   * Finds the best potential code for a given encrypted string.
   */
  def bestGuess(s: Quote, code: String, smallDictionary: Dictionary, largeDictionary: Dictionary): String = {
    
    if(!s.quote.isEmpty){
    quote = s
    decipher(0, code, smallDictionary)
    
    if(!decoded){
      println("Small Dictionary Failed, Trying Larger Dictionary")
      decipher(0, code, largeDictionary)
    } 
    bestSolution.code
    }
    else bestSolution.code
  }

  /**
   * Deciphers an encoded string by searching possible solutions to each encoded word,
   * and comparing those solutions to the current code.
   */
  def decipher(level: Int, code: String, dictionary: Dictionary): Unit = {
    var count = 0
    val encodedWord = quote.quote(level)
    

    //Lists all possible word choices for a given encoded word
    val possibleChoices = dictionary.search(encodedWord, code)
    
    //recursively checks each word of the encoded string to words in the dictionary 
    while (!decoded && count < possibleChoices.length) {
      
      //updates code based based on which potential solution word
      val updatedCode = updateCode(quote.quote(level), possibleChoices(count), code)

      //If on the last word, pick the most frequent and use the code as the solution
      if (level == quote.length - 1) {
        bestSolution = new PossibleSolution(updatedCode, level)
        decoded = true
      } 
      //Checks if this solution is better than the previous solution.
      else if (level > bestSolution.level) {
        bestSolution = new PossibleSolution(updatedCode, level)
        decipher(level + 1, updatedCode, dictionary)
      } 
      //Goes to the next word (level)
      else if (level + 1 < quote.length) {
        decipher(level + 1, updatedCode, dictionary)
      }
      count += 1
    }
  }

  /**
   * Takes a given code and updated it with respect to a new potential encoded/decoded word pair
   */
  def updateCode(encodedWord: String,
    decodedWord: String,
    code: String): String = {
    
    var updatedCode = code.toArray

    for (ch <- decodedWord if (ch.isLetter)) {
      updatedCode(ch - 'A') = encodedWord(decodedWord.indexOf(ch))
    }
    updatedCode.mkString
  }

  /**
   * Takes a plain text string, and a code and returns the encrypted plain text
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
   * Takes an encoded string, and the code that was used to encode it
   * and returns the decoded string
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
}