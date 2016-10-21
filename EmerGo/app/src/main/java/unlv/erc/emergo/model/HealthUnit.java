/*****************************
 * Class name: HealthUnit (.java)
 *
 * Purpose: Represent the data from a health units's.
 ****************************/

package unlv.erc.emergo.model;

import com.orm.SugarRecord;

public class HealthUnit extends SugarRecord {

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
      exception.printStackTrace();
    }
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    try {
      this.latitude = latitude;
    }catch (NullPointerException exception){
      exception.printStackTrace();
    }
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    try{
      this.longitude = longitude;
    }catch (NullPointerException exception){
      exception.printStackTrace();
    }
  }

  public String getNameHospital() {
    return nameHospital;
  }

  public void setNameHospital(String nameHospital) {
    try{
      this.nameHospital = nameHospital;
    }catch (NullPointerException exception){
        exception.printStackTrace();
    }
  }

  public String getUnitType() {
    return healthUnitType;
  }

  public void setUnitType(String healthUnitType) {
    try{
      this.healthUnitType = healthUnitType;
    }catch (NullPointerException exception){
        exception.printStackTrace();
    }
  }

  public String getAddressNumber() {
    return addressNumber;
  }

  public void setAddressNumber(String addressNumber) {
    try{
      this.addressNumber = addressNumber;
    }catch (NullPointerException exception){
      exception.printStackTrace();
    }
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    try{
      this.district = district;
    }catch (NullPointerException exception){
        exception.printStackTrace();
    }
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    try{
      this.state = state;
    }catch (NullPointerException exception){
        exception.printStackTrace();
    }
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    try{
      this.city = city;
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
    }catch (NullPointerException exception){
        exception.printStackTrace();
    }
  }
}