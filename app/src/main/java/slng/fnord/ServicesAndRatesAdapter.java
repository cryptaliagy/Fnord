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
        Services ser = MainActivity.getServices();
        String service = objects.get(position);
        Double  rate = ser.getServiceRate(Common.makeMD5(service));

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvService = (TextView) convertView.findViewById(R.id.srv);
        TextView tvRate = (TextView) convertView.findViewById(R.id.rateField);

        tvService.setText(service);
        tvRate.setText(String.format("%.2f", rate));
        return convertView;

    }
}
