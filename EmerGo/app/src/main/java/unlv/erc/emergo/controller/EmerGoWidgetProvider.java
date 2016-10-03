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

public class EmerGoWidgetProvider extends AppWidgetProvider { // Begin of EmerGoWidgetProvider

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
      int[] appWidgetIds) { // Begin of onUpdate
    Log.d("Begin of Method: ","onUpdate");

    // Instantiation of class ComponentName
    ComponentName thisWidget = new ComponentName(context, EmerGoWidgetProvider.class);
    // Array of integers widgets
    int [] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
    Log.i("Widgets: ","It is being made an instantiation of an array of widgets");
    // Private request code for the sender
    final int requestCode = 0 ;
    // Code of flags that will be used
    final int flag = 0;
    // The integer data value
    final int value = -1;

    // By clicking a widget will be directed to Router Activity class
    for (int widgetId : allWidgetIds) { // Begin for

      // Instantiation of class Intent
      Intent intent = new Intent(context, RouteActivity.class);
      intent.putExtra("numeroUs" , value);
      PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, flag);

      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
      views.setOnClickPendingIntent(R.id.update, pendingIntent);

      appWidgetManager.updateAppWidget(widgetId, views);
    } // End for
    Log.d("End of Method: ","onUpdate");
  } // Begin of onUpdate
} // End of EmeroWidgetProvider
