package phonetism.cookmyfood;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class SavedRecipes extends Fragment {
    private static final String TAG = "SavedRecipes";

    private GridView gridView;
    private Button btnMainMenu;

    private ArrayList<String> listTitle;
    private ArrayList<String> listImage;
    private ArrayList<String> listUrl;

    private TextView displayText;
    private ImageView displayImg;

    private String imgPos;
    private String getSelectUrl;
    private String getSelectTitle;

    private AdView adView;

    public DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        gridView = view.findViewById(R.id.fragmentGridView);
        btnMainMenu = view.findViewById(R.id.mainMenu);
        MobileAds.initialize(getActivity(),"ca-app-pub-4159448769103503~9105503094");

        adView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        databaseHelper = new DatabaseHelper(getActivity());

        getSqliteData();

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                RecipeMenu recipeMenu = new RecipeMenu();
                fragmentManager.beginTransaction().replace(R.id.mainFragment, recipeMenu).commit();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getSelectUrl = listUrl.get(position);
                Intent intent = new Intent(getActivity().getBaseContext(), WebViewer.class);
                intent.putExtra("link", getSelectUrl);
                getActivity().startActivity(intent);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                getSelectTitle = listTitle.get(position);
                databaseHelper.deleteRecipe(getSelectTitle);
                toastMessage("Recipe Deleted!");
                reloadFragment();
                return true;
            }
        });

        return view;
    }

    private void reloadFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        SavedRecipes savedRecipes = new SavedRecipes();
        fragmentManager.beginTransaction().replace(R.id.mainFragment, savedRecipes).commit();
    }

    public void getSqliteData() {
        Cursor data = databaseHelper.getData();

        listTitle = new ArrayList<>();
        listImage = new ArrayList<>();
        listUrl = new ArrayList<>();

        while (data.moveToNext()) {
            listTitle.add(data.getString(1));
            listImage.add(data.getString(2));
            listUrl.add(data.getString(3));
        }

        gridView.setAdapter(new SetData());
    }

    public class SetData extends BaseAdapter {

        @Override
        public int getCount() {
            return listTitle.size();
        }

        @Override
        public Object getItem(int position) {
            return listTitle.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View viewGrid = layoutInflater.inflate(R.layout.fragment_recipe_layout, null);

            displayImg = viewGrid.findViewById(R.id.img);
            imgPos = listImage.get(position);
            Glide.with(getContext()).load(imgPos).into(displayImg);

            displayText = viewGrid.findViewById(R.id.text);
            displayText.setText(listTitle.get(position));
            return viewGrid;
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
    }
}
