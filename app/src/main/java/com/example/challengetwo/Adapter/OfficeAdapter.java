package com.example.challengetwo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.challengetwo.R;
import com.example.challengetwo.ShowMapsActivity;
import com.example.challengetwo.model.Office;

import java.util.ArrayList;

public class OfficeAdapter extends BaseAdapter {
    Context context;
    ArrayList<Office> offices;
    LayoutInflater layoutInflater;

    public OfficeAdapter(Context context, ArrayList<Office> offices) {
        this.context = context;
        this.offices = offices;
    }

    @Override
    public int getCount() {
        return offices.size();
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
        if(layoutInflater == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null){
            view = layoutInflater.inflate(R.layout.grid_item,null);
        }
        ImageView imageView = view.findViewById(R.id.imageItem);
        TextView textField1 = view.findViewById(R.id.tvField1);
        TextView textField2 = view.findViewById(R.id.tvField2);
        TextView textField3 = view.findViewById(R.id.tvField3);
        TextView fieldId = view.findViewById(R.id.tvId);

        Office office = offices.get(i);
        byte [] image = office.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);

        fieldId.setText("ID:"+ office.getId());
        textField1.setText(office.getName());
        textField2.setText(office.getDescription());
        textField3.setText(office.getLocation());
        imageView.setImageBitmap(bitmap);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowMapsActivity.class);
                intent.putExtra("name", office.getName());
                intent.putExtra("location", office.getLocation());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
