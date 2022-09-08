package com.colorful.colorful_android.DTO;

import com.google.gson.annotations.SerializedName;

public class PsycologicaltestQuestionDTO {

    @SerializedName("questionId")
    int questionId;
    @SerializedName("content")
    String content;
    @SerializedName("type")
    String type;

    public PsycologicaltestQuestionDTO(int questionId, String content, String type) {
        this.questionId = questionId;
        this.content = content;
        this.type = type;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
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
