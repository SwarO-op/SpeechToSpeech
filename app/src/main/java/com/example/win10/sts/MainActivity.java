package com.example.win10.sts;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView voiceInput;
    private ImageButton btnspeak;
    private TextToSpeech t1;
    private ImageView img;
    private String spokenWords;
    static final int CAM_REQUEST=1;
    private final int REQ_CODE_SPEECH_INPUT=100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voiceInput = (TextView) findViewById(R.id.voiceInput);

        btnspeak=(ImageButton) findViewById(R.id.btnspeak);
        img=(ImageView) findViewById(R.id.image);
        btnspeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                askSpeechInput();
            }
        });

       /* b1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR) {
                            t1.setLanguage(Locale.UK);
                            //String toSpeak = voiceInput.getText().toString();
                            Toast.makeText(getApplicationContext(), spokenWords,Toast.LENGTH_SHORT).show();
                            t1.speak(spokenWords, TextToSpeech.QUEUE_FLUSH, null);

                        }
                    }
                });

            }
        });*/

    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    // Showing google speech input dialog

    private void askSpeechInput()
    {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try
        {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        }
        catch (ActivityNotFoundException a) {

        }
    }

    // Receiving speech input

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path="sdcard/camera_app/cam_image.jpg";
       img.setImageDrawable(Drawable.createFromPath(path) );
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    spokenWords =result.get(0);
                    voiceInput.setText(result.get(0));
                    tellSpokenWords();
                }
                break;
            }

        }
    }
public File getFile()
{
    File folder= new File("sdcard/camera_app");
    if(!folder.exists())
    {
        folder.mkdir();
    }
    File image= new File(folder,"cam_img.jpg");
    return image;
}
    private void tellSpokenWords(){
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.getDefault());
                    //String toSpeak = voiceInput.getText().toString();
                    //Toast.makeText(getApplicationContext(), spokenWords,Toast.LENGTH_SHORT).show();
                    if(spokenWords.equalsIgnoreCase("Who are you"))//question
                    {
                        t1.speak("Me Tejas,personel assistant", TextToSpeech.QUEUE_FLUSH, null);//answer

                    }else if(spokenWords.equalsIgnoreCase("Age"))//question
                    {
                        t1.speak("19", TextToSpeech.QUEUE_FLUSH, null);//answer

                    }
                    else if(spokenWords.equalsIgnoreCase("college"))//question
                    {
                        t1.speak("ATME", TextToSpeech.QUEUE_FLUSH, null);//answer

                    }
                    else if(spokenWords.equalsIgnoreCase("father name"))//question
                    {
                        t1.speak("rajesh kumar", TextToSpeech.QUEUE_FLUSH, null);//answer

                    }
                    else if(spokenWords.equalsIgnoreCase("mother name"))//question
                    {
                        t1.speak("bhanumathi", TextToSpeech.QUEUE_FLUSH, null);//answer

                    }
                    else if(spokenWords.equalsIgnoreCase("whatsapp"))//question
                    {
                        Intent sendIntent = new Intent();
                        sendIntent.setPackage("com.whatsapp");
                        startActivity(sendIntent);
                    }
                    else if(spokenWords.equalsIgnoreCase("what's up"))//question
                    {
                        Intent sendIntent = new Intent();
                        sendIntent.setPackage("com.whatsapp");
                        startActivity(sendIntent);
                    }
                    else if(spokenWords.equalsIgnoreCase("hi"))//question
                    {
                        t1.speak("hello how can i help you", TextToSpeech.QUEUE_FLUSH, null);//answer

                    }
                    else if(spokenWords.equalsIgnoreCase("hey"))//question
                    {
                        t1.speak("hello how can i help you", TextToSpeech.QUEUE_FLUSH, null);//answer

                    }
                    else if(spokenWords.equalsIgnoreCase("google"))//question
                    {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse("http://www.google.com/"));
                        startActivity(viewIntent);
                }
                    else if(spokenWords.equalsIgnoreCase("facebook"))//question
                    {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse("http://www.facebook.com/"));
                        startActivity(viewIntent);
                    }
                    else if(spokenWords.equalsIgnoreCase("flipkart"))//question
                    {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse("http://www.flipkart.com/"));
                        startActivity(viewIntent);
                    }
                    else if(spokenWords.equalsIgnoreCase("amazon"))//question
                    {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse("http://www.amazon.in/"));
                        startActivity(viewIntent);
                    }
                    else if(spokenWords.equalsIgnoreCase("wikipedia"))//question
                    {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse("http://www.wikipedia.org/"));
                        startActivity(viewIntent);
                    }
                    else if(spokenWords.equalsIgnoreCase("gmail"))//question
                    {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse("http://www.gmail.com/"));
                        startActivity(viewIntent);
                    }
                    else if(spokenWords.equalsIgnoreCase("camera"))//question
                    {
                     Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File file=getFile();
                        camera.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
                        startActivityForResult(camera,CAM_REQUEST);

                    }


                    else{
                        t1.speak(spokenWords, TextToSpeech.QUEUE_FLUSH, null);

                    }


                }
            }
        });


    }


}