package net.legitimoose.script;

import java.util.List;

public record Message(Type type, String value, Record data) {
  public enum Type {
    FUNCTION_CALL,
    MINECRAFT_COMMAND,
    MOOSESCRIPT_COMMAND,
    CHAT_MESSAGE,
    PLAIN_TEXT,
    JSON_FORMATTED_TEXT
  }

  public record FunctionCallData(
      long funcCallId, FunctionExecutor functionExecutor, List<?> args) {}

  public Message(Type type, String value) {
    this(type, value, null);
  }

  public static Message createFunctionCall(
      long funcCallId, FunctionExecutor functionExecutor, String functionName, List<?> args) {
    return new Message(
        Type.FUNCTION_CALL, functionName, new FunctionCallData(funcCallId, functionExecutor, args));
  }

  public static Message createMinecraftCommand(String value) {
    return new Message(Type.MINECRAFT_COMMAND, value);
  }

  public static Message createMoosescriptCommand(String value) {
    return new Message(Type.MOOSESCRIPT_COMMAND, value);
  }

  public static Message createChatMessage(String value) {
    return new Message(Type.CHAT_MESSAGE, value);
  }

  public static Message fromPlainText(String value) {
    return new Message(Type.PLAIN_TEXT, value);
  }

  public static Message fromJsonFormattedText(String value) {
    return new Message(Type.JSON_FORMATTED_TEXT, value);
  }

  public static Message formatAsJsonColoredText(String text, String color) {
    return Message.fromJsonFormattedText(
        "{\"text\":\""
            + text.replace("\\", "\\\\").replace("\"", "\\\"")
            + "\",\"color\":\""
            + color
            + "\"}");
  }
}
