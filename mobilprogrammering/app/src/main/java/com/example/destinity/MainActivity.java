package com.example.destinity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private  final int RC_SIGN_IN= 1;


RecyclerView.LayoutManager layoutManager;
    private Uri imgUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseFirestore firestoreDb = FirebaseFirestore.getInstance();
    private CollectionReference brukerCollectionReference;
    private List<Users> brukerListe;
    private List<String> brukerUidList;
    private ListenerRegistration firestoreListenerRegistration;
    public ProgramAdapter programAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        brukerListe= new ArrayList<>();
        brukerUidList = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavController controller = Navigation.findNavController(this, R.id.fragment2);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNav);
        NavigationUI.setupWithNavController(bottomNavigation, controller);
        controller.navigate(R.id.mainFragmentOne);

        storage =FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firestoreDb = FirebaseFirestore.getInstance();

            //firebase Collection "users"
        brukerCollectionReference = firestoreDb.collection("users");
        generetestData();

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = auth.getCurrentUser();

                if (currentUser == null){
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),

                            new AuthUI.IdpConfig.GoogleBuilder().build());
                // Create and launch sign-in intent
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                }
                else {
                    Toast.makeText(getApplicationContext(), "signed in as " + currentUser.getDisplayName(), Toast.LENGTH_LONG).show();


                }
            }

        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != RC_SIGN_IN)
        return;
           // IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser currentUser = auth.getCurrentUser();
                Toast.makeText(getApplicationContext(), "signed in as " + currentUser.getDisplayName(), Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(), "signed in cancelled ", Toast.LENGTH_LONG);
            }
        ImageView imgView= findViewById(R.id.profile_image);

                if(requestCode == 1 && resultCode ==RESULT_OK && data != null && data.getData()!=null){
                    imgUri = data.getData();
                    imgView.setImageURI(imgUri);
                    uploadPicture();
                }
    }

//-------------laster opp bildet til firebase
    private void uploadPicture() {
        final ProgressDialog pd= new ProgressDialog(this);
        pd.setTitle("uploading image...");
        pd.show();

      final String randomKey =UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/"+ randomKey);

        riversRef.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                       Snackbar.make(findViewById(R.id.settingsFragment), "Bildet ble lastet opp", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Bildet kunne ikke bli lastet opp ", Toast.LENGTH_LONG);
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Progress: " +(int) progressPercent+ "%");
            }
        });
    }


    @Override
    protected void onResume() {
            super.onResume();
            CreateFireStoreREadListener();
            auth.addAuthStateListener(authStateListener);

    }

    private void CreateFireStoreREadListener() {

        firestoreListenerRegistration = brukerCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if( error!= null){
                        System.out.println("feil");
                        return;
                    }

                        for (DocumentChange documentChange : value.getDocumentChanges()){
                            QueryDocumentSnapshot documentSnapshot = documentChange.getDocument();
                            Users user = documentSnapshot.toObject(Users.class);
                            user.setId(documentSnapshot.getId());
                            int pos = brukerListe.indexOf(user.getId());
                            switch (documentChange.getType()){
                                case ADDED:
                                    try {
                                        brukerListe.add(user);
                                        brukerUidList.add(user.getId());
                                        programAdapter.notifyItemInserted(brukerListe.size()-1);
                                        programAdapter.notifyDataSetChanged();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case REMOVED:
                                    try {
                                        brukerListe.remove(pos);
                                        brukerUidList.remove(pos);
                                        programAdapter.notifyItemRemoved(pos);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case MODIFIED:
                                    try {
                                        brukerListe.set(pos, user);
                                        programAdapter.notifyItemChanged(pos);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;

                            }


                        }

                }
            });

    }

    @Override
    protected void onPause() {
        super.onPause();
        auth.removeAuthStateListener(authStateListener);
        if(firestoreListenerRegistration != null ){
            firestoreListenerRegistration.remove();
        }
    }

  public void changeUserProfilePicture(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

        }

//når brukeren trykker på knapp A
public void knappATrykkerpo(View view) {
        programAdapter = new ProgramAdapter(this, brukerListe);
        TextView dagensSpors = (TextView) findViewById(R.id.dagspm);
        dagensSpors.setVisibility(View.GONE);
        Button removeButtonA  = findViewById(R.id.knappA);
        Button removeButtonB  = findViewById(R.id.knappB);
        removeButtonA.setVisibility(View.GONE);
        removeButtonB.setVisibility(View.GONE);

        TextView questionDay = findViewById(R.id.questionRemove);
        questionDay.setVisibility(View.GONE);

        RecyclerView recyclerView = findViewById(R.id.recView);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(programAdapter);
    }
//onclick listener for knappen "chat" i RecycleViewet
    public void gotoChatFragment(View view) {
        switchToFragment1();
    }
    //metode for å gå til ChatFragmentet
    public void switchToFragment1() {
        NavController controller = Navigation.findNavController(this, R.id.fragment2);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNav);
        NavigationUI.setupWithNavController(bottomNavigation, controller);
        controller.navigate(R.id.chatFragment);

    }

    //når knapp b på mainFragment trykkes på
    public void knappBtrykkerpo(View view) {
        TextView dagensSpors = (TextView) findViewById(R.id.dagspm);
        dagensSpors.setVisibility(View.GONE);
        Button removeButtonA  = findViewById(R.id.knappA);
        Button removeButtonB  = findViewById(R.id.knappB);
        removeButtonA.setVisibility(View.GONE);
        removeButtonB.setVisibility(View.GONE);

        TextView questionDay = findViewById(R.id.questionRemove);
        questionDay.setVisibility(View.GONE);

        RecyclerView recyclerView = findViewById(R.id.recView);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(programAdapter);
    }



    private void  generetestData(){
        ArrayList<Users> users = new ArrayList<>();

        for(Users auser : users){
            brukerCollectionReference.add(auser);
        }
    }
}
