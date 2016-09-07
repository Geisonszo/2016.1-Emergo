
package unlv.erc.emergo.model;

public class User{

    private String nameUser = "";
    private String birthdayUser = "";
    private String typeBloodUser = "";
    private String cardiacUser = "";
    private String diabeticUser = "";
    private String hypertensionUser = "";
    private String seropositiveUser = "";
    private String observationsUser = "";
    private Integer idUser = 0;

    public User() {

    }

    public User(String nameUser,String birthdayUser,String typeBloodUser,String cardiacUser,
                String diabeticUser,String hypertensionUser,String seropositiveUser,
                String observationsUser,Integer idUser) {
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

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getBirthdayUser() {
        return birthdayUser;
    }

    public void setBirthdayUser(String birthdayUser) {
        this.birthdayUser = birthdayUser;
    }

    public String getTypeBloodUser() {
        return typeBloodUser;
    }

    public void setTypeBloodUser(String typeBloodUser) {
        this.typeBloodUser = typeBloodUser;
    }

    public String getCardiacUser() {
        return cardiacUser;
    }

    public void setCardiacUser(String cardiacUser) {
        this.cardiacUser = cardiacUser;
    }

    public String getDiabeticUser() {
        return diabeticUser;
    }

    public void setDiabeticUser(String diabeticUser) {
        this.diabeticUser = diabeticUser;
    }

    public String getHypertensionUser() {
        return hypertensionUser;
    }

    public void setHypertensionUser(String hypertensionUser) {
        this.hypertensionUser = hypertensionUser;
    }

    public String getSeropositiveUser() {
        return seropositiveUser;
    }

    public void setSeropositiveUser(String seropositiveUser) {
        this.seropositiveUser = seropositiveUser;
    }

    public String getObservationsUser() {
        return observationsUser;
    }

    public void setObservationsUser(String observationsUser) {
        this.observationsUser = observationsUser;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
