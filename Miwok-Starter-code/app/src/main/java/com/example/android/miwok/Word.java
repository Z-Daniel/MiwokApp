package com.example.android.miwok;

/**
 * Created by Murdoc on 3/21/2017.
 * Represents a word with its' english and miwok version.
 */

public class Word {
    /**
     * Default translation of the word.
     */
    private String mDefaultTranslation;
    /**
     * Miwok translation of the word.
     */
    private String mMiwokTranslation;
    /**
     * Resource ID of the image illustration that belongs to the given word.
     */
    private int mMiwokImage = NO_IMAGE_PROVIDED;
    /**
     * Need it for indicating, if the word is a ListItem for the PhrasesActivity (not having an image)
     * or not.
     */
    private static final int NO_IMAGE_PROVIDED = -1;
    /**
     * Resource ID of the audio file that belongs to the given word.
     */
    private int mMiwokAudio;

    /**
     * Constructor without an image --> it is needed at the PhrasesActivity which has no images
     *
     * @param englishTranslation
     * @param miwokTranslation
     */
    public Word(String englishTranslation, String miwokTranslation, int miwokAudio) {
        mDefaultTranslation = englishTranslation;
        mMiwokTranslation = miwokTranslation;
        mMiwokAudio = miwokAudio;
    }

    /**
     * Constructor with a parameter image resource
     *
     * @param englishTranslation
     * @param miwokTranslation
     * @param miwokImage
     */
    public Word(String englishTranslation, String miwokTranslation, int miwokImage, int miwokAudio) {
        this(englishTranslation, miwokTranslation, miwokAudio);
        mMiwokImage = miwokImage;
    }

    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the miwok translation of the word.
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    /**
     * Get the resource ID of the (image) illustration of the word.
     */
    public int getMiwokImage() {
        return mMiwokImage;
    }

    /**
     * Get the resource ID of the audio that belongs to the word.
     */

    public int getMiwokAudio() {
        return mMiwokAudio;
    }

    /**
     * Indicates if a word object has an image, or not.
     *
     * @return false if it hasn't
     */
    public boolean hasImage() {
        return mMiwokImage != NO_IMAGE_PROVIDED;
    }

    /**
     * Returns a String representation of the Word object.
     */
    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mMiwokImage=" + mMiwokImage +
                ", mMiwokAudio=" + mMiwokAudio +
                '}';
    }
}
