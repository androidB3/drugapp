package com.example.drugapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.drugapp.Adapter.DrugAdpter;
import com.example.drugapp.Model.Drug;
import com.example.drugapp.R;

import java.util.ArrayList;

public class DruglistActivity extends AppCompatActivity {
    private ArrayList<Drug> drugArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_druglist);

        drugArrayList = new ArrayList<>();

        Resources res = getResources();
        String[] allDrugs = res.getStringArray(R.array.drugs);
        String[] allActives = res.getStringArray(R.array.active);

        populuateDrugList(allDrugs,allActives);

        DrugAdpter adpter = new DrugAdpter(DruglistActivity.this,
                R.layout.list_item_drug, drugArrayList);
        ListView listView = findViewById(R.id.lvdrugs);
        listView.setAdapter(adpter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(DruglistActivity.this, "Hello" +i, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void populuateDrugList(String[] allDrugs, String[] allActives) {
        drugArrayList.add(new Drug(allDrugs[0], allActives[0], R.drawable.efferalgan_removebg_preview));
        drugArrayList.add(new Drug(allDrugs[1], allActives[1], R.drawable.hutech));
        drugArrayList.add(new Drug(allDrugs[2], allActives[2], R.drawable.efferalgan_removebg_preview));
        drugArrayList.add(new Drug(allDrugs[3], allActives[3], R.drawable.efferalgan_removebg_preview));
        drugArrayList.add(new Drug(allDrugs[4], allActives[4], R.drawable.efferalgan_removebg_preview));
        drugArrayList.add(new Drug(allDrugs[5], allActives[5], R.drawable.efferalgan_removebg_preview));
        drugArrayList.add(new Drug(allDrugs[6], allActives[6], R.drawable.efferalgan_removebg_preview));
        drugArrayList.add(new Drug(allDrugs[7], allActives[7], R.drawable.efferalgan_removebg_preview));
    }
}