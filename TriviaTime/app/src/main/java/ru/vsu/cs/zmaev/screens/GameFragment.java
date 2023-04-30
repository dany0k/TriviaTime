package ru.vsu.cs.zmaev.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ru.vsu.cs.zmaev.R;
import ru.vsu.cs.zmaev.databinding.FragmentGameBinding;
import ru.vsu.cs.zmaev.model.Question;
import ru.vsu.cs.zmaev.viewmodels.GameViewModel;

public class GameFragment extends Fragment {

    private static final int FIRST_ANSWER_SELECTED = 0;
    private static final int SECOND_ANSWER_SELECTED = 1;
    private static final int THIRD_ANSWER_SELECTED = 2;
    private static final int FOURTH_ANSWER_SELECTED = 3;

    private int currentQuestionIndex = 0;
    private int answerIndex;
    private int correctAnsAmount = 0;

    private FragmentGameBinding binding;
    private GameViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGameBinding.inflate(
                inflater,
                container,
                false
        );
        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getQuestionBank().observe(getViewLifecycleOwner(), questions ->
                setQuestion(viewModel.getQuestionBank().getValue().get(currentQuestionIndex)));
        binding.firstAnswerButton.setOnClickListener(v -> {
            answerIndex = FIRST_ANSWER_SELECTED;
            processButton(v);
        });
        binding.secondAnswerButton.setOnClickListener(v -> {
            answerIndex = SECOND_ANSWER_SELECTED;
            processButton(v);
        });
        binding.thirdAnswerButton.setOnClickListener(v -> {
            answerIndex = THIRD_ANSWER_SELECTED;
            processButton(v);
        });
        binding.fourthAnswerButton.setOnClickListener(v -> {
            answerIndex = FOURTH_ANSWER_SELECTED;
            processButton(v);
        });
    }

    private void processButton(View v) {
        if (viewModel.checkAnswer(currentQuestionIndex, answerIndex)) {
            correctAnsAmount++;
        }
        if (viewModel.isLastQuestion(currentQuestionIndex)) {
            viewModel.setCorrPercentage(correctAnsAmount);
            Navigation.findNavController(v)
                    .navigate(R.id.action_gameFragment_to_resultFragment);
            return;
        }
        updateUI();
    }

    private void updateUI() {
        currentQuestionIndex++;
        setQuestion(viewModel.getQuestionBank().getValue().get(currentQuestionIndex));
    }

    private void setQuestion(Question question) {
        binding.questionText.setText(question.getQuestionText());
        binding.firstAnswerButton.setText(question.getAnswerOptions().get(0));
        binding.secondAnswerButton.setText(question.getAnswerOptions().get(1));
        binding.thirdAnswerButton.setText(question.getAnswerOptions().get(2));
        binding.fourthAnswerButton.setText(question.getAnswerOptions().get(3));
    }
}
