package cryptograms

case class Quote (val phrase: String) {

  //Removes all punctuation except for - and '  
  var temp = phrase filter (ch => ch.isLetter || ch == ''' 
    || ch == '-' || ch == ' ')
  
  //Make a list of words, and order it by length
  val quote = {
    (temp.toUpperCase.split(" ").filterNot(x => x.isEmpty)).sortBy(x => (x.length, x.head)).reverse.toList
  }
  
  val length = quote.length
  
}