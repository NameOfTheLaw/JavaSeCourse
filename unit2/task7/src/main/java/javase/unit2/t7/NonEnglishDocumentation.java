package javase.unit2.t7;

import java.lang.annotation.*;

/**
 * Created by andrey on 25.02.2017.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface NonEnglishDocumentation {
    String language();
}
