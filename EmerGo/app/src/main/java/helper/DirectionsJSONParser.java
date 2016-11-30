/*
 * Class: DirectionsJSONParser(.java)
 *
 * Porpouse: This class contais the method related to the JSON Parse conversions.
 */

package helper;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectionsJSONParser {

  private static final String TAG = "DirectionsJSONParser";

  /*
   * This method parses the JSON Objects to an list os Objects
   * @param jObject JSON Objects
   */

  public List<List<HashMap<String,String>>> parse(JSONObject jObject) {

    Log.d(TAG, "parse() called with: jObject = [" + jObject + "]");

    assert jObject != null : "jObject can't be null";

    List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>() ;
    JSONArray jRoutes = null;
    JSONArray jLegs = null;
    JSONArray jSteps = null;

    try {

      jRoutes = jObject.getJSONArray("routes");

      try {

        for (int i = 0; i < jRoutes.length(); i++) {

          jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
          List path = new ArrayList<HashMap<String, String>>();

          // Traversing all legs.
          for (int j = 0; j < jLegs.length(); j++) {

            jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");
            assert jSteps != null : "jSteps can't be null";

            // Traversing all steps.
            for (int k = 0 ; k < jSteps.length(); k++) {

              String polyline = "";
              polyline = (String)((JSONObject)((JSONObject)jSteps.get(k))
                  .get("polyline")).get("points");
              assert polyline != null : "polyline can't be null";

              List<LatLng> list = decodePoly(polyline);
              assert list != null : "list can't be null";

              // Traversing all points.
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
      } catch (NullPointerException exception) {

        exception.printStackTrace();
      }

    } catch (JSONException jsonException) {

      Log.d(TAG, "parse() ", jsonException);      
      jsonException.printStackTrace();
    }

    Log.d(TAG, "parse() returned: " + routes);
    return routes;
  }

  /**
   * Method to decode polyline points.
   * @param encoded String to be decoded
   * @return poly String decoded
   */

  private List<LatLng> decodePoly(String encoded) {

    Log.d(TAG, "decodePoly() called with: encoded = [" + encoded + "]");

    assert encoded != null : "encoded can't be null";

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
    Log.d(TAG, "decodePoly() returned: " + poly);
    return poly;
  }

  @Override
  protected void finalize() throws Throwable {

    Log.d(TAG, "finalize: " + getClass());
    super.finalize();
  }
}
