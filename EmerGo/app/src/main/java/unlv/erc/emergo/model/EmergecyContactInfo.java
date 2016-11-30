package unlv.erc.emergo.model;

public interface EmergecyContactInfo {

  int NAME_MAXIMUM_SIZE = 100;

  boolean verifyEmergencyContactName(String name);

}
