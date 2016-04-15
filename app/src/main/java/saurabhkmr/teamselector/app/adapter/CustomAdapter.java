package saurabhkmr.teamselector.app.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import saurabhkmr.teamselector.app.MatchesActivity;
import saurabhkmr.teamselector.app.R;
import saurabhkmr.teamselector.app.Utils.Utils;
import saurabhkmr.teamselector.app.models.Matches;

import java.util.Date;
import java.util.List;

/**
 * Created by saurabhkmr on 23/3/16.
 */
public class CustomAdapter extends BaseAdapter {
    List<Matches> result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;

    public CustomAdapter(MatchesActivity mainActivity, List<Matches> matches) {
        // TODO Auto-generated constructor stub
        result=matches;
        context=mainActivity;
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
        TextView date;
        TextView squad1;
        TextView squad2;
        TextView venue;
        ImageView squad1Img;
        ImageView squad2Img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.show_item, null);
        String date = result.get(position).getDate();
        holder.date=(TextView) rowView.findViewById(R.id.dateTextView);
        holder.squad1=(TextView) rowView.findViewById(R.id.squad1TextView);
        holder.squad2=(TextView) rowView.findViewById(R.id.sqaud2TextView);
        holder.venue=(TextView) rowView.findViewById(R.id.venueTextView);
        holder.squad1Img=(ImageView)rowView.findViewById(R.id.squad1Img);
        holder.squad2Img=(ImageView)rowView.findViewById(R.id.squad2Img);
        holder.date.setText(date);
        String squad1=result.get(position).getHomeTeam();
        String squad2=result.get(position).getAwayTeam();
        holder.squad1.setText(squad1);
        holder.squad2.setText(squad2);
        holder.venue.setText(result.get(position).getVenue());

        Utils.setImage(holder.squad1Img,squad1.toLowerCase());
        Utils.setImage(holder.squad2Img,squad2);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(context, "You Clicked "+result.get(position), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }


}
