package com.example.ebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChapterAdapter extends  RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>{

    private final List<Chapter> mListChapter;
    private final IClickItemListener mIClickItemListener;

    public interface IClickItemListener {
        void onClickChapter(Chapter chapter);
    }

    public ChapterAdapter(List<Chapter> mListChapter, IClickItemListener listener) {
        this.mIClickItemListener = listener;
        this.mListChapter = mListChapter;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        final Chapter chapter = mListChapter.get(position);
        if(chapter == null) return;
        holder.chapterName.setText(chapter.getName());
        holder.chapterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickItemListener.onClickChapter(chapter);
            }
        });
    }


    @Override
    public int getItemCount() {
        if(mListChapter != null) {
            return mListChapter.size();
        }
        return 0;
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView chapterName;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterName = itemView.findViewById(R.id.chapter_name);
        }
    }

}
