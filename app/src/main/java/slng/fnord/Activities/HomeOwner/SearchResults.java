package slng.fnord.Activities.HomeOwner;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosearch_results);
        createResults();
        ListView lv = findViewById(R.id.searchResultsList);
        ServiceProviderAdapter adapter = new ServiceProviderAdapter(this, R.layout.adapter_view_layout,
               results);
        lv.setAdapter(adapter);

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


    /**

     //lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
     lv.setOnItemClickListener((parent, view, position, id) -> {
     String serviceName = services.get(position);
     boolean certified = user.isCertified(serviceName);
     System.out.println(certified);
     AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
     dialogBuilder.setTitle(serviceName)
     //.setMessage(services.get(position))
     .setMultiChoiceItems(new CharSequence[]{"Certified"}, new boolean[]{certified},
     (dialog, which, isChecked) -> user.updateCertified(serviceName, isChecked))
     .setPositiveButton("Done", (dialog, buttonID) -> {
     DBHelper.updateUser(user);
     dialog.dismiss();
     })
     .setNegativeButton("Remove", (dialog, buttonID) -> {
     user.removeService(serviceName);
     DBHelper.updateUser(user);
     adapter.remove(serviceName);
     dialog.dismiss();
     });
     AlertDialog dialog = dialogBuilder.create();
     dialog.show();
     });
     }**/

    public ArrayList<String> getResults() {
        return results;
    }
}
