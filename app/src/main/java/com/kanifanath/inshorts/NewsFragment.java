package com.kanifanath.inshorts;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.fragment.app.Fragment;

public class NewsFragment extends Fragment {

    private int flag = 0;
    private List<News> totalNews;
    private int position;
    private TextView headingTextView, detailTextView, linkTextView;
    private ImageView imageView;
    private Callback callback;

    public interface Callback{
        void imageSelected(int p);
        void linkSelected(int p);
        void menuSelected();
    }
    public static Fragment newInstance(List<News> total, int position) {
        NewsFragment newsFragment = new NewsFragment();

        newsFragment.totalNews = total;
        newsFragment.position = position;
        return newsFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_fragment, parent, false);
        headingTextView = (TextView)view.findViewById(R.id.headlineId);
        detailTextView = (TextView)view.findViewById(R.id.detailId);
        imageView = (ImageView)view.findViewById(R.id.photoid);
        linkTextView = (TextView)view.findViewById(R.id.linkTextViewId);

        if(NewsLab.get(getActivity()).getNews(totalNews.get(position).heading) != null){
            headingTextView.setTextColor(getResources().getColor(R.color.blue));
            flag = 1;
        }

        headingTextView.setText(totalNews.get(position).heading);
        detailTextView.setText(totalNews.get(position).detail);
        imageView.setImageBitmap(totalNews.get(position).bitImage);
        linkTextView.setText("read more at " + totalNews.get(position).linkWords +
                "\n" + "Tap to Know More");


        headingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag == 1) {
                    headingTextView.setTextColor(getResources().getColor(R.color.black));
                    Toast.makeText(getActivity(), "Bookmark Removed", Toast.LENGTH_SHORT).show();
                    NewsLab.get(getActivity()).deleteNews(totalNews.get(position));
                    flag = 0;
                }
                else {
                    headingTextView.setTextColor(getResources().getColor(R.color.blue));
                    Toast.makeText(getActivity(), "Bookmark Added", Toast.LENGTH_SHORT).show();
                    NewsLab.get(getActivity()).addNews(totalNews.get(position));
                    flag = 1;
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.imageSelected(position);
            }
        });

        linkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.linkSelected(position);
            }
        });

        detailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.menuSelected();
            }
        });
        Log.d("NewsFragment", "In NewsFragment");
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        callback = (Callback)context;
    }
    @Override
    public void onDetach(){
        super.onDetach();
        callback = null;
    }
}
