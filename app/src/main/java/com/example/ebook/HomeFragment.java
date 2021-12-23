package com.example.ebook;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    private MainActivity mMainActivity;
    private ChapterAdapter chapterAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_home, container, false);
        mMainActivity = (MainActivity) getActivity();

        RecyclerView rcvChapter = mView.findViewById(R.id.rcv_chapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainActivity);
        rcvChapter.setLayoutManager(linearLayoutManager);

        chapterAdapter = new ChapterAdapter(getListChapter(), new ChapterAdapter.IClickItemListener() {
            @Override
            public void onClickChapter(Chapter chapter) {
                mMainActivity.goToContentFragment(chapter);
            }
        });
        rcvChapter.setAdapter(chapterAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mMainActivity, DividerItemDecoration.VERTICAL);
        rcvChapter.addItemDecoration(itemDecoration);

        return mView;
    }

    private List<Chapter> getListChapter() {
        List<Chapter> chapterList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("chapter_list");

        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Chapter chapter = dataSnapshot.getValue(Chapter.class);
                    chapterList.add(chapter);
                }
                chapterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mMainActivity, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        return chapterList;
    }

}