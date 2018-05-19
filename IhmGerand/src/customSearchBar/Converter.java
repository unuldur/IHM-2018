package customSearchBar;

import javafx.util.StringConverter;

public class Converter extends StringConverter<String> {
    @Override
    public String toString(String object) {
        return object;
    }

    @Override
    public String fromString(String string) {
        return string;
    }
}
