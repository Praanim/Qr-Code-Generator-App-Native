package com.example.qrgeneratorscanner;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QrCodeGenerator {

    public static Bitmap generateQrCode(String text){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        try{
            // 1. Generate the BitMatrix for the QR code
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 512, 512);

            // 2. Get the height and width of Bit matrix
           final int height = bitMatrix.getHeight();
           final int width = bitMatrix.getWidth();

           // 3. Create a Bitmap with same dimension as Bit matrix
            Bitmap bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);

            // 4. Fill the Bitmap with the QR code data
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    // If the bit at (x, y) is set, color it black, otherwise color it white
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? android.graphics.Color.BLACK : android.graphics.Color.WHITE);
                }
            }

            // 5. Return the bit map
            return bitmap;


        }catch (WriterException writerException){
            writerException.printStackTrace();;
        }
        return  null;
    }
}
