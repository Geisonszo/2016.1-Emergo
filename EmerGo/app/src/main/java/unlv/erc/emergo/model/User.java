/*****************************
 * Class name: User (.java)
 *
 * Purpose: Represent the data from a user's medical records.
 ****************************/

package unlv.erc.emergo.model;

public class User {

    private String nameUser = "";
    private String birthdayUser = "";
    private String typeBloodUser = "";
    private String cardiacUser = "";
    private String diabeticUser = "";
    private String hypertensionUser = "";
    private String seropositiveUser = "";
    private String observationsUser = "";
    private Integer idUser = 0;

    /**
     * Empty Constructor.
     */

    public User() {

    }

    /**
     * Constructor of user.
     *
     * @param nameUser
     * @param birthdayUser
     * @param typeBloodUser
     * @param cardiacUser
     * @param diabeticUser
     * @param hypertensionUser
     * @param seropositiveUser
     * @param observationsUser
     * @param idUser
     */

    public User(String nameUser, String birthdayUser, String typeBloodUser, String cardiacUser,
                String diabeticUser, String hypertensionUser, String seropositiveUser,
                String observationsUser, Integer idUser) {
        setNameUser(nameUser);
        setBirthdayUser(birthdayUser);
        setTypeBloodUser(typeBloodUser);
        setCardiacUser(cardiacUser);
        setDiabeticUser(diabeticUser);
        setHypertensionUser(hypertensionUser);
        setSeropositiveUser(seropositiveUser);
        setObservationsUser(observationsUser);
        setIdUser(idUser);
    }

    /**
     * Get the value of attribute observationsUser.
     *
     * @return observationsUser:String
     *
     */

    public String getObservationsUser() {
        return observationsUser;
    }

    /**
     * Set the value of attribute observationUser.
     *
     * @param observationsUser
     *
     */

    public void setObservationsUser(String observationsUser) {
        this.observationsUser = observationsUser;
    }

    /**
     * Get the value of attribute idUser.
     *
     * @return idUser:int
     *
     */

    public Integer getIdUser() {
        return idUser;
    }

    /**
     * Set the value of attribute idUser.
     *
     * @param idUser
     *
     */

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    /**
     * Get the value of attribute nameUser.
     *
     * @return nameUser:String
     *
     */

    public String getNameUser() {
        return nameUser;
    }

    /**
     * Set the value of attribute nameUser.
     *
     * @param nameUser
     *
     */

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    /**
     * Get the value of attribute birthdayUser.
     *
     * @return birthdayUser:String
     *
     */

    public String getBirthdayUser() {
        return birthdayUser;
    }

    /**
     * Set the value of attribute birthdayUser.
     *
     * @param birthdayUser
     *
     */

    public void setBirthdayUser(String birthdayUser) {
        this.birthdayUser = birthdayUser;
    }

    /**
     * Get the value of attribute typeBloodUser.
     *
     * @return typebloodUser:String
     *
     */

    public String getTypeBloodUser() {
        return typeBloodUser;
    }

    /**
     * Set the value of attribute typeBloodUser.
     *
     * @param typeBloodUser
     *
     */

    public void setTypeBloodUser(String typeBloodUser) {
        this.typeBloodUser = typeBloodUser;
    }

    /**
     * Get the value of attribute cardiacUser.
     *
     * @return cardiacUser:String
     *
     */

    public String getCardiacUser() {
        return cardiacUser;
    }

    /**
     * Set the value of attribute cardiacUser.
     *
     * @param cardiacUser
     *
     */

    public void setCardiacUser(String cardiacUser) {
        this.cardiacUser = cardiacUser;
    }

    /**
     * Get the value of attribute diabeticUser.
     *
     * @return diabeticUser:String
     *
     */

    public String getDiabeticUser() {
        return diabeticUser;
    }

    /**
     * Set the value of attribute diabeticUser.
     *
     * @param diabeticUser
     *
     */

    public void setDiabeticUser(String diabeticUser) {
        this.diabeticUser = diabeticUser;
    }

    /**
     * Get the value of attribute hypertensionUser.
     *
     * @return hypertensionUser:String
     *
     */

    public String getHypertensionUser() {
        return hypertensionUser;
    }

    /**
     * Set the value of attribute hypertensionUser.
     *
     * @param hypertensionUser
     *
     */

    public void setHypertensionUser(String hypertensionUser) {
        this.hypertensionUser = hypertensionUser;
    }

    /**
     * Get the value of attribute seropositiveUser.
     *
     * @return seropositiveUser:String
     *
     */

    public String getSeropositiveUser() {
        return seropositiveUser;
    }

    /**
     * Set the value of attribute seropositiveUser.
     *
     * @param seropositiveUser
     *
     */

    public void setSeropositiveUser(String seropositiveUser) {
        this.seropositiveUser = seropositiveUser;
    }
}
