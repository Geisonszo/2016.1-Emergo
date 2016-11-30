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

public class EmergencyContactController extends Activity {

  // O usuário poderá salvar 3 contatos de emergência.

  // Name must be at least 3 characters
  final static int MINIMUM_LENGTH_NAME = 3;

  // Button for saveFirstContact
  private Button saveFirstContact;
  // Button for saveSecondContact
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
  // Id constant of firstContact
  private static final int IDFIRSTCONTACT = 1;
  // Id constant of secondContact
  private static final int IDSECONDCONTACT = 2;
  // Id constant of thirdContact
  private static final int IDTHIRDCONTACT = 3;
  // Database is empty
  private static final int DATABASEEMPTY = 0;
  // The second position on database. In second position on database is refer to name of contact
  private static final int NAMEONDATABASE = 1;
  // The third position on database. In third position on database is refer to phone of contact
  private static final int PHONEONDATABASE = 2;
  // Instantiation of class EmergencyContactDao
  private EmergencyContactDao emergencyContactDao;
  // Instantiation of class Cursor
  private Cursor result;

  public EmergencyContactController() {
    //Empty Constructor.
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.emergency_contact);

    try {

      emergencyContactDao = new EmergencyContactDao(this);
      result = emergencyContactDao.getEmergencyContact();
    } catch (NullPointerException nullPointException) {

      nullPointException.printStackTrace();
    }


    assert result != null : "result can't be null";

