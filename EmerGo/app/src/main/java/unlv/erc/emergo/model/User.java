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
   * @param nameUser This is a name of user.
   * @param birthdayUser This is a birthday of user.
   * @param typeBloodUser This a type blood of user.
   * @param cardiacUser Describe is user has cardiac problem.
   * @param diabeticUser Describe is user has diabetic problem.
   * @param hypertensionUser Describe is user has hypertension.
   * @param seropositiveUser Describe is user has seropositive.
   * @param observationsUser Describe observations of user.
   * @param idUser This is a id of user.
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

  protected String getObservationsUser() {

    return observationsUser;
  }

  /**
   * Set the value of attribute observationUser.
   *
   * @param observationsUser
   *
   */

  protected void setObservationsUser(String observationsUser) {

    this.observationsUser = observationsUser;
  }

  /**
   * Get the value of attribute idUser.
   *
   * @return idUser:int
   *
   */

  protected Integer getIdUser() {

    return idUser;
  }

  /**
   * Set the value of attribute idUser.
   *
   * @param idUser
   *
   */

  protected void setIdUser(Integer idUser) {

    assert idUser < 0 : "idUser can not be < 0";

    this.idUser = idUser;
  }

  /**
   * Get the value of attribute nameUser.
   *
   * @return nameUser:String
   *
   */

  protected String getNameUser() {

    return nameUser;
  }

  /**
   * Set the value of attribute nameUser.
   *
   * @param nameUser
   *
   */

  protected void setNameUser(String nameUser) {

    this.nameUser = nameUser;
  }

  /**
   * Get the value of attribute birthdayUser.
   *
   * @return birthdayUser:String
   *
   */

  protected String getBirthdayUser() {

    return birthdayUser;
  }

  /**
   * Set the value of attribute birthdayUser.
   *
   * @param birthdayUser
   *
   */

  protected void setBirthdayUser(String birthdayUser) {

    this.birthdayUser = birthdayUser;
  }

  /**
   * Get the value of attribute typeBloodUser.
   *
   * @return typebloodUser:String
   *
   */

  protected String getTypeBloodUser() {

    return typeBloodUser;
  }

  /**
   * Set the value of attribute typeBloodUser.
   *
   * @param typeBloodUser
   *
   */

  protected void setTypeBloodUser(String typeBloodUser) {

    this.typeBloodUser = typeBloodUser;
  }

  /**
    * Get the value of attribute cardiacUser.
    *
    * @return cardiacUser:String
    *
   */

  protected String getCardiacUser() {

    return cardiacUser;
  }

  /**
    * Set the value of attribute cardiacUser.
    *
    * @param cardiacUser
    *
   */

  protected void setCardiacUser(String cardiacUser) {

    this.cardiacUser = cardiacUser;
  }

  /**
    * Get the value of attribute diabeticUser.
    *
    * @return diabeticUser:String
    *
   */

  protected String getDiabeticUser() {

    return diabeticUser;
  }

  /**
    * Set the value of attribute diabeticUser.
    *
    * @param diabeticUser
    *
   */

  protected void setDiabeticUser(String diabeticUser) {

    this.diabeticUser = diabeticUser;
  }

  /**
   * Get the value of attribute hypertensionUser.
   *
   * @return hypertensionUser:String
   *
   */

  protected String getHypertensionUser() {

    return hypertensionUser;
  }

  /**
   * Set the value of attribute hypertensionUser.
   *
   * @param hypertensionUser
   *
   */

  protected void setHypertensionUser(String hypertensionUser) {

    this.hypertensionUser = hypertensionUser;
  }

  /**
   * Get the value of attribute seropositiveUser.
   *
   * @return seropositiveUser:String
   *
   */

  protected String getSeropositiveUser() {

    return seropositiveUser;
  }

  /**
   * Set the value of attribute seropositiveUser.
   *
   * @param seropositiveUser
   *
   */

  protected void setSeropositiveUser(String seropositiveUser) {

    this.seropositiveUser = seropositiveUser;
  }
}
