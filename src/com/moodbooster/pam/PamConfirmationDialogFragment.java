package com.moodbooster.pam;

import com.moodbooster.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class PamConfirmationDialogFragment extends DialogFragment {

	// Handle data from parent activity
	int currentWallpaperId;
	String currentWallpaperCategory;
	int pamScore;
	int totalScreenUnlocked;

	// Use this instance of the interface to deliver action events
	PamConfirmationDialogListener mListener;

	/**
	 * Custom dialog box constructor
	 * 
	 * @param currentWallpaperId
	 * @param currentWallpaperCategory
	 * @param pamScore
	 * @param totalScreenUnlocked
	 */
	public PamConfirmationDialogFragment(int currentWallpaperId,
			String currentWallpaperCategory, int pamScore,
			int totalScreenUnlocked) {
		this.currentWallpaperId = currentWallpaperId;
		this.currentWallpaperCategory = currentWallpaperCategory;
		this.pamScore = pamScore;
		this.totalScreenUnlocked = totalScreenUnlocked;
		this.setRetainInstance(true);
	}

	/*
	 * The activity that creates an instance of this dialog fragment must
	 * implement this interface in order to receive event callbacks. Each method
	 * passes the DialogFragment in case the host needs to query it.
	 */
	public interface PamConfirmationDialogListener {
		public boolean onDialogPositiveClick(DialogFragment dialog,
				int currentWallpaperId, String currentWallpaperCategory,
				int pamScore, int totalScreenUnlocked);

		public boolean onDialogNegativeClick(DialogFragment dialog);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.select_picture)
				.setPositiveButton(R.string.select_picture_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								mListener.onDialogPositiveClick(
										PamConfirmationDialogFragment.this,
										currentWallpaperId,
										currentWallpaperCategory, pamScore,
										totalScreenUnlocked);
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
		Log.d("alert", "created");
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
}