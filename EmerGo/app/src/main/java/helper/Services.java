/************************
 * Class name: Services (.java)
 *
 * Purpose: The purpose of this class is to serve as a class which has required methods when
 * requested.
 ************************/

package helper;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;

import unlv.erc.emergo.model.HealthUnit;

import java.util.ArrayList;

public class Services extends Activity {

  /**
    * Add "marker" on the map according to the amount of health units registered in the database.
    * @param map GoogleMap.
    * @param healthUnits ArrayList.
    *
   */

  public void setMarkersOnMap(GoogleMap map, ArrayList<HealthUnit> healthUnits) {

    for (int markersQuantity = 0; markersQuantity < healthUnits.size(); markersQuantity++) {

      map.addMarker(new MarkerOptions()
                    .position(new LatLng(healthUnits.get(markersQuantity).getLatitude(),
                                         healthUnits.get(markersQuantity).getLongitude()))
                    .title(healthUnits.get(markersQuantity).getNameHospital() + "")
                    .snippet(healthUnits.get(markersQuantity).getUnitType()));
    }
  }
}
