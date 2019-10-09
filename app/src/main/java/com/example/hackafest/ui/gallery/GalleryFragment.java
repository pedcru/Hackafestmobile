package com.example.hackafest.ui.gallery;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackafest.R;
import com.example.hackafest.SemanticModule;


public class GalleryFragment extends Fragment {

    public static class GalleryData{

        public String dato;


        public static GalleryData _instance;

        public static GalleryData getInstance(){
            if(_instance == null){
                _instance = new GalleryData();
            }

            return _instance;
        }
    }

    private GalleryViewModel galleryViewModel;
    private EditText texto_solicitante;
    String texto1 = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        texto_solicitante = root.findViewById(R.id.nuevo_solicitante);

        Button btn = (Button) root.findViewById(R.id.checkButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newApplicant(v);
            }
        });
        return root;
    }


    public void newApplicant(View v) {

        AssetManager mngr = getActivity().getAssets();
        SemanticModule sm = new SemanticModule();
        sm.createList(getActivity(),mngr);
        texto1 = texto_solicitante.getText().toString();
        Log.d("tag","Application started "+ texto1);
        texto1=texto1.replaceAll(",","");
        texto1=texto1.replaceAll(";","");

        sm.getEmotionalValue(texto1.replaceAll(",","")+" ");
        //
    }
}