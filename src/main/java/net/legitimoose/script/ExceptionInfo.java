package net.legitimoose.script;

import com.google.common.collect.ImmutableList;
import java.util.List;

public record ExceptionInfo(String type, String message, String desc, List<StackElement> stack) {

  public record StackElement(String file, String method, int line) {}

  public static ExceptionInfo fromException(Exception e) {
    var type = e.getClass().getName();
    var desc = e.toString();
    var stackBuilder = new ImmutableList.Builder<StackElement>();
    boolean hitMoosescriptJava = false;
    int stackDepth = 0;
    for (var element : e.getStackTrace()) {
      if (++stackDepth > 10) {
        break;
      }
      var className = element.getClassName();
      var fileName = element.getFileName();
      if (className != null) {
        // Capture stacktrace up through Moosescript code, but no further.
        if (!hitMoosescriptJava && className.contains("moosescript")) {
          hitMoosescriptJava = true;
        } else if (hitMoosescriptJava && !className.contains("moosescript")) {
          break;
        }
      }
      stackBuilder.add(
          new StackElement(fileName, element.getMethodName(), element.getLineNumber()));
    }
    return new ExceptionInfo(type, e.getMessage(), desc, stackBuilder.build());
  }
}
