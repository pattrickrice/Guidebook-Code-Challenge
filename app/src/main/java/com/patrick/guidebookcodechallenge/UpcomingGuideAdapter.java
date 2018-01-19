package com.patrick.guidebookcodechallenge;

import android.content.Context;
import android.support.annotation.NonNull;
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

    // Required constructor
    public UpcomingGuideAdapter(Context context, List<GuideDataModel> guideObjects) {
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

        // load image
        Picasso.with(context).load(guideModel
                .getIcon())
                .placeholder(R.drawable.ic_action_name)
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.iconView);

        viewHolder.nameView.setText(guideModel.getName());
        viewHolder.cityView.setText(guideModel.getCity());
        viewHolder.stateView.setText(guideModel.getState());
        viewHolder.startDateView.setText(String.format("%s - ", guideModel.getStartDate()));
        viewHolder.endDateView.setText(guideModel.getEndDate());

        return viewHolder.rootView;
    }

    static class ViewHolder {
        final RelativeLayout rootView;
        final ImageView iconView;
        final TextView nameView;
        final TextView startDateView;
        final TextView endDateView;
        final TextView stateView;
        final TextView cityView;

        private ViewHolder(RelativeLayout rootView,
                           ImageView iconView,
                           TextView nameView,
                           TextView startDateView,
                           TextView endDateView,
                           TextView stateView,
                           TextView cityView) {
            this.iconView = iconView;
            this.rootView = rootView;
            this.nameView = nameView;
            this.startDateView = startDateView;
            this.endDateView = endDateView;
            this.stateView = stateView;
            this.cityView = cityView;
        }

        private static ViewHolder create(RelativeLayout rootView) {
            ImageView iconView = (ImageView) rootView.findViewById(R.id.guide_icon_view);
            TextView nameView = (TextView) rootView.findViewById(R.id.guide_name_tv);
            TextView startDateView = (TextView) rootView.findViewById(R.id.start_date_tv);
            TextView endDateView = (TextView) rootView.findViewById(R.id.end_date_tv);
            TextView stateView = (TextView) rootView.findViewById(R.id.state_tv);
            TextView cityView = (TextView) rootView.findViewById(R.id.city_tv);

            return new ViewHolder(rootView, iconView, nameView, startDateView, endDateView, stateView, cityView);
        }
    }
}
