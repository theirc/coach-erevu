package com.ryanwarsaw.coach_erevu.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.DefaultEventListener;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.AssetDataSource;
import com.google.android.exoplayer2.upstream.AssetDataSource.AssetDataSourceException;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSource.Factory;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.FileDataSource.FileDataSourceException;
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.coach_erevu.MainActivity;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.model.Preferences;

import java.io.File;

public class VideoActivity extends AppCompatActivity {

  private SimpleExoPlayer exoPlayer;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video);

    final Preferences preferences = new GsonBuilder().create()
            .fromJson(getIntent().getStringExtra("preferences"), Preferences.class);

    exoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());

    PlayerView playerView = findViewById(R.id.video_player);
    playerView.setPlayer(exoPlayer);

    // When the video has finished playing, go back to the previous activity.
    exoPlayer.addListener(new DefaultEventListener() {
      @Override
      public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        super.onPlayerStateChanged(playWhenReady, playbackState);
        if (playbackState == Player.STATE_ENDED) {
          MainActivity.getLoggingHandler().write(VideoActivity.this.getClass().getSimpleName(),
              "VIDEO_ENDED_WATCHED_FULL");
          finish();
        }
      }
    });

    exoPlayer.prepare(buildVideoSource(getIntent().getStringExtra("video_name")));
    exoPlayer.setPlayWhenReady(true);
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocused) {
    super.onWindowFocusChanged(hasFocused);
    if (hasFocused) {
      // Hide the system UI when watching a video, but leave the navigation buttons there.
      View decorView = getWindow().getDecorView();
      decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    exoPlayer.setPlayWhenReady(false);
    MainActivity.getLoggingHandler().write(getClass().getSimpleName(), "VIDEO_PAUSED");
  }

  @Override
  public void onResume() {
    super.onResume();
    exoPlayer.setPlayWhenReady(true);
    MainActivity.getLoggingHandler().write(getClass().getSimpleName(), "VIDEO_RESUMED");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    exoPlayer.release();
  }

  /**
   * Builds a new MediaSource object with the filename provided. By default it will
   * check the Download/ folder on your Android device for a file with the name provided. If
   * it is unable to find that file, it will default to the app packaged version in src/main/assets.
   * @param videoName The fully qualified name of the file (including extension).
   * @return MediaSource object for use in the SimpleExoPlayer instance defined above.
   */
  private MediaSource buildVideoSource(String videoName) {
    final File externalFile = new File(Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + videoName);
    Uri videoUri = Uri.parse(externalFile.exists() ? externalFile.toURI().toString() : "assets:///" + videoName);
    final DataSpec dataSpec = new DataSpec(videoUri);

    if (externalFile.exists()) {
      final FileDataSource fileDataSource = new FileDataSource();

      try {
        fileDataSource.open(dataSpec);
      } catch (FileDataSourceException e) {
        e.printStackTrace();
      }

      DataSource.Factory factory = new DataSource.Factory() {
        @Override
        public DataSource createDataSource() {
          return fileDataSource;
        }
      };
      return new ExtractorMediaSource.Factory(factory).createMediaSource(videoUri);
    } else {
      final AssetDataSource assetDataSource = new AssetDataSource(this);

      try {
        assetDataSource.open(dataSpec);
      } catch (AssetDataSourceException e) {
        e.printStackTrace();
      }

      DataSource.Factory factory = new Factory() {
        @Override
        public DataSource createDataSource() {
          return assetDataSource;
        }
      };
      return new ExtractorMediaSource.Factory(factory).createMediaSource(videoUri);
    }
  }
}
