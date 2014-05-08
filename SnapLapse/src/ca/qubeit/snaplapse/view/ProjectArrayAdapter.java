package ca.qubeit.snaplapse.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectList;
import ca.qubeit.snaplapse.view.DeleteProjectDialogFragment.OnButtonClickListener;

public class ProjectArrayAdapter extends ArrayAdapter<Project>{

	private Context ctx;
	private ProjectList projects;	

	public ProjectArrayAdapter(Context context, ProjectList projects) {
		super(context, android.R.id.content, projects);
		this.ctx = context;
		this.projects = projects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        //Reuse views
        if(view == null) {

            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.project_listview_row, null);

            //Set the ViewHolder
            ViewHolder holder = new ViewHolder();
            holder.tvProjectName = (TextView) view.findViewById(R.id.tv_project_name);
            holder.tvProjectDescription = (TextView) view.findViewById(R.id.tv_project_description);
            holder.ivSettings = (ImageView) view.findViewById(R.id.iv_settings);
            holder.position = position;


            view.setTag(holder);
        }
        Project project = projects.get(position);
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.tvProjectName.setText(project.getName());
        holder.tvProjectDescription.setText(project.getDescription());
        holder.ivSettings.setOnClickListener(new OnClickListener() {
        	
            @Override
            public void onClick(View v) {
            	OnButtonClickListener listener = ((OnButtonClickListener)ctx);
            	listener.settingsButtonClick(v, holder.position);
            }
        });
        return view;
	}

    static class ViewHolder{
        public TextView tvProjectName;
        public TextView tvProjectDescription;
        public ImageView ivSettings;
        public int position;
    }
	
	

}
