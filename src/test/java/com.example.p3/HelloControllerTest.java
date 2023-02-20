package com.example.p3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloControllerTest {
    @Test
    public void test(){
        HelloController test=new HelloController();
        boolean result= test.verificareEmail("ana@gmail.com");
        assertEquals(true,result);

    }
    @Test
    public void testtPass(){
        HelloController test=new HelloController();
        boolean result=test.verificareParola("parola");
        assertEquals(false,result);
    }
}