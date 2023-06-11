package com.example.fa22bcs040lab_final;

import java.io.Serializable;

public class Female extends Person implements Serializable {

    @Override
    public void calculateBMI() {
       BMI= (weight)/(height)*(weight / height)*0.9;
    }
    public void findage()
    {
        age=2023-birthyear;
    }
}
