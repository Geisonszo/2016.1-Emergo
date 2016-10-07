/*
 * Class: EmergencyContactController (.java)
 *
 * Porpouse: This class contais the objects and methods related to the user's emergency contacts.
 */


package unlv.erc.emergo.controller;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import dao.EmergencyContactDao;
import helper.MaskHelper;
import unlv.erc.emergo.R;

public class EmergencyContactController extends Activity { // Begin of EmergencyController class

  // Button for saveFirstContact
  private Button saveFirstContact;
  // Button for saveFirstContact
  private Button saveSecondContact;
  // Button for saveThirdContact
  private Button saveThirdContact;
  // Button for deleteFirstContact
  private Button deleteFirstContact;
  // Button for deleteSecondContact
  private Button deleteSecondContact;
  // Button for deleteThirdContact
  private Button deleteThirdContact;
  // Button for updateFirstContact
  private Button updateFirstContact;
  // Button for updateSecondContact
  private Button updateSecondContact;
  // Button for updateThirdContact
  private Button updateThirdContact;
  // Field text for nameFirstContact
  private EditText nameFirstContact;
  // Field text for nameSecondContact
  private EditText nameSecondContact;
  // Field text for nameThirdContact
  private EditText nameThirdContact;
  // Field text for phoneFirstContact
  private EditText phoneFirstContact;
  // Field text for phoneSecondContact
  private EditText phoneSecondContact;
  // Field text for phoneThirdContact
  private EditText phoneThirdContact;
  // String of phoneContact
  private String phoneContact = " ";
  // String of nameContact
  private String nameContact = "";
  // Id constant of firstContact
  private final int idFirstContact = 1;
  // Id constant of secondContact
  private final int idSecondContact = 2;
  // Id constant of thirdContact
  private final int idThirdContact = 3;
  // Constant of minimum length name
  private final int minimum = 3;
  // Constant string about route
  private final String routeTraced = "Rota mais próxima traçada";
  // Database is empty
  private final int databaseEmpty = 0;
  // The second position on database. In second position on database is refer to name of contact
  private final int nameOnDatabase = 1;
  // The third position on database. In third position on database is refer to phone of contact
  private final int phoneOnDatabase = 2;
  // Instantiation of class EmergencyContactDao
  private EmergencyContactDao emergencyContactDao;
  // Instantiation of class Cursor
  private Cursor result;

  public EmergencyContactController() {
      //Empty Constructor.
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // Begin of onCreate

    super.onCreate(savedInstanceState);
    setContentView(R.layout.emergency_contact);

    emergencyContactDao = new EmergencyContactDao(this);
    result = emergencyContactDao.getEmergencyContact();

    setBTFirstContact();
    logicFirstContact();
    setBTSecondContact();
    logicSecondContact();
    setBTThirdContact();
    logicThirdContact();
  } // End of onCreate

  /*
   * Set informations of bottons and edittext about first emergency contact.
   *
   */

  private void setBTFirstContact() { // Begin of setBTFirstContact

    Log.d("Begin of Method: ","setBTFirstContact");
    saveFirstContact = (Button) findViewById(R.id.saveButtonFirstContact);
    updateFirstContact = (Button) findViewById(R.id.updateButtonFirstContact);
    deleteFirstContact = (Button) findViewById(R.id.deleteFirstContactButton);

    nameFirstContact = (EditText) findViewById(R.id.nameFirstContactEditText);
    phoneFirstContact = (EditText) findViewById(R.id.phoneEditText);
    phoneFirstContact.addTextChangedListener(MaskHelper.insert("(###)#####-####",
            phoneFirstContact));
    Log.d("End of Method: ","setBTFirstContact");
  } // End of setBTFirstContact

  /*
   * Organize the logic of save,update and delete of first contact.
   *
   */

