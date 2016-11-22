/*****************************
 * Class name: HealthUnit (.java)
 *
 * Purpose: Represent the data from a health units's.
 ****************************/

package unlv.erc.emergo.model;

import android.util.Log;

import com.orm.SugarRecord;

import java.util.regex.Pattern;

public class HealthUnit extends SugarRecord implements HealthUnitInfo{

  private Double latitude = 0.0;
  private Double longitude = 0.0;
  private String nameHospital = "";
  private String healthUnitType = "";
  private String addressNumber = "";
  private String district = "";
  private String state = "";
  private String city = "";
  private Float distance = 0f;

  public HealthUnit(){

  }

  /**
   * This methods set the information about the health unit.
   * @param latitude latitude coordinate of health unit
   * @param longitude longitude coordinate of health unit
   * @param nameHospital The name of health unit
   * @param healthUnitType type of health unit
   * @param addressNumber Address of health unit
   * @param district health unit district
   * @param state health unit state
   * @param city health unit city
   */

  public HealthUnit(Double latitude, Double longitude, String nameHospital, String healthUnitType,
                      String addressNumber, String district, String state, String city) {

    try {
      setLatitude(latitude);
      setLongitude(longitude);
      setNameHospital(nameHospital);
      setUnitType(healthUnitType);
      setAddressNumber(addressNumber);
      setDistrict(district);
      setState(state);
      setCity(city);
    }catch (NullPointerException exception){
      Log.i("Impossível cadastrar HU",nameHospital+"");
      exception.printStackTrace();
    }
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    try {
      if(verifyHealthUnitLatitude(latitude)) {
        this.latitude = latitude;
        Log.i("latitude setted","HealthUnit.class");
      }
    }catch (NullPointerException exception){
      exception.printStackTrace();
    }
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    try{
      if(verifyHealthUnitLongitude(longitude)) {
        this.longitude = longitude;
        Log.i("longitude setted", "HealthUnit.class");
      }
    }catch (NullPointerException exception){
      exception.printStackTrace();
    }
  }

  public String getNameHospital() {
    return nameHospital;
  }

  public void setNameHospital(String nameHospital) {
    try{
     if(checkStringIntegrity(nameHospital) == true) {
       this.nameHospital = nameHospital;
       Log.i("hospitals name setted", "HealthUnit.class");
     }
    }catch (NullPointerException exception){
        exception.printStackTrace();
    }
  }

  public String getUnitType() {
    return healthUnitType;
  }

  public void setUnitType(String healthUnitType) {
    try{
      if (checkStringIntegrity(healthUnitType)) {
        this.healthUnitType = healthUnitType;
        Log.i("health unit type setted", "HealthUnit.class");
      }
    }catch (NullPointerException exception){
        exception.printStackTrace();
    }
  }

  public String getAddressNumber() {
    return addressNumber;
  }

  public void setAddressNumber(String addressNumber) {
    try{
      if (checkStringIntegrity(addressNumber) == true) {
        this.addressNumber = addressNumber;
        Log.i("adress setted", "HealthUnit.class");
      }
    }catch (NullPointerException exception){
      exception.printStackTrace();
    }
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    try{
      if (checkStringIntegrity(district)) {
        this.district = district;
        Log.i("district setted", "HealthUnit.class");
      }
    }catch (NullPointerException exception){
        exception.printStackTrace();
    }
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    try{
      if(checkStringIntegrity(state) == true) {
        this.state = state;
        Log.i("state setted", "HealthUnit.class");
      }
    }catch (NullPointerException exception){
        exception.printStackTrace();
    }
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    try{
      if(checkStringIntegrity(city) == true) {
        this.city = city;
        Log.i("city setted", "HealthUnit.class");
      }
    }catch (NullPointerException exception){
        exception.printStackTrace();
    }
  }

  public Float getDistance() {
    return distance;
  }

  public void setDistance(Float distance) {
    try{
      this.distance = distance;
      Log.i("distance setted","HealthUnit.class");
    }catch (NullPointerException exception){
        exception.printStackTrace();
    }
  }

  private boolean checkStringIntegrity(String nameHospital) {

    for(int i = 0; i < nameHospital.length(); i++){
      if(Character.isLetter(nameHospital.charAt(i)) == false &&
              Character.isDigit(nameHospital.charAt(i)) == false){
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean verifyHealthUnitName(String name) {
    if (name.length() <= HU_NAME_MAX_SIZE) {
      return true;
    }
    return false;
  }

  @Override
  public boolean verifyHealthUnitType(String type) {
    if (type.length() <= HU_TYPE_MAX_SIZE){
      return true;
    }
    return false;
  }

  @Override
  public boolean verifyHealthUnitAdress(String adress) {
    if(adress.length() <= HU_ADRESS_MAX_SIZE) {
      return true;
    }
    return false;
  }

  @Override
  public boolean verifyHealthUnitDistrict(String district) {
    if(district.length() <= HU_DISTRICT_MAX_SIZE){
      return true;
    }
    return false;
  }

  @Override
  public boolean verifyHealthUnitState(String state) {
    if(state.length() <= HU_STATE_MAX_SIZE){
     return true;
    }
    return false;
  }

  @Override
  public boolean verifyHealthUnitCity(String city) {
    if(city.length() <= HU_CITY_MAX_SIZE){
      return true;
    }
    return false;
  }

  @Override
  public boolean verifyHealthUnitLatitude(double latitude) {
    if (latitude <= HU_MAX_LATITUDE && latitude >= -HU_MAX_LATITUDE){
      return true;
    }
    return false;
  }

  @Override
  public boolean verifyHealthUnitLongitude(double longitude) {
    if(longitude <= HU_MAX_LONGITUDE && longitude >= -HU_MAX_LONGITUDE){
      return true;
    }
    return false;
  }
}