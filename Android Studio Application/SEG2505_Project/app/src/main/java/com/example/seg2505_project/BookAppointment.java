package com.example.seg2505_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Clock;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class BookAppointment extends AppCompatActivity {
    TextView monday;
    TextView tuesday;
    TextView wednesday;
    TextView thursday;
    TextView friday;
    TextView saturday;
    TextView sunday;
    DatabaseReference clinicRef;
    DatabaseReference patientRef;
    private int timeDelayed;
    DatabaseReference bookingsRef;
    Patient patient;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    String clinicId;
    Employee employee;
    Spinner day;
    String dayChosen;
    Button book;
    TextView date;
    TextView errorOutput;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        Bundle extras = getIntent().getExtras();
        clinicId = extras.getString("id");

        title = findViewById(R.id.appointmentTitle);
        monday = findViewById(R.id.mondayTime);
        tuesday = findViewById(R.id.tuesdayTime);
        wednesday = findViewById(R.id.wednesdayTime);
        thursday = findViewById(R.id.thursdayTime);
        friday = findViewById(R.id.fridayTime);
        saturday = findViewById(R.id.saturdayTime);
        sunday = findViewById(R.id.sundayTime);
        day = (Spinner) findViewById(R.id.daySpinner);
        book = findViewById(R.id.bookAppointment);
        date =findViewById(R.id.dateInput);
        clinicRef = FirebaseDatabase.getInstance().getReference("users/"+clinicId);
        errorOutput = findViewById(R.id.errorOutput);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        patientRef = FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid());
        bookingsRef = FirebaseDatabase.getInstance().getReference("users");


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adapter);


        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patient = dataSnapshot.getValue(Patient.class);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        clinicRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employee = dataSnapshot.getValue(Employee.class);
                setAvailabilities();
                title.setText(employee.getClinicName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayChosen = parent.getItemAtPosition(position).toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateDay() && validateDate()){
                    bookingsRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            timeDelayed = 0;
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) { //look though database
                                User user = postSnapshot.getValue(User.class);
                                if(user.getRole().equals("Patient")){
                                    Patient patient1 =  postSnapshot.getValue(Patient.class);
                                    List<Booking> bookings = patient1.getBookings();
                                    if(!(bookings==null)){
                                        for(int i =0; i<bookings.size(); i++){
                                            Booking book = bookings.get(i);
                                            if(book.getDay().equals(date.getText().toString()) && book.getId().equals(employee.getId())){
                                                timeDelayed+=15;
                                            }

                                        }
                                    }

                                }

                            }
                            showBookingDialog();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });


                }
            }
        });
    }

    public void showBookingDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.confirm_appointment_dialog, null);
        dialogBuilder.setView(dialogView);
        final TextView expectedTime = dialogView.findViewById(R.id.waitTime);
        final Button confirmBooking = (Button) dialogView.findViewById(R.id.confirmAppointment);
        final Button cancel = (Button) dialogView.findViewById(R.id.cancelAppointment);
        expectedTime.setText(""+timeDelayed + " mins");
        dialogBuilder.setTitle("Booking Confirmation");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        confirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Booking booking = new Booking(date.getText().toString(), employee.getId());
                    patient.getBookings().add(booking);
                    patientRef.setValue(patient);
                startActivity(new Intent(BookAppointment.this, MainPatientActivity.class));
                    b.dismiss(); // close the popup
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss(); // close the popup
            }
        });
    }

    public boolean validateDay(){
        errorOutput.setText("");
        System.out.println(dayChosen);
        if(dayChosen.equals("Monday")) {
            if(monday.getText().toString().trim().equals("Closed") || monday.getText().toString().trim().equals("Clinic did not set availabilities")){
                errorOutput.setText("Clinic closed or did not set availabilities");
                return false;
            }
        }
        if(dayChosen.equals("Tuesday")) {
            if(tuesday.getText().toString().trim().equals("Closed") || tuesday.getText().toString().trim().equals("Clinic did not set availabilities")) {
                errorOutput.setText("Clinic closed or did not set availabilities");
                return false;
            }
        }
        if(dayChosen.equals("Wednesday")) {
            if(wednesday.getText().toString().trim().equals("Closed") || wednesday.getText().toString().trim().equals("Clinic did not set availabilities")) {
                errorOutput.setText("Clinic closed or did not set availabilities");
                return false;
            }
        }
        if(dayChosen.equals("Thursday")) {
            if(thursday.getText().toString().trim().equals("Closed") || thursday.getText().toString().trim().equals("Clinic did not set availabilities")) {
                errorOutput.setText("Clinic closed or did not set availabilities");
                return false;
            }
        }
        if(dayChosen.equals("Friday")) {
            if(friday.getText().toString().trim().equals("Closed") || friday.getText().toString().trim().equals("Clinic did not set availabilities")) {
                errorOutput.setText("Clinic closed or did not set availabilities");
                return false;
            }
        }

        if(dayChosen.equals("Saturday")) {
            if(saturday.getText().toString().trim().equals("Closed") || saturday.getText().toString().trim().equals("Clinic did not set availabilities")) {
                errorOutput.setText("Clinic closed or did not set availabilities");
                return false;
            }
        }
        if(dayChosen.equals("Sunday")) {
            System.out.println("in if");
            if(sunday.getText().toString().trim().equals("Closed") || sunday.getText().toString().trim().equals("Clinic did not set availabilities")) {
                System.out.println("other if");
                errorOutput.setText("Clinic closed or did not set availabilities");
                return false;
            }
        }

        return true;
    }

    public boolean validateDate(){
        errorOutput.setText("");
        String message = date.getText().toString().trim();
        if(verifyFormat(message)){
            if(verifyTime(message)){
            } else{
                errorOutput.setText("This is date is not valid");
                return false;
            }
        } else{
            errorOutput.setText("Date is not right format");
            return false;
        }
        return true;
    }

    public static boolean verifyFormat(String message){
        if(message.length()!=10){
            return false;
        }

        char[] word = message.toCharArray();
        for(int i=0; i<word.length; i++){
            if ((i==0||i==1||i==2||i==3||i==5||i==6||i==8||i==9)&& !isInteger(Character.toString(word[i]))){
                return false;
            }
            if((i==4||i==7) && word[i]!='/'){
                return false;
            }
        }
        return true;
    }



    public static boolean verifyTime(String message){
        int year = Integer.parseInt(message.substring(0,4));
        int month = Integer.parseInt(message.substring(5,7));
        int day  = Integer.parseInt(message.substring(8,10));

        if(day>31 || day<1){
            return false;
        }

        if(month>12 || month<1){
            return false;
        }

        LocalDateTime now = LocalDateTime.now();

        int actualDay = now.getDayOfMonth();
        int actualMonth = now.getMonthValue();
        int actualYear = 2019;

        if(year<actualYear) {
            return false;
        }
        if(month<actualMonth&&year==actualYear){
            return false;
        }

        if(day<actualDay&&month==actualMonth&&year==actualYear){
            return false;
        }

        if(month==4||month==6||month==9||month==11){
            if(day==31){
                return false;
            }
        }

        if(month==2){
            if (day>29){
                return false;
            }
        }
        return true;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }



    public void setAvailabilities(){
        if(employee.getAvailabilities()==null || employee.getAvailabilities().size()==0){
            monday.setText("Clinic did not set availabilities");
            tuesday.setText("Clinic did not set availabilities");
            wednesday.setText("Clinic did not set availabilities");
            thursday.setText("Clinic did not set availabilities");
            friday.setText("Clinic did not set availabilities");
            saturday.setText("Clinic did not set availabilities");
            sunday.setText("Clinic did not set availabilities");
        } else {
            for (int i = 0; i<employee.getAvailabilities().size();i++){
                Availability day  = employee.getAvailabilities().get(i);
                if(day.getDay().equals("Monday")){
                    if (day.getStartTime().equals(day.getEndTime())){
                        monday.setText("Closed");
                    } else{
                        monday.setText(day.getStartTime()+" to " + day.getEndTime());
                    }}

                if(day.getDay().equals("Tuesday")){
                    if (day.getStartTime().equals(day.getEndTime())){
                        tuesday.setText("Closed");
                    } else{
                        tuesday.setText(day.getStartTime()+" to " + day.getEndTime());
                    }}

                if(day.getDay().equals("Wednesday")){
                    if (day.getStartTime().equals(day.getEndTime())){
                        wednesday.setText("Closed");
                    } else{
                        wednesday.setText(day.getStartTime()+" to " + day.getEndTime());
                    }}
                if(day.getDay().equals("Thursday")){
                    if (day.getStartTime().equals(day.getEndTime())){
                        thursday.setText("Closed");
                    } else{
                        thursday.setText(day.getStartTime()+" to " + day.getEndTime());
                    }}
                if(day.getDay().equals("Friday")){
                    if (day.getStartTime().equals(day.getEndTime())){
                        friday.setText("Closed");
                    } else{
                        friday.setText(day.getStartTime()+" to " + day.getEndTime());
                    }}
                if(day.getDay().equals("Saturday")){
                    if (day.getStartTime().equals(day.getEndTime())){
                        saturday.setText("Closed");
                    } else{
                        saturday.setText(day.getStartTime()+" to " + day.getEndTime());
                    }}
                if(day.getDay().equals("Sunday")){
                    if (day.getStartTime().equals(day.getEndTime())){
                        sunday.setText("Closed");
                    } else{
                        sunday.setText(day.getStartTime()+" to " + day.getEndTime());
                    }}

            }
        }
    }
}