  private void logicFirstContact() { // Begin of logicFirstContact

    Log.d("Begin of Method: ","logicFirstContact");
    // Verifies that has something registered in the database on the emergencyContact.
    if (result.getCount() == databaseEmpty) { // Begin of if

      disableOptionsButSave(saveFirstContact,updateFirstContact,deleteFirstContact);
      Log.i("Database is Empty.","");
      // End of if
    } else { //Begin of else

      // If you have something in the emergencyContact database will be shown in the field name,
      // phone to first contact.
      if (result.moveToFirst()) { // Begin of if

        nameFirstContact.setText(result.getString(nameOnDatabase));
        phoneFirstContact.setText(result.getString(phoneOnDatabase));
        disableField(saveFirstContact,nameFirstContact,phoneFirstContact);
        Log.i("Database not is empty","In database have at least an emergency contact");
      } // End of if
    } // End of else

    saveFirstContact.setOnClickListener(new View.OnClickListener() {
      // Begin of saveFirstContact.setOnClickListener

      // When the button saveFirstContact is clicked will be the method signInFirstContact
      public void onClick(View emergencyContactView) { // Begin of onClick

        // Register the first emergency contact
        if (!signInFirstContact()) { // Begin of if

          saveFirstContact.setOnClickListener(new View.OnClickListener() {
            // Begin of saveFirstContact.setOnClickListener

            // For register the first emergency contact will done some validations and if on
            // validation not passed, will be given a chance to register the emergency contact
            // again
            public void onClick(View emergencyContactView) { //Begin of onClick

              signInFirstContact();
              disableField(nameFirstContact,phoneFirstContact);
            } // End of onClick
          }); // End of saveFirstContact.setOnClickListener and End of else
          // If all validation passed will disable some buttons
          Log.d("Emergency Contact save!"," Not have problem for save emergency contact");
        } else { // Begin of else

          disableSaveButton(saveFirstContact,updateFirstContact,deleteFirstContact);
          Log.d("Nothing to do"," Already had one emergency contact in that first position");
        } // End of else
      } // End of onClick
    }); // End of saveFirstContact.setOnClickListener

    updateFirstContact.setOnClickListener(new View.OnClickListener() {
      // Begin of updateFirstContact.setOnClickListener

      // Disable some buttons and the "alterar" pass to "salvar"
      public void onClick(View emergencyContactView) { // Begin of onClick

        disableUpdateButton(nameFirstContact, phoneFirstContact, updateFirstContact,
                saveFirstContact);

        saveFirstContact.setOnClickListener(new View.OnClickListener() {
          // Begin of saveFirstContact.setOnClickListener

          // Update the first emergency contact
          public void onClick(View emergencyContactView) { // Begin of onClick

            updateContact(idFirstContact,nameFirstContact,phoneFirstContact,
                    saveFirstContact,updateFirstContact,deleteFirstContact);
            setOptionsVisible(saveFirstContact,updateFirstContact);
            Log.d("Update contact"," Was update at informations about first emergency contact");
          } // End of onClick
        }); // End of saveFirstContact.setOnClickListener
      } // End of onClick
    }); // End of updateFirstContact.setOnClickListener

    deleteFirstContact.setOnClickListener(new View.OnClickListener() {
      // Begin of deleteFirstContact.setOnClickListener

      // Delete first emergency contact
      public void onClick(View emergencyContactView) { // Begin of onClick

        deleteContact(nameFirstContact,phoneFirstContact,saveFirstContact,
                idFirstContact,updateFirstContact,deleteFirstContact);
        Log.d("Delete contact"," Was delete at informations about first emergency contact");

        saveFirstContact.setOnClickListener(new View.OnClickListener() {
          // Begin of saveFirstContact.setOnClickListener

          public void onClick(View emergencyContactView) { // Begin of onClick

            // For register the first emergency contact will done some validations and if on
            // validation not passed, will be given a chance to register the emergency contact
            // again
            if (!signInFirstContact()) { //Begin of if

              saveFirstContact.setOnClickListener(new View.OnClickListener() {
                // Begin of saveFirstContact.setOnClickListener

                public void onClick(View emergencyContactView) { // Begin of onClick

                  signInFirstContact();
                  disableField(nameFirstContact,phoneFirstContact);
                } // End of onClick
              }); // End of saveFirstContact.setOnClickListener and End of if
              // If all validation passed will disable some buttons
              Log.d("Emergency Contact save!"," Not have problem for save emergency contact");
            } else { // Begin of else

              disableSaveButton(saveFirstContact,updateFirstContact,deleteFirstContact);
            } // End of else
          } // End of onClick
        }); // End of saveFirstContact.setOnClickListener
      } // End of onClick
    }); // End of deleteFirstContact.setOnClickListener
    Log.d("End of Method: ","logicFirstContact");
  } // End of logicFirstContact

  /*
   * Set informations of bottons and edittext about second emergency contact.
   *
   */

