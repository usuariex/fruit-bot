package com.devs.frutybot.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devs.frutybot.R;
import com.devs.frutybot.domain.model.Department;

import java.util.List;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> {
    private List<Department> departments;
    private OnDepartmentClickListener listener;

    public interface OnDepartmentClickListener {
        void onClick(String departmentName);
    }

    public DepartmentAdapter(List<Department> departments, OnDepartmentClickListener listener) {
        this.departments = departments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Department dept = departments.get(position);
        holder.textDepartment.setText(dept.getName());
        Glide.with(holder.itemView.getContext())
                .load(dept.getImageUrl())
                .into(holder.imageDepartment);

        holder.itemView.setOnClickListener(v -> listener.onClick(dept.getName()));
    }

    @Override
    public int getItemCount() {
        return departments.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        ImageView imageDepartment;
        TextView textDepartment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageDepartment = itemView.findViewById(R.id.imageDepartment);
            textDepartment = itemView.findViewById(R.id.textDepartment);
        }
    }
}
