package com.acote.mastermind;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMastermindInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MastermindGame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MastermindGame extends Fragment {

    private static final String LOGGER_TAG = "MMGame";

    private static final String ARG_GUESSES = "numGuesses";
    private static final String ARG_DUPES = "dupes";
    private static final String ARG_BLANKS = "blanks";
    private static final String ARG_ServerIP = "serverIP";
    private static final String ARG_serverPort = "serverPort";

    private int mGuesses;
    private boolean mDuplicates;
    private boolean mBlanks;
    private String mServerIP;
    private int mServerPort;

    private LinkedList<MastermindPeg> pegs = new LinkedList<>();
    private RecyclerView feedbackView;
    private MMClient game;

    private OnMastermindInteractionListener mListener;

    public MastermindGame() {
        // Required empty public constructor
    }

    /**
     *
     * @param guesses Number of guesses.
     * @param dupes Duplicates allowed?
     * @param blanks Blanks allowed?
     * @return A new instance of fragment MastermindGame.
     */
    public static MastermindGame newInstance(int guesses, boolean dupes, boolean blanks, String serverIP, int serverPort) {
        MastermindGame fragment = new MastermindGame();
        Bundle args = new Bundle();
        args.putInt(ARG_GUESSES, guesses);
        args.putBoolean(ARG_DUPES, dupes);
        args.putBoolean(ARG_BLANKS, blanks);
        args.putString(ARG_ServerIP, serverIP);
        args.putInt(ARG_serverPort, serverPort);
        fragment.setArguments(args);
        return fragment;
    }

    //Récupère les options de difficulté fournies par l'usager, crée un objet MMSettings (qui sera serialisé et envoyé au serveur)
    //puis crée une thread qui sera chargée de communiquer avec le serveur.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGuesses = getArguments().getInt(ARG_GUESSES);
            mDuplicates = getArguments().getBoolean(ARG_DUPES);
            mBlanks = getArguments().getBoolean(ARG_BLANKS);
            mServerIP = getArguments().getString(ARG_ServerIP);
            mServerPort = getArguments().getInt(ARG_serverPort);
        }

        MMSettings settings = new MMSettings(mGuesses, mDuplicates, mBlanks);

        //Ma class MMClient comporte une interface, MMResponseCallback, qui est un paramètre nécessaire du constructeur.
        //En instanciant un objet anonyme qui implémente ses méthodes, je permet à ce fragment de répondre aux
        //Événements qui se produisent lors de la communication avec le serveur.
        game = new MMClient(mServerIP, mServerPort, settings, new MMClient.MMResponseCallback() {
            @Override
            public void onConnectionReady() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(LOGGER_TAG, "Connection successful!");
                    }
                });
            }

            @Override
            public void onResponse(String guess, MMHint response) {

                if (response.win){
                    win(response);
                }

                else {
                    addFeedback(guess, response);
                }
            }

            @Override
            public void onError(final Exception e) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(LOGGER_TAG, e.getMessage());
                    }
                });
            }
        });

        new Thread(game).start();

    }

    //Quand l'usager gagne, on envoie un message à MainActivity.
    private void win(MMHint response) {
        mListener.onFragmentInteraction(response.score);
    }

    private void addFeedback(final String guess, final MMHint response) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (feedbackView.getAdapter() instanceof  FeedbackAdapter){
                    FeedbackAdapter adapter = (FeedbackAdapter) feedbackView.getAdapter();
                    adapter.getDataset().offerFirst(new MastermindFeedback(guess, response));
                    adapter.notifyDataSetChanged();

                    Toast.makeText(getContext(), "Guesses left: " + response.guessesLeft, Toast.LENGTH_LONG).show();
                    Log.i(LOGGER_TAG, String.valueOf(adapter.getItemCount()));
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View screen = inflater.inflate(R.layout.fragment_mastermind_game, container, false);

        pegs.add(new MastermindPeg(screen.findViewById(R.id.input_pegOne)));
        pegs.add(new MastermindPeg(screen.findViewById(R.id.input_pegTwo)));
        pegs.add(new MastermindPeg(screen.findViewById(R.id.input_pegThree)));
        pegs.add(new MastermindPeg(screen.findViewById(R.id.input_pegFour)));

        feedbackView = screen.findViewById(R.id.game_feedback);

        MMHint testHint = new MMHint();
        testHint.hardMatches = 2;
        testHint.softMatches = 2;

        LinkedList<MastermindFeedback> testDataset =  new LinkedList<>();

        FeedbackAdapter adapter = new FeedbackAdapter(testDataset);
        feedbackView.setAdapter(adapter);

        feedbackView.setLayoutManager(new LinearLayoutManager(getContext()));

        ImageButton btSend = screen.findViewById(R.id.game_btSend);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guess = buildGuess();
                game.addGuessToBuffer(guess);
            }
        });

        return screen;
    }

    private String buildGuess() {
        String guess = "";
        for (MastermindPeg peg: pegs){
            guess += peg.getColorPosition();
        }

        Log.i(LOGGER_TAG, guess);

        return guess;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onConfirmPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(0);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMastermindInteractionListener) {
            mListener = (OnMastermindInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMastermindInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMastermindInteractionListener {
        void onFragmentInteraction(int score);
    }
}
