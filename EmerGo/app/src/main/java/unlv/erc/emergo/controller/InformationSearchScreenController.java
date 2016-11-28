/************************
 * Class name: InformationSearchScreenController (.java)
 * <p>
 * Purpose: The purpose of this class is to show the information of the healthunits.
 ************************/

package unlv.erc.emergo.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class InformationSearchScreenController extends Activity {

  private static final String TAG = "InformationSearchScreen";

  // The Bundle data value of numeroUs.
  private static final int VALUE_LOWER_CLOSE = -1;

  // The value to be returned if no value of the desired type is stored with the given name.
  private static final int RETURN_NOT_FOUND = 0;

  // The name of the desired item.
  private static final String POSITION = "position";

  // The Bundle data value.
  private static final String NUMBER_HEALTH_UNIT = "numeroUs";

  private List<String> listOfInformations = new ArrayList<String>();
  private ListView healthUnitInfo;
  private Intent receive;
  private Button buttonRoute;
  private ImageView buttonGo;
  private int numberHealthUnitSelected;
  private String padding = "";
  private String titleHealthUnit = "";
  private String nameHealthUnit = "";
  private String healthUnitType = "";
  private String state = "";
  private String city = "";
  private String district = "";
  private String addressNumber = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    Log.d(TAG, "onCreate() called with: Bundle = [" + savedInstanceState + "]");

    // Creation of screen.
    super.onCreate(savedInstanceState);
    setContentView(R.layout.information_us_screen);

    // Route button setting.
    setReceive(getIntent());
    searchHealthUnitSelected(receive.getIntExtra(POSITION, RETURN_NOT_FOUND));
    buttonRoute = (Button) findViewById(R.id.botaoRota);
    buttonRoute.setOnClickListener(new View.OnClickListener() {

      // Call the route setup method.
      public void onClick(View viewButtonRoute) {

        buttonRoute();
      }
    });

    // Go button setting.
    buttonGo = (ImageView) findViewById(R.id.buttonGo);
    buttonGo.setOnClickListener(new View.OnClickListener() {

      // Call the button "Go" setup method.
      public void onClick(View viewButtonGo) {

        buttonClickGo();
      }
    });

    // List with health units sought.
    setHealthUnitInfo((ListView) findViewById(R.id.hospInformation));
    setInformation(HealthUnitController.getClosestHealthUnit().get(numberHealthUnitSelected));
    addInformationToList();
  }

  /**
   * Exchange of current activity and start activity "SearchController".
   * @param mapScreen View of mapScree.
   *
   */

  public void openSearch(View mapScreen) {

    Log.d(TAG, "openSearch() called with: View = [" + mapScreen + "]");

    Intent openSearch = new Intent();

    openSearch.setClass(this, SearchHealthUnitActivity.class);
    startActivity(openSearch);
  }

  /**
   * Exchange of current activity and start activity "SettingsController".
   * @param viewSettings View of config.
   *
   */

  public void openSettings(View viewSettings) {

    Log.d(TAG, "openSettings() called with: View = [" + viewSettings + "]");

    Intent config = new Intent();

    config.setClass(this, SettingsController.class);
    startActivity(config);
  }

  /**
   * Exchange of current activity and start activity "ListOfHealthUnitsController".
   * @param mapScreen View of mapScreen.
   */

  public void listMapsImageClicked(View mapScreen) {

    Log.d(TAG, "listMapsImageClicked() called with: View = [" + mapScreen + "]");

    Intent listOfHealth = new Intent();

    listOfHealth.setClass(this, ListOfHealthUnitsController.class);
    startActivity(listOfHealth);
    finish();
  }

  /**
   * Exchange of current activity and start activity "MapScreenController".
   * @param mapScreen View of mapScreen.
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
   * Get the value of attribute of image buttonGo.
   * @return buttonGo: ImageView.
   *
   */

  public ImageView getButtonGo() {

    return buttonGo;
  }

  /**
   * Set the value of attribute of image buttonGo.
   * @param buttonGo Button go.
   *
   */

  public void setButtonGo(ImageView buttonGo) {

    Log.d(TAG, "setButtonGo() called with: ImageView = [" + buttonGo + "]");

    this.buttonGo = buttonGo;
  }

  /**
   * Get the value of attribute padding.
   * @return padding: String.
   *
   */

  protected String getPadding() {

    return padding;
  }

  /**
   * Set the value of attribute padding.
   * @param padding String.
   *
   */

  protected void setPadding(String padding) {

    Log.d(TAG, "setPadding() called with: String = [" + padding+ "]");

    this.padding = padding;
  }

  /**
   * Get the value of attribute titleHealthUnit.
   * @return titleHealthUnit: String.
   *
   */

  protected String getTitleHealthUnit() {

    return titleHealthUnit;
  }

  /**
   * Set the value of attribute titleHealthUnit.
   * @param titleHealthUnit String.
   *
   */

  protected void setTitleHealthUnit(String titleHealthUnit) {

    Log.d(TAG, "setTitleHealthUnit() called with: String = [" + titleHealthUnit + "]");

    this.titleHealthUnit = titleHealthUnit;
  }

  /**
   * Get the value of attribute nameHealthUnit.
   * @return nameHealthUnit: String.
   *
   */

  protected String getNameHealthUnit() {

    return nameHealthUnit;
  }

  /**
   * Set the value of attribute nameHealthUnit.
   * @param nameHealthUnit String.
   *
   */

  protected void setNameHealthUnit(String nameHealthUnit) {

    Log.d(TAG, "setNameHealthUnit() called with: String = [" + nameHealthUnit + "]");

    this.nameHealthUnit = nameHealthUnit;
  }

  /**
   * Get the value of attribute healthUnitType.
   * @return healthUnitType: String.
   *
   */

  protected String getUnitType() {

    return healthUnitType;
  }

  /**
   * Set the value of attribute healthUnitType.
   * @param healthUnitType String.
   *
   */

  protected void setUnitType(String healthUnitType) {

    Log.d(TAG, "setUnitType() called with: String = [" + healthUnitType + "]");

    this.healthUnitType = healthUnitType;
  }

  /**
   * Get the value of attribute state.
   * @return state: String.
   *
   */

  protected String getState() {

    return state;
  }

  /**
   * Set the value of attribute state.
   * @param state String.
   *
   */

  protected void setState(String state) {

    Log.d(TAG, "setState() called with: String = [" + state + "]");

    this.state = state;
  }

  /**
   * Get the value of attribute city.
   * @return city: String.
   *
   */

  protected String getCity() {

    return city;
  }

  /**
   * Set the value of attribute city.
   * @param city String.
   *
   */

  protected void setCity(String city) {

    Log.d(TAG, "setCity() called with: String = [" + city + "]");

    this.city = city;
  }

  /**
   * Get the value of attribute district.
   * @return district: String.
   *
   */

  protected String getDistrict() {

    return district;
  }

  /**
   * Set the value of attribute district.
   * @param district String.
   *
   */

  protected void setDistrict(String district) {

    Log.d(TAG, "setDistrict() called with: String = [" + district + "]");

    this.district = district;
  }

  /**
   * Get the value of attribute addressNumber.
   * @return addressNumber: String.
   *
   */

  protected String getAddressNumber() {

    return addressNumber;
  }

  /**
   * Set the value of attribute addressNumber.
   * @param addressNumber String.
   *
   */

  protected void setAddressNumber(String addressNumber) {

    Log.d(TAG, "setAddressNumber() called with: String = [" + addressNumber + "]");

    this.addressNumber = addressNumber;
  }

  /**
   * This function takes the data on the health units saved in the database and through
   * ArrayLists, each arrow information in place. The data that will be represented are: Name,
   * type of care,UF,city,neighborhood and zip code.
   *
   */

  private void setInformation(HealthUnit healthUnit) {

    Log.d(TAG, "setInformation() called with: HealthUnit = [" + healthUnit + "]");

    setPadding("\n");
    setTitle("        Informações da Unidade de Saúde");
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
   *
   */

  private void addInformationToList() {

    listOfInformations.add(padding);
    listOfInformations.add(titleHealthUnit);
    listOfInformations.add(nameHealthUnit);
    listOfInformations.add(healthUnitType);
    listOfInformations.add(state);
    listOfInformations.add(city);
    listOfInformations.add(district);
    listOfInformations.add(addressNumber);

    showInformationOnScreen();
  }

  /**
   * Shows the information saved in the arraylist and put in a ArrayAdapter.
   *
   */

  private void showInformationOnScreen() {

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, listOfInformations);

    healthUnitInfo.setAdapter(adapter);
  }

  /**
   * When the "buttonRoute" is pressed, it will exit the current class and opened the activity and
   * "RouteActivity" class. When RouteActivity is finished, come in method "finish ()" which will
   * close the activity "RouteActivity".
   *
   */

  private void buttonRoute() {

    Intent route = new Intent();

    route.setClass(this, RouteActivity.class);
    route.putExtra(NUMBER_HEALTH_UNIT, receive.getIntExtra(POSITION, RETURN_NOT_FOUND));
    startActivity(route);
    finish();
  }

  /**
   * When the "buttonGo" is pressed, it will exit the current class, opened the activity
   * "RouteActivity," only different from the method of "buttonRoute", will now be chosen the
   * route nearest the user.When the RouteActivity is finished, come in method "finish ()"
   * which will close the activity "RouteActivity".
   *
   */

  private void buttonClickGo() {

    String routeTraced = "Rota mais próxima traçada";

    Toast.makeText(this, routeTraced, Toast.LENGTH_SHORT).show();
    Intent routeActivity = new Intent();
    routeActivity.setClass(this, RouteActivity.class);
    routeActivity.putExtra(NUMBER_HEALTH_UNIT, VALUE_LOWER_CLOSE);
    startActivity(routeActivity);
    finish();
  }

  /**
   * Get the value of attribute listOfInformations.
   * @return listofInformations: String
   */

  private List<String> getListOfInformations() {

    return listOfInformations;
  }

  /**
   * Set the value of attribute listOfInformations.
   * @param listOfInformations List of Informations about health units.
   *
   */

  private void setListOfInformations(List<String> listOfInformations) {

    Log.d(TAG, "setListOfInformation() called with: List<String> = [" + listOfInformations + "]");

    this.listOfInformations = listOfInformations;
  }

  /**
   * Get the value of attribute healthUnitInfo.
   * @return healthUnitInfo: ListView
   *
   */

  private ListView getHealthUnitInfo() {

    return healthUnitInfo;
  }

  /**
   * Set the value of attribute healthUnitInfo.
   * @param healthUnitInfo ListView about health units.
   *
   */

  private void setHealthUnitInfo(ListView healthUnitInfo) {

    Log.d(TAG, "setHealthUnitInfo() called with: ListView = [" + healthUnitInfo + "]");

    this.healthUnitInfo = healthUnitInfo;
  }

  /**
   * Get the value of attribute receive.
   * @return healthUnitInfo: Intent.
   *
   */

  private Intent getReceive() {

    return receive;
  }

  /**
   * Set the value of attribute receive.
   * @param receive Intent.
   *
   */

  private void setReceive(Intent receive) {

    Log.d(TAG, "setReceive() called with: Intent = [" + receive + "]");

    this.receive = receive;
  }

  /**
   * Get the value of attribute buttonRoute.
   * @return buttonRoute: ButtonRoute.
   *
   */

  private Button getButtonRoute() {

    return buttonRoute;
  }

  /**
   * Set the value of attribute buttonRoute.
   * @param buttonRoute Button route.
   *
   */

  private void setButtonRoute(Button buttonRoute) {

    Log.d(TAG, "setButtonRoute() called with: String = [" + buttonRoute + "]");

    this.buttonRoute = buttonRoute;
  }

  /**
   * Get the value of attribute numberHealthUnitSelected.
   * @return numberHealthUnitSelected: int.
   *
   */

  private int getNumberUsSelected() {

    return numberHealthUnitSelected;
  }

  /**
   * Set the value of attribute numberHealthUnitSelected.
   * @param numberHealthUnitSelected int.
   *
   */

  private void searchHealthUnitSelected(int numberHealthUnitSelected) {

    Log.d(TAG, "searchHealthUnitSelected() called with: int = [" + numberHealthUnitSelected + "]");

    this.numberHealthUnitSelected = numberHealthUnitSelected;
  }
}
