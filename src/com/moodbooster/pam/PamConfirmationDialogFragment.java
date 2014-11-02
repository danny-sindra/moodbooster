package com.moodbooster.pam;

import java.io.IOException;
import java.io.InputStream;

import com.moodbooster.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class PamConfirmationDialogFragment extends DialogFragment {

	// Handle data from parent activity
	int pam_photo_id;
	int currentWallpaperId;
	String currentWallpaperCategory;
	int pamScore;

	// Use this instance of the interface to deliver action events
	PamConfirmationDialogListener mListener;

	/**
	 * Custom dialog box constructor
	 * 
	 * @param currentWallpaperId
	 * @param currentWallpaperCategory
	 * @param pamScore
	 */
	public PamConfirmationDialogFragment(int pam_photo_id,
			int currentWallpaperId, String currentWallpaperCategory,
			int pamScore) {
		this.pam_photo_id = pam_photo_id;
		this.currentWallpaperId = currentWallpaperId;
		this.currentWallpaperCategory = currentWallpaperCategory;
		this.pamScore = pamScore;
		this.setRetainInstance(true);
	}

	/*
	 * The activity that creates an instance of this dialog fragment must
	 * implement this interface in order to receive event callbacks. Each method
	 * passes the DialogFragment in case the host needs to query it.
	 */
	public interface PamConfirmationDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog,
				int currentWallpaperId, String currentWallpaperCategory,
				int pamScore);

		public void onDialogNegativeClick(DialogFragment dialog);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Set the image confirmation into view layout
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.dialog_pam_confirm, null);
		ImageView imageView = (ImageView) dialoglayout
				.findViewById(R.id.picture_confirmation);
		try {
			imageView
					.setImageBitmap(getBitmapFromAsset(getPamPhotoResource(pam_photo_id)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Customize alert dialog
		builder.setView(dialoglayout)
				.setTitle(R.string.select_picture)
				.setPositiveButton(R.string.select_picture_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								mListener.onDialogPositiveClick(
										PamConfirmationDialogFragment.this,
										currentWallpaperId,
										currentWallpaperCategory, pamScore);
							}
						})
				.setNegativeButton(R.string.select_picture_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								mListener
										.onDialogNegativeClick(PamConfirmationDialogFragment.this);
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}

	@Override
	public void onDestroyView() {
		if (getDialog() != null && getRetainInstance())
			getDialog().setDismissMessage(null);
		super.onDestroyView();
	}

	// Override the Fragment.onAttach() method to instantiate the
	// NoticeDialogListener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the
			// host
			mListener = (PamConfirmationDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement PamConfirmationDialogListener");
		}
	}

	/**
	 * Load bitmap image from assets folder
	 * 
	 * @param strName
	 * @return
	 * @throws IOException
	 */
	private Bitmap getBitmapFromAsset(String strName) throws IOException {
		AssetManager assetManager = getActivity().getAssets();

		InputStream istr = assetManager.open(strName);
		Bitmap bitmap = BitmapFactory.decodeStream(istr);
		return bitmap;
	}

	/**
	 * Get the PAM image resource
	 * 
	 * @param pam_photo_id
	 * @return
	 */
	private String getPamPhotoResource(int pam_photo_id) {
		String pam_folder = "pam_images/";
		String pam_category = ""; // [1-16]_[category]
		String pam_local_id = ""; // [1-16]_[1-3]

		int pam_category_id = pam_photo_id / 3;
		if ((pam_photo_id % 3) == 0) {
			pam_category = pam_category_id + "_"
					+ getPamCategory(pam_category_id) + "/";
			pam_local_id = pam_category_id + "_" + 3;
		} else if ((pam_photo_id % 3) % 3 == 2) {
			pam_category = (pam_category_id + 1) + "_"
					+ getPamCategory(pam_category_id + 1) + "/";
			pam_local_id = (pam_category_id + 1) + "_" + 2;
		} else if ((pam_photo_id % 3) % 3 == 1) {
			pam_category = (pam_category_id + 1) + "_"
					+ getPamCategory(pam_category_id + 1) + "/";
			pam_local_id = (pam_category_id + 1) + "_" + 1;
		}

		return pam_folder + pam_category + pam_local_id + ".jpg";
	}

	/**
	 * Get the PAM category name from id
	 * 
	 * @param category_id
	 * @return
	 */
	private String getPamCategory(int category_id) {
		switch (category_id) {
		case 1:
			return "afraid";
		case 2:
			return "tense";
		case 3:
			return "excited";
		case 4:
			return "delighted";
		case 5:
			return "frustrated";
		case 6:
			return "angry";
		case 7:
			return "happy";
		case 8:
			return "glad";
		case 9:
			return "miserable";
		case 10:
			return "sad";
		case 11:
			return "calm";
		case 12:
			return "satisfied";
		case 13:
			return "gloomy";
		case 14:
			return "tired";
		case 15:
			return "sleepy";
		case 16:
			return "serene";
		default:
			return "";
		}
	}
}