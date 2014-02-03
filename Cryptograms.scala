/**
 * Cryptogram Solver
 * @author Mark Mercurio 
 * @author Wenyi Wu
 * @version 2.0
 * @date October 2013.
 */
package cryptograms

import scala.io.Source, scala.io.Source._, scala.swing.FileChooser


  /** 
   *  Program to solve a cryptogram input by the user.
   *  @author Mark Mercurio
   *  @author Wenyi Wu
   */  
object Cryptograms {


  def main(args: Array[String]): Unit = {    
    //Create small dictionary
    val smallDictionary = getSmallDictionary()
    
    //Create Large Dictionary
    val largeDictionary = getLargeDictionary()
    
    //Get a Quote from User
    val cryptogram = {
      println("Tell me your cryptogram please")
      readLine()
    }.toUpperCase
    
    //Make a object using the entered word
    val quote = new Quote(cryptogram)
    
    //Attempt to discover the code with the small dictionary
    var code = Decoder.bestGuess(quote, "**************************", smallDictionary, largeDictionary)
    
    //Fill Unused Letter of Code
    code = FillCode.fillCode(code)
    
    //Print the discovered code and the decoded cryptogram.
    println("Final Code: " + code)
    println("Solved Cryptogram: " + Decoder.decode(cryptogram, code))
  }
  
    /** 
   *  Returns a list of words from the large (100k) dictionary
   *  file that has one word per line 
   */
  def getLargeDictionary(): Dictionary = {
    val file = "Dictionary100k.txt"
    val stream = scala.io.Source.fromFile(file)
    val words = stream.getLines.toList
    stream.close
    
    new Dictionary(for(word <- words) yield word.toUpperCase)
  }

  /** 
   *  Returns a list of words from the small (6k) dictionary
   *  file that has one word per line 
   */  
  def getSmallDictionary(): Dictionary = {   
    val file = "Dictionary6k.txt"
    val stream = scala.io.Source.fromFile(file)
    val words = stream.getLines.toList
    stream.close

    new Dictionary(for(word <- words) yield word.toUpperCase)
  }

}