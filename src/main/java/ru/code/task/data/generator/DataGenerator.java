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

            logger.info("... генерация 1-4 Answer сущности...");
            String[] arrays = new String[]{"Немецкий", "Английский", "Китайский", "Другой"};
            List<Answer> answers1 = new ArrayList<>();
            Answer answer;
            for (String array : arrays) {
                answer = new Answer(array);
                answers1.add(answer);
            }
            answerRepository.saveAll(answers1);

            logger.info("... генерация 5-6 Answer сущности...");
            arrays = new String[]{"Плохо", "Нормально", "Хорошо", "Отлично"};
            List<Answer> answers2 = new ArrayList<>();
            for (String array : arrays) {
                answer = new Answer(array);
                answers2.add(answer);
            }
            answerRepository.saveAll(answers2);

            logger.info("... генерация 2 Question сущности...");
            Question question1 = new Question("Какими языками вы владеете?",
                    false, answers1);
            Question question2 = new Question("Как хорошо вы разговариваете на английском языке?",
                    true, answers2);
            List<Question> questions = new ArrayList<>();
            questions.add(question1);
            questions.add(question2);
            questionRepository.saveAll(questions);

            logger.info("... генерация 1 Questionnaire сущности...");
            Questionnaire questionnaire = new Questionnaire("Иностранные языки",
                    "Нам важно знать уровень владения языком.", questions);
            questionnaireRepository.save(questionnaire);

            logger.info("Сгенерированы демонстрационные данные");
        };
    }

}