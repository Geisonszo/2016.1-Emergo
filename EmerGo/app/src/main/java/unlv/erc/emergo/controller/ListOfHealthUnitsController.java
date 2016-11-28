/************************
 * Class name: ListOfHealthUnitsController (.java)
 *
 * Purpose: The purpose of this class is to show the health units in list format..
 ************************/

package unlv.erc.emergo.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import unlv.erc.emergo.R;
import unlv.erc.emergo.model.HealthUnit;

import java.util.ArrayList;
import java.util.List;

public class ListOfHealthUnitsController extends Activity {


  private static final String ROUTE_TRACED = "Rota mais próxima traçada";

  private static final String TAG = "ListOfHealthUnits";

  // The Bundle data value of numeroUs .
  private static final int VALUE_LOWER_CLOSE = -1;

  // The name of the extra data.
  private static final String POSITION = "position";

  // The Bundle data value.
  private static final String NUMBER_HEALTH_UNIT = "numeroUs";

  private List<String> listHealthUnits = new ArrayList<>();
  private ListView listHealthUnitsListView;
  private int numberOfHealthUnitsClicked = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    Log.d(TAG, "onCreate() called with: Bundle = [" + savedInstanceState + "]");

    super.onCreate(savedInstanceState);
    setContentView(R.layout.list_of_health_unit);

    listHealthUnits = get50closestUs(HealthUnitController.getClosestHealthUnit());
    setuSsList((ListView) findViewById(R.id.list_of_hospitalUnit));
    listHealthUnitsListView.setAdapter(new ArrayAdapter<String>(this, R.layout.item,
                                                        R.id.hospitalUnitText,listHealthUnits));
    listHealthUnitsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

          @Override
          public void onItemClick(AdapterView<?> parent, View view,int position,long id) {

            numberOfHealthUnitsClicked = parent.getPositionForView(view);
            openInformationHealthUnitScreen();
          }
        });
  }

  /**
    * The purpose of this method is based on the choice of the health unit that the User choose to
    * take that choice and open the InformationUsScreen class, which will be shown the information
    * of the health unit chosen.
    *
   */

  public void openInformationHealthUnitScreen() {

    Intent informationScreen = new Intent();

    informationScreen.putExtra(POSITION, numberOfHealthUnitsClicked);
    informationScreen.setClass(ListOfHealthUnitsController.this,
                                InformationHealthUnitScreenController.class);
    startActivity(informationScreen);
    finish();
  }

  /**
    * The purpose of this function is to list the 50 closest to the user's healthUnits.
    * @param closest
    *
   */

  public List<String> get50closestUs(ArrayList<HealthUnit> closest) {

    Log.d(TAG, "get50closestUs() called with: ArrayList<HealthUnit> = [" + closest + "]");

    List<String> closestHealthUnits = new ArrayList<String>();

    /**
     * Set the array closestHealthUnits with the name of all the closest health units.
     *
     */

    int numberOfHealthUnits = 0;
    for (numberOfHealthUnits = 0;numberOfHealthUnits < HealthUnitController.getClosestHealthUnit()
        .size(); numberOfHealthUnits++) {

      closestHealthUnits.add(closest.get(numberOfHealthUnits).getNameHospital());
    }
    return closestHealthUnits;
  }

  /**
    * Set the value of attribute listHealthUnitsListView.
    * @param listHealthUnitsListView lists of health units.
    *
   */

  public void setuSsList(ListView listHealthUnitsListView) {

    Log.d(TAG, "setuSsList() called with: ListView = [" + listHealthUnitsListView + "]");

    this.listHealthUnitsListView = listHealthUnitsListView;
  }

  /**
    * When the "buttonGo" is pressed, it will exit the current class, opened the activity
    * "RouteActivity," only different from the method of "buttonRoute", will now be chosen the
    * route nearest the user.When the RouteActivity is finished, come in method "finish ()"
    * which will close the activity "RouteActivity".
  */

  public void goClicked(View mapScreen) {

    Log.d(TAG, "goClicked() called with: View = [" + mapScreen + "]");

    Intent routeActivity = new Intent();

    Toast.makeText(this, ROUTE_TRACED, Toast.LENGTH_SHORT).show();
    routeActivity.setClass(ListOfHealthUnitsController.this, RouteActivity.class);
    routeActivity.putExtra(NUMBER_HEALTH_UNIT, VALUE_LOWER_CLOSE);
    startActivity(routeActivity);
    finish();
  }

  /**
    * Exchange of current activity and start activity "MapScreenController".
    * @param mapScreen View of map.
    *
   */

  public void openMap(View mapScreen) {

    Log.d(TAG, "openMap() called with: View = [" + mapScreen + "]");

    Intent mapActivity = new Intent();

    mapActivity.setClass(this, MapScreenController.class);
    startActivity(mapActivity);
    finish();
  }

  /**
    * Exchange of current activity and start activity "SettingsController".
    * @param viewConfig View of config.
    *
   */

  public void openSettings(View viewSettings) {

    Log.d(TAG, "openSettings() called with: View = [" + viewSettings + "]");

    Intent config = new Intent();

    config.setClass(ListOfHealthUnitsController.this, SettingsController.class);
    startActivity(config);
  }

  /**
    * Exchange of current activity and start activity "SearchController".
    * @param mapScreen View of mapScreen.
    *
   */

  public void openSearch(View mapScreen) {

    Log.d(TAG, "openSearch() called with: View = [" + mapScreen + "]");

    Intent openSearch = new Intent();

    openSearch.setClass(this, SearchHealthUnitActivity.class);
    startActivity(openSearch);
    finish();
  }
}