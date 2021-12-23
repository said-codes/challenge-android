package com.example.challengetwo.ui.offices;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.challengetwo.Adapter.OfficeAdapter;
import com.example.challengetwo.FormActivity;
import com.example.challengetwo.FormMapsActivity;
import com.example.challengetwo.R;
import com.example.challengetwo.data.DBHelper;
import com.example.challengetwo.databinding.FragmentOfficesBinding;
import com.example.challengetwo.model.Office;
import com.example.challengetwo.use_case.UseCaseOffice;

import java.util.ArrayList;

public class OfficesFragment extends Fragment {

    private com.example.challengetwo.ui.offices.OfficesViewModel officesViewModel;
    private GridView gridView;
    private DBHelper dbHelper;
    private ArrayList<Office> offices;
    private String TABLE_NAME = "Offices";
    private UseCaseOffice useCaseOffice;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        officesViewModel =
                new ViewModelProvider(this).get(com.example.challengetwo.ui.offices.OfficesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_offices,container,false);

        try{
            useCaseOffice = new UseCaseOffice();
            dbHelper = new DBHelper(getContext());
            Cursor cursor = dbHelper.getData(TABLE_NAME);
            offices = useCaseOffice.fillOfficeList(cursor);
            gridView = (GridView) root.findViewById(R.id.gridOffices);
            OfficeAdapter officeAdapter = new OfficeAdapter(root.getContext(), offices);
            gridView.setAdapter(officeAdapter);
        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            Log.w("Error ->>>", e.toString());
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                try {
                    Intent intent = new Intent(getContext(), FormMapsActivity.class);
                    getActivity().startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getContext(), "Hola Sucursales", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}