package com.example.helpets.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.helpets.R;
import com.example.helpets.viewmodel.ViewModelAdoptar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFormularioAdopcion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFormularioAdopcion extends Fragment {

    private ViewModelAdoptar viewModelAdoptar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_formulario_adopcion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelAdoptar = new ViewModelProvider(getActivity()).get(ViewModelAdoptar.class);

    }
}
