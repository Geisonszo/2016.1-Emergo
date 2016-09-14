/************************
 * Class name: Services (.java)
 *
 * Purpose: The purpose of this class is to serve as a class which has required methods when
 * requested.
 ************************/

package helper;

import android.app.Activity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import unlv.erc.emergo.model.HealthUnit;

public class Services extends Activity {

  /**
    * Add "marker" on the map according to the amount of health units registered in the database.
    * @param map GoogleMap.
    * @param uss ArrayList.
    *
   */

  public void setMarkersOnMap(GoogleMap map,ArrayList<HealthUnit> uss) {

    for (int markersQuantity = 0; markersQuantity < uss.size(); markersQuantity++) {
      map.addMarker(new MarkerOptions()
                    .position(new LatLng(uss.get(markersQuantity).getLatitude(),
                                         uss.get(markersQuantity).getLongitude()))
                    .title(uss.get(markersQuantity).getNameHospital() + "")
                    .snippet(uss.get(markersQuantity).getUnitType()));
    }
  }
}
