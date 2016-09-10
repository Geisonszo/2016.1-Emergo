/************************
 * Class name: InformationUsScreenController (.java)
 *
 * Purpose: The purpose of this class is to show the information of the healthunits.
 ************************/

package unlv.erc.emergo.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unlv.erc.emergo.R;
import unlv.erc.emergo.model.HealthUnit;

public class InformationUsScreenController extends Activity implements View.OnClickListener {

    private List <String> listOfInformations = new ArrayList<String>();
    private ListView healthUnitInfo;
    private Button buttonRoute;
    private ImageView buttonGo;
    private Intent receive;
    private int numberUsSelected = 0;
    private String padding = "";
    private String titleHealthUnit = "";
    private String nameHealthUnit = "";
    private String unitType = "";
    private String state = "";
    private String city = "";
    private String district = "";
    private String addressNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_us_screen);

        setReceive(getIntent());
        setNumberUsSelected(receive.getIntExtra("position" , 0));
        buttonRoute = (Button) findViewById(R.id.botaoRota);
        buttonRoute.setOnClickListener(this);
        buttonGo = (ImageView) findViewById(R.id.buttonGo);
        buttonGo.setOnClickListener(this);

        setHealthUnitInfo((ListView) findViewById(R.id.hospInformation));
        setInformation(HealthUnitController.getClosestsUs().get(numberUsSelected));

        addInformationToList();
    }

    /**
     * The purpose of this function is to be in accordance with the choice of User buttons, to open
     * the RouteActivity class but with two options:
     * If he hit the "buttonRoute" to open the map showing the nearest health facilities.
     * If he hit the "buttonGo" to open the map tracing the route to the nearest health facility,
     * and call the samu and send a message pros emergency contacts.
     * @param viewOnClick
     */

    @Override
    public void onClick(View viewOnClick) {

        /**
         * When the "buttonRoute" is pressed, it will exit the current class and opened the activity and
         * "RouteActivity" class. When RouteActivity is finished, come in method "finish ()" which will
         * close the activity "RouteActivity".
         */

        if(viewOnClick.getId() == R.id.botaoRota) {

            Intent route = new Intent();

            route.setClass(InformationUsScreenController.this , RouteActivity.class);
            route.putExtra("numeroUs" , receive.getIntExtra("position" , 0));
            startActivity(route);
            finish();
        }

        /**
         * When the "buttonGo" is pressed, it will exit the current class, opened the activity
         * "RouteActivity," only different from the method of "buttonRoute", will now be chosen the
         * route nearest the user.When the RouteActivity is finished, come in method "finish ()"
         * which will close the activity "RouteActivity".
         */

        if(viewOnClick.getId() == R.id.buttonGo) {

            final String ROUTETRACED = "Rota mais próxima traçada";
            Intent routeActivity = new Intent();

            Toast.makeText(this, ROUTETRACED , Toast.LENGTH_SHORT).show();
            routeActivity.setClass(InformationUsScreenController.this , RouteActivity.class);
            routeActivity.putExtra("numeroUs" , -1);
            startActivity(routeActivity);
            finish();
        }
    }

    /**
     * Takes the arraylist data that is linked to the related database healthUnits.
     * @param healthUnit
     */

    public void setInformation(HealthUnit healthUnit) {

        setPadding("\n");
        setTitleHealthUnit("        Informações da Unidade de Saúde");
        setNameHealthUnit("  Nome: " + healthUnit.getNameHospital());
        setUnitType("  Tipo de atendimento: " + healthUnit.getUnitType());
        setState("  UF: " + healthUnit.getState());
        setCity("  Cidade: " + healthUnit.getCity());
        setDistrict("  Bairro: " + healthUnit.getDistrict());
        setAddressNumber("  Cep: " + healthUnit.getAddressNumber());
    }

    /**
     * Takes the data from the database, saved in a arraylist and calls another function, where the
     * saved data in ArrayLists will be shown in a ArrayAdapter.
     */

    public void  addInformationToList() {

        listOfInformations.add(padding);
        listOfInformations.add(titleHealthUnit);
        listOfInformations.add(nameHealthUnit);
        listOfInformations.add(unitType);
        listOfInformations.add(state);
        listOfInformations.add(city);
        listOfInformations.add(district);
        listOfInformations.add(addressNumber);
        showInformationOnScreen();
    }

    /**
     * Shows the information saved in the arraylist and put in a ArrayAdapter.
     */

    public void showInformationOnScreen() {

        ArrayAdapter<String> adapter = new ArrayAdapter <String> (this,
                                                                android.R.layout.simple_list_item_1,
                                                                listOfInformations);

        healthUnitInfo.setAdapter(adapter);
    }

    /**
     * Exchange of current activity and start activity "SearchController".
     * @param mapScreen
     */

    public void open_search(View mapScreen) {

        Intent openSearch = new Intent();

        openSearch.setClass(this,SearchUsController.class);
        startActivity(openSearch);
    }

    /**
     * Exchange of current activity and start activity "ListOfHealthUnitsController".
     * @param map_screen
     */

    public void listMapsImageClicked(View map_screen) {

        Intent listOfHealth = new Intent();

        listOfHealth.setClass(this,ListOfHealthUnitsController.class);
        startActivity(listOfHealth);
        finish();
    }

    /**
     * Exchange of current activity and start activity "MapScreenController".
     * @param mapScreen
     */

    public void openMap(View mapScreen) {

        Intent mapActivity = new Intent();

        mapActivity.setClass(this,MapScreenController.class);
        startActivity(mapActivity);
        finish();
    }

    /**
     * Get the value of attribute listOfInformations.
     * @return listofInformations: String
     */

    public List<String> getListOfInformations() {

        return listOfInformations;
    }

    /**
     * Set the value of attribute listOfInformations.
     * @param listOfInformations
     */

    public void setListOfInformations(List<String> listOfInformations) {

        this.listOfInformations = listOfInformations;
    }

    /**
     * Get the value of attribute healthUnitInfo.
     * @return healthUnitInfo: ListView
     */

    public ListView getHealthUnitInfo() {

        return healthUnitInfo;
    }

    /**
     * Set the value of attribute healthUnitInfo.
     * @param healthUnitInfo
     */

    public void setHealthUnitInfo(ListView healthUnitInfo) {

        this.healthUnitInfo = healthUnitInfo;
    }

    /**
     * Get the value of attribute receive.
     * @return healthUnitInfo: Intent
     */

    public Intent getReceive() {

        return receive;
    }

    /**
     * Set the value of attribute receive.
     * @param receive
     */

    public void setReceive(Intent receive) {

        this.receive = receive;
    }

    /**
     * Get the value of attribute buttonRoute.
     * @return buttonRoute: Button
     */

    public Button getButtonRoute() {

        return buttonRoute;
    }

    /**
     * Set the value of attribute buttonRoute.
     * @param buttonRoute
     */

    public void setButtonRoute(Button buttonRoute) {

        this.buttonRoute = buttonRoute;
    }

    /**
     * Get the value of attribute of image buttonGo.
     * @return buttonGo: ImageView
     */

    public ImageView getButtonGo() {

        return buttonGo;
    }

    /**
     * Set the value of attribute of image buttonGo.
     * @param buttonGo
     */

    public void setButtonGo(ImageView buttonGo) {

        this.buttonGo = buttonGo;
    }

    /**
     * Get the value of attribute numberUsSelected.
     * @return numberUsSelected: int
     */

    public int getNumberUsSelected() {

        return numberUsSelected;
    }

    /**
     * Set the value of attribute numberUsSelected.
     * @param numberUsSelected
     */

    public void setNumberUsSelected(int numberUsSelected) {

        this.numberUsSelected = numberUsSelected;
    }

    /**
     * Get the value of attribute padding.
     * @return padding: String
     */

    public String getPadding() {

        return padding;
    }

    /**
     * Set the value of attribute padding.
     * @param padding
     */

    public void setPadding(String padding) {

        this.padding = padding;
    }

    /**
     * Get the value of attribute titleHealthUnit.
     * @return titleHealthUnit: String
     */

    public String getTitleHealthUnit() {

        return titleHealthUnit;
    }

    /**
     * Set the value of attribute titleHealthUnit.
     * @param titleHealthUnit
     */

    public void setTitleHealthUnit(String titleHealthUnit) {

        this.titleHealthUnit = titleHealthUnit;
    }

    /**
     * Get the value of attribute nameHealthUnit.
     * @return nameHealthUnit: String
     */

    public String getNameHealthUnit() {
        return nameHealthUnit;
    }

    /**
     * Set the value of attribute nameHealthUnit.
     * @param nameHealthUnit
     */

    public void setNameHealthUnit(String nameHealthUnit) {

        this.nameHealthUnit = nameHealthUnit;
    }

    /**
     * Get the value of attribute unitType.
     * @return unitType: String
     */

    public String getUnitType() {

        return unitType;
    }

    /**
     * Set the value of attribute unitType.
     * @param unitType
     */

    public void setUnitType(String unitType) {

        this.unitType = unitType;
    }

    /**
     * Get the value of attribute state.
     * @return state: String
     */

    public String getState() {

        return state;
    }

    /**
     * Set the value of attribute state.
     * @param state
     */

    public void setState(String state) {

        this.state = state;
    }

    /**
     * Get the value of attribute city.
     * @return city: String
     */

    public String getCity() {

        return city;
    }

    /**
     * Set the value of attribute city.
     * @param city
     */

    public void setCity(String city) {

        this.city = city;
    }

    /**
     * Get the value of attribute district.
     * @return district: String
     */

    public String getDistrict() {

        return district;
    }

    /**
     * Set the value of attribute district.
     * @param district
     */

    public void setDistrict(String district) {

        this.district = district;
    }

    /**
     * Get the value of attribute addressNumber.
     * @return addressNumber: String
     */

    public String getAddressNumber() {

        return addressNumber;
    }

    /**
     * Set the value of attribute addressNumber.
     * @param addressNumber
     */

    public void setAddressNumber(String addressNumber) {

        this.addressNumber = addressNumber;
    }
}


