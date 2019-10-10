package com.example.hackafest.ui.gallery;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hackafest.R;
import com.example.hackafest.SemanticModule;

import java.util.ArrayList;


public class GalleryFragment extends Fragment {

    public static class GalleryData{
        public static GalleryData _instance;
        public String nombre;
        public String telefono;
        public String direccion;
        public String parentesco;
        public String neutro;
        public String referencia;
        public String positivo;
        public String negativo;

        public static GalleryData getInstance(){
            if(_instance == null){
                _instance = new GalleryData();
            }

            return _instance;
        }
    }

    private GalleryViewModel galleryViewModel;
    private EditText editExperiencia;
    private EditText editReferencia;
    private EditText editDomicilio;
    private EditText editNameProductor;
    private EditText editTelefono;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioGroup radioGroupFamiliar;
    private RadioButton radioButtonFamiliar;
    public View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_gallery, container, false);
        radioGroup = (RadioGroup) root.findViewById(R.id.radio);
        editExperiencia = root.findViewById(R.id.experiencia);
        editReferencia = root.findViewById(R.id.referencias);
        editNameProductor = root.findViewById(R.id.nameproductor);
        editDomicilio = root.findViewById(R.id.domicilio);
        editTelefono = root.findViewById(R.id.telefono);
        radioGroupFamiliar = (RadioGroup) root.findViewById(R.id.familiar);




        Button btn = (Button) root.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newApplicant(v);
            }
        });
        return root;
    }


    public void newApplicant(View v) {
        //Final string
        String stringAnalyzed ="";
        //primer radio
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) root.findViewById(selectedId);
        int selectedIdFam = radioGroupFamiliar.getCheckedRadioButtonId();
        radioButtonFamiliar = (RadioButton) root.findViewById(selectedIdFam);
        //Log.d("tag","Application started "+  radioButton.getText());
        //Log.d("tag","Application started "+  editExperiencia.getText());
        ArrayList<String> results= new ArrayList<String>();


        stringAnalyzed = radioButton.getText()+ " "+ editExperiencia.getText()+ " ";

        GalleryData.getInstance().nombre = editNameProductor.getText() + "";
        GalleryData.getInstance().referencia = editReferencia.getText() + "";

        GalleryData.getInstance().telefono = editTelefono.getText() + "";
        GalleryData.getInstance().direccion = editDomicilio.getText()+ "";
        GalleryData.getInstance().parentesco = radioButtonFamiliar.getText() + "";



       AssetManager mngr = getActivity().getAssets();
        SemanticModule sm = new SemanticModule();
        sm.createList(getActivity(),mngr);
        stringAnalyzed=stringAnalyzed.replaceAll(",","");
        stringAnalyzed=stringAnalyzed.replaceAll(";","");
        results = sm.getEmotionalValue(stringAnalyzed.replaceAll(",","")+" ");
        GalleryData.getInstance().negativo =  results.get(0) + "";
        GalleryData.getInstance().neutro =  results.get(1) + "";
        GalleryData.getInstance().positivo =  results.get(2) + "";
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_home);

    }
}