package learn.field_agent.domain;

import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.SecurityClearance;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author NAHID
 * Date  07,  June, 2021
 **/

@Service
public class clearanceService {
    private final SecurityClearanceRepository repository;

    public clearanceService(SecurityClearanceRepository repository) {
        this.repository = repository;
    }

    // GET ALL SECURITY CLEARANCE
    public List<SecurityClearance> findAll() {
        return repository.findAll();
    }

    // GET SECURITY CLEARANCE BY ID
    public SecurityClearance findById(int securityClearanceId) {
        return repository.findById(securityClearanceId);
    }

    // ADD NEW CLEARANCE
    public Result<SecurityClearance> add(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = validate(securityClearance);
        if(!result.isSuccess()) {
            return result;
        }

        if (securityClearance.getSecurityClearanceId() != 0) {
            result.addMessage("ERROR", ResultType.INVALID);
            return result;
        }

        securityClearance = repository.add(securityClearance);
        result.setPayload(securityClearance);
        return result;
    }

    private Result<SecurityClearance> validate(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = new Result<>();
        if (Validations.isNullOrBlank(securityClearance.getName())) {
            result.addMessage("SECURITY CLEARANCE NAME IS REQUIRED, PLEASE ENTER A NAME", ResultType.INVALID);
            return result;
        }

        SecurityClearance checkUniqueName = repository.findAll().stream()
                .filter(temp -> temp.getName().equals(securityClearance.getName())).findFirst().orElse(null);
        if (checkUniqueName != null) {
            result.addMessage("PLEASE ENTER A UNIQUE NAME",ResultType.INVALID);
        }

        return result;
    }

    // UPDATE EXISTING SECURITY CLEARANCE
    public Result<SecurityClearance> update(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = validate(securityClearance);
        if (!result.isSuccess()) {
            return result;
        }

        if (securityClearance.getSecurityClearanceId() <= 0) {
            result.addMessage("ERROR", ResultType.INVALID);
            return result;
        }

        if (!repository.update(securityClearance)) {
            String msg = String.format("Clearence: %s, not found", securityClearance.getSecurityClearanceId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;

    }

    // DELETE EXISTING SECURITY CLEARANCE BY ID
    public boolean deleteById(int securityClearanceId) {
        return repository.deleteById(securityClearanceId);
    }
}
