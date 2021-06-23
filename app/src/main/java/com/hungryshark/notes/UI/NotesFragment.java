package com.hungryshark.notes.UI;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungryshark.notes.CardNote;
import com.hungryshark.notes.R;
import com.hungryshark.notes.data.CardsCardsSourceImpl;
import com.hungryshark.notes.data.CardsSource;

public class NotesFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    SocialNetworkAdapter myAdapter;
    RecyclerView recyclerView;
    private CardNote cardNote;
    private CardsSource data;
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
        setHasOptionsMenu(true);
        initList(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initList(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        data = new CardsCardsSourceImpl(getResources()).init();
        myAdapter = new SocialNetworkAdapter(data, this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        myAdapter.SetOnItemClickListener((view1, position) -> {
            cardNote = data.getCardNote(position);
            showNotes(cardNote);
        });

        if (getContext() != null) {
            DividerItemDecoration itemDecoration = new
                    DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
            itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
            recyclerView.addItemDecoration(itemDecoration);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_notes, menu);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query,
                        Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                data.addData(new CardNote("Запись" + (data.size() + 1), "Дата" + (data.size() + 1)));
                myAdapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
                myAdapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
                return true;
            case R.id.action_delete:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.popup, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = myAdapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.change:
                return true;
            case R.id.delete:
                data.deleteData(position);
                myAdapter.notifyItemRemoved(position);
                return true;
            case R.id.clear:
                data.clearData();
                myAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, cardNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            cardNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            cardNote = new CardNote(getResources().getStringArray(R.array.notes)[0],
                    getResources().getStringArray(R.array.date)[0]);
        }
        if (isLandscape) {
            showLandNotes(cardNote);
        }
    }

    private void showNotes(CardNote cardNote) {
        if (isLandscape) {
            showLandNotes(cardNote);
        } else {
            showPortNotes(cardNote);
        }
    }

    private void showPortNotes(CardNote cardNote) {
        NoteFragment details = NoteFragment.newInstance(cardNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_container, details)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void showLandNotes(CardNote cardNote) {
        NoteFragment detail = NoteFragment.newInstance(cardNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_container_land, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commitAllowingStateLoss();
    }
}