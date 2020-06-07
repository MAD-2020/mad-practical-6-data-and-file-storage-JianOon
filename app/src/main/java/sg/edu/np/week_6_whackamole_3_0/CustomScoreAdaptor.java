package sg.edu.np.week_6_whackamole_3_0;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomScoreAdaptor extends RecyclerView.Adapter<CustomScoreViewHolder> {
    /* Hint:
        1. This is the custom adaptor for the recyclerView list @ levels selection page

     */

    String name;
    final Context context;
    ArrayList<Integer> levellist;
    ArrayList<Integer> scorelist;

    private static final String FILENAME = "CustomScoreAdaptor.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    public CustomScoreAdaptor(UserData userdata, Context c){
        /* Hint:
        This method takes in the data and readies it for processing.
         */
        name = userdata.getMyUserName();
        levellist = userdata.getLevels();
        scorelist = userdata.getScores();
        context = c;
    }

    public CustomScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType)   {
        /* Hint:
        This method dictates how the viewholder layuout is to be once the viewholder is created.
         */
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_select,parent,false);
            final CustomScoreViewHolder holder = new CustomScoreViewHolder(item);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putString("level",String.valueOf(holder.getAdapterPosition()));
                    b.putString("Username",name);
                    Intent activityName = new Intent(context, Main4Activity.class);
                    Log.v("Test", String.valueOf(holder.getAdapterPosition()));
                    activityName.putExtras(b);
                    v.getContext().startActivity(activityName);
                }
            });

            return holder;



        }

    public void onBindViewHolder(CustomScoreViewHolder holder, final int position) {

        /* Hint:
        This method passes the data to the viewholder upon bounded to the viewholder.
        It may also be used to do an onclick listener here to activate upon user level selections.

        Log.v(TAG, FILENAME + " Showing level " + level_list.get(position) + " with highest score: " + score_list.get(position));
        Log.v(TAG, FILENAME+ ": Load level " + position +" for: " + list_members.getMyUserName());
         */

        holder.level.setText("Level " + String.valueOf(levellist.get(position)));
        holder.score.setText("Highest Score: " +String.valueOf(scorelist.get(position)));
        Log.v("test", String.valueOf(getItemCount()))
;    }

    public int getItemCount(){
        /* Hint:
        This method returns the the size of the overall data.
         */
        return levellist.size();
    }
}