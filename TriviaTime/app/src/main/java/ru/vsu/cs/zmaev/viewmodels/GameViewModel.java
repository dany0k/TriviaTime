package ru.vsu.cs.zmaev.viewmodels;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.vsu.cs.zmaev.model.Question;
import ru.vsu.cs.zmaev.model.Quiz;
import ru.vsu.cs.zmaev.model.Topic;

public class GameViewModel extends ViewModel {
    private MutableLiveData<Long> topicId = new MutableLiveData<>();
    private MutableLiveData<List<Question>> questionsMutable = new MutableLiveData<>();
    private MutableLiveData<List<Topic>> quizTitles = new MutableLiveData<>();
    private MutableLiveData<Float> corrPercentage = new MutableLiveData<>();
    private DatabaseReference databaseRef;

    public GameViewModel() {
        databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    private void getQuestions() {
        String quizNum = "quiz" + (topicId.getValue() + 1);
        System.out.println("Quiz:" + quizNum);
        DatabaseReference questionRef = databaseRef.child("quizzes").child(quizNum);

        questionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Quiz quiz = dataSnapshot.getValue(Quiz.class);
                List<Question> questionsTemp = quiz.getQuestions();
                questionsMutable.setValue(questionsTemp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибки чтения данных из базы данных
            }
        });
    }

    public boolean isLastQuestion(int questionIndex) {
        return questionsMutable.getValue().size() - 1 == questionIndex;
    }

    public boolean checkAnswer(int currentQuestionIndex, int answerIndex) {
        return questionsMutable.getValue().get(currentQuestionIndex).getCorrectAnswer() == answerIndex;
    }

    private void getQuizzesAmount() {
        DatabaseReference quizzesRef = FirebaseDatabase.getInstance().getReference().child("quizzes");
        quizzesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Topic> tempTopics = new ArrayList<>();
                for (DataSnapshot quizSnapshot : dataSnapshot.getChildren()) {
                    String quizTitle = quizSnapshot.child("title").getValue(String.class);
                    tempTopics.add(new Topic(quizTitle));
                }
                quizTitles.setValue(tempTopics);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    public LiveData<Long> getTopicId() {
        return topicId;
    }

    public void setTopicId(long id) {
        topicId.setValue(id);
    }

    public void setQuestions() {
        getQuestions();
    }

    public LiveData<List<Question>> getQuestionBank() {
        return questionsMutable;
    }

    public LiveData<List<Topic>> getQuizTitles() {
        return quizTitles;
    }

    public void setQuizTitles() {
        getQuizzesAmount();
    }

   public void setCorrPercentage(int correctAns) {
        corrPercentage.setValue(100f * correctAns / 10f);
   }

   public LiveData<Float> getCorrPercentage() {
        return corrPercentage;
   }
}
