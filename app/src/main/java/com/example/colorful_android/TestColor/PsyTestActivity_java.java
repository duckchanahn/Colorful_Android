package com.example.colorful_android.TestColor;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.DTO.PsycologicaltestAnswerDTO;
import com.example.colorful_android.DTO.PsycologicaltestQuestionDTO;
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

public class PsyTestActivity_java extends AppCompatActivity {

    private List<PsycologicaltestQuestionDTO> questions;
    private List<PsycologicaltestAnswerDTO> answers;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.excute_GETQuestions();

//        Log.e("Get question success", this.questions.size()+"");
//        Log.e("Get answer success", this.answers.size()+"");

        setContentView(R.layout.psychological_test_ei);

        this.question = findViewById(R.id.question);
        this.firstAnswer = findViewById(R.id.first_answer);
        this.secondAnswer = findViewById(R.id.second_answer);
        this.textviewIndex = findViewById(R.id.textview_index);
        this.next_button = findViewById(R.id.next_button_psy);
        this.prev_button = findViewById(R.id.prev_button_psy);

        this.index = 0;
        this.select = -1;

        this.user_answers = new int[12];
        Arrays.fill(this.user_answers, -1);

        this.firstAnswer.setOnClickListener(v -> {
            this.select = 1;
            Log.e("FirstButton", "click first! ");
//            this.firstAnswer.setBackgroundColor(Color.parseColor(String.valueOf(R.color.main_color)));
//            this.secondAnswer.setBackgroundColor(Color.parseColor(String.valueOf(R.color.black)));
//            this.firstAnswer.setTextColor(Color.parseColor(String.valueOf(R.color.main_color)));
//            this.secondAnswer.setTextColor(Color.parseColor(String.valueOf(R.color.black)));
        });

        this.secondAnswer.setOnClickListener(v -> {
            this.select = 2;
            Log.e("FirstButton", "click second! ");
//            this.secondAnswer.setBackgroundColor(Color.parseColor(String.valueOf(R.color.main_color)));
//            this.firstAnswer.setBackgroundColor(Color.parseColor(String.valueOf(R.color.black)));
//            this.firstAnswer.setTextColor(Color.parseColor(String.valueOf(R.color.black)));
//            this.secondAnswer.setTextColor(Color.parseColor(String.valueOf(R.color.main_color)));
        });

