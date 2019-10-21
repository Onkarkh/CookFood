package phonetism.cookmyfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeLoader extends Fragment {
    private GridView gridView;
    private Button btnMainMenu;

    public DatabaseHelper databaseHelper;

    private String getTitle;
    private String getImg;
    private String getUrl;
    private String getSelectUrl;

    private String dbTitle;
    private String dbImage;
    private String dbUrl;

    private ArrayList<String> listTitle;
    private ArrayList<String> listImg;
    private ArrayList<String> listUrl;

    private TextView displayText;
    private ImageView displayImg;
    private String imgPos;

    private String keyword;

    private AdView adView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        gridView = view.findViewById(R.id.fragmentGridView);
        btnMainMenu = view.findViewById(R.id.mainMenu);
        MobileAds.initialize(getActivity(), "ca-app-pub-3940256099942544~3347511713");

        adView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        databaseHelper = new DatabaseHelper(getActivity());


        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                RecipeMenu recipeMenu = new RecipeMenu();
                fragmentManager.beginTransaction().replace(R.id.mainFragment, recipeMenu).commit();
            }
        });


        Bundle bundle = getArguments();
        keyword = bundle.getString("value");

        GetFoodRecipes getFoodRecipes = new GetFoodRecipes();
        getFoodRecipes.execute();
        return view;
    }

public class GetFoodRecipes extends AsyncTask<Void, Void, Void> {
    private ProgressDialog dialog = new ProgressDialog(getActivity());

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Loading...");
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ProtocolHandler protocolHandler = new ProtocolHandler();
        /* Visit Food2Fork.com and Generate an API and place it in the URL below. Replace the word KEY with your API key. */
        String url = "https://www.food2fork.com/api/search?key=1810c4b5fdadb943713cacc120040d4d&q=" + keyword + "&page=2";
        String jsonData = protocolHandler.makeServiceCall(url);

        if (jsonData != null) {
            try {
                listTitle = new ArrayList<>();
                listImg = new ArrayList<>();
                listUrl = new ArrayList<>();

                JSONObject jsonObject = new JSONObject(jsonData);
                JSONArray recipes = jsonObject.getJSONArray("recipes");

                for (int i = 0; i < recipes.length(); i++) {
                    JSONObject getRecipes = recipes.getJSONObject(i);

                    getTitle = getRecipes.getString("title");
                    getImg = getRecipes.getString("image_url");
                    getUrl = getRecipes.getString("source_url");

                    listTitle.add(getTitle);
                    listImg.add(getImg);
                    listUrl.add(getUrl);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        SetData setData = new SetData();
        gridView.setAdapter(setData);
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
                dbTitle = listTitle.get(position);
                dbImage = listImg.get(position);
                dbUrl = listUrl.get(position);

                boolean insertData = databaseHelper.addData(dbTitle, dbImage, dbUrl);
                if (insertData) {
                    toastMessage("Recipe Saved!");
                } else {
                    toastMessage("Error Occurred!");
                }
                return true;
            }
        });
    }
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
        imgPos = listImg.get(position);
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

