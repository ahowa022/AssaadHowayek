package com.example.seg2505_project;

import static org.junit.Assert.*;
import org.junit.Test;

public class BookAppointmentTest {
    @Test
    public void checkRightVerifyFormat(){
        boolean valid = BookAppointment.verifyFormat("2020/12/12");
        assertEquals(true ,valid);
    }
    @Test
    public void checkFalseVerifyFormat(){
        boolean valid = BookAppointment.verifyFormat("202/12/12");
        assertEquals(false ,valid);
    }
    @Test
    public void checkFalseVerifyFormat2(){
        boolean valid = BookAppointment.verifyFormat("2020/1/12");
        assertEquals(false ,valid);
    }
    @Test
    public void checkFalseVerifyFormat3(){
        boolean valid = BookAppointment.verifyFormat("2020/12/1");
        assertEquals(false ,valid);
    }
    @Test
    public void checkFalseVerifyFormat4(){
        boolean valid = BookAppointment.verifyFormat("2020/12-12");
        assertEquals(false ,valid);
    }
    @Test
    public void checkFalseVerifyFormat5(){
        boolean valid = BookAppointment.verifyFormat("2020-12/12");
        assertEquals(false ,valid);
    }


}
