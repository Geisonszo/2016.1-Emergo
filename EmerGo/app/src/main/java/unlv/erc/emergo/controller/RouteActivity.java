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
import com.google.android.gms.maps.model.RuntimeRemoteException;

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
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
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

public class RouteActivity  extends FragmentActivity implements
    OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

  static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
  public Boolean canceled = false;
  public int indexOfClosestHealthUnit = 0; //Index responsable for future searching methods
  public static final String SAMU_NUMBER = "tel:192"; // Actual number of public service SAMU
  public static final float MAP_ZOOM_LEVEL = 13.0f;
  private static final long MILLIS_IN_FUTURE = 3000;
  private static final long COUNTDOWN = 1000;
  private static final int SPLASH_TIME_OUT = 3400;
  private GoogleMap map;
  private Cursor result;
  private GoogleApiClient mapGoogleApiClient = null;
  EmergencyContactDao emergencyContactDao = new EmergencyContactDao(this);
  LatLng userLocation ;
  TextView timer;
  ImageView cancelCall;
  ImageView phone;
  ImageView userInformation;
  UserDao myDatabase;
  Intent intent;
  SupportMapFragment mapFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
      setContentView(R.layout.route_activity);

    //Line responsible for creating an connection with API client
    mapGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

    checkPermissions();
    linkButtonsAndXml();
    getExtraIntent();
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {

    if(googleMap != null){
      if (Build.VERSION.SDK_INT >= 23) {

        checkPermissions();
      } else {

        // nothing to do
      }
      map = googleMap;
    }else{
      // nothing to do
    }
  }


  private void getMapData() {

    //Block responsible for instaciante an Url and Download map previous info.

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
      setOnCallingConfig();
      startCountDown();
    } else {
      setDefaultCallConfig();
    }
  }

  private void setOnCallingConfig(){
    cancelCall.setVisibility(View.VISIBLE);
    phone.setVisibility(View.INVISIBLE);
  }

  private void setDefaultCallConfig(){
    timer.setText("");
    phone.setVisibility(View.VISIBLE);
    cancelCall.setVisibility(View.INVISIBLE);
  }


  @NonNull
  private Location getUserPosition(Location mapLastLocation) {

    Location location = new Location("");
    location.setLatitude(mapLastLocation.getLatitude());
    location.setLongitude(mapLastLocation.getLongitude());

    Log.i("RouteActivity","User Location:"+location);
    return location;
  }

  @Override
  public void onConnected(Bundle connectionHint) {

    if (Build.VERSION.SDK_INT >= 23) {

      checkPermissions();
    } else {

      // nothing to do
    }

    myDatabase = new UserDao(this);
    result = myDatabase.getUser();

    getMapFragment();

    //Line responsible for getting user last location
    try {
      Location mapLastLocation = LocationServices.FusedLocationApi.getLastLocation(mapGoogleApiClient);

      startBuildingInfoInMap(mapLastLocation);
    }catch (RuntimeException runTimeException){
      throw new LocationException("Erro em achar posição do usuário");
    }
  }

  private void getMapFragment() {
    map = mapFragment.getMap();
  }

  private void startBuildingInfoInMap(Location mapLastLocation){
    Location location = getUserPosition(mapLastLocation);

    HealthUnitController.setDistanceBetweenUserAndUs(HealthUnitController.getClosestHealthUnit(),
            location);
    selectindexOfClosestHealthUnit(location);

    userLocation = new LatLng(location.getLatitude(), location.getLongitude());

    setYourPositionOnMap();
    focusOnYourPosition();
    getDataBasedOnInternetStatus();
  }

  private void getDataBasedOnInternetStatus(){
    try {
      getMapData();
      setMarkerOfClosestUsOnMap();
    } catch (RuntimeException c) {
      Toast.makeText(this, getResources().getText(R.string.no_internet_connection).toString(), Toast.LENGTH_SHORT).show();
      launchMainScreenController();
      finish();
    }
  }

  private void launchMainScreenController(){
    Intent main = new Intent();
    main.setClass(this , MainScreenController.class);
    startActivity(main);
  }

  private void checkPermissions() {

    List<String> permissions = new ArrayList<>();
    String permissionMessage = getResources().getText(R.string.permission_message).toString();
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

      permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
      permissionMessage += "\n"+getResources().getText(R.string.map_permission_message);
    } else {
      // nothing to do
    }

    if (permissions.isEmpty() == false) {
      Toast.makeText(this, permissionMessage, Toast.LENGTH_LONG).show();
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

  private void getExtraIntent() {

    intent = getIntent();
    indexOfClosestHealthUnit =  intent.getIntExtra("numeroUs" , 0);
  }

  private void linkButtonsAndXml() {

    //Block responsible for linking xml components to class attributes
    userInformation = (ImageView) findViewById(R.id.userInformation);
    phone = (ImageView) findViewById(R.id.phone);
    cancelCall = (ImageView) findViewById(R.id.cancelCall);
    timer = (TextView) findViewById(R.id.timer);
  }

  private void setYourPositionOnMap() {

    map.addMarker(new MarkerOptions().position(userLocation)
            .title(getResources().getText(R.string.user_position).toString())
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }

  protected void onStart() {

    mapGoogleApiClient.connect();
    super.onStart();
  }

  protected void onStop() {

    mapGoogleApiClient.disconnect();
    super.onStop();
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

    new CountDownTimer(MILLIS_IN_FUTURE, COUNTDOWN) {

      public void onTick(long millisUntilFinished) {

        if (canceled == false) {

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

    if (canceled == false) {

      sendMessage();
      callSamu();
    } else {

      // nothing to do
    }
  }

  /**
   * This method will launch emergency mode screen when button Go is clicked
   * @param view
   */
  public void goClicked(View view){
    openMap();
  }

  /**
   * This method will cancel the call before its starts.
   * @param view
   */
  public void cancelCall(View view){
    canceled = true;
    cancelCall.setVisibility(View.INVISIBLE);
    phone.setVisibility(View.VISIBLE);
  }

  /**
   * This method will initializa a call to Samu.
   * @param view
     */
  public void startCall(View view){
    callSamu();
  }

  /**
   * This method will open settings controller, and show the info about the user.
   * @param view
     */
  public void openUserInformation(View view){
    Intent config = new Intent();
    config.setClass(RouteActivity.this , SettingsController.class);
    startActivity(config);
  }

  /**
   * This method will centralize map on user current position.
   * @param view
   */
  public void findSelfLocation(View view){
    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLocation.latitude,
            userLocation.longitude), 13.0f));
  }

  /**
   * This method sends help SMS to the emergency contact.
   */
  public void sendMessage() {
    Cursor result = emergencyContactDao.getEmergencyContact();

    if (result.getCount() != 0) {

      try {

        //Control structure responsible to send messages to the emergency contacts
        while (result.moveToNext()) {

          SmsManager.getDefault().sendTextMessage(result.getString(2),null,
                  result.getString(1) +
                          getResources().getText(R.string.help_request_message).toString(),
                  null,null);
        }
        Toast.makeText(getApplicationContext(),getResources()
                .getText(R.string.help_contacted_message).toString(), Toast.LENGTH_LONG).show();
      } catch (Exception exception) {

        Toast.makeText(getApplicationContext(),getResources()
                .getText(R.string.error_on_contacting_message), Toast.LENGTH_LONG)
                .show();
      }
    } else {

      Toast.makeText(getApplicationContext(), getResources()
              .getText(R.string.no_contact_added).toString(), Toast.LENGTH_LONG).show();
    }
  }

  private void callSamu() {

    Intent callIntent = new Intent(Intent.ACTION_CALL);
    callIntent.setData(Uri.parse(SAMU_NUMBER));
    startActivity(callIntent);
  }

  private void openMap() {

    canceled = true;
    Intent mapScreen = new Intent();
    mapScreen.setClass(RouteActivity.this , MapScreenController.class);
    startActivity(mapScreen);
    finish();
  }

  private void setMarkerOfClosestUsOnMap() {

    //Block responsible for adding a marker on map, in the ṕosition of the closest US

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
        userLocation.longitude), MAP_ZOOM_LEVEL));
  }


  private String getDirectionsUrl(LatLng origin,LatLng dest) {

    String stringOrigin = "origin=" + origin.latitude + "," + origin.longitude;
    String stringDestination = "destination=" + dest.latitude + "," + dest.longitude;
    String sensor = "sensor=false";
    String parameters = stringOrigin + "&" + stringDestination + "&" + sensor;
    String output = "json";
    String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

    Log.i("RouteActivity","URL: "+url);
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

      Log.i("DowloadTask","Data: "+data);
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

  private String downloadUrl(String strUrl) throws IOException {

    assert strUrl != null : "Erro: strUrl is null";

    String data = "";
    InputStream inStream = null;
    HttpURLConnection urlConnection = null;

    try {

      urlConnection = getHttpUrlConnection(strUrl, urlConnection);
      inStream = urlConnection.getInputStream();

      data = createBufferReaderLogic(data, inStream);
    } catch (Exception e) {

      Log.d("Error downloading url", e.toString());
    } finally {

      inStream.close();
      urlConnection.disconnect();
    }

    Log.i("RouteActivity.java", "Data: "+data);
    return data;
  }

  @NonNull
  private String createBufferReaderLogic(String data, InputStream inStream) throws IOException {

    BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
    StringBuffer sb  = new StringBuffer();
    concatenateBufferRead(br, sb);

    data = sb.toString();
    br.close();
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

    assert urlConnection != null : "Error: urlConnection is null";

    URL url = new URL(strUrl);
    urlConnection = (HttpURLConnection) url.openConnection();
    urlConnection.connect();

    Log.i("RouteActivity.java","Url Connection"+urlConnection.toString());
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

      Log.i("ParserTask","Routes nullity: "+routes.isEmpty());
      return routes;
    }

    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {

      assert result != null : "Error: result is null";

      ArrayList<LatLng> points = null;

      for (int i = 0; i < result.size();i++) {

        points = new ArrayList<LatLng>();

        List<HashMap<String, String>> path = result.get(i);

        for (int j = 0; j < path.size();j++) {

          HashMap<String,String> point = path.get(j);

          double lat = Double.parseDouble(point.get("lat"));
          double lng = Double.parseDouble(point.get("lng"));
          LatLng position = new LatLng(lat, lng);

          points.add(position);
        }
      }
      map.addPolyline(drawPolyline(points));
    }

    private PolylineOptions drawPolyline(ArrayList<LatLng> points){
      PolylineOptions lineOptions = new PolylineOptions();

      lineOptions.addAll(points);
      lineOptions.width(7);
      lineOptions.color(Color.BLUE);

      return lineOptions;
    }
  }




  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                         int[] grantResults) {

    assert permissions != null : "Error: permissions are null";

    switch (requestCode) {
      case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
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
        break;
      default:
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

  }

  private Boolean getPermission(Map<String, Integer> perms, Boolean storage) {

    assert perms != null : "Error: perms are null";

    try {

      int externalPermission = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE);
      int grantedPermission = PackageManager.PERMISSION_GRANTED;
      storage = externalPermission == grantedPermission;
    } catch (RuntimeException ex) {

      Toast.makeText(this , "É necessário ter a permissão" , Toast.LENGTH_LONG).show();
      Intent main = new Intent();
      main.setClass(this , MainScreenController.class);
      startActivity(main);
      finish();
    }

    Log.i("RouteActivity","Storage: "+storage);
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

    if(!title.isEmpty() && !message.isEmpty()){
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setCancelable(true);
      builder.setTitle(title);
      builder.setMessage(message);
      builder.show();
    }
  }

  // Exceptions

  public class CursorConnectionException extends NullPointerException{
    public CursorConnectionException(String error){
      super(error);
    }
  }

  public class LocationException extends RuntimeException{
    public LocationException(String error){
      super(error);
    }
  }

}

