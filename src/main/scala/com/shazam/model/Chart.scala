package com.shazam.model

import com.shazam.util.AppConstants

case class Chart(position:Int, title:String, artist:String){

  override def toString: String = Array(position, title, artist).mkString(AppConstants.printSeparator)
}
