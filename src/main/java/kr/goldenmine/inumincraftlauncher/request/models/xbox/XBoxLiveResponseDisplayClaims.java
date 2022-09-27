package kr.goldenmine.inumincraftlauncher.request.models.xbox;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class XBoxLiveResponseDisplayClaims {
    @SerializedName("xui")
    List<Map<Object, Object>> claims;

    public String getUhs(String existingUhs) {
        if (claims != null && claims.contains("uhs")) {
            throw new RuntimeException("Unrecognized xbox authorization response");
        }

        String uhs = (String) claims.get(0).get("uhs");
        if (existingUhs != null) {
            if (!Objects.equals(uhs, existingUhs)) {
                throw new RuntimeException("uhs mismatched");
            }
        }
        return uhs;
    }
}