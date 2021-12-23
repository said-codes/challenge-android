package com.example.challengetwo.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.challengetwo.R;
import com.example.challengetwo.model.Product;

import java.util.ArrayList;


public class ProductAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> products;
    LayoutInflater layoutInflater;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
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


        Product product = products.get(i);
        byte [] image = product.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);

        fieldId.setText("ID:"+ product.getId());
        textField1.setText(product.getName());
        textField2.setText(product.getDescription());
        textField3.setText(product.getPrice());
        imageView.setImageBitmap(bitmap);

        return view;
    }

}
