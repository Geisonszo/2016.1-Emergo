/********************
 * Class name: MapScreenController (.java)
 *
 * Purpose: The purpose of this class is to show the map of the region and its health units.
 ********************/

package unlv.erc.emergo.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helper.Services;
import unlv.erc.emergo.R;

public class MapScreenController extends FragmentActivity implements OnMapReadyCallback ,
        GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

  //Google Maps API.
  private GoogleMap map;
  private GoogleApiClient mapGoogleApiClient = null;

  /**
   * Called when a marker has been clicked or tapped.
   * @param marker The marker that was clicked.
   * @return The answer of event.
   */

  @Override
  public boolean onMarkerClick(Marker marker) {

    final String POSITION_MESSAGE = "Posição";

    assert marker != null : "marker can't be null";

    boolean valid = false;

    // Set information about health units on the screen.
    for (int aux = 0 ; aux < HealthUnitController.getClosestHealthUnit().size() ; aux++) {

      if (marker.getTitle().compareTo(HealthUnitController.getClosestHealthUnit().get(aux)
                .getNameHospital()) == 0) {

        // Set an intent to bind MapScreen to InformationHealthUnitScreen.
        Intent information = new Intent();
        information.setClass(MapScreenController.this, InformationHealthUnitScreenController.class);
        information.putExtra(POSITION_MESSAGE, aux);
        startActivity(information);
        finish();
      } else {

        // Nothing to do
      }
    }
    return valid;
  }

  //Code of Google services that makes the request of several permissions.
  final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

  /**
   * This method callback for the result from requesting permissions.
   * @param requestCode  The request code passed.
   * @param permissions The requested permissions. Never null.
   * @param grantResults The grant results for the corresponding permissions. Never null.
   */

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {

    final String PERMISION_MESSAGE = "É necessário ter a permissão";

    switch (requestCode) {

      case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
        Map<String, Integer> perms = new HashMap<>();

        perms.put(Manifest.permission.ACCESS_FINE_LOCATION,
                PackageManager.PERMISSION_GRANTED);

        // Set permissions on grantResults.
        for (int i = 0; i < permissions.length; i++) {

          perms.put(permissions[i], grantResults[i]);
        }

        Boolean location = false;
        Boolean storage = false;

        location = perms.get(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
        try {

          storage = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                  == PackageManager.PERMISSION_GRANTED;
        } catch (RuntimeException ex) {

          Toast.makeText(this , PERMISION_MESSAGE, Toast.LENGTH_LONG).show();

          // Set intent to bind current screen to MainScreen.
          Intent main = new Intent();
          main.setClass(this , MainScreenController.class);
          startActivity(main);
          finish();
        }
        messageAboutPermission();
      }
      break;

      default:
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  /**
   * This method shows a route to the health unit clicked.
   * @param mapScreen The map view passed.
   */
  public void goClicked(View mapScreen) throws IOException, JSONException {

    final String INFORMATION_MESSAGE = "numeroUs";
    final String ROUTE_TRACED = "Rota mais próxima traçada";

    assert mapScreen != null : "mapScreen can not be null";

    Toast.makeText(this, ROUTE_TRACED, Toast.LENGTH_SHORT).show();

    // Set intent to bind MapScreen to RouteActivity
    Intent routeActivity = new Intent();
    routeActivity.setClass(MapScreenController.this , RouteActivity.class);
    routeActivity.putExtra(INFORMATION_MESSAGE , -1);
    startActivity(routeActivity);
    finish();
  }

  /**
   * This method shows the list of health units image on map.
   * @param mapscreen The map view passed.
   */

  public void listMapsImageClicked(View mapscreen) {

    assert mapscreen != null : "mapscreen can not be null";

    // Set intent to bind current screen to List of Health unit screen.
    Intent listOfHealth = new Intent();
    listOfHealth.setClass(this, ListOfHealthUnitsController.class);
    startActivity(listOfHealth);
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }

  private static final int FINAL_VERSION_SDK = 23;

  @Override
  public void onConnected(Bundle connectionHint) {

    final String POSITION_NOT_LOCATED_MESSAGE = "Não foi possível localizar sua " +
            "posição";

    final Services services = new Services();

    // Verify the SDK version and check the permissions.
    if (Build.VERSION.SDK_INT >= FINAL_VERSION_SDK) {

      checkPermissions();
    } else {

      // Nothing to do
    }

    Location mapLastLocation = LocationServices.FusedLocationApi.getLastLocation(
            mapGoogleApiClient);

    // Check the user's last location.
    if (mapLastLocation != null) {

      // Instantiate an object with the user location
      LatLng userLatLng = new LatLng(mapLastLocation.getLatitude() , mapLastLocation.getLongitude());

      // Focus on user position
      focusOnSelfPosition(userLatLng);
      services.setMarkersOnMap(map , HealthUnitController.getClosestHealthUnit());

    } else {

      // Shows a message when the position is not found.
      Toast.makeText(this, POSITION_NOT_LOCATED_MESSAGE, Toast.LENGTH_SHORT).show();
      Intent mainScreen = new Intent();

      mainScreen.setClass(this, MainScreenController.class);
      startActivity(mainScreen);
      finish();
    }
  }

  @Override
  public void onConnectionSuspended(int aux) {

  }

  @Override
  public void onMapReady(GoogleMap googleMap) {

    // // Verify the SDK version and check the permissions.
    if (Build.VERSION.SDK_INT >= FINAL_VERSION_SDK) {

      checkPermissions();
    } else {

      // Nothing to do
    }

    map = googleMap;
  }

  public void openSearch(View mapScreen) {

    assert mapScreen != null : "mapScreen can not be null";

    // Set intent to bind current screen to SearchHealthUnit screen.
    Intent openSearch = new Intent();
    openSearch.setClass(this , SearchHealthUnitActivity.class);
    startActivity(openSearch);
  }

  public void openMap(View mapScreen) {

  }

  public void openConfig(View mapScreen) {

    assert mapScreen != null : "mapScreen can not be null";

    // Set intent to bind MapScreen to Settings screen.
    Intent config = new Intent();
    config.setClass(MapScreenController.this , SettingsController.class);
    startActivity(config);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    setContentView(R.layout.map_screen);
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
    map = mapFragment.getMap();
    map.setOnMarkerClickListener(this);

    // Verify if the mapGoogleApiClient is null, if yes set the mapGoogleApiClient.
    if (mapGoogleApiClient == null) {

      mapGoogleApiClient = new GoogleApiClient.Builder(this)
              .addConnectionCallbacks(this)
              .addOnConnectionFailedListener(this)
              .addApi(LocationServices.API)
              .build();
    } else {

      // Nothing to do
    }
  }

  protected void onStart() {

    mapGoogleApiClient.connect();
    super.onStart();
  }

  protected void onStop() {

    mapGoogleApiClient.disconnect();
    super.onStop();
  }

  /**
   * This method shows the approved permission message and request permission method for location
   * and storage.
   */

  private void messageAboutPermission() {

    final String PERMISION_APPROVED_MESSAGE = "Permissão aprovada";
    final String REQUEST_PERMISION_MESSAGE = "Permita ter o acesso para te localizar";

    boolean location = false;
    boolean storage = false;

    // Verify if the location an storage permission is available, if not, request permission.
    if (location && storage) {

      // Shows up the message when the permission is approved.
      Toast.makeText(this, PERMISION_APPROVED_MESSAGE, Toast.LENGTH_SHORT).show();
    } else {
      // Shows up the message when is need to request the permission.
      Toast.makeText(this,REQUEST_PERMISION_MESSAGE, Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * This method focus on map the location of the user.
   * @param userLatLng The location (latitude and longitude) of the user
   */

  private void focusOnSelfPosition(LatLng userLatLng) {

    final String yourPosition = "Sua posição";

    assert userLatLng != null : "userLatLng can not be null";

    map.addMarker(new MarkerOptions().position(userLatLng).title(yourPosition)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    map.animateCamera(CameraUpdateFactory.newLatLngZoom( new LatLng(userLatLng.latitude,
            userLatLng.longitude), 13.0f));
  }

  /**
   * This method check the permission to use the location.
   */

  private void checkPermissions() {

    List<String> permissions = new ArrayList<>();
    String message = "Permissão";

    // Verify that the application has permission to use the location.
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

      permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
      message += "\nTer acesso a localização no mapa";
    } else {

      //Nothing to do
    }

    // Verify if the permissions array is empty, if yes, request permission.
    if (!permissions.isEmpty()) {

      // Shows up a permission message.
      Toast.makeText(this, message, Toast.LENGTH_LONG).show();
      String[] params = permissions.toArray(new String[permissions.size()]);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        requestPermissions(params, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
      } else {

        // Nothing to do
      }
    } else {

      // Nothing to do
    }
  }

}

