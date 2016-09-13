package unlv.erc.emergo.controller;

import android.content.Context;
import android.location.Location;

import unlv.erc.emergo.model.HealthUnit;

import java.util.ArrayList;



public class HealthUnitController {

  private static HealthUnitController instance = null;
  static ArrayList<HealthUnit> closestsUs = new ArrayList<HealthUnit>();

  /**
   * This method create an instance of Health Unit.
   * @param latitude latitude coordinate of health unit
   * @param longitude longitude coordinate of health unit
   * @param nameHospital The name of health unit
   * @param unityType type of health unit
   * @param addressNumber Address of health unit
   * @param district health unit district
   * @param state health unit state
   * @param city health unit city
   * @return healthUnit
   */

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

  /**
   * This method calculate the distance between the user and the health unit.
   * @param closestsUs The health unit closest to user
   * @param userLocation The location of the user
   */

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