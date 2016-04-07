package saurabhkmr.teamselector.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import saurabhkmr.teamselector.app.R;
import saurabhkmr.teamselector.app.SquadsActivity;
import saurabhkmr.teamselector.app.models.Players;

import java.util.List;

/**
 * Created by saurabhkmr on 27/3/16.
 */
public class CustomSquadAdapter extends BaseAdapter {

    List<Players> result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;

    public CustomSquadAdapter(SquadsActivity mainActivity, List<Players> players) {
        // TODO Auto-generated constructor stub
        result=players;
        context=mainActivity;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
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
        TextView country;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.show_players, null);
        holder.name=(TextView) rowView.findViewById(R.id.playerName);
        holder.country=(TextView) rowView.findViewById(R.id.playerCountryName);

        String playerName=result.get(position).getName();
        String playerCountryName=result.get(position).getCountryName();
        holder.name.setText(playerName);
        holder.country.setText(playerCountryName);

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
