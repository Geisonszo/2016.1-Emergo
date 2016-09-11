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

import java.util.ArrayList;

import unlv.erc.emergo.model.HealthUnit;

public class Services {

    /**
     * Add "marker" on the map according to the amount of health units registered in the database.
     * @param map
     * @param uSs
     */

    public void setMarkersOnMap(GoogleMap map,ArrayList<HealthUnit> uSs) {

        for(int markersQuantity = 0; markersQuantity < uSs.size(); markersQuantity++) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(uSs.get(markersQuantity).getLatitude()
                            ,uSs.get(markersQuantity).getLongitude()))
                    .title(uSs.get(markersQuantity).getNameHospital() + "")
                    .snippet(uSs.get(markersQuantity).getUnitType()));
        }
    }
}
