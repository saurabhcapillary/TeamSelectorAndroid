package saurabhkmr.teamselector.app;;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import saurabhkmr.teamselector.app.adapter.CustomSquadAdapter;
import saurabhkmr.teamselector.app.models.Players;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by saurabhkmr on 27/3/16.
 */
public class SquadsActivity extends BaseActivity {

    ListView lv;
    Context context;
    LinearLayout linearLayout;
    ImageView squadImageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.squads);

        context=this;

        lv=(ListView) findViewById(R.id.playersListView);
        linearLayout=(LinearLayout)findViewById(R.id.squadLayout);
        squadImageView=(ImageView)findViewById(R.id.squadLogo);
        Bundle extras = getIntent().getExtras();
        List<Players> players = (ArrayList<Players>) extras.getSerializable("players");
        if(players!=null && players.size()>0 && players.size()!=11) {
            long squadId=players.get(0).getSquadId();
            int val=(int)squadId;
            switch (val){
                case 6:
                    linearLayout.setBackgroundColor(Color.BLUE);
                    squadImageView.setImageResource(R.drawable.mi);
                    break;
                case 4:
                    linearLayout.setBackgroundColor(Color.RED);
                    squadImageView.setImageResource(R.drawable.dd);
                    break;
                case 3:
                    linearLayout.setBackgroundColor(Color.RED);
                    squadImageView.setImageResource(R.drawable.rcb);
                    break;
                case 2:
                    linearLayout.setBackgroundColor(Color.parseColor("#F88017"));
                    squadImageView.setImageResource(R.drawable.srh);
                    android.view.ViewGroup.MarginLayoutParams params2 = (android.view.ViewGroup.MarginLayoutParams)
                        squadImageView.getLayoutParams();
                    params2.leftMargin=300;
                    squadImageView.setLayoutParams(params2);
                    break;
                case 5:
                    linearLayout.setBackgroundColor(Color.parseColor("#EE0000"));
                    squadImageView.setImageResource(R.drawable.kx1p);
                    android.view.ViewGroup.MarginLayoutParams params3 = (android.view.ViewGroup.MarginLayoutParams)
                        squadImageView.getLayoutParams();
                    params3.leftMargin=200;
                    squadImageView.setLayoutParams(params3);
                    break;
                case 1:
                    linearLayout.setBackgroundColor(Color.parseColor("#2E0854"));
                    squadImageView.setImageResource(R.drawable.kkr);
                    break;
                case 7:
                    linearLayout.setBackgroundColor(Color.parseColor("#ffa500"));
                    squadImageView.setImageResource(R.mipmap.gl);
                    android.view.ViewGroup.MarginLayoutParams params1 = (android.view.ViewGroup.MarginLayoutParams)
                        squadImageView.getLayoutParams();
                    params1.leftMargin=300;
                    squadImageView.setLayoutParams(params1);

                    break;
                case 8:
                    linearLayout.setBackgroundColor(Color.parseColor("#FAAFBE"));
                    squadImageView.setImageResource(R.mipmap.rps);
                      android.view.ViewGroup.MarginLayoutParams params = (android.view.ViewGroup.MarginLayoutParams)
                        squadImageView.getLayoutParams();
                      params.leftMargin=300;
                    squadImageView.setLayoutParams(params);
                    break;
                default:break;
            }
        }
        else{
            squadImageView.setVisibility(View.INVISIBLE);
        }
        lv.setAdapter(new CustomSquadAdapter(this, players));
    }
}
