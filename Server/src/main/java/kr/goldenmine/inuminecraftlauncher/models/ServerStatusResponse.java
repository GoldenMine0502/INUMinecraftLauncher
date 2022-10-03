package kr.goldenmine.inuminecraftlauncher.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

@Getter
@AllArgsConstructor
public class ServerStatusResponse {

    @SerializedName("available_counts")
    int availableCounts;

    @SerializedName("total_counts")
    int totalCounts;
}