  private void setBTSecondContact() { // Begin of setBTSecondContact

    Log.d("Begin of Method: ","setBTSecondContact");
    saveSecondContact = (Button) findViewById(R.id.saveSecondContactButton);
    updateSecondContact = (Button) findViewById(R.id.updateSecondContactButton);
    deleteSecondContact = (Button) findViewById(R.id.deleteSecondContactButton);

    nameSecondContact = (EditText) findViewById(R.id.nameSecondContactEditText);
    phoneSecondContact = (EditText) findViewById(R.id.phoneSecondContactEditText);
    phoneSecondContact.addTextChangedListener(MaskHelper.insert("(###)#####-####",
            phoneSecondContact));
    Log.d("End of Method: ","setBTSecondContact");
  } // End of setBTSecondContact

  /*
   * Organize the logic of save,update and delete of second contact.
   *
   */

  private void logicSecondContact() { // Begin of logicSecondContact

    Log.d("Begin of Method: ","logicSecondContact");
    // Verifies that has something registered in the database on the emergencyContact.
    if (result.getCount() == databaseEmpty) { // Begin of if

      disableOptionsButSave(saveSecondContact, updateSecondContact,deleteSecondContact);
      Log.i("Database is Empty.","");
      // End of if
      // If you have something in the emergencyContact database will be shown in the field name,
      // phone to second contact.
    } else { // Begin of else

      // Move the "cursor" to next position. If has something on first position of table
      // emergencyContact, the "cursor" will moved to next position
      if (result.moveToNext()) { // Begin of if

        nameSecondContact.setText(result.getString(nameOnDatabase));
        phoneSecondContact.setText(result.getString(phoneOnDatabase));
        disableField(saveSecondContact,nameSecondContact,phoneSecondContact);
        Log.i("Database not is empty","In database have at least an emergency contact");
      } // End of if
    } // End of else

    saveSecondContact.setOnClickListener(new View.OnClickListener() {
      // Begin of saveSecondContact.setOnCLickListener

      // When the button saveFirstContact is clicked will be the method signInSecondContact
      public void onClick(View emergencyContactView) { // Begin of onClick

        // Register the second emergency contact
        if (!signInSecondContact()) { // Begin of if

          saveSecondContact.setOnClickListener(new View.OnClickListener() {
            // Begin of saveSecondContact.setOnClickListener

            // For register the second emergency contact will done some validations and if on
            // validation not passed, will be given a chance to register the emergency contact
            // again
            public void onClick(View emergencyContactView) { // Begin of onClick

              signInSecondContact();
              disableField(nameSecondContact,phoneSecondContact);
              Log.d("Emergency Contact save!"," Not have problem for save emergency contact");
            } // End of onClick
          }); // End of saveSecondContact.setOnClickListener and End of if
        // If all validation passed will disable some buttons
        } else { // Begin of else

          disableSaveButton(saveSecondContact,updateSecondContact,deleteSecondContact);
          Log.d("Nothing to do"," Already had one emergency contact in that second position");
        } // End of else
      } // End of onClick
    }); // End of saveSecondContact.setOnClickListener

    updateSecondContact.setOnClickListener(new View.OnClickListener() {
      // Begin of updateSecondContact.setOnClickListener

      public void onClick(View emergencyContactView) { // Begin of onClick

        // Disable some buttons and the "alterar" pass to "salvar"
        disableUpdateButton(nameSecondContact, phoneSecondContact, updateSecondContact,
                saveSecondContact);

        saveSecondContact.setOnClickListener(new View.OnClickListener() {
          // Begin of saveSecondContact.setOnClickListener

          // Update the second emergency contact
          public void onClick(View emergencyContactView) { // Begin of onClick

            updateContact(idSecondContact,nameSecondContact,phoneSecondContact,
                    saveSecondContact,updateSecondContact,deleteSecondContact);
            setOptionsVisible(saveSecondContact,updateSecondContact);
            Log.d("Update contact"," Was update at informations about second emergency contact");
          } // End of onClick
        }); // End of saveSecondContact.setOnClickListener
      } // End of onClick
    }); // End of updateSecondContact.setOnClickListener

    deleteSecondContact.setOnClickListener(new View.OnClickListener() {
      // Begin of deleteSecondContact.setOnClickListener

      // Delete second emergency contact
      public void onClick(View emergencyContactView) { //Begin of onClick

        deleteContact(nameSecondContact,phoneSecondContact,saveSecondContact,
                idSecondContact,updateSecondContact,deleteSecondContact);
        Log.d("Delete contact"," Was delete at informations about third emergency contact");

        saveSecondContact.setOnClickListener(new View.OnClickListener() {
          // Begin of saveSecondContact.setOnClickListener

          public void onClick(View emergencyContactView) { // Begin of onClick

            // For register the third emergency contact will done some validations and if on
            // validation not passed, will be given a chance to register the emergency contact
            // again
            if (!signInSecondContact()) { // Begin of if

              saveSecondContact.setOnClickListener(new View.OnClickListener() {
                // Begin of saveSecondContact.setOnClickListener

                public void onClick(View emergencyContactView) { // Begin of onClick

                  signInSecondContact();
                  disableField(nameSecondContact,phoneSecondContact);
                  Log.d("Emergency Contact save!"," Not have problem for save emergency contact");
                } // End of onClick
              }); // End of saveSecondContact.setOnClickListener and End of if
            // If all validation passed will disable some buttons
            } else { // Begin of if

              disableSaveButton(saveSecondContact,updateSecondContact,
                      deleteSecondContact);
            } // End of else
          } // End of onClick
        }); // End of saveSecondContact.setOnClickListener
      } // End of onClick
    }); // End of deleteSecondContact.setOnClickListener
    Log.d("End of Method: ","logicSecondContact");
  } // End of logicSecondContact