        this.next_button.setOnClickListener(v -> {
            Log.e("select : ", select+"");
            if(select == 11) {
                this.finishPsyTest(user_answers);
            } else {
                this.setButtonPress(index+1);
                if (index == -1) {
                Toast.makeText(getBaseContext(), "답변을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    //
                } else {
                    if (index == 0) {this.prev_button.setVisibility(View.VISIBLE);}
                    index += 1;
                    nextQuestion(index);
                    selectAnswer(select, index);
                    select = -1;
                }
            }
        });

        this.prev_button.setOnClickListener(v -> {
            Log.e("select : ", select+"");

            if(index == 1) {
                this.prev_button.setVisibility(View.INVISIBLE);
            } else {
                this.setButtonPress(index-1);
                index -= 1;
                nextQuestion(index);
            }
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

        Log.e("1", "-------------------------------------");
        Log.e("MBTI :", b.toString());
        Log.e("1", "-------------------------------------");
    }

    private void nextQuestion(int index) {
        int questionId = questionIdList.get(index);
        Log.e("nextQuestion", "index : " + index + ", questionId : " + questionId + ", connector : " + connector.get(questionId)[0] + " / " + connector.get(questionId)[1]);
        textviewIndex.setText("(" + (index+1) + "/12)");
        question.setText(questions.get(questionId).getContent());


        firstAnswer.setText(answers.get(connector.get(questionId)[0]).getContent());
        secondAnswer.setText(answers.get(connector.get(questionId)[1]).getContent());
    }

    private void selectAnswer(int select, int index) {
        user_answers[index] = select;
    }

    private int setButtonPress(int index) {
        if(user_answers[index] == 1) {
            this.firstAnswer.callOnClick();
//            this.firstAnswer.setPressed(true);
//            this.secondAnswer.setPressed(false);
        } else if(user_answers[index] == 2) {
            this.secondAnswer.callOnClick();
//            this.firstAnswer.setPressed(false);
//            this.secondAnswer.setPressed(true);
        }

        return user_answers[index];
    }

    private ArrayList<Integer> makeRandomList() {
        HashSet<Integer> set;
        ArrayList<Integer> questionIdList = new ArrayList<>();

        for(int i = 1; i < 5; i++) {
            int max = 5 * i;
            int min = 5 * (i-1) +1;
            // 5 1 / 10 6 / 15 11
//            System.out.println("max : " + max + ", min : " + min);
            set = new HashSet<>();
            while(set.size() < 3) {
//                int temp = (int)(Math.random() * (max - min + 1))+min;
//                System.out.println("random : " + temp);
                set.add((int)(Math.random() * (max - min + 1))+min);
//                set.add(temp);
            }
            Iterator<Integer> it = set.iterator();
            while (it.hasNext()) {
                questionIdList.add(it.next());
            }
        }


        Collections.shuffle(questionIdList);

        String a = "";
        for(int x : questionIdList) {
            a += x+", ";
        }

        System.out.println("questionIdList : " + a);

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
//                Log.e("조건문 : ", answer.getQuestionId() + " == " + questionIndex);
                if(answer.getQuestionId() == questionId) {
//                    System.out.println(answer.getAnswerId());
//                    Log.e("index", index+"");
//                    System.out.println(connector.get(questionIndex)[0]);
//                    System.out.println(connector.get(questionIndex)[index]);
                    connector.get(questionId)[index] = answer.getAnswerId();
                    index++;
                }
            }

//            Log.e("1", "-------------------------------------");
//            Log.e("1", "questionIndex " + questionIndex + " : "  + connector.get(questionIndex)[0] + ", " + connector.get(questionIndex)[1]);
//            Log.e("1", "-------------------------------------");
        }

        String a = "";
        for(int x : questionIdList) {
            a += " [" + connector.get(x)[0] + ", " + connector.get(x)[1] + "] ";
        }

        System.out.println("connector : " + a);

        return connector;
    }
    private void excute_GETQuestions() {
        //Retrofit 호출

        Log.e("excute()", "start rest api");

        Call<List<PsycologicaltestQuestionDTO>> call_q = MyRetrofit.getApiService().getPsycologicalTestQuestion();
        call_q.enqueue(new Callback<List<PsycologicaltestQuestionDTO>>() {
            @Override
            public void onResponse(Call<List<PsycologicaltestQuestionDTO>> call, Response<List<PsycologicaltestQuestionDTO>> response) {
                Log.e("excute()", "get question");

                if(!response.isSuccessful()){Log.e("연결이 비정상적 : ", "error code : " + response.code()); return;}
                questions = response.body();
                excute_GETAnswer();
                Log.e("rest api q", questions.size()+"");
            }
            @Override
            public void onFailure(Call<List<PsycologicaltestQuestionDTO>> call, Throwable t) {Log.e("연결실패", t.getMessage());}
        });
    }

    private void excute_GETAnswer() {
        Call<List<PsycologicaltestAnswerDTO>> call_a = MyRetrofit.getApiService().getPsycologicalTestAnswer();
        call_a.enqueue(new Callback<List<PsycologicaltestAnswerDTO>>() {
            @Override
            public void onResponse(Call<List<PsycologicaltestAnswerDTO>> call, Response<List<PsycologicaltestAnswerDTO>> response) {
                Log.e("excute()", "get answer");

                if(!response.isSuccessful()){Log.e("연결이 비정상적 : ", "error code : " + response.code()); return;}
                answers = response.body();
                Log.e("rest api a", answers.size()+"");

                questionIdList = makeRandomList();
                connector = getConnector(questionIdList);
                prev_button.setVisibility(View.INVISIBLE);
                nextQuestion(index);
//                index++;
            }
            @Override
            public void onFailure(Call<List<PsycologicaltestAnswerDTO>> call, Throwable t) {Log.e("연결실패", t.getMessage());}
        });

        Log.e("excute()", "end rest api");
    }

}
