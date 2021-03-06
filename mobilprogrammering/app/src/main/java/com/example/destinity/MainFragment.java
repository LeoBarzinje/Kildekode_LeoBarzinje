package com.example.destinity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

public class MainFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgramAdapter programAdapter;
    private List<String> brukerUidList;
    private List<Users> brukerListe;
    Button removeButtonA, removeButtonB;
    TextView questionDay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, null);


        return inflater.inflate(R.layout.fragment_main, container, false);


    }

    }

