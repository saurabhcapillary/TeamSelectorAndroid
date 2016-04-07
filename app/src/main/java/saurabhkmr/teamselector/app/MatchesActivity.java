package saurabhkmr.teamselector.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import saurabhkmr.teamselector.app.adapter.CustomAdapter;
import saurabhkmr.teamselector.app.models.Matches;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabhkmr on 23/3/16.
 */
public class MatchesActivity extends BaseActivity {

    ListView lv;
    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches);

        context=this;

        lv=(ListView) findViewById(R.id.matchesListView);
        Bundle extras = getIntent().getExtras();
        List<Matches> matches = (ArrayList<Matches>) extras.getSerializable("matches");
        lv.setAdapter(new CustomAdapter(this, matches));
    }
}
