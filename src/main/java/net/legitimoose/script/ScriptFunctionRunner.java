package net.legitimoose.script;

import java.util.List;

public interface ScriptFunctionRunner {
  void run(Job job, String functionName, long funcCallId, List<?> parsedArgs);
}
