package com.example.fragmentNews;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsTitleFragment extends Fragment {

    private boolean isTwoPane;
    RecyclerView newsRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.news_title_frag, container, false);
        newsRecyclerView = view.findViewById(R.id.news_title_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        newsRecyclerView.setLayoutManager(manager);
        NewsAdapter adapter = new NewsAdapter(getNews());
        newsRecyclerView.setAdapter(adapter);
        return view;
    }

    private List<News> getNews(){
        List<News> newsList = new ArrayList<>();
        for(int i = 1;i<=50;i++){
            News news = new News();
            news.setTitle("Title"+i);
            news.setContent(getRandomLengthContent("Content:big big world."));
            newsList.add(news);
        }
        return newsList;
    }
    private String getRandomLengthContent(String content){
        Random random = new Random();
        int length = random.nextInt(20)+1;
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<length;i++){
            builder.append(content);
        }
        return builder.toString();
    }
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TODO TODO TODO
        if(getActivity().findViewById(R.id.news_content_layout) != null){
            isTwoPane = true;
        }else{
            isTwoPane = false;
        }
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
        private List<News> mNewsList;
        private NewsAdapter(List<News> list) {
            mNewsList = list;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView newsTitleText;
            private ViewHolder( View itemView) {
                super(itemView);
                newsTitleText = itemView.findViewById(R.id.news_title);
            }
        }
        @Override
        public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.news_item,viewGroup,false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    News news = mNewsList.get(holder.getAdapterPosition());
                    if(isTwoPane){
                        //todo 如果是双页模式，则刷新NewsContentFragment中的内容
                        NewsContentFragment newsContentFragment = (NewsContentFragment)
                                getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(),
                        news.getContent());
                    }else{
                        //TODO 如果是单页模式直接启动NewsContentActivity
                        NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            News news = mNewsList.get(i);
            //todo err:'void android.widget.TextView.setText(java.lang.CharSequence)' on a null object reference
           //todo 这个null异常通常是找不到该Layout中的TextView组件，检查layout和初始化组件是否有误
            //todo 未解决
            viewHolder.newsTitleText.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }
}
