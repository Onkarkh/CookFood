package phonetism.cookmyfood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMainMenu();
        toastMessage("Long Press the Recipe to Save!");

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
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
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
