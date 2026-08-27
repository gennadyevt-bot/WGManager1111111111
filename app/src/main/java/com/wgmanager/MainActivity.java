package com.wgmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle, tvServerName, tvServerLocation, tvStatus, tvTapHint;
    private ImageView ivStatusDot;
    private CardView cardServer;
    private ImageButton btnAdd, btnVpn;
    private ServerManager serverManager;
    private boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverManager = new ServerManager(this);

        tvTitle = findViewById(R.id.tvTitle);
        tvServerName = findViewById(R.id.tvServerName);
        tvServerLocation = findViewById(R.id.tvServerLocation);
        tvStatus = findViewById(R.id.tvStatus);
        tvTapHint = findViewById(R.id.tvTapHint);
        ivStatusDot = findViewById(R.id.ivStatusDot);
        cardServer = findViewById(R.id.cardServer);
        btnAdd = findViewById(R.id.btnAdd);
        btnVpn = findViewById(R.id.btnVpn);

        tvTitle.setText(R.string.app_name);

        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddServerActivity.class));
        });

        btnVpn.setOnClickListener(v -> toggleConnection());

        cardServer.setOnClickListener(v -> showServerList());

        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        Server server = serverManager.getSelectedServer();
        if (server != null) {
            cardServer.setVisibility(View.VISIBLE);
            tvServerName.setText(server.getName());
            tvServerLocation.setText(server.getLocation());
            isConnected = server.isConnected();
        } else {
            cardServer.setVisibility(View.GONE);
            isConnected = false;
        }
        updateConnectionState();
    }

    private void updateConnectionState() {
        if (isConnected) {
            btnVpn.setImageResource(R.drawable.ic_vpn_button_green);
            tvStatus.setText(R.string.connected);
            tvStatus.setTextColor(ContextCompat.getColor(this, R.color.green));
            tvTapHint.setText(R.string.tap_to_disconnect);
            ivStatusDot.setColorFilter(ContextCompat.getColor(this, R.color.green));
        } else {
            btnVpn.setImageResource(R.drawable.ic_vpn_button_red);
            tvStatus.setText(R.string.disconnected);
            tvStatus.setTextColor(ContextCompat.getColor(this, R.color.gray_text));
            tvTapHint.setText(R.string.tap_to_connect);
            ivStatusDot.setColorFilter(ContextCompat.getColor(this, R.color.gray_dot));
        }
    }

    private void toggleConnection() {
        Server server = serverManager.getSelectedServer();
        if (server == null) {
            startActivity(new Intent(this, AddServerActivity.class));
            return;
        }
        isConnected = !isConnected;
        server.setConnected(isConnected);
        int idx = serverManager.getSelectedIndex();
        serverManager.updateServer(server, idx);
        updateConnectionState();
    }

    private void showServerList() {
        // TODO: показать диалог выбора сервера
    }
}
