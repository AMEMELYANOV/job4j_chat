package ru.job4j.chat.service;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Optional;

public class DTOService {
    public static <T extends Model> Optional<T> patchModel(CrudRepository<T,
            Integer> repository, T t)
            throws InvocationTargetException, IllegalAccessException {
        var currentOpt = repository.findById(t.getId());
        if (currentOpt.isEmpty()) {
            return Optional.empty();
        }
        var current = currentOpt.get();
        var methods = current.getClass().getDeclaredMethods();
        var namePerMethod = new HashMap<String, Method>();
        for (var method: methods) {
            var name = method.getName();
            if (name.startsWith("get") || name.startsWith("set")) {
                namePerMethod.put(name, method);
            }
        }
        for (var name : namePerMethod.keySet()) {
            if (name.startsWith("get")) {
                var getMethod = namePerMethod.get(name);
                var setMethod = namePerMethod.get(name.replace("get", "set"));
                if (setMethod == null) {
                    return Optional.empty();
                }
                var newValue = getMethod.invoke(t);
                if (newValue != null) {
                    setMethod.invoke(current, newValue);
                }
            }
        }
        repository.save(t);
        return Optional.of(current);
    }
}
