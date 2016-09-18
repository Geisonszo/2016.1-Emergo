package unlv.erc.emergo.controller;

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
import helper.Services;

import org.json.JSONException;

import unlv.erc.emergo.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapScreenController extends FragmentActivity implements OnMapReadyCallback ,
        GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

  static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
  final String yourPosition = "Sua posição";
  private GoogleMap map;
  private Services services = new Services();
  private Location location;
  private Location mapLastLocation;
  private GoogleApiClient mapGoogleApiClient = null;

  @Override
  public boolean onMarkerClick(Marker marker) {

    for (int aux = 0 ; aux < HealthUnitController.getClosestHealthUnit().size() ; aux++) {
      if (marker.getTitle().toString().compareTo(HealthUnitController.getClosestHealthUnit()
              .get(aux)
                .getNameHospital()) == 0) {
        Intent information = new Intent();
        information.setClass(MapScreenController.this , InformationUsScreenController.class);
        information.putExtra("position" , aux);
        startActivity(information);
        finish();
      }
    }
    return false;
  }

  public void goClicked(View mapScreen) throws IOException, JSONException {

    final String routeTraced = "Rota mais próxima traçada";

    Toast.makeText(this, routeTraced , Toast.LENGTH_SHORT).show();
    Intent routeActivity = new Intent();
    routeActivity.setClass(MapScreenController.this , RouteActivity.class);
    routeActivity.putExtra("numeroUs" , -1);
    startActivity(routeActivity);
    finish();
  }

  public void listMapsImageClicked(View mapscreen) {

    Intent listOfHealth = new Intent();
    listOfHealth.setClass(this, ListOfHealthUnitsController.class);
    startActivity(listOfHealth);
  }

  public void open_search(View mapScreen) {

    Intent openSearch = new Intent();
    openSearch.setClass(this , SearchUsController.class);
    startActivity(openSearch);
  }

  public void openMap(View mapScreen) {

  }

  public void openConfig(View mapScreen) {

    Intent config = new Intent();
    config.setClass(MapScreenController.this , ConfigController.class);
    startActivity(config);
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }

  private void messageAboutPermission(Boolean location, Boolean storage) {

    if (location && storage) {

      Toast.makeText(this, "Permissão aprovada", Toast.LENGTH_SHORT).show();
    } else {

      Toast.makeText(this,"Permita ter o acesso para te localizar",
                    Toast.LENGTH_SHORT).show();
    }
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

    if (mapGoogleApiClient == null) {

      mapGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
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

  @Override
  public void onConnected(Bundle connectionHint) {

    if (Build.VERSION.SDK_INT >= 23) {

      checkPermissions();
    }

    this.mapLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mapGoogleApiClient);

    if (mapLastLocation != null) {

      location = mapLastLocation;
      LatLng userLatLng = new LatLng(location.getLatitude() , location.getLongitude());

      focusOnSelfPosition(userLatLng);
      services.setMarkersOnMap(map , HealthUnitController.getClosestHealthUnit());

    } else {

      Toast.makeText(this, "Não foi possível localizar sua posição",
                    Toast.LENGTH_SHORT).show();
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

    if (Build.VERSION.SDK_INT >= 23) {

      checkPermissions();
    }

    map = googleMap;
  }


  private void focusOnSelfPosition(LatLng userLatLng) {

    map.addMarker(new MarkerOptions().position(userLatLng).title(yourPosition)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    map.animateCamera(CameraUpdateFactory.newLatLngZoom( new LatLng(userLatLng.latitude,
            userLatLng.longitude), 13.0f));
  }


  private void checkPermissions() {

    List<String> permissions = new ArrayList<>();
    String message = "Permissão";

    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

      permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
      message += "\nTer acesso a localização no mapa";
    }

    if (!permissions.isEmpty()) {

      Toast.makeText(this, message, Toast.LENGTH_LONG).show();
      String[] params = permissions.toArray(new String[permissions.size()]);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        requestPermissions(params, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

    switch (requestCode) {

      case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
        Map<String, Integer> perms = new HashMap<>();

        perms.put(Manifest.permission.ACCESS_FINE_LOCATION,
                        PackageManager.PERMISSION_GRANTED);

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

          Toast.makeText(this , "É necessário ter a permissão" ,
                          Toast.LENGTH_LONG).show();
          Intent main = new Intent();
          main.setClass(this , MainScreenController.class);
          startActivity(main);
          finish();
        }
        messageAboutPermission(location, storage);
      }
          break;

      default:
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }
}

