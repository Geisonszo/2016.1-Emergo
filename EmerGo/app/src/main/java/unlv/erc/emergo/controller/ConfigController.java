/*
 * Class: ConfigController (.java)
 *
 * Porpouse: This class contains methods the related to the configuration screen on EmerGo.
 */

package unlv.erc.emergo.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import unlv.erc.emergo.R;

public class ConfigController extends Activity {

  private Button emergencyContact;
  private ImageView iconList;
  private ImageView buttonGo;
  private Button medicalRecord;
  private Button aboutApp;

  final String ERRORMESSAGE = "Está tentando abrir a página atual";
  final String ROUTETRACED = "Rota mais próxima traçada";

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.config);
    iconList = (ImageView) findViewById(R.id.iconList);
    buttonGo = (ImageView) findViewById(R.id.buttonGo);
    emergencyContact = (Button) findViewById(R.id.emergencyContact);
    medicalRecord = (Button) findViewById(R.id.medicalRecords);
    aboutApp = (Button) findViewById(R.id.app);


    buttonGo.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        createRouteScreen();
      }
    });
    iconList.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        listUs();
      }
    });
    medicalRecord.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        showMedicalRecord();
      }
    });
    emergencyContact.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        acessEmergencyContact();
      }
    });
    aboutApp.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        seeInfoAboutApp();
      }
    });
  }

  /**
   * This method create a route screen after GO button is clicked, tracing a route to a
   * specific health unity.
   */

  public void createRouteScreen() {
        
    Intent map = new Intent();
    map.setClass(this , RouteActivity.class);
    map.putExtra("numeroUs" , -1);
    startActivity(map);
    finish();
  }

  /**
   * This method launchs the search screen after the search button is clicked.
   * @param mapScreen
   */

  public void openSearch(View mapScreen) {
    Intent openSearch = new Intent();
    openSearch.setClass(this , SearchUsController.class);
    startActivity(openSearch);
    finish();
  }

  /**
   * This method list all the USs, by proximity of the user location, after the list button
   * is clicked.
   */

  public void listUs() {

    Intent listUs = new Intent();
    listUs.setClass(this , ListOfHealthUnitsController.class);
    startActivity(listUs);
    finish();
  }

  /**
   * This method show users medical information, after the medical information button is clicked.
   */

  public void showMedicalRecord() {

    Intent medicalRecord = new Intent();
    medicalRecord.setClass(this,MedicalRecordsController.class);
    startActivity(medicalRecord);
  }

  /**
   * This method show user's emergency contacts, after the emergency contact button is clicked.
   */

  public void acessEmergencyContact() {

    Intent emergencyContact = new Intent();
    emergencyContact.setClass(this,EmergencyContactController.class);
    startActivity(emergencyContact);
  }

  /**
   * This method show the info related to EmerGo, after the button about is clicked.
   */

  public void seeInfoAboutApp() {

    Intent aboutApp = new Intent();
    aboutApp.setClass(this,AboutApp.class);
    startActivity(aboutApp);
  }

  /**
   * This method is activated when user clicks in GO button, tracing a route to the closest
   * health unity.
   */

  public void goClicked(View mapScreen) throws IOException, JSONException {

    Toast.makeText(this, ROUTETRACED , Toast.LENGTH_SHORT).show();
    Intent routeActivity = new Intent();
    routeActivity.setClass(ConfigController.this , RouteActivity.class);
    routeActivity.putExtra("numeroUs" , -1);
    startActivity(routeActivity);
    finish();
  }

  /**
   * This method list all the USs, by proximity of the user location, after the list button
   * is clicked.
   */

  public void listMapsImageClicked(View mapScreen) {

    Intent listOfHealth = new Intent();
    listOfHealth.setClass(this , ListOfHealthUnitsController.class);
    startActivity(listOfHealth);
    finish();
  }

  /**
   * This method is activated when user is already in the configuration screen, and
   * try to open it again.
   */

  public void openConfig(View mapScreen) {

    Toast.makeText(this , ERRORMESSAGE , Toast.LENGTH_SHORT ).show();
  }

  /**
   * This method is activated when user clicks in the map button, and open a new map.
   */

  public void openMap(View mapScreen) {

    Intent mapActivity = new Intent();
    mapActivity.setClass(this, MapScreenController.class);
    startActivity(mapActivity);
    finish();
  }

}