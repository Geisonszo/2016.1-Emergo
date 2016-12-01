/********************
 * Class name: MedicalRecordsController (.java)
 *
 * Purpose: The purpose of this class is to register the medical file of the user.
 ********************/

package unlv.erc.emergo.controller;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.UserDao;
import helper.MaskHelper;
import unlv.erc.emergo.R;

@SuppressWarnings("ALL")
public class MedicalRecordsController extends Activity {

  private static final String TAG = "MedicalRecordsControlle";

  private EditText fullName;
  private EditText birthday;
  private EditText observations;

  private Spinner typeBlood;
  private Spinner cardiac;
  private Spinner diabect;
  private Spinner hypertension;
  private Spinner seropositive;

  private Button saveButton;
  private Button updateButton;
  private Button deleteButton;

  private static final Integer ID = 1;
  private UserDao myDatabase;

  public MedicalRecordsController() {

  }

  @Override
  protected void onCreate(final Bundle savedInstanceState) {

    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

    assert savedInstanceState != null : "savedInstanceState can't be null";

    super.onCreate(savedInstanceState);
    setContentView(R.layout.medical_records);

    myDatabase = new UserDao(this);

    // Creation of the edit fields.
    fullName = (EditText) findViewById(R.id.fullNameEditText);
    birthday = (EditText) findViewById(R.id.birthdayEditText);
    observations = (EditText) findViewById(R.id.observationsEditText);
    typeBlood = (Spinner) findViewById(R.id.typeBloodSpinner);
    birthday.addTextChangedListener(MaskHelper.insert("##/##/####", birthday));
    cardiac = (Spinner) findViewById(R.id.cardiacSpinner);
    diabect = (Spinner) findViewById(R.id.diabeticSpinner);
    hypertension = (Spinner) findViewById(R.id.hipertensionSpinner);
    seropositive = (Spinner) findViewById(R.id.soropositiveSpinner);
    saveButton = (Button) findViewById(R.id.saveButton);
    updateButton = (Button) findViewById(R.id.updateButton);
    deleteButton = (Button) findViewById(R.id.deleteButton);

    Cursor result = myDatabase.getUser();

    // checks the number of rows filled in Cursor result is equal to zero and...
    if (result.getCount() == 0) {
      // ... if it is, disable the buttons

      disableOptions(saveButton,updateButton,deleteButton);
    } else {

      if (result.moveToFirst()) {
        // if it is in the first position, allocate values ​​to fields and disable

        fullName.setText(result.getString(1));
        birthday.setText(result.getString(2));
        observations.setText(result.getString(8));
        disableField(saveButton,fullName,birthday,observations,cardiac,diabect,hypertension,
                seropositive,typeBlood);
      } else {

        // Nothing to do
      }
    } // end of if/else.

    // Sets the save button.
    saveButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {

        if (createUser() == false) {
          // Checks if there is already an established user.

          saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
              // if it does not exist, create a user

              createUser();
            }
          });
        } else {

          disableButtons(saveButton,updateButton,deleteButton);
        }
      }
    });

    // Sets the update button.
    updateButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {

        cancelNotification();
        disableJustUpdateButton(fullName, birthday,updateButton,saveButton,observations,
                typeBlood,cardiac,diabect,hypertension,seropositive);
        saveButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {
            updateUser(ID,saveButton,updateButton,deleteButton);
            visibleOptions(saveButton,updateButton);
          }
        });
      }
    });

    // Sets the delete button.
    deleteButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        deleteUser(fullName,birthday,observations,saveButton,ID,updateButton,deleteButton,
                typeBlood,cardiac,diabect,hypertension,seropositive);
        saveButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {
            if (createUser() == false) {
              // Checks if there is already an established user.
              saveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                  createUser();
                }
              });
            } else {
              disableButtons(saveButton,updateButton,deleteButton);
            }
          }
        });
      }
    });

    System.gc(); // Performs garbage collection.
  }

  /**
   * This method aims to direct the user to the page that forwards to the nearest health unit and
   *  call the SAMU.
   *
   * @param mapScreen go to RouteActivity class.
   */
  public void goClicked(View mapScreen) {

    Log.d(TAG, "goClicked() called with: mapScreen = [" + mapScreen + "]");

    assert mapScreen != null : "mapScreen can't be null";

    final String FUNCTION_NOT_ENABLE_MESSAGE = "Função não habilitada!";

    Toast.makeText(this , FUNCTION_NOT_ENABLE_MESSAGE , Toast.LENGTH_SHORT).show();
    Intent routeActivity = new Intent();
    routeActivity.setClass(this, RouteActivity.class);
    startActivity(routeActivity);
    finish();

    System.gc(); // Performs garbage collection.
  }

  /**
   * This method aims to direct the user to the page with the Health Units list.
   *
   * @param mapScreen go to ListOfHealthUnitsController class.
   */
  public void listMapsImageClicked(View mapScreen) {

    Log.d(TAG, "listMapsImageClicked() called with: mapScreen = [" + mapScreen + "]");

    assert mapScreen != null : "mapScreen can't be null";

    Intent listOfHealth = new Intent();
    listOfHealth.setClass(this , ListOfHealthUnitsController.class);
    startActivity(listOfHealth);
    finish();

    System.gc(); // Performs garbage collection.
  }

  /**
   * This method aims to direct the user to the settings page.
   *
   * @param config go to SettingsController class.
   */
  public void openSettings(View settings) {

    Log.d(TAG, "openSettings() called with: View = [" + settings + "]");

    assert settings != null : "settings can't be null";

    Intent configuration = new Intent();
    configuration.setClass(this , SettingsController.class);
    startActivity(configuration);
    finish();

    System.gc(); // Performs garbage collection.
  }

  /**
   * This method aims to direct the user to the map page.
   *
   * @param mapScreen go to MapScreenController class.
   */
  public void openMap(View mapScreen) {

    Log.d(TAG, "openMap() called with: mapScreen = [" + mapScreen + "]");

    assert mapScreen != null : "mapScreen can't be null";

    Intent mapActivity = new Intent();
    mapActivity.setClass(this, MapScreenController.class);
    startActivity(mapActivity);
    finish();

    System.gc(); // Performs garbage collection.
  }

  /**
   * This method aims to direct the page to search Health Units.
   *
   * @param search go to SearchHealthUnitController class.
   */
  public void openSearch(View search) {

    Log.d(TAG, "openSearch() called with: search = [" + search + "]");

    assert search != null : "search can't be null";

    Intent openSearch = new Intent();
    openSearch.setClass(this , SearchHealthUnitController.class);
    startActivity(openSearch);

    System.gc(); // Performs garbage collection.
  }


  private String nameUser = "";
  private String birthdayUser = "";
  private String typeBloodUser = "";
  private String cardiacUser = "";
  private String observationsUser = "";
  private String diabeticUser = "";
  private String hypertensionUser = "";
  private String seropositiveUser = "";

  /*
   * This method aims to create a user's medical records.
   */
  private boolean createUser() {

    Log.d(TAG, "createUser() called");

    final String MEDICAL_FORM_REGISTERED = "Ficha Médica Cadastrada Com Sucesso!";
    final String MEDICAL_FORM_NOT_REGISTERED = "Ficha Médica Não Cadastrada! Tente Novamente.";

    boolean sucess = true;
    boolean valid = false;

    // Verifies that the name and age field are empty.
    if (checksName(fullName.getText().toString()) == false
            && checkBirthday(birthday.getText().toString()) == false) {

      // reported data are obtained from related fields.
      birthdayUser = birthday.getText().toString();
      observationsUser = observations.getText().toString();
      nameUser = fullName.getText().toString();
      typeBloodUser = typeBlood.getSelectedItem().toString();
      cardiacUser = cardiac.getSelectedItem().toString();
      diabeticUser = diabect.getSelectedItem().toString();
      hypertensionUser = diabect.getSelectedItem().toString();
      seropositiveUser = seropositive.getSelectedItem().toString();

      sucess = myDatabase.insertUser(ID, nameUser, birthdayUser, typeBloodUser, cardiacUser,
              diabeticUser,hypertensionUser, seropositiveUser,
              observationsUser);

      // Verifies that the fields have been successfully saved in the database and ...
      if (sucess == true) {
        
        showMessage(MEDICAL_FORM_REGISTERED);
        disableOptionsCreateUser(fullName,birthday,observations,typeBlood,cardiac,diabect,
                hypertension,seropositive);
        disableOptionsUpdate(saveButton,updateButton,deleteButton);
        medicalRecordsNotification(nameUser,birthdayUser,typeBloodUser,cardiacUser,
                diabeticUser,hypertensionUser,seropositiveUser,
                observationsUser);
        valid = true;
        // ... displays a message on the situation.
      } else {
        showMessage(MEDICAL_FORM_NOT_REGISTERED);
        valid = false;
      }
    } else {

      // Nothing to do
    }

    Log.d(TAG, "createUser() returned: " + valid);
    return valid;
  }

  /*
   * This method aims to upgrade the registered user data.
   *
   * @param id number that identifies the user.
   * @param save button that saves the information.
   * @param update button that updates the information.
   * @param delete button that deletes the information.
   */
  private void updateUser(Integer id,Button save,Button update,
                          Button delete) {

    Log.d(TAG, "updateUser() called with: id = [" + id + "], save = [" + save + "], " +
        "update = [" + update + "], delete = [" + delete + "]");

    assert id != null : "id can't be null";
    assert save != null : "save can't be null";
    assert update != null : "update can't be null";
    assert delete != null : "delete can't be null";

    final String CHANGE_MESSAGE = "Alteração Realizada Com Sucesso!";
    final String CHANGE_NOT_DONE = "Não Foi Possível Fazer A Alteração, Tente Novamente.";

    boolean sucess = true;

    if (checksName(fullName.getText().toString()) == false
            && checkBirthday(birthday.getText().toString()) == false) {

      // Get the updated data.
      nameUser = fullName.getText().toString();
      birthdayUser = birthday.getText().toString();
      observationsUser = observations.getText().toString();
      nameUser = fullName.getText().toString();
      typeBloodUser = typeBlood.getSelectedItem().toString();
      cardiacUser = cardiac.getSelectedItem().toString();
      diabeticUser = diabect.getSelectedItem().toString();
      hypertensionUser = diabect.getSelectedItem().toString();
      seropositiveUser = seropositive.getSelectedItem().toString();

      sucess = myDatabase.updateUser(id, nameUser, birthdayUser, typeBloodUser, cardiacUser,
              diabeticUser, hypertensionUser, seropositiveUser,observationsUser);

      // It verifies that the data was updated and ...
      if (sucess == true) {

        showMessage(CHANGE_MESSAGE);
        save.setVisibility(View.VISIBLE);
        disableOptionsCreateUser(fullName,birthday,observations,typeBlood,cardiac,diabect,
                hypertension,seropositive);
        save.setEnabled(false);
        update.setEnabled(true);
        delete.setEnabled(true);
        medicalRecordsNotification(nameUser,birthdayUser,typeBloodUser,cardiacUser,
                diabeticUser,hypertensionUser,seropositiveUser,
                observationsUser);
        // ... displays a message on the situation.
      } else {

        showMessage(CHANGE_NOT_DONE);
      }
    } else {

      // Nothing to do
    }

    System.gc(); // Performs garbage collection.
  }

  /*
   * This method aims to make the exclusion of registered user data.
   *
   * @param name username.
   * @param birthday user birthday.
   * @param observations additional observations of the user.
   * @param save button that saves the information.
   * @param id number that identifies the user.
   * @param update button that updates the information.
   * @param delete button that deletes the information.
   * @param typeBlood type blood user.
   * @param cardiac field that checks if user have heart problems.
   * @param diabect field that checks if user have diabetes.
   * @param hypertension field that checks if the user has high blood pressure problems.
   * @param seropositive field that checks if the user is HIV positive.
   */
  private void deleteUser(final EditText name, final EditText birthday,
                          final EditText observations,
                          final Button save, final Integer id, final Button update,
                          final Button delete,final Spinner typeBlood,final Spinner cardiac,
                          final Spinner diabect,final Spinner hypertension,
                          final Spinner seropositive) {

    Log.d(TAG, "deleteUser() called with: name = [" + name + "], birthday = [" + birthday + "], " +
        "observations = [" + observations + "], save = [" + save + "], id = [" + id + "], " +
        "update = [" + update + "], delete = [" + delete + "], typeBlood = [" + typeBlood + "], " +
        "cardiac = [" + cardiac + "], diabect = [" + diabect + "], " +
        "hypertension = [" + hypertension + "], seropositive = [" + seropositive + "]");

    assert name != null : "name can't be null";
    assert birthday != null : "birthday can't be null";
    assert observations != null : "observation can't be null";
    assert save != null : "save can't be null";
    assert id != null : "id can't be null";
    assert update != null : "update can't be null";
    assert delete != null : "delete can't be null";
    assert typeBlood != null : "typeBlood can't be null";
    assert cardiac != null : "cardiac can't be null";
    assert diabect != null : "diabect can't be null";
    assert hypertension != null : "hypertension can't be null";
    assert seropositive != null : "seropositive can't be null";

    final String REQUEST_OF_EXCLUSION = "Deseja Mesmo Excluir Esta Ficha Médica?";
    final String TITLE_REQUEST_OF_EXCLUSION = "Deseja Mesmo Excluir Esta Ficha Médica?";
    final String EXCLUSION_MESSAGE = "Ficha Médica Excluida Com Sucesso";

    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    builder.setTitle(REQUEST_OF_EXCLUSION);
    builder.setMessage(TITLE_REQUEST_OF_EXCLUSION);
    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        myDatabase.deleteUser(id);
        showMessage(EXCLUSION_MESSAGE);
        visibleOptionsUser(save,name,birthday,observations,update,delete,typeBlood,cardiac,
                hypertension,seropositive,diabect);
        cancelNotification();
      }
    });
    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {

      }
    });
    builder.show();

    System.gc(); // Performs garbage collection.
  }

  private void showMessage(String message) {

    Log.d(TAG, "showMessage() called with: message = [" + message + "]");

    assert message != null : "message can't be null";

    Toast.makeText(this,"" + message,Toast.LENGTH_LONG).show();

    System.gc(); // Performs garbage collection.
  }

  private boolean checksName(String nameUser) {

    Log.d(TAG, "checksName() called with: nameUser = [" + nameUser + "]");

    assert nameUser != null : "nameUser can't be null";

    final String EMPTY_NAME_MESSAGE = "Nome Vazio! Informe Seu Nome.";
    final String LITTLE_NAME_MESSAGE = "Informe um nome com no mínimo 3 caracteres.";

    boolean valid = false;
    final int minimumSizeName = 3;

    // Verifies that the name is empty.
    if (nameUser.isEmpty()) {

      showMessage(EMPTY_NAME_MESSAGE);
      valid = true;
      return valid;
    } else {

      // Nothing to do.
    }

    if (nameUser.trim().length() < minimumSizeName) {

      showMessage(LITTLE_NAME_MESSAGE);
      fullName.requestFocus();
      valid = true;
      return valid;
    } else {

      // Nothing to do.
    }
    return valid;
  }

  /*
   * This method aims to make verification of the user's birth date.
   *
   * @param birthdayUser user birthday.
   */
  private boolean checkBirthday(String birthdayUser) {

    Log.d(TAG, "checkBirthday() called with: birthdayUser = [" + birthdayUser + "]");

    assert birthdayUser != null : "birthdayUser can't be null";

    final String YEAR_OLD_MESSAGE = "Informe um ano superior a 1942.";
    final String INVALID_YEAR_MESSAGE = "Ops, essa data é inválida!.";
    final String INVALID_DATE_MESSAGE = "Data Inválida! Informe uma data inválida,com o dia " +
            "entre 1 e 31.\n";
    final String REQUEST_MONTH_MESSAGE = "Informe um mês válido entre 1 e 12.\n";
    final String REQUEST_YEAR_MESSAGE = "Informe um ano entre 1942 e o ano atual";
    final String BIRTH_DATE_MESSAGE = "Informe a sua data de nascimento.";

    final int MINIMUMYEAR = 42;

    // Verifies that the birthday field is not empty or null and ...
    if (!birthdayUser.isEmpty()) {
      try {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        Date userDate = format.parse(birthdayUser);

        // Check if the age is greater than the determined and ...
        if (userDate.before(new Date())) {

          if (userDate.getTime() < MINIMUMYEAR) {

            showMessage(YEAR_OLD_MESSAGE);
            return true;
          } else {

            // Nothing to do
          }
          this.birthdayUser = birthdayUser;
          return false;

          // ... displays a message on the situation.
        } else {

          showMessage(INVALID_YEAR_MESSAGE);
          return true;
        }
      } catch (ParseException exception) {

        showMessage(INVALID_DATE_MESSAGE + REQUEST_MONTH_MESSAGE + REQUEST_YEAR_MESSAGE);
        return true;
      }
      // ... displays a message on the situation.
    } else {

      showMessage(BIRTH_DATE_MESSAGE);
      return true;
    }
  }

  /**
   * This method aims to let the fields visible to the user.
   *
   * @param save button that saves the information.
   * @param name username.
   * @param birthday user birthday.
   * @param observations additional observations of the user.
   * @param update button that updates the information.
   * @param delete button that deletes the information.
   * @param typeBlood type blood user.
   * @param cardiac field that checks if user have heart problems.
   * @param hypertension field that checks if the user has high blood pressure problems.
   * @param seropositive field that checks if the user is HIV positive.
   * @param diabect field that checks if user have diabetes.
   */

  private void visibleOptionsUser(Button save,EditText name,EditText birthday,
                                  EditText observations,Button update,Button delete,
                                  Spinner typeBlood,Spinner cardiac,Spinner hypertension,
                                  Spinner seropositive,Spinner diabect) {

    Log.d(TAG, "visibleOptionsUser() called with: save = [" + save + "], name = [" + name + "], " +
        "birthday = [" + birthday + "], observations = [" + observations + "], " +
        "update = [" + update + "], delete = [" + delete + "], typeBlood = [" + typeBlood + "], " +
        "cardiac = [" + cardiac + "], hypertension = [" + hypertension + "], " +
        "seropositive = [" + seropositive + "], diabect = [" + diabect + "]");

    assert name != null : "name can't be null";
    assert birthday != null : "birthday can't be null";
    assert observations != null : "observation can't be null";
    assert save != null : "save can't be null";
    assert update != null : "update can't be null";
    assert delete != null : "delete can't be null";
    assert typeBlood != null : "typeBlood can't be null";
    assert cardiac != null : "cardiac can't be null";
    assert diabect != null : "diabect can't be null";
    assert hypertension != null : "hypertension can't be null";
    assert seropositive != null : "seropositive can't be null";

    // Enabled data fields.
    save.setVisibility(View.VISIBLE);
    save.setEnabled(true);
    name.setText("");
    birthday.setText("");
    observations.setText("");
    diabect.setEnabled(true);
    name.setEnabled(true);
    birthday.setEnabled(true);
    observations.setEnabled(true);
    typeBlood.setEnabled(true);
    cardiac.setEnabled(true);
    hypertension.setEnabled(true);
    seropositive.setEnabled(true);
    update.setVisibility(View.INVISIBLE);
    delete.setVisibility(View.INVISIBLE);

    System.gc(); // Performs garbage collection.
  }

  /*
   * This method aims to let the disabled buttons to the user.
   *
   * @param save button that saves the information.
   * @param update button that updates the information.
   * @param delete button that deletes the information.
   */
  private void disableOptions(Button save, Button update,Button delete) {

    Log.d(TAG, "disableOptions() called with: save = [" + save + "], " +
        "update = [" + update + "], delete = [" + delete + "]");

    assert save != null : "save can't be null";
    assert update != null : "update can't be null";
    assert delete != null : "delete can't be null";

    save.setVisibility(View.VISIBLE);
    update.setVisibility(View.INVISIBLE);
    delete.setVisibility(View.INVISIBLE);

    System.gc(); // Performs garbage collection.
  }

  /*
   * This method aims to cancel the notification on the user's mobile phone.
   */
  private void cancelNotification() {

    Log.d(TAG, "cancelNotification() called");

    NotificationManager notifManager = (NotificationManager) this.getSystemService(Context
            .NOTIFICATION_SERVICE);
    notifManager.cancel(1);

    System.gc(); // Performs garbage collection.
  }

  /*
   * This method aims to make all writing fields inaccessible to the user.
   *
   * @param save button that saves the information.
   * @param name username.
   * @param birthday user birthday.
   * @param observations additional observations of the user.
   * @param cardiac field that checks if user have heart problems.
   * @param diabect field that checks if user have diabetes.
   * @param hypertension field that checks if the user has high blood pressure problems.
   * @param seropositive field that checks if the user is HIV positive.
   * @param typeBlood type blood user.
   */

  private void disableField(Button save,EditText name, EditText birthday,EditText observations,
                            Spinner cardiac,Spinner diabect,Spinner hypertension,
                            Spinner seropositive,Spinner typeBlood) {

    Log.d(TAG, "disableField() called with: save = [" + save + "], name = [" + name + "], " +
        "birthday = [" + birthday + "], observations = [" + observations + "], " +
        "cardiac = [" + cardiac + "], diabect = [" + diabect + "], " +
        "hypertension = [" + hypertension + "], seropositive = [" + seropositive + "], " +
        "typeBlood = [" + typeBlood + "]");

    assert name != null : "name can't be null";
    assert birthday != null : "birthday can't be null";
    assert observations != null : "observation can't be null";
    assert save != null : "save can't be null";
    assert typeBlood != null : "typeBlood can't be null";
    assert cardiac != null : "cardiac can't be null";
    assert diabect != null : "diabect can't be null";
    assert hypertension != null : "hypertension can't be null";
    assert seropositive != null : "seropositive can't be null";

    // All fields disabled.
    save.setVisibility(View.INVISIBLE);
    name.setEnabled(false);
    birthday.setEnabled(false);
    observations.setEnabled(false);
    cardiac.setEnabled(false);
    typeBlood.setEnabled(false);
    diabect.setEnabled(false);
    hypertension.setEnabled(false);
    seropositive.setEnabled(false);

    System.gc(); // Performs garbage collection.
  }

  /*
   * This method aims to disable the refresh button.
   *
   * @param save button that saves the information.
   * @param update button that updates the information.
   * @param delete button that deletes the information.
   */

  private void disableOptionsUpdate(Button save,Button update,Button delete) {

    Log.d(TAG, "disableOptionsUpdate() called with: save = [" + save + "], " +
        "update = [" + update + "], delete = [" + delete + "]");

    assert save != null : "save can't be null";
    assert update != null : "update can't be null";
    assert delete != null : "delete can't be null";

    save.setEnabled(false);
    update.setVisibility(View.VISIBLE);
    update.setEnabled(true);
    delete.setVisibility(View.VISIBLE);
    delete.setEnabled(true);

    System.gc(); // Performs garbage collection.
  }

  /**
   * This method aims to make all screen buttons invisible to the user.
   *
   * @param save button that saves the information.
   * @param update button that updates the information.
   * @param delete button that deletes the information.
   */

  private void disableButtons(Button save,Button update,Button delete) {

    Log.d(TAG, "disableButtons() called with: save = [" + save + "], " +
        "update = [" + update + "], delete = [" + delete + "]");

    assert save != null : "save can't be null";
    assert update != null : "update can't be null";
    assert delete != null : "delete can't be null";

    save.setVisibility(View.INVISIBLE);
    update.setVisibility(View.VISIBLE);
    update.setEnabled(true);
    delete.setVisibility(View.VISIBLE);
    delete.setEnabled(true);

    System.gc(); // Performs garbage collection.
  }

  /*
   * This method aims to make all fields visible to the user unless the refresh button.
   *
   * @param name username.
   * @param birthday user birthday.
   * @param update button that updates the information.
   * @param save button that saves the information.
   * @param observations additional observations of the user.
   * @param typeBlood type blood user.
   * @param cardiac field that checks if user have heart problems.
   * @param diabect field that checks if user have diabetes.
   * @param hypertension field that checks if the user has high blood pressure problems.
   * @param seropositive field that checks if the user is HIV positive.
   */

  private void disableJustUpdateButton(EditText name, EditText birthday, Button update, Button save,
                                       EditText observations,Spinner typeBlood,Spinner cardiac,
                                       Spinner diabect,Spinner hypertension,Spinner
                                               seropositive) {

    Log.d(TAG, "disableJustUpdateButton() called with: name = [" + name + "], " +
        "birthday = [" + birthday + "], update = [" + update + "], save = [" + save + "], " +
        "observations = [" + observations + "], typeBlood = [" + typeBlood + "], " +
        "cardiac = [" + cardiac + "], diabect = [" + diabect + "], " +
        "hypertension = [" + hypertension + "], seropositive = [" + seropositive + "]");

    assert name != null : "name can't be null";
    assert birthday != null : "birthday can't be null";
    assert observations != null : "observation can't be null";
    assert save != null : "save can't be null";
    assert update != null : "update can't be null";
    assert typeBlood != null : "typeBlood can't be null";
    assert cardiac != null : "cardiac can't be null";
    assert diabect != null : "diabect can't be null";
    assert hypertension != null : "hypertension can't be null";
    assert seropositive != null : "seropositive can't be null";

    // Only the update button is invisible.
    name.setEnabled(true);
    birthday.setEnabled(true);
    observations.setEnabled(true);
    update.setVisibility(View.INVISIBLE);
    save.setVisibility(View.VISIBLE);
    save.setEnabled(true);
    typeBlood.setEnabled(true);
    cardiac.setEnabled(true);
    diabect.setEnabled(true);
    hypertension.setEnabled(true);
    seropositive.setEnabled(true);

    System.gc(); // Performs garbage collection.
  }

  /*
   * Este método tem como objetivo deixar os botões de salvar e atualizar visíveis ao usuário.
   *
   * @param save button that saves the information.
   * @param update button that updates the information.
   */

  private void visibleOptions(Button save,Button update) {

    Log.d(TAG, "visibleOptions() called with: save = [" + save + "], update = [" + update + "]");

    assert save != null : "save can't be null";
    assert update != null : "update can't be null";

    update.setVisibility(View.VISIBLE);
    save.setVisibility(View.INVISIBLE);

    System.gc(); // Performs garbage collection.
  }

  /*
   * This method aims to disable only the option to create user.
   *
   * @param name username.
   * @param birthday user birthday.
   * @param observations additional observations of the user.
   * @param typeBlood type blood user.
   * @param cardiac field that checks if user have heart problems.
   * @param diabect field that checks if user have diabetes.
   * @param hypertension field that checks if the user has high blood pressure problems.
   * @param seropositive field that checks if the user is HIV positive.
   */

  private void disableOptionsCreateUser(EditText name,EditText birthday,EditText observations,
                                        Spinner typeBlood,Spinner cardiac,Spinner diabect,
                                        Spinner hypertension,Spinner seropositive) {


    Log.d(TAG, "disableOptionsCreateUser() called with: name = [" + name + "], " +
        "birthday = [" + birthday + "], observations = [" + observations + "], " +
        "typeBlood = [" + typeBlood + "], cardiac = [" + cardiac + "]," +
        " diabect = [" + diabect + "], hypertension = [" + hypertension + "], " +
        "seropositive = [" + seropositive + "]");

    assert name != null : "name can't be null";
    assert birthday != null : "birthday can't be null";
    assert observations != null : "observation can't be null";
    assert typeBlood != null : "typeBlood can't be null";
    assert cardiac != null : "cardiac can't be null";
    assert diabect != null : "diabect can't be null";
    assert hypertension != null : "hypertension can't be null";
    assert seropositive != null : "seropositive can't be null";

    // Disabled data fields.
    name.setEnabled(false);
    birthday.setEnabled(false);
    observations.setEnabled(false);
    typeBlood.setEnabled(false);
    cardiac.setEnabled(false);
    diabect.setEnabled(false);
    hypertension.setEnabled(false);
    seropositive.setEnabled(false);

    System.gc(); // Performs garbage collection.
  }

  /**
   * This method aims to show the user their medical records in the phone notification bar.
   *
   * @param nameUser username.
   * @param birthdayUser user birthday.
   * @param typeBloodUser type blood user.
   * @param cardiacUser check if user have heart problems.
   * @param diabeticUser check if user have diabetes.
   * @param hypertensionUser check if the user has high blood pressure problems.
   * @param seropositiveUser check if the user is HIV positive.
   * @param observationsUser additional observations of the user.
   */

  private void medicalRecordsNotification(String nameUser, String birthdayUser, String typeBloodUser,
                                          String cardiacUser, String diabeticUser,
                                          String hypertensionUser, String seropositiveUser,
                                          String observationsUser) {

    Log.d(TAG, "medicalRecordsNotification() called with: nameUser = [" + nameUser + "], " +
        "birthdayUser = [" + birthdayUser + "], typeBloodUser = [" + typeBloodUser + "], " +
        "cardiacUser = [" + cardiacUser + "], diabeticUser = [" + diabeticUser + "], " +
        "hypertensionUser = [" + hypertensionUser + "], " +
        "seropositiveUser = [" + seropositiveUser + "], " +
        "observationsUser = [" + observationsUser + "]");

    assert nameUser != null : "nameUser can't be null";
    assert birthday != null : "birthday can't be null";
    assert observations != null : "observation can't be null";
    assert typeBlood != null : "typeBlood can't be null";
    assert cardiac != null : "cardiac can't be null";
    assert diabect != null : "diabect can't be null";
    assert hypertension != null : "hypertension can't be null";
    assert seropositive != null : "seropositive can't be null";

    final int notifyIdentifier = 1;

    final String TITLE_MESSAGE = "Ficha Médica";
    final String TEXT_MESSAGE = "Você tem uma ficha médica!";
    final String ALERT_MESSAGE = "Alerta de Mensagem";

    final int MAXIMUM_ARRAY = 7;

    NotificationCompat.Builder notification = new NotificationCompat.Builder(this);

    // Basic settings of a mobile notification.
    notification.setContentTitle(TITLE_MESSAGE);
    notification.setContentText(TEXT_MESSAGE);
    notification.setTicker(ALERT_MESSAGE);
    notification.setSmallIcon(R.drawable.icon_emergo);

    NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

    String events[] = new String[MAXIMUM_ARRAY];

    // Array with the user data.
    events[0] = new String("Nome: "  + nameUser);
    events[1] = new String("Data de Nascimento: " + birthdayUser);
    events[2] = new String("Tipo Sanguineo: " + typeBloodUser);
    events[3] = new String("Cardiaco: " + cardiacUser);
    events[4] = new String("Diabetico: " + diabeticUser);
    events[5] = new String("Hipertenso: " + hypertensionUser);
    events[6] = new String("Soropositivo: " + seropositiveUser);
    events[6] = new String("Observações Especiais: " + observationsUser);

    inboxStyle.setBigContentTitle(TITLE_MESSAGE);

    for (int aux = 0;aux < MAXIMUM_ARRAY;aux++) {
      inboxStyle.addLine(events[aux]);
    }
    notification.setStyle(inboxStyle);

    // The user comes to the application via the notification bar.
    Intent resultIntent = new Intent(this,MedicalRecordsController.class);
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    stackBuilder.addParentStack(MedicalRecordsController.class);
    stackBuilder.addNextIntent(resultIntent);
    PendingIntent resultPedindIntent = stackBuilder.getPendingIntent(0,
            PendingIntent.FLAG_UPDATE_CURRENT);
    notification.setContentIntent(resultPedindIntent);

    NotificationManager notificationManager = (NotificationManager)
            getSystemService(NOTIFICATION_SERVICE);

    notificationManager.notify(notifyIdentifier,notification.build());

    System.gc(); // Performs garbage collection.
  }
}