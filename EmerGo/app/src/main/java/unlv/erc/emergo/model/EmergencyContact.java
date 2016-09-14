/**
 * Class name: EmergencyContact.java
 *
 * Purpouse: represent the data from an emergency contact signed in the Emergo.
 */

package unlv.erc.emergo.model;

public class EmergencyContact {

  private String name;
  private String phone;


  /**
   * Empty constructor.
   */
  public EmergencyContact() {

  }


  /**
   * Emergency contact constructor.
   * @param name
   * @param phone
   */
  public EmergencyContact(String name, String phone) {
    setName(name);
    setPhone(phone);
  }

  /**
   * Get the name of this emergency contact instance.
   *
   * @return name;
   */
  public String getName() {

    return name;
  }

  /**
   * Set the received name as name of this emergency contact instance.
   *
   * @param name
   */
  public void setName(String name) {

    this.name = name;
  }

  /**
   * Get the phone of this emergency contact instance.
   *
   * @return phone;
   */
  public String getPhone() {

    return phone;
  }

   /**
    * Set the received phone as phone of this emergency contact instance.
    *
    * @param phone
    */
  public void setPhone(String phone) {

    this.phone = phone;
  }
}