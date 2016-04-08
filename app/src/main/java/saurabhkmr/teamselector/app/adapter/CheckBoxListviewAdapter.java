package saurabhkmr.teamselector.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import saurabhkmr.teamselector.app.MatchesActivity;
import saurabhkmr.teamselector.app.PickTeamActivity;
import saurabhkmr.teamselector.app.R;
import saurabhkmr.teamselector.app.Utils.Utils;
import saurabhkmr.teamselector.app.models.Players;

import java.util.List;

/**
 * Created by saurabhkmr on 7/4/16 @Capillary Tech
 */
public class CheckBoxListviewAdapter extends BaseAdapter {
    List<Players> squad1Players;
    List<Players> squad2Players;
    Context context;
    private static LayoutInflater inflater=null;

    public CheckBoxListviewAdapter(PickTeamActivity pickTeamActivity, List<Players> squad1Players,List<Players> squad2Players) {
        // TODO Auto-generated constructor stub
        this.squad1Players=squad1Players;
        this.squad2Players=squad2Players;
        context=pickTeamActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(squad1Players.size()>squad2Players.size()){
            return squad1Players.size();
        }
        return squad2Players.size();
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
        CheckBox playerSquad1NameCheckBox;
        ImageView  squad1Img;
        CheckBox playerSquad2NameCheckBox;
        ImageView  squad2Img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.team_picker, null);
        holder.playerSquad1NameCheckBox=(CheckBox) rowView.findViewById(R.id.tp_checkBoxSquad1);
        holder.playerSquad2NameCheckBox=(CheckBox) rowView.findViewById(R.id.tp_checkBoxSquad2);
      //  holder.squad1Img=(ImageView)rowView.findViewById(R.id.tp_squad1Img);
       // holder.squad2Img=(ImageView)rowView.findViewById(R.id.tp_squad2Img);

        if(squad1Players.size()>position) {
            String playerName = squad1Players.get(position).getName();
            holder.playerSquad1NameCheckBox.setText(playerName);
            holder.playerSquad1NameCheckBox.setVisibility(View.VISIBLE);
           // Utils.setImage(holder.squad1Img, squad1Players.get(position).getSquadName());
        }

        if(squad2Players.size()>position) {
            String playerNameSquad2 = squad2Players.get(position).getName();
            holder.playerSquad2NameCheckBox.setText(playerNameSquad2);
            holder.playerSquad2NameCheckBox.setVisibility(View.VISIBLE);
         //   Utils.setImage(holder.squad2Img, squad2Players.get(position).getSquadName());
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               // Toast.makeText(context, "You Clicked "+result.get(position), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }
}
