package ru.job4j.accidents.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.data.AccidentDataRepository;
import ru.job4j.accidents.repository.data.AccidentTypeDataRepository;
import ru.job4j.accidents.repository.data.RuleDataRepository;

import java.util.*;

@Service
public class AccidentServiceImpl implements AccidentService {
    private final AccidentDataRepository accidentRepository;
    private final AccidentTypeDataRepository accidentTypeRepository;
    private final RuleDataRepository ruleRepository;
    private final Logger logger = LoggerFactory.getLogger(AccidentServiceImpl.class);

    public AccidentServiceImpl(AccidentDataRepository accidentRepository,
                               AccidentTypeDataRepository accidentTypeRepository,
                               RuleDataRepository ruleRepository) {
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
        Optional<Accident> accidentFromDB = accidentRepository.findById(accident.getId());
        if (accidentFromDB.isPresent()) {
            accidentFromDB.get().setDescription(accident.getDescription());
            accidentRepository.save(accidentFromDB.get());
        }
        if (accidentFromDB.isEmpty()) {
            String errorMsg = " Нет возможности обновить авто инцидент. Id"
                    + " в переданном объекте и в БД. в объекте -> "
                    + accident.getId() + " нет в БД.";
            logger.error(errorMsg);
            throw new NoSuchElementException(errorMsg);
        }
    }

    private boolean create(Accident accident) {
        accidentRepository.save(accident);
        return true;
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
        Set<Rule> result = new HashSet<>(ruleRepository.findAllById(ids));
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
