package com.example.seg2505_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class RatingListAdapter extends ArrayAdapter<Employee> {
    private Activity context;
    List<Employee> employees;
    Double rating;



    public RatingListAdapter(Activity context, List<Employee> employees){
        super(context, R.layout.activity_rating_list, employees);
        this.context = context;
        this.employees = employees;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_rating_list, null,true);

        TextView userName = (TextView) listViewItem.findViewById(R.id.textViewClinicName); //used show username (main Item)
        TextView userRole = (TextView) listViewItem.findViewById(R.id.textViewClinicRating); //used show role (sub item)
        rating = 0.0;
        Employee employee = employees.get(position);
        userName.setText(employee.getClinicName()); //set mainItem to firstName and lastName of user

        if(employee.getRatings() == null || employee.getRatings().size()==0) {
            userRole.setText("Not yet rated"); // set sub item in list view to the role of the user
        }
        else{
            for(int i=0; i<employee.getRatings().size();i++){
                rating = rating + employee.getRatings().get(i).getRating();
            }
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            userRole.setText(""+df.format(rating/employee.getRatings().size())); // set sub item in list view to the role of the user
        }
        return listViewItem;
    }
}
