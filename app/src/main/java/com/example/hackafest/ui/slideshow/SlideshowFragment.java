package com.example.hackafest.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hackafest.R;
import com.example.hackafest.ui.gallery.GalleryFragment;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        Button buttonYes = (Button) root.findViewById(R.id.buttonYes);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YesMethod(v);
            }
        });
        Button buttonNo = (Button) root.findViewById(R.id.buttonNo);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoMethod(v);
            }
        });
        return root;
    }




    public void NoMethod(View v) {
       // GalleryFragment.GalleryData.getInstance().dato = "X";
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_gallery);
    }

    public void YesMethod(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
       navController.navigate(R.id.nav_audio);
    }





}