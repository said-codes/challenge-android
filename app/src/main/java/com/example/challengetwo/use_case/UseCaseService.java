package com.example.challengetwo.use_case;

import android.database.Cursor;

import com.example.challengetwo.model.Service;

import java.util.ArrayList;

public class UseCaseService {

    public ArrayList<Service> fillServiceList(Cursor cursor){
        ArrayList<Service> list = new ArrayList<>();
        if(cursor.getCount() == 0){
            return list;
        }else{
            while(cursor.moveToNext()){
                Service service = new Service(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getBlob(4)
                );
                list.add(service);
            }
            return list;
        }
    }
}
