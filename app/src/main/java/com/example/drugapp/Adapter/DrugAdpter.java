package com.example.drugapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drugapp.Model.Drug;
import com.example.drugapp.R;

import java.util.List;

public class DrugAdpter extends BaseAdapter {

    private  Context context;
    private  int layout;
    private List<Drug> drugList;

    public DrugAdpter(Context context, int layout, List<Drug> drugList) {
        this.context = context;
        this.layout = layout;
        this.drugList = drugList;
    }

    @Override
    public int getCount() {
        return drugList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        TextView txtName = (TextView) view.findViewById(R.id.tvNamedrugs);
        TextView txtActor = (TextView) view.findViewById(R.id.tvActiveDrug);
        ImageView imgHinh = (ImageView) view.findViewById(R.id.ivdrugs);

        Drug drug = drugList.get(i);

        txtName.setText(drug.getDrugTitle());
        txtActor.setText(drug.getDrugActive());
        imgHinh.setImageResource(drug.getDrugImageId());
        return view;
    }
}
