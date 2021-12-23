package com.example.challengetwo.ui.services;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

import com.example.challengetwo.Adapter.ServiceAdapter;
import com.example.challengetwo.FormActivity;
import com.example.challengetwo.R;
import com.example.challengetwo.data.DBHelper;
import com.example.challengetwo.databinding.FragmentServicesBinding;
import com.example.challengetwo.model.Service;
import com.example.challengetwo.use_case.UseCaseService;

import java.util.ArrayList;

public class ServicesFragment extends Fragment {

    private ServicesViewModel servicesViewModel;
    private GridView gridView;
    private DBHelper dbHelper;
    private ArrayList<Service> services;
    private String TABLE_NAME = "Services";
    private UseCaseService useCaseService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        servicesViewModel =
                new ViewModelProvider(this).get(ServicesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_services,container,false);

        try {
            useCaseService = new UseCaseService();
            dbHelper = new DBHelper(getContext());
            Cursor cursor = dbHelper.getData(TABLE_NAME);
            services = useCaseService.fillServiceList(cursor);
            gridView = (GridView) root.findViewById(R.id.gridServices);
            ServiceAdapter  serviceAdapter = new ServiceAdapter(root.getContext(),services);
            gridView.setAdapter(serviceAdapter);
        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("name","Services");
                getActivity().startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}