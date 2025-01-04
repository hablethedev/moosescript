package net.legitimoose.script;

import com.google.gson.JsonElement;

public interface Task {
  int run(ScriptConfig.BoundCommand command, JobControl jobControl);

  /** Sends a return value to the given script function call. Returns true if response succeeds. */
  default boolean sendResponse(long functionCallId, JsonElement returnValue, boolean finalReply) {
    return false;
  }

  /** Sends an exception to the given script function call. Returns true if response succeeds. */
  default boolean sendException(long functionCallId, ExceptionInfo exception) {
    return false;
  }
}
