package unlv.erc.emergo.model;

interface EmergecyContactInfo {

  int NAME_MAXIMUM_SIZE = 100;
  int PHONE_MAXIMUM_SIZE = 11;

  boolean verifyEmergencyContactName(String name);
  boolean verifyEmergencyContactPhone(String phone);

}
