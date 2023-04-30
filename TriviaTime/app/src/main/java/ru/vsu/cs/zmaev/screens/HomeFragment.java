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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.vsu.cs.zmaev.R;
import ru.vsu.cs.zmaev.adapter.TopicRVAdapter;
import ru.vsu.cs.zmaev.databinding.FragmentHomeBinding;
import ru.vsu.cs.zmaev.model.Question;
import ru.vsu.cs.zmaev.model.Quiz;
import ru.vsu.cs.zmaev.viewmodels.GameViewModel;


public class HomeFragment extends Fragment implements TopicRVAdapter.TopicClickInterface {

    private FragmentHomeBinding binding;
    private FirebaseAuth mAuth;
    private TopicRVAdapter adapter;
    private GameViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(
                inflater,
                container,
                false);
        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        initRvItems();
//        addQuizzesToFirebase();
        viewModel.setQuizTitles();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getQuizTitles().observe(getViewLifecycleOwner(), topics ->
                adapter.setItems(viewModel.getQuizTitles().getValue()));
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
                    .navigate(R.id.action_navigation_home_to_registerFragment);
        }
    }
    private void initRvItems() {
        adapter = new TopicRVAdapter(this::onClick);
        binding.topicsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.topicsRv.setAdapter(adapter);
    }




    private void addQuizzesToFirebase() {
        // Получаем ссылку на базу данных Firebase Realtime Database
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

        // Создаем первую викторину
        List<Question> questions1 = new ArrayList<>();
        questions1.add(new Question("Кто был первым президентом США?", Arrays.asList("Джордж Вашингтон", "Авраам Линкольн", "Томас Джефферсон", "Барак Обама"), 0));
        questions1.add(new Question("В каком году началась Вторая мировая война?", Arrays.asList("1939", "1945", "1917", "1941"), 0));
        questions1.add(new Question("В каком году отменили крепостное право В России?", Arrays.asList("1901", "1861", "1917", "1854"), 1));
        questions1.add(new Question("Какую реку в конце XVII века следует считать южным рубежом России?", Arrays.asList("Волга", "Дон", "Кубань", "Терек"), 3));
        questions1.add(new Question("На каких кораблях велась торговля через Архангельск в конце XVII века?", Arrays.asList("Голландские", "Датские", "Шведские", "Британские"), 0));
        questions1.add(new Question("Как назывался первый русский торговый корабль?", Arrays.asList("Святой Петр", "Святой Павел", "Святой Николай", "Святой Андрей"), 1));
        questions1.add(new Question("В каком году был спущен на воду первый русский торговый корабль?", Arrays.asList("1690", "1692", "1694", "1696"), 2));
        questions1.add(new Question("С какими из перечисленных государств у России в XVII веке были наиболее хорошие отношения?", Arrays.asList("Дания и Англия", "Галландия и Швеция", "Польша и Франция", "Дания и Голландия"), 3));
        questions1.add(new Question("В какой коалиции, направленной против Османской империи, в конце XVII века принимала участие Россия?", Arrays.asList("Венская Лига", "Священная Лига", "Северная Лига", "Христианская Лига"), 1));
        questions1.add(new Question("В каком российском городе в конце XVII века находилась Немецкая слобода?", Arrays.asList("Архангельск", "Новгород", "Киев", "Москва"), 3));
        Quiz quiz1 = new Quiz("Викторина по истории", "Тест на знание истории", 10, 1200, questions1);


        // Создаем вторую викторину
        List<Question> questions2 = new ArrayList<>();
        questions2.add(new Question("Какое самое высокое горное озеро в мире?", Arrays.asList("Онежское", "Байкал", "Титикака", "Марачо"), 2));
        questions2.add(new Question("Какой город является столицей Японии?", Arrays.asList("Осака", "Токио", "Киото", "Хоккайдо"), 1));
        questions2.add(new Question("Какое государство является самым большим по территории в мире?", Arrays.asList("Китай", "США", "Канада", "Россия"), 3));
        questions2.add(new Question("Какая река является самой длинной в мире?", Arrays.asList("Нил", "Амазонка", "Миссисипи", "Янцзы"), 1));
        questions2.add(new Question("Какой океан является самым большим по площади?", Arrays.asList("Тихий", "Атлантический", "Индийский", "Северный Ледовитый"), 0));
        questions2.add(new Question("Какое государство является самым маленьким по территории в мире?", Arrays.asList("Монако", "Сан-Марино", "Ватикан", "Лихтенштейн"), 2));
        questions2.add(new Question("Какой горный хребет является самым длинным в мире?", Arrays.asList("Альпы", "Анды", "Гималаи", "Урал"), 1));
        questions2.add(new Question("Какой город является самым населенным в мире?", Arrays.asList("Шанхай", "Москва", "Дели", "Токио"), 3));
        questions2.add(new Question("Какое государство находится в самом центре Африки?", Arrays.asList("Руанда", "Габон", "Центрально-Африканская Республика", "Демократическая Республика Конго"), 2));
        questions2.add(new Question("Какой город является самым северным в мире?", Arrays.asList("Омск", "Рейкьявик", "Мурманск", "Таллин"), 2));
        Quiz quiz2 = new Quiz("Викторина по географии", "Тест на знание географии", 10, 1800, questions2);

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("Кто является автором проекта Бурдж-Халифа, самого высокого здания в мире?", Arrays.asList("Адриан Смит", "Фредерик Гендерсон", "Сома Чатерджи", "Шив Надар"), 0));
        questions.add(new Question("Какое здание было построено в Риме в 80 году н.э. и до сих пор остаётся одним из самых известных памятников архитектуры?", Arrays.asList("Колизей", "Пантеон", "Каркассонский замок", "Тадж-Махал"), 1));
        questions.add(new Question("Какое здание, построенное в 1931 году, было самым высоким в мире до 1971 года?", Arrays.asList("Эмпайр-стейт-билдинг", "Храмовый комплекс Абу-Симбел", "Токийская башня", "Здание Верховного суда США"), 0));
        questions.add(new Question("Какая архитектурная школа была основана в Германии в 1919 году?", Arrays.asList("Баухауз", "Арте-поверо", "Ар-деко", "Ар-нуво"), 0));
        questions.add(new Question("Какое здание, построенное в 1889 году, было создано в честь 100-летия Французской революции?", Arrays.asList("Эйфелева башня", "Храм Святого Семейства", "Башня Спейс Нидл", "Башня Канада"), 0));
        questions.add(new Question("Какое здание, построенное в 1973 году, является самым высоким в США?", Arrays.asList("Всемирный торговый центр", "Хайатт Ридженси Дубай", "Клифтонский мост", "Башня Джона Хэнкока"), 3));
        questions.add(new Question("Какое здание, построенное в Москве в 1561 году, является одним из самых высоких православных храмов в мире?", Arrays.asList("Храм Христа Спасителя", "Собор Василия Блаженного", "Спасо-Преображенский собор", "Успенский собор"), 0));
        questions.add(new Question("Какое здание, построенное в 1972 году, является самым высоким в мире среди жилых зданий?", Arrays.asList("Высотное здание на улице Шейх-Зайед", "Шанхайская башня", "Восточная жемчужина", "Тайпей 101"), 3));
        questions.add(new Question("Какой стиль архитектуры, популярный в Европе в 12-15 веках, характеризуется использованием сводов и арок?", Arrays.asList("Готика", "Ренессанс", "Барокко", "Классицизм"), 0));
        questions.add(new Question("Какое здание, построенное в 2004 году, является самым высоким в мире и до сих пор остаётся в числе самых известных памятников архитектуры?", Arrays.asList("Бурдж-Халифа", "Шанхайская башня", "Восточная жемчужина", "Тайпей 101"), 0));
        Quiz quiz = new Quiz("Викторина по архитектуре", "Тест на знание архитектуры", 10, 1800, questions);

        databaseRef.child("quizzes").child("quiz3").setValue(quiz1);
//        databaseRef.child("quizzes").child("quiz1").setValue(quiz1);
//        databaseRef.child("quizzes").child("quiz2").setValue(quiz2);
    }

    @Override
    public void onClick(long id) {
        viewModel.setTopicId(id);
        viewModel.setQuestions();
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
                .navigate(R.id.action_navigation_home_to_gameFragment);
    }
}