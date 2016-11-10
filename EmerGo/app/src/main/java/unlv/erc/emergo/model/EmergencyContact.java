/**
 * Class nameContact: EmergencyContact.java
 *
 * Purpouse: represent the data from an emergency contact signed in the Emergo.
 */

package unlv.erc.emergo.model;

public class EmergencyContact {

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

  private EmergencyContact(String nameContact, String phone) {
    
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

  private String getnameContact() {

    return nameContact;
  } 

  /**
   * Set the received nameContact as nameContact of this emergency contact instance.
   * @param nameContact String nameContact
   *
   */

  private void setnameContact(String nameContact) {

    assert nameContact != null : "nameContact can't be null";

    this.nameContact = nameContact;
  }

  /**
   * Get the phone of this emergency contact instance.
   * @return phone;
   *
   */

  private String getPhone() {

    return phone;
  } 

  /**
   * Set the received phone as phone of this emergency contact instance.
   * @param phone String phone
   *
   */

  private void setPhone(String phone) {

    assert phone != null : "phone can't be null";

    this.phone = phone;
    }
} 