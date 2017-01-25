package nl.mprog.rens.vinylcountdown;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    // Authenticator:
    // The authenticator for firebase.
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    // Initiate drawer modules:
    DrawerLayout drawerLayout;
    ListView drawers;
    String[] navigations;
    Button menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get the user and his/her status:
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get the menubutton.
        menuButton = (Button) findViewById(R.id.profileButton);

        // Check the login state.
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user is logged in!

                } else {
                    // User is signed out, they don't belong here so send them back!
                    goToLogin();
                }
            }
        };

        // Get the users data:
        DatabaseReference mSettingsReference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        if (user != null) {
            // User is signed in
            ValueEventListener settingsListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get UserSettings object and use the values to update the UI
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    if (userProfile != null) {
                        TextView userNameTV = (TextView) findViewById(R.id.profileUserEdit);
                        TextView emailTV = (TextView) findViewById(R.id.profileEmailEdit);
                        userNameTV.setText(userProfile.getUsername());
                        emailTV.setText(userProfile.getEmail());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mSettingsReference.addValueEventListener(settingsListener);
        }

        // Navigation drawer from: https://developer.android.com/training/implementing-navigation/nav-drawer.html#Init
        navigations = getResources().getStringArray(R.array.menuOptions);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawers = (ListView) findViewById(R.id.profile_drawer);

        // Set the adapter for the list view
        drawers.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_item, navigations));
        // Set the list's click listener
        drawers.setOnItemClickListener(new ProfileActivity.DrawerItemClickListener());
    }

    // Redirects the user to their profile.
    public void goToProfile() {
        Intent goToProfile = new Intent(this, ProfileActivity.class);
        startActivity(goToProfile);
        finish();
    }

    // Redirects the user to where they can search the marketplace.
    public void goToMarketSearch() {
        Intent goToMarketSearch = new Intent(this, SaleSearchActivity.class);
        goToMarketSearch.putExtra("method", "buySearch");
        startActivity(goToMarketSearch);
        finish();
    }

    // Redirects the user to where they can search possible records they can sell.
    public void goToSaleSearch() {

        Intent goToSaleSearch = new Intent(this, SaleSearchActivity.class);
        goToSaleSearch.putExtra("method", "saleSearch");
        startActivity(goToSaleSearch);
        finish();
    }

    // Redirects the user to the login screen and logs them out.
    public void goToLogin() {
        Intent goToLogin = new Intent(this, LoginActivity.class);
        mAuth.getCurrentUser();
        mAuth.signOut();
        startActivity(goToLogin);
        finish();
    }

    public void openDrawer(View view) {

        if(drawerLayout.isDrawerOpen(drawers)){
            drawerLayout.closeDrawer(drawers);
            menuButton.setText("+");
        }
        else {
            drawerLayout.openDrawer(drawers);
            menuButton.setText("-");
        }
    }

    // onclicklistener for the drawer, manages navigation.
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Get the right position and go to the right activity.
        String[] menuOptions = getResources().getStringArray(R.array.menuOptions);
        switch (menuOptions[position]){
            case ("Menu"):
                break;
            case ("Sell"):
                goToSaleSearch();
                break;
            case ("Buy"):
                goToMarketSearch();
                break;
            case ("Profile"):
                goToProfile();
                break;
            case ("Logout"):
                goToLogin();
                break;
        }
        drawerLayout.closeDrawer(drawers);
        menuButton.setText("+");
    }


}
