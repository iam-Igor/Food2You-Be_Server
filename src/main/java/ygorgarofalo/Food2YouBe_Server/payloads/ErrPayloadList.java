package ygorgarofalo.Food2YouBe_Server.payloads;

import java.util.List;

public record ErrPayloadList(String message,
                             List<String> errorsList) {
}
