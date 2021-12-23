package com.example.ebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    String chapterString = "Select Chapter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_EBook);
        setContentView(R.layout.activity_main);

        HomeFragment homeFragment = new HomeFragment();
        ContentFragment contentFragment = new ContentFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.chapter_view, homeFragment);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {
            if (savedInstanceState != null) {
                chapterString = savedInstanceState.getString("chapter_state");
            }
            fragmentTransaction.add(R.id.content_view, contentFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ContentFragment contentFragment = new ContentFragment();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentTransaction.replace(R.id.content_view, contentFragment);
        }
        fragmentTransaction.replace(R.id.chapter_view, new HomeFragment());
        fragmentTransaction.commit();
    }

    public void goToContentFragment (Chapter chapter) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ContentFragment contentFragment = new ContentFragment();
//        HomeFragment homeFragment = new HomeFragment();

        chapterString = chapter.getContent();

        Bundle bundle = new Bundle();
        bundle.putSerializable("object_chapter", chapter);

        contentFragment.setArguments(bundle);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            fragmentTransaction.replace(R.id.chapter_view, contentFragment);
            fragmentTransaction.addToBackStack(ContentFragment.TAG);
        } else {
            fragmentTransaction.replace(R.id.content_view, contentFragment);
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("chapter_state", chapterString);
    }

    public String getChapterString() {
        return chapterString;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.menu_detail) {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            startActivity(intent);
        }
         else if (id == R.id.about_us) {
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}