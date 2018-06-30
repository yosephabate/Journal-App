package android.yoseph.cyberacademy.edu.et.journalapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener {

    public static final int RC_SIGN_IN = 1;
    public static final String TAG = "OneWeekChallenge";

    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton mSignInButton;

    private ImageView mProfileImage;
    private TextView mProfileName;
    private TextView mProfileEmail;
    private Button mButtonContinue;
    private Button mSignOutButton;

    private String mCurrentSignInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mSignInButton = findViewById(R.id.btn_sign_in);
        mSignInButton.setSize(SignInButton.SIZE_STANDARD);
        mSignInButton.setOnClickListener(this);

        mProfileName = findViewById(R.id.tv_prof_name);
        mProfileEmail = findViewById(R.id.tv_prof_email);
        mProfileImage = findViewById(R.id.iv_prof_picture);

        mButtonContinue = findViewById(R.id.btn_continue);
        mButtonContinue.setOnClickListener(this);

        mSignOutButton = findViewById(R.id.btn_sign_out);
        mSignOutButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            mSignInButton.setEnabled(false);

            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            String fullName = personGivenName + " " + personFamilyName;
            mProfileName.setText(fullName);
            mProfileEmail.setText(personEmail);
            TextDrawable drawable = TextDrawable.builder()
                    .buildRect(personGivenName.substring(0,1) +
                            personFamilyName.substring(0, 1),
                            getResources().getColor(R.color.colorAccent));
            mProfileImage.setImageDrawable(drawable);
            //mProfileImage.setImageURI(personPhoto);

            mButtonContinue.setEnabled(true);
            mSignOutButton.setEnabled(true);
        } else {
            Log.d(TAG, "No account retrieved!");
            mButtonContinue.setEnabled(false);
            mSignOutButton.setEnabled(false);

            mSignInButton.setEnabled(true);
            mProfileName.setText(R.string.unknown_user_label);
            mProfileEmail.setText(R.string.unknown_user_label);
            mProfileImage.setImageResource(R.mipmap.unknown_user);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                signIn();
                break;
            case R.id.btn_sign_out:
                signOut();
                break;
            case R.id.btn_continue:
                continueToMain();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void continueToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, mCurrentSignInUser);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            mCurrentSignInUser = account.getEmail();
            Log.d("TTT", mCurrentSignInUser);
            updateUI(account);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
}
