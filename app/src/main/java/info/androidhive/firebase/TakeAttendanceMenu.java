package info.androidhive.firebase;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
/*

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
*/


public class TakeAttendanceMenu extends AppCompatActivity implements OnItemSelectedListener{

    /*final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Courses");*/

    String baseDb = "Courses";
    String selectedSem;
    String semClassDb;

    Spinner semSpinner,classSpinner,subjectSpinner;
    private static final String TAG = "TakeAttendanceMenu";

   FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<String> semSpinnerList =  new ArrayList<String>();
    List<String> ClassSpinnerList = new ArrayList<String>();







    public void getAllSemesters()
    {
        db.collection(baseDb)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document: task.getResult()){
                                Log.d(TAG,document.getId() + "=>" + document.getData());
                                semSpinnerList.add(document.getId());
                            }
                        }
                        Toast.makeText(getApplicationContext(), "Please chal ja bc", Toast.LENGTH_SHORT).show();
                    }
});
    }

    public void getSemesterClasses()
    {
        semClassDb = baseDb + "/" + selectedSem;
        db.collection(semClassDb).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot: task.getResult()){
                        ClassSpinnerList.add(documentSnapshot.getId());
                    }
                }
            }
        });
    }

/*    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, spinnerArray);

adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    Spinner sItems = (Spinner) findViewById(R.id.spinner1);
sItems.setAdapter(adapter);

    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
spinner.setAdapter(adapter);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        semSpinner = (Spinner)findViewById(R.id.semester_spinner);
        classSpinner = (Spinner)findViewById(R.id.class_spinner);
        subjectSpinner = (Spinner)findViewById(R.id.subject_spinner);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        );
        getAllSemesters(); // calling this method to instantiate the first spinner(semester spinner) with the values of firestore

        ArrayAdapter<String> semAdapter= new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, semSpinnerList);
        semAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semSpinner.setAdapter(semAdapter);
        semSpinner.setOnItemSelectedListener(this);

        /*Adapter for the Semester -> Class*/
        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassSpinnerList);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);
        classSpinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long id) {
        switch (view.getId()) {

            case R.id.semester_spinner:
                Toast.makeText(this, "Item Selected "+position, Toast.LENGTH_SHORT).show();
                break;

            case R.id.class_spinner:
                Toast.makeText(this, "Item Selected "+position, Toast.LENGTH_SHORT).show();
                break;

            case R.id.subject_spinner:
                Toast.makeText(this, "Item Selected "+position, Toast.LENGTH_SHORT).show();
                break;

            case R.id.time_spinner:
                Toast.makeText(this, "Item Selected "+position, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(), "Nothing has been selected", Toast.LENGTH_SHORT).show();
    }
}
