package com.example.hackafest.ui;

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

import static android.app.Activity.RESULT_OK;


public class AudioFragment extends Fragment {
    private Button mSpeakBtn;
    private TextView messagge, porcentajes;
    private static final int REQ_CODE_SPEECH_INPUT = 100;

    ArrayList<String> results= new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_audio, container, false);
        messagge = root.findViewById(R.id.textView);
        porcentajes = root.findViewById(R.id.porcentaje);
        mSpeakBtn =  (Button) root.findViewById(R.id.button);

        Button btn = (Button) root.findViewById(R.id.checkButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceInput(v);
            }
        });
        return root;
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


                }
                break;
            }

        }
    }


    public void startVoiceInput(View v) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }

    }



}