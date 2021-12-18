package com.example.challengetwo.ui.offices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.challengetwo.databinding.FragmentOfficesBinding;

public class OfficesFragment extends Fragment {

    private com.example.challengetwo.ui.offices.OfficesViewModel officesViewModel;
    private FragmentOfficesBinding binding;
    private ImageView img1, img2,img3,img4,img5,img6,img7;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        officesViewModel =
                new ViewModelProvider(this).get(com.example.challengetwo.ui.offices.OfficesViewModel.class);

        binding = FragmentOfficesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        img1 = (ImageView) binding.imageView1;
        img2= (ImageView) binding.imageView2;

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Img1 Functionality  under construction", Toast.LENGTH_SHORT).show();
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Img2 Functionality under construction", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}