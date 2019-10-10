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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackafest.MainActivity;
import com.example.hackafest.R;
import com.example.hackafest.SemanticModule;
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


public class AudioFragment extends Fragment {
    private Button mSpeakBtn;
    private TextView messagge, porcentajes;
    private static final int REQ_CODE_SPEECH_INPUT = 100;

    ArrayList<String> results= new ArrayList<>();
    ImageView imageViewFaceresult;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_audio, container, false);
        messagge = root.findViewById(R.id.textView);
        porcentajes = root.findViewById(R.id.porcentaje);
        mSpeakBtn =  (Button) root.findViewById(R.id.button);

        imageViewFaceresult=(ImageView)root.findViewById(R.id.imageViewFaceResult);

        mSpeakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceInput(v);
            }
        });
        return root;
    }



    public boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    messagge.setText((result.get(0)));


                    AssetManager mngr = getActivity().getAssets();
                    SemanticModule s = new SemanticModule();
                    s.createList(getActivity(),mngr);
                    results=s.getEmotionalValue(result.get(0));
                    porcentajes.setText("negativo: "+results.get(0)+"% neutral: "+results.get(1)+"% positivo: "+results.get(2));

                    double dd= Double.parseDouble(results.get(0));
                    if(Double.parseDouble(results.get(0))>Double.parseDouble(results.get(1)) &&
                            Double.parseDouble(results.get(0))>Double.parseDouble(results.get(2))){//&& results.get(0)>results.get(3)
                        //carita negativa
                        imageViewFaceresult.setImageResource(R.drawable.ic_menu_camera);
                    }
                    else if(Double.parseDouble(results.get(1))>Double.parseDouble(results.get(0)) &&
                            Double.parseDouble(results.get(1))>Double.parseDouble(results.get(2))){
                        //carita neutra
                        imageViewFaceresult.setImageResource(R.drawable.ic_menu_send);

                    }
                    else if(Double.parseDouble(results.get(2))>Double.parseDouble(results.get(0)) &&
                            Double.parseDouble(results.get(2))>Double.parseDouble(results.get(1))){
                        //carita positiva
                        imageViewFaceresult.setImageResource(R.drawable.my_button_bg);

                    }




                }
                break;
            }

        }
    }


    public void startVoiceInput(View v) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }

    }



}