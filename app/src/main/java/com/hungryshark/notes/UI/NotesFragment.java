package com.hungryshark.notes.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungryshark.notes.Note;
import com.hungryshark.notes.R;

public class NotesFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private Note currentNote;
    private boolean isLandscape;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        String[] notes = getResources().getStringArray(R.array.notes);
        SocialNetworkAdapter myAdapter = new SocialNetworkAdapter(notes);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        LinearLayout layoutView = (LinearLayout) view;
        for (String note : notes) {
            TextView title = new TextView(getContext());
            title.setText(note);
            layoutView.addView(title);
            myAdapter.SetOnItemClickListener((view1, position) -> {

                currentNote = new Note(getResources().getStringArray(R.array.notes)[position],
                        getResources().getStringArray(R.array.date)[position]);
                showNotes(currentNote);

            });
            myAdapter.setMyLongClickListener((view12, position) -> {
                Activity activity = requireActivity();
                PopupMenu popupMenu = new PopupMenu(activity, view12);
                activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.show();
            });
        }

        DividerItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,
                null));
        recyclerView.addItemDecoration(itemDecoration);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new Note(getResources().getStringArray(R.array.notes)[0],
                    getResources().getStringArray(R.array.date)[0]);
        }
        if (isLandscape) {
            showLandNotes(currentNote);
        }
    }

    private void showNotes(Note currentNote) {
        if (isLandscape) {
            showLandNotes(currentNote);
        } else {
            showPortNotes(currentNote);
        }
    }

    private void showPortNotes(Note currentNote) {
        NoteFragment details = NoteFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_container, details)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void showLandNotes(Note currentNote) {
        NoteFragment detail = NoteFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.note_container_land, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commitAllowingStateLoss();
    }
}