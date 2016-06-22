package br.com.wgbn.meuguia.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Utils {

    public static Bitmap base64ParaImagem(String _b64){
        byte[] decodedString = Base64.decode(_b64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

}
