package com.example.challengetwo.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.challengetwo.R;
import com.example.challengetwo.model.Service;

import java.util.ArrayList;

public class ServiceAdapter extends BaseAdapter {
    Context context;
    ArrayList<Service> services;
    LayoutInflater layoutInflater;

    public ServiceAdapter(Context context, ArrayList<Service> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public int getCount() {
        return services.size();
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


        Service service = services.get(i);
        byte [] image = service.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);

        fieldId.setText("ID:"+ service.getId());
        textField1.setText(service.getName());
        textField2.setText(service.getDescription());
        textField3.setText(service.getPrice());
        imageView.setImageBitmap(bitmap);

        return view;
    }
}
