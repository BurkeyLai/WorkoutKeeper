package com.example.workoutkeeper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.LinkedList;

import static android.content.Context.MODE_PRIVATE;

public class PreDefineMenuPage extends Fragment {

    private RecyclerView mRecyclerView;
    private PreDefineMenuAdapter mAdapter;
    private final LinkedList<String> mProgramTitle = new LinkedList<>();
    private final LinkedList<Integer> mProgramIcon = new LinkedList<>();

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "CustomizedRecipeDataSet";

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

        if (getView() != null) {
            mRecyclerView = (RecyclerView) getView().findViewById(R.id.program_recyclerview);
        }
        mAdapter = new PreDefineMenuAdapter(getContext(), mProgramTitle, mProgramIcon);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final String[] programTitleList = getResources().getStringArray(R.array.predefine_menu_titles);

        for (int i = 0; i < programTitleList.length; i++) {
            mProgramTitle.addLast(programTitleList[i]);
        }
        mProgramIcon.addLast(R.drawable.ic_chest);
        mProgramIcon.addLast(R.drawable.ic_back);
        mProgramIcon.addLast(R.drawable.ic_leg);
        mProgramIcon.addLast(R.drawable.ic_shoulder);
        mProgramIcon.addLast(R.drawable.ic_arm);
        mProgramIcon.addLast(R.drawable.ic_abs);

        mPreferences = getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        int swipeDirs = ItemTouchHelper.LEFT;
        // Helper class for creating swipe to dismiss and drag and drop functionality.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                /*ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT*/
                swipeDirs) {
            /**
             * Defines the drag and drop functionality.
             *
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The SportsViewHolder that is being moved
             * @param target The SportsViewHolder that you are switching the
             *               original one with.
             * @return true if the item was moved, false otherwise
             */

            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Get the from and to positions.
                /*
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                // Swap the items and notify the adapter.
                Collections.swap(mProgramTitle, from, to);
                Collections.swap(mProgramIcon, from, to);
                mAdapter.notifyItemMoved(from, to);
                */
                return true;
            }

            /**
             * Defines the swipe to dismiss functionality.
             *
             * @param viewHolder The viewholder being swiped.
             * @param direction The direction it is swiped in.
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                final RecyclerView.ViewHolder holder = viewHolder;
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Delete these?");
                builder.setMessage("You will lost your customized recipes.");
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mAdapter.notifyDataSetChanged();
                        builder.create();
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (mProgramTitle.get(holder.getAdapterPosition()).equals(programTitleList[0]) ||
                                mProgramTitle.get(holder.getAdapterPosition()).equals(programTitleList[1]) ||
                                mProgramTitle.get(holder.getAdapterPosition()).equals(programTitleList[2]) ||
                                mProgramTitle.get(holder.getAdapterPosition()).equals(programTitleList[3]) ||
                                mProgramTitle.get(holder.getAdapterPosition()).equals(programTitleList[4]) ||
                                mProgramTitle.get(holder.getAdapterPosition()).equals(programTitleList[5])) {

                            // Remove the item from the dataset.
                            mProgramTitle.remove(holder.getAdapterPosition());
                            mProgramIcon.remove(holder.getAdapterPosition());
                            // Show up the hint text when list is empty

                            // Notify the adapter.
                            mAdapter.notifyItemRemoved(holder.getAdapterPosition());
                        } else {
                            // Delete data in SharedPreferences
                            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                            int index = holder.getAdapterPosition() - mProgramIcon.size();

                            preferencesEditor.putInt("Customized_Key_" + index, 0);
                            preferencesEditor.remove("Customized_Key_" + index + "_Title");
                            int num = mPreferences.getInt("Customized_Key_" + index + "_Size", 0);
                            for (int i = 0; i < num; i++) {
                                preferencesEditor.remove("Customized_Key_" + index + "_" + i + "_Action");
                                preferencesEditor.remove("Customized_Key_" + index + "_" + i + "_Weights");
                                preferencesEditor.remove("Customized_Key_" + index + "_" + i + "_Unit");
                                preferencesEditor.remove("Customized_Key_" + index + "_" + i + "_Sets");
                                preferencesEditor.remove("Customized_Key_" + index + "_" + i + "_Reps");
                                preferencesEditor.remove("Customized_Key_" + index + "_" + i + "_Time");
                            }
                            preferencesEditor.apply();

                            // Remove the item from the dataset.
                            mProgramTitle.remove(holder.getAdapterPosition());
                            // Notify the adapter.
                            mAdapter.notifyItemRemoved(holder.getAdapterPosition());
                        }
                    }
                });
                builder.show();
            }
        });

        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Load title name when onStart.

        for (int i = 0; i < 20; i++) {
            if (mPreferences.getString("Customized_Key_" + i + "_Title", null) == null) {
                break;
            } else {
                mProgramTitle.addLast(mPreferences.getString("Customized_Key_" + i + "_Title", null));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Remove title name when onStop.
        for (int j = mProgramTitle.size(); j > 6; j--) {
            mProgramTitle.removeLast();
        }
        mAdapter.notifyDataSetChanged();
    }

    /*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("Fragment 1", "onAttach");
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
        Log.d("Fragment 1", "onResume");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("Fragment 1", "onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Fragment 1", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Fragment 1", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Fragment 1", "onDetach");
    }
    */
}
