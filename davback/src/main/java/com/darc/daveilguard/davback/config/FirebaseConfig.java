package com.darc.daveilguard.davback.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value("${FIREBASE_CREDENTIALS_JSON}")
    private String firebaseCredentialsJson;

    @Bean
    public FirebaseAuth firebaseAuth() throws IOException {
        // Esto carga la configuración de Firebase con el archivo .json
        ByteArrayInputStream serviceAccountStream =
                new ByteArrayInputStream(firebaseCredentialsJson.getBytes());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .build();

        FirebaseApp.initializeApp(options);

        return FirebaseAuth.getInstance();
    }
}
