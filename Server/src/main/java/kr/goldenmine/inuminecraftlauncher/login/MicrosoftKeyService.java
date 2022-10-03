package kr.goldenmine.inuminecraftlauncher.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MicrosoftKeyService {
    private MicrosoftKey primary;

    private final MicrosoftKeyRepository microsoftKeyRepository;

    public MicrosoftKeyService(MicrosoftKeyRepository microsoftKeyRepository) {
        this.microsoftKeyRepository = microsoftKeyRepository;
    }

    public MicrosoftKey getPrimary() {
        if(primary == null) {
            primary = microsoftKeyRepository.findAll().get(0);
        }
        return primary;
    }
}