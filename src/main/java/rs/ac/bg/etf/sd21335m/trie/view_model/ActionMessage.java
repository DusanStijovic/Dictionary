package rs.ac.bg.etf.sd21335m.trie.view_model;

public enum ActionMessage {
    WORD_SUCCESSFULLY_ADDED("Word is added successfully"), WORD_ALREADY_EXIST("Word already exist"), ILLEGAL_WORD("Word is illegal"), NO_ACTION("No last action"), SEARCH_DONE_SUCCESSFULLY("Search done successfully"), DELETE_DONE_SUCCESSFULLY("Delete done successfully");

    private final String message;

    ActionMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
