/**
 * Class name: EmergencyContact.java
 *
 * Purpouse: represent the data from an emergency contact signed in the Emergo.
 */

package unlv.erc.emergo.model;

public class EmergencyContact { // Begin of EmergencyContact class

  // Name contact
  private String name = "";
  // Phone contact
  private String phone = "";

  /**
   * Empty constructor.
   *
   */

  public EmergencyContact() { // Begin of EmergencyContact

    // Nothing to do
  } // End of EmergencyContact


  /**
   * Emergency contact constructor.
   * @param name name of user
   * @param phone phone of user
   *
   */

  public EmergencyContact(String name, String phone) { // Begin of EmergencyContact
    setName(name);
    setPhone(phone);
  } // End of EmergencyContact

  /**
   * Get the name of this emergency contact instance.
   * @return name;
   *
   */

  public String getName() { // Begin of getName

    return name;
  } // End of getName

  /**
   * Set the received name as name of this emergency contact instance.
   * @param name String name
   *
   */

  public void setName(String name) { // Begin of setName

    this.name = name;
  } // Begin of setName

  /**
   * Get the phone of this emergency contact instance.
   * @return phone;
   *
   */

  public String getPhone() { // Begin of getPhone

    return phone;
  } // End of getPhone

  /**
   * Set the received phone as phone of this emergency contact instance.
   * @param phone String phone
   *
   */

  public void setPhone(String phone) { // Begin of setPhone

    this.phone = phone;
  } // End of setPhone
}