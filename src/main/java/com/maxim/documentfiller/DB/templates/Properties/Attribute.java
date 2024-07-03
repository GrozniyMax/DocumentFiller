package com.maxim.documentfiller.DB.templates.Properties;



import com.mongodb.lang.NonNull;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Attribute {
    /**
     * full path with '.' separation
     */
    private @NonNull String name;
    /**
     * label to render for user
     */
    private Optional<String> label;
    private Optional<Boolean> required;
    private Optional<String> validationRExString;

    public Boolean validateValue(String value) {
        Pattern pattern = Pattern.compile(validationRExString.get());
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }



}