  /*
   * Set informations of bottons and edittext about third emergency contact.
   *
   */

  private void setBTThirdContact() { // Begin of setBTThirdContact

    Log.d("Begin of Method: ","setBTThirdContact");

    saveThirdContact = (Button) findViewById(R.id.saveThirdContactButton);
    updateThirdContact = (Button) findViewById(R.id.updateThirdContactButton);
    deleteThirdContact = (Button) findViewById(R.id.deleteThirdContactButton);

    nameThirdContact = (EditText) findViewById(R.id.nameThirdContactEditText);
    phoneThirdContact = (EditText) findViewById(R.id.phoneThirdContactEditText);
    phoneThirdContact.addTextChangedListener(MaskHelper.insert("(###)#####-####",
            phoneThirdContact));
    Log.d("End of Method: ","setBTThirdContact");
  } // End of setBTTThirdContact

  /*
   * Organize the logic of save,update and delete of third contact.
   *
   */

  private void logicThirdContact() { // Begin of logicThirdContact

    Log.d("Begin of Method: ","logicThirdContact");
    // Verifies that has something registered in the database on the emergencyContact.
    if (result.getCount() == databaseEmpty) { // Begin of if

      disableOptionsButSave(saveThirdContact, updateThirdContact,deleteThirdContact);
      Log.i("Database is Empty.","");
      // End of if
      // If you have something in the emergencyContact database will be shown in the field name,
      // phone to third contact.
    } else { // Begin of else

      // Move the "cursor" to next position. If has something on second position of table
      // emergencyContact, the "cursor" will moved to next position
      if (result.moveToNext()) { // Begin of if

        nameThirdContact.setText(result.getString(nameOnDatabase));
        phoneThirdContact.setText(result.getString(phoneOnDatabase));
        disableField(saveThirdContact,nameThirdContact,phoneThirdContact);
        Log.i("Database not is empty","In database have at least an emergency contact");
      } // End of if
    } // End of else

    saveThirdContact.setOnClickListener(new View.OnClickListener() {
      // Begin of saveThirdContact.setOnClickListener

      // When the button saveFirstContact is clicked will be the method signInThirdContact
      public void onClick(View emergencyContactView) { // Begin of onClick

        // Register the third emergency contact
        if (!signInthirdContact()) { // Begin of if

          saveThirdContact.setOnClickListener(new View.OnClickListener() {
            // Begin of saveThirdContact.setOnClickListener

            // For register the third emergency contact will done some validations and if on
            // validation not passed, will be given a chance to register the emergency contact
            // again
            public void onClick(View emergencyContactView) { // Begin of onClick

              signInthirdContact();
              disableField(nameThirdContact,phoneThirdContact);
              Log.d("Emergency Contact save!"," Not have problem for save emergency contact");
            } // End of onClick
          }); // End of saveThirdContact.setOnClickListener and End of if
        // If all validation passed will disable some buttons
        } else { // End of if

          disableSaveButton(saveThirdContact,updateThirdContact,deleteThirdContact);
          Log.d("Nothing to do"," Already had one emergency contact in that third position");
        } // End of else
      } // End of onClick
    }); // End of saveThirdContact.setOnClickListener

    updateThirdContact.setOnClickListener(new View.OnClickListener() {
      // Begin of updateThirdContact.setOnClickListener

      public void onClick(View emergencyContactView) { // Begin of onClick

        // Disable some buttons and the "alterar" pass to "salvar"
        disableUpdateButton(nameThirdContact,phoneThirdContact,updateThirdContact,
                saveThirdContact);

        saveThirdContact.setOnClickListener(new View.OnClickListener() {
          // Begin of saveThirdContact.setOnClickListener

          // Update the third emergency contact
          public void onClick(View emergencyContactView) { // Begin of onClick

            updateContact(idThirdContact,nameThirdContact,phoneThirdContact,
                    saveThirdContact,updateThirdContact,deleteThirdContact);
            setOptionsVisible(saveThirdContact,updateFirstContact);
            Log.d("Update contact"," Was update at informations about third emergency contact");
          } // End of onClick
        }); // End of saveThirdContact.setOnClickListener
      } // End of onClick
    }); // End of updateThirdContact.setOnClickListener

    deleteThirdContact.setOnClickListener(new View.OnClickListener() {
      // Begin of deleteThirdContact.setOnClickListener

      // Delete third emergency contact
      public void onClick(View emergencyContactView) { //Begin of onClick

        deleteContact(nameThirdContact,phoneThirdContact,saveThirdContact,
                idThirdContact,updateThirdContact,deleteThirdContact);
        Log.d("Delete contact"," Was delete at informations about third emergency contact");

        saveThirdContact.setOnClickListener(new View.OnClickListener() {
          //Begin of saveThirdContact.setOnClickListener

          public void onClick(View emergencyContactView) { //Begin of onClick

            // For register the third emergency contact will done some validations and if on
            // validation not passed, will be given a chance to register the emergency contact
            // again
            if (!signInthirdContact()) {

              saveThirdContact.setOnClickListener(new View.OnClickListener() {
                // Begin of saveThirdContact.setOnClickListener

                public void onClick(View emergencyContactView) { // Begin of onClick

                  signInthirdContact();
                  disableField(nameThirdContact,phoneThirdContact);
                } // End of onClick
              }); // End of saveThirdContact.setOnClickListener and End of if
              // If all validation passed will disable some buttons
              Log.d("Emergency Contact save!"," Not have problem for save emergency contact");
            } else { // Begin of else

              disableSaveButton(saveThirdContact,updateThirdContact,
                      deleteThirdContact);
            } // End of else
          } // End of onClick
        }); // End of saveThirdContact.setOnClickListener
      } // End of onClick
    }); // End of deleteThirdContact.setOnClickListener
    Log.d("End of Method: ","logicThirdContact");
  } // End of logicThirdContact

