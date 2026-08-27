package com.wgmanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class AddServerActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextInputEditText etName, etLocation, etCountryCode;
    private Button btnSave;
    private ServerManager serverManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_server);

        serverManager = new ServerManager(this);

        btnBack = findViewById(R.id.btnBack);
        etName = findViewById(R.id.etName);
        etLocation = findViewById(R.id.etLocation);
        etCountryCode = findViewById(R.id.etCountryCode);
        btnSave = findViewById(R.id.btnSave);

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> saveServer());
    }

    private void saveServer() {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String location = etLocation.getText() != null ? etLocation.getText().toString().trim() : "";
        String code = etCountryCode.getText() != null ? etCountryCode.getText().toString().trim() : "";

        if (name.isEmpty()) {
            etName.setError(getString(R.string.required_field));
            return;
        }

        Server server = new Server(name, location, code);
        serverManager.addServer(server);
        finish();
    }
}
