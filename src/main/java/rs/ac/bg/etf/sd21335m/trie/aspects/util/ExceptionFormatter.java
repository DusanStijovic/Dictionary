package rs.ac.bg.etf.sd21335m.trie.aspects.util;

public class ExceptionFormatter {
    public String getStackTraceAsString(Throwable exception) {
        StringBuilder sb = new StringBuilder();
        sb.append("Exception occurred:\n");
        sb.append(exception.toString());
        sb.append("\n\n");
        for (StackTraceElement element : exception.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

}
