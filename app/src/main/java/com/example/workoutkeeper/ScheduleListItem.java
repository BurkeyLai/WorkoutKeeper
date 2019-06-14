package com.example.workoutkeeper;

import android.os.Parcel;
import android.os.Parcelable;

class ScheduleListItem implements Parcelable {
    private String action;
    private String weights;
    private String unit;
    private String sets;
    private String reps;
    private String time;

    public ScheduleListItem (String a, String w, String u, String s, String r, String t) {
        this.action = a;
        this.weights = w;
        this.unit = u;
        this.sets = s;
        this.reps = r;
        this.time = t;
    }

    protected ScheduleListItem(Parcel in) {
        action = in.readString();
        weights = in.readString();
        unit = in.readString();
        sets = in.readString();
        reps = in.readString();
        time = in.readString();
    }

    public static final Creator<ScheduleListItem> CREATOR = new Creator<ScheduleListItem>() {
        @Override
        public ScheduleListItem createFromParcel(Parcel in) {
            return new ScheduleListItem(in);
        }

        @Override
        public ScheduleListItem[] newArray(int size) {
            return new ScheduleListItem[size];
        }
    };

    String getAction() { return action; }
    String getWeights() { return weights; }
    String getUnit() { return unit; }
    String getSets() { return sets; }
    String getReps() { return reps; }
    String getTime() { return time; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(action);
        dest.writeString(weights);
        dest.writeString(unit);
        dest.writeString(sets);
        dest.writeString(reps);
        dest.writeString(time);
    }
}
