package com.example.rxjava3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rxjava3.rx.jurel.network.model.Post1DTO;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Post1DTO> post1DTOList = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_list_item, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(post1DTOList.get(position));
    }

    @Override
    public int getItemCount() {
        return post1DTOList.size();
    }

    public void setPostList(List<Post1DTO> post1DTOList) {
        this.post1DTOList = post1DTOList;
        notifyDataSetChanged();
    }

    public List<Post1DTO> getPosts() {
        return post1DTOList;
    }

    public void clear() {
        post1DTOList.clear();
        notifyDataSetChanged();
    }

    public void updatePost(Post1DTO post1DTO) {
        var index = post1DTOList.indexOf(post1DTO);
        post1DTOList.set(index, post1DTO);
        notifyItemChanged(index);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, numComments;
        ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            numComments = itemView.findViewById(R.id.num_comments);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }

        public void bind(Post1DTO post1DTO) {
            title.setText(post1DTO.getTitle());

            if (post1DTO.getComments() == null) {
                showProgressBar(true);
                numComments.setText("");
            } else {
                showProgressBar(false);
                numComments.setText(String.valueOf(post1DTO.getComments().size()));
            }
        }

        private void showProgressBar(boolean showProgressBar) {
            if (showProgressBar) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }
    }
}