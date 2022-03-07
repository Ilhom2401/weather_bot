package model;

import com.fasterxml.jackson.annotation.JsonRootName;
import enums.BotState;
import lombok.*;

@EqualsAndHashCode()
@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
@ToString(callSuper = true)
public class User {
    String name;
    String username;
    String chatId;
    BotState state;
    int start;
    int finish;
}
