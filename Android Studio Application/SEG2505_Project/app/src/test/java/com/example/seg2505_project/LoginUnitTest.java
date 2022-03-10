package com.example.seg2505_project;

import static org.junit.Assert.*;
import org.junit.Test;
public class LoginUnitTest {

    @Test
    public void checkWrongIsAlphabetPatient(){
        boolean valid = RegistrationPatientActivity.isAlphabet("ABC123ABC");
        assertEquals("Check if message is invalid by containing alphabetical letters, a space or '-' ", false ,valid);
    }
    @Test
    public void checkRightIsAlphabetPatient(){
        boolean valid = RegistrationPatientActivity.isAlphabet("ABC-Test");
        assertEquals("Check if message only contains alphabetical letters, a space or '-' ", true ,valid);
    }

    @Test
    public void checkWrongIsAlphabetEmployee(){
        boolean valid = RegistrationClinicActivity.isAlphabet("ABC123ABC");
        assertEquals("Check if message is invalid by containing alphabetical letters, a space or '-' ", false ,valid);
    }
    @Test
    public void checkRightIsAlphabetEmployee(){
        boolean valid = RegistrationClinicActivity.isAlphabet("ABC-Test");
        assertEquals("Check if message only contains alphabetical letters, a space or '-' ", true ,valid);
    }

}
