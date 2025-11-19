package com.devs.frutybot.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devs.frutybot.R;
import com.devs.frutybot.data.dto.RequestItemDto;

public class NotificationsAdapter extends ListAdapter<RequestItemDto, NotificationsAdapter.ViewHolder> {

    public interface OnItemClick {
        void onClick(RequestItemDto item);
    }

    private final OnItemClick onItemClick;

    public NotificationsAdapter(OnItemClick onItemClick) {
        super(DIFF_CALLBACK);
        this.onItemClick = onItemClick;
    }

    public static final DiffUtil.ItemCallback<RequestItemDto> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<RequestItemDto>() {
                @Override
                public boolean areItemsTheSame(@NonNull RequestItemDto oldItem, @NonNull RequestItemDto newItem) {
                    return oldItem.getRequestId().equals(newItem.getRequestId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull RequestItemDto oldItem, @NonNull RequestItemDto newItem) {
                    // Comparamos campos relevantes para re-renderizar
                    boolean sameStatus = safeEquals(oldItem.getStatus(), newItem.getStatus());
                    boolean samePhoto = safeEquals(oldItem.getPhotoPath(), newItem.getPhotoPath());
                    boolean sameFruitName =
                            oldItem.getFruit() == null && newItem.getFruit() == null
                                    || (oldItem.getFruit() != null && newItem.getFruit() != null
                                    && safeEquals(oldItem.getFruit().getNombre(), newItem.getFruit().getNombre()));
                    return sameStatus && samePhoto && sameFruitName;
                }

                private boolean safeEquals(String a, String b) {
                    return (a == null && b == null) || (a != null && a.equals(b));
                }
            };

    @NonNull
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position), onItemClick);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtStatus;
        private final ImageView imgPhoto;

        ViewHolder(View itemView) {
            super(itemView);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            imgPhoto  = itemView.findViewById(R.id.imgPhoto);
        }

        void bind(RequestItemDto item, OnItemClick onItemClick) {
            if (item.getFruit() != null && item.getFruit().getNombre() != null) {
                txtStatus.setText(item.getStatus() + " - " + item.getFruit().getNombre());
            } else {
                txtStatus.setText(item.getStatus());
            }

            // Cargar miniatura con Glide (photoPath viene como URL del backend)
            if (item.getPhotoPath() != null && !item.getPhotoPath().isEmpty()) {
                Glide.with(imgPhoto.getContext())
                        .load(item.getPhotoPath())
                        .placeholder(R.drawable.ic_fruit_placeholder)
                        .error(R.drawable.ic_fruit_placeholder)
                        .centerCrop()
                        .into(imgPhoto);
            } else {
                imgPhoto.setImageResource(R.drawable.ic_fruit_placeholder);
            }

            itemView.setOnClickListener(v -> {
                if (onItemClick != null) onItemClick.onClick(item);
            });
        }
    }
}
