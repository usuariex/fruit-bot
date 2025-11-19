package com.devs.frutybot.presentation.adapters;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.devs.frutybot.R;
import com.devs.frutybot.data.dto.RequestItemDto;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    private List<RequestItemDto> requests = new ArrayList<>();
    private NavController navController;

    public NotificationsAdapter(NavController navController) {
        this.navController = navController;
    }

    public void submitList(List<RequestItemDto> newRequests) {
        requests = newRequests;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestItemDto item = requests.get(position);
        holder.bind(item, navController);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtStatus;
        ImageView imgPhoto;

        ViewHolder(View itemView) {
            super(itemView);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
        }

        void bind(RequestItemDto item, NavController navController) {
            if (item.getFruit() != null) {
                txtStatus.setText(item.getStatus() + " - " + item.getFruit().getNombre());
            } else {
                txtStatus.setText(item.getStatus());
            }

            if (item.getPhotoPath() != null) {
                imgPhoto.setImageURI(Uri.parse(item.getPhotoPath()));
            } else {
                imgPhoto.setImageResource(R.drawable.ic_fruit_placeholder);
            }

            // Click para navegar al detalle
            itemView.setOnClickListener(v -> {
                Bundle args = new Bundle();
                args.putString("requestId", item.getRequestId());
                // Aquí también podrías pasar el FruitDto completo como JSON
                if (item.getFruit() != null) {
                    args.putString("fruitJson", new Gson().toJson(item.getFruit()));
                }
                navController.navigate(R.id.requestDetailFragment, args);

                DialogFragment dialogFragment = (DialogFragment)
                        ((FragmentActivity) v.getContext())
                                .getSupportFragmentManager()
                                .findFragmentByTag("NotificationsDialog");
                if (dialogFragment != null) {
                    dialogFragment.dismiss();
                }
            });
        }

    }


}
