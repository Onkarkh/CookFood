package phonetism.cookmyfood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class RecipeMenu extends Fragment {
    private ListView listView;

    private Button savedRecipes;

    private ArrayList<String> listTitle;

    private String keyword;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        listView = view.findViewById(R.id.lvmenu);
        savedRecipes = view.findViewById(R.id.savedRecipes);

        listTitle = new ArrayList<>();
        listTitle.add("Chicken");
        listTitle.add("Soup");

        savedRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                SavedRecipes savedRecipes = new SavedRecipes();
                fragmentManager.beginTransaction().replace(R.id.mainFragment,savedRecipes).commit();
            }
        });

        listView.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,listTitle));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();
                keyword = listTitle.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("value", keyword);
                RecipeLoader recipeLoader = new RecipeLoader();
                recipeLoader.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.mainFragment, recipeLoader).commit();
            }
        });


        return view;
    }
}