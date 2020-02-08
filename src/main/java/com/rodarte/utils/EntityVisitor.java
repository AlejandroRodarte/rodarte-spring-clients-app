package com.rodarte.utils;

public abstract class EntityVisitor<T extends Identifiable, P extends Identifiable> {

    private final Class<T> targetClass;

    public EntityVisitor(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    public Class<T> getTargetClazz() {
        return targetClass;
    }

    public void visit(T object, EntityContext entityContext) {

        

    }

}
