package com.example.myvideo.ui.Explore.Article;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myvideo.R;
import com.example.myvideo.adapters.ArticlesRecyclerAdapter;
import com.example.myvideo.adapters.CatsRecyclerAdapter;
import com.example.myvideo.databinding.FragmentBlogBinding;
import com.example.myvideo.models.ArticleModel;
import com.example.myvideo.ui.Explore.ExploreViewModel;
import com.example.myvideo.utils.SharedModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class BlogFragment extends Fragment {


    FragmentBlogBinding binding;
    BottomNavigationView nav;
    ExploreViewModel viewModel;
    ArticlesRecyclerAdapter articlesRecyclerAdapter = new ArticlesRecyclerAdapter();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentBlogBinding.bind(view);
        nav = requireActivity().findViewById(R.id.nav);
        nav.setVisibility(View.VISIBLE);
        viewModel = new ViewModelProvider(this).get(ExploreViewModel.class);
        getArticles();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getArticles(){

        viewModel.getArticles();
        viewModel.articles.observe(getViewLifecycleOwner(), new Observer<ArrayList<ArticleModel>>() {
            @Override
            public void onChanged(ArrayList<ArticleModel> articleModels) {
                articlesRecyclerAdapter.setList(articleModels);
                binding.recycler.setAdapter(articlesRecyclerAdapter);

                articlesRecyclerAdapter.setOnItemClick(new ArticlesRecyclerAdapter.OnItemClick() {
                    @Override
                    public void OnClick(ArticleModel article) {
                        SharedModel.setSelected_article(article);
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame , new ArticleFragment(),"a")
                                .addToBackStack("a").commit();
                    }
                });
            }
        });


    }
}
