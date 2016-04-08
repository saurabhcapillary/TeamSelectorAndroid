package saurabhkmr.teamselector.app;


import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import saurabhkmr.teamselector.app.Utils.Utils;
import saurabhkmr.teamselector.app.adapter.CheckBoxListviewAdapter;
import saurabhkmr.teamselector.app.models.Players;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabhkmr on 7/4/16 @Capillary Tech
 */
public class PickTeamActivity extends BaseActivity {

    ListView lv;
    Context context;
    ImageView squad1Img;
    ImageView squad2Img;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_team);
        context = this;
        lv = (ListView) findViewById(R.id.pick_PlayersListView);
        squad1Img=(ImageView)findViewById(R.id.tp_squad1Img);
        squad1Img=(ImageView)findViewById(R.id.tp_squad2Img);
        Bundle extras = getIntent().getExtras();
        List<Players> squad1Players = (ArrayList<Players>) extras.getSerializable("squad1Players");
        List<Players> squad2Players = (ArrayList<Players>) extras.getSerializable("squad2Players");
        Utils.setImage(squad1Img,squad1Players.get(0).getSquadName() );
        Utils.setImage(squad1Img, squad2Players.get(0).getSquadName());
        if(squad1Players!=null && squad1Players.size()>0 && squad2Players!=null && squad2Players.size()>0) {
            lv.setAdapter(new CheckBoxListviewAdapter(PickTeamActivity.this,squad1Players,squad2Players));
        }
    }
}
