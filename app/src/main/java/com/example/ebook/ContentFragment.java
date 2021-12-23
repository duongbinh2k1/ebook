package com.example.ebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

public class ContentFragment extends Fragment {

    public static final String TAG = ContentFragment.class.getName();

    TextView tvContent;
    Button btnBack;
    Button btnPlayAudio;
    MediaPlayer mediaPlayer;
    MainActivity mainActivity;
    Boolean isPlaying = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_content, container, false);
        tvContent = mView.findViewById(R.id.tv_content);
        btnBack = mView.findViewById(R.id.btn_back);
        btnPlayAudio = mView.findViewById(R.id.btn_play_audio);
        mainActivity = (MainActivity) getActivity();
        String PkgName = BuildConfig.APPLICATION_ID;

        Bundle bundle = getArguments();

        if(bundle != null) {
            Chapter chapter = (Chapter) bundle.get("object_chapter");
            if(chapter != null) {
                tvContent.setText(chapter.getContent());
                int ID = getResources().getIdentifier(chapter.getName().toLowerCase().replace(" ", ""), "raw", PkgName);
                Log.d("Log1", String.valueOf(ID));
                if(ID != 0) {
                    mediaPlayer = MediaPlayer.create(mainActivity, ID);
                } else {
                    mediaPlayer = MediaPlayer.create(mainActivity, R.raw.chapter0);
//                    btnPlayAudio.setVisibility(View.GONE);
                }
            }
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            String stringState = null;
            if (mainActivity != null) {
                stringState = mainActivity.getChapterString();
            }
            tvContent.setText(stringState);
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            btnBack.setVisibility(View.GONE);
            btnPlayAudio.setVisibility(View.GONE);
        } else  {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getFragmentManager() != null) {
                        getFragmentManager().popBackStack();
                    }
                    pauseAudio();
                }
            });
        }
        btnPlayAudio.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if(!isPlaying) {
                    playAudio();
                    btnPlayAudio.setText("Playing");
                    isPlaying = true;
                } else {
                    pauseAudio();
                    btnPlayAudio.setText("Pause");
                    isPlaying = false;
                }
            }
        });
        return mView;
    }

    private void playAudio() {
        mediaPlayer.start();
    }
    private void  pauseAudio() {
        mediaPlayer.pause();
    }
}