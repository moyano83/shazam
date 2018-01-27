package com.shazam.model

import com.shazam.util.AppConstants

case class StateChart(position:Long, state:String, title:String, artist:String){

  override def toString: String = Array(position, state, title, artist).mkString(AppConstants.printSeparator)
}
