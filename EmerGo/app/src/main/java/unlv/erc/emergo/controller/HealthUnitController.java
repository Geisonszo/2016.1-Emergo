package unlv.erc.emergo.controller;

import android.content.Context;
import android.location.Location;

import unlv.erc.emergo.model.HealthUnit;

import java.util.ArrayList;


public class HealthUnitController {

  static ArrayList<HealthUnit> closestHealthUnit = new ArrayList<HealthUnit>();

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
                                              String nameHealthUnit,
                                              String unityType, String addressNumber ,
                                              String district,
                                              String state, String city) {

    HealthUnit healthUnit = new HealthUnit(latitude, longitude, nameHealthUnit, unityType,
          addressNumber,
          district, state, city );
    return healthUnit;
  }

  public HealthUnitController(Context context) {

  }

  public static ArrayList<HealthUnit> getClosestHealthUnit() {

    return closestHealthUnit;
  }

  public static void setClosestHealthUnit(HealthUnit healthUnit) {

    closestHealthUnit.add(healthUnit);
  }

  /**
   * This method calculate the distance between the user and the health unit.
   * @param closestHealthUnit The health unit closest to user
   * @param userLocation The location of the user
   */

  public static void setDistanceBetweenUserAndUs(ArrayList<HealthUnit> closestHealthUnit,
                                                   Location userLocation) {

    Location usLocation = new Location("");

    for (int aux = 0 ; aux < closestHealthUnit.size() ; aux++) {

      usLocation.setLatitude(closestHealthUnit.get(aux).getLatitude());
      usLocation.setLongitude(closestHealthUnit.get(aux).getLongitude());
      closestHealthUnit.get(aux).setDistance(userLocation.distanceTo(usLocation) / 1000);

    }
  }

  /**
   * This method select the health unit closest to user.
   * @param closestHealthUnit Array of heath units
   * @param location
   * @return position Return the position of the closest health unit
   */

  public static int selectClosestUs(ArrayList<HealthUnit> closestHealthUnit, Location location) {

    double smaller = 99999;
    int position = 0;

    for (int aux = 0 ; aux < closestHealthUnit.size() ; aux++) {

      if (closestHealthUnit.get(aux).getDistance() < smaller) {

        smaller = closestHealthUnit.get(aux).getDistance();
        position = aux;
      } else {

        /** nothing to do*/
      }
    }
    return position;
  }
}