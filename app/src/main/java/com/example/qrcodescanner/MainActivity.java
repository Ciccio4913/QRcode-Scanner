package com.example.qrcodescanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import androidx.activity.result.ActivityResultLauncher;

public class MainActivity extends AppCompatActivity {

    private TextView resultText;

    private final ActivityResultLauncher<ScanOptions> qrLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if(result.getContents() != null) {
                    String scannedText = result.getContents();
                    resultText.setText(scannedText);

                    // PROVA: apri link automaticamente se inizia con http o https
                    if(scannedText.startsWith("http://") || scannedText.startsWith("https://")) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scannedText));
                        startActivity(browserIntent);
                    }
                } else {
                    resultText.setText("Scansione annullata");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanButton = findViewById(R.id.scanButton);
        resultText = findViewById(R.id.resultText);

        scanButton.setOnClickListener(v -> startQRScanner());
    }

    private void startQRScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scansiona un QR code");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);

        qrLauncher.launch(options);
    }
}