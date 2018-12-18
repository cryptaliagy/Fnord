package slng.fnord.Helpers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import slng.fnord.Database.DBHelper;
import slng.fnord.Managers.ServicesManager;
import slng.fnord.R;

//this is a helper class to make sure the UI on the view services page displays properly - do not touch
public class ServicesAndRatesAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private int mResource;
    private ArrayList<String> objects;

    public ServicesAndRatesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ServicesManager manager = ServicesManager.getInstance();
        String service = objects.get(position);

        System.out.println(service);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvService = convertView.findViewById(R.id.srv);
        TextView tvRate = convertView.findViewById(R.id.rateField);

        tvService.setText(service);
        manager.getServiceRateForView(service, tvRate);
        return convertView;

    }
}
