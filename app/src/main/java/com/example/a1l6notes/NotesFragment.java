package com.example.a1l6notes;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class NotesFragment extends Fragment {

    private Note currentNote;
    private boolean isLandscape;
    public static final String CURRENT_NOTE = "CurrentNote";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout)view;
        String[] notes_name = getResources().getStringArray(R.array.notes_name);

        for(int i=0; i < notes_name.length; i++){
            String note = notes_name[i];
            TextView textView = new TextView(getContext());
            textView.setText(note);
            textView.setTextSize(30);
            layoutView.addView(textView);
            final int fi = i;
            textView.setOnClickListener(v -> {
                currentNote = new Note(getResources().getStringArray(R.array.notes_name)[fi],
                        getResources().getStringArray(R.array.notes_description)[fi],
                        getResources().getStringArray(R.array.notes_date)[fi]);
                showNoteDetails(currentNote);
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

        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new Note(getResources().getStringArray(R.array.notes_name)[0],
                    getResources().getStringArray(R.array.notes_description)[0],
                    getResources().getStringArray(R.array.notes_date)[0]);
        }

        if (isLandscape) {
            showLandNoteDetails(currentNote);
        }
    }

    private void showNoteDetails(Note currentNote) {
        if (isLandscape) {
            showLandNoteDetails(currentNote);
        } else {
            showPortNoteDetails(currentNote);
        }
    }

    private void showPortNoteDetails(Note currentNote) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteActivity.class);
        intent.putExtra(NoteDetailsFragment.ARG_NOTE, currentNote);

        startActivity(intent);
    }

    private void showLandNoteDetails(Note currentNote) {
        NoteDetailsFragment details = NoteDetailsFragment.newInstance(currentNote);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_details, details);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


}
