package com.hungryshark.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class NotesFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    boolean isLandscape;
    private Note currentNote;

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

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;

        String[] notes = getResources().getStringArray(R.array.notes);

        for (int i = 0; i < notes.length; i++) {
            String note = notes[i];
            TextView title = new TextView(getContext());
            title.setText(note);
            title.setTextSize(30);
            layoutView.addView(title);
            final int fi = i;
            title.setOnClickListener(v -> {
                currentNote = new Note(getResources().getStringArray(R.array.notes)[fi],
                        getResources().getStringArray(R.array.date)[fi]);
                showNotes(currentNote);
            });
        }
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

    private void showNotes(Note note) {
        if (isLandscape) {
            showLandNotes(note);
        } else {
            showPortNotes(note);
        }
    }

    private void showPortNotes(Note note) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteActivity.class);
        intent.putExtra(NoteFragment.ARG_NOTE, note);
        startActivity(intent);
    }

    private void showLandNotes(Note note) {
        NoteFragment detail = NoteFragment.newInstance(note);
        FragmentActivity context = getActivity();
        if (context != null) {
            FragmentManager fragmentManager = context.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.note_container_land, detail);
            fragmentTransaction.commit();
        }

    }
}