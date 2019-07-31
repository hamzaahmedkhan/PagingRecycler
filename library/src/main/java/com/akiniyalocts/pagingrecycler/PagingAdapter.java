package com.example.buttonsmasher.adapter.PagingAdapterBS;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams;


public abstract class PagingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static int VIEW_TYPE_PAGING = -100;
    private final Handler postHandler = new Handler();
    protected boolean paging = false;
    private PagingDelegate pagingDelegate;

    public PagingAdapter() {
    }

    public abstract int getPagingLayout();

    public abstract int getPagingItemCount();

    public void setPagingDelegate(PagingDelegate pagingDelegate) {
        this.pagingDelegate = pagingDelegate;
    }

    public void setPaging() {
        if (!this.paging) {
            this.paging = true;
            Runnable r = new Runnable() {
                public void run() {
                    PagingAdapter.this.notifyItemInserted(PagingAdapter.this.getItemCount() + 1);
                }
            };
            this.postHandler.post(r);
        }

    }

    public int getItemCount() {
        return this.paging ? this.getPagingItemCount() + 1 : this.getPagingItemCount();
    }

    public void donePaging() {
        this.paging = false;
        this.notifyItemRemoved(this.getItemCount() + 1);
    }

    public int getItemViewType(int position) {
        return this.paging ? VIEW_TYPE_PAGING : super.getItemViewType(position);
    }


    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (this.pagingDelegate != null && this.pagingDelegate.isFullspanLoadingView() && this.pagingDelegate.getRecyclerView().getLayoutManager() instanceof StaggeredGridLayoutManager) {
            LayoutParams layoutParams = (LayoutParams)holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }

    }


}
