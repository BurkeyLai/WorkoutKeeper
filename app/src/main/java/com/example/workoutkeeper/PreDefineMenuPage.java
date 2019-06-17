package com.example.workoutkeeper;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class PreDefineMenuPage extends Fragment {

    private RecyclerView mRecyclerView;
    private PreDefineMenuAdapter mAdapter;
    private final LinkedList<String> mProgramTitle = new LinkedList<>();
    private final LinkedList<Integer> mProgramIcon = new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.predefine_menu_page_content, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        // Make sure that when you switch between each fragment, the fragment layout should be initialized.
        mRecyclerView = null;
        mAdapter = null;

        String[] programTitleList = getResources().getStringArray(R.array.predefine_menu_titles);

        for (int i = 0; i < programTitleList.length; i++) {
            mProgramTitle.addLast(programTitleList[i]);
        }
        mProgramIcon.addLast(R.drawable.ic_chest);
        mProgramIcon.addLast(R.drawable.ic_back);
        mProgramIcon.addLast(R.drawable.ic_leg);
        mProgramIcon.addLast(R.drawable.ic_shoulder);
        mProgramIcon.addLast(R.drawable.ic_arm);
        mProgramIcon.addLast(R.drawable.ic_abs);

        if (getView() != null) {
            mRecyclerView = (RecyclerView) getView().findViewById(R.id.program_recyclerview);
        }
        mAdapter = new PreDefineMenuAdapter(getContext(), mProgramTitle, mProgramIcon);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
