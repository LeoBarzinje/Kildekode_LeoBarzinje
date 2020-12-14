package com.example.destinity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class SettingsFragment extends Fragment {
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_settings, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button knap = (Button)view.findViewById(R.id.knapp);

//viser informasjonen til brukeren
        knap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                TextView editAlderText = getView().findViewById(R.id.editAlder);
                TextView editBoSted = getView().findViewById(R.id.editBosted);
                TextView editInformasjon = getView().findViewById(R.id.editInfo);
                TextView user_alder = getView().findViewById(R.id.alder_user);
                TextView user_bosted = getView().findViewById(R.id.bosted_user);
                TextView user_description =getView().findViewById(R.id.description_user);
                Button godtaKnappen = getView().findViewById(R.id.godtaEditInfo);
                Button cancelKnappen = getView().findViewById(R.id.kanselerEditInformasjon);
                godtaKnappen.setVisibility(View.GONE);
                cancelKnappen.setVisibility(View.GONE);
                TextView bald_alder = getView().findViewById(R.id.alder_bald);
                TextView bald_bosted = getView().findViewById(R.id.bosted_bald);
                TextView description_bald = getView().findViewById(R.id.description_bald);
                bald_alder.setText("Alder: ");
                bald_bosted.setText("Bosted: ");
                description_bald.setText("Litt om deg selv: ");
                user_alder.setText(editAlderText.getText().toString());
                user_bosted.setText(editBoSted.getText().toString());
                user_description.setText(editInformasjon.getText().toString());



                TextView brkNavn = getView().findViewById(R.id.brukerNavn);
                Button hide = getView().findViewById(R.id.hideText);
                hide.setVisibility(View.VISIBLE);
                Button showInfo =getView().findViewById(R.id.knapp);
                showInfo.setVisibility(View.GONE);

                godtaKnappen.setVisibility(View.GONE);

                cancelKnappen.setVisibility(View.GONE);





            }
        });
        Button changeUserInfo = (Button) view.findViewById(R.id.Editinfo);

        //show info
        changeUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView editAlderText = getView().findViewById(R.id.editAlder);
                TextView editBoSted = getView().findViewById(R.id.editBosted);
                TextView editInformasjon = getView().findViewById(R.id.editInfo);
                TextView user_alder = getView().findViewById(R.id.alder_user);
                TextView user_bosted = getView().findViewById(R.id.bosted_user);
                TextView  user_description=getView().findViewById(R.id.description_user);
                TextView  bald_alder=getView().findViewById(R.id.alder_bald);
                TextView  bald_bosted= getView().findViewById(R.id.bosted_bald);
                TextView  description_bald= getView().findViewById(R.id.description_bald);
                bald_alder.setText("Alder: ");
                bald_bosted.setText("Bosted: ");
                description_bald.setText("Litt om deg selv: ");


                editAlderText.setVisibility(View.VISIBLE);
                editBoSted.setVisibility(View.VISIBLE);
                editInformasjon.setVisibility(View.VISIBLE);

                user_alder.setVisibility(View.GONE);
                user_alder.setVisibility(View.GONE);
                user_description.setVisibility(View.GONE);

                Button godtaKnappen = getView().findViewById(R.id.godtaEditInfo);
                godtaKnappen.setVisibility(View.VISIBLE);
                Button cancelKnappen = getView().findViewById(R.id.kanselerEditInformasjon);
                cancelKnappen.setVisibility(View.VISIBLE);
            }

        });


        Button kanseller = (Button) view.findViewById(R.id.kanselerEditInformasjon);

        //show info
        kanseller.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
        TextView editAlderText = getView().findViewById(R.id.editAlder);
        TextView editBoSted = getView().findViewById(R.id.editBosted);
        TextView editInformasjon = getView().findViewById(R.id.editInfo);
        TextView user_alder = getView().findViewById(R.id.alder_user);
        TextView  user_description= getView().findViewById(R.id.description_user);
       // showInfo(view);


        editAlderText.setVisibility(View.GONE);
        editBoSted.setVisibility(View.GONE);
        editInformasjon.setVisibility(View.GONE);

        user_alder.setVisibility(View.GONE);
        user_alder.setVisibility(View.GONE);
        user_description.setVisibility(View.GONE);

        Button godtaKnappen = getView().findViewById(R.id.godtaEditInfo);
        godtaKnappen.setVisibility(View.GONE);
        Button cancelKnappen = getView().findViewById(R.id.kanselerEditInformasjon);
        cancelKnappen.setVisibility(View.GONE);
    }

});

        Button godtaEditInformasjon = (Button) view.findViewById(R.id.godtaEditInfo);

        //show info
        godtaEditInformasjon.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
        TextView editAlderText = getView().findViewById(R.id.editAlder);
        TextView editBoSted = getView().findViewById(R.id.editBosted);
        TextView editInformasjon = getView().findViewById(R.id.editInfo);
        TextView user_alder = getView().findViewById(R.id.alder_user);
        TextView  user_description= getView().findViewById(R.id.description_user);
        TextView user_bosted = getView().findViewById(R.id.bosted_user);
        editAlderText.setVisibility(View.GONE);
        editBoSted.setVisibility(View.GONE);
        editInformasjon.setVisibility(View.GONE);

        user_alder.setVisibility(View.VISIBLE);
        user_bosted.setVisibility(View.VISIBLE);
        user_description.setVisibility(View.VISIBLE);

        Button godtaKnappen = getView().findViewById(R.id.godtaEditInfo);
        godtaKnappen.setVisibility(View.GONE);
        Button cancelKnappen = getView().findViewById(R.id.kanselerEditInformasjon);
        cancelKnappen.setVisibility(View.GONE);

        user_alder.setText(editAlderText.getText().toString());
        user_bosted.setText(editBoSted.getText().toString());
        user_description.setText(editInformasjon.getText().toString());
    }}
    );


        //skjuler info
        Button skjulInfo = getView().findViewById(R.id.hideText);
        skjulInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView editAlderText = getView().findViewById(R.id.editAlder);
                TextView editBoSted = getView().findViewById(R.id.editBosted);
                TextView editInformasjon = getView().findViewById(R.id.editInfo);
                TextView user_alder = getView().findViewById(R.id.alder_user);
                TextView user_bosted = getView().findViewById(R.id.bosted_user);
                TextView  user_description= getView().findViewById(R.id.description_user);
                TextView  bald_alder= getView().findViewById(R.id.alder_bald);
                TextView  bald_bosted= getView().findViewById(R.id.bosted_bald);
                TextView  description_bald= getView().findViewById(R.id.description_bald);
                bald_alder.setText(null);
                bald_bosted.setText(null);
                description_bald.setText(null);

                user_alder.setText(editAlderText.getText().toString());
                user_bosted.setText(editBoSted.getText().toString());
                user_description.setText(editInformasjon.getText().toString());
                user_alder.setVisibility(View.GONE);
                user_description.setVisibility(View.GONE);
                user_bosted.setVisibility(View.GONE);
                Button skjul = getView().findViewById(R.id.hideText);
                skjul.setVisibility(View.GONE);
                Button visInformasjon = getView().findViewById(R.id.knapp);
                visInformasjon.setVisibility(View.VISIBLE);
            }
        });

        Button logOutButton = getView().findViewById(R.id.logOut);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(getApplicationContext()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Signed out ", Toast.LENGTH_LONG);
                    }
                });
            }
        });

    }};

