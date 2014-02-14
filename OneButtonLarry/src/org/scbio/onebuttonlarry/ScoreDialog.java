package org.scbio.onebuttonlarry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ScoreDialog extends DialogFragment {

	public interface ScoreDialogListener {
		public void onDialogSubmitClick(DialogFragment dialog, CharSequence player, long score);
	}

	private long score = 0;
	private CharSequence playerName = "Unknown";
	private EditText playernameEditText;
	private TextView scoreTextView;
	private ScoreDialogListener mListener;
	
	/*
	 * On create dialog. (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Inflate the layout.
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View mView = inflater.inflate(R.layout.dialog_result, null);
		
		playernameEditText = (EditText) mView.findViewById(R.id.scorenameEditText);
		scoreTextView = (TextView) mView.findViewById(R.id.scoreTextView);		
		scoreTextView.setText(String.valueOf(score));
		
		builder.setView(mView)
		// Add action button
		.setPositiveButton(R.string.submit_score_dialog, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				playerName = playernameEditText.getText();
				mListener.onDialogSubmitClick(ScoreDialog.this, playerName, score);
			}
		});      
		return builder.create();
	}
	
	/*
	 * Override the Fragment.onAttach() method to instantiate the NoticeDialogListener(non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onAttach(android.app.Activity)
	 */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ScoreDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }
    
    /*
     * Getters & Setters 
     */
    public void setScore(long score) {
		this.score = score;
	}
}
