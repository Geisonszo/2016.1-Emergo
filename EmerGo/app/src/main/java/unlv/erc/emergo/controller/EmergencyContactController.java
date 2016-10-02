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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import dao.EmergencyContactDao;
import helper.MaskHelper;
import unlv.erc.emergo.R;

public class EmergencyContactController extends Activity {

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
  private EmergencyContactDao emergencyContactDao;

  public EmergencyContactController(){
      //Empty Constructor.
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    emergencyContactDao = new EmergencyContactDao(this);

    setContentView(R.layout.emergency_contact);

    saveFirstContact = (Button) findViewById(R.id.saveButtonFirstContact);
    saveSecondContact = (Button) findViewById(R.id.saveSecondContactButton);
    saveThirdContact = (Button) findViewById(R.id.saveThirdContactButton);

    updateFirstContact = (Button) findViewById(R.id.updateButtonFirstContact);
    updateSecondContact = (Button) findViewById(R.id.updateSecondContactButton);
    updateThirdContact = (Button) findViewById(R.id.updateThirdContactButton);

    nameFirstContact = (EditText) findViewById(R.id.nameFirstContactEditText);
    nameSecondContact = (EditText) findViewById(R.id.nameSecondContactEditText);
    nameThirdContact = (EditText) findViewById(R.id.nameThirdContactEditText);

    phoneFirstContact = (EditText) findViewById(R.id.phoneEditText);
    phoneFirstContact.addTextChangedListener(MaskHelper.insert("(###)#####-####",
                                                                phoneFirstContact));
    phoneSecondContact = (EditText) findViewById(R.id.phoneSecondContactEditText);
    phoneSecondContact.addTextChangedListener(MaskHelper.insert("(###)#####-####",
                                                                phoneSecondContact));
    phoneThirdContact = (EditText) findViewById(R.id.phoneThirdContactEditText);
    phoneThirdContact.addTextChangedListener(MaskHelper.insert("(###)#####-####",
                                                                phoneThirdContact));

    deleteFirstContact = (Button) findViewById(R.id.deleteFirstContactButton);
    deleteSecondContact = (Button) findViewById(R.id.deleteSecondContactButton);
    deleteThirdContact = (Button) findViewById(R.id.deleteThirdContactButton);

    Cursor result = emergencyContactDao.getEmergencyContact();

    // Verifies that has something registered in the database on the emergencyContact.
    if (result.getCount() == databaseEmpty) { // Begin of if

      disableOptionsButSave(saveFirstContact,updateFirstContact,deleteFirstContact);
      // End of if
    } else { //Begin of else

      // If you have something in the emergencyContact database will be shown in the field name,
      // phone to first contact.
      if (result.moveToFirst()) { // Begin of if

        nameFirstContact.setText(result.getString(nameOnDatabase));
        phoneFirstContact.setText(result.getString(phoneOnDatabase));
        disableField(saveFirstContact,nameFirstContact,phoneFirstContact);
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
        } else { // Begin of else

          disableSaveButton(saveFirstContact,updateFirstContact,deleteFirstContact);
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
            } else { // Begin of else

              disableSaveButton(saveFirstContact,updateFirstContact,deleteFirstContact);
            } // End of else
          } // End of onClick
        }); // End of saveFirstContact.setOnClickListener
      } // End of onClick
    }); // End of deleteFirstContact.setOnClickListener

    // Verifies that has something registered in the database on the emergencyContact.
    if (result.getCount() == databaseEmpty) { // Begin of if

      disableOptionsButSave(saveSecondContact, updateSecondContact,deleteSecondContact);
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
            } // End of onClick
          }); // End of saveSecondContact.setOnClickListener and End of if
        // If all validation passed will disable some buttons
        } else { // Begin of else

          disableSaveButton(saveSecondContact,updateSecondContact,deleteSecondContact);
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

    // Verifies that has something registered in the database on the emergencyContact.
    if (result.getCount() == databaseEmpty) { // Begin of if

      disableOptionsButSave(saveThirdContact, updateThirdContact,deleteThirdContact);
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
            } // End of onClick
          }); // End of saveThirdContact.setOnClickListener and End of if
        // If all validation passed will disable some buttons
        } else { // End of if

          disableSaveButton(saveThirdContact,updateThirdContact,deleteThirdContact);
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
              } else { // Begin of else

                disableSaveButton(saveThirdContact,updateThirdContact,
                     deleteThirdContact);
              } // End of else
            } // End of onClick
          }); // End of saveThirdContact.setOnClickListener
        } // End of onClick
      }); // End of deleteThirdContact.setOnClickListener
  } // End of onCreate

  /*
   * This method is used to sign the first contact in the system, returns a bollean that informs
   * if the sign was sucessfull.
   * @return valid
   *
   */

  private boolean signInFirstContact() {

    // Sucess is true is all informations is correct or false if ate least information not correct
    boolean sucess = true;
    // Valid is false if sucess is true that is was register first emergency contact
    boolean valid = false;

    //Verific is all information is correct
    if (!checksName(nameFirstContact.getText().toString())) { // Begin of if

      nameContact = nameFirstContact.getText().toString();
      phoneContact = phoneFirstContact.getText().toString();

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
      } else { // Begin of else

        showMessage("Contato de Emergência Não Cadastrado! Tente Novamente");
        valid = false;
      } // End of else and End of if
    } else {

      //Nothing to do
    } //End of else
    return valid;
  }

  /*
   * This method is used to sign the second contact in the system, returns a bollean that informs
   * if the sign was sucessfull.
   * @return valid
   *
   */

  private boolean signInSecondContact() {

    // Sucess is true is all informations is correct or false if ate least information not correct
    boolean sucess = true;
    // Valid is false if sucess is true that is was register first emergency contact
    boolean valid = false;

    //Verific is all information is correct
    if (!checksName(nameSecondContact.getText().toString())) { // Begin of if

      //Verific is all information is correct
      if (!checksName(nameSecondContact.getText().toString())) { //Begin of if

        nameContact = nameSecondContact.getText().toString();
        phoneContact = phoneSecondContact.getText().toString();
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
        } else { //Begin of else

          showMessage("Contato de Emergência Não Cadastrado! Tente Novamente");
          valid = true;
        } // End of else and End of if
      } else { // Begin of else

        //Nothing to do
      } //End of else and End of if
    } else { //Begin of else

      //Nothing to do
    } //End of else
    return valid;
  }

  /*
   * This method is used to sign the third contact in the system, returns a bollean that informs
   * if the sign was sucessfull.
   * @return valid
   *
   */

  private boolean signInthirdContact() {

    // Sucess is true is all informations is correct or false if ate least information not correct
    boolean sucess = true;
    // Valid is false if sucess is true that is was register first emergency contact
    boolean valid = false;

    //Verific is all information is correct
    if (!checksName(nameThirdContact.getText().toString())) { //Begin of if

      //Verific is all information is correct
      if (!checksName(nameThirdContact.getText().toString())) { //Begin of if

        nameContact = nameThirdContact.getText().toString();
        phoneContact = phoneThirdContact.getText().toString();

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
        } else { //Begin of else

          showMessage("Contato de Emergência Não Cadastrado! Tente Novamente");
          valid = true;
        } //End of else and End of if
      } else { //Begin of else

        //Nothing to do
      } //End of else
    } else { //Begin of else

      //Nothing to do
    } //End of else
    return valid;
  }

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
                             Button delete) {

    // Sucess is true is all informations is correct or false if ate least information not correct
    boolean sucess = true;

    // Verific is all information is correct
    if (!checksName(name.getText().toString())) { //Begin of if

      nameContact = name.getText().toString();
      phoneContact = phone.getText().toString();

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
      } else { //Begin of else

        showMessage("Contato de Emergência Não Alterado! Tente Novamente");
      }
    } else { //Begin of else

      //Nothing to do
    } //End of else
  }

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
                             final Button delete) {

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
      } //End of onClick
    }); //Enf of builder.setPositiveButton

    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
      //Begin of builder.setNegativeButton

      @Override
      public void onClick(DialogInterface dialog, int which) { //Begin of onClick

      } //End of onClick
    }); //End of builder.setNegativeButton
    builder.show();
  }

  /*
   * This method is used to check if the name informed by the user is valid.
   * If the name is valid, false is returned.
   * @param nameUser emergency contact name
   * @return boolean
   *
   */

  private boolean checksName(String nameUser) {

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
    valid = false;
    return valid;
  }

  /*
   * This method is used to show a message to the user in the screen.
   * @param message message that will be shown to the user.
   *
   */

  private void showMessage(String message) {

    Toast.makeText(this,"" + message,Toast.LENGTH_SHORT).show();
  }

  /*
   * This method is used to disable save,update and delete buttons on the field of
   * given emergency contact.
   * @param save button used to save
   * @param update button used to update
   * @param delete button used to delete
   *
   */

  private void disableOptionsButSave(Button save, Button update, Button delete) {

    save.setVisibility(View.VISIBLE);
    update.setVisibility(View.INVISIBLE);
    delete.setVisibility(View.INVISIBLE);
  }

  /*
   * This method disable only save button.
   * @param save button used to save
   * @param update button used to update
   * @param delete button used to delete
   *
   */

  private void disableSaveButton(Button save, Button update, Button delete) {

    save.setEnabled(false);
    save.setVisibility(View.INVISIBLE);
    update.setVisibility(View.VISIBLE);
    update.setEnabled(true);
    delete.setVisibility(View.VISIBLE);
    delete.setEnabled(true);
  }
 
  /*
   * This method disable only update button.
   * @param name emergency contact name
   * @param phone emergency contact phone
   * @param update button used to update
   * @param save button used to save
   *
   */

  private void disableUpdateButton(EditText name, EditText phone, Button update, Button save) {

    name.setEnabled(true);
    phone.setEnabled(true);
    update.setVisibility(View.INVISIBLE);
    save.setVisibility(View.VISIBLE);
    save.setEnabled(true);
  }

  /*
   * This method is used after the user is already signed in the system, and user will try to
   * update it. It only will have the update button visible.
   * @param save button used to save
   * @param update button used to update
   *
   */

  private void setOptionsVisible(Button save, Button update) {

    update.setVisibility(View.VISIBLE);
    save.setVisibility(View.INVISIBLE);
  }

  /*
   * This method is used to disable name and phone field.
   * @param name emergency contact name
   * @param phone emergency contact phone
   *
   */

  private void disableField(EditText name,EditText phone) {

    name.setEnabled(false);
    phone.setEnabled(false);
  }

  /*
   * This method is used to disable an emergency contact field.
   * @param save button used to save
   * @param name emergency contact name
   * @param phone emergency contact phone
   *
   */

  private void disableField(Button save,EditText name, EditText phone) {

    save.setVisibility(View.INVISIBLE);
    name.setEnabled(false);
    phone.setEnabled(false);
  }

  /**
   * This method is activated when user clicks in GO button, tracing a route to the closest
   * health unity.
   * @param mapScreen actual View on app
   */

  public void goClicked(View mapScreen) throws IOException, JSONException {

    Toast.makeText(this, routeTraced , Toast.LENGTH_SHORT).show();
    Intent routeActivity = new Intent();
    routeActivity.setClass(this , RouteActivity.class);
    routeActivity.putExtra("numeroUs" , -1);
    startActivity(routeActivity);
    finish();
  }

  /**
   * This method list all the USs, by proximity of the user location, after the list button
   * is clicked.
   * @param mapScreen actual View on app
   */

  public void listMapsImageClicked(View mapScreen) {

    Intent listOfHealth = new Intent();
    listOfHealth.setClass(this , ListOfHealthUnitsController.class);
    startActivity(listOfHealth);
    finish();
  }

  /**
   * This method is activated when user is already in the configuration screen, and
   * try to open it again.
   * @param mapScreen actual View on app
   */

  public void openConfig(View mapScreen) {

    Intent config = new Intent();
    config.setClass(this, ConfigController.class);
    startActivity(config);
  }

  /**
   * This method is activated when user clicks in the map button, and open a new map.
   * @param mapScreen actual View on app
   */

  public void openMap(View mapScreen) {

    Intent mapActivity = new Intent();
    mapActivity.setClass(this, MapScreenController.class);
    startActivity(mapActivity);
    finish();
  }
}