package com.patrick.guidebookcodechallenge;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * RecyclerView Adapter for upcoming guides list
 */
public class UpcomingGuideAdapter extends ArrayAdapter<GuideDataModel> {
    private List<GuideDataModel> guideModelList;
    private Context context;
    private LayoutInflater mInflator;

    UpcomingGuideAdapter(Context context, List<GuideDataModel> guideObjects) {
        super(context, 0, guideObjects);
        this.context = context;
        this.mInflator = LayoutInflater.from(context);
        guideModelList = guideObjects;
    }

    private GuideDataModel getGuideItem(int position) {
        return guideModelList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            View view = mInflator.inflate(R.layout.guide_item, parent, false);
            viewHolder = ViewHolder.create((RelativeLayout) view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GuideDataModel guideModel = getGuideItem(position);

        // MILESTONE 4: In addition the object’s name, have your view display
        // the image located at each object’s icon url.

        // load image
        Picasso.with(context).load(guideModel
                .getIcon())
                .placeholder(R.drawable.ic_action_name)
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.iconView);

        viewHolder.nameView.setText(guideModel.getName());
        viewHolder.cityView.setText(guideModel.getCity());
        viewHolder.stateView.setText(guideModel.getState());
        viewHolder.startDateView.setText(guideModel.getStartDate());
        viewHolder.endDateView.setText(String.format(" - %s", guideModel.getStartDate()));

        // Handle if city and state objects are not present
        if (guideModel.getState() == "" && guideModel.getCity() == "") {
            viewHolder.cityStateLayout.setVisibility(View.GONE);
        } else if (guideModel.getCity() == "") {
            viewHolder.cityView.setVisibility(View.INVISIBLE);
        } else if (guideModel.getState() == "") {
            viewHolder.stateView.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.cityStateLayout.setVisibility(View.VISIBLE);
            viewHolder.cityView.setVisibility(View.VISIBLE);
            viewHolder.stateView.setVisibility(View.VISIBLE);
        }

        return viewHolder.rootView;
    }

    /**
     * Viewholder contains handles to all of the views
     */
    static class ViewHolder {
        final RelativeLayout rootView;
        final LinearLayoutCompat cityStateLayout;
        final ImageView iconView;
        final TextView nameView;
        final TextView startDateView;
        final TextView endDateView;
        final TextView stateView;
        final TextView cityView;

        private ViewHolder(RelativeLayout rootView,
                           LinearLayoutCompat cityStateLayout,
                           ImageView iconView,
                           TextView nameView,
                           TextView startDateView,
                           TextView endDateView,
                           TextView stateView,
                           TextView cityView) {
            this.rootView = rootView;
            this.cityStateLayout = cityStateLayout;
            this.iconView = iconView;
            this.nameView = nameView;
            this.startDateView = startDateView;
            this.endDateView = endDateView;
            this.stateView = stateView;
            this.cityView = cityView;
        }

        private static ViewHolder create(RelativeLayout rootView) {
            LinearLayoutCompat cityStateLayout = rootView.findViewById(R.id.city_and_state_layout);
            ImageView iconView = rootView.findViewById(R.id.guide_icon_view);
            TextView nameView = rootView.findViewById(R.id.guide_name_tv);
            TextView startDateView = rootView.findViewById(R.id.start_date_tv);
            TextView endDateView = rootView.findViewById(R.id.end_date_tv);
            TextView stateView = rootView.findViewById(R.id.state_tv);
            TextView cityView = rootView.findViewById(R.id.city_tv);

            return new ViewHolder(rootView, cityStateLayout, iconView, nameView, startDateView, endDateView, stateView, cityView);
        }
    }
}
