package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class kiniseis extends AppCompatActivity {

    public String message="";
    public CalendarView calendarView;
    static Handler handler;
    public TextView t5;




    public String apo;
    CalendarView calendar;
    TextView dateView;
    int flag1 = 0;
    String d1,d2;
    public List<String> values=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiniseis);
        // show_kin();
        GridView moviesList;
        moviesList = findViewById(R.id.grid);
        moviesList.setVisibility(View.INVISIBLE);

        calendar = findViewById(R.id.calendarView7);
        dateView = findViewById(R.id.apoHmer);
        t5=findViewById(R.id.textView5);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String word,cMonth,cDay;

                int mm=100+(month+1);
                word=Integer.toString(mm) ;
                cMonth=word.substring(word.length() - 2);

                mm=100+(dayOfMonth);
                word=Integer.toString(mm) ;
                cDay=word.substring(word.length() - 2);

                // String Date = dayOfMonth + "-" + (month + 1) + "-" + year;
                String Date = cDay + "-" + cMonth + "-" + year;

                if (flag1==0) {
                    // dateView.setText(Date);
                    d1= year+"-"+ cMonth +"-"+cDay  ;
                    dateView.setText(d1);
                    flag1 = 1;
                } else{
                    // t5.setText(Date);

                    // d2= year+"/"+ (month + 1) +dayOfMonth  ;
                    d2= year+"-"+ cMonth +"-"+cDay  ;
                    t5.setText(d2);
                    calendar.setVisibility(View.INVISIBLE );

                    GridView moviesList;
                    moviesList = findViewById(R.id.grid);
                    moviesList.setVisibility(View.VISIBLE);




                    EditText sql;
                    sql=findViewById(R.id.editText);
                    sql.setVisibility(View.INVISIBLE);
                    sql.setText("select IDBARDIA, CH1,CH2  from  parousies where CH1>='"+d1+"' AND CH1<='"+d2+"' AND CH2+CH1 NOT NULL order by CH1 desc");





                }
            }
        });

    }


    public void showkin(View view){
        values=new ArrayList<>();  // μηδενιζω την λιστα
        try{

            SQLiteDatabase db = null;
            db = openOrCreateDatabase("pelates", MODE_PRIVATE, null);

            EditText ckod=findViewById(R.id.editID);
            String kod=ckod.getText().toString();

            Cursor cursor;


            if (kod.length()>0){ // where IDBARDIA="+kod+"
                cursor = db.rawQuery("select IDBARDIA,CH1,CH2  from  parousies where IDBARDIA="+kod+" and  CH1>='" + d1 + "'  and CH1<='" + d2 + "' order by CH1 desc", null);  //+ " order by CH1 desc"
            }else {
                cursor = db.rawQuery("select IDBARDIA,CH1,CH2  from  parousies where CH1>='" + d1 + "'  and CH1<='" + d2 + "' order by CH1 desc", null);  //+ " order by CH1 desc"
            }


            if (cursor.moveToFirst()) {
                do {
                    values.add( cursor.getString(2));
                    values.add( cursor.getString(1));

                } while (cursor.moveToNext());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

            db.close();
        } catch (Exception e) {
        }

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values)

                        // arxh  αυτο το κομματι βαζει πλαισια στο gridview
                {
                    public View getView(int position, View convertView, ViewGroup parent) {

                        // Return the GridView current item as a View
                        View view = super.getView(position,convertView,parent);

                        // Convert the view as a TextView widget
                        TextView tv = (TextView) view;

                        //tv.setTextColor(Color.DKGRAY);

                        // Set the layout parameters for TextView widget
                        RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT
                        );
                        tv.setLayoutParams(lp);

                        // Get the TextView LayoutParams
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();

                        // Set the width of TextView widget (item of GridView)
                /*
                    IMPORTANT
                        Adjust the TextView widget width depending
                        on GridView width and number of columns.

                        GridView width / Number of columns = TextView width.

                        Also calculate the GridView padding, margins, vertical spacing
                        and horizontal spacing.
                 */


                        Resources r = kiniseis.this.getResources();
                        int  px = (int) (TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, 168, r.getDisplayMetrics()));



                        params.width = px;  // getPixelsFromDPs(EpiloghEid.this,168);

                        // Set the TextView layout parameters
                        tv.setLayoutParams(params);

                        // Display TextView text in center position
                        tv.setGravity(Gravity.CENTER);

                        // Set the TextView text font family and text size
                        tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);

                        // Set the TextView text (GridView item text)
                        tv.setText(values.get(position));

                        // Set the TextView background color
                        tv.setBackgroundColor(Color.parseColor("#CDDC39"));

                        // Return the TextView widget as GridView item
                        return tv;
                    }
                };
        GridView moviesList;
        moviesList=(GridView)findViewById(R.id.grid);
        moviesList.setAdapter(arrayAdapter);







    }


    public void send_email(View view) {


        // Intent intent = new Intent(view.getContext(), kiniseis.class);
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
        //  kiniseis.this.startActivity(intent);


        File folder = new File(Environment.getExternalStorageDirectory()
                + "/lagakis2");

        boolean var = false;
        if (!folder.exists()) {
            var = folder.mkdir();
        } else {
            var = true;
        }
        final String filename = folder.toString() + "/" + "Test.csv";

        // show waiting screen
        CharSequence contentTitle = getString(R.string.app_name);
        final ProgressDialog progDailog = ProgressDialog.show(
                kiniseis.this, contentTitle, "even geduld aub...",
                true);//please wait
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
            }
        };

        new Thread() {
            public void run() {
                try {

                    FileWriter fw = new FileWriter(filename);
                    SQLiteDatabase db = null;
                    db = openOrCreateDatabase("pelates", MODE_PRIVATE, null);

                    TextView ckod=findViewById(R.id.ID);
                    String kod=ckod.getText().toString();

                    Cursor cursor;

                    if (1==1){ // where IDBARDIA="+kod+"
                         cursor = db.rawQuery("select IDBARDIA,CH1,CH2  from  parousies where IDBARDIA="+kod+" and  CH1>='" + d1 + "'  and CH1<='" + d2 + "' order by CH1 desc", null);  //+ " order by CH1 desc"
                    }else {
                         cursor = db.rawQuery("select IDBARDIA,CH1,CH2  from  parousies where CH1>='" + d1 + "'  and CH1<='" + d2 + "' order by CH1 desc", null);  //+ " order by CH1 desc"
                    }
                    // Cursor cursor = db.selectAll();
                    fw.append("ONOMA");
                    fw.append(',');
                    fw.append("CODE");
                    fw.append(',');
                    fw.append("TIME/DATE");
                    fw.append(',');
                    fw.append('\n');

                    if (cursor.moveToFirst()) {
                        do {
                            //NAME
                            fw.append(cursor.getString(2));
                            fw.append(',');
                            //ID
                            // fw.append(Float.toString(cursor.getFloat(2)));
                            fw.append(Float.toString(cursor.getInt(0)));
                            fw.append(',');
                            // DATE
                            fw.append(cursor.getString(1));
                            fw.append(',');
                             /*  fw.append(cursor.getString(10));
                                  fw.append(',');
                             */
                            fw.append('\n');

                            message = message + cursor.getString(2) + "," + cursor.getString(0) + "," + cursor.getString(1) + "\n";

                        } while (cursor.moveToNext());
                    }
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }

                    // fw.flush();
                    fw.close();
                    db.close();
                } catch (Exception e) {
                }
                handler.sendEmptyMessage(0);
                progDailog.dismiss();
            }
        }.start();


        values = new ArrayList<>();  // μηδενιζω την λιστα
        values.add("Ονομα");
        values.add("Ημ/νία");


        EditText sql;
        sql = findViewById(R.id.editText);
        String SQL = sql.getText().toString();
        sql.setVisibility(View.INVISIBLE);


        message = "";
        try {


            SQLiteDatabase db = null;
            db = openOrCreateDatabase("pelates", MODE_PRIVATE, null);


            Cursor cursor = db.rawQuery(SQL, null);  //+ " order by CH1 desc"

            if (cursor.moveToFirst()) {
                do {
                    message = message + cursor.getString(2) + "," + cursor.getString(0) + "," + cursor.getString(1) + "\n";
                    values.add(cursor.getString(2));
                    values.add(cursor.getString(1));
                } while (cursor.moveToNext());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();

        } catch (Exception e) {
        }

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(kiniseis.this, android.R.layout.simple_list_item_1, values);

        GridView moviesList;
        moviesList = findViewById(R.id.grid);
        moviesList.setAdapter(arrayAdapter);


        Boolean fl=getYesNoWithExecutionStop("email", "Να σταλεί;", kiniseis.this);
        if (fl) {
            send_mail(  message);
        }
    }

    private boolean mResult;
    public boolean getYesNoWithExecutionStop(String title, String message, Context context) {
        // make a handler that throws a runtime exception when a message is received
        handler = new Handler() {
            @Override
            public void handleMessage(Message mesg) {
                throw new RuntimeException();
            }
        };

        // make a text input dialog and show it
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mResult = true;
                handler.sendMessage(handler.obtainMessage());
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mResult = false;
                handler.sendMessage(handler.obtainMessage());
            }
        });
        alert.show();

        // loop till a runtime exception is triggered.
        try { Looper.loop(); }
        catch(RuntimeException e2) {}

        return mResult;
    }

    public void send_mail( String message){


        //  email----------------------------

        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "glagakis@gmail.com", null));
        //  intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test Subject");

        //  context.startActivity(Intent.createChooser(intent, "Send mail..."));




        String to = "glagakis@gmail.com";  // textTo.getText().toString();
        String subject = "κινησεις" ; //textSubject.getText().toString();
        //String message = "zzzzvvvvvvv"; // textMessage.getText().toString();

        //   Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("plain/text");
        File data = null;
        try {
            Date dateVal = new Date();
            //  String filename2 = dateVal.toString();
            data =File.createTempFile("Report", ".csv");
            FileWriter out = (FileWriter) GenerateCsv.generateCsvFile(
                    data, "Name,Data1");



            File file = new File("/lagakis2/Test.csv");
            i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file)); // data));
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
            i.putExtra(Intent.EXTRA_SUBJECT,"κινησεις");
            i.putExtra(Intent.EXTRA_TEXT, message);
            i.setData(Uri.parse("mailto:"));

            // i.setType( "message/rfc822");
            startActivity(Intent.createChooser(i, "e-mail"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
