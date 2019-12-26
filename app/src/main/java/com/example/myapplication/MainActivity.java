package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static   String DBname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), addPel.class);
                // intent.putExtra(EXTRA_MESSAGE, o.toString());
                // String ctrapezi;
                // trapezi = (TextView)findViewById(R.id.textView);
                // ctrapezi=trapezi.getText().toString();

                // intent.putExtra("mpel2", TrapeziFull);  // αριθμος τραπεζιου
                // intent.putExtra("WhoCall", "trapezia");  // ποια φορμα καλει
                // intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
                // intent.putExtra("mEIDH", EIDH); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
                // intent.putExtra("mKATHG", KATHG); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
                // intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
                MainActivity.this.startActivity(intent);
                //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //   .setAction("Action", null).show();
            }
        });


        final EditText mKOD;
        mKOD = (EditText) findViewById(R.id.KOD);

        mKOD.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    adding();
                    // Perform action on key press
                    Toast.makeText(MainActivity.this, mKOD.getText().toString(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });





        mKOD.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.toString().length()>=4) {
                    //   adding();
                    //  Toast.makeText(getApplicationContext(), "editable "+s.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /*This method is called to notify you that, within s, the count characters beginning at start are about to be replaced by new text with length after. It is an error to attempt to make changes to s from this callback.*/
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }


// prosuetei thn kinhsh sto arxeio
    public void adding(){
        //'προσθετει την παρουσια'

        EditText mKOD, mONO;
        TextView t;
        mKOD = (EditText) findViewById(R.id.KOD);
        //  mONO = (EditText) findViewById(R.id.ONO);
        t=(TextView)findViewById(R.id.textView);
        String cmKOD = mKOD.getText().toString();
        //    String cmONO = mONO.getText().toString();

          //  if(cmKOD = null && !cmKOD.trim().isEmpty()){
          //      mKOD.setText("");
           //     notification();
            //    return;

           // }else{


               // int  U_ID = Integer.parseInt(cmKOD);
                if (!TextUtils.isDigitsOnly(cmKOD)){
                    mKOD.setText("");
                    notification();
                    return;
                }



          //  }


        SQLiteDatabase mydatabase = null;
        mydatabase = openOrCreateDatabase("pelates", MODE_PRIVATE, null);
        Cursor cursor = mydatabase.rawQuery("select KOD,ONO from pel where KOD=" + cmKOD + " ", null);
        int n = 0;
        String mName="";
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                n = cursor.getInt(0);
                // n = Integer.parseInt(cursor.getString(0));
                mName=cursor.getString(1);
                t.setText(mName);
            } while (cursor.moveToNext());
        }






        mydatabase.execSQL("INSERT INTO parousies (CH2,IDBARDIA,CH1) VALUES('"+mName+"'," + cmKOD + ", datetime('now','localtime') ) ;");


        mydatabase.close();

       /* Thread closeActivity = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    TextView t;

                    //  mONO = (EditText) findViewById(R.id.ONO);
                    t=(TextView)findViewById(R.id.textView);
                    t.setText("...");
                    // Do some stuff
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }
            }
        });
*/




        //  t.setText("");

        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // do something after 1s
            }

            @Override
            public void onFinish() {
                // do something end times 5s
                TextView t;

                //  mONO = (EditText) findViewById(R.id.ONO);
                t=(TextView)findViewById(R.id.textView);
                t.setText("..");

                EditText mKOD;
                mKOD = (EditText) findViewById(R.id.KOD);
                mKOD.setText("");

            }

        }.start();


    }


    private void notification() {


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);
        mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

     //   NotificationCompat.Builder builder= new NotificationCompat.Builder(this);
     //   builder.setAutoCancel(true);
     //   builder.setContentTitle("Work Progress");
    //    builder.setContentText("Submit your today's work progress");
     //   builder.setSmallIcon(R.drawable.ic_email_black_24dp);
      //  Intent intent=new Intent(this, WorkStatus.class);
       // PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, intent,
         //       PendingIntent.FLAG_UPDATE_CURRENT);
     //   builder.setContentIntent(pendingIntent);
    //    builder.setDefaults(Notification.DEFAULT_VIBRATE);
     //   builder.setDefaults(Notification.DEFAULT_SOUND);

    //    NotificationManager notificationManager= (NotificationManager)
     //           getSystemService(NOTIFICATION_SERVICE);
    //    notificationManager.notify(1, builder.build());
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void getpel (View view) {
        SQLiteDatabase mydatabase = null;
        mydatabase =

                openOrCreateDatabase("pelates",MODE_PRIVATE,null);

  /*  trapezi =(TextView)

    findViewById(R.id.textView);

    String skTrapezi = trapezi.getText().toString();

        if(skTrapezi.substring(0,1).

    equals("#"))

    {
        String[] separated3 = skTrapezi.split("#");
        skTrapezi = separated3[1];
    }

    String[] cTrapeziFull = TrapeziFull.split(";");
    String idpar = cTrapeziFull[1];



        mydatabase.execSQL("UPDATE TABLES SET KATEILHMENO=0,IDPARAGG=0 WHERE ONO='"+skTrapezi +"'");
        mydatabase.execSQL("UPDATE PARAGGMASTER SET CH2= datetime('now','localtime'),AJIA=0 ,TROPOS=2  WHERE ID="+idpar);
  mydatabase.close();

   */

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.





        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
