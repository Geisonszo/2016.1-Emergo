/*
 * Class: HealthUnitController (.java)
 *
 * Porpouse: The purpose of this class is select and set the distance between user and heath units.
 */

package unlv.erc.emergo.controller;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import unlv.erc.emergo.model.HealthUnit;

import java.util.ArrayList;


public class HealthUnitController {

    static ArrayList<HealthUnit> closestHealthUnit = new ArrayList<HealthUnit>();

    public HealthUnitController() {

    }

    public static ArrayList<HealthUnit> getClosestHealthUnit() {

        return closestHealthUnit;
    }

    public static void setClosestHealthUnit(HealthUnit healthUnit) {
        try {
            closestHealthUnit.add(healthUnit);
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This method calculate the distance between the user and the health unit.
     *
     * @param closestHealthUnit The health unit closest to user
     * @param userLocation      The location of the user
     */

    protected static void setDistanceBetweenUserAndUs(ArrayList<HealthUnit> closestHealthUnit,
                                                      Location userLocation) {

        Location healthUnitLocation = new Location("");

        try {

            for (int aux = 0; aux < closestHealthUnit.size(); aux++) {

                healthUnitLocation.setLatitude(closestHealthUnit.get(aux).getLatitude());
                healthUnitLocation.setLongitude(closestHealthUnit.get(aux).getLongitude());
                closestHealthUnit.get(aux).setDistance(userLocation.distanceTo(healthUnitLocation) / 1000);

            }
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This method select the health unit closest to user.
     *
     * @param closestHealthUnit Array of heath units
     * @param location          the health unity location
     * @return position Return the position of the closest health unit
     */

    protected static int selectClosestUs(ArrayList<HealthUnit> closestHealthUnit, Location location) {

        double smaller = 99999;
        int position = 0;

        try {
            for (int aux = 0; aux < closestHealthUnit.size(); aux++) {

                if (closestHealthUnit.get(aux).getDistance() < smaller) {

                    smaller = closestHealthUnit.get(aux).getDistance();
                    position = aux;
                } else {

                    /** nothing to do*/
                }
            }
            return position;
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

}