package co.agenda.alex;
import android.content.*;
public class RestoreReceiver extends BroadcastReceiver { @Override public void onReceive(Context c,Intent i){ReminderManager.reschedule(c);} }
