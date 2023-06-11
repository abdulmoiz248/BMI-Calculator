package com.example.fa22bcs040lab_final;

import java.io.Serializable;
import java.util.Date;

public class Male extends Person implements Serializable {
    @Override
    public void calculateBMI() {
        BMI = (weight / height) *(weight / height);
    }
    public void findage()
    {
         age=2023-birthyear;
    }

}
