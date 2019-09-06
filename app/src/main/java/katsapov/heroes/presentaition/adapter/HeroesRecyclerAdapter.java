package katsapov.heroes.presentaition.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import katsapov.heroes.R;
import katsapov.heroes.data.entitiy.Hero;



public class HeroesRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    private List<Hero> mHeroItems;

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hero, parent, false));
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public HeroesRecyclerAdapter(List<Hero> heroItems) {
        this.mHeroItems = heroItems;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == mHeroItems.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return mHeroItems == null ? 0 : mHeroItems.size();
    }

    public void addItems(List<Hero> heroItems) {
        mHeroItems.addAll(heroItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        mHeroItems.add(new Hero());
        notifyItemInserted(mHeroItems.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = mHeroItems.size() - 1;
        Hero item = getItem(position);
        if (item != null) {
            mHeroItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        mHeroItems.clear();
        notifyDataSetChanged();
    }

    private Hero getItem(int position) {
        return mHeroItems.get(position);
    }

    public class ViewHolder extends BaseViewHolder {
        TextView tvName;
        TextView tvGender;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textViewName);
            tvGender = itemView.findViewById(R.id.textViewGender);
        }

        protected void clear() {
        }

        public void onBind(int position) {
            super.onBind(position);
            Hero item = mHeroItems.get(position);

            tvName.setText(item.getName());
            tvGender.setText(item.getGender());
        }
    }

    public class ProgressHolder extends BaseViewHolder {
        ProgressHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void clear() {
        }
    }
}