package com.example.p3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JavaMailUtilTest {
    @Test
    public void test(){
        JavaMailUtil jmu=new JavaMailUtil();
        boolean result=jmu.sendMail("lazar.iasmina5@e-uvt.ro","Cu drag,\nIasmina\n","Ce faci?");
        assertEquals(true,result);
    }
    @Test
    public void test1(){
        JavaMailUtil jmu=new JavaMailUtil();
        boolean result=jmu.sendMail("lazar.iasmina7@e-uvt.ro","Cu drag,\nIasmina\n","Ce faci?");
        assertEquals(true,result);
    }
    @Test
    public void test2(){
        JavaMailUtil jmu=new JavaMailUtil();
        boolean result=jmu.sendMail("lazar.iasmina6@e-uvt.ro","Cu drag,\nIasmina\n","Ce faci?");
        assertEquals(true,result);
    }

}