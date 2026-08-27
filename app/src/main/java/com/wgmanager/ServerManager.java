package com.wgmanager;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    private static final String PREFS_NAME = "wg_servers";
    private static final String KEY_SERVERS = "servers";
    private static final String KEY_SELECTED = "selected_index";
    private SharedPreferences prefs;
    private Gson gson;

    public ServerManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public List<Server> getServers() {
        String json = prefs.getString(KEY_SERVERS, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Server>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public void saveServers(List<Server> servers) {
        String json = gson.toJson(servers);
        prefs.edit().putString(KEY_SERVERS, json).apply();
    }

    public void addServer(Server server) {
        List<Server> servers = getServers();
        servers.add(server);
        saveServers(servers);
        if (servers.size() == 1) {
            setSelectedIndex(0);
        }
    }

    public int getSelectedIndex() {
        return prefs.getInt(KEY_SELECTED, 0);
    }

    public void setSelectedIndex(int index) {
        prefs.edit().putInt(KEY_SELECTED, index).apply();
    }

    public Server getSelectedServer() {
        List<Server> servers = getServers();
        int idx = getSelectedIndex();
        if (idx >= 0 && idx < servers.size()) {
            return servers.get(idx);
        }
        return null;
    }

    public void updateServer(Server server, int index) {
        List<Server> servers = getServers();
        if (index >= 0 && index < servers.size()) {
            servers.set(index, server);
            saveServers(servers);
        }
    }
}
