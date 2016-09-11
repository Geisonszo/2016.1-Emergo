package unlv.erc.emergo.controller;

import android.content.Context;
import android.location.Location;

import unlv.erc.emergo.model.HealthUnit;

import java.util.ArrayList;



public class HealthUnitController {
  private static HealthUnitController instance = null;
  static ArrayList<HealthUnit> closestsUs = new ArrayList<HealthUnit>();

  public static HealthUnit createHealthUnit(Double latitude , Double longitude ,
                                              String nameHospital,
                                              String unityType , String addressNumber ,
                                              String district ,
                                              String state, String city) {
    HealthUnit healthUnit = new HealthUnit(latitude, longitude, nameHospital, unityType,
          addressNumber,
          district, state, city );
    return healthUnit;
  }

  public HealthUnitController(Context context) {

  }

  public static ArrayList<HealthUnit> getClosestsUs() {
    return closestsUs;
  }

  public static void setClosestsUs(HealthUnit healthUnit) {
    closestsUs.add(healthUnit);
  }

  public static void setDistanceBetweenUserAndUs(ArrayList<HealthUnit> closestsUs,
                                                   Location userLocation) {
    Location usLocation = new Location("");

    for (int aux = 0 ; aux < closestsUs.size() ; aux++) {

      usLocation.setLatitude(closestsUs.get(aux).getLatitude());
      usLocation.setLongitude(closestsUs.get(aux).getLongitude());
      closestsUs.get(aux).setDistance(userLocation.distanceTo(usLocation) / 1000);

    }
  }

  public static int selectClosestUs(ArrayList<HealthUnit> closestsUs , Location location) {

    double smaller = 99999;
    int position = 0;

    for (int aux = 0 ; aux < closestsUs.size() ; aux++) {

      if (closestsUs.get(aux).getDistance() < smaller) {

        smaller = closestsUs.get(aux).getDistance();
        position = aux;
      } else {
        /** nothing to do*/
      }
    }
    return position;
  }
}