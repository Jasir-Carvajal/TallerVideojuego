package com.example.tallervideojuego;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.tallervideojuego.modelo.Api.Api;
import com.example.tallervideojuego.modelo.Api.Login;
import com.example.tallervideojuego.modelo.Radom_player;
import com.example.tallervideojuego.modelo.entidades.Jugador;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Headers;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws  IOException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Login api = new Login();
        assertEquals(true, api.post("http://playdi.ml/api/login/") );

    }
}