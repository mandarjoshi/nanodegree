package com.gandivainc.myappporfolio.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.gandivainc.myappporfolio.R;
import com.gandivainc.myappporfolio.adapter.MovieTrailerAdapter;

/**
 * This is common implementation for popup dialog with list.
 * Created by ea8b26s on 04/16/2016.
 */
public class CustomListDialog extends Dialog {

    RecyclerView listView;
    RecyclerView.Adapter adapter;
    ImageView shareImage;
    Context context;

    public CustomListDialog(Context context, RecyclerView.Adapter adapter) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_list_dialog);

        this.adapter = adapter;

        listView = (RecyclerView) findViewById(R.id.custom_list_popup_list);
        listView.setLayoutManager(new LinearLayoutManager(context));
        listView.setAdapter(adapter);

        shareImage = (ImageView) findViewById(R.id.custom_list_popup_share);
        shareImage.setVisibility(View.GONE);


        Button closeButton = (Button) findViewById(R.id.custom_list_popup_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * This method is used for sharing movie trailer when popup dialog is used for trailers.
     */
    public void doShareUrl()
    {
        shareImage.setVisibility(View.VISIBLE);
        shareImage.setTag(R.id.TRAILER_OBJECT,((MovieTrailerAdapter) adapter).getShareTrailer());
        shareImage.setTag(R.id.MOVIE_OBJECT,((MovieTrailerAdapter)adapter).getMovie());
    }

}
