package com.example.fragmentNews;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsContentFragment extends Fragment {

    private View view;
    private TextView newsTitleText,newsContentText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_content, container, false);
        return view;
    }

    public void refresh(String newsTitle,String newsContent){
        View visibilityLayout = view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        newsTitleText = view.findViewById(R.id.news_title);
        newsContentText = visibilityLayout.findViewById(R.id.news_content);
        newsTitleText.setText(newsTitle);
        newsContentText.setText(newsContent);
    }

}
