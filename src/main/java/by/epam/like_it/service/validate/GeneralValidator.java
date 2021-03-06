package by.epam.like_it.service.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralValidator {

    private static final String langRegEx="[a-zA-Z]{2,4}";

    public static boolean checkId(int id) {
        return id > 0;
    }

    public static boolean checkLangStrict(String lang){

        Pattern pattern= Pattern.compile(langRegEx);
        Matcher matcher=pattern.matcher(lang);

        return matcher.find();
    }

    public static boolean checkLangWithNull(String lang){

        if(lang == null) {
            return true;
        } else {
            return checkLangStrict(lang);
        }
    }
}
