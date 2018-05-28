package me.prokawsar.cloudfiresoreapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowData extends AppCompatActivity {
    private ListView listview;
    private List<Products> productList;
    private ProgressBar progressBar;
    private FirebaseFirestore mDatabase;
    private ArrayAdapter<Products> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        mDatabase = FirebaseFirestore.getInstance();

        listview = findViewById(R.id.list_id);
        productList = new ArrayList<>();
        progressBar = findViewById(R.id.progress_id);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,productList);
        listview.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.collection("Products").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                       if (!documentSnapshots.isEmpty()){
                           List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                           for (DocumentSnapshot data : list){
                               Products products = data.toObject(Products.class);
                               productList.add(products);
                           }
                           adapter.notifyDataSetChanged();
                       }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}
