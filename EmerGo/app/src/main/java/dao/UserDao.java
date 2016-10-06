/*****************************
 * Class name: UserDao (.java)
 *
 * Purpose: This class have all the methods needed to form the CRUD (create,change and delete) user.
 ****************************/

package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDao extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "MedicalRecords";
  private static final String USER_TABLE = "User";
  private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS" + USER_TABLE;
  private static final String NAME_USER = "[nameUser]";
  private static final String BIRTHDAY_USER = "[birthdayUser]";
  private static final String TYPE_BLOOD_USER = "[typeBloodUser]";
  private static final String CARDIAC_USER = "[cardiacUser]";
  private static final String DIABETIC_USER = "[diabeticUser]";
  private static final String HYPERTENSION_USER = "[hipertensionUser]";
  private static final String SEROPOSITIVE_USER = "[seropositiveUser]";
  private static final String OBSERVATIONS = "[observationsUser]";
  private static final String USER_ID = "[IDUser]";
  private static UserDao instance = null;
  private static final int VERSION = 42;

  private static final String CREATE_USER = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " ("
          + USER_ID + "INTEGER PRIMARY KEY,"
          + NAME_USER + " VARCHAR(42), "
          + BIRTHDAY_USER + " VARCHAR(10), "
          + TYPE_BLOOD_USER + " VARCHAR(8), "
          + CARDIAC_USER + " VARCHAR(8), "
          + DIABETIC_USER + " VARCHAR(8), "
          + HYPERTENSION_USER + "VARCHAR(8),"
          + SEROPOSITIVE_USER + "VARCHAR(8),"
          + OBSERVATIONS + "VARCHAR(442)); ";

  /**
  * You receive the "context" of the HealthUnitDao class.
  * @param context
  *
  */

  public UserDao(Context context) {

      super(context,DATABASE_NAME,null,VERSION);
  }

  /**
   * Method used to get instance of database. If the database where not
   * created, then the method create a instance.
   * @param context - Context application
   * @return UserDao.instance
   */

  public static UserDao getInstance(Context context) {

    if (UserDao.instance != null) {

            //Nothing to do.
    } else {

      UserDao.instance = new UserDao(context);
    }
    return UserDao.instance;
  }

  /**
   * Create table to user.
   * @param database
   *
   */

  @Override
  public void onCreate(SQLiteDatabase database) {

    database.execSQL(CREATE_USER);
  }

  /**
   * This method has the function of an "upgrade" in the database, then first he will end the
   * table and then will re-creates it.
   * @paramdatabase
   * @paramoldVersion
   * @paramnewVersion
   *
   */

  @Override
  public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    database.execSQL(DROP_TABLE_USER);
    onCreate(database);
  }

  /**
    * Enter user data in the "User" table.
    * @param idUser This is a id of user on database.
    * @param nameUser This is a name of user on database.
    * @param birthdayUser This a birthday of user.
    * @param typeBloodUser This a type blood of user on database.
    * @param cardiacUser Describe is user has cardiac problem on database.
    * @param diabeticUser Describe is user has diabetic problem on database.
    * @param hypertensionUser Describe is user has hypertension on database.
    * @param seropositiveUser Describe is user has seropositive on database.
    * @param observationsUser Describe observations of user on database.
    *
   */

  public boolean insertUser(Integer idUser,String nameUser,String birthdayUser,
                            String typeBloodUser,String cardiacUser,String diabeticUser,
                            String hypertensionUser,String seropositiveUser,
                            String observationsUser) {

    SQLiteDatabase database = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();

    contentValues.put(USER_ID, idUser);
    contentValues.put(NAME_USER, nameUser);
    contentValues.put(BIRTHDAY_USER,birthdayUser);
    contentValues.put(TYPE_BLOOD_USER,typeBloodUser);
    contentValues.put(CARDIAC_USER,cardiacUser);
    contentValues.put(DIABETIC_USER,diabeticUser);
    contentValues.put(HYPERTENSION_USER,hypertensionUser);
    contentValues.put(SEROPOSITIVE_USER,seropositiveUser);
    contentValues.put(OBSERVATIONS,observationsUser);

    long result = database.insert(USER_TABLE,null,contentValues);
    database.close();
    
    if (result == -1) {

      return false;
    } else {

      return true;
    }
  }

  /**
    * Change user data in the "User" table.
    * @param idUser This is a id of user on database.
    * @param nameUser This is a name of user on database.
    * @param birthdayUser This a birthday of user.
    * @param typeBloodUser This a type blood of user on database.
    * @param cardiacUser Describe is user has cardiac problem on database.
    * @param diabeticUser Describe is user has diabetic problem on database.
    * @param hypertensionUser Describe is user has hypertension on database.
    * @param seropositiveUser Describe is user has seropositive on database.
    * @param observationsUser Describe observations of user on database.
    *
   */

  public boolean updateUser(Integer idUser,String nameUser,String birthdayUser,
                            String typeBloodUser,String cardiacUser,String diabeticUser,
                            String hypertensionUser,String seropositiveUser,
                            String observationsUser) {

    SQLiteDatabase database = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();

    contentValues.put(NAME_USER, nameUser);
    contentValues.put(BIRTHDAY_USER,birthdayUser);
    contentValues.put(TYPE_BLOOD_USER,typeBloodUser);
    contentValues.put(CARDIAC_USER,cardiacUser);
    contentValues.put(DIABETIC_USER,diabeticUser);
    contentValues.put(HYPERTENSION_USER,hypertensionUser);
    contentValues.put(SEROPOSITIVE_USER,seropositiveUser);
    contentValues.put(OBSERVATIONS,observationsUser);

    database.update(USER_TABLE, contentValues,"[IDUser] = ? ",new String[] {
                                                                        String.valueOf(idUser)});
    database.close();
    return true;
  }

  /**
    * Recover registration User data in the "User" table.
    * @return data User
   */

  public Cursor getUser() {

    SQLiteDatabase database = this.getWritableDatabase();
    Cursor cursor = database.rawQuery("SELECT * FROM " + USER_TABLE,null);

    return cursor;
  }

  /**
    * Delete table User.
    * @param idUser This is a id of user on database.
    * @return deleteTableUser
   */

  public Integer deleteUser(Integer idUser) {

    SQLiteDatabase database = this.getWritableDatabase();
    int deleteTableUser = 0;

    deleteTableUser = database.delete(USER_TABLE, "[IDUser] = ? ",new String[] {
                                                                        String.valueOf(idUser)});
    return deleteTableUser;
  }
}
