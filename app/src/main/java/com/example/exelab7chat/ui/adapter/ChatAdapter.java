package com.example.exelab7chat.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exelab7chat.R;
import com.example.exelab7chat.model.ContentChat;
import com.example.exelab7chat.model.UserClient;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ContentChat> listContentsChat;

    public ChatAdapter(List<ContentChat> contents) {
        this.listContentsChat = contents;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new chatUserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_user, parent, false));
        } else {
            return new chatBossViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_boss, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ContentChat content = listContentsChat.get(position);
        if (holder.getItemViewType() == 0) {
            chatUserViewHolder chatUserViewHolder = (ChatAdapter.chatUserViewHolder) holder;
            chatUserViewHolder.tvMessageUser.setText(content.getMessage().getText());
        } else {
            chatBossViewHolder chatBossViewHolder = (ChatAdapter.chatBossViewHolder) holder;
            chatBossViewHolder.tvMessageBoss.setText(content.getMessage().getText());
        }
    }

    @Override
    public int getItemCount() {
        return listContentsChat.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (listContentsChat.get(position).getSend().equals(UserClient.getInstance().get_id())) {
            return 0;
        } else {
            return 1;
        }
    }

    public class chatUserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMessageUser;

        public chatUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessageUser = itemView.findViewById(R.id.tvMessageUser);
        }
    }

    public class chatBossViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMessageBoss;

        public chatBossViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessageBoss = itemView.findViewById(R.id.tvMessageBoss);
        }
    }
}
