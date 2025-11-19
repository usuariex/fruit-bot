package com.devs.frutybot.presentation.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devs.frutybot.R;
import com.devs.frutybot.common.Config;
import com.devs.frutybot.domain.model.Department;
import com.devs.frutybot.presentation.adapters.DepartmentAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final int REQ_PERM_LOCATION = 1001;
    private RecyclerView recyclerDepartments;
    private Button btnGetLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private List<Department> listDepartments;
    private DepartmentAdapter adapter;
    private String mi_ip_local = Config.BASE_URL;

    // Coordenadas aproximadas de cada departamento (lat, lng)
    // Solo ejemplo: reemplaza con coordenadas reales si quieres precisión
    private final double[][] departmentCoords = {
            { -6.5, -79.8 }, // Amazonas
            { -9.5, -77.5 }, // Áncash
            { -14.0, -72.0 }, // Apurimac
            { -16.4, -71.5 }, // Arequipa
            { -13.2, -74.2 }, // Ayacucho
            { -7.2, -78.5 }, // Cajamarca
            { -12.0, -77.1 }, // Callao
            { -13.5, -71.9 }, // Cusco
            { -12.8, -74.9 }, // Huancavelica
            { -9.9, -76.2 }, // Huánuco
            { -14.0, -75.7 }, // Ica
            { -11.0, -75.2 }, // Junín
            { -8.1, -79.0 }, // La Libertad
            { -6.7, -79.8 }, // Lambayeque
            { -12.0, -77.0 }, // Lima
            { -4.5, -74.0 }, // Loreto
            { -12.9, -69.2 }, // Madre de Dios
            { -17.2, -70.9 }, // Moquegua
            { -10.7, -76.2 }, // Pasco
            { -5.2, -80.6 }, // Piura
            { -15.8, -70.0 }, // Puno
            { -6.5, -76.5 }, // San Martin
            { -18.0, -70.2 }, // Tacna
            { -3.6, -80.5 }, // Tumbes
            { -8.3, -74.5 }  // Ucayali
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerDepartments = root.findViewById(R.id.recyclerDepartments);
        btnGetLocation = root.findViewById(R.id.btn_get_location);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        // Crear lista de departamentos
        listDepartments = Arrays.asList(
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

        adapter = new DepartmentAdapter(listDepartments, departmentName -> {
            Bundle args = new Bundle();
            args.putString("department", departmentName);
            Navigation.findNavController(root).navigate(R.id.action_homeFragment_to_catalogFragment, args);
        });

        recyclerDepartments.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerDepartments.setAdapter(adapter);

        checkPermissionsAndLocate();

        btnGetLocation.setOnClickListener(v -> requestLocationPermission());

        return root;
    }

    private void checkPermissionsAndLocate() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            btnGetLocation.setVisibility(View.GONE);
            getLastLocation();
        } else {
            btnGetLocation.setVisibility(View.VISIBLE);
        }
    }

    private void requestLocationPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERM_LOCATION);
    }

    private void getLastLocation() {
        LocationRequest request = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(0)
                .setFastestInterval(0)
                .setNumUpdates(1);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) return;

        fusedLocationClient.requestLocationUpdates(request, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult result) {
                fusedLocationClient.removeLocationUpdates(this);
                Location loc = result.getLastLocation();
                if (loc != null) highlightDepartment(loc);
            }
        }, getActivity().getMainLooper());
    }

    private void highlightDepartment(Location loc) {
        // Encuentra el departamento más cercano
        double minDistance = Double.MAX_VALUE;
        int closestIndex = 0;

        for (int i = 0; i < listDepartments.size(); i++) {
            double lat = departmentCoords[i][0];
            double lng = departmentCoords[i][1];
            float[] result = new float[1];
            Location.distanceBetween(loc.getLatitude(), loc.getLongitude(), lat, lng, result);
            if (result[0] < minDistance) {
                minDistance = result[0];
                closestIndex = i;
            }
        }

        for (int i = 0; i < listDepartments.size(); i++) {
            listDepartments.get(i).setHighlighted(i == closestIndex);
        }

        adapter.notifyDataSetChanged();

        // Redirigir al catálogo del departamento más cercano
        Bundle args = new Bundle();
        args.putString("department", listDepartments.get(closestIndex).getName());
        Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_catalogFragment, args);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQ_PERM_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btnGetLocation.setVisibility(View.GONE);
                getLastLocation();
            }
        }
    }
}
