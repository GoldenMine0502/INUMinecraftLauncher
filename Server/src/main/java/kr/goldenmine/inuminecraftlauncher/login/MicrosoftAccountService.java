package kr.goldenmine.inuminecraftlauncher.login;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MicrosoftAccountService {
    private final MicrosoftAccountRepository microsoftAccountRepository;

    public MicrosoftAccountService(MicrosoftAccountRepository microsoftAccountRepository) {
        this.microsoftAccountRepository = microsoftAccountRepository;
    }

    public MicrosoftAccount save(MicrosoftAccount user) {
        return microsoftAccountRepository.save(user);
    }

    public List<MicrosoftAccount> list() {
        return microsoftAccountRepository.findAll();
    }

    public void flush() {
        microsoftAccountRepository.flush();
    }

    public Optional<MicrosoftAccount> selectOneAccount() {
        long currentTime = System.currentTimeMillis();

        // 조건에 맞는 게시글의 개수를 가져온다.
        long qty = microsoftAccountRepository.countAvailableAccounts(currentTime);
        // 가져온 개수 중 랜덤한 하나의 인덱스를 뽑는다.
        int idx = (int)(Math.random() * qty);
        // 페이징하여 하나만 추출해낸다.
        Page<MicrosoftAccount> postPage = microsoftAccountRepository.getAvailableAccounts(currentTime, PageRequest.of(idx, 1));

        if (postPage.hasContent()) {
            MicrosoftAccount post = postPage.getContent().get(0);
            return Optional.of(post);
        }

        return Optional.empty();
    }
}