package kr.goldenmine.inumincraftlauncher;


import com.google.gson.Gson;
import kr.goldenmine.inuminecraftlauncher.request.models.xbox.XBoxLiveRequest;
import kr.goldenmine.inuminecraftlauncher.request.models.xbox.XBoxLiveRequestProperties;

public class JsonTest {
    public static void main(String[] args) {
        XBoxLiveRequestProperties properties = new XBoxLiveRequestProperties(
                "AuthMethod",
                "SiteName",
                "AccessToken"
        );

        XBoxLiveRequest request = new XBoxLiveRequest(
                "relyingParty",
                "TokenType",
                properties
        );

        Gson gson = new Gson();
        String str = gson.toJson(request);

        System.out.println(str);
    }
}
