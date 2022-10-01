package kr.goldenmine.inuminecraftlauncher.request.models.statistics;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MinecraftStatisticsRequest {
    @SerializedName("joined_players")
    List<String> joinedPlayers;

    @SerializedName("joined_players_count")
    int joinedPlayersCount;
}
