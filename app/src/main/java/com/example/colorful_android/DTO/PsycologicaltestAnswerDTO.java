package com.example.colorful_android.DTO;

import com.google.gson.annotations.SerializedName;

public class PsycologicaltestAnswerDTO {

    @SerializedName("answerId")
    int answerId;
    @SerializedName("questionId")
    int questionId;
    @SerializedName("questionType")
    String questionType;
    @SerializedName("content")
    String content;
    @SerializedName("type")
    String type;

    public PsycologicaltestAnswerDTO(int answerId, int questionId, String questionType, String content, String type) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.questionType = questionType;
        this.content = content;
        this.type = type;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
