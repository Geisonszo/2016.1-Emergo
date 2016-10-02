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

  private Button saveFirstContact;
  private Button saveSecondContact;
  private Button saveThirdContact;
  private EditText nameFirstContact;
  private EditText nameSecondContact;
  private EditText nameThirdContact;
  private EditText phoneFirstContact;
  private EditText phoneSecondContact;
  private EditText phoneThirdContact;
  private Button deleteFirstContact;
  private Button deleteSecondContact;
  private Button deleteThirdContact;
  private Button updateFirstContact;
  private Button updateSecondContact;
  private Button updateThirdContact;
  private String phoneContact = " ";
  private String nameContact = "";
  private final int idFirstContact = 1;
  private final int idSecondContact = 2;
  private final int idThirdContact = 3;
  private final int minimum = 3;
  final String routeTraced = "Rota mais próxima traçada";

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

    //Verifies that has something registered in the database on the User.
    if (result.getCount() == 0) { //Begin of if

      disableOptionsButSave(saveFirstContact,updateFirstContact,deleteFirstContact);
      //End of if
    } else { //Begin of else

      //If you have something in the User database will be shown in the field name, phone.
      if (result.moveToFirst()) { //Begin of if

        nameFirstContact.setText(result.getString(1));
        phoneFirstContact.setText(result.getString(2));
        disableField(saveFirstContact,nameFirstContact,phoneFirstContact);
      } //End of if
    } //End of else

    saveFirstContact.setOnClickListener(new View.OnClickListener() {
      //Begin of saveFirstContact.setOnClickListener

      public void onClick(View emergencyContactView) { //Begin of onClick

        if (!signInFirstContact()) { //Begin of if

          saveFirstContact.setOnClickListener(new View.OnClickListener() {
            //Begin of saveFirstContact.setOnClickListener

            public void onClick(View emergencyContactView) { //Begin of onClick

              signInFirstContact();
              disableField(nameFirstContact,phoneFirstContact);
            } //End of onClick
          }); //End of saveFirstContact.setOnClickListener and End of else
        } else { //Begin of else

          disableSaveButton(saveFirstContact,updateFirstContact,deleteFirstContact);
        } //End of else
      } //End of onClick
    }); //End of saveFirstContact.setOnClickListener

    updateFirstContact.setOnClickListener(new View.OnClickListener() {
      //Begin of updateFirstContact.setOnClickListener

      public void onClick(View emergencyContactView) { //Begin of onClick

        disableUpdateButton(nameFirstContact, phoneFirstContact, updateFirstContact,
            saveFirstContact);

            saveFirstContact.setOnClickListener(new View.OnClickListener() {
              //Begin of saveFirstContact.setOnClickListener

              public void onClick(View emergencyContactView) { //Begin of onClick

                updateContact(idFirstContact,nameFirstContact,phoneFirstContact,
                    saveFirstContact,updateFirstContact,deleteFirstContact);
                setOptionsVisible(saveFirstContact,updateFirstContact);
              } //End of onClick
            }); //End of saveFirstContact.setOnClickListener
      } //End of onClick
    }); //End of updateFirstContact.setOnClickListener

    deleteFirstContact.setOnClickListener(new View.OnClickListener() {
      //Begin of deleteFirstContact.setOnClickListener

      public void onClick(View emergencyContactView) { //Begin of onClick

        deleteContact(nameFirstContact,phoneFirstContact,saveFirstContact,
            idFirstContact,updateFirstContact,deleteFirstContact);

          saveFirstContact.setOnClickListener(new View.OnClickListener() {
            //Begin of saveFirstContact.setOnClickListener

            public void onClick(View emergencyContactView) { //Begin of onClick

              if (!signInFirstContact()) { //Begin of if

                saveFirstContact.setOnClickListener(new View.OnClickListener() {
                  //Begin of saveFirstContact.setOnClickListener

                  public void onClick(View emergencyContactView) { //Begin of onClick

                    signInFirstContact();
                    disableField(nameFirstContact,phoneFirstContact);
                  } //End of onClick
                }); //End of saveFirstContact.setOnClickListener and End of if
              } else { //Begin of else

                  disableSaveButton(saveFirstContact,updateFirstContact,
                      deleteFirstContact);
              } //End of else
            } //End of onClick
          }); //End of saveFirstContact.setOnClickListener
      } //End of onClick
    }); //End of deleteFirstContact.setOnClickListener

    if (result.getCount() == 0) { //Begin of if

      disableOptionsButSave(saveSecondContact, updateSecondContact,deleteSecondContact);
      //End of if
    } else { //Begin of else

      if (result.moveToNext()) { //Begin of if

        nameSecondContact.setText(result.getString(1));
        phoneSecondContact.setText(result.getString(2));
        disableField(saveSecondContact,nameSecondContact,phoneSecondContact);
      } //End of if
    } // End of else

    saveSecondContact.setOnClickListener(new View.OnClickListener() {
      //Begin of saveSecondContact.setOnCLickListener

      public void onClick(View emergencyContactView) { //Begin of onClick

        if (!signInSecondContact()) { //Begin of if

          saveSecondContact.setOnClickListener(new View.OnClickListener() {
            //Begin of saveSecondContact.setOnClickListener

            public void onClick(View emergencyContactView) { //Begin of onClick

              signInSecondContact();
              disableField(nameSecondContact,phoneSecondContact);
            } //End of onClick
          }); //End of saveSecondContact.setOnClickListener and End of if
        } else { //Begin of else

          disableSaveButton(saveSecondContact,updateSecondContact,deleteSecondContact);
        } //End of else
      } //End of onClick
    }); //End of saveSecondContact.setOnClickListener

    updateSecondContact.setOnClickListener(new View.OnClickListener() {
      //Begin of updateSecondContact.setOnClickListener

      public void onClick(View emergencyContactView) { //Begin of onClick

        disableUpdateButton(nameSecondContact, phoneSecondContact, updateSecondContact,
            saveSecondContact);

          saveSecondContact.setOnClickListener(new View.OnClickListener() {
            //Begin of saveSecondContact.setOnClickListener

            public void onClick(View emergencyContactView) { //Begin of onClick

              updateContact(idSecondContact,nameSecondContact,phoneSecondContact,
                  saveSecondContact,updateSecondContact,deleteSecondContact);
                setOptionsVisible(saveSecondContact,updateSecondContact);
            } //End of onClick
          }); //End of saveSecondContact.setOnClickListener
      } //End of onClick
    }); //End of updateSecondContact.setOnClickListener

    deleteSecondContact.setOnClickListener(new View.OnClickListener() {
      //Begin of deleteSecondContact.setOnClickListener

      public void onClick(View emergencyContactView) { //Begin of onClick

        deleteContact(nameSecondContact,phoneSecondContact,saveSecondContact,
            idSecondContact,updateSecondContact,deleteSecondContact);

          saveSecondContact.setOnClickListener(new View.OnClickListener() {
            //Begin of saveSecondContact.setOnClickListener

            public void onClick(View emergencyContactView) { //Begin of onClick

              if (!signInSecondContact()) { //Begin of if

                saveSecondContact.setOnClickListener(new View.OnClickListener() {
                  //Begin of saveSecondContact.setOnClickListener

                  public void onClick(View emergencyContactView) { //Begin of onClick

                    signInSecondContact();
                    disableField(nameSecondContact,phoneSecondContact);
                  } //End of onClick
                }); //End of saveSecondContact.setOnClickListener and End of if
              } else { //Begin of if

                disableSaveButton(saveSecondContact,updateSecondContact,
                    deleteSecondContact);
              } //End of else
            } //End of onClick
          }); //End of saveSecondContact.setOnClickListener
        } //End of onClick
      }); //End of deleteSecondContact.setOnClickListener

    if (result.getCount() == 0) { //Begin of if

      disableOptionsButSave(saveThirdContact, updateThirdContact,deleteThirdContact);
      //End of if
    } else { //Begin of else

      if (result.moveToNext()) { //Begin of if

        nameThirdContact.setText(result.getString(1));
        phoneThirdContact.setText(result.getString(2));
        disableField(saveThirdContact,nameThirdContact,phoneThirdContact);
      } //End of if
    } //End of else

    saveThirdContact.setOnClickListener(new View.OnClickListener() {
      //Begin of saveThirdContact.setOnClickListener

      public void onClick(View emergencyContactView) { //Begin of onClick

        if (!signInthirdContact()) { //Begin of if

          saveThirdContact.setOnClickListener(new View.OnClickListener() {
            //Begin of saveThirdContact.setOnClickListener

            public void onClick(View emergencyContactView) { //Begin of onClick

              signInthirdContact();
              disableField(nameThirdContact,phoneThirdContact);
            } //End of onClick
          }); //End of saveThirdContact.setOnClickListener and End of if
        } else { //End of if

          disableSaveButton(saveThirdContact,updateThirdContact,deleteThirdContact);
        } //End of else
      } //End of onClick
    }); //End of saveThirdContact.setOnClickListener

    updateThirdContact.setOnClickListener(new View.OnClickListener() {
      //Begin of updateThirdContact.setOnClickListener

      public void onClick(View emergencyContactView) { //Begin of onClick

        disableUpdateButton(nameThirdContact,phoneThirdContact,updateThirdContact,
            saveThirdContact);

          saveThirdContact.setOnClickListener(new View.OnClickListener() {
            //Begin of saveThirdContact.setOnClickListener

            public void onClick(View emergencyContactView) { //Begin of onClick

              updateContact(idThirdContact,nameThirdContact,phoneThirdContact,
                  saveThirdContact,updateThirdContact,deleteThirdContact);
                setOptionsVisible(saveThirdContact,updateFirstContact);

            } //End of onClick
          }); //End of saveThirdContact.setOnClickListener
      } //End of onClick
    }); //End of updateThirdContact.setOnClickListener

    deleteThirdContact.setOnClickListener(new View.OnClickListener() {
      //Begin of deleteThirdContact.setOnClickListener

      public void onClick(View emergencyContactView) { //Begin of onClick

        deleteContact(nameThirdContact,phoneThirdContact,saveThirdContact,
            idThirdContact,updateThirdContact,deleteThirdContact);

          saveThirdContact.setOnClickListener(new View.OnClickListener() {
            //Begin of saveThirdContact.setOnClickListener

            public void onClick(View emergencyContactView) { //Begin of onClick

              if (!signInthirdContact()) {

                saveThirdContact.setOnClickListener(new View.OnClickListener() {
                  //Begin of saveThirdContact.setOnClickListener

                  public void onClick(View emergencyContactView) { //Begin of onClick

                    signInthirdContact();
                    disableField(nameThirdContact,phoneThirdContact);
                  } //End of onClick
                }); //End of saveThirdContact.setOnClickListener and End of if
              } else { //Begin of else

                disableSaveButton(saveThirdContact,updateThirdContact,
                     deleteThirdContact);
              } //End of else
            } //End of onClick
          }); //End of saveThirdContact.setOnClickListener
        } //End of onClick
      }); //End of deleteThirdContact.setOnClickListener
  } //End of onCreate

  /**
   * This method is used to sign the first contact in the system, returns a bollean that informs
   * if the sign was sucessfull.
   * @return valid
   *
   */

  private boolean signInFirstContact() {

    boolean sucess = true;
    boolean valid = false;
    if (!checksName(nameFirstContact.getText().toString())) { //Begin of if

      nameContact = nameFirstContact.getText().toString();
      phoneContact = phoneFirstContact.getText().toString();

      sucess = emergencyContactDao.insertEmergencyContact(idFirstContact, nameContact,
                                                                phoneContact);
      if (sucess) { //Begin of if

        showMessage("Contato de Emergência Cadastrado Com Sucesso!");
        valid = true;
        nameFirstContact.setEnabled(false);
        phoneFirstContact.setEnabled(false);
        disableSaveButton(saveFirstContact,updateFirstContact,deleteFirstContact);
        //End of if
      } else { //Begin of else

        showMessage("Contato de Emergência Não Cadastrado! Tente Novamente");
        valid = false;
      } //End of else
    } //End of if
    return valid;
  }

  /**
   * This method is used to sign the second contact in the system, returns a bollean that informs
   * if the sign was sucessfull.
   * @return valid
   *
   */

  private boolean signInSecondContact() {
    boolean sucess = true;
    boolean valid = false;

    if (!checksName(nameSecondContact.getText().toString())) { //Begin of if

      if (!checksName(nameSecondContact.getText().toString())) { //Begin of if

        nameContact = nameSecondContact.getText().toString();
        phoneContact = phoneSecondContact.getText().toString();
        sucess = emergencyContactDao.insertEmergencyContact(idSecondContact, nameContact,
                                                                    phoneContact);
        if (sucess) { //Begin of if

          showMessage("Contato de Emergência Cadastrado Com Sucesso!");
          valid = true;
          nameSecondContact.setEnabled(false);
          phoneSecondContact.setEnabled(false);
          disableSaveButton(saveSecondContact,updateSecondContact,deleteSecondContact);
          //End of if
        } else { //Begin of else

          showMessage("Contato de Emergência Não Cadastrado! Tente Novamente");
          valid = true;
        } //End of else and End of if
      } else { //Begin of else

        //Nothing to do
      } //End of else and End of if
    } else { //Begin of else

      //Nothing to do
    } //End of else
    return valid;
  }

  /**
   * This method is used to sign the third contact in the system, returns a bollean that informs
   * if the sign was sucessfull.
   * @return valid
   *
   */

  private boolean signInthirdContact() {

    boolean sucess = true;
    boolean valid = false;

    if (!checksName(nameThirdContact.getText().toString())) { //Begin of if

      if (!checksName(nameThirdContact.getText().toString())) { //Begin of if

        nameContact = nameThirdContact.getText().toString();
        phoneContact = phoneThirdContact.getText().toString();

        sucess = emergencyContactDao.insertEmergencyContact(idThirdContact, nameContact,
                                                                    phoneContact);
        if (sucess) { //Begin of if

          showMessage("Contato de Emergência Cadastrado Com Sucesso!");
          valid = true;
          nameThirdContact.setEnabled(false);
          phoneThirdContact.setEnabled(false);
          disableSaveButton(saveThirdContact,updateThirdContact,deleteThirdContact);
          //End of if
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

  /**
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

    boolean sucess = true;

    if (!checksName(name.getText().toString())) { //Begin of if

      nameContact = name.getText().toString();
      phoneContact = phone.getText().toString();

      sucess = emergencyContactDao.updateEmergencyContact(id,nameContact,phoneContact);
      if (sucess) { //Begin of if

        showMessage("Contato de Emergência Alterado Com Sucesso!");
        save.setVisibility(View.VISIBLE);
        save.setEnabled(false);
        update.setEnabled(true);
        delete.setEnabled(true);
        //End of if
      } else { //Begin of else

        showMessage("Contato de Emergência Não Alterado! Tente Novamente");
      }
    } else { //Begin of else

      //Nothing to do
    } //End of else
  }

  /**
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

  /**
   * This method is used to check if the name informed by the user is valid.
   * If the name is valid, false is returned.
   * @param nameUser emergency contact name
   * @return boolean
   *
   */

  private boolean checksName(String nameUser) {

    boolean valid = true;

    if (nameUser.isEmpty()) { //Begin of if

      showMessage("Nome Vazio! Informe Seu Nome.");
      return valid;
      //End of if
    } else if (nameUser.trim().length() < minimum) { //Begin of else if

      showMessage("Informe um nome com no mínimo 3 caracteres.");
      return valid;
      //End of else if
    } else if (nameUser.matches(".*\\d.*")) { //Begin of else if

      showMessage("Um nome não pode ter um número!");
      return valid;
      //Enf of else if
    }
    valid = false;
    return valid;
  }

  /**
   * This method is used to show a message to the user in the screen.
   * @param message message that will be shown to the user.
   */

  private void showMessage(String message) {

    Toast.makeText(this,"" + message,Toast.LENGTH_SHORT).show();
  }

  /**
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

  /**
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
 
  /**
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

  /**
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

  /**
   * This method is used to disable name and phone field.
   * @param name emergency contact name
   * @param phone emergency contact phone
   *
   */

  private void disableField(EditText name,EditText phone) {

    name.setEnabled(false);
    phone.setEnabled(false);
  }

  /**
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