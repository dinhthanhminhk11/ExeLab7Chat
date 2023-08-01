package com.example.exelab7chat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.exelab7chat.R;
import com.example.exelab7chat.constants.AppConstant;
import com.example.exelab7chat.databinding.ActivityChatBinding;
import com.example.exelab7chat.model.ContentChat;
import com.example.exelab7chat.model.MessageChat;
import com.example.exelab7chat.model.MessageSocket;
import com.example.exelab7chat.model.Text;
import com.example.exelab7chat.model.UserClient;
import com.example.exelab7chat.ui.adapter.ChatAdapter;
import com.example.exelab7chat.viewmodel.ChatViewModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private Socket mSocket;
    private String nameUser = "";
    private String idUser = "";
    private String imageUser = "";
    private List<ContentChat> listChat = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private ChatViewModel chatViewModel;

    {
        try {
            mSocket = IO.socket(AppConstant.BASE);
        } catch (URISyntaxException e) {
            e.getMessage();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        initView();
        intiData();

    }

    private void intiData() {
        idUser = getIntent().getStringExtra(AppConstant.ID_USER);
        nameUser = getIntent().getStringExtra(AppConstant.NAME_USER);

        String[] stringArray = {
                "https://images.pexels.com/photos/1379636/pexels-photo-1379636.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                "https://images.pexels.com/photos/12186839/pexels-photo-12186839.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2%202x",
                "https://images.pexels.com/photos/797797/pexels-photo-797797.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2%202x",
                "https://images.pexels.com/photos/17309773/pexels-photo-17309773/free-photo-of-th-i-trang-dan-ba-lang-m-n-hoa.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2%202x",
                "https://images.pexels.com/photos/1402787/pexels-photo-1402787.jpeg?auto=compress&cs=tinysrgb&w=1600"
        };
        String randomString = getRandomStringFromArray(stringArray);

        RequestOptions optionsUser = new RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background);
        Glide.with(this).load(randomString).apply(optionsUser).into(binding.imgBossChat);

        binding.tvNameBossChat.setText(nameUser);

        chatViewModel.getContentChatLiveData(UserClient.getInstance().get_id(), idUser).observe(this, it -> {
            for (int i = 0; i <= it.size() - 1; i++) {
                listChat.add(it.get(i));
            }
            chatAdapter = new ChatAdapter(listChat);
            binding.rcvChatMessage.setAdapter(chatAdapter);
            binding.rcvChatMessage.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
            binding.rcvChatMessage.smoothScrollToPosition(listChat.size());
        });
        if (mSocket != null) {
            mSocket.on("new message", onNewMessage);
            mSocket.emit("join", UserClient.getInstance().get_id());
            mSocket.on("join", checkOnline);
        }

        binding.btnSent.setOnClickListener(v -> {
            sendChat();
        });

        binding.btnSent.setAlpha(0.4f);
        binding.btnSent.setEnabled(false);

        binding.edContentChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    binding.btnSent.setEnabled(true);
                    binding.btnSent.setAlpha(1);
                } else {
                    binding.btnSent.setAlpha(0.4f);
                    binding.btnSent.setEnabled(false);
                }
            }
        });

    }
    private void initView() {
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
    }

    private void initToolbar() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (mSocket != null) {
            mSocket.connect();
        }
    }

    private final Emitter.Listener checkOnline = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                String mess = data.optString("id");
                if (mess.equals(idUser)) {
                    binding.tvCheckOnline.setText("Online");
                    binding.imgOnlineChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_online));
                } else {
                    binding.tvCheckOnline.setText("Offline");
                    binding.imgOnlineChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_offline));
                }
            });
        }
    };

    public void sendChat() {
        if (binding.edContentChat.getText().toString().isEmpty() || binding.edContentChat.getText().toString().trim().equals("")) {
            binding.edContentChat.setText("");
            return;
        }
        DateFormat df = new SimpleDateFormat("HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        Gson gson = new Gson();
        MessageSocket ms = new MessageSocket(UserClient.getInstance().get_id(), idUser, UserClient.getInstance().get_id(), binding.edContentChat.getText().toString(), date);
        try {
            JSONObject jObject = new JSONObject(gson.toJson(ms));
            if (mSocket != null) {
                mSocket.emit("message", jObject);
            }
            MessageChat message = new MessageChat(idUser, UserClient.getInstance().get_id(), binding.edContentChat.getText().toString());
            chatViewModel.insertChat(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listChat.add(new ContentChat(new Text(binding.edContentChat.getText().toString()), UserClient.getInstance().get_id(), idUser));
        chatAdapter.notifyDataSetChanged();
        binding.rcvChatMessage.smoothScrollToPosition(listChat.size() - 1);
        binding.edContentChat.setText("");
    }


    private final Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                String mess = data.optString("message");
                listChat.add(new ContentChat(new Text(mess), idUser, UserClient.getInstance().get_id()));
                chatAdapter.notifyDataSetChanged();
                binding.rcvChatMessage.smoothScrollToPosition(listChat.size() - 1);
            });
        }
    };

    private static String getRandomStringFromArray(String[] stringArray) {
        Random random = new Random();
        int randomIndex = random.nextInt(stringArray.length);
        return stringArray[randomIndex];
    }
}