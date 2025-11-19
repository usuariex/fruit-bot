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
import com.devs.frutybot.common.Config;
import com.devs.frutybot.domain.model.Department;
import com.devs.frutybot.presentation.adapters.DepartmentAdapter;

import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private RecyclerView recyclerDepartments;
    
    // Usar la constante directa de Config para asegurar que se use la IP actualizada
    // NOTA: Asegúrate de que Config.BASE_URL ya no tenga el puerto si las rutas de imagen no lo necesitan, 
    // o ajusta aquí si tus imágenes están en el mismo puerto que la API.
    // Según tu server.js, las imágenes estáticas se sirven en el mismo express app, 
    // así que BASE_URL debe incluir puerto.
    private String mi_ip_local = Config.BASE_URL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerDepartments = view.findViewById(R.id.recyclerDepartments);

        // Se usa mi_ip_local (que viene de Config.BASE_URL)
        List<Department> list = Arrays.asList(
                new Department("Amazonas", mi_ip_local + "/public/depto/Amazonas.jpg"),
                new Department("Áncash", mi_ip_local + "/public/depto/Ancash.jpg"),
                new Department("Apurimac", mi_ip_local + "/public/depto/Apurimac.jpg"),
                new Department("Arequipa", mi_ip_local + "/public/depto/Arequipa.jpg"),
                new Department("Ayacucho", mi_ip_local + "/public/depto/Ayacucho.jpg"),
                new Department("Cajamarca", mi_ip_local + "/public/depto/Cajamarca.jpg"),
                new Department("Callao", mi_ip_local + "/public/depto/Callao.jpg"),
                new Department("Cusco", mi_ip_local + "/public/depto/Cusco.jpg"),
                new Department("Huancavelica", mi_ip_local + "/public/depto/Huancavelica.jpg"),
                new Department("Huánuco", mi_ip_local + "/public/depto/Huanuco.jpg"),
                new Department("Ica", mi_ip_local + "/public/depto/Ica.jpg"),
                new Department("Junin", mi_ip_local + "/public/depto/Junin.jpg"),
                new Department("La Libertad", mi_ip_local + "/public/depto/lalibertad.jpg"),
                new Department("Lambayeque", mi_ip_local + "/public/depto/Lambayeque.jpg"),
                new Department("Lima", mi_ip_local + "/public/depto/lima.jpg"),
                new Department("Loreto", mi_ip_local + "/public/depto/Loreto.jpg"),
                new Department("Madre de Dios", mi_ip_local + "/public/depto/MadredeDios.jpg"),
                new Department("Moquegua", mi_ip_local + "/public/depto/Moquegua.jpg"),
                new Department("Pasco", mi_ip_local + "/public/depto/Pasco.jpg"),
                new Department("Piura", mi_ip_local + "/public/depto/Piura.jpg"),
                new Department("Puno", mi_ip_local + "/public/depto/Puno.jpg"),
                new Department("San Martin", mi_ip_local + "/public/depto/SanMartin.jpg"),
                new Department("Tacna", mi_ip_local + "/public/depto/Tacna.jpg"),
                new Department("Tumbes", mi_ip_local + "/public/depto/Tumbes.jpg"),
                new Department("Ucayali", mi_ip_local + "/public/depto/Ucayali.jpg")
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
