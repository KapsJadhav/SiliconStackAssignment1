package com.kaps.siliconstack.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kaps.siliconstack.R;
import com.kaps.siliconstack.model.Callback.EditDeleteCallback;
import com.kaps.siliconstack.model.Note;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context context;
    private List<Note> noteArrayList;
    private EditDeleteCallback editDeleteCallback;

    public NoteAdapter(Context context, List<Note> noteArrayList, EditDeleteCallback editDeleteCallback) {
        this.context = context;
        this.noteArrayList = noteArrayList;
        this.editDeleteCallback = editDeleteCallback;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_adapter_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteArrayList.get(position);
        holder.textViewTitle.setText(note.getTitle());
        holder.textViewBody.setText(note.getBody());
        holder.textViewNote.setText(note.getNote());
        holder.textViewStatus.setText(note.getStatus());
        holder.textViewTime.setText(note.getUpdated_at());

        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogDelete = new Dialog(context, android.R.style.Theme_Translucent);
                dialogDelete.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialogDelete.getWindow().setGravity(Gravity.CENTER);
                dialogDelete.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogDelete.setCancelable(false);
                dialogDelete.setContentView(R.layout.layout_dialog_alert);
                TextView textViewCancel = dialogDelete.findViewById(R.id.textViewCancel);
                TextView textViewSubmit = dialogDelete.findViewById(R.id.textViewSubmit);

                textViewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogDelete.cancel();
                    }
                });
                textViewSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogDelete.cancel();
                        editDeleteCallback.onDelete(note);
                    }
                });
                dialogDelete.show();
            }
        });

        holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDeleteCallback.onEdit(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewTitle)
        TextView textViewTitle;
        @BindView(R.id.textViewBody)
        TextView textViewBody;
        @BindView(R.id.textViewNote)
        TextView textViewNote;
        @BindView(R.id.textViewStatus)
        TextView textViewStatus;
        @BindView(R.id.textViewTime)
        TextView textViewTime;
        @BindView(R.id.imageViewEdit)
        ImageView imageViewEdit;
        @BindView(R.id.imageViewDelete)
        ImageView imageViewDelete;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