  /*
   * This method is used to sign the first contact in the system, returns a bollean that informs
   * if the sign was sucessfull.
   * @return valid
   *
   */

  private boolean signInFirstContact() { // Begin of signInFirstContact

    Log.d("Begin method: ","signInFirstContact");
    // Sucess is true is all informations is correct or false if ate least information not correct
    boolean sucess = true;
    // Valid is false if sucess is true that is was register first emergency contact
    boolean valid = false;

    //Verific is all information is correct
    if (!checksName(nameFirstContact.getText().toString())) { // Begin of if

      nameContact = nameFirstContact.getText().toString();
      Log.i("Nome do Contato: ",nameContact);
      phoneContact = phoneFirstContact.getText().toString();
      Log.i("Telefone do Contato: ",phoneContact);

      sucess = emergencyContactDao.insertEmergencyContact(idFirstContact, nameContact,
                                                                phoneContact);
      // If all the information is correct, it will be saved in the database
      if (sucess) { //Begin of if

        showMessage("Contato de Emergência Cadastrado Com Sucesso!");
        valid = true;
        nameFirstContact.setEnabled(false);
        phoneFirstContact.setEnabled(false);
        disableSaveButton(saveFirstContact,updateFirstContact,deleteFirstContact);
        // End of if
        // If not possible save the first emergency contact
        Log.d("Usuário Cadastrado","O primeiro contato de emergência foi cadastrado com sucesso");
      } else { // Begin of else

        showMessage("Contato de Emergência Não Cadastrado! Tente Novamente");
        valid = false;
        Log.d("Usuário Não Cadastrado","O primeiro contato de emergência não foi cadastrado");
      } // End of else and End of if
    } else {

      // Nothing to do
    } // End of else
    Log.d("End of Method: ","signInFirstContact");
    return valid;
  } // End of signInFirstContact

