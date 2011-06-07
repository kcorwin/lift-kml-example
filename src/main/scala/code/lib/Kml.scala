package code
package lib

import model._

import net.liftweb._
import common._
import http._
import rest._
import json._
import scala.xml._

// fake record object...
case class Location( val lat: Double, val lon: Double )

/**
 * A simple example of a REST style interface
 * using the basic Lift tools
 */
object Kml extends RestHelper {
  val locations = Location(13.3966495,52.531336)::Location(13.415618082153287,52.52773314397097)::Location(13.401026865112271,52.529717362117665)::Location(13.413322111236539,52.54088996936841)::Location(13.404846330749479,52.544883225659675)::Location(13.403867077832047,52.534678915187726)::Nil

  val coords = ( "" /: locations ) { (a,e) => a+"%s,%s,0\n".format(e.lon,e.lat) }

  serve {
    case "api" :: "location.kml" :: Nil Get _ =>
      val resp = <kml xmlns="http://earth.google.com/kml/2.1">
          <Document>
            <name>Places You've Gone</name>
            <description>These are some places you've been</description>
            <Style id="pathLine">
              <LineStyle>
                <color>ffff0000</color>
                <width>4</width>
              </LineStyle>
            </Style>
            <Placemark>
              <name>Your path</name>
              <styleUrl>#pathLine</styleUrl>
              <LineString>
                <altitudeMode>relative</altitudeMode>
                <coordinates>{coords}</coordinates>
              </LineString>
            </Placemark>
          </Document>
        </kml>
      println( resp )
      resp
  }
}
