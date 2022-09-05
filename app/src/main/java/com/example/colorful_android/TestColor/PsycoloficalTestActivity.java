package com.example.colorful_android.TestColor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.DTO.PsycologicaltestAnswerDTO;
import com.example.colorful_android.DTO.PsycologicaltestQuestionDTO;
import com.example.colorful_android.Model.User;
import com.example.colorful_android.R;
import com.example.colorful_android.Retrofit.MyRetrofit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PsycoloficalTestActivity extends AppCompatActivity {

    // questions index = question id - 1
    private List<PsycologicaltestQuestionDTO> questions;
    // answers index = answer id -1
    private List<PsycologicaltestAnswerDTO> answers;

    // question Id List = question id = index - 1
    private ArrayList<Integer> questionIdList;
    private HashMap<Integer, int[]> connector;

    private TextView question;
    private Button firstAnswer;
    private Button secondAnswer;
    private TextView textviewIndex;
    private Button next_button;
    private Button prev_button;

    private int index;
    private int select;

    private int[] user_answers;

    private Button[] answerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("oncreate", "start --------");

        this.excute_GETQuestions();

        Log.e("oncreate", "end getquestion");

        setContentView(R.layout.psychologial_test);

        this.question = findViewById(R.id.question);
        this.firstAnswer = findViewById(R.id.first_answer);
        this.secondAnswer = findViewById(R.id.second_answer);
        this.textviewIndex = findViewById(R.id.textview_index);
        this.next_button = findViewById(R.id.next_button_psy);
        this.prev_button = findViewById(R.id.prev_button_psy);

//        this.answerButton = new Button[2];
//        this.answerButton[0] = next_button;
//        this.answerButton[1] = prev_button;

        this.index = 1;
        this.select = -1;

        this.user_answers = new int[13];
        Arrays.fill(this.user_answers, -1);
        this.user_answers[0] = -7;

        this.firstAnswer.setOnClickListener(v -> {
            Log.e("answer click", "firstAnswer : " + index);
            this.select = 1;
            this.selectAnswer(select, index);
        });


        this.secondAnswer.setOnClickListener(v -> {
            Log.e("answer click", "secondAnswer : " + index);
            this.select = 2;
            this.selectAnswer(select, index);
        });

        this.next_button.setOnClickListener(v -> {
//            this.setButtonPress(1);
            if(index == 11) {this.next_button.setText("결과보기");}

            if(index == 12) {
                this.finishPsyTest(user_answers);
            } else {
                if (user_answers[index] == -1) {
                Toast.makeText(getBaseContext(), "답변을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    //
                } else {
                    if (index == 1) {this.prev_button.setVisibility(View.VISIBLE);}
                    index += 1;
                    this.nextQuestion(index);
                    this.setButtonPress(index);
                    this.select = -1;
                }
            }
        });

        this.prev_button.setOnClickListener(v -> {
            if(index == 2) {this.prev_button.setVisibility(View.INVISIBLE);}
            if(index == 12) {this.next_button.setText("다음");}

            index -= 1;
            this.nextQuestion(index);
        }) ;
    }

    private void finishPsyTest(int[] user_answers) {
        HashMap<String, Integer> resultMap = new HashMap<>();
        resultMap.put("E", 0); resultMap.put("I", 0);
        resultMap.put("S", 0); resultMap.put("N", 0);
        resultMap.put("T", 0); resultMap.put("F", 0);
        resultMap.put("J", 0); resultMap.put("P", 0);
        for(int i = 0; i < 12; i++) {
            if(user_answers[i] == 1) {
                resultMap.put(answers.get(connector.get(questionIdList.get(i))[0]).getType(), resultMap.get(answers.get(connector.get(questionIdList.get(i))[0]).getType())+1);
            } else {
                resultMap.put(answers.get(connector.get(questionIdList.get(i))[1]).getType(), resultMap.get(answers.get(connector.get(questionIdList.get(i))[1]).getType())+1);
            }
        }

        StringBuilder b = new StringBuilder();
        if(resultMap.get("E") > resultMap.get("I")) {b.append("E");
        } else {b.append("I");}
        if(resultMap.get("S") > resultMap.get("N")) {b.append("S");
        } else {b.append("N");}
        if(resultMap.get("T") > resultMap.get("F")) {b.append("T");
        } else {b.append("F");}
        if(resultMap.get("J") > resultMap.get("P")) {b.append("J");
        } else {b.append("P");}

        Log.e("MBTI :", b.toString());
        excute_UpdateResult(this, b.toString());

        TestMainActivity.testMainActivity.finish();
        finish();
    }

    private void nextQuestion(int index) {
        setButtonPress(index);
        int questionId = questionIdList.get(index-1);
        textviewIndex.setText("(" + (index) + "/12)");
        question.setText(questions.get(questionId).getContent());

        firstAnswer.setText(answers.get(connector.get(questionId)[0]).getContent());
        secondAnswer.setText(answers.get(connector.get(questionId)[1]).getContent());
    }

    private void selectAnswer(int select, int index) {
        user_answers[index] = select;
        setStrokeInAnswer(index);

//        String answer_select = "";
//        for(int i : user_answers) {
//            answer_select += i + ", ";
//        }
//        Log.e("test", "answer_select : " + answer_select + ", index : " + index);
//        Log.e("answer", "first : " + this.firstAnswer.getSolidColor() + ", second : " + this.secondAnswer.getSolidColor());
    }

    private void setButtonPress(int index) {

//        String userAnswersString = "";
//        for(int i : user_answers) {
//            userAnswersString += i +", ";
//        }
//        Log.e("test", "userAnswersString : " + userAnswersString + ", index : " + index);

        setStrokeInAnswer(index);
        if(user_answers[index] == 1) {
            this.firstAnswer.callOnClick();
        } else if(user_answers[index] == 2) {
            this.secondAnswer.callOnClick();
        }
    }

    private void setStrokeInAnswer(int index) {
        if(user_answers[index] == 1) {
            this.firstAnswer.setBackground(getResources().getDrawable(R.drawable.psy_test_answer_select_btn));
            this.secondAnswer.setBackground(getResources().getDrawable(R.drawable.psy_test_answer_non_select_btn));
        } else if(user_answers[index] == 2) {
            this.firstAnswer.setBackground(getResources().getDrawable(R.drawable.psy_test_answer_non_select_btn));
            this.secondAnswer.setBackground(getResources().getDrawable(R.drawable.psy_test_answer_select_btn));
        } else {
            this.firstAnswer.setBackground(getResources().getDrawable(R.drawable.psy_test_answer_non_select_btn));
            this.secondAnswer.setBackground(getResources().getDrawable(R.drawable.psy_test_answer_non_select_btn));
        }
    }


    private ArrayList<Integer> makeRandomList() {
        HashSet<Integer> set;
        ArrayList<Integer> questionIdList = new ArrayList<>();

        for(int i = 1; i < 5; i++) {
            int max = 15 * i;
            int min = 15 * (i-1) +1;
            set = new HashSet<>();
            while(set.size() < 3) { set.add((int)(Math.random() * (max - min + 1))+min); }

            Iterator<Integer> it = set.iterator();
            while (it.hasNext()) {
                questionIdList.add(it.next());
            }

        }
        Collections.shuffle(questionIdList);

//        questionIdList.set(0, 1);
//        questionIdList.set(11, 40);

        return questionIdList;
    }

    private HashMap<Integer, int[]> getConnector(ArrayList<Integer> questionIdList) {
        HashMap<Integer, int[]> connector = new HashMap<>();
        for(int questionId : questionIdList) {
            connector.put(questionId, new int[] {0, 0});
        }

        for(int questionId : questionIdList) {
            int index = 0;
            for(PsycologicaltestAnswerDTO answer : answers) {
                if(answer.getQuestionId() == questionId) {
                    connector.get(questionId)[index] = answer.getAnswerId();
                    index++;
                }
            }
        }

        return connector;
    }
    private void excute_GETQuestions() {
        Call<List<PsycologicaltestQuestionDTO>> call_q = MyRetrofit.getApiService().getPsycologicalTestQuestion();
        call_q.enqueue(new Callback<List<PsycologicaltestQuestionDTO>>() {
            @Override
            public void onResponse(Call<List<PsycologicaltestQuestionDTO>> call, Response<List<PsycologicaltestQuestionDTO>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                questions = response.body();
                Log.e("getQuestion", "questions.size : " + questions.size());
                excute_GETAnswer();
            }
            @Override
            public void onFailure(Call<List<PsycologicaltestQuestionDTO>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    private void excute_GETAnswer() {
        Call<List<PsycologicaltestAnswerDTO>> call_a = MyRetrofit.getApiService().getPsycologicalTestAnswer();
        call_a.enqueue(new Callback<List<PsycologicaltestAnswerDTO>>() {
            @Override
            public void onResponse(Call<List<PsycologicaltestAnswerDTO>> call, Response<List<PsycologicaltestAnswerDTO>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                answers = response.body();

                questionIdList = makeRandomList();
                connector = getConnector(questionIdList);
                prev_button.setVisibility(View.INVISIBLE);

//                Log.e("getAnswer", "questions.size : " + questionIdList.size());
//                Log.e("getAnswer", "connector.size : " + connector.size());
//
//                Log.e("excute", "questions : " + questions.size());
//                Log.e("excute", "answers : " + answers.size());
//                Log.e("excute", "questionIdList : " + questionIdList.size());
//                Log.e("excute", "connector : " + connector.size());

                nextQuestion(index);
            }
            @Override
            public void onFailure(Call<List<PsycologicaltestAnswerDTO>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    private void excute_UpdateResult(PsycoloficalTestActivity psycoloficalTestActivity, String result) {
        Call<Integer> call_a = MyRetrofit.getApiService().updatePsycologicalResult(User.getInstance().getCustomerId(), result);
        call_a.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }

                Intent next_button_intent = new Intent(psycoloficalTestActivity, PsycologicalTestResult.class);
                next_button_intent.putExtra("result", result);
                startActivity(next_button_intent);
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}
