package slng.fnord.Activities.HomeOwner;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

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
    private ServiceProvider selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosearch_results);
        createResults();
        ListView lv = findViewById(R.id.searchResultsList);
        ServiceProviderAdapter adapter = new ServiceProviderAdapter(this, R.layout.adapter_view_layout,
               results);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener((parent, view, position, id) -> {
            String[] node = results.get(position).split("#");
            String companyName = node[0];
            for (String company: goodProviders.keySet()){
                if (company.equals(companyName)) {
                    managerAcc.getUser(goodProviders.get(company).getEmail(), this::setProvider);
                    selection = provider;
                }
            }

            Intent intent = new Intent(this, slng.fnord.Activities.HomeOwner.ServiceProfile.class);
            this.startActivity(intent);



        });

    }

    private void createResults(){
        String oneItem;
        goodProviders = SearchProvider.getGoodProviders();
        for(String company : goodProviders.keySet()){
            managerAcc.getUser(goodProviders.get(company).getEmail(), this::setProvider);
            oneItem = company + "#" + provider.getAverageRating();
            results.add(oneItem);
        }
    }


    private void setProvider(User user) {
        provider = (ServiceProvider) user;
    }


    public ArrayList<String> getResults() {
        return results;
    }
}
