package com.example.yaroslav.englishapp.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaroslav.englishapp.R;
import com.example.yaroslav.englishapp.controler.LessonController;
import com.example.yaroslav.englishapp.model.Lesson;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by yaroslav on 13.08.15.
 */
public class LingvoTestFragment extends Fragment implements View.OnClickListener, YouTubePlayer.OnInitializedListener {
    public static final String ARG_PLANET_NUMBER = "planet_number";
    TextView tvName;
    Lesson lesson;
    TextView tvTestLink;
    public static  String VIDEO_ID;
    public static final String API_KEY = "AIzaSyDf5azfQWQYKqFbfLr1jKCvorzMAqR6ATk";

    Context context;

    public LingvoTestFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lingvomap_test, container, false);
        int i = getArguments().getInt(ARG_PLANET_NUMBER);
        String lessonName = getResources().getStringArray(R.array.Lingvomap_lessons)[i];
        context = getActivity().getApplicationContext();
        getActivity().setTitle(lessonName);

        tvName = (TextView)rootView.findViewById(R.id.tvVideoName);

        tvTestLink = (TextView)rootView.findViewById(R.id.tvPassTest);
        tvTestLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(lesson.testIntent);
            }
        });
        int TEST_ID = i + 1;
        LessonController controller = new LessonController(getActivity().getApplicationContext());
        if(TEST_ID != -1) {
            lesson = controller.loadFromJson(TEST_ID);
            tvName.setText(lesson.name);
            VIDEO_ID = lesson.videoId;
        }
        /** Initializing YouTube player view **/
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView)rootView.findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(API_KEY, this);
        return rootView;
    }
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(context, "Failured to Initialize!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        /** Start buffering **/
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }

    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
            System.out.println(arg0 == YouTubePlayer.ErrorReason.INTERNAL_ERROR);
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
        }
    };

    @Override
    public void onClick(View view) {

    }
}