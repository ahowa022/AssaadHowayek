package com.example.seg2505_project;


import static org.junit.Assert.*;
import org.junit.Test;

public class WorkingHoursActivityTest {

    @Test
    public void checkRightIsRightFormat(){
        boolean valid = WorkingHoursActivity.isRightFormat("00:00");
        assertEquals(true ,valid);
    }
    @Test
    public void checkRightIsRightFormat2(){
        boolean valid = WorkingHoursActivity.isRightFormat("23:59");
        assertEquals(true ,valid);
    }
    @Test
    public void checkFailIsRightFormat(){
        boolean valid = WorkingHoursActivity.isRightFormat("23:60");
        assertEquals( false ,valid);
    }
    @Test
    public void checkFailIsRightFormat2(){
        boolean valid = WorkingHoursActivity.isRightFormat("25:00");
        assertEquals( false ,valid);
    }
    @Test
    public void checkFailIsRightFormat3(){
        boolean valid = WorkingHoursActivity.isRightFormat("000:60");
        assertEquals( false ,valid);
    }
    @Test
    public void checkFailIsRightFormat4(){
        boolean valid = WorkingHoursActivity.isRightFormat("00:000");
        assertEquals( false ,valid);
    }
    @Test
    public void checkFailIsRightFormat5(){
        boolean valid = WorkingHoursActivity.isRightFormat("1600");
        assertEquals( false ,valid);
    }


}
