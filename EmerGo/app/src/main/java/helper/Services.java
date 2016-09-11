package helper;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import unlv.erc.emergo.model.HealthUnit;

public class Services {

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
