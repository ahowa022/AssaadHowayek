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

public class UserListAdapter extends ArrayAdapter<User> {
    private Activity context;
    List<User> users;

    public UserListAdapter(Activity context, List<User> users){
        super(context, R.layout.activity_user_list, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_user_list, null,true);

        TextView userName = (TextView) listViewItem.findViewById(R.id.userName); //used show username (main Item)
        TextView userRole = (TextView) listViewItem.findViewById(R.id.userRole); //used show role (sub item)

        User user = users.get(position);
        userName.setText(user.getFirstName()+" " +user.getLastName()); //set mainItem to firstName and lastName of user
        userRole.setText(user.getRole()); // set sub item in list view to the role of the user
        return listViewItem;
    }
}
