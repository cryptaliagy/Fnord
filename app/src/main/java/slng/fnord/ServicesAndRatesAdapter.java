package slng.fnord;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//this is a helper class to make sure the UI on the view services page displays properly - do not touch
public class ServicesAndRatesAdapter extends ArrayAdapter<String> {
    private Context mContext;
    int mResource;

    public ServicesAndRatesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String service = MainActivity.ser.getServices().get(position);
        String rate = MainActivity.ser.getServiceRates().get(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvService = (TextView) convertView.findViewById(R.id.srv);
        TextView tvRate = (TextView) convertView.findViewById(R.id.rateField);

        tvService.setText(service);
        tvRate.setText(rate);
        return convertView;

    }
}
