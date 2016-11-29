/*
 * Class: EmerGoWidgetProvider (.java)
 *
 * Purpose: This class is responsible to create an EmerGo widget.
 */

package unlv.erc.emergo.controller;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.widget.RemoteViews;

import unlv.erc.emergo.R;

public class EmerGoWidgetProvider extends AppWidgetProvider {

  // Private request code for the sender
  private final int requestCode = 0 ;
  // Code of flags that will be used
  private final int flag = 0;
  // The integer data value
  private final int value = -1;

  private static final String TAG = "EmerGoWidgetProvider";

  /**
   * This method provides RemoteViews for a set of AppWidgets.
   * @param context The Context in which this receiver is running.
   * @param appWidgetManager A AppWidgetManager object you can call updateAppWidget on.
   * @param appWidgetIds The appWidgetIds for which an update is needed.
   */

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    Log.d("Begin of Method: ","onUpdate");
    assert context != null : "context can't be null";
    assert appWidgetIds != null : "appWidgetsIds can't be null";
    
    // Instantiation of class ComponentName
    ComponentName thisWidget = new ComponentName(context, EmerGoWidgetProvider.class);
    
    // Array of integers widgets
    try {

      int [] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
      Log.d("Widgets: ","It is being made an instantiation of an array of widgets");

      setWidget(allWidgetIds, context, appWidgetManager);
    }catch(RuntimeException runtimeException){

      Log.d(TAG, "onUpdate: " + runtimeException);
      runtimeException.printStackTrace();
    }


    Log.d("End of Method: ","onUpdate");
  }
  
  private void setWidget(int [] allWidgetIds, Context context, AppWidgetManager appWidgetManager) {

    Log.d(TAG, "setWidget() called with: allWidgetIds = [" + allWidgetIds + "], " +
        "context = [" + context + "], appWidgetManager = [" + appWidgetManager + "]");

    // By clicking a widget will be directed to Router Activity class
    for (int widgetId : allWidgetIds) {

      // Instantiation of class Intent
      Intent intent = new Intent(context, RouteActivity.class);

      // Instantiation of  class RemoteViews
      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

      intent.putExtra("numeroUs" , value);
      PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, flag);
      
      views.setOnClickPendingIntent(R.id.update, pendingIntent);

      appWidgetManager.updateAppWidget(widgetId, views);
    }    
  }
}
