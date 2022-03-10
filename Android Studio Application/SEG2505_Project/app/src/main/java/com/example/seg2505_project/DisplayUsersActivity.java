package com.example.seg2505_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayUsersActivity extends AppCompatActivity {
    //instance variables
    ListView userList;
    DatabaseReference mref;
    DatabaseReference iref;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_users);
        //initialise instance variables
        userList = (ListView) findViewById(R.id.userList);
        users = new ArrayList<>();
        mref = FirebaseDatabase.getInstance().getReference("users");
        //When one long clicks on a user it comes into this method
        userList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = users.get(i);//gets the instance of user clicked on
                showUpdateDeleteDialog(user);//shows popup to update or delete user
                return true;
            }
        });
    }

    public void showUpdateDeleteDialog(final User user){
        //opens the popup on the screen
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayUsersActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_user_dialog, null);
        dialogBuilder.setView(dialogView);
        //the buttons delete and canceld
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUser);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancelUser);

        dialogBuilder.setTitle("Deleting User");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        //onclick listener for cancel
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
        //onclick listener for delete
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(user.getId());//removes user from database
                b.dismiss();
            }
        });
    }

    private void deleteUser(String id){
        removeServices(id);
        DatabaseReference jref = FirebaseDatabase.getInstance().getReference("users").child(id);//gets the reference of the user in the database using his id
        jref.removeValue();//removes from database
        Toast.makeText(this, "User Deleted", Toast.LENGTH_LONG).show();
    }

    public void removeServices(final String id){
        iref = FirebaseDatabase.getInstance().getReference("services/");
        iref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) { //look though database
                    Service service = postSnapshot.getValue(Service.class);
                    ArrayList<String> clinicsInServices = (ArrayList<String>) service.getClinics();
                    clinicsInServices.remove(id);
                    service.setClinics(clinicsInServices);
                    FirebaseDatabase.getInstance().getReference("services/"+service.getId()).setValue(service);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        //loops through all users in the database and add them to the arraylist of users
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user  = postSnapshot.getValue(User.class);
                    if(!user.getRole().equals("Admin")){//if user is an admin we dont add it to list as they shouldnt be able to delete themselves or other admins
                        users.add(user);
                    }

                }
                UserListAdapter serviceAdapter = new UserListAdapter(DisplayUsersActivity.this, users);//creates adapter for UserListAdapter view
                userList.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
