package com.example.chess;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TutorialFragment extends Fragment {

    View view;
    TextView tutorial;
    ImageView pawn, bishop, king, knight, queen, rook;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tutorial, container, false);
        db = FirebaseFirestore.getInstance();
        tutorial = view.findViewById(R.id.tvTutorial);
        pawn = view.findViewById(R.id.ivPawn);
        bishop = view.findViewById(R.id.ivBishop);
        king = view.findViewById(R.id.ivKing);
        knight = view.findViewById(R.id.ivKnight);
        queen = view.findViewById(R.id.ivQueen);
        rook = view.findViewById(R.id.ivRook);
        pawn.setOnClickListener(this::onClick);
        bishop.setOnClickListener(this::onClick);
        king.setOnClickListener(this::onClick);
        knight.setOnClickListener(this::onClick);
        queen.setOnClickListener(this::onClick);
        rook.setOnClickListener(this::onClick);
        return view;
    }

    public void onClick(View view){
        String piece = "null";
        if (view == pawn){
            piece = "pawn";
        }
        if (view == bishop){
            piece = "bishop";
        }
        if (view == king){
            piece = "king";
        }
        if (view == knight){
            piece = "knight";
        }
        if (view == queen){
            piece = "queen";
        }
        if (view == rook){
            piece = "rook";
        }
        db.collection("Tutorial").document(piece).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Product product = new Product(task.getResult().getString("text"));
                    tutorial.setText(product.getText());
                }
            }
        });
    }
}