/********************
 * Class name: RouteActivity (.java)
 *
 * Purpose: The purpose of this class is show the route to the heath unit on the map.
 ********************/

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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import dao.EmergencyContactDao;
import dao.UserDao;

import helper.DirectionsJSONParser;

import org.json.JSONObject;

import unlv.erc.emergo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static unlv.erc.emergo.R.id.map;

public class RouteActivity  extends FragmentActivity implements View.OnClickListener,
    OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

  GoogleMap map;
  static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
  public Boolean canceled = false;
  public int indexOfClosestHealthUnit = 0; //Index responsable for future searching methods
  public String samuNumber = "tel:192"; // Actual number of public service SAMU
  private Cursor result;
  private Location mapLastLocation;
  private GoogleApiClient mapGoogleApiClient = null;
  private static final int SPLASH_TIME_OUT = 3400;
  ArrayList<LatLng> pointsOfRoute = new ArrayList<>();
  EmergencyContactDao emergencyContactDao = new EmergencyContactDao(this);
  LatLng userLocation ;
  TextView timer;
  ImageView user;
  ImageView cancelCall;
  ImageView phone;
  ImageView userInformation;
  Button buttonGo;
  Button selfLocation;
  UserDao myDatabase;
  Intent intent;
  SupportMapFragment mapFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.route_activity);

    if (mapGoogleApiClient == null) {

      mapGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    } else {
      // nothing to do
    }

    checkPermissions();
    linkButtonsAndXml();
    getExtraIntent();
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
    } else {

      // nothing to do
    }

    this.mapLastLocation = LocationServices.FusedLocationApi.getLastLocation(mapGoogleApiClient);

    myDatabase = new UserDao(this);
    result = myDatabase.getUser();

    getMapFragment();
    Location location = getUserPosition(mapLastLocation);

    HealthUnitController.setDistanceBetweenUserAndUs(HealthUnitController.getClosestHealthUnit(),
        location);
    selectindexOfClosestHealthUnit(location);

    userLocation = new LatLng(location.getLatitude(), location.getLongitude());

    setYourPositionOnMap();
    focusOnYourPosition();
    pointsOfRoute.add(userLocation);

    try {

      getMapData();
      setMarkerOfClosestUsOnMap();
    } catch (RuntimeException c) {

      Toast.makeText(this, "Sem internet", Toast.LENGTH_SHORT).show();

      Intent main = new Intent();

      main.setClass(this , MainScreenController.class);
      startActivity(main);
      finish();
    }
  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onMapReady(GoogleMap googleMap) {

    if (Build.VERSION.SDK_INT >= 23) {

      checkPermissions();
    } else {

      // nothing to do
    }

    map = googleMap;
  }


  @NonNull
  private Location getUserPosition(Location mapLastLocation) {

    Location location = new Location("");
    location.setLatitude(mapLastLocation.getLatitude());
    location.setLongitude(mapLastLocation.getLongitude());

    return location;
  }

  private void getExtraIntent() {

    intent = getIntent();
    indexOfClosestHealthUnit =  intent.getIntExtra("numeroUs" , 0);
  }

  private void getMapFragment() {
    map = mapFragment.getMap();
  }

  private void getMapData() {

    String urlInitial =  getDirectionsUrl(userLocation, new LatLng(HealthUnitController
        .getClosestHealthUnit()
        .get(indexOfClosestHealthUnit).getLatitude(), HealthUnitController.getClosestHealthUnit()
        .get(indexOfClosestHealthUnit).getLongitude()));
    DownloadTask downloadTask = new DownloadTask();
    downloadTask.execute(urlInitial);
  }

  private void selectindexOfClosestHealthUnit(Location location) {

    if (indexOfClosestHealthUnit == -1) {

      indexOfClosestHealthUnit = HealthUnitController.selectClosestUs(HealthUnitController
        .getClosestHealthUnit(), location);
      cancelCall.setVisibility(View.VISIBLE);
      phone.setVisibility(View.INVISIBLE);
      startCountDown();
    } else {

      timer.setText("");
      phone.setVisibility(View.VISIBLE);
      cancelCall.setVisibility(View.INVISIBLE);
    }
  }

  private void linkButtonsAndXml() {

    buttonGo = (Button) findViewById(R.id.buttonGo);
    buttonGo.setOnClickListener(this);
    userInformation = (ImageView) findViewById(R.id.userInformation);
    userInformation.setOnClickListener(this);
    selfLocation = (Button) findViewById(R.id.selfLocation);
    selfLocation.setOnClickListener(this);
    phone = (ImageView) findViewById(R.id.phone);
    phone.setOnClickListener(this);
    cancelCall = (ImageView) findViewById(R.id.cancelarLigacao);
    cancelCall.setOnClickListener(this);
    timer = (TextView) findViewById(R.id.timer);
    user = (ImageView) findViewById(R.id.userInformation);
    user.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        showInformationUser();
      }
    });
  }

  private void startCountDown() {

    new Handler().postDelayed(new Runnable() {

      @Override
      public void run() {
        openCountDown();
      }

    }, SPLASH_TIME_OUT);

  }

  private void openCountDown() {

    new CountDownTimer(3000 , 1000) {

      public void onTick(long millisUntilFinished) {

        if (!canceled) {

          long milis = millisUntilFinished / 1000;
          String time =  String.valueOf(milis) ;
          timer.setText(time);
        } else {

          timer.setText("");
        }

      }

      public void onFinish() {
        timer.setText("");
        cancelCall.setVisibility(View.INVISIBLE);
        phone.setVisibility(View.VISIBLE);
      }
    }.start();

    if (!canceled) {

      sendMessage();
      callSamu();
    } else {

      // nothing to do
    }
  }

  private void callSamu() {

    Intent callIntent = new Intent(Intent.ACTION_CALL);
    callIntent.setData(Uri.parse(samuNumber));
    startActivity(callIntent);
  }

  @Override
  public void onClick(View routeActivity) {

    //Open emergency mode
    if (routeActivity.getId() == R.id.buttonGo) {
      openMap();
    } else {

      // nothing to do
    }

    //Open map on user location
    if (routeActivity.getId() == R.id.selfLocation) {

      map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLocation.latitude,
          userLocation.longitude), 13.0f));
    } else {
      // nothing to do
    }

    //Open user info
    if (routeActivity.getId() == R.id.userInformation) {

      Intent config = new Intent();
      config.setClass(RouteActivity.this , ConfigController.class);
      startActivity(config);
    } else {

      // nothing to do
    }

    if (routeActivity.getId() == R.id.cancelCall) {

      cancelCalling();
    } else {

      // nothing to do
    }

    //Call samu method
    if (routeActivity.getId() == R.id.phone) {

      callSamu();
    } else {

      // nothing to do
    }
  }

  private void cancelCalling() {

    canceled = true;
    cancelCall.setVisibility(View.INVISIBLE);
    phone.setVisibility(View.VISIBLE);
  }

  private void openMap() {

    canceled = true;
    Intent mapScreen = new Intent();
    mapScreen.setClass(RouteActivity.this , MapScreenController.class);
    startActivity(mapScreen);
    finish();
  }

  private void setMarkerOfClosestUsOnMap() {

    map.addMarker(new MarkerOptions().position(new LatLng(HealthUnitController
        .getClosestHealthUnit().get(indexOfClosestHealthUnit).getLatitude(),
        HealthUnitController.getClosestHealthUnit()
        .get(indexOfClosestHealthUnit).getLongitude())).title(HealthUnitController
        .getClosestHealthUnit().get(indexOfClosestHealthUnit).getNameHospital() + "")
        .snippet(HealthUnitController.getClosestHealthUnit().get(indexOfClosestHealthUnit)
          .getUnitType()));
  }

  private void focusOnYourPosition() {
    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLocation.latitude,
        userLocation.longitude), 13.0f));
  }

  private void setYourPositionOnMap() {
    final String yourPosition = "Sua posição";

    map.addMarker(new MarkerOptions().position(userLocation).title(yourPosition)
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
  }


  private String getDirectionsUrl(LatLng origin,LatLng dest) {

    String stringOrigin = "origin=" + origin.latitude + "," + origin.longitude;
    String stringDestination = "destination=" + dest.latitude + "," + dest.longitude;
    String sensor = "sensor=false";
    String parameters = stringOrigin + "&" + stringDestination + "&" + sensor;
    String output = "json";
    String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

    return url;
  }


  private class DownloadTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... url) {
      String data = "";
      try {

        data = downloadUrl(url[0]);
      } catch (Exception e) {

        Log.d("Background Task",e.toString());
      }

      return data;
    }

    @Override
    protected void onPostExecute(String result) {

      super.onPostExecute(result);

      ParserTask parserTask = new ParserTask();
      parserTask.execute(result);
    }
  }

  /**
   * This method shows the medical information about the user.
   */

  public void showInformationUser() {

    result.moveToFirst();

    if (result.getCount() == 0) {

      Toast.makeText(this,"Não existe nenhum cadastro no momento.",Toast.LENGTH_LONG).show();
    } else {

      showMessageDialog("Notificações do Usuário","Nome: " + result.getString(1) + "\n"
          + "Data de Aniversário: " + result.getString(2) + "\n"
          + "Tipo Sanguíneo: " + result.getString(3) + "\n"
          + "Cardiaco: " + result.getString(4) + "\n"
          + "Diabetico: " + result.getString(5) + "\n"
          + "Hipertenso: " + result.getString(6) + "\n"
          + "Soropositivo: " + result.getString(7) + "\n"
          + "Observações Especiais: " + result.getString(8));
    }
  }

  private String downloadUrl(String strUrl) throws IOException {

    String data = "";
    InputStream inStream = null;
    HttpURLConnection urlConnection = null;

    try {

      urlConnection = getHttpUrlConnection(strUrl, urlConnection);
      inStream = urlConnection.getInputStream();

      BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
      StringBuffer sb  = new StringBuffer();
      concatenateBufferRead(br, sb);

      data = sb.toString();
      br.close();
    } catch (Exception e) {

      Log.d("Error downloading url", e.toString());
    } finally {

      inStream.close();
      urlConnection.disconnect();
    }

    return data;
  }

  private void concatenateBufferRead(BufferedReader br, StringBuffer sb) throws IOException {

    String line = "";

    while ( ( line = br.readLine())  != null) {

      sb.append(line);
    }
  }

  @NonNull
  private HttpURLConnection getHttpUrlConnection(String strUrl, HttpURLConnection urlConnection)
      throws IOException {

    URL url = new URL(strUrl);
    urlConnection = (HttpURLConnection) url.openConnection();
    urlConnection.connect();
    return urlConnection;
  }

  public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>> {

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

      JSONObject jsonObject;
      List<List<HashMap<String, String>>> routes = null;
      try {

        jsonObject = new JSONObject(jsonData[0]);
        DirectionsJSONParser parser = new DirectionsJSONParser();

        routes = parser.parse(jsonObject);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return routes;
    }

    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {

      ArrayList<LatLng> points = null;
      PolylineOptions lineOptions = null;

      for (int i = 0; i < result.size();i++) {

        points = new ArrayList<LatLng>();
        lineOptions = new PolylineOptions();

        List<HashMap<String, String>> path = result.get(i);

        for (int j = 0; j < path.size();j++) {

          HashMap<String,String> point = path.get(j);

          double lat = Double.parseDouble(point.get("lat"));
          double lng = Double.parseDouble(point.get("lng"));
          LatLng position = new LatLng(lat, lng);

          points.add(position);
        }

        lineOptions.addAll(points);
        lineOptions.width(7);
        lineOptions.color(Color.BLUE);
      }
      map.addPolyline(lineOptions);
    }
  }

  private void checkPermissions() {

    List<String> permissions = new ArrayList<>();
    String message = "Permissão";
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {

      permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
      message += "\nTer acesso a localização no mapa";
    } else {
      // nothing to do
    }

    if (!permissions.isEmpty()) {
      Toast.makeText(this, message, Toast.LENGTH_LONG).show();
      String[] params = permissions.toArray(new String[permissions.size()]);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(params, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
      } else {
        // nothing to do
      }

    } else {
      // nothing to do
    }
  }

  /**
   * This method sends help SMS to the emergency contact.
   */

  public void sendMessage() {
    Cursor result = emergencyContactDao.getEmergencyContact();

    if (result.getCount() != 0) {

      try {

        while (result.moveToNext()) {

          SmsManager.getDefault().sendTextMessage(result.getString(2),null,
              result.getString(1) + ", Estou precisando de ajuda urgente!",null,null);
        }
        Toast.makeText(getApplicationContext(),"Ajuda a caminho!", Toast.LENGTH_LONG).show();
      } catch (Exception exception) {

        Toast.makeText(getApplicationContext(),"Impossivel encaminhar o SMS", Toast.LENGTH_LONG)
            .show();
      }
    } else {

      Toast.makeText(getApplicationContext(),"Nenhum contato adicionado", Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                         int[] grantResults) {

    switch (requestCode) {
      case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
        Map<String, Integer> perms = new HashMap<>();
        perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
        for (int i = 0; i < permissions.length; i++) {

          perms.put(permissions[i], grantResults[i]);
        }

        Boolean location = false;
        Boolean storage = false;
        location = perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager
            .PERMISSION_GRANTED;
        storage = getPermission(perms, storage);
        messageAboutPermission(location, storage);
      }
      break;
      default:
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }

  private Boolean getPermission(Map<String, Integer> perms, Boolean storage) {

    try {

      storage = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
          .PERMISSION_GRANTED;
    } catch (RuntimeException ex) {

      Toast.makeText(this , "É necessário ter a permissão" , Toast.LENGTH_LONG).show();
      Intent main = new Intent();
      main.setClass(this , MainScreenController.class);
      startActivity(main);
      finish();
    }
    return storage;
  }

  private void messageAboutPermission(Boolean location, Boolean storage) {
    if (location && storage) {
      Toast.makeText(this, "Permissão aprovada", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(this,"Permita ter o acesso para te localizar", Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * This method shows the message dialog.
   * @param title Title of the message
   * @param message The message
   */

  public void showMessageDialog(String title,String message) {

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setCancelable(true);
    builder.setTitle(title);
    builder.setMessage(message);
    builder.show();
  }
}

