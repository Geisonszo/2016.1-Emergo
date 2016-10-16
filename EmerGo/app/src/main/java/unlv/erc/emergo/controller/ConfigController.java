/*
 * Class: ConfigController (.java)
 *
 * Porpouse: This class contains methods the related to the configuration screen on EmerGo.
 */

package unlv.erc.emergo.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import unlv.erc.emergo.R;

public class ConfigController extends Activity { // Begin of ConfigController class

  // ImageView for ListOfHealthUnitsController.class
  private ImageView iconList;
  // ImageView for RouteActivity.class
  private ImageView buttonGo;
  // Button for EmergencyContact class
  private Button emergencyContact;
  // Button for MedicalRecordsController class
  private Button medicalRecord;
  // Button for AboutApp class
  private Button aboutApp;
  // String speaking being clicked the current screen
  final String errorMessage = "Está tentando abrir a página atual";
  // String speaking that the route will be drawn to the nearest health unit
  final String routeTraced = "Rota mais próxima traçada";

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.config);
    setImageViewIcons();
    setButtons();
    createRouteScreen();
    listUs();
    showMedicalRecord();
    acessEmergencyContact();
  }

  /*
   * Set ImageView.
   *
   */

  private void setImageViewIcons() {

    Log.d("Begin of method: ","setImageViewIcons");
    iconList = (ImageView) findViewById(R.id.iconList);
    buttonGo = (ImageView) findViewById(R.id.buttonGo);
    Log.d("End of method: ","Method setImageViewIcons ");
  }

  /*
   * Set Buttons.
   *
   */

  private void setButtons() {

    Log.d("Method setButtons ","Begin of method");
    emergencyContact = (Button) findViewById(R.id.emergencyContact);
    medicalRecord = (Button) findViewById(R.id.medicalRecords);
    aboutApp = (Button) findViewById(R.id.app);
    Log.d("Method setButtons ","End of method");
  }

  /**
   * This method create a route screen after GO button is clicked, tracing a route to a
   * specific health unity.
   *
   */

  public void createRouteScreen() {

    Log.d("Begin of method: ","Method createRouteScreen ");
    //When buttonGo is clicked will open the method "createRouteScreen".
    buttonGo.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      @Override
      public void onClick(View goView) {

        Intent map = new Intent();

        map.setClass(ConfigController.this , RouteActivity.class);
        map.putExtra("numeroUs" , -1);
        startActivity(map);
        finish();
      } //End of onClick
    });
    Log.d("End of method: ","Method createRouteScreen ");
  }

  /**
   * This method list all the USs, by proximity of the user location, after the list button
   * is clicked.
   *
   */

  public void listUs() {

    Log.d("Begin of method: ","Method listUs ");
    //When iconList is clicked will open the method "listUs".
    iconList.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      @Override
      public void onClick(View iconView) {

        Intent listUs = new Intent();

        listUs.setClass(ConfigController.this , ListOfHealthUnitsController.class);
        startActivity(listUs);
        finish();
      } //End of onClick
    });
    Log.d("End of method: ","Method listUs ");
  }

  /**
   * This method show users medical information, after the medical information button is clicked.
   *
   */

  public void showMedicalRecord() {

    Log.d("Begin of method: ","Method showMedicalRecord");
    //When medicalRecord is clicked will open the method "showMedicalRecord".
    medicalRecord.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      public void onClick(View medicalRecordView) {

        Intent medicalRecord = new Intent();

        medicalRecord.setClass(ConfigController.this,MedicalRecordsController.class);
        startActivity(medicalRecord);
        finish();
      } // End of onClick
    });
    Log.d("End of method: ","Method showMedicalRecord");
  }

  /**
   * This method show user's emergency contacts, after the emergency contact button is clicked.
   *
   */

  public void acessEmergencyContact() {

    Log.d("Begin of method: ","Method acessEmergencyContact");
    //When emergencyContact is clicked will open the method "acessEmergencyContact".
    emergencyContact.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      public void onClick(View emergencyContactView) {

        Intent emergencyContact = new Intent();

        emergencyContact.setClass(ConfigController.this,EmergencyContactController.class);
        startActivity(emergencyContact);
        finish();
      } //End of onClick
    });
    Log.d("End of method: ","Method acessEmergencyContact ");
  }

  /**
   * This method show the info related to EmerGo, after the button about is clicked.
   *
   */

  public void seeInfoAboutApp() {

    Log.d("Begin of method: ","Method seeInforAboutApp ");
    //When aboutApp is clicked will open the method "settInfoAboutApp".
    aboutApp.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      public void onClick(View aboutAppView) {

        Intent aboutApp = new Intent();

        aboutApp.setClass(ConfigController.this,AboutApp.class);
        startActivity(aboutApp);
        finish();
      } //End of OnClick
    });
    Log.d("End of method: ","Method seeInfoAboutApp ");
  }

  /**
   * This method is activated when user clicks in GO button, tracing a route to the closest
   * health unity.
   * @param mapScreen go to routeActivity class.
   *
   */

  public void goClicked(View mapScreen) throws IOException, JSONException {

    assert mapScreen != null : "mapScreen can't be null";

    Log.d("Begin of method: ","Method goClicked ");
    Intent routeActivity = new Intent();

    Toast.makeText(this, routeTraced , Toast.LENGTH_SHORT).show();
    routeActivity.setClass(ConfigController.this , RouteActivity.class);
    routeActivity.putExtra("numeroUs" , -1);
    startActivity(routeActivity);
    finish();
    Log.d("End of method: ","Method goClicked ");
  }

  /**
   * This method list all the USs, by proximity of the user location, after the list button
   * is clicked.
   * @param mapScreen go to ListOfHeatlthUnitsController class.
   *
   */

  public void listMapsImageClicked(View mapScreen) {

    assert mapScreen != null : "mapScreen can't be null";

    Log.d("Begin of method: ","Method listMapsImageClicked ");
    Intent listOfHealth = new Intent();

    listOfHealth.setClass(this , ListOfHealthUnitsController.class);
    startActivity(listOfHealth);
    finish();
    Log.d("End of method: ","Method listMapsImageClicked ");
  }

  /**
   * This method is activated when user is already in the configuration screen, and
   * try to open it again.Intent routeActivity = new Intent();
   * @param openConfig actual View Object.
   *
   */

  public void openConfig(View openConfig) {

    assert openConfig != null : "openConfig can't be null";

    Log.d("Method openConfig ","Begin of method");
    Toast.makeText(this , errorMessage , Toast.LENGTH_SHORT ).show();
    Log.d("Method openConfig ","End of method");
  }

  /**
   * This method is activated when user clicks in the map button, and open a new map.
   * @param openMap go to MapScreenController class.
   *
   */

  public void openMap(View openMap) {

    assert openMap != null : "openMap can't be null";

    Log.d("Begin of method: ","Method openMap ");
    Intent mapActivity = new Intent();

    mapActivity.setClass(this, MapScreenController.class);
    startActivity(mapActivity);
    finish();
    Log.d("End of method: ","Method openMap ");
  }
}