    setBTFirstContact();
    logicFirstContact();
    setBTSecondContact();
    logicSecondContact();
    setBTThirdContact();
    logicThirdContact();
  }

  /*
   * Set informations of bottons and edittext about first emergency contact.
   *
   */

  private void setBTFirstContact() {

    saveFirstContact = (Button) findViewById(R.id.saveButtonFirstContact);
    updateFirstContact = (Button) findViewById(R.id.updateButtonFirstContact);
    deleteFirstContact = (Button) findViewById(R.id.deleteFirstContactButton);

    nameFirstContact = (EditText) findViewById(R.id.nameFirstContactEditText);
    phoneFirstContact = (EditText) findViewById(R.id.phoneEditText);
    phoneFirstContact.addTextChangedListener(MaskHelper.insert("(###)#####-####",
            phoneFirstContact));
  }

  /*
   * Organize the logic of save,update and delete of first contact.
   *
   */

  private void logicFirstContact() {

    // Verifies that has something registered in the database on the emergencyContact.
    if (result.getCount() == DATABASEEMPTY) {

      disableOptionsButSave(saveFirstContact,updateFirstContact,deleteFirstContact);
      Log.e("Database is Empty.","");
    } else {

      // If you have something in the emergencyContact database will be shown in the field name,
      // phone to first contact.
      if (result.moveToFirst() == true) {

        nameFirstContact.setText(result.getString(NAMEONDATABASE));
        phoneFirstContact.setText(result.getString(PHONEONDATABASE));
        disableField(saveFirstContact,nameFirstContact,phoneFirstContact);
        Log.e("Database not is empty","In database have at least an emergency contact");
      } else {

        // Nothing to do.
      }
    }

    saveFirstContact.setOnClickListener(new View.OnClickListener() {

      public void onClick(View emergencyContactView) {

        // Register the first emergency contact
        if (signInFirstContact() == false) {

          saveFirstContact.setOnClickListener(new View.OnClickListener() {

            // For register the first emergency contact will done some validations and if on
            // validation not passed, will be given a chance to register the emergency contact
            // again
            public void onClick(View emergencyContactView) {

              signInFirstContact();
              disableField(nameFirstContact,phoneFirstContact);
            }
          });
          // If all validation passed will disable some buttons
          Log.e("Emergency Contact save!"," Not have problem for save emergency contact");
        } else {

          disableSaveButton(saveFirstContact,updateFirstContact,deleteFirstContact);
          Log.e("Nothing to do"," Already had one emergency contact in that first position");
        }
      }
    });

    updateFirstContact.setOnClickListener(new View.OnClickListener() {

      // Disable some buttons and the "alterar" pass to "salvar"
      public void onClick(View emergencyContactView) {

        disableUpdateButton(nameFirstContact, phoneFirstContact, updateFirstContact,
                saveFirstContact);

        saveFirstContact.setOnClickListener(new View.OnClickListener() {

          // Update the first emergency contact
          public void onClick(View emergencyContactView) {

            updateContact(IDFIRSTCONTACT,nameFirstContact,phoneFirstContact,
                    saveFirstContact,updateFirstContact,deleteFirstContact);
            setOptionsVisible(saveFirstContact,updateFirstContact);
            Log.e("Update contact"," Was update at informations about first emergency contact");
          }
        });
      }
    });

    deleteFirstContact.setOnClickListener(new View.OnClickListener() {

      // Delete first emergency contact
      public void onClick(View emergencyContactView) {

        deleteContact(nameFirstContact,phoneFirstContact,saveFirstContact,
                IDFIRSTCONTACT,updateFirstContact,deleteFirstContact);
        Log.e("Delete contact"," Was delete at informations about first emergency contact");

        saveFirstContact.setOnClickListener(new View.OnClickListener() {

          public void onClick(View emergencyContactView) {

            // For register the first emergency contact will done some validations and if on
            // validation not passed, will be given a chance to register the emergency contact
            // again
            if (signInFirstContact() == false) {

              saveFirstContact.setOnClickListener(new View.OnClickListener() {

                public void onClick(View emergencyContactView) {

                  signInFirstContact();
                  disableField(nameFirstContact,phoneFirstContact);
                }
              });
              // If all validation passed will disable some buttons
              Log.e("Emergency Contact save!"," Not have problem for save emergency contact");
            } else { // Begin of else

              disableSaveButton(saveFirstContact,updateFirstContact,deleteFirstContact);
            }
          }
        });
      }
    });
  }

  /*
   * Set informations of bottons and edittext about second emergency contact.
   *
   */

  private void setBTSecondContact() {

    saveSecondContact = (Button) findViewById(R.id.saveSecondContactButton);
    updateSecondContact = (Button) findViewById(R.id.updateSecondContactButton);
    deleteSecondContact = (Button) findViewById(R.id.deleteSecondContactButton);

    nameSecondContact = (EditText) findViewById(R.id.nameSecondContactEditText);
    phoneSecondContact = (EditText) findViewById(R.id.phoneSecondContactEditText);
    phoneSecondContact.addTextChangedListener(MaskHelper.insert("(###)#####-####",
            phoneSecondContact));
  }

  /*
   * Organize the logic of save,update and delete of second contact.
   *
   */

  private void logicSecondContact() {

    // Verifies that has something registered in the database on the emergencyContact.
    if (result.getCount() == DATABASEEMPTY) {

      disableOptionsButSave(saveSecondContact, updateSecondContact,deleteSecondContact);
      Log.e("Database is Empty.","");
      // If you have something in the emergencyContact database will be shown in the field name,
      // phone to second contact.
    } else {

      // Move the "cursor" to next position. If has something on first position of table
      // emergencyContact, the "cursor" will moved to next position
      if (result.moveToNext() ==  true) {

        nameSecondContact.setText(result.getString(NAMEONDATABASE));
        phoneSecondContact.setText(result.getString(PHONEONDATABASE));
        disableField(saveSecondContact,nameSecondContact,phoneSecondContact);
        Log.e("Database not is empty","In database have at least an emergency contact");
      } else {

        // Nothing to do
      }
    }

    saveSecondContact.setOnClickListener(new View.OnClickListener() {

      public void onClick(View emergencyContactView) {

        // Register the second emergency contact
        if (signInSecondContact() == false) {

          saveSecondContact.setOnClickListener(new View.OnClickListener() {

            // For register the second emergency contact will done some validations and if on
            // validation not passed, will be given a chance to register the emergency contact
            // again
            public void onClick(View emergencyContactView) {

              signInSecondContact();
              disableField(nameSecondContact,phoneSecondContact);
              Log.e("Emergency Contact save!"," Not have problem for save emergency contact");
            }
          });
          // If all validation passed will disable some buttons
        } else {

          disableSaveButton(saveSecondContact,updateSecondContact,deleteSecondContact);
          Log.e("Nothing to do"," Already had one emergency contact in that second position");
        }
      }
    });

    updateSecondContact.setOnClickListener(new View.OnClickListener() {

      public void onClick(View emergencyContactView) {

        // Disable some buttons and the "alterar" pass to "salvar"
        disableUpdateButton(nameSecondContact, phoneSecondContact, updateSecondContact,
                saveSecondContact);

        saveSecondContact.setOnClickListener(new View.OnClickListener() {

          // Update the second emergency contact
          public void onClick(View emergencyContactView) {

            updateContact(IDSECONDCONTACT,nameSecondContact,phoneSecondContact,
                    saveSecondContact,updateSecondContact,deleteSecondContact);
            setOptionsVisible(saveSecondContact,updateSecondContact);
            Log.e("Update contact"," Was update at informations about second emergency contact");
          }
        });
      }
    });

    deleteSecondContact.setOnClickListener(new View.OnClickListener() {

      // Delete second emergency contact
      public void onClick(View emergencyContactView) {

        deleteContact(nameSecondContact,phoneSecondContact,saveSecondContact,
                IDSECONDCONTACT,updateSecondContact,deleteSecondContact);
        Log.e("Delete contact"," Was delete at informations about third emergency contact");

        saveSecondContact.setOnClickListener(new View.OnClickListener() {

          public void onClick(View emergencyContactView) {

            // For register the third emergency contact will done some validations and if on
            // validation not passed, will be given a chance to register the emergency contact
            // again
            if (signInSecondContact() == false) {

              saveSecondContact.setOnClickListener(new View.OnClickListener() {
                // Begin of saveSecondContact.setOnClickListener

                public void onClick(View emergencyContactView) {

                  signInSecondContact();
                  disableField(nameSecondContact,phoneSecondContact);
                } // End of onClick
              });
              // If all validation passed will disable some buttons
            } else {

              disableSaveButton(saveSecondContact,updateSecondContact,
                      deleteSecondContact);
            }
          }
        });
      }
    });
  }

  /*
   * Set informations of bottons and edittext about third emergency contact.
   *
   */

  private void setBTThirdContact() {

    saveThirdContact = (Button) findViewById(R.id.saveThirdContactButton);
    updateThirdContact = (Button) findViewById(R.id.updateThirdContactButton);
    deleteThirdContact = (Button) findViewById(R.id.deleteThirdContactButton);

    nameThirdContact = (EditText) findViewById(R.id.nameThirdContactEditText);
    phoneThirdContact = (EditText) findViewById(R.id.phoneThirdContactEditText);
    phoneThirdContact.addTextChangedListener(MaskHelper.insert("(###)#####-####",
            phoneThirdContact));
  }

  /*
   * Organize the logic of save,update and delete of third contact.
   *
   */

  private void logicThirdContact() {

    // Verifies that has something registered in the database on the emergencyContact.
    if (result.getCount() == DATABASEEMPTY) {

      disableOptionsButSave(saveThirdContact, updateThirdContact,deleteThirdContact);
      Log.e("Database is Empty.","");
      // If you have something in the emergencyContact database will be shown in the field name,
      // phone to third contact.
    } else {

      // Move the "cursor" to next position. If has something on second position of table
      // emergencyContact, the "cursor" will moved to next position
      if (result.moveToNext() == true) {

        nameThirdContact.setText(result.getString(NAMEONDATABASE));
        phoneThirdContact.setText(result.getString(PHONEONDATABASE));
        disableField(saveThirdContact,nameThirdContact,phoneThirdContact);
        Log.e("Database not is empty","In database have at least an emergency contact");
      } else {

        // Nothing to do.
      }
    }

    saveThirdContact.setOnClickListener(new View.OnClickListener() {

      // When the button saveFirstContact is clicked will be the method signInThirdContact
      public void onClick(View emergencyContactView) {

        // Register the third emergency contact
        if (signInthirdContact() == false) {

          saveThirdContact.setOnClickListener(new View.OnClickListener() {

            // For register the third emergency contact will done some validations and if on
            // validation not passed, will be given a chance to register the emergency contact
            // again
            public void onClick(View emergencyContactView) {

              signInthirdContact();
              disableField(nameThirdContact,phoneThirdContact);
              Log.e("Emergency Contact save!"," Not have problem for save emergency contact");
            }
          });
          // If all validation passed will disable some buttons
        } else {

          disableSaveButton(saveThirdContact,updateThirdContact,deleteThirdContact);
          Log.e("Nothing to do"," Already had one emergency contact in that third position");
        }
      }
    });

    updateThirdContact.setOnClickListener(new View.OnClickListener() {

      public void onClick(View emergencyContactView) {

        // Disable some buttons and the "alterar" pass to "salvar"
        disableUpdateButton(nameThirdContact,phoneThirdContact,updateThirdContact,
                saveThirdContact);

        saveThirdContact.setOnClickListener(new View.OnClickListener() {

          // Update the third emergency contact
          public void onClick(View emergencyContactView) {

            updateContact(IDTHIRDCONTACT,nameThirdContact,phoneThirdContact,
                    saveThirdContact,updateThirdContact,deleteThirdContact);
            setOptionsVisible(saveThirdContact,updateFirstContact);
            Log.e("Update contact"," Was update at informations about third emergency contact");
          }
        });
      }
    });

    deleteThirdContact.setOnClickListener(new View.OnClickListener() {

      // Delete third emergency contact
      public void onClick(View emergencyContactView) {

        deleteContact(nameThirdContact,phoneThirdContact,saveThirdContact,
                IDTHIRDCONTACT,updateThirdContact,deleteThirdContact);
        Log.e("Delete contact"," Was delete at informations about third emergency contact");

        saveThirdContact.setOnClickListener(new View.OnClickListener() {

          public void onClick(View emergencyContactView) {

            // For register the third emergency contact will done some validations and if on
            // validation not passed, will be given a chance to register the emergency contact
            // again
            if (signInthirdContact() == false) {

              saveThirdContact.setOnClickListener(new View.OnClickListener() {

                public void onClick(View emergencyContactView) {

                  signInthirdContact();
                  disableField(nameThirdContact,phoneThirdContact);
                }
              });
              // If all validation passed will disable some buttons
              Log.e("Emergency Contact save!"," Not have problem for save emergency contact");
            } else {

              disableSaveButton(saveThirdContact,updateThirdContact,
                      deleteThirdContact);
            }
          }
        });
      }
    });
  }

  /*
   * This method is used to sign the first contact in the system, returns a bollean that informs
   * if the sign was sucessfull.
   * @return valid
   *
   */

  private boolean signInFirstContact() {

    // String of phoneContact
    String phoneContact = "";
    // String of nameContact
    String nameContact = "";
    // Sucess is true is all informations is correct or false if ate least information not correct
    boolean sucess = true;
    // Valid is false if sucess is true that is was register first emergency contact
    boolean valid = true;

    assert sucess != true : "sucess can't be false";
    assert valid != true : "valid can't be false";


    // Conversion type from EditText to String.
    nameContact = nameFirstContact.getText().toString();
    Log.d("Nome do Contato: ",nameContact);

    // Conversion type from EditText to String.
    phoneContact = phoneFirstContact.getText().toString();
    Log.d("Telefone do Contato: ",phoneContact);

    //Verific is all information is correct
    if (checksName(nameContact) == false) {

      sucess = emergencyContactDao.insertEmergencyContact(IDFIRSTCONTACT, nameContact,
              phoneContact);
      // If all the information is correct, it will be saved in the database
      if (sucess == true) {

        showMessage("Contato de Emergência Cadastrado Com Sucesso!");
        valid = true;
        nameFirstContact.setEnabled(false);
        phoneFirstContact.setEnabled(false);
        disableSaveButton(saveFirstContact,updateFirstContact,deleteFirstContact);
        Log.e("Usuário Cadastrado","O primeiro contato de emergência foi cadastrado com sucesso");
      } else {

        showMessage("Contato de Emergência Não Cadastrado! Tente Novamente");
        valid = false;
        Log.e("Usuário Não Cadastrado","O primeiro contato de emergência não foi cadastrado");
      }
    } else {

      // Nothing to do
    }
    return valid;
  }

  /*
   * This method is used to sign the second contact in the system, returns a bollean that informs
   * if the sign was sucessfull.
   * @return valid
   *
   */

  private boolean signInSecondContact() {

    // String of phoneContact
    String phoneContact = "";
    // String of nameContact
    String nameContact = "";
    // Sucess is true is all informations is correct or false if ate least information not correct
    boolean sucess = true;
    // Valid is false if sucess is true that is was register first emergency contact
    boolean valid = true;

    assert sucess != true : "sucess can't be false";
    assert valid != true : "valid can't be false";

    // Cnversion type from EditTExt to String.
    nameContact = nameSecondContact.getText().toString();
    Log.d("Nome do Contato: ",nameContact);
    phoneContact = phoneSecondContact.getText().toString();
    Log.d("Telefone do Contato: ",phoneContact);

    //Verific is all information is correct
    if (checksName(nameContact) == false) {

      //Verific is all information is correct
      if (checksName(nameContact) == false) {



        sucess = emergencyContactDao.insertEmergencyContact(IDSECONDCONTACT, nameContact,
                phoneContact);
        // If all the information is correct, it will be saved in the database
        if (sucess == true) {

          showMessage("Contato de Emergência Cadastrado Com Sucesso!");
          valid = true;
          nameSecondContact.setEnabled(false);
          phoneSecondContact.setEnabled(false);
          disableSaveButton(saveSecondContact,updateSecondContact,deleteSecondContact);
          Log.e("Usuário Cadastrado!"," O segundo contato de emergência foi cadastrado");
        } else {

          showMessage("Contato de Emergência Não Cadastrado! Tente Novamente");
          Log.e("Usuário Não Cadastrado!", "O segundo contato de emergência não foi cadastrado");
          valid = false;
        }
      } else {

        //Nothing to do
      }
    } else {

      //Nothing to do
    }
    return valid;
  }

  /*
   * This method is used to sign the third contact in the system, returns a bollean that informs
   * if the sign was sucessfull.
   * @return valid
   *
   */

  private boolean signInthirdContact() {

    // String of phoneContact
    String phoneContact = "";
    // String of nameContact
    String nameContact = "";
    // Sucess is true is all informations is correct or false if ate least information not correct
    boolean sucess = true;
    // Valid is false if sucess is true that is was register first emergency contact
    boolean valid = true;

    assert sucess != true : "sucess can't be false";
    assert valid != true : "valid can't be false";

    // Coversion from TextEdit to String.
    nameContact = nameSecondContact.getText().toString();
    Log.d("Nome do Contato: ",nameContact);
    phoneContact = phoneSecondContact.getText().toString();
    Log.d("Telefone do Contato: ",phoneContact);

    //Verific is all information is correct
    if (checksName(nameContact) == false) {

      //Verific is all information is correct
      if (checksName(nameContact) == false) {



        sucess = emergencyContactDao.insertEmergencyContact(IDTHIRDCONTACT, nameContact,
                phoneContact);
        // If all the information is correct, it will be saved in the database
        if (sucess == true) {

          showMessage("Contato de Emergência Cadastrado Com Sucesso!");
          valid = true;
          nameThirdContact.setEnabled(false);
          phoneThirdContact.setEnabled(false);
          disableSaveButton(saveThirdContact,updateThirdContact,deleteThirdContact);
          Log.e("Usuário Cadastrado!"," O terceiro contato de emergência foi cadastrado");
        } else {

          showMessage("Contato de Emergência Não Cadastrado! Tente Novamente");
          valid = false;
          Log.e("Usuário Não Cadastrado!"," O terceiro contato de emergência não foi cadastrado");
        }
      } else {

        //Nothing to do
      }
    } else {

      //Nothing to do
    }
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

  private boolean updateContact(Integer id,EditText name,EditText phone,Button save,Button update,
                                Button delete) {

    // String of phoneContact
    String phoneContact = "";
    // String of nameContact
    String nameContact = "";
    // Constant of minimum length name
    final int MINIMUM_LENGTH_NAME = 3;
    // Sucess is true is all informations is correct or false if ate least information not correct
    boolean sucess = true;
    // Valid is false if sucess is true that is was register first emergency contact
    boolean valid = true;

    assert id < IDFIRSTCONTACT && id > IDTHIRDCONTACT: "id can't be lower than idFirstContact and "
            + "bigger than idThirdContact";
    assert name != null : "the name can't be null";
    assert name.getText().length() > MINIMUM_LENGTH_NAME;
    assert phone != null : "the phone can't be null";
    assert save != null : "the save can't be null";
    assert update != null : "the update can't be null";
    assert delete != null : "the delete can't be null";

    nameContact = nameSecondContact.getText().toString();
    Log.d("Nome do Contato: ",nameContact);
    phoneContact = phoneSecondContact.getText().toString();
    Log.d("Telefone do Contato: ",phoneContact);

    // Verific is all information is correct
    if (checksName(nameContact) == false) {

      sucess = emergencyContactDao.updateEmergencyContact(id, nameContact, phoneContact);
      // If all the information is correct, it will be changed and saved in the database
      if (sucess == true) {

        showMessage("Contato de Emergência Alterado Com Sucesso!");
        save.setVisibility(View.VISIBLE);
        save.setEnabled(false);
        update.setEnabled(true);
        delete.setEnabled(true);
        valid = true;
        Log.e("Usuário Alterado"," O Contato de emergência foi alterado com sucesso");
      } else { //Begin of else

        showMessage("Contato de Emergência Não Alterado! Tente Novamente");
        Log.e("Usuário Não Alterado","O Contato de emergência não alterado com sucesso");
        valid = false;
      }
    } else {

      //Nothing to do
    }
    return valid;
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

    assert nameContact != null : "nameContact can't be null";
    assert phoneContact != null : "phoneContact can't be null";
    assert save != null : "save can't be null";
    assert id != null : "id can't be null";
    assert update != null : "update can't be null";
    assert delete != null : "delete can't be null";

    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    builder.setTitle("Excluir Contato");
    builder.setMessage("Deseja Mesmo Excluir Este Contato?");

    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int which) {

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
        Log.e("Contato Excluido"," O contato de emergência foi excluido com sucesso");
      }
    });

    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {


      @Override
      public void onClick(DialogInterface dialog, int which) {

      }
    });
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

    // Constant of minimum length name
    final int MINIMUM_LENGTH_NAME = 3;
    // Valid is true if at least information not correct
    boolean valid = true;

    assert valid != true : "valid can't be false";
    assert nameUser != null : "nameUser can't be null";

    Log.d("Begin of Method: ","checksName");

    if (nameUser.isEmpty() == true) {

      showMessage("Nome Vazio! Informe Seu Nome.");
      return valid;
    } else if (nameUser.trim().length() < MINIMUM_LENGTH_NAME) {

      showMessage("Informe um nome com no mínimo 3 caracteres.");
      return valid;
      // Verific is nameUser is numeric
    } else if (nameUser.matches(".*\\d.*")) {

      showMessage("Um nome não pode ter um número!");
      return valid;
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

    assert message != null : "message can't be null";

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

    assert save != null : "save can't be null";
    assert update != null : "update can't be null";
    assert delete != null : "delete can't be null";

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

    assert save != null : "save can't be null";
    assert update != null : "update can't be null";
    assert delete != null : "delete can't be null";

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

    assert name != null : "name can't be null";
    assert phone != null : "phone can't be null";
    assert update != null : "update can't be null";
    assert save != null : "save can't be null";

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

    assert save != null : "save can't be null";
    assert update != null : "update can't be null";

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

    assert name != null : "name can't be null";
    assert phone != null : "phone can't be null";
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

    assert save != null : "save can't be null";
    assert name != null : "name can't be null";
    assert phone != null : "phone can't be null";

    save.setVisibility(View.INVISIBLE);
    name.setEnabled(false);
    phone.setEnabled(false);
  }

  /**
   * This method is activated when user clicks in GO button, tracing a route to the closest
   * health unity.
   * @param goClicked button of emergency
   *
   */

  private void goClicked(View goClicked) throws IOException, JSONException {

    // Constant string about route
    final String ROUTETRACED = "Rota mais próxima traçada";

    assert goClicked != null : "goClicked can't be null";

    Toast.makeText(this, ROUTETRACED , Toast.LENGTH_SHORT).show();
    Intent routeActivity = new Intent();
    routeActivity.setClass(this , RouteActivity.class);
    routeActivity.putExtra("numeroUs" , -1);
    startActivity(routeActivity);
    finish();
  }

  /**
   * This method list all the USs, by proximity of the user location, after the list button
   * is clicked.
   * @param listMaps list maps on app
   *
   */

  private void listMapsImageClicked(View listMaps) {

    Intent listOfHealth = new Intent();

    assert listMaps != null : "listMaps can't be null";

    listOfHealth.setClass(this , ListOfHealthUnitsController.class);
    startActivity(listOfHealth);
    finish();
  }

  /**
   * This method is activated when user is already in the configuration screen, and
   * try to open it again.
   * @param config open the options of config on app
   *
   */

  private void openConfig(View config) {

    Intent openConfig = new Intent();

    assert config != null : "config can't be null";

    openConfig.setClass(this, SettingsController.class);
    startActivity(openConfig);
  }

  /**
   * This method is activated when user clicks in the map button, and open a new map.
   * @param openMap open map on app
   *
   */

  private void openMap(View openMap) {

    Intent mapActivity = new Intent();

    assert openMap != null : "openMap can't be null";

    mapActivity.setClass(this, MapScreenController.class);
    startActivity(mapActivity);
    finish();
  }
}