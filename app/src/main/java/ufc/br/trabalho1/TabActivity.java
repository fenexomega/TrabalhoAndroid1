package ufc.br.trabalho1;

import android.database.DataSetObserver;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String[] lista = {"Hello","World","Its","A","Pleasure","To","Meet","You"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);


        ViewPagerAdapter adapter = new ViewPagerAdapter();
        adapter.addView(createView(R.layout.content_tab,null),"Texto");
        adapter.addView(createView(R.layout.content_tab_button, new Command() {
            @Override
            public void Run(View view) {
                Button btn = (Button) view.findViewById(R.id.button);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tocarSom();
                    }
                });
            }
        }),"Som");
        adapter.addView(createView(R.layout.content_tab_list, new Command() {
            @Override
            public void Run(View view) {
                ListView list = (ListView) view.findViewById(R.id.listView);
                list.setAdapter(new ArrayAdapter<String>(TabActivity.this,R.layout.simple_list_item,lista));
            }
        }),"Lista");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);





    }

    interface Command{
        void Run(View view);
    }

    private View createView(int viewId, @Nullable Command command)
    {
        View view = LayoutInflater.from(this).inflate(viewId,null);
        if(command != null)
            command.Run(view);
        return view;
    }

    public void tocarSom()
    {
        final MediaPlayer mp = MediaPlayer.create(TabActivity.this,R.raw.som);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.release();
            }
        });
        mp.start();
    }

    class ViewPagerAdapter extends PagerAdapter{
        List<View> views = new ArrayList<>();
        List<String> titles = new ArrayList<>();


        public void addView(View view, String title)
        {
            views.add(view);
            titles.add(title);
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = views.get(position);
            container.removeView(view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return views.indexOf(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position);
            container.addView(view);
            return view;
        }
    }




}
