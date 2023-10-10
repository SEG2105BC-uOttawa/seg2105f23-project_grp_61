package com.grimpeurs.cycling;

import org.junit.Test;
import static com.grimpeurs.cycling.LoginActivity.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import static org.junit.Assert.*;
public class UnitTests {
    LoginActivity test = new LoginActivity();

    @Test
    public void BlankTest(){
        assertFalse(test.updateUI(null));
    }
    @Test
    public void AdminTest(){
        assertTrue(test.updateUI(admin));
    }
    @Test
    public void UserTest(){
        assertEquals(false, test.updateUI(null));
    }
}
