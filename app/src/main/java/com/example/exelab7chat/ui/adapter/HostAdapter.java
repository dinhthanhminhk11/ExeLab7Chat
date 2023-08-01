package com.example.exelab7chat.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.exelab7chat.R;
import com.example.exelab7chat.databinding.ItemListHostChatBinding;
import com.example.exelab7chat.model.User;
import com.example.exelab7chat.model.UserClient;

import java.util.List;
import java.util.Random;

public class HostAdapter extends RecyclerView.Adapter<HostAdapter.MyViewHolder> {
    private List<User> listHost;
    private EventClick eventClick;

    private int color = Color.BLACK;
    private int color2;
    private int background;

    public HostAdapter() {
    }


    public void setListHost(List<User> listHost) {
        this.listHost = listHost;
    }

    public void setEventClick(EventClick eventClick) {
        this.eventClick = eventClick;
    }

    @NonNull
    @Override
    public HostAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ItemListHostChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HostAdapter.MyViewHolder holder, int position) {
        User item = listHost.get(position);
        if (item != null) {

            String[] stringArray = {
                    "https://images.pexels.com/photos/1379636/pexels-photo-1379636.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                    "https://images.pexels.com/photos/12186839/pexels-photo-12186839.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2%202x",
                    "https://images.pexels.com/photos/797797/pexels-photo-797797.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2%202x",
                    "https://images.pexels.com/photos/17309773/pexels-photo-17309773/free-photo-of-th-i-trang-dan-ba-lang-m-n-hoa.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2%202x",
                    "https://images.pexels.com/photos/1402787/pexels-photo-1402787.jpeg?auto=compress&cs=tinysrgb&w=1600"
            };
            String randomString = getRandomStringFromArray(stringArray);


            holder.binding.tvNameHost.setText(item.getName());
            holder.binding.email.setText(item.getEmail());
            Glide.with(holder.itemView.getContext()).load(randomString).transform(new CenterCrop(), new RoundedCorners(20)).into(holder.binding.imgHost);
            holder.itemView.setOnClickListener(v -> {
                if(item.get_id().equals(UserClient.getInstance().get_id())){
                    Toast.makeText(holder.itemView.getContext(), "Không thể chat với chính mình", Toast.LENGTH_SHORT).show();
                }else {
                    eventClick.onClick(item);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listHost == null ? 0 : listHost.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemListHostChatBinding binding;

        public MyViewHolder(ItemListHostChatBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private static String getRandomStringFromArray(String[] stringArray) {
        Random random = new Random();
        int randomIndex = random.nextInt(stringArray.length);
        return stringArray[randomIndex];
    }

    public interface EventClick {
        void onClick(User user);
    }
}
