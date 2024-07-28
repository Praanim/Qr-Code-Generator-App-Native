package com.example.qrgeneratorscanner;

import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;

public class BarCodeScanningHelperClass {

    // Bar code scanning options
    private static final  BarcodeScannerOptions options = new BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE).build();

    // Bar code scanner options
    static BarcodeScanner scanner = BarcodeScanning.getClient(options);

}
