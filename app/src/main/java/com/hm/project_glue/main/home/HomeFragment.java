package com.hm.project_glue.main.home;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hm.project_glue.R;
import com.hm.project_glue.main.MainActivity;
import com.hm.project_glue.main.OnFragmentInteractionListener;
import com.hm.project_glue.main.home.data.HomeData;
import com.hm.project_glue.main.home.data.HomeResponse;

import java.util.ArrayList;

import static com.hm.project_glue.main.MainActivity.metrics;


public class HomeFragment extends Fragment implements HomePresenter.View{
    private static final String TAG = "HomeFragment";
    private LinearLayoutManager linearLayoutManager;
    private HomePresenter homePresenter;
    private RecyclerView recyclerView;
    public static ArrayList<HomeResponse> homeResponses = null;
    private HomeData homeData;
    HomeRecyclerAdapter adapter;
    LinearLayout linearNoGroup;

    private static OnFragmentInteractionListener mListener;
    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            return;
        }
        homePresenter = new HomePresenterImpl(HomeFragment.this);

        homePresenter.setView(this);
        homeData = HomeData.newHomeInstance();
        Log.i(TAG, "----------- HomeData.newHomeInstance ----- " + homeData.getHomeResponses());
        homeResponses = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.i(TAG, "----------- onCreateView");
        if(savedInstanceState == null){
            homePresenter.callHttp(homeData);
        }
        FloatingActionButton fab;

        fab = (FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 2016.12.18 AddGroupActivity.class로 이동하도록 설정
                ((MainActivity)getActivity()).moveActivity(3);
            }
        });
        linearNoGroup = (LinearLayout) view.findViewById(R.id.gr_linearNoGroup);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView)view.findViewById(R.id.homeRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new HomeRecyclerAdapter(homeResponses,
                R.layout.fragment_home_item, recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        Log.i(TAG, "----------- end of onCreateView");
        return view;
    }

    @Override
    public void dataChanged(HomeData res) {
        if(res.getHomeResponses().size() == 0){
            linearNoGroup.setVisibility(View.VISIBLE);
        }else{
            linearNoGroup.setVisibility(View.GONE);
        }
        Log.i(TAG, "----------- dataChanged --- " + res.getHomeResponses());
        homeResponses.addAll(res.getHomeResponses());
        adapter.notifyDataSetChanged();
    }

    private static class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>{
        ArrayList<HomeResponse> datas;
        int itemLayout;
        Context context;

        public HomeRecyclerAdapter(ArrayList<HomeResponse> datas, int itemLayout, Context context) {
            this.datas = datas;
            this.itemLayout = itemLayout;
            this.context = context;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            HomeResponse response = datas.get(position);

            holder.ivHomeCard.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mListener.goToListFragment(response.getId());
                    
                }
            });

            String url="";
            Log.i(TAG, "----------- onBindViewHolder : if ---- " + response.getGroup_image());
//            if(response.getGroup_image() == null){
//                url = "";
//                holder.ivHomeCard.setVisibility(View.GONE);
//            }else{
//
//            }

            url = response.getGroup_image().getThumbnail();
            if(url == null){
                Log.i(TAG, "----------- image URL ---- url == null");
                // 서버에 이미지가 없을 경우, 로고를 기본 이미지로 보여준다.
                // holder.ivHomeCard.setImageResource(R.drawable.gluelogo_pu);
                Glide.with(context).load(R.drawable.gluelogo_pu).override(holder.cardWidth, holder.cardWidth)
                        .centerCrop().into(holder.ivHomeCard);
            } else {
                Log.i(TAG, "----------- image URL ---- "+ url);
                Glide.with(context).load(url).override(holder.cardWidth, holder.cardWidth)
                        .centerCrop().into(holder.ivHomeCard);
            }

            holder.tvHomeCard.setText(response.getGroup_name());
            holder.cardView.setTag(response);
            setAnimation(holder.cardView, position);
        }

        int lastPosition = -1;
        public void setAnimation(View view, int position){
            if(position > lastPosition){
                Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
                view.startAnimation(animation);
                lastPosition = position;
            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivHomeCard;
            TextView tvHomeCard;
            CardView cardView;
            // double cardHeight;
            int cardWidth;

            public ViewHolder(View itemView) {
                super(itemView);
                cardView   = (CardView) itemView.findViewById(R.id.homeCardView);
                ivHomeCard = (ImageView)itemView.findViewById(R.id.ivHomeCard);
                tvHomeCard = (TextView) itemView.findViewById(R.id.tvHomeCard);

                Log.i(TAG, "----------- metrics.xdpi / metrics.DENSITY_DEFAULT ---- " + metrics.xdpi
                        + " // "+ metrics.DENSITY_DEFAULT);
                int px = Math.round(3 * (metrics.xdpi / metrics.DENSITY_DEFAULT));

                ViewGroup.LayoutParams params =  cardView.getLayoutParams();
                cardWidth = (metrics.widthPixels / 2)-(px * 2);
//                params.width = (metrics.widthPixels / 2)-(px * 2);
//                params.height = params.width;
            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}