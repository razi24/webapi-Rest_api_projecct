package serverside.course.webapi.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) //

public class PlayersEx {
    private String first_name;
    private String last_name;
    private double height_inches;
    private String position;

}
