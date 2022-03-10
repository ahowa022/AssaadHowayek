package com.example.seg2505_project;


import static org.junit.Assert.*;
import org.junit.Test;

public class DisplayUnitTest {

    @Test
    public void checkWrongValidateServices(){
        boolean valid = DisplayServicesActivity.validate("123","Nurse");
        assertEquals("Check if Name or Role is invalid by containing alphabetical letters, a space or '-' ", false ,valid);
    }
    @Test
    public void checkWrongValidateServices2(){
        boolean valid = DisplayServicesActivity.validate("Physiotherapy","123");
        assertEquals("Check if Name or Role is invalid by containing alphabetical letters, a space or '-' ", false ,valid);
    }
    @Test
    public void checkRightValidateServices(){
        boolean valid = DisplayServicesActivity.validate("Massage","Doctor");
        assertEquals("Check if Name or Role is invalid by containing alphabetical letters, a space or '-' ", true ,valid);
    }
}
