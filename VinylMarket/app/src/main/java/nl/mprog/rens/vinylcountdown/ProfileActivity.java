package nl.mprog.rens.vinylcountdown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;import nl.mprog.com.example.rens.vinylmarket.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void goToWishlist(View view) {
        Intent goToWishlist = new Intent(this, WishlistActivity.class);
        startActivity(goToWishlist);
    }

    public void goToSettings(View view) {
        Intent goToSettings = new Intent(this, SettingsActivity.class);
        startActivity(goToSettings);
    }

    public void goToCollection(View view) {
        Intent goToCollection = new Intent(this, CollectionActivity.class);
        startActivity(goToCollection);
    }

    public void goToMenu(View view) {
        Intent goToMenu = new Intent(this, MainActivity.class);
        startActivity(goToMenu);
    }
}
