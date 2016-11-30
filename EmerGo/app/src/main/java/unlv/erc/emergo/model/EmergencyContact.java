/**
 * Class nameContact: EmergencyContact.java
 *
 * Purpouse: represent the data from an emergency contact signed in the Emergo.
 */

package unlv.erc.emergo.model;

public class EmergencyContact implements EmergecyContactInfo{

  /**
   * Empty constructor.
   *
   */

  EmergencyContact() {

    // Nothing to do
  } 

  // nameContact contact
  private String nameContact = "";
  // Phone contact
  private String phone = "";

  /**
   * Emergency contact constructor.
   * @param nameContact nameContact of user
   * @param phone phone of user
   *
   */

  protected EmergencyContact(String nameContact, String phone) {
    
    assert nameContact != null : "nameContact can't be null";
    assert phone != null : "phone can't be null";

    setnameContact(nameContact);
    setPhone(phone);
  }

  /**
   * Get the nameContact of this emergency contact instance.
   * @return nameContact;
   *
   */

  protected String getnameContact() {

    return nameContact;
  } 

  /**
   * Set the received nameContact as nameContact of this emergency contact instance.
   * @param nameContact String nameContact
   *
   */

  protected void setnameContact(String nameContact) {

    assert nameContact != null : "nameContact can't be null";

    try {

      if (verifyEmergencyContactName(nameContact)) {

        this.nameContact = nameContact;
      }
    } catch (NullPointerException nullPointException) {

      nullPointException.printStackTrace();
    }


  }

  /**
   * Get the phone of this emergency contact instance.
   * @return phone;
   *
   */

  protected String getPhone() {

    return phone;
  } 

  /**
   * Set the received phone as phone of this emergency contact instance.
   * @param phone String phone
   *
   */

  protected void setPhone(String phone) {

    assert phone != null : "phone can't be null";

    this.phone = phone;
    }

  @Override
  public boolean verifyEmergencyContactName(String name) {

    return name.length() <= NAME_MAXIMUM_SIZE;
  }
}