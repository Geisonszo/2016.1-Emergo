/********************
 * Class name: EmergencyContactDao (.java)
 *
 * Purpose: The purpose of this class is to register the emergency contacts in the database .
 ********************/

package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmergencyContactDao extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "emerGo";
  // Database version required for the operation of the program.
  private static final int VERSION = 42;
  private static EmergencyContactDao instance = null;
  private static final String EmergencyContact_TABLE = "EmergencyContact";
  private static final String DROP_TABLE_EmergencyContact = "DROP TABLE IF EXISTS "
            + EmergencyContact_TABLE;
  //EmergencyContact data
  private static final String NAMECONTACT = "[nameContact]";
  private static final String PHONECONTACT = "[phoneContact]";
  private static final String CONTACT_ID = "[IDContact]";

  private static final String CREATE_EMERGENCYCONTACT = "CREATE TABLE IF NOT EXISTS "
          + EmergencyContact_TABLE + " ("
          + CONTACT_ID + "INTEGER PRIMARY KEY,"
          + NAMECONTACT + " VARCHAR(42), "
          + PHONECONTACT + " VARCHAR(13)); ";

  // EmergencyContactDao constructor.
  public EmergencyContactDao(Context context) {
    // Database creation with the stated name and version declared above.
    super(context, DATABASE_NAME, null, VERSION);
  }

  /**
   * This method returns a instance of EmergencyContactDao.
   * @param context  The Context in which this receiver is running.
   * @return instance An instance of EmergencyContactDao class.
   */
  public static EmergencyContactDao getInstance(Context context) {

    if (EmergencyContactDao.instance == null) {

      EmergencyContactDao.instance = new EmergencyContactDao(context);
    } else  {

      // Nothing to do.
    }
    return EmergencyContactDao.instance;
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(CREATE_EMERGENCYCONTACT);
  }

  public Cursor getEmergencyContact() {

    SQLiteDatabase database = this.getWritableDatabase();
    Cursor cursor = database.rawQuery("SELECT * FROM " + EmergencyContact_TABLE,null);

    return cursor;
  }

  /**
   * This method insert into database an emergency contact.
   * @param id The id of the contact.
   * @param nameContact The name of contact.
   * @param phone the phone of contact.
   * @return success The result of operation.
   */

  public boolean insertEmergencyContact(Integer id,String nameContact,String phone) {

    assert id != null : "id can't be null";
    assert nameContact != null : "nameContact can't be null";
    assert phone != null : "phone can't be null";

    boolean success =  false;

    SQLiteDatabase database = this.getWritableDatabase();
    // Entering values
    ContentValues contentValues = new ContentValues();
    contentValues.put(CONTACT_ID, id);
    contentValues.put(NAMECONTACT, nameContact);
    contentValues.put(PHONECONTACT,phone);

    long result = database.insert(EmergencyContact_TABLE,null,contentValues);
    database.close();

    if (result == -1) {

      return success;

    } else {

      success = true;
      return success;
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    database.execSQL(DROP_TABLE_EmergencyContact);
    onCreate(database);
  }

  /**
   * This method update the emergency contact on database.
   * @param id The id of the contact.
   * @param nameContact The name of contact.
   * @param phone the phone of contact.
   * @return success The result of operation.
   */

  public boolean updateEmergencyContact(Integer id,String nameContact,String phone) {

    assert id != null : "id can't be null";
    assert nameContact != null : "nameContact can't be null";
    assert phone != null : "phone can't be null";

    boolean success = true;

    SQLiteDatabase database = this.getWritableDatabase();
    // Update values
    ContentValues contentValues = new ContentValues();
    contentValues.put(NAMECONTACT, nameContact);
    contentValues.put(PHONECONTACT,phone);
    database.update(EmergencyContact_TABLE, contentValues, "[IDContact] = " + id,null);
    database.close();
    return success;
  }

  /**
   * This method delete an emergency contact from database .
   * @param id The id of the contact.
   * @return success The result of operation.
   */

  public Integer deleteEmergencyContact(Integer id) {

    assert id != null : "id can't be null";

    int success = 0;

    SQLiteDatabase database = this.getWritableDatabase();
    success = database.delete(EmergencyContact_TABLE, "[IDContact] = " + id,null);

    return success;

  }
}