package com.example.hackafest.ui;

import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hackafest.MainActivity;
import com.example.hackafest.R;
import com.example.hackafest.SemanticModule;
import com.example.hackafest.ui.gallery.GalleryFragment;
import com.example.hackafest.ui.gallery.GalleryViewModel;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ArrayList;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;


public class WriteFragment extends Fragment {
    private GalleryViewModel galleryViewModel;
    private EditText editExperiencia;
    private EditText editReferencia;
    private EditText editDomicilio;
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
        root = inflater.inflate(R.layout.fragment_write, container, false);
        radioGroup = (RadioGroup) root.findViewById(R.id.radio);
        editExperiencia = root.findViewById(R.id.experiencia);
        editReferencia = root.findViewById(R.id.referencias);
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
        ArrayList<String> results= new ArrayList<String>();


        stringAnalyzed = radioButton.getText()+ " "+ editExperiencia.getText()+ " ";

        GalleryFragment.GalleryData.getInstance().nombre = editReferencia.getText() + "";
        GalleryFragment.GalleryData.getInstance().telefono = editTelefono.getText() + "";
        GalleryFragment.GalleryData.getInstance().direccion = editDomicilio.getText()+ "";
        GalleryFragment.GalleryData.getInstance().parentesco = radioButtonFamiliar.getText() + "";



        AssetManager mngr = getActivity().getAssets();
        SemanticModule sm = new SemanticModule();
        sm.createList(getActivity(),mngr);
        stringAnalyzed=stringAnalyzed.replaceAll(",","");
        stringAnalyzed=stringAnalyzed.replaceAll(";","");
        results = sm.getEmotionalValue(stringAnalyzed.replaceAll(",","")+" ");
        GalleryFragment.GalleryData.getInstance().negativo =  results.get(0) + "";
        GalleryFragment.GalleryData.getInstance().neutro =  results.get(1) + "";
        GalleryFragment.GalleryData.getInstance().positivo =  results.get(2) + "";
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_slideshow);

    }
}