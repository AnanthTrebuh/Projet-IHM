package fr.univ_poitiers.tpinfo.cinematech;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

//Class used to Load an Image, take an ImageView
public class LoadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public LoadImage(ImageView img){
        this.imageView = img;
    }

    //When received an imageView, we loadImage.execute with an url to find the image from this url
    @Override
    protected Bitmap doInBackground(String... strings){
        String urlLink = strings[0];
        Bitmap bitmap = null;
        try{
            //get ht image and transform it into a bitmap
            InputStream inputStream = new java.net.URL(urlLink).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }
}
