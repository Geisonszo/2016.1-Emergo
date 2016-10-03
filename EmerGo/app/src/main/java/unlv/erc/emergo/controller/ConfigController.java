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
  protected void onCreate(Bundle savedInstanceState) { // Begin of onCreate

    super.onCreate(savedInstanceState);
    setContentView(R.layout.config);
    setImageViewIcons();
    setButtons();
    createRouteScreen();
    listUs();
    showMedicalRecord();
    acessEmergencyContact();
  } // End of onCreate

  /*
   * Set ImageView.
   *
   */

  private void setImageViewIcons() { // Begin of setImageViewIcons

    Log.i("Begin of method: ","setImageViewIcons");
    iconList = (ImageView) findViewById(R.id.iconList);
    buttonGo = (ImageView) findViewById(R.id.buttonGo);
    Log.i("End of method: ","Method setImageViewIcons ");
  } // Enf of setImageViewIcons

  /*
   * Set Buttons.
   *
   */

  private void setButtons() { // Begin of setButtons

    Log.i("Method setButtons ","Begin of method");
    emergencyContact = (Button) findViewById(R.id.emergencyContact);
    medicalRecord = (Button) findViewById(R.id.medicalRecords);
    aboutApp = (Button) findViewById(R.id.app);
    Log.i("Method setButtons ","End of method");
  } // End of setButtons

  /**
   * This method create a route screen after GO button is clicked, tracing a route to a
   * specific health unity.
   *
   */

  public void createRouteScreen() { // Begin of createRouteScreen

    Log.i("Begin of method: ","Method createRouteScreen ");
    //When buttonGo is clicked will open the method "createRouteScreen".
    buttonGo.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      @Override
      public void onClick(View goView) { //Begin on click

        Intent map = new Intent();

        map.setClass(ConfigController.this , RouteActivity.class);
        map.putExtra("numeroUs" , -1);
        startActivity(map);
        finish();
      } //End of onClick
    }); //End of setOnClickListener
    Log.i("End of method: ","Method createRouteScreen ");
  } // End of createRouteScreen

  /**
   * This method list all the USs, by proximity of the user location, after the list button
   * is clicked.
   *
   */

  public void listUs() { // Begin of listUs

    Log.i("Begin of method: ","Method listUs ");
    //When iconList is clicked will open the method "listUs".
    iconList.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      @Override
      public void onClick(View iconView) { //Begin of onClick

        Intent listUs = new Intent();

        listUs.setClass(ConfigController.this , ListOfHealthUnitsController.class);
        startActivity(listUs);
        finish();
      } //End of onClick
    }); //End of setOnClickListener
    Log.i("End of method: ","Method listUs ");
  } // End of listUs

  /**
   * This method show users medical information, after the medical information button is clicked.
   *
   */

  public void showMedicalRecord() { // Begin of showMedicalRecord

    Log.i("Begin of method: ","Method showMedicalRecord");
    //When medicalRecord is clicked will open the method "showMedicalRecord".
    medicalRecord.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      public void onClick(View medicalRecordView) { //Begin of onClick

        Intent medicalRecord = new Intent();

        medicalRecord.setClass(ConfigController.this,MedicalRecordsController.class);
        startActivity(medicalRecord);
        finish();
      } // End of onClick
    }); // End of setOnClickListener
    Log.i("End of method: ","Method showMedicalRecord");
  } // End of showMessageRecord

  /**
   * This method show user's emergency contacts, after the emergency contact button is clicked.
   *
   */

  public void acessEmergencyContact() { // Begin of acessEmergencyContact

    //When emergencyContact is clicked will open the method "acessEmergencyContact".
    emergencyContact.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      public void onClick(View emergencyContactView) { //Begin of onClick

        Intent emergencyContact = new Intent();

        emergencyContact.setClass(ConfigController.this,EmergencyContactController.class);
        startActivity(emergencyContact);
        finish();
      } //End of onClick
    }); //End of setOnClickListener
    Log.i("Begin of method: ","Method acessEmergencyContact");

    Log.i("End of method: ","Method acessEmergencyContact ");
  } // End of acessEmergencyContact

  /**
   * This method show the info related to EmerGo, after the button about is clicked.
   *
   */

  public void seeInfoAboutApp() { // Begin of seeInfoAboutApp

    Log.i("Begin of method: ","Method seeInforAboutApp ");
    //When aboutApp is clicked will open the method "settInfoAboutApp".
    aboutApp.setOnClickListener(new View.OnClickListener() { //Begin of setOnClickListener

      public void onClick(View aboutAppView) { //Begin of onClick

        Intent aboutApp = new Intent();

        aboutApp.setClass(ConfigController.this,AboutApp.class);
        startActivity(aboutApp);
        finish();
      } //End of OnClick
    }); //End of setOnClickListener
    Log.i("End of method: ","Method seeInfoAboutApp ");
  } // End of seeInfoAboutApp

  /**
   * This method is activated when user clicks in GO button, tracing a route to the closest
   * health unity.
   * @param mapScreen actual View Object.
   *
   */

  public void goClicked(View mapScreen) throws IOException, JSONException { // Begin of goClicked

    Log.i("Begin of method: ","Method goClicked ");
    Intent routeActivity = new Intent();

    Toast.makeText(this, routeTraced , Toast.LENGTH_SHORT).show();
    routeActivity.setClass(ConfigController.this , RouteActivity.class);
    routeActivity.putExtra("numeroUs" , -1);
    startActivity(routeActivity);
    finish();
    Log.i("End of method: ","Method goClicked ");
  } // End of goClicked

  /**
   * This method list all the USs, by proximity of the user location, after the list button
   * is clicked.
   * @param mapScreen actual View Object.
   *
   */

  public void listMapsImageClicked(View mapScreen) { // Begin of litsMapsImageClicked

    Log.i("Begin of method: ","Method listMapsImageClicked ");
    Intent listOfHealth = new Intent();

    listOfHealth.setClass(this , ListOfHealthUnitsController.class);
    startActivity(listOfHealth);
    finish();
    Log.i("End of method: ","Method listMapsImageClicked ");
  } // End of listMapsIMageClicked

  /**
   * This method is activated when user is already in the configuration screen, and
   * try to open it again.Intent routeActivity = new Intent();
   * @param mapScreen actual View Object.
   *
   */

  public void openConfig(View mapScreen) { // Begin of openConfig

    Log.i("Method openConfig ","Begin of method");
    Toast.makeText(this , errorMessage , Toast.LENGTH_SHORT ).show();
    Log.i("Method openConfig ","End of method");
  } // End of openConfig

  /**
   * This method is activated when user clicks in the map button, and open a new map.
   * @param mapScreen actual View Object.
   *
   */

  public void openMap(View mapScreen) { // Begin of openMap

    Log.i("Begin of method: ","Method openMap ");
    Intent mapActivity = new Intent();

    mapActivity.setClass(this, MapScreenController.class);
    startActivity(mapActivity);
    finish();
    Log.i("End of method: ","Method openMap ");
  } // End of openMap
} // End of ConfigController class