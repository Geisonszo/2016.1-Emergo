/*
 * Class: DirectionsJSONParser(.java)
 *
 * Porpouse: This class contais the method related to the JSON Parse conversions.
 */

package helper;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectionsJSONParser {

  /*
   * This method parses the JSON Objects to an list os Objects
   * @param jObject JSON Objects
   */

  public List<List<HashMap<String,String>>> parse(JSONObject jObject) {

    List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>() ;
    JSONArray jRoutes = null;
    JSONArray jLegs = null;
    JSONArray jSteps = null;

    try {

      jRoutes = jObject.getJSONArray("routes");

      for (int i = 0; i < jRoutes.length(); i++) {
        jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
        List path = new ArrayList<HashMap<String, String>>();

        for (int j = 0; j < jLegs.length(); j++) {
          jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

          for (int k = 0 ; k < jSteps.length(); k++) {
            String polyline = "";
            polyline = (String)((JSONObject)((JSONObject)jSteps.get(k))
                        .get("polyline")).get("points");
            List<LatLng> list = decodePoly(polyline);

            for (int l = 0; l < list.size(); l++) {

              HashMap<String, String> hm = new HashMap<String, String>();
              hm.put("lat", Double.toString(list.get(l).latitude) );
              hm.put("lng", Double.toString(list.get(l).longitude) );
              path.add(hm);
            }
          }
          routes.add(path);
        }
      }

    } catch (JSONException jsonException) {

      jsonException.printStackTrace();
    }

    return routes;
  }

  private List<LatLng> decodePoly(String encoded) {

    List<LatLng> poly = new ArrayList<LatLng>();
    int index = 0;
    int len = encoded.length();
    int lat = 0;
    int lng = 0;
    int dlat = 0;
    int dlng = 0;

    while (index < len) {

      int adress;
      int shift = 0;
      int result = 0;

      do {

        adress = encoded.charAt(index) - 63;
        index = index + 1;
        result |= (adress & 0x1f) << shift;
        shift = shift + 5;
      } while (adress >= 0x20);

      if ((result & 1) != 0) {

        dlat = ~(result >> 1);

      } else {

        dlat = (result >> 1);
      }

      lat = lat + dlat;

      shift = 0;
      result = 0;

      do {

        adress = encoded.charAt(index) - 63;
        index = index + 1;
        result |= (adress & 0x1f) << shift;
        shift = shift + 5;
      }
      while (adress >= 0x20);

      if ((result & 1) != 0) {

        dlng = ~(result >> 1);

      } else {

        dlng = (result >> 1);
      }

      lng = lng + dlng;

      LatLng latLng = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
      poly.add(latLng);
    }
    return poly;
  }
}