  /*
   * This method is used to sign the second contact in the system, returns a bollean that informs
   * if the sign was sucessfull.
   * @return valid
   *
   */

  private boolean signInSecondContact() { // Begin of signInSecondContact

    Log.d("Begin method: ","signInSecondContact");
    // Sucess is true is all informations is correct or false if ate least information not correct
    boolean sucess = true;
    // Valid is false if sucess is true that is was register first emergency contact
    boolean valid = false;

    //Verific is all information is correct
    if (!checksName(nameSecondContact.getText().toString())) { // Begin of if

      //Verific is all information is correct
      if (!checksName(nameSecondContact.getText().toString())) { //Begin of if

        nameContact = nameSecondContact.getText().toString();
        Log.i("Nome do Contato: ",nameContact);
        phoneContact = phoneSecondContact.getText().toString();
        Log.i("Telefone do Contato: ",phoneContact);

        sucess = emergencyContactDao.insertEmergencyContact(idSecondContact, nameContact,
                                                                    phoneContact);
        // If all the information is correct, it will be saved in the database
        if (sucess) { // Begin of if

          showMessage("Contato de Emergência Cadastrado Com Sucesso!");
          valid = true;
          nameSecondContact.setEnabled(false);
          phoneSecondContact.setEnabled(false);
          disableSaveButton(saveSecondContact,updateSecondContact,deleteSecondContact);
          // End of if
          // If not possible save the second emergency contact
          Log.d("Usuário Cadastrado!"," O segundo contato de emergência foi cadastrado");
        } else { //Begin of else

          showMessage("Contato de Emergência Não Cadastrado! Tente Novamente");
          valid = true;
          Log.d("Usuário Cadastrado!", "O segundo contato de emergência não foi cadastrado");
        } // End of else and End of if
      } else { // Begin of else

        //Nothing to do
      } //End of else and End of if
    } else { //Begin of else

      //Nothing to do
    } //End of else
    Log.d("End of Method: ","signInSecondContact");
    return valid;
  } // End of signInSecondContact

  /*
   * This method is used to sign the third contact in the system, returns a bollean that informs
   * if the sign was sucessfull.
   * @return valid
   *
   */

  private boolean signInthirdContact() { // Begin of signInThirdContact

    Log.d("Begin of Method: ","signInSecondContact");
    // Sucess is true is all informations is correct or false if ate least information not correct
    boolean sucess = true;
    // Valid is false if sucess is true that is was register first emergency contact
    boolean valid = false;

    //Verific is all information is correct
    if (!checksName(nameThirdContact.getText().toString())) { //Begin of if

      //Verific is all information is correct
      if (!checksName(nameThirdContact.getText().toString())) { //Begin of if

        nameContact = nameSecondContact.getText().toString();
        Log.i("Nome do Contato: ",nameContact);
        phoneContact = phoneSecondContact.getText().toString();
        Log.i("Telefone do Contato: ",phoneContact);

        sucess = emergencyContactDao.insertEmergencyContact(idThirdContact, nameContact,
                                                                    phoneContact);
        // If all the information is correct, it will be saved in the database
        if (sucess) { //Begin of if

          showMessage("Contato de Emergência Cadastrado Com Sucesso!");
          valid = true;
          nameThirdContact.setEnabled(false);
          phoneThirdContact.setEnabled(false);
          disableSaveButton(saveThirdContact,updateThirdContact,deleteThirdContact);
          //End of if
          // If not possible save the third emergency contact
          Log.d("Usuário Cadastrado!"," O terceiro contato de emergência foi cadastrado");
        } else { //Begin of else

          showMessage("Contato de Emergência Não Cadastrado! Tente Novamente");
          valid = true;
          Log.d("Usuário Não Cadastrado!"," O terceiro contato de emergência não foi cadastrado");
        } //End of else and End of if
      } else { //Begin of else

        //Nothing to do
      } //End of else
    } else { //Begin of else

      //Nothing to do
    } //End of else
    Log.d("End of Method: ","signInThirdContact");
    return valid;
  } // End of signInThirdContact

