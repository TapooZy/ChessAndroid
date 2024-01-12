package com.example.chess;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class StartGameFragment extends Fragment {

    View view;
    Button btnStartGame;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_start_game, container, false);
        btnStartGame = view.findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(this::onClick);
        return view;
    }

    public void onClick(View view){
        showStartGameDialog();
    }

    private void showStartGameDialog() {
        final CharSequence[] options = {"Play against a bot", "Play against a friend", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Start a game");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                intent = new Intent(getContext(), Chess.class);
                if (options[item].equals("Play against a bot")) {
                    Toast.makeText(getContext(), "You chose to play against a bot", Toast.LENGTH_SHORT).show();
                    intent.putExtra("againstWhat", "bot");
                    startActivity(intent);
                } else if (options[item].equals("Play against a friend")) {
                    Toast.makeText(getContext(), "You chose to play against a friend", Toast.LENGTH_SHORT).show();
                    intent.putExtra("againstWhat", "friend");
                    startActivity(intent);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}