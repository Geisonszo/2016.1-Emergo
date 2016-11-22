package unlv.erc.emergo.model;

public interface HealthUnitInfo {

    int HU_NAME_MAX_SIZE = 120; //Maximum size an Health Unit name can have.
    int HU_TYPE_MAX_SIZE = 50;  //Maximum size an Health Unit type can have.
    int HU_ADRESS_MAX_SIZE = 120; //Maximum size an Health Unit adress may have.
    int HU_DISTRICT_MAX_SIZE = 70; //Maximum size an Health Unit adress may have.
    int HU_STATE_MAX_SIZE = 40; //Maximum size an Health Unit state may have.
    int HU_CITY_MAX_SIZE = 50; //Maximum size an Health Unit city may have.
    double HU_MAX_LONGITUDE = 180; //Max longitude an Health unity may be located.
    double HU_MAX_LATITUDE = 90; // Max longitude an Health unity may be located;

    boolean verifyHealthUnitName(String name);
    boolean verifyHealthUnitType(String type);
    boolean verifyHealthUnitAdress(String adress);
    boolean verifyHealthUnitDistrict(String district);
    boolean verifyHealthUnitState(String state);
    boolean verifyHealthUnitCity(String city);
    boolean verifyHealthUnitLatitude(double latitude);
    boolean verifyHealthUnitLongitude(double longitude);
}
