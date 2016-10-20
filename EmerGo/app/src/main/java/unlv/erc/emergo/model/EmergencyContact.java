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

  public EmergencyContact() { 

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

  public EmergencyContact(String nameContact, String phone) {
    
    assert nameContact != null : "nameContact can't be null";
    assert phone != null : "phone can't be null";

    if (nameContact != null && phone != null) {

      setnameContact(nameContact);
      setPhone(phone);
    } else {

      // Nothing to do
    }
  } 

  /**
   * Get the nameContact of this emergency contact instance.
   * @return nameContact;
   *
   */

  public String getnameContact() { 

    return nameContact;
  } 

  /**
   * Set the received nameContact as nameContact of this emergency contact instance.
   * @param nameContact String nameContact
   *
   */

  public void setnameContact(String nameContact) { 

    assert nameContact != null : "nameContact can't be null";

    if (nameContact != null) {

      this.nameContact = nameContact;
    } else {

      // Nothing to do
    }
  } 

  /**
   * Get the phone of this emergency contact instance.
   * @return phone;
   *
   */

  public String getPhone() { 

    return phone;
  } 

  /**
   * Set the received phone as phone of this emergency contact instance.
   * @param phone String phone
   *
   */

  public void setPhone(String phone) { 

    assert phone != null : "phone can't be null";

    if (phone != null) {

      this.phone = phone;
    } else {

      // Nothing to do
    }
  } 
} 