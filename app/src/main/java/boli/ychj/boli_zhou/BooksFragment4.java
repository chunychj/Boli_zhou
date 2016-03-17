package boli.ychj.boli_zhou;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import boli.ychj.bean.BoliPic4;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Chenyc on 15/7/1.
 */
public class BooksFragment4 extends Fragment {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ProgressBar mProgressBar;


    private static final int ANIM_DURATION_FAB = 400;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bolis, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        mAdapter = new MyAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mFabButton.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        doSearch(getString(R.string.default_search_keyword));
    }


    private void doSearch(String keyword) {
        mProgressBar.setVisibility(View.VISIBLE);
        mAdapter.clearItems();

        final BmobQuery<BoliPic4> bmobQuery	 = new BmobQuery<BoliPic4>();
   //     bmobQuery.addWhereEqualTo("age", 25);
        bmobQuery.setLimit(200);
        bmobQuery.order("createdAt");
        //先判断是否有缓存
        boolean isCache = bmobQuery.hasCachedResult(getActivity(),BoliPic4.class);
        if(isCache){
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取。
        }else{
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
        }
        bmobQuery.findObjects(getActivity(), new FindListener<BoliPic4>() {

            @Override
            public void onSuccess(List<BoliPic4> object) {
                // TODO Auto-generated method stub
          //      toast("查询成功：共" + object.size() + "条数据。");

                mProgressBar.setVisibility(View.GONE);

                mAdapter.updateItems(object, true);

            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub

            }
        });


//        BoliPic.searchBolis(keyword, new BoliPic.IBoliResponse<List<BoliPic>>() {
//            @Override
//            public void onData(List<BoliPic> bolis) {
//                mProgressBar.setVisibility(View.GONE);
//
//                mAdapter.updateItems(bolis, true);
//            }
//        });
    }


    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {


            BoliPic4 boliPic = mAdapter.getBoliPic(position);
            Intent intent = new Intent(getActivity(), ZoomActivity4.class);

            intent.putExtra("boli", boliPic);

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            view.findViewById(R.id.ivBook), getString(R.string.transition_book_img));

            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());

        }
    };



    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private final int mBackground;
        private List<BoliPic4> mBoliPics = new ArrayList<BoliPic4>();
        private final TypedValue mTypedValue = new TypedValue();

        private static final int ANIMATED_ITEMS_COUNT = 4;

        private boolean animateItems = false;
        private int lastAnimatedPosition = -1;

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(Context context) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public ImageView ivBolipic;
            public TextView tvTitle;
            public TextView tvDesc;

            public int position;

            public ViewHolder(View v) {
                super(v);
                ivBolipic = (ImageView) v.findViewById(R.id.ivBook);
                tvTitle = (TextView) v.findViewById(R.id.tvTitle);
                tvDesc = (TextView) v.findViewById(R.id.tvDesc);
            }
        }


        private void runEnterAnimation(View view, int position) {
            if (!animateItems || position >= ANIMATED_ITEMS_COUNT - 1) {
                return;
            }

            if (position > lastAnimatedPosition) {
                lastAnimatedPosition = position;
                view.setTranslationY(Utils.getScreenHeight(getActivity()));
                view.animate()
                        .translationY(0)
                        .setStartDelay(100 * position)
                        .setInterpolator(new DecelerateInterpolator(3.f))
                        .setDuration(700)
                        .start();
            }
        }


        public void updateItems(List<BoliPic4> boliPics, boolean animated) {
            animateItems = animated;
            lastAnimatedPosition = -1;
            mBoliPics.addAll(boliPics);
            notifyDataSetChanged();
        }

        public void clearItems() {
            mBoliPics.clear();
            notifyDataSetChanged();
        }


        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book_item, parent, false);
            //v.setBackgroundResource(mBackground);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            runEnterAnimation(holder.itemView, position);
            BoliPic4 boliPic = mBoliPics.get(position);
//            holder.tvTitle.setText(book.getTitle());
//            String desc = "作者: " + book.getAuthor()[0] + "\n副标题: " + book.getSubtitle()
//                    + "\n出版年: " + book.getPubdate() + "\n页数: " + book.getPages() + "\n定价:" + book.getPrice();
            holder.tvDesc.setText(R.string.book_description_1);
            Glide.with(holder.ivBolipic.getContext())
                    .load(boliPic.getPic().getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(holder.ivBolipic);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mBoliPics.size();
        }


        public BoliPic4 getBoliPic(int pos) {
            return mBoliPics.get(pos);
        }
    }
}
