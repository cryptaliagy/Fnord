package slng.fnord.Activities.HomeOwner;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AlertDialog;
import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.ServiceProviderAdapter;
import slng.fnord.Helpers.ServicesAndRatesAdapter;
import slng.fnord.Managers.AccountManager;
import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;
import slng.fnord.Structures.ServiceProviderMeta;
import slng.fnord.Structures.User;

public class SearchResults extends Activity {

    private AccountManager managerAcc;
    private HashMap<String, ServiceProviderMeta> goodProviders;
    private ArrayList<String> results;
    private ServiceProvider provider;
    private static ServiceProvider selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosearch_results);
        createResults();

    }

    private void buildActivity() {
        ListView lv = findViewById(R.id.searchResultsList);
        ServiceProviderAdapter adapter = new ServiceProviderAdapter(this, R.layout.adapter_view_layout,
                results);
        lv.setAdapter(adapter);

        managerAcc = new AccountManager(new DBHelper());


        lv.setOnItemClickListener((parent, view, position, id) -> {
            String[] node = results.get(position).split("#");
            String companyName = node[0];

            System.out.println(goodProviders.get(companyName).getEmail());
            managerAcc.getUser(goodProviders.get(companyName).getEmail(), this::makeSelection);
        });

    }

    private void createResults(){
        results = new ArrayList<>();
        goodProviders = SearchProvider.getGoodProviders();
        for(String company : goodProviders.keySet()){
            results.add(company + "#" + String.format("%.2f", goodProviders.get(company).getAverageRating()));
        }

        buildActivity();
    }

    private void makeSelection(User user) {
        if (user == null) {
            Toast.makeText(getApplicationContext(), "Null user", Toast.LENGTH_SHORT).show();
            return;
        }
        ServiceProfile.provider = (ServiceProvider) user;
        Intent intent = new Intent(this, ServiceProfile.class);
        this.startActivity(intent);

    }


    public static ServiceProvider getSelection() {
        return selection;
    }

    public ArrayList<String> getResults() {
        return results;
    }
}
