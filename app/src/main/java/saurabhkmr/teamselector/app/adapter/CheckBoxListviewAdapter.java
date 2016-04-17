package saurabhkmr.teamselector.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import saurabhkmr.teamselector.app.MatchesActivity;
import saurabhkmr.teamselector.app.PickTeamActivity;
import saurabhkmr.teamselector.app.R;
import saurabhkmr.teamselector.app.Utils.Utils;
import saurabhkmr.teamselector.app.models.Players;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabhkmr on 7/4/16 @Capillary Tech
 */
public class CheckBoxListviewAdapter extends BaseAdapter  {
    List<Players> squad1Players;
    List<Players> squad2Players;
    Context context;
    private static LayoutInflater inflater=null;
    public static List<Players> selectedPlayers;


    public CheckBoxListviewAdapter(PickTeamActivity pickTeamActivity, List<Players> squad1Players,List<Players> squad2Players) {
        // TODO Auto-generated constructor stub
        this.squad1Players=squad1Players;
        this.squad2Players=squad2Players;
        context=pickTeamActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        selectedPlayers=new ArrayList<>(11);
        if(squad1Players.size()<squad2Players.size()){
            equaliseSquad1(squad1Players,squad2Players);
        }
        else
        {
            equaliseSquad2(squad2Players,squad1Players);
        }
    }

    private void equaliseSquad1(List<Players> squad1Players, List<Players> squad2Players) {
        int size=squad2Players.size()-squad1Players.size();
        Players defaultPlayers=new Players();
        defaultPlayers.setMatchId(0);
        defaultPlayers.setName("");
        defaultPlayers.setCountryId(1);
        defaultPlayers.setSquadId(0);
        while (size>0){
            squad1Players.add(defaultPlayers);
            size --;
        }
    }

    private void equaliseSquad2(List<Players> squad2Players, List<Players> squad1Players) {
        int size=squad1Players.size()-squad2Players.size();
        Players defaultPlayers=new Players();
        defaultPlayers.setMatchId(0);
        defaultPlayers.setName("");
        defaultPlayers.setCountryId(1);
        defaultPlayers.setSquadId(0);
        while (size>0){
            squad2Players.add(defaultPlayers);
            size--;
        }
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
        CheckBox playerSquad2NameCheckBox;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        //rowView = inflater.inflate(R.layout.team_picker, null);


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.team_picker, null);
            holder.playerSquad1NameCheckBox = (CheckBox) convertView.findViewById(R.id.tp_checkBoxSquad1);
            holder.playerSquad2NameCheckBox = (CheckBox) convertView.findViewById(R.id.tp_checkBoxSquad2);
            holder.playerSquad1NameCheckBox.setOnCheckedChangeListener(null);
            holder.playerSquad1NameCheckBox.setChecked(squad1Players.get(position).isSelected());
            holder.playerSquad2NameCheckBox.setOnCheckedChangeListener(null);
            holder.playerSquad2NameCheckBox.setChecked(squad2Players.get(position).isSelected());

            holder.playerSquad1NameCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    squad1Players.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                    maintainPlayersSelectedSquad1(isChecked,getPosition);
                }
            });

            holder.playerSquad2NameCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    squad2Players.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                    maintainPlayersSelectedSquad2(isChecked,getPosition);
                }
            });

            convertView.setTag(holder);
            convertView.setTag(R.id.tp_checkBoxSquad1, holder.playerSquad1NameCheckBox);
            convertView.setTag(R.id.tp_checkBoxSquad2, holder.playerSquad2NameCheckBox);
        } else {
            holder = (Holder) convertView.getTag();
        }
        try {
            if(squad1Players.get(position).getName()==""){
                holder.playerSquad1NameCheckBox.setTag(position);
                holder.playerSquad1NameCheckBox.setVisibility(View.INVISIBLE);
            }
            else {
                holder.playerSquad1NameCheckBox.setTag(position);
                holder.playerSquad1NameCheckBox.setVisibility(View.VISIBLE);
                holder.playerSquad1NameCheckBox.setText(squad1Players.get(position).getName());// This line is important.
                holder.playerSquad1NameCheckBox.setChecked(squad1Players.get(position).isSelected());
            }
        }
        catch (Exception ex){}

        try {

            if(squad2Players.get(position).getName()==""){
                holder.playerSquad2NameCheckBox.setTag(position);
                holder.playerSquad2NameCheckBox.setVisibility(View.INVISIBLE);
            }
            else {
                holder.playerSquad2NameCheckBox.setTag(position);
                holder.playerSquad2NameCheckBox.setVisibility(View.VISIBLE);
                holder.playerSquad2NameCheckBox.setText(squad2Players.get(position).getName());
                holder.playerSquad2NameCheckBox.setChecked(squad2Players.get(position).isSelected());
            }
        }
        catch (Exception ex){}
        return convertView;
    }

    private void maintainPlayersSelectedSquad1(boolean isChecked, int position) {
        if(squad1Players.get(position).isSelected()){
            if(!selectedPlayers.contains(squad1Players.get(position))) {
           //     Toast.makeText(context, "Players selected count "+selectedPlayers.size(), Toast.LENGTH_LONG).show();
                selectedPlayers.add(squad1Players.get(position));
            }
        }
        else{
            if(selectedPlayers.contains(squad1Players.get(position))) {
             //   Toast.makeText(context, "Players selected count "+selectedPlayers.size(), Toast.LENGTH_LONG).show();
                selectedPlayers.remove(squad1Players.get(position));
            }
        }

    }

    private void maintainPlayersSelectedSquad2(boolean isChecked, int position) {
        if(squad2Players.get(position).isSelected()){
            if(!selectedPlayers.contains(squad2Players.get(position))) {
               // Toast.makeText(context, "Players selected count "+selectedPlayers.size(), Toast.LENGTH_LONG).show();
                selectedPlayers.add(squad2Players.get(position));
            }
        }
        else {
            if(selectedPlayers.contains(squad2Players.get(position))) {
               // Toast.makeText(context, "Players selected count "+selectedPlayers.size(), Toast.LENGTH_LONG).show();
                selectedPlayers.remove(squad2Players.get(position));
            }
        }
    }


}
