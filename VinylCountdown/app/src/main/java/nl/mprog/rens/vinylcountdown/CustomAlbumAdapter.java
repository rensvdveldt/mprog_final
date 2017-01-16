package nl.mprog.rens.vinylcountdown;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Rens on 16/01/2017.
 */

public class CustomAlbumAdapter extends ArrayAdapter<RecordInfo> {

    // List of items
    private List<RecordInfo> objects;

    // Constructor
    public CustomAlbumAdapter(Context context, int textViewResourceId, List<RecordInfo> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }


    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.record_layout, null);
        }

        RecordInfo i = objects.get(position);

        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView artist = (TextView) v.findViewById(R.id.artistTV);
            TextView title = (TextView) v.findViewById(R.id.titleTV);

            artist.setText(i.getArtist());
            title.setText(i.getTitle());

            // Download and set the image for the album:
            ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
            new DownloadImageTask(imageView).execute(i.getImgLinkmed());
        }

        // the view must be returned to our activity
        return v;

    }
}


