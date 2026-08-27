package com.wgmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ViewHolder> {

    private List<Server> servers;
    private OnServerClickListener listener;

    public interface OnServerClickListener {
        void onServerClick(int position);
    }

    public ServerAdapter(List<Server> servers, OnServerClickListener listener) {
        this.servers = servers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_server, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Server server = servers.get(position);
        holder.tvName.setText(server.getName());
        holder.tvLocation.setText(server.getLocation());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onServerClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return servers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvLocation;
        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvLocation = itemView.findViewById(R.id.tvItemLocation);
        }
    }
}
