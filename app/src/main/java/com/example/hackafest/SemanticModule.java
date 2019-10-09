package com.example.hackafest;

import java.util.ArrayList;
import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import static java.util.Date.parse;

/**
 *
 * @author juan
 */
public class SemanticModule {
    public static String direccion="";//la carpeta donde se van a guardar los archivos generados.

    ArrayList<String> negativeWordsArray= new ArrayList<String>();
    ArrayList<String> positiveWordsArray= new ArrayList<String>();
    ArrayList<String> stopWordsArray= new ArrayList<String>();
    ArrayList<String> results= new ArrayList<>();

    static String linea="";
    static String palabra="";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SemanticModule s = new SemanticModule();
        // s.createList(this);
        s.getEmotionalValue("Las imágenes más increíbles de la naturaleza horrible y sus paisajes. imágenes que marcarán la historia del mundo... SIGUENOS!!!");

    }

    public  void createList(Context context, AssetManager assetManager){

        AssetManager mngr;


        try{
            BufferedReader br=new BufferedReader(new
                    InputStreamReader(context.getAssets().open("negative_words_es.txt")));
            while((linea = br.readLine()) != null){
                linea=linea+' ';
                for(int i=0; i<linea.length(); i++){
                    if(linea.charAt(i)!=' '){
                        palabra=palabra+linea.charAt(i);
                    }
                    else if(linea.charAt(i)==' '){
                        //System.out.println("--"+palabra);
                        negativeWordsArray.add(palabra);
                        palabra="";
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("error: "+e);
        }

        try{
            BufferedReader br=new BufferedReader(new
                    InputStreamReader(context.getAssets().open("positive_words_es.txt")));
            while((linea = br.readLine()) != null){
                linea=linea+' ';
                for(int i=0; i<linea.length(); i++){
                    if(linea.charAt(i)!=' '){
                        palabra=palabra+linea.charAt(i);
                    }
                    else if(linea.charAt(i)==' '){
                        //System.out.println("--"+palabra);
                        positiveWordsArray.add(palabra);
                        palabra="";
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("error: "+e);
        }



        try{
            BufferedReader br=new BufferedReader(new
                    InputStreamReader(context.getAssets().open("stop_words.txt")));
            while((linea = br.readLine()) != null){
                linea=linea+' ';
                for(int i=0; i<linea.length(); i++){
                    if(linea.charAt(i)!=' '){
                        palabra=palabra+linea.charAt(i);
                    }
                    else if(linea.charAt(i)==' '){
                        //System.out.println("--"+palabra);
                        stopWordsArray.add(palabra);
                        palabra="";
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("error: "+e);
        }








    }

    public ArrayList getEmotionalValue(String text){
        //this is the person's evaluation, the survey.
        int negative=0, neutral=0, positive=0;

        String word="";
        for(int i=0; i<text.length(); i++){
            if(text.charAt(i)!=' '){
                word=word+text.charAt(i);
            }
            else if(text.charAt(i)==' ' ){
                if(!istopWords(word)) {
                    //ver si la palabra es positiva o negativa.
                    if(isPositive(word)){
                        positive++;
                    }
                    else if(isNegative(word)){
                        negative++;
                    }
                    else{
                        neutral++;
                    }
                }
                else{
                    //System.out.println(word+ " es una stop");
                }
                word="";
            }
        }



















        int total=negative+neutral+positive;
        double x = Double.parseDouble(total+"");
        double Dnegative = Double.parseDouble(negative+"");
        double Dneutral=   Double.parseDouble(neutral+"");
        double Dpositive=Double.parseDouble(positive+"");
        double nn= Dnegative/x;

        System.out.println("negativo: "+(Dnegative/x)*100+"% neutral: "+(Dneutral/x)*100+"% positivo: "+(Dpositive/x)*100+"% ");


        results.add(""+(Dnegative/x)*100);
        results.add(""+(Dneutral/x)*100);
        results.add(""+(Dpositive/x)*100);


        return results;

    }

    public boolean isPositive(String word){
        boolean f=false;
        for(int i=0; i<positiveWordsArray.size(); i++){
            //System.out.println("palabra: "+word);
            if(word.equals(positiveWordsArray.get(i))){
                //System.out.println("positivo: "+word+" ++ "+positiveWordsArray.get(i));
                f=true;
            }
        }
        return f;
    }
    public boolean isNegative(String word){
        boolean f=false;
        for(int i=0; i<negativeWordsArray.size(); i++){
            if(word.equals(negativeWordsArray.get(i))){
                //System.out.println("compara: "+word+" -- "+negativeWordsArray.get(i));
                f=true;
            }
        }
        return f;
    }

    public boolean istopWords(String word){
        boolean f=false;
        for(int i=0; i<stopWordsArray.size(); i++){
            if(word.equals(stopWordsArray.get(i))){
                //System.out.println("compara: "+word+" -- "+negativeWordsArray.get(i));
                f=true;
            }
        }
        return f;
    }

}
