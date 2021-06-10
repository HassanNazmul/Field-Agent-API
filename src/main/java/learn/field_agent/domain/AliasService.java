package learn.field_agent.domain;

import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Alias;
import learn.field_agent.models.SecurityClearance;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author NAHID
 * Date  09,  June, 2021
 **/
@Service
public class AliasService {

    private final AliasRepository repository;


    public AliasService(AliasRepository repository) {
        this.repository = repository;

    }

    // GET ALL ALIAS
    public List<Alias> findAll() {
        return repository.findAll();
    }

    // ADD AN ALIAS
    public Result<Alias> add(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() != 0) {
            result.addMessage("ERROR", ResultType.INVALID);
            return result;
        }

        alias = repository.add(alias);
        result.setPayload(alias);
        return result;
    }

    // UPDATE EXISTING ALIAS
    public Result<Alias> update(Alias alias) {
        Result<Alias> result = validate(alias);
        if ((!result.isSuccess())) {
            return result;
        }

        if (alias.getAliasId() <= 0) {
            result.addMessage("ERROR", ResultType.INVALID);
            return result;
        }

        if (!repository.update(alias)) {
            String msg = String.format("%s NOT FOUND", alias.getAliasId());
        }
        return result;
    }

    // DELETE AN ALIAS
    public boolean deleteById(int aliasId) {
        return repository.deleteById(aliasId);
    }

    // VALIDATION
    private Result<Alias> validate(Alias alias) {
        Result<Alias> result = new Result<>();

        if (alias == null) {
            result.addMessage("ERROR", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(alias.getName())) {
            result.addMessage("NAME IS REQUIRED, PLEASE ENTER A NAME", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(alias.getPersona())) {
            result.addMessage("PERSONA IS REQUIRED, PLEASE ENTER A PERSONA", ResultType.INVALID);
        }

        Alias checkUniqueName = repository.findAll().stream()
                .filter(temp -> temp.getName().equals(alias.getName())).findFirst().orElse(null);
        if (checkUniqueName != null) {
            result.addMessage("PLEASE ENTER A UNIQUE NAME",ResultType.INVALID);
        }

        return result;
    }

}
