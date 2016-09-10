package unlv.erc.emergo.model;

import junit.framework.TestCase;

public class UserTest extends TestCase {

    private User user;

    private final int MINIMUM_LENGTH_NAME = 3;
    private final int MAXIMUM_LENGHT_NAME = 42;
    private final int MAXIMUM_TYPEBLOOD = 3;

    public void testGetName() {

        user = new User();
        String name = "Peter";
        user.setNameUser("Peter");
        assertEquals(name,user.getNameUser());
    }

    public void testSetName() {

        user = new User();
        user.setNameUser("Maria Joaquina");
        assertEquals("Maria Joaquina", user.getNameUser());
    }

    public void testSetNameEmpty() {

        user = new User();
        user.setNameUser("");
        assertEquals("", user.getNameUser());
    }

    public void testSetNameLowerThree() {

        user = new User();
        user.setNameUser("Ana");
        boolean result = true;

        if(user.getNameUser().trim().length()<MINIMUM_LENGTH_NAME) {

            assertFalse(result);
        } else {

            assertTrue(result);
        }
    }

    public void testSetNameMaximumSize() {

        user = new User();
        user.setNameUser("Pedro Alvares Cabral de Valentina");
        boolean result = true;

        if(user.getNameUser().trim().length()>MAXIMUM_LENGHT_NAME) {

            assertFalse(result);
        } else {

            assertTrue(result);
        }
    }

    // tests birthday

    public void testSetBirthday() {

        user = new User();
        user.setBirthdayUser("12/03/1990");
        assertEquals("12/03/1990", user.getBirthdayUser());
    }

    // tests for typeBlood

    public void testSetTypeBloodBiggerThree() {

        user = new User();
        user.setTypeBloodUser("AB-");
        boolean result = true;

        if(user.getTypeBloodUser().length()>MAXIMUM_TYPEBLOOD) {

            assertFalse(result);
        } else {

            assertTrue(result);
        }
    }

    public void testSetTypeBloodNull() {

        user = new User();
        user.setTypeBloodUser("AB+");
        boolean result = true;

        if(user.getTypeBloodUser()==null) {

            result = false;
            assertTrue(result);
        } else {

            assertTrue(result);
        }
    }

    public void testSetTypeBloodLowerOrEqualsTwo() {

        User user = new User();
        user.setTypeBloodUser("A-");
        boolean result = true;

        if(user.getTypeBloodUser().length()<=MAXIMUM_TYPEBLOOD-1) {

            assertTrue(result);
        } else {

            assertFalse(result);
        }
    }

    public void testGetInformationsAboutUser() {

        user = new User("Leoncio", "24/03/1994", "AB", "yes", "no", "no", "no",
                "o que se leva da vida é a vida que se leva",1);
        assertEquals("Leoncio", user.getNameUser());
        assertEquals("24/03/1994", user.getBirthdayUser());
        assertEquals("AB", user.getTypeBloodUser());
        assertEquals("yes", user.getCardiacUser());
        assertEquals("no", user.getDiabeticUser());
        assertEquals("no", user.getHypertensionUser());
        assertEquals("no", user.getSeropositiveUser());
        assertEquals("o que se leva da vida é a vida que se leva", user.getObservationsUser());
    }

    public void testSetInformationsAboutUser() {

        user = new User("", "", "", "", "", "", "",
                "",1);
        user.setNameUser("Gabriela");
        user.setBirthdayUser("01/01/1990");
        user.setTypeBloodUser("A+");
        user.setCardiacUser("no");
        user.setDiabeticUser("yes");
        user.setHypertensionUser("no");
        user.setSeropositiveUser("no");
        user.setObservationsUser("alergias a lactose e glutem");

        assertEquals("Gabriela", user.getNameUser());
        assertEquals("01/01/1990", user.getBirthdayUser());
        assertEquals("A+", user.getTypeBloodUser());
        assertEquals("no", user.getCardiacUser());
        assertEquals("yes", user.getDiabeticUser());
        assertEquals("no", user.getHypertensionUser());
        assertEquals("no", user.getSeropositiveUser());
        assertEquals("alergias a lactose e glutem", user.getObservationsUser());
    }
}