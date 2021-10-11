package org.python;

@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Attribute {
    boolean readonly() default false;
}
