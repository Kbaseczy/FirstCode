package com.example.fragmentNews;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/*

 */
public class NewsTitleFragment extends Fragment {

    private boolean isTwoPane;
    RecyclerView newsRecyclerView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.news_title_frag, container, false);
        newsRecyclerView = view.findViewById(R.id.news_title_recycler_view);
        Log.e("onCreateView",String.valueOf(newsRecyclerView));
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
            news.setContent(getRandomLengthContent()+i);
            newsList.add(news);
        }
        return newsList;
    }
    private String getRandomLengthContent(){
        Random random = new Random();
        int length = random.nextInt(20)+1;
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<length;i++){
            builder.append("Content:big big world.");
        }
        return builder.toString();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TODO 1.这里getActivity 获取的是 MainActivity对象  2.三目运算符获取boolean值
        isTwoPane = Objects.requireNonNull(getActivity()).findViewById(R.id.news_content_layout) != null;
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
        private List<News> mNewsList;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView newsTitleText;
            private ViewHolder( View itemView) {
                super(itemView);
                newsTitleText = itemView.findViewById(R.id.news_title_item);
                Log.e("ViewHolder",String.valueOf(newsTitleText));
            }
        }

        private NewsAdapter(List<News> list) {
            mNewsList = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.news_item,viewGroup,false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    News news = mNewsList.get(holder.getAdapterPosition());
                    if(isTwoPane){
                        //todo 如果是双页模式，则刷新NewsContentFragment中的内容
                        assert getFragmentManager() != null;   //todo debugPoint
                        NewsContentFragment newsContentFragment = (NewsContentFragment)
                                getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        assert newsContentFragment != null;
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
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
            //给每个子项绑定数据
            News news = mNewsList.get(i);
            //todo err:'void android.widget.TextView.setText(java.lang.CharSequence)' on a null object reference
           //todo 这个null异常通常是找不到该Layout中的TextView组件，检查layout和初始化组件是否有误

            //todo 解决。 --> 此处newsTitleText --> ViewHolder构造方法中的newsTitleText --> 构造方法中的item由视图View寻找
            // TODO      ->由onCreateViewHolder中的 View view = LayoutInflater.from(viewGroup.getContext()).
            // todo                   inflate(R.layout.news_item,viewGroup,false);
            // todo              final ViewHolder holder = new ViewHolder(view); 传入view实参
            //todo     此处newsTitleText为null原因是在构造方法中ID绑定错乱
            //  正确 newsTitleText = itemView.findViewById(R.id.news_title_item); --> 属于 news_item布局
            //  错误 newsTitleText = itemView.findViewById(R.id.news_title);    --> 属于 fragment_news_content布局
            //   之所以为空，因为你绑定成了 news_title，而传入的视图却是news_item布局，无法找到news_title的 id，造成空对象引用
            Log.e("onBindViewHolder",String.valueOf(holder.newsTitleText));
           holder.newsTitleText.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }
}
