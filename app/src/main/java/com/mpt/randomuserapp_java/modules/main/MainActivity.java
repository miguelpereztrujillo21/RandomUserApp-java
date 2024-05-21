package com.mpt.randomuserapp_java.modules.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mpt.randomuserapp_java.adapters.UserAdapter;
import com.mpt.randomuserapp_java.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.annotations.NonNull;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel ;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivityMainBinding.inflate(getLayoutInflater());
       viewModel = new ViewModelProvider(this).get(MainViewModel.class);
       setContentView(binding.getRoot());
       setUpAdapter();
       initObservers();
       viewModel.getUsersFromDatabase();
    }

    private void initObservers() {
        viewModel.getUsers().observe(this, users -> {
            adapter.submitList(users);
        });
    }

    private void setUpAdapter() {
        adapter = new UserAdapter(this, user -> {
            // Handle click event
        });
        binding.recyclerMain.setAdapter(adapter);
        setUpRecyclerView();
    }
    private void setUpRecyclerView() {
        binding.recyclerMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@androidx.annotation.NonNull @NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();
                    if (dy > 0 && totalItemCount >= lastVisible - 5) {
                        viewModel.loadNextPage();
                    }
                }
            }
        });
    }


}