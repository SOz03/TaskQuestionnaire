package ru.code.task.data.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;

import ru.code.task.data.entity.Answer;
import ru.code.task.data.entity.Question;
import ru.code.task.data.entity.Questionnaire;
import ru.code.task.data.repository.AnswerRepository;
import ru.code.task.data.repository.QuestionRepository;
import ru.code.task.data.repository.QuestionnaireRepository;
import ru.code.task.data.repository.UserRepository;
import ru.code.task.data.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.code.task.data.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder, UserRepository userRepository,
                                      AnswerRepository answerRepository,
                                      QuestionnaireRepository questionnaireRepository,
                                      QuestionRepository questionRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (userRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Генерация демонстрационных данных");

            logger.info("... генерация 2 User сущности...");
            User user = new User();
            user.setName("Просто User");
            user.setUsername("user");
            user.setHashedPassword(passwordEncoder.encode("user"));
            user.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);

            User admin = new User();
            admin.setName("Admin");
            admin.setUsername("admin");
            admin.setHashedPassword(passwordEncoder.encode("admin"));
            admin.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            admin.setRoles(Collections.singleton(Role.ADMIN));
            userRepository.save(admin);

            logger.info("... генерация 2 Questionnaire сущности...");
            Questionnaire questionnaire = new Questionnaire("Иностранные языки",
                    "Нам важно знать уровень владения языком.", 2);
            questionnaireRepository.save(questionnaire);

            Questionnaire questionnaire1 = new Questionnaire("Машины",
                    "Анкета просто для теста.", 4);
            questionnaireRepository.save(questionnaire1);

            logger.info("... генерация 6 Question сущности...");
            Question question1 = new Question("Какими языками вы владеете?",
                    false, questionnaire);
            Question question2 = new Question("Как хорошо вы разговариваете на английском языке?",
                    true, questionnaire);
            Question question3 = new Question("Сколько вы машин хотели бы иметь?",
                    true, questionnaire1);
            Question question4 = new Question("Какие вам нравятся?",
                    false, questionnaire1);
            Question question5 = new Question("Много ли вы делаете для этого?",
                    true, questionnaire1);
            Question question6 = new Question("Понравилась анкета?",
                    true, questionnaire1);
            List<Question> questions = new ArrayList<>();
            questions.add(question1);
            questions.add(question2);
            questions.add(question3);
            questions.add(question4);
            questions.add(question5);
            questions.add(question6);
            questionRepository.saveAll(questions);

            logger.info("... генерация 1-4 Answer сущности...");
            String[] arrays = new String[]{"Немецкий", "Английский", "Китайский", "Другой"};
            List<Answer> answers = new ArrayList<>();
            Answer answer;
            for (String array : arrays) {
                answer = new Answer(array, question1);
                answers.add(answer);
            }

            logger.info("... генерация 5-8 Answer сущности...");
            String[] arrays1 = new String[]{"Плохо", "Нормально", "Хорошо", "Отлично"};
            for (String array : arrays1) {
                answer = new Answer(array, question2);
                answers.add(answer);
            }

            logger.info("... генерация  Answer сущности...");
            String[] arrays2 = new String[]{"Одну", "Две", "Три", "МНОГО"};
            for (String array : arrays2) {
                answer = new Answer(array, question3);
                answers.add(answer);
            }
            arrays2 = new String[]{"Mercedes", "BMW", "Lada", "Kia","Renault","Volkswagen"};
            for (String array : arrays2) {
                answer = new Answer(array, question4);
                answers.add(answer);
            }
            arrays2 = new String[]{"Много", "Ничего", "Жду своего шанса"};
            for (String array : arrays2) {
                answer = new Answer(array, question5);
                answers.add(answer);
            }
            arrays2 = new String[]{"Да", "Не особо", "Нет"};
            for (String array : arrays2) {
                answer = new Answer(array, question6);
                answers.add(answer);
            }
            answerRepository.saveAll(answers);
            logger.info("Сгенерированы демонстрационные данные");
        };
    }

}