  /*
   * This method is used to update any of the three emergency contacts already signed on EmerGo.
   * @param id id related to the contact
   * @param name emergency contact name
   * @param phone emergency contact phone
   * @param save button save
   * @param update button update
   * @param delete button delete
   *
   */

  private void updateContact(Integer id,EditText name,EditText phone,Button save,Button update,
                             Button delete) { // Begin of updateContact

    Log.d("Begin of Method: ","updateContact");
    // Sucess is true is all informations is correct or false if ate least information not correct
    boolean sucess = true;

    // Verific is all information is correct
    if (!checksName(name.getText().toString())) { //Begin of if

      nameContact = nameSecondContact.getText().toString();
      Log.i("Nome do Contato: ",nameContact);
      phoneContact = phoneSecondContact.getText().toString();
      Log.i("Telefone do Contato: ",phoneContact);

      sucess = emergencyContactDao.updateEmergencyContact(id,nameContact,phoneContact);
      // If all the information is correct, it will be changed and saved in the database
      if (sucess) { //Begin of if

        showMessage("Contato de Emergência Alterado Com Sucesso!");
        save.setVisibility(View.VISIBLE);
        save.setEnabled(false);
        update.setEnabled(true);
        delete.setEnabled(true);
        //End of if
        //If not possible change informations about emergency contact
        Log.d("Usuário Alterado"," O Contato de emergência foi alterado com sucesso");
      } else { //Begin of else

        showMessage("Contato de Emergência Não Alterado! Tente Novamente");
        Log.d("Usuário Não Alterado","O Contato de emergência não alterado com sucesso");
      }
    } else { //Begin of else

      //Nothing to do
    } //End of else
    Log.d("End of Method:","updateContact");
  } // End of updateContact

  /*
   * This method is used to exclude any of the three emergency contacts signed on system.
   * @param nameContact emergency contact name
   * @param phoneContact emergency contact phone
   * @param save button save
   * @param id emergency contact id
   * @param update button update
   * @param delete button delete
   *
   */

