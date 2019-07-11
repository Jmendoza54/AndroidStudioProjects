package com.example.responsiveui.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.responsiveui.NewNoteDialogFragment;
import com.example.responsiveui.NewNoteDialogViewModel;
import com.example.responsiveui.R;
import com.example.responsiveui.db.entity.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private List<NoteEntity> noteEntityList;
    private MyNoteRecyclerViewAdapter adapterNotes;
    private NewNoteDialogViewModel noteViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NoteFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NoteFragment newInstance(int columnCount) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        //Indicamos que el fragmento tiene un menu de opciones propio
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if(view.getId() == R.id.listPortrait){
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                DisplayMetrics dpMetrics = context.getResources().getDisplayMetrics();
                float dpWidth = dpMetrics.widthPixels / dpMetrics.density;
                int numCol = (int) (dpWidth/ 180);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(numCol, StaggeredGridLayoutManager.VERTICAL));
            }

            noteEntityList = new ArrayList<>();

            adapterNotes = new MyNoteRecyclerViewAdapter(noteEntityList, getActivity());
            recyclerView.setAdapter(adapterNotes);

            lanzarViewModel();
        }
        return view;
    }

    private void lanzarViewModel() {
        noteViewModel = ViewModelProviders.of(getActivity()).get(NewNoteDialogViewModel.class);
        noteViewModel.getAllNotes().observe(getActivity(), new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                adapterNotes.setNewNotes(noteEntities);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_note_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_note:
                showDialogNewNote();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }    }

    private void showDialogNewNote() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        NewNoteDialogFragment dialogNewNote = new NewNoteDialogFragment();
        dialogNewNote.show(fm, "NewNoteDialogFragment");
    }
}
