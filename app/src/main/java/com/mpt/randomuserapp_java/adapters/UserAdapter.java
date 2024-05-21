package com.mpt.randomuserapp_java.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mpt.randomuserapp_java.R;
import com.mpt.randomuserapp_java.databinding.ItemRowProgressBinding;
import com.mpt.randomuserapp_java.databinding.UserItemBinding;
import com.mpt.randomuserapp_java.models.User;

import io.reactivex.rxjava3.annotations.NonNull;

public class UserAdapter extends ListAdapter<User, RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private boolean isLoading;
    private Context context;
    private ClickListener clickListener;

    public UserAdapter(Context context, ClickListener clickListener) {
        super(new CountryDiffCallback());
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoading && position == getItemCount() - 1) {
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            UserItemBinding binding = UserItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new UserViewHolder(binding);
        } else {
            ItemRowProgressBinding binding = ItemRowProgressBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new LoadingViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            User user = getItem(position);
            ((UserViewHolder) holder).bind(context, user, clickListener);
        }
    }

    public User getUserPosition(int position) {
        return getItem(position);
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private UserItemBinding binding;

        public UserViewHolder(UserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Context context, User user, ClickListener clickListener) {
            Glide.with(context).load(user.getPicture().getThumbnail()).into(binding.profileImageUserItem);
            binding.nameUserItem.setText(context.getString(R.string.user_full_name, user.getName().getFirst(), user.getName().getLast()));
            binding.emailUserItem.setText(user.getEmail());
            binding.containerUserItem.setOnClickListener(v -> clickListener.onClick(user));
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(ItemRowProgressBinding binding) {
            super(binding.getRoot());
        }
    }

    public static class CountryDiffCallback extends DiffUtil.ItemCallback<User> {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    }

    public interface ClickListener {
        void onClick(User user);
    }
}
