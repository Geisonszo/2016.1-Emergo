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

public class ConfigController extends Activity {

  //ImageView for ListOfHealthUnitsController.class
  private ImageView iconList;

  //ImageView for RouteActivity.class
  private ImageView buttonGo;

  //Button for EmergencyContact class
  private Button emergencyContact;

  //Button for MedicalRecordsController class
  private Button medicalRecord;

  //Button for AboutApp class
  private Button aboutApp;

  //String speaking being clicked the current screen
  final String errorMessage = "Está tentando abrir a página atual";

  //String speaking that the route will be drawn to the nearest health unit
  final String routeTraced = "Rota mais próxima traçada";

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.config);
    setImageViewIcons();
    setButtons();

    //When buttonGo is clicked will open the method "createRouteScreen".
    buttonGo.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View goView) {

        createRouteScreen();
      }
    });

    //When iconList is clicked will open the method "listUs".
    iconList.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View iconView) {

        listUs();
      }
    });

    //When medicalRecord is clicked will open the method "showMedicalRecord".
    medicalRecord.setOnClickListener(new View.OnClickListener() {

      public void onClick(View medicalRecordView) {

        showMedicalRecord();
      }
    });

    //When emergencyContact is clicked will open the method "acessEmergencyContact".
    emergencyContact.setOnClickListener(new View.OnClickListener() {

      public void onClick(View emergencyContactView) {

        acessEmergencyContact();
      }
    });

    //When aboutApp is clicked will open the method "settInfoAboutApp".
    aboutApp.setOnClickListener(new View.OnClickListener() {

      public void onClick(View aboutAppView) {

        seeInfoAboutApp();
      }
    });
  }

  /*
    Set ImageView.

   */

  private void setImageViewIcons() {

    Log.i("Method setImageViewIcons","Initializing ImageViews");
    iconList = (ImageView) findViewById(R.id.iconList);
    buttonGo = (ImageView) findViewById(R.id.buttonGo);
  }

  /*
    Set Buttons.

   */

  private void setButtons() {

    Log.i("Method setButtons","Initializing Buttons");
    emergencyContact = (Button) findViewById(R.id.emergencyContact);
    medicalRecord = (Button) findViewById(R.id.medicalRecords);
    aboutApp = (Button) findViewById(R.id.app);
  }

  /**
   * This method create a route screen after GO button is clicked, tracing a route to a
   * specific health unity.
   *
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
   * @param mapScreen actual View object.
   *
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
   *
   */

  public void listUs() {

    Intent listUs = new Intent();
    listUs.setClass(this , ListOfHealthUnitsController.class);
    startActivity(listUs);
    finish();
  }

  /**
   * This method show users medical information, after the medical information button is clicked.
   *
   */

  public void showMedicalRecord() {

    Intent medicalRecord = new Intent();
    medicalRecord.setClass(this,MedicalRecordsController.class);
    startActivity(medicalRecord);
  }

  /**
   * This method show user's emergency contacts, after the emergency contact button is clicked.
   *
   */

  public void acessEmergencyContact() {

    Intent emergencyContact = new Intent();
    emergencyContact.setClass(this,EmergencyContactController.class);
    startActivity(emergencyContact);
  }

  /**
   * This method show the info related to EmerGo, after the button about is clicked.
   *
   */

  public void seeInfoAboutApp() {

    Intent aboutApp = new Intent();
    aboutApp.setClass(this,AboutApp.class);
    startActivity(aboutApp);
  }

  /**
   * This method is activated when user clicks in GO button, tracing a route to the closest
   * health unity.
   * @param mapScreen actual View Object.
   *
   */

  public void goClicked(View mapScreen) throws IOException, JSONException {

    Toast.makeText(this, routeTraced , Toast.LENGTH_SHORT).show();
    Intent routeActivity = new Intent();
    routeActivity.setClass(ConfigController.this , RouteActivity.class);
    routeActivity.putExtra("numeroUs" , -1);
    startActivity(routeActivity);
    finish();
  }

  /**
   * This method list all the USs, by proximity of the user location, after the list button
   * is clicked.
   * @param mapScreen actual View Object.
   *
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
   * @param mapScreen actual View Object.
   *
   */

  public void openConfig(View mapScreen) {

    Toast.makeText(this , errorMessage , Toast.LENGTH_SHORT ).show();
  }

  /**
   * This method is activated when user clicks in the map button, and open a new map.
   * @param mapScreen actual View Object.
   *
   */

  public void openMap(View mapScreen) {

    Intent mapActivity = new Intent();
    mapActivity.setClass(this, MapScreenController.class);
    startActivity(mapActivity);
    finish();
  }
}