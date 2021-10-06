package org.python.java;

import org.python.Attribute;

public class Field extends org.python.types.Object {
    java.lang.reflect.Field field;

    public Field(java.lang.reflect.Field field) {
        super();
        this.field = field;
    }

    public org.python.types.Str __repr__() {
        return new org.python.types.Str(
                String.format("<unbound native field %s>",
                        this.field
                )
        );
    }

    public org.python.Object __get__(org.python.Object instance, org.python.Object klass) {
        if (instance == klass) {
            if (java.lang.reflect.Modifier.isStatic(this.field.getModifiers())) {
                try {
                    return org.python.types.Type.toPython(this.field.get(null));
                } catch (IllegalAccessException iae) {
                    throw new org.python.exceptions.RuntimeError("Illegal access to native field " + this.field.getName());
                }
            } else {
                return this;
            }
        } else {
            try {
                return org.python.types.Type.toPython(this.field.get(instance.toJava()));
            } catch (IllegalAccessException iae) {
                throw new org.python.exceptions.RuntimeError("Illegal access to native field " + this.field.getName());
            }
        }
    }

    public void __set__(org.python.Object instance, org.python.Object value) {
        try {
            Attribute attr = this.field.getAnnotation(Attribute.class);
            if (attr != null && attr.readonly()) {
                String typeName = instance.toObject().getClass().getTypeName();
                typeName = typeName.replace("org.python.", "").replace("stdlib.", "").toLowerCase();
                throw new org.python.exceptions.AttributeError("attribute \'" + this.field.getName() + "\' of \'" + typeName + "\' objects is not writable");
            }
            this.field.set(instance.toJava(), org.python.types.Type.toJava(this.field.getType(), value));
        } catch (IllegalAccessException iae) {
            throw new org.python.exceptions.RuntimeError("Illegal access to native field " + this.field.getName());
        }
    }
}
