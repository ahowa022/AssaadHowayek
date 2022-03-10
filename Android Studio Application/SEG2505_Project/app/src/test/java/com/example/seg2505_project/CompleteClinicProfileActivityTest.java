package com.example.seg2505_project;

import static org.junit.Assert.*;
import org.junit.Test;

public class CompleteClinicProfileActivityTest {
    @Test
    public void checkRightisAlphabet(){
        boolean valid = ClinicCompleteProfileActivity.isAlphabet("  Message  ");
        assertEquals(true, valid);
    }
    @Test
    public void checkRightisAlphabet2(){
        boolean valid = ClinicCompleteProfileActivity.isAlphabet("  Me-ss-age  ");
        assertEquals(true, valid);
    }
    @Test
    public void checkRightisAlphabet3(){
        boolean valid = ClinicCompleteProfileActivity.isAlphabet("  Mes sage  ");
        assertEquals(true, valid);
    }
    @Test
    public void checkRightisAlphabet4(){
        boolean valid = ClinicCompleteProfileActivity.isAlphabet("  Me-s s-age  ");
        assertEquals(true, valid);
    }
    @Test
    public void checkFalseisAlphabet(){
        boolean valid = ClinicCompleteProfileActivity.isAlphabet("  Message1  ");
        assertEquals(false, valid);
    }
    @Test
    public void checkFalseisAlphabet2(){
        boolean valid = ClinicCompleteProfileActivity.isAlphabet("  Message.  ");
        assertEquals(false, valid);
    }

}
