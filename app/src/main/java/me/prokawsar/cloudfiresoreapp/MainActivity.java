package me.prokawsar.cloudfiresoreapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText nameText,brandText,descText,prizeText,qualityText;
    private Button saveButton,showButton;
    private FirebaseFirestore mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = findViewById(R.id.name_id);
        brandText = findViewById(R.id.brand_id);
        descText = findViewById(R.id.desc_id);
        prizeText = findViewById(R.id.prize_id);
        qualityText = findViewById(R.id.quality_id);
        saveButton = findViewById(R.id.save_id);
        showButton = findViewById(R.id.show_button);

        mDatabase = FirebaseFirestore.getInstance();

        saveButton.setOnClickListener(this);
        showButton.setOnClickListener(this);



    }
    private boolean validateInputs(String name, String brand, String desc, String price, String qty) {
        if (name.isEmpty()) {
            nameText.setError("Name required");
            nameText.requestFocus();
            return true;
        }

        if (brand.isEmpty()) {
            brandText.setError("Brand required");
            brandText.requestFocus();
            return true;
        }

        if (desc.isEmpty()) {
            descText.setError("Description required");
            descText.requestFocus();
            return true;
        }

        if (price.isEmpty()) {
            prizeText.setError("Price required");
            prizeText.requestFocus();
            return true;
        }

        if (qty.isEmpty()) {
            qualityText.setError("Quantity required");
            qualityText.requestFocus();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        String name = nameText.getText().toString().trim();
       String brand = brandText.getText().toString().trim();
       String desc = descText.getText().toString().trim();
       String price = prizeText.getText().toString().trim();
       String qty = qualityText.getText().toString().trim();
       switch(v.getId()){
           case R.id.save_id:
                if (!validateInputs(name,brand,desc,price,qty)){

           CollectionReference dbProducts = mDatabase.collection("Products");
           Products products = new Products(
                   name,
                   brand,
                   desc,
                   Double.parseDouble(price),
                   Integer.parseInt(qty)
           );
           dbProducts.add(products)
                   .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                       @Override
                       public void onSuccess(DocumentReference documentReference) {

                           Toast.makeText(MainActivity.this, "New Products are added.", Toast.LENGTH_SHORT).show();
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {

                           Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });
       }
               break;
                case R.id.show_button:
                     startActivity(new android.content.Intent(MainActivity.this,ShowData.class));

                     break;
}



   }
}

