package com.devs.frutybot.presentation.home;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devs.frutybot.R;
import com.devs.frutybot.domain.model.Department;
import com.devs.frutybot.presentation.adapters.DepartmentAdapter;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerDepartments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerDepartments = view.findViewById(R.id.recyclerDepartments);

        List<Department> list = Arrays.asList(
                new Department("Junin", "http://192.168.100.229:3020/public/depto/lalibertad.png"),
                new Department("Cusco", "http://192.168.100.229:3020/public/depto/lima.jpg"),
                new Department("Lima", "http://192.168.100.229:3020/public/depto/piura.jpeg"),
                new Department("chiclayo", "http://192.168.100.229:3020/public/depto/lima.jpg")
        );

        DepartmentAdapter adapter = new DepartmentAdapter(list, departmentName -> {
            Bundle args = new Bundle();
            args.putString("department", departmentName);
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_catalogFragment, args);
        });

        recyclerDepartments.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerDepartments.setAdapter(adapter);

        return view;
    }
}
