package ca.qubeit.snaplapse.activity;

import android.view.View;
import ca.qubeit.snaplapse.data.Project;

public interface OnButtonClickListener {
	
	public static final int CLICK_CANCEL = -1;
	public static final int CLICK_DELETE = 0;
	public static final int CLICK_CONFIRM = 1;
	
	public void projectOptionsButtonClick(View view, int position);
	public void dialogButtonClick(int result, Project project);
}
