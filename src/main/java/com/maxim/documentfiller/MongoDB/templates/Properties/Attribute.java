package com.maxim.documentfiller.MongoDB.templates.Properties;



import com.mongodb.lang.NonNull;
import lombok.*;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Attribute {
    /**
     * full path with '.' separation
     * <br>
     * <strong>Must be unique for each file</strong>
     */
    private @NonNull String name;
    /**
     * label to render for user
     */
    private Optional<String> label;
    /**
     * Is this field required for document
     */
    private Optional<Boolean> required;
    private Optional<String> validationRExString;

    /**
     *
     * @param value
     * @return true if value matches and false otherwise
     * <br>if validationRExString is null returns true automatically
     */
    public Boolean validateValue(String value) {
        try {
            Objects.requireNonNull(validationRExString);
            Pattern pattern = Pattern.compile(validationRExString.get());
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        } catch (NullPointerException e) {
            return true;
        }
    }



}
