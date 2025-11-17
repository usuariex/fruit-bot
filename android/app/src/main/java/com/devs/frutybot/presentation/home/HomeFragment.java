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
                new Department("Amazonas", "http://192.168.100.229:3020/public/depto/Amazonas.jpg"),
                new Department("Áncash", "http://192.168.100.229:3020/public/depto/Ancash.jpg"),
                new Department("Apurimac", "http://192.168.100.229:3020/public/depto/Apurimac.jpg"),
                new Department("Arequipa", "http://192.168.100.229:3020/public/depto/Arequipa.jpg"),
                new Department("Ayacucho", "http://192.168.100.229:3020/public/depto/Ayacucho.jpg"),
                new Department("Cajamarca", "http://192.168.100.229:3020/public/depto/Cajamarca.jpg"),
                new Department("Callao", "http://192.168.100.229:3020/public/depto/Callao.jpg"),
                new Department("Cusco", "http://192.168.100.229:3020/public/depto/Cusco.jpg"),
                new Department("Huancavelica", "http://192.168.100.229:3020/public/depto/Huancavelica.jpg"),
                new Department("Huánuco", "http://192.168.100.229:3020/public/depto/Huanuco.jpg"),
                new Department("Ica", "http://192.168.100.229:3020/public/depto/Ica.jpg"),
                new Department("Junin", "http://192.168.100.229:3020/public/depto/Junin.jpg"),
                new Department("La Libertad", "http://192.168.100.229:3020/public/depto/lalibertad.jpg"),
                new Department("Lambayeque", "http://192.168.100.229:3020/public/depto/Lambayeque.jpg"),
                new Department("Lima", "http://192.168.100.229:3020/public/depto/Lima.jpg"),
                new Department("Loreto", "http://192.168.100.229:3020/public/depto/Loreto.jpg"),
                new Department("Madre de Dios", "http://192.168.100.229:3020/public/depto/MadredeDios.jpg"),
                new Department("Moquegua", "http://192.168.100.229:3020/public/depto/Moquegua.jpg"),
                new Department("Pasco", "http://192.168.100.229:3020/public/depto/Pasco.jpg"),
                new Department("Piura", "http://192.168.100.229:3020/public/depto/Piura.jpg"),
                new Department("Puno", "http://192.168.100.229:3020/public/depto/Puno.jpg"),
                new Department("San Martin", "http://192.168.100.229:3020/public/depto/SanMartin.jpg"),
                new Department("Tacna", "http://192.168.100.229:3020/public/depto/Tacna.jpg"),
                new Department("Tumbes", "http://192.168.100.229:3020/public/depto/Tumbes.jpg"),
                new Department("Ucayali", "http://192.168.100.229:3020/public/depto/Ucayali.jpg ")

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
