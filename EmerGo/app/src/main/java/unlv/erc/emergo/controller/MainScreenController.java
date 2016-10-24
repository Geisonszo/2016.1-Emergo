/********************
 * Class name: MainScreenController (.java)
 *
 * Purpose: The purpose of this class is to know the current state of the user.
 ********************/

package unlv.erc.emergo.controller;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.orm.SugarContext;

import dao.HealthUnitDao;
import dao.UserDao;
import unlv.erc.emergo.R;

@SuppressWarnings("ALL")
public class MainScreenController extends Activity {

  private final HealthUnitDao dataAccessObject = new HealthUnitDao(this);
  private Cursor resultOfTheUser;
  private static final int MAXIMUM_ARRAY = 7;  //Maximum number of rows that the medical records may
  // have.
  private int clickPosition = 0;  //Initial position of the user click on the notification screen.
  private static final String TITLE_MESSAGE = "Ficha Médica";
  private static final String TEXT_MESSAGE = "Você tem uma ficha médica!";
  private static final String ALERT_MESSAGE = "Alerta de Mensagem";
  private static final String ROUTE_TRACED = "Rota mais próxima traçada";
  private static final String INFORMATION_MESSAGE = "numeroUs";

  @Override
    protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_screen);

    // Creation of the buttons.
    Button goButton = (Button) findViewById(R.id.buttonGo);
    Button fineButton = (Button) findViewById(R.id.buttonOkay);

    // Access to the database.
    Firebase.setAndroidContext(this);
    SugarContext.init(this);
    UserDao myDatabase = new UserDao(this);
    dataAccessObject.setDataOnSugar();
    resultOfTheUser = myDatabase.getUser();

    if (resultOfTheUser.getCount() != 0) {
      medicalRecordsNotification();
    } else {
      //Nothing to do
    }
  }

  /**
   * Method that traces the nearest route and call the Health Unit.
   */
  public void goClicked(View mainScreen) {

    Toast.makeText(MainScreenController.this, ROUTE_TRACED,
                Toast.LENGTH_SHORT).show();
    Intent routeActivity = new Intent();
    routeActivity.setClass(MainScreenController.this, RouteActivity.class);
    routeActivity.putExtra(INFORMATION_MESSAGE, -1);
    startActivity(routeActivity);
  }

  /**
   * Method that goes to the home page.
   */
  public void okayClicked(View view) {

    Intent mapScreen = new Intent();
    mapScreen.setClass(getBaseContext(), MapScreenController.class);
    startActivity(mapScreen);
  }

  /*
   * Method that sets up the medical records in the status bar.
   */
  private void medicalRecordsNotification() {

    resultOfTheUser.moveToFirst();
    final int notifyId = 1;
    NotificationCompat.Builder notification =
                new NotificationCompat.Builder(this);

    // Basic settings of a mobile notification.
    notification.setContentTitle(TITLE_MESSAGE);
    notification.setContentText(TEXT_MESSAGE);
    notification.setTicker(ALERT_MESSAGE);
    notification.setSmallIcon(R.drawable.icon_emergo);

    notification.setNumber(++clickPosition);

    final NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();

    String events[] = new String[7];

    // Array with the user data.
    events[0] = "Nome: " + resultOfTheUser.getString(1);
    events[1] = "Data de Nascimento: " + resultOfTheUser.getString(2);
    events[2] = "Tipo Sanguineo: " + resultOfTheUser.getString(3);
    events[3] = "Cardiaco: " + resultOfTheUser.getString(4);
    events[4] = "Diabetico: " + resultOfTheUser.getString(5);
    events[5] = "Hipertenso: " + resultOfTheUser.getString(6);
    events[6] = "Soropositivo: " + resultOfTheUser.getString(7);
    events[6] = "Observações Especiais: " + resultOfTheUser.getString(8);

    inboxStyle.setBigContentTitle(TITLE_MESSAGE);

    if (MAXIMUM_ARRAY == 7) {

      for (int aux = 0; aux < MAXIMUM_ARRAY; aux++) {
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

      notificationManager.notify(notifyId,notification.build());
    } else {

      Log.e("Limite excedido! ","O limite do array foi excedido");
    }
  }
}