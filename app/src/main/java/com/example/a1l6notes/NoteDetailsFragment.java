package com.example.a1l6notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NoteDetailsFragment extends Fragment {
    public static final String ARG_NOTE = "note";
    private Note note;

    public static NoteDetailsFragment newInstance(Note note) {

        NoteDetailsFragment f = new NoteDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_details, container, false);
        TextView noteDescription =  view.findViewById(R.id.note_description);
        TextView noteDate =  view.findViewById(R.id.note_date);
        noteDescription.setText(note.getNoteDescription());
        noteDate.setText(note.getNoteDate());

        TextView noteNameView = view.findViewById(R.id.textView);
        noteNameView.setText(note.getNoteName());
        return view;
    }
}
