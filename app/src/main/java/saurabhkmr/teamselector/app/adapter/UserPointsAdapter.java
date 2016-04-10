package saurabhkmr.teamselector.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import saurabhkmr.teamselector.app.CurrentMatchLeaderBoard;
import saurabhkmr.teamselector.app.LeaderboardActivity;
import saurabhkmr.teamselector.app.MatchesActivity;
import saurabhkmr.teamselector.app.R;
import saurabhkmr.teamselector.app.Utils.Utils;
import saurabhkmr.teamselector.app.models.Matches;
import saurabhkmr.teamselector.app.models.User;

import java.util.List;

/**
 * Created by saurabhkmr on 8/4/16 @Capillary Tech
 */
public class UserPointsAdapter extends BaseAdapter {

    List<User> result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;

    public UserPointsAdapter(CurrentMatchLeaderBoard currentMatchLeaderBoard,LeaderboardActivity mainActivity, List<User> users) {
        // TODO Auto-generated constructor stub
        result=users;
        if(currentMatchLeaderBoard==null) {
            context = mainActivity;
        }
        else {
            context=currentMatchLeaderBoard;
        }
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView name;
        TextView points;
        TextView rank;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.user_points, null);
        holder.name=(TextView) rowView.findViewById(R.id.user_name);
        holder.points=(TextView) rowView.findViewById(R.id.user_points);
        String name=result.get(position).getName();
        Long points=result.get(position).getPoints();
        int rank=result.get(position).getRank();
        if(name==""){
            name="Not Available";
        }
        holder.name.setText("Rank #"+rank+"   "+name);
        holder.points.setText(points.toString());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result.get(position), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }
}
