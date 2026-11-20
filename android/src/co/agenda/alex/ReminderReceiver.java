package co.agenda.alex;
import android.content.*;
public class ReminderReceiver extends BroadcastReceiver { @Override public void onReceive(Context c,Intent i){ReminderManager.fire(c);} }
