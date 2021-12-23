package com.example.challengetwo.use_case;


import android.database.Cursor;

import com.example.challengetwo.model.Product;

import java.util.ArrayList;

public class UseCaseProduct {

    public ArrayList<Product> fillProductList(Cursor cursor){
        ArrayList<Product> list = new ArrayList<>();
        if(cursor.getCount() == 0){
            return list;
        }else{
            while(cursor.moveToNext()){
                Product product = new Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getBlob(4)
                );
                list.add(product);
            }
            return list;
        }
    }

}
