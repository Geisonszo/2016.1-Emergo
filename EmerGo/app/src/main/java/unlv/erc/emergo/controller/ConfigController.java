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
  protected void onCreate(Bundle savedInstanceState) { //Begin of onCreate

    super.onCreate(savedInstanceState);
    setContentView(R.layout.config);
    setImageViewIcons();
    setButtons();

    //When buttonGo is clicked will open the method "createRouteScreen".
    buttonGo.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      @Override
      public void onClick(View goView) { //Begin on click

        createRouteScreen();
      } //End of onClick
    }); //End of setOnClickListener

    //When iconList is clicked will open the method "listUs".
    iconList.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      @Override
      public void onClick(View iconView) { //Begin of onClick

        listUs();
      } //End of onClick
    }); //End of setOnClickListener

    //When medicalRecord is clicked will open the method "showMedicalRecord".
    medicalRecord.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      public void onClick(View medicalRecordView) { //Begin of onClick

        showMedicalRecord();
      } // End of onClick
    }); // End of setOnClickListener

    //When emergencyContact is clicked will open the method "acessEmergencyContact".
    emergencyContact.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      public void onClick(View emergencyContactView) { //Begin of onClick

        acessEmergencyContact();
      } //End of onClick
    }); //End of setOnClickListener

    //When aboutApp is clicked will open the method "settInfoAboutApp".
    aboutApp.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      public void onClick(View aboutAppView) { //Begin of onClick

        seeInfoAboutApp();
      } //End of OnClick
    }); //End of setOnClickListener
  } //End of onCreate

  /*
    Set ImageView.

   */

  private void setImageViewIcons() {

    Log.i("Method setImageViewIcons ","Begin of method");
    iconList = (ImageView) findViewById(R.id.iconList);
    buttonGo = (ImageView) findViewById(R.id.buttonGo);
    Log.i("Method setImageViewIcons ","End of method");
  }

  /*
    Set Buttons.

   */

  private void setButtons() {

    Log.i("Method setButtons ","Begin of method");
    emergencyContact = (Button) findViewById(R.id.emergencyContact);
    medicalRecord = (Button) findViewById(R.id.medicalRecords);
    aboutApp = (Button) findViewById(R.id.app);
    Log.i("Method setButtons ","End of method");
  }

  /**
   * This method create a route screen after GO button is clicked, tracing a route to a
   * specific health unity.
   *
   */

  public void createRouteScreen() {

    Log.i("Method createRouteScreen ","Begin of method");
    Intent map = new Intent();

    map.setClass(this , RouteActivity.class);
    map.putExtra("numeroUs" , -1);
    startActivity(map);
    finish();
    Log.i("Method createRouteScreen ","End of method");
  }

  /**
   * This method list all the USs, by proximity of the user location, after the list button
   * is clicked.
   *
   */

  public void listUs() {

    Log.i("Method listUs ","Begin of method");
    Intent listUs = new Intent();

    listUs.setClass(this , ListOfHealthUnitsController.class);
    startActivity(listUs);
    finish();
    Log.i("Method listUs ","End of method");
  }

  /**
   * This method show users medical information, after the medical information button is clicked.
   *
   */

  public void showMedicalRecord() {

    Log.i("Method showMedicalRecord ","Begin of method");
    Intent medicalRecord = new Intent();

    medicalRecord.setClass(this,MedicalRecordsController.class);
    startActivity(medicalRecord);
    finish();
    Log.i("Method showMedicalRecord ","End of method");
  }

  /**
   * This method show user's emergency contacts, after the emergency contact button is clicked.
   *
   */

  public void acessEmergencyContact() {

    Log.i("Method acessEmergencyContact ","Begin of method");
    Intent emergencyContact = new Intent();

    emergencyContact.setClass(this,EmergencyContactController.class);
    startActivity(emergencyContact);
    finish();
    Log.i("Method acessEmergencyContact ","End of method");
  }

  /**
   * This method show the info related to EmerGo, after the button about is clicked.
   *
   */

  public void seeInfoAboutApp() {

    Log.i("Method seeInforAboutApp ","Begin of method");
    Intent aboutApp = new Intent();

    aboutApp.setClass(this,AboutApp.class);
    startActivity(aboutApp);
    finish();
    Log.i("Method seeInfoAboutApp ","End of method");
  }

  /**
   * This method is activated when user clicks in GO button, tracing a route to the closest
   * health unity.
   * @param mapScreen actual View Object.
   *
   */

  public void goClicked(View mapScreen) throws IOException, JSONException {

    Log.i("Method goClicked ","Begin of method");
    Intent routeActivity = new Intent();

    Toast.makeText(this, routeTraced , Toast.LENGTH_SHORT).show();
    routeActivity.setClass(ConfigController.this , RouteActivity.class);
    routeActivity.putExtra("numeroUs" , -1);
    startActivity(routeActivity);
    finish();
    Log.i("Method goClicked ","End of method");
  }

  /**
   * This method list all the USs, by proximity of the user location, after the list button
   * is clicked.
   * @param mapScreen actual View Object.
   *
   */

  public void listMapsImageClicked(View mapScreen) {

    Log.i("Method listMapsImageClicked ","Begin of method");
    Intent listOfHealth = new Intent();

    listOfHealth.setClass(this , ListOfHealthUnitsController.class);
    startActivity(listOfHealth);
    finish();
    Log.i("Method listMapsImageClicked ","End of method");
  }

  /**
   * This method is activated when user is already in the configuration screen, and
   * try to open it again.Intent routeActivity = new Intent();
   * @param mapScreen actual View Object.
   *
   */

  public void openConfig(View mapScreen) {

    Log.i("Method openConfig ","Begin of method");
    Toast.makeText(this , errorMessage , Toast.LENGTH_SHORT ).show();
    Log.i("Method openConfig ","End of method");
  }

  /**
   * This method is activated when user clicks in the map button, and open a new map.
   * @param mapScreen actual View Object.
   *
   */

  public void openMap(View mapScreen) {

    Log.i("Method openMap ","Begin of method");
    Intent mapActivity = new Intent();

    mapActivity.setClass(this, MapScreenController.class);
    startActivity(mapActivity);
    finish();
    Log.i("Method openMap ","End of method");
  }
}