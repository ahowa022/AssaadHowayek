package com.example.seg2505_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ServiceListAdapter extends ArrayAdapter<Service> {
    private Activity context;
    List<Service> services;

    public ServiceListAdapter(Activity context, List<Service> services){
        super(context, R.layout.activity_service_list, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_service_list, null,true);

        TextView serviceName = (TextView) listViewItem.findViewById(R.id.textViewName);// saves an variable for the TextView of the service name in activity_service_list
        TextView serviceRole = (TextView) listViewItem.findViewById(R.id.textViewRole);// saves an variable for the TextView of the service name in activity_service_list

        Service service = services.get(position);
        serviceName.setText(service.getName());//set mainItem to name of the service
        serviceRole.setText(service.getRole());// set sub item in list view to the role of the usr
        return listViewItem;
    }
}
