package co.agenda.alex;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.os.Build;
import org.json.*;
import java.text.SimpleDateFormat;
import java.util.*;

public final class ReminderManager {
 static final String PREF="reminders", KEY="events", CHANNEL="agenda_reminders";
 static PendingIntent pending(Context c){Intent i=new Intent(c,ReminderReceiver.class).setAction("co.agenda.alex.REMIND");return PendingIntent.getBroadcast(c,17,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);}
 static long date(JSONObject o){try{SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.US);f.setLenient(false);return f.parse(o.getString("day")+" "+o.getString("time")).getTime();}catch(Exception e){return -1;}}
 static JSONArray get(Context c){try{return new JSONArray(c.getSharedPreferences(PREF,0).getString(KEY,"[]"));}catch(Exception e){return new JSONArray();}}
 static void put(Context c,JSONArray a){if(!c.getSharedPreferences(PREF,0).edit().putString(KEY,a.toString()).commit())throw new IllegalStateException("write failed");}
 static synchronized void sync(Context c,String data)throws Exception{if(data==null||data.length()>4*1024*1024)throw new IllegalArgumentException();JSONArray all=new JSONArray(data), clean=new JSONArray();if(all.length()>20000)throw new IllegalArgumentException();for(int n=0;n<all.length();n++){JSONObject x=all.getJSONObject(n);if(x.optBoolean("done")||x.optString("time").isEmpty())continue;String title=x.optString("title"),id=x.optString("id");if(title.length()==0||title.length()>200||id.length()==0||id.length()>200)continue;JSONObject o=new JSONObject();o.put("id",id);o.put("title",title);o.put("day",x.optString("day"));o.put("time",x.optString("time"));if(date(o)>System.currentTimeMillis())clean.put(o);}put(c,clean);schedule(c,clean);}
 static void schedule(Context c,JSONArray a){AlarmManager m=(AlarmManager)c.getSystemService(Context.ALARM_SERVICE);PendingIntent p=pending(c);m.cancel(p);long next=Long.MAX_VALUE;for(int n=0;n<a.length();n++){JSONObject o=a.optJSONObject(n);if(o!=null){long t=date(o);if(t>0&&t<next)next=t;}}if(next!=Long.MAX_VALUE)m.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,Math.max(next,System.currentTimeMillis()+1000),p);}
 static synchronized void reschedule(Context c){schedule(c,get(c));}
 static synchronized void fire(Context c){JSONArray a=get(c),future=new JSONArray();ArrayList<String> due=new ArrayList<>();long now=System.currentTimeMillis();for(int n=0;n<a.length();n++){JSONObject o=a.optJSONObject(n);if(o==null)continue;long t=date(o);if(t<=0)continue;if(t<=now){if(now-t<=24L*3600000L)due.add(o.optString("title"));}else future.put(o);}put(c,future);schedule(c,future);if(due.isEmpty())return;NotificationManager nm=(NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);if(Build.VERSION.SDK_INT>=26)nm.createNotificationChannel(new NotificationChannel(CHANNEL,"Recordatorios de actividades",NotificationManager.IMPORTANCE_DEFAULT));if(Build.VERSION.SDK_INT>=33&&c.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)!=PackageManager.PERMISSION_GRANTED)return;if(Build.VERSION.SDK_INT>=24&&!nm.areNotificationsEnabled())return;Intent open=new Intent(c,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);PendingIntent tap=PendingIntent.getActivity(c,0,open,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);String text=due.size()==1?due.get(0):due.size()+" actividades: "+android.text.TextUtils.join(", ",due);Notification.Builder b=Build.VERSION.SDK_INT>=26?new Notification.Builder(c,CHANNEL):new Notification.Builder(c);b.setSmallIcon(android.R.drawable.ic_popup_reminder).setContentTitle("Recordatorio de Mi Agenda").setContentText(text).setStyle(new Notification.BigTextStyle().bigText(text)).setAutoCancel(true).setContentIntent(tap);nm.notify(18,b.build());}
}
