package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        List<Person> rsl = StreamSupport.stream(
                this.personRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
        return rsl;
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void delete(Person person) {
        personRepository.delete(person);
    }
}