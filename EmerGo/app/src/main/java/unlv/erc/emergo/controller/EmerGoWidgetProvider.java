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

import android.widget.RemoteViews;

import unlv.erc.emergo.R;

public class EmerGoWidgetProvider extends AppWidgetProvider { // Begin of EmerGoWidgetProvider

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
      int[] appWidgetIds) { // Begin of onUpdate

    // Instantiation of class ComponentName
    ComponentName thisWidget = new ComponentName(context, EmerGoWidgetProvider.class);
    // Array of integers widgets
    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

    // By clicking a widget will be directed to Router Activity class.
    for (int widgetId : allWidgetIds) { // Begin for

      // Instantiation of class Intent
      Intent intent = new Intent(context, RouteActivity.class);
      intent.putExtra("numeroUs" , -1);
      PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
      views.setOnClickPendingIntent(R.id.update, pendingIntent);

      appWidgetManager.updateAppWidget(widgetId, views);
    } // End for
  } // Begin of onUpdate
} // End of EmeroWidgetProvider
