package com.yogesh.getdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yogesh.getdata.adapter.MyAdpater;
import com.yogesh.getdata.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdpater myAdpater;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  static  String TAG = "tag";
    TextView textView;
    List<User> userList = new ArrayList<>();
    Button maleBtn,femaleBtn;
    Spinner spinner;
    String[] country = {"India","USA","China","Saudi Arab","iraq","Singapore","Morococco"};
    ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.recyle);
        maleBtn =  findViewById(R.id.maleBtn);
        femaleBtn =  findViewById(R.id.femaleBtn);
        textView =  findViewById(R.id.tname);
        spinner  =  findViewById(R.id.spinner);
        progressbar  =  findViewById(R.id.progressbar);
        myAdpater =  new MyAdpater(this,userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdpater);
        ArrayAdapter v  =  new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,country);
        v.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(v);

        maleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("MALE",spinner.getSelectedItem().toString());
            }
        });


        femaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseAuth.getInstance().signOut();
                getData("FEMALE",spinner.getSelectedItem().toString());

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void getData(String gender, String country){
        progressbar.setVisibility(View.VISIBLE);
        Query query = db.collection("users")
                .whereEqualTo("gender",gender)
                .whereEqualTo("country",country)
                .orderBy("createDate", Query.Direction.DESCENDING);


        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            userList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                userList.add(user);
                            }
                            myAdpater.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        progressbar.setVisibility(View.GONE);
                    }
                });
    }

}
