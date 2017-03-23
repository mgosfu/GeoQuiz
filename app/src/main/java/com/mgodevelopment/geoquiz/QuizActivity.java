package com.mgodevelopment.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mgodevelopment.geoquiz.models.Question;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static android.widget.Toast.makeText;

public class QuizActivity extends AppCompatActivity {
    // add comment

    private Button mBtnTrue;
    private Button mBtnFalse;
    private ImageButton mBtnNext;
    private ImageButton mBtnPrev;
    private Button mBtnCheat;
    private TextView mTvQuestion;
    private int mQuestionAnsweredCount = 0;
    private int mAnswerScore = 0;
    private static final String KEY_INDEX = "index";

    private static final String TAG = QuizActivity.class.getName();

    private int mCurrentIndex = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy(Bundle) called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop(Bundle) called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause(Bundle) called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume(Bundle) called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart(Bundle) called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null)
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

        mTvQuestion = (TextView) findViewById(R.id.tvQuestion);

        //mBtnTrue = (Button) findViewById(R.id.btnTrue);
        mBtnTrue = (Button) findViewById(R.id.btnTrue);
        mBtnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mBtnFalse = (Button) findViewById(R.id.btnFalse);
        mBtnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mBtnPrev = (ImageButton) findViewById(R.id.btnPrev);
        mBtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = mCurrentIndex > 0 ? (mCurrentIndex - 1) % mQuestionBank.length : mQuestionBank.length - 1;
                updateQuestion();
            }
        });

        mBtnNext = (ImageButton) findViewById(R.id.btnNext);
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mBtnCheat = (Button) findViewById(R.id.btnCheat);
        mBtnCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                startActivity(intent);
            }
        });

        mTvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    private void checkAnswer(boolean userPressedTrue) {
        mQuestionAnsweredCount++;
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        mQuestionBank[mCurrentIndex].setUserAnswer(userPressedTrue);
        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            mQuestionBank[mCurrentIndex].setCorrect(true);
            mAnswerScore++;
        } else {
            messageResId = R.string.incorrect_toast;
            mQuestionBank[mCurrentIndex].setCorrect(false);
        }
        updateButton(userPressedTrue, answerIsTrue);

        showToast(QuizActivity.this, messageResId, Toast.LENGTH_SHORT, Gravity.BOTTOM);

        if (mQuestionAnsweredCount >= mQuestionBank.length) {
            Double score = round((((double)mAnswerScore) / mQuestionAnsweredCount)*100,2);
            String finalMessage = getApplicationContext().getString(R.string.final_score, score.toString(), Integer.toString(mAnswerScore), Integer.toString(mQuestionAnsweredCount));
            showToast(QuizActivity.this, finalMessage, Toast.LENGTH_LONG, Gravity.CENTER_VERTICAL);
        }

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void updateButton(boolean userAnswer, boolean questionAnswer) {
        if (userAnswer && questionAnswer)
            mBtnTrue.setTextColor(Color.GREEN);
        else if (userAnswer && !questionAnswer)
            mBtnTrue.setTextColor(Color.RED);
        else if (!userAnswer && !questionAnswer)
            mBtnFalse.setTextColor(Color.GREEN);
        else
            mBtnFalse.setTextColor(Color.RED);
        mBtnTrue.setClickable(false);
        mBtnFalse.setClickable(false);
    }

    private void resetButton() {
        mBtnTrue.setTextColor(Color.BLACK);
        mBtnFalse.setTextColor(Color.BLACK);
        mBtnTrue.setClickable(true);
        mBtnFalse.setClickable(true);
    }

    private void updateQuestion() {
        resetButton();
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mTvQuestion.setText(question);
        if (mQuestionBank[mCurrentIndex].getCorrect() != null) {
            updateButton(mQuestionBank[mCurrentIndex].isUserAnswer(), mQuestionBank[mCurrentIndex].isAnswerTrue());
        }
    }

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private void showToast(Context context, int message, int toastLength, int toastLocation) {
        Toast toast = makeText(context, message, toastLength);
        toast.setGravity(toastLocation, 0, 0);
        toast.show();
    }

    private void showToast(Context context, String message, int toastLength, int toastLocation) {
        Toast toast = makeText(context, message, toastLength);
        toast.setGravity(toastLocation, 0, 0);
        toast.show();
    }
}
