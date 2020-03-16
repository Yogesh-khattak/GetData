package com.yogesh.getdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yogesh.getdata.pojo.User;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG = 123;
    EditText et,et1,et2,et3,et4,et5,et6;
    RadioGroup radioGroup;
    Button btn,imgBtn;
    ProgressBar progressBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Spinner spinner2;
    String[] country = {"India","USA","China","Saudi Arab","iraq","Singapore","Morococco"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         et =  findViewById(R.id.editText_name);
        et1 =  findViewById(R.id.editText_last);
       et2 =  findViewById(R.id.editText_father);
       et3 =  findViewById(R.id.editText_mother);
       et4 =  findViewById(R.id.editText_email);
       et6 =  findViewById(R.id.editText_address);
       et5 =  findViewById(R.id.editText_number);
        imgBtn =  findViewById(R.id.imgbtn);
        radioGroup =  findViewById(R.id.radio_grp);
       progressBar =  findViewById(R.id.progressbar);
        spinner2 =  findViewById(R.id. spinner2);
        ArrayAdapter v =  new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,country);
        v.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(v);


        btn =  findViewById(R.id.button);
      btn.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {
              Intent i  = new Intent(MainActivity.this,Main2Activity.class);
              startActivity(i);
              return false;
          }
      });
      btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String name,last,farther,mother,email,address,number,gender,country;

                name = et.getText().toString();
              last = et1.getText().toString();
              farther = et2.getText().toString();
             mother = et3.getText().toString();
              email = et4.getText().toString();
             number = et5.getText().toString();
             address = et6.getText().toString();
              country = spinner2.getSelectedItem().toString();

              RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
             gender = radioButton.getText().toString();




             StringBuffer sb =  new StringBuffer();
             sb.append("name: "+name);
              sb.append("last: "+last);
              sb.append("father: "+farther);
              sb.append("mother: "+mother);
              sb.append("email: "+email);
              sb.append("number: "+number);
              sb.append("address: "+address);
              sb.append("gender: "+gender);
              sb.append("country:"+country);

              User user =  new User();

              user.setName(name);
              user.setLast(last);
              user.setFather(farther);
              user.setMother(mother);
              user.setEmail(email);
              user.setNumber(number);
              user.setAddress(address);
              user.setGender(gender);
              user.setCountry(country);

              addData(user);
              Intent i  = new Intent(MainActivity.this,Main2Activity.class);
              startActivity(i);
          }
      });


        imgBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
              photoPickerIntent.setType("image/*");
              startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
          }
      });

    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
               // imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(MainActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }


   public void addData(User user){
        btn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        btn.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Log.w(TAG, "Error adding document", e);
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
