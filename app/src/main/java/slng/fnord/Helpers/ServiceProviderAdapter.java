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

import slng.fnord.R;

//this is a helper class to make sure the UI on the view services page displays properly - do not touch
public class ServiceProviderAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private int mResource;
    private ArrayList<String> objects;

    public ServiceProviderAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String[] stringArray = objects.get(position).split("#");
        String service = stringArray[0];
        String rating = stringArray[1];

        System.out.println(service);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView ServiceProviderCompany = convertView.findViewById(R.id.srv);
        TextView ServiceProviderRating = convertView.findViewById(R.id.rateField);

        ServiceProviderCompany.setText(service);
        ServiceProviderRating.setText("Average Rating:" + rating);
        return convertView;

    }
}
