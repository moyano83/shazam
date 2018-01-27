package com.shazam

import org.scalatest.FlatSpec
import com.shazam.util.AppConstants.AllowedCommands._

class ChartAppTest extends FlatSpec{

  it should "verify the parseArgs function with different inputs" in {
    assert(ChartApp.parseArgs(Array("chart", "4")) == Some(chart, 4))
    assert(ChartApp.parseArgs(Array("CHaRt", "10")) == Some(chart, 10))
    assert(ChartApp.parseArgs(Array("statE_cHart", "6")) == Some(state_chart, 6))
    assert(ChartApp.parseArgs(Array("chart", "-4")) == None)
    assert(ChartApp.parseArgs(Array("Some", "4")) == None)
    assert(ChartApp.parseArgs(Array("chart", "four")) == None)
    assert(ChartApp.parseArgs(Array("state_chart", "200")) == Some(state_chart, 200))
    assert(ChartApp.parseArgs(Array("state_chart", "0")) == None)
  }

}
