package kr.goldenmine.inuminecraftlauncher.util;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;

import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
public class LoopUtil {


    public static <T> Optional<T> waitWhile(Supplier<Optional<T>> filter, long sleep, int limit) throws InterruptedException {
        int count = 0;
        while(count <= limit || limit == -1) {
            try {
                Optional<T> optional = filter.get();

                if(optional.isPresent()) {
                    if(!(optional.get() instanceof WebElement) || ((WebElement)optional.get()).isDisplayed()) {
                        return optional;
                    }
                }
                count++;
            } catch (Exception ex) {
                log.warn(ex.getMessage());
            }
            Thread.sleep(sleep);
        }

        return Optional.empty();
    }
}
