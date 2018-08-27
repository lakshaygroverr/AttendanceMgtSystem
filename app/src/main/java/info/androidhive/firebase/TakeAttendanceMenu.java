package info.androidhive.firebase;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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


public class TakeAttendanceMenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

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

    /*

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, spinnerArray);

adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    Spinner sItems = (Spinner) findViewById(R.id.spinner1);
sItems.setAdapter(adapter);*/

    /*
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
        getAllSemesters();

        ArrayAdapter<String> semAdapter= new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, semSpinnerList);
        semAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semSpinner.setAdapter(semAdapter);
        semSpinner.setOnItemSelectedListener(this);


        //selectedSem = semSpinner.getSelectedItem().toString();

        /*getSemesterClasses();
        ArrayAdapter<String> classAdapter= new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, ClassSpinnerList);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);*/

       /* semSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d(TAG,"please select an option");
            }
        });*/


        /*semSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?>parent,View selectedItemView, int position, long id){
                selectedSem = (String)parent.getItemAtPosition(position);
                getSemesterClasses();
                Log.d(TAG,"hey");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
                Toast.makeText(TakeAttendanceMenu.this, "Please select the Class", Toast.LENGTH_LONG).show();
            }
        });*/

        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassSpinnerList);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String randValue = parent.getItemAtPosition(i).toString();
        Toast.makeText(this,randValue,Toast.LENGTH_LONG).show();
    }

    @Override   
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d(TAG,"Yo baby");
    }
}
