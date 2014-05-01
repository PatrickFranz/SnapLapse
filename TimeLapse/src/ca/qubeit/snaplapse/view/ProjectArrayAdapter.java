package ca.qubeit.snaplapse.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectHelper;
import ca.qubeit.snaplapse.data.ProjectList;

public class ProjectArrayAdapter extends ArrayAdapter<Project>{

	private Context ctx;
	private ProjectList projects;
	private int selectedProjectPosition;
	private final int DELETE 	= 0;
	private final int SETTINGS  = 1;
	

	public ProjectArrayAdapter(Context context, ProjectList projects) {
		super(context, android.R.id.content, projects);
		this.ctx = context;
		this.projects = projects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        //Reuse views
        if(v == null) {

            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.project_listview_row, null);

            //Set the ViewHolder
            ViewHolder holder = new ViewHolder();
            holder.tvProjectName = (TextView) v.findViewById(R.id.tv_project_name);
            holder.tvProjectDescription = (TextView) v.findViewById(R.id.tv_project_description);
            holder.ivSettings = (ImageView) v.findViewById(R.id.iv_settings);
            holder.position = position;


            v.setTag(holder);
        }
        Project project = projects.get(position);
        final ViewHolder holder = (ViewHolder) v.getTag();
        holder.tvProjectName.setText(project.getName());
        holder.tvProjectDescription.setText(project.getDescription());
        holder.ivSettings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupWindow popup = getPopupWindow(v, holder.position);
                popup.showAsDropDown(v);

            }
        });

        return v;
	}
	
	private PopupWindow getPopupWindow(View view, final int pos){
		final PopupWindow puw = new PopupWindow(view.getContext());
		
		ListView lvOptions = new ListView(view.getContext());
		
		String[] options = {"Delete", "Settings"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, options);
		
		lvOptions.setAdapter(adapter);
		lvOptions.setBackgroundColor(Color.WHITE);
		lvOptions.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int puPosition, long id) {
				switch(puPosition){
				case DELETE:
					Toast.makeText(getContext(),
                            "puPostion:" + puPosition + " Row:" + pos,
                            Toast.LENGTH_SHORT).show();
//					ProjectHelper pHelper = new ProjectHelper(view.getContext());
                    FragmentManager fm = ((Activity)ctx).getFragmentManager();
                    DeleteProjectDialogFragment dialog = new DeleteProjectDialogFragment();


					Project selectedProject = projects.get(pos);
					projects.remove(pos);
//					if(pHelper.deleteProject(selectedProject)){
//						projects.refresh();
//					}
					puw.dismiss();
					break;
				
				case SETTINGS:
					Toast.makeText(view.getContext()
							, "Setting for " + projects.get(pos).getName()
							, Toast.LENGTH_SHORT).show();
					puw.dismiss();
					break;
				
				default:
					puw.dismiss();
					break;
					
				}				
			}
		});
		
		puw.setContentView(lvOptions);
		puw.setFocusable(true);
		puw.setWidth(400);
		puw.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		return puw;
	}

    static class ViewHolder{
        public TextView tvProjectName;
        public TextView tvProjectDescription;
        public ImageView ivSettings;
        public int position;
    }
	
	

}
