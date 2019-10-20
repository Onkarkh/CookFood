package phonetism.cookmyfood;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
        listTitle.add("Pizza");
        listTitle.add("Burger");
        listTitle.add("Dessert");
        listTitle.add("Veg");
        listTitle.add("Roll");
        listTitle.add("Pasta");
        listTitle.add("Sandwich");
        listTitle.add("Salad");
        listTitle.add("Pie");
        listTitle.add("Pork");
        listTitle.add("Ice");
        listTitle.add("Potatoes");
        listTitle.add("Coffee");
        listTitle.add("Cheese");
        listTitle.add("Cake");
        listTitle.add("Brownie");
        listTitle.add("Buffalo");
        listTitle.add("Italian");
        listTitle.add("Bread");
        listTitle.add("Mushroom");
        listTitle.add("Avocado");
        listTitle.add("Appetizers");
        listTitle.add("Bacon");
        listTitle.add("Rice");
        listTitle.add("Muffins");
        listTitle.add("Chocolate");
        listTitle.add("Yogurt");
        listTitle.add("Cookie");
        listTitle.add("Beans");
        listTitle.add("Dumplings");
        listTitle.add("Butter");
        listTitle.add("Egg");
        listTitle.add("Wrap");
        listTitle.add("Fruit");
        listTitle.add("Milk");
        listTitle.add("Vodka");


        savedRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                SavedRecipes savedRecipes = new SavedRecipes();
                fragmentManager.beginTransaction().replace(R.id.mainFragment,savedRecipes).commit();
            }
        });


        listView.setAdapter(new CustomListMenu());


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

    public class CustomListMenu extends BaseAdapter {

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
            TextView textView;
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.fragment_custom_listmenu,null);

            textView = view.findViewById(R.id.itemlist);
            textView.setText(listTitle.get(position));
            return view;
        }
    }
}
