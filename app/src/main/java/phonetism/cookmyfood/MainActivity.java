package phonetism.cookmyfood;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMainMenu();
        toastMessage("Sorry!\n Currently the API has Shutdown!\n Hence the API can't fetch data from the Server.");

        MobileAds.initialize(this,"ca-app-pub-4159448769103503~9105503094");
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
    private void toastMessage(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_LONG).show();
    }

    private void loadMainMenu() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeMenu recipeMenu = new RecipeMenu();
        fragmentManager.beginTransaction().replace(R.id.mainFragment, recipeMenu).commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Select one of the Following");
        builder.setCancelable(true);

        builder.setNegativeButton(
                "Back",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        loadMainMenu();
                    }
                });

        builder.setPositiveButton(
                "Quit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