  private void deleteContact(final EditText nameContact, final EditText phoneContact,
                             final Button save, final Integer id, final Button update,
                             final Button delete) { // Begin of deleteContact

    Log.d("Begin of Method: ","updateContact");
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    builder.setTitle("Excluir Contato");
    builder.setMessage("Deseja Mesmo Excluir Este Contato?");

    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
      //Begin of builder.setPositiveButton

      @Override
      public void onClick(DialogInterface dialog, int which) { //Begin of onClick

        emergencyContactDao.deleteEmergencyContact(id);
        showMessage("Contato de Emergência Excluido Com Sucesso");
        save.setVisibility(View.VISIBLE);
        save.setEnabled(true);
        nameContact.setText("");
        phoneContact.setText("");
        nameContact.setEnabled(true);
        phoneContact.setEnabled(true);
        update.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        Log.d("Contato Excluido"," O contato de emergência foi excluido com sucesso");
      } //End of onClick
    }); //Enf of builder.setPositiveButton

    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
      //Begin of builder.setNegativeButton

      @Override
      public void onClick(DialogInterface dialog, int which) { //Begin of onClick

      } //End of onClick
    }); //End of builder.setNegativeButton
    builder.show();
  } // End of deleteContact

  /*
   * This method is used to check if the name informed by the user is valid.
   * If the name is valid, false is returned.
   * @param nameUser emergency contact name
   * @return boolean
   *
   */

  private boolean checksName(String nameUser) { // Begin of checksName

    Log.d("Begin of Method: ","checksName");
    // Valid is true if at least information not correct
    boolean valid = true;

    // Verific is nameUser is empty
    if (nameUser.isEmpty()) { //Begin of if

      showMessage("Nome Vazio! Informe Seu Nome.");
      return valid;
      // End of if
      // Verific if length of nameUser is smaller than minimum
    } else if (nameUser.trim().length() < minimum) { // Begin of else if

      showMessage("Informe um nome com no mínimo 3 caracteres.");
      return valid;
      // End of else if
      // Verific is nameUser is numeric
    } else if (nameUser.matches(".*\\d.*")) { // Begin of else if

      showMessage("Um nome não pode ter um número!");
      return valid;
      // Enf of else if
    }
    Log.d("End of Method: ","checksName");
    valid = false;
    return valid;
  } // End of checksName

  /*
   * This method is used to show a message to the user in the screen.
   * @param message message that will be shown to the user.
   *
   */

  private void showMessage(String message) { // Begin of showMessage

    Toast.makeText(this,"" + message,Toast.LENGTH_SHORT).show();
  } // End of showMessage

  /*
   * This method is used to disable save,update and delete buttons on the field of
   * given emergency contact.
   * @param save button used to save
   * @param update button used to update
   * @param delete button used to delete
   *
   */

  private void disableOptionsButSave(Button save, Button update, Button delete) {
    // Begin of disableOptionsButSave

    save.setVisibility(View.VISIBLE);
    update.setVisibility(View.INVISIBLE);
    delete.setVisibility(View.INVISIBLE);
  } // End of disableOptionsButSave

  /*
   * This method disable only save button.
   * @param save button used to save
   * @param update button used to update
   * @param delete button used to delete
   *
   */

  private void disableSaveButton(Button save, Button update, Button delete) {
    // Begin of disableSaveButton

    save.setEnabled(false);
    save.setVisibility(View.INVISIBLE);
    update.setVisibility(View.VISIBLE);
    update.setEnabled(true);
    delete.setVisibility(View.VISIBLE);
    delete.setEnabled(true);
  } // End of disableSaveButton
 
  /*
   * This method disable only update button.
   * @param name emergency contact name
   * @param phone emergency contact phone
   * @param update button used to update
   * @param save button used to save
   *
   */

  private void disableUpdateButton(EditText name, EditText phone, Button update, Button save) {
    // Begin of disableUpdateButton

    name.setEnabled(true);
    phone.setEnabled(true);
    update.setVisibility(View.INVISIBLE);
    save.setVisibility(View.VISIBLE);
    save.setEnabled(true);
  } // End of disableUpdateButton

  /*
   * This method is used after the user is already signed in the system, and user will try to
   * update it. It only will have the update button visible.
   * @param save button used to save
   * @param update button used to update
   *
   */

  private void setOptionsVisible(Button save, Button update) { // Begin of setOptionsVisible

    update.setVisibility(View.VISIBLE);
    save.setVisibility(View.INVISIBLE);
  } // End of setOptionsVisible

  /*
   * This method is used to disable name and phone field.
   * @param name emergency contact name
   * @param phone emergency contact phone
   *
   */

  private void disableField(EditText name,EditText phone) { // Begin of disableField

    name.setEnabled(false);
    phone.setEnabled(false);
  } // End of disableField

  /*
   * This method is used to disable an emergency contact field.
   * @param save button used to save
   * @param name emergency contact name
   * @param phone emergency contact phone
   *
   */

  private void disableField(Button save,EditText name, EditText phone) { // Begin of disableField

    save.setVisibility(View.INVISIBLE);
    name.setEnabled(false);
    phone.setEnabled(false);
  } // End of disableField

  /**
   * This method is activated when user clicks in GO button, tracing a route to the closest
   * health unity.
   * @param mapScreen actual View on app
   *
   */

  public void goClicked(View mapScreen) throws IOException, JSONException { // Begin of goClicked

    Toast.makeText(this, routeTraced , Toast.LENGTH_SHORT).show();
    Intent routeActivity = new Intent();
    routeActivity.setClass(this , RouteActivity.class);
    routeActivity.putExtra("numeroUs" , -1);
    startActivity(routeActivity);
    finish();
  } // End of goClicked

  /**
   * This method list all the USs, by proximity of the user location, after the list button
   * is clicked.
   * @param mapScreen actual View on app
   *
   */

  public void listMapsImageClicked(View mapScreen) { // Begin of listMapsImageClicked

    Intent listOfHealth = new Intent();
    listOfHealth.setClass(this , ListOfHealthUnitsController.class);
    startActivity(listOfHealth);
    finish();
  } // End of listMapsImageClicked

  /**
   * This method is activated when user is already in the configuration screen, and
   * try to open it again.
   * @param mapScreen actual View on app
   *
   */

  public void openConfig(View mapScreen) { // Begin of openConfig

    Intent config = new Intent();
    config.setClass(this, ConfigController.class);
    startActivity(config);
  } // End of openConfig

  /**
   * This method is activated when user clicks in the map button, and open a new map.
   * @param mapScreen actual View on app
   *
   */

  public void openMap(View mapScreen) { // Begin of openMap

    Intent mapActivity = new Intent();
    mapActivity.setClass(this, MapScreenController.class);
    startActivity(mapActivity);
    finish();
  } // End of openMap
} // End of EmergencyContactController class