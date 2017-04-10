package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Murdoc on 3/21/2017.
 * The purpose of the WordAdapter class is to get the content from the Word class' objects to the ListView that is being displayed.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    /**
     * Color of the TextViews' layout.
     */
    private int mColorResource;

    /**
     * MediaPlayer that stores the object of the audio file.
     */
    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    /**
     * Constructor of WordAdapter class
     *
     * @param context       is the current Activity which displays a list of items
     * @param words         is the list of items that should be displayed in the given Activity
     * @param colorResource is the background color of the TextViews' images in the ListItems
     */
    public WordAdapter(Activity context, ArrayList<Word> words, int colorResource) {
        super(context, 0, words);
        mColorResource = colorResource;
    }

    /**
     * This method gets called, when the ListView is trying to display a list of items at a given position.
     *
     * @param position
     * @param convertView The reused View which is inflated (this way we won't waste memory)
     * @param parent      The parent View of the list items, which is the ListView
     * @return The ListItem that should be displayed
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        View listItemView = convertView;
        final Context context = this.getContext();

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        /**
         * The current Word object which holds the displayable content.
         */
        final Word currentWord = getItem(position);

        /**
         * Setting the LinearLayouts' background color based on the category. (based on the
         * color resource id the WordAdapter gets when creating an instance)
         */
        LinearLayout wordsLayout = (LinearLayout) listItemView.findViewById(R.id.texts_layout);
        wordsLayout.setBackgroundResource(mColorResource);

        /**
         * Specifying here, which object property should be displayed in which view inside the ListItem
         * (so the miwok translation should be displayed in the upper TextView,
         * the english translation should be displayed in the bottom TextView,
         * and the image should be in the ImageView)
         */
        TextView defaultTranslation = (TextView) listItemView.findViewById(R.id.english_tv);
        defaultTranslation.setText(currentWord.getDefaultTranslation());

        TextView miwokTranslation = (TextView) listItemView.findViewById(R.id.miwok_tv);
        miwokTranslation.setText(currentWord.getMiwokTranslation());

        /**
         * The PhrasesActivity does not have images, therefor I change the ImageViews' visibility to GONE,
         * and I only change it back to visible, if the current word objects' mMiwokImage field was actually
         * filled and the constructor with 3 parameters was used, not the other one.
         */
        ImageView miwokImage = (ImageView) listItemView.findViewById(R.id.miwok_iv);
        miwokImage.setVisibility(View.GONE); //setting the ImageViews' visibility, so it won't take any space
        if (currentWord.hasImage()) {
            miwokImage.setVisibility(View.VISIBLE);
            miwokImage.setImageResource(currentWord.getMiwokImage());
        }

        /**
         * Putting the given audio file to the right list item. Also setting an onClickListener on the
         * wordsLayout that contains the two translations of the word, so the audio file would be played
         * when these are clicked.
         */
        wordsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //callback method
                mMediaPlayerRelease();

                /**
                 * Requesting audio focus for playing a sound. This way if for example some music is playing,
                 * it will be stopped.
                 */
                mAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                mAudioManager.requestAudioFocus(null,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                mMediaPlayer = MediaPlayer.create(context, currentWord.getMiwokAudio());
                mMediaPlayer.start();
                Toast.makeText(context, "Playing sound!", Toast.LENGTH_SHORT).show();

                /**
                 * Setting up an onCompletionListener, so the MediaPlayer resource would be released.
                 */
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) { //callback method
                       //Toast.makeText(context, "Playing ended!", Toast.LENGTH_SHORT).show();
                        mMediaPlayerRelease();
                        /**
                         * If for example some music was playing before the user wanted to hear audio
                         * from this app, then it will be continued because after finishing playing the
                         * audio focus will be abandoned.
                         */
                        mAudioManager.abandonAudioFocus(null);
                    }
                });
            }
        });

        return listItemView;
    }

    /**
     * This method only releases the mMediaPlayer if it is not null. This way an exception cannot appear making the app crash.
     */
    public void mMediaPlayerRelease() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

}
