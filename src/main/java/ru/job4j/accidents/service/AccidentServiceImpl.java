package ru.job4j.accidents.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class AccidentServiceImpl implements AccidentService {
    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleRepository ruleRepository;
    private final Logger logger = LoggerFactory.getLogger(AccidentServiceImpl.class);

    public AccidentServiceImpl(AccidentRepository accidentRepository, AccidentTypeRepository accidentTypeRepository, RuleRepository ruleRepository) {
        this.accidentRepository = accidentRepository;
        this.accidentTypeRepository = accidentTypeRepository;
        this.ruleRepository = ruleRepository;
    }

    @Override
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public void checkAndCreate(Accident accident, List<Integer> ruleIds) {
        accident.setType(checkAccidentType(accident));
        accident.setRules(checkAccidentRules(ruleIds));
        create(accident);
    }

    @Override
    public Accident findById(int id) {
        return accidentRepository.findById(id).orElseThrow(
                () -> {
                    String errorMsg = "Инцидента с id ="
                            + id + " нет в БД";
                    logger.error(errorMsg);
                    return new NoSuchElementException(errorMsg);
                }
        );
    }

    @Override
    public void updateText(Accident accident) {
        boolean result = accidentRepository.updateText(accident);
        if (!result) {
            String errorMsg = " Нет возможности обновить авто инцидент. Id"
                    + " в переданном объекте и в БД. в объекте -> "
                    + accident.getId() + " нет в БД.";
            logger.error(errorMsg);
            throw new NoSuchElementException(errorMsg);
        }
    }

    private boolean create(Accident accident) {
        return accidentRepository.create(accident);
    }

    private AccidentType checkAccidentType(Accident accident) {
        int accidentId = accident.getType().getId();
        Optional<AccidentType> optionalType = accidentTypeRepository.findById(accidentId);
        return optionalType.orElseThrow(
                () -> {
                    String errorMsg = " Нет возможности сохранить авто инцидент. Типа инцидента с id ="
                            + accidentId + "нет в БД";
                    logger.error(errorMsg);
                    return new NoSuchElementException(errorMsg);
                }
        );
    }

    private Set<Rule> checkAccidentRules(List<Integer> ids) {
        Set<Rule> result = ruleRepository.findById(ids);
        if (result.size() != ids.size()) {
            String errorMsg = " Нет возможности сохранить авто инцидент."
                    + " Несовпадение набора связанных статей"
                    + " в переданном объекте и в БД. в объекте -> "
                    + ids + " в БД ->" + result;
            logger.error(errorMsg);
            throw new NoSuchElementException(errorMsg);
        }
        return result;
    }
}
