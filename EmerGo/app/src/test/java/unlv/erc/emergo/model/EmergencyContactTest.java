package unlv.erc.emergo.model;

import junit.framework.TestCase;

public class EmergencyContactTest extends TestCase {

  private EmergencyContact emergencyContact;

  private final int MINIMUM_LENGTH_NAME = 3;
  private final int MAXIMUM_LENGHT_NAME = 42;
  private final int MAXIMUM_LENGHT_PHONE = 11;

  public void testSetNameEmergencyContact() {

    emergencyContact = new EmergencyContact();
    emergencyContact.setnameContact("Joaquina Josefina");
    assertEquals("Joaquina Josefina", emergencyContact.getnameContact());

    try {
      emergencyContact.finalize();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

  public void testGetNameEmergencyContact() {

    emergencyContact = new EmergencyContact();
    String name = "Pedro Henrique";
    emergencyContact.setnameContact("Pedro Henrique");
    assertEquals(name, emergencyContact.getnameContact());

    try {
      emergencyContact.finalize();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

  public void testSetNameEmergencyContactEmpty() {

    emergencyContact = new EmergencyContact();
    emergencyContact.setnameContact("");
    assertEquals("", emergencyContact.getnameContact());

    try {
      emergencyContact.finalize();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

    public void testSetNameEmergencyContactLowerThree() {

      emergencyContact = new EmergencyContact();
      emergencyContact.setnameContact("Ana");
      boolean result = true;

      if(emergencyContact.getnameContact().trim().length()<MINIMUM_LENGTH_NAME) {

        assertFalse(result);
      } else {

        assertTrue(result);
      }

      try {
        emergencyContact.finalize();
      } catch (Throwable throwable) {
        throwable.printStackTrace();
      }
  }

  public void testSetNameMaximumSize() {

    emergencyContact = new EmergencyContact();
    emergencyContact.setnameContact("Maraia Maraisa Mendonça");
    boolean result = true;

    if(emergencyContact.getnameContact().trim().length()>MAXIMUM_LENGHT_NAME) {

      assertFalse(result);
    } else {

      assertTrue(result);
    }

    try {
      emergencyContact.finalize();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

  public void testSetPhoneEmergencyContact() {

    emergencyContact = new EmergencyContact();
    emergencyContact.setPhone("84009765");
    assertEquals("84009765", emergencyContact.getPhone());

    try {
      emergencyContact.finalize();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

  public void testGetPhoneEmergencyContact() {

    emergencyContact = new EmergencyContact();
    String phone = "61985980034";
    emergencyContact.setPhone("61985980034");
    assertEquals(phone, emergencyContact.getPhone());

    try {
      emergencyContact.finalize();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

  public void testSetPhoneEmergencyContactEmpty() {

    emergencyContact = new EmergencyContact();
    emergencyContact.setPhone("");
    assertEquals("", emergencyContact.getPhone());

    try {
      emergencyContact.finalize();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

  public void testSetPhoneMaximumSize() {

    emergencyContact = new EmergencyContact();
    emergencyContact.setPhone("61985993870");
    boolean result = true;

    if(emergencyContact.getPhone().trim().length()>MAXIMUM_LENGHT_PHONE) {

      result = false;
      assertTrue(result);
    } else {

      assertTrue(result);
    }

    try {
      emergencyContact.finalize();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

  public void testGetNameAndPhoneEmergencyContact() {

    emergencyContact = new EmergencyContact( "Justin Timberlake", "61985980034");
    assertEquals("Justin Timberlake", emergencyContact.getnameContact());
    assertEquals("61985980034", emergencyContact.getPhone());

    try {
      emergencyContact.finalize();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

  public void testSetNameAndPhoneEmergencyContact() {

    emergencyContact = new EmergencyContact("","");
    emergencyContact.setnameContact("Joana");
    emergencyContact.setPhone("6185993870");
    assertEquals("Joana", emergencyContact.getnameContact());
    assertEquals("6185993870", emergencyContact.getPhone());

    try {
      emergencyContact.finalize();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

  public void testSetJustNameEmergencyContact() {

    emergencyContact = new EmergencyContact("","");
    emergencyContact.setnameContact("Mariana");
    emergencyContact.setPhone("");
    assertEquals("Mariana", emergencyContact.getnameContact());
    assertEquals("", emergencyContact.getPhone());

    try {
      emergencyContact.finalize();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

  public void testGetJustNameEmergencyContact(){

    emergencyContact = new EmergencyContact( "Justin Timberlake", "");
    assertEquals("Justin Timberlake", emergencyContact.getnameContact());
    assertEquals("", emergencyContact.getPhone());

    try {
      emergencyContact.finalize();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

  public void testSetNameEmergencyContactLessThan42() {

    emergencyContact = new EmergencyContact("","");
    emergencyContact.setnameContact("Mariana Andrade Queiroz");
    emergencyContact.setPhone("");
    boolean result = true;

      if(emergencyContact.getnameContact().trim().length()>MAXIMUM_LENGHT_NAME) {

        result = false;
        assertTrue(result);
      } else {

        assertTrue(result);
      }

    try {
      emergencyContact.finalize();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

  public void testSetNameEmergencyContactMoreThan3() {

    emergencyContact = new EmergencyContact("","");
    emergencyContact.setnameContact("Ana");
    emergencyContact.setPhone("");
    boolean result = true;

    if(emergencyContact.getnameContact().trim().length()<MINIMUM_LENGTH_NAME) {

      result = false;
      assertTrue(result);
    } else {

      assertTrue(result);
    }

    try {
      emergencyContact.finalize();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }
}
