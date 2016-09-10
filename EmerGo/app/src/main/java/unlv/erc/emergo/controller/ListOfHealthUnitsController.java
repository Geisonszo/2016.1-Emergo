/************************
 * Class name: ListOfHealthUnitsController (.java)
 *
 * Purpose: The purpose of this class is to show the health units in list format..
 ************************/

package unlv.erc.emergo.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unlv.erc.emergo.R;
import unlv.erc.emergo.model.HealthUnit;

public class ListOfHealthUnitsController extends Activity {

    private List<String> listHealthUnits = new ArrayList<>();
    private ListView listHealthUnitsListView;
    private int numberOfUsClicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_health_unit);

        listHealthUnits = get50closestUs(HealthUnitController.getClosestsUs());
        setuSsList((ListView) findViewById(R.id.list_of_hospitalUnit));
        listHealthUnitsListView.setAdapter(new ArrayAdapter<String>(this, R.layout.item,
                                                    R.id.hospitalUnitText,listHealthUnits));
        listHealthUnitsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position,long id) {

                numberOfUsClicked = parent.getPositionForView(view);
                openInformationUsScreen();
            }
        });
    }

    /**
     * The purpose of this method is based on the choice of the health unit that the User choose to
     * take that choice and open the InformationUsScreen class, which will be shown the information
     * of the health unit chosen.
     */

    public void openInformationUsScreen() {

        Intent informationScreen = new Intent();

        informationScreen.putExtra("position", numberOfUsClicked);
        informationScreen.setClass(ListOfHealthUnitsController.this,
                                    InformationUsScreenController.class);
        startActivity(informationScreen);
        finish();
    }

    /**
     * The purpose of this function is to list the 50 closest to the user's healthUnits.
     * @param closest
     * @return List<String> closest
     */
    public List<String> get50closestUs(ArrayList<HealthUnit> closest) {

        List<String> closestsUs = new ArrayList<String>();
        int numberOfUs = 0;
        for (numberOfUs = 0;numberOfUs < HealthUnitController.getClosestsUs().size();numberOfUs++) {

            closestsUs.add(closest.get(numberOfUs).getNameHospital());
        }
        return closestsUs;
    }

    /**
     * /**
     * Set the value of attribute listHealthUnitsListView.
     * @param listHealthUnitsListView
     */

    public void setuSsList(ListView listHealthUnitsListView) {

        this.listHealthUnitsListView= listHealthUnitsListView;
    }

    /**
     * When the "buttonGo" is pressed, it will exit the current class, opened the activity
     * "RouteActivity," only different from the method of "buttonRoute", will now be chosen the
     * route nearest the user.When the RouteActivity is finished, come in method "finish ()"
     * which will close the activity "RouteActivity".
     */

    public void goClicked(View map_screen) {

        final String ROUTETRACED = "Rota mais próxima traçada";
        Intent routeActivity = new Intent();

        Toast.makeText(this, ROUTETRACED, Toast.LENGTH_SHORT).show();

        routeActivity.setClass(ListOfHealthUnitsController.this, RouteActivity.class);
        routeActivity.putExtra("numeroUs", -1);
        startActivity(routeActivity);
        finish();

    }

    /**
     * Exchange of current activity and start activity "MapScreenController".
     * @param mapScreen
     */

    public void openMap(View mapScreen) {

        Intent mapActivity = new Intent();

        mapActivity.setClass(this, MapScreenController.class);
        startActivity(mapActivity);
        finish();
    }

    /**
     * Exchange of current activity and start activity "ConfigController".
     * @param viewConfig
     */

    public void openConfig(View viewConfig) {

        Intent config = new Intent();

        config.setClass(ListOfHealthUnitsController.this, ConfigController.class);
        startActivity(config);
    }

    /**
     * Exchange of current activity and start activity "SearchController".
     * @param mapScreen
     */

    public void open_search(View mapScreen) {

        Intent openSearch = new Intent();

        openSearch.setClass(this, SearchUsController.class);
        startActivity(openSearch);
        finish();
    }
}