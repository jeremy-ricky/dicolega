package com.getsoft.dicolega;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class DetailFragment extends Fragment {

    private String value = "";
    private TextView tvWord;
    private ImageButton btnBookmark, btnVolume, btnPartage;
    private WebView tvWordTranslate;
    private DBHelper mDBHelper;
    private int mDicType;

    EditText editText;
    TextToSpeech textToSpeech;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment getNewInstance(String value, DBHelper dbHelper, int dicType){
        DetailFragment fragment = new DetailFragment();
        fragment.value = value;
        fragment.mDBHelper = dbHelper;
        fragment.mDicType = dicType;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvWord = view.findViewById(R.id.tvWord);
        tvWordTranslate = view.findViewById(R.id.tvWordTranslate);
        btnBookmark = view.findViewById(R.id.btnBookmark);
        btnVolume = view.findViewById(R.id.btnVolume);

        btnPartage = view.findViewById(R.id.btnPartage);
        btnVolume = view.findViewById(R.id.btnVolume);

        final Word word = mDBHelper.getWord(value, mDicType);
        tvWord.setText(word.key);
        tvWordTranslate.loadDataWithBaseURL(null, word.value, "text/html", "utf-8", null);

        Word bookmarkWord = mDBHelper.getWordFromBookmark(value);
        int isMark = bookmarkWord == null ? 0 : 1;
        btnBookmark.setTag(isMark);

        //btnBookmark.setTag(0);

        int icon = bookmarkWord == null? R.drawable.ic_bookmark_border : R.drawable.ic_bookmark_fill;
        btnBookmark.setImageResource(icon);

        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) btnBookmark.getTag();
                if (i == 0) {
                    btnBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                    btnBookmark.setTag(1);
                    mDBHelper.addBookmark(word);
                } else if (i == 1) {
                    btnBookmark.setImageResource(R.drawable.ic_bookmark_border);
                    btnBookmark.setTag(0);
                    mDBHelper.removeBookmark(word);
                }
            }
        });

        /*
        textToSpeech = new TextToSpeech(DetailFragment.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.FRENCH);
                }
            }
        });

        btnVolume.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                // get text from editetext
                String text = String.valueOf(tvWordTranslate.getWebViewClient());

                //convert text to speech
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }); */

        btnPartage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareSub = getString(R.string.sharSub);
                String shareBody = getString(R.string.sharBody);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.sharTitle)));
            }
        });

    }

    @Override
    public void onPause() {
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}

