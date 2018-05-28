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
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateActivity extends AppCompatActivity {
    private EditText unameText,ubrandText,udescText,upriceText,uqtyText;
    private Button button;
    private FirebaseFirestore mDatabase;
    private Products products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mDatabase = FirebaseFirestore.getInstance();
        products = (Products) getIntent().getSerializableExtra("products");

        unameText = findViewById(R.id.uname_id);
        ubrandText = findViewById(R.id.ubrand_id);
        udescText = findViewById(R.id.udesc_id);
        upriceText = findViewById(R.id.uprize_id);
        uqtyText = findViewById(R.id.uquality_id);
        button = findViewById(R.id.update_id);

        unameText.setText(products.getName());
        ubrandText.setText(products.getBrand());
        udescText.setText(products.getDesc());
        upriceText.setText(String.valueOf(products.getPrice()));
        uqtyText.setText(String.valueOf(products.getQty()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               update();
            }
        });
    }

    private void update() {
        String name = unameText.getText().toString().trim();
        String brand = ubrandText.getText().toString().trim();
        String desc = upriceText.getText().toString().trim();
        String price = upriceText.getText().toString().trim();
        String qty = uqtyText.getText().toString().trim();

        if (!validateInputs(name,brand,desc,price,qty)){

            Products p = new Products(
              name,
              brand,
              desc,
              Double.parseDouble(price),
              Integer.parseInt(qty)
            );
            mDatabase.collection("Products").document(products.getId()).set(p)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(UpdateActivity.this, "Product Updated", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(UpdateActivity.this, "Not updated", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private boolean validateInputs(String name, String brand, String desc, String price, String qty) {
        if (name.isEmpty()) {
            unameText.setError("Name required");
            unameText.requestFocus();
            return true;
        }

        if (brand.isEmpty()) {
            ubrandText.setError("Brand required");
            ubrandText.requestFocus();
            return true;
        }

        if (desc.isEmpty()) {
            udescText.setError("Description required");
            udescText.requestFocus();
            return true;
        }

        if (price.isEmpty()) {
            upriceText.setError("Price required");
            upriceText.requestFocus();
            return true;
        }

        if (qty.isEmpty()) {
            uqtyText.setError("Quantity required");
            uqtyText.requestFocus();
            return true;
        }
        return false;
    }

}
