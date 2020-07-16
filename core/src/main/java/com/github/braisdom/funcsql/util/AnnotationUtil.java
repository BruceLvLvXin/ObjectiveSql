package com.github.braisdom.funcsql.util;

import com.github.braisdom.funcsql.Database;
import com.github.braisdom.funcsql.RelationType;
import com.github.braisdom.funcsql.annotations.DomainModel;
import com.github.braisdom.funcsql.annotations.Relation;

public final class AnnotationUtil {

    public static final String getPrimaryKey(Relation relation, Class relationClass) {
        if (StringUtil.isBlank(relation.primaryKey())) {
            if (RelationType.HAS_MANY.equals(relation.relationType())
                    || RelationType.HAS_ONE.equals(relation.relationType())) {
                return Database.DEFAULT_PRIMARY_KEY;
            } else {
                String rawForeignKey = relationClass.getSimpleName();
                return String.format("%s_%s", WordUtil.underscore(rawForeignKey), Database.DEFAULT_PRIMARY_KEY);
            }
        } else
            return relation.primaryKey();
    }

    public static final String getForeignKey(Class baseClass, Class relationClass, Relation relation) {
        if (StringUtil.isBlank(relation.foreignKey())) {
            if (RelationType.HAS_MANY.equals(relation.relationType())
                    || RelationType.HAS_ONE.equals(relation.relationType())) {
                String rawForeignKey = baseClass.getSimpleName();
                return String.format("%s_%s", WordUtil.underscore(rawForeignKey), Database.DEFAULT_PRIMARY_KEY);
            } else {
                String rawForeignKey = relationClass.getSimpleName();
                return String.format("%s_%s", WordUtil.underscore(rawForeignKey), Database.DEFAULT_PRIMARY_KEY);
            }
        } else
            return relation.foreignKey();
    }

    public static final String getForeignFieldName(Class baseClass, Class relationClass, Relation relation) {
        if (StringUtil.isBlank(relation.foreignKey())) {
            if (RelationType.HAS_MANY.equals(relation.relationType())
                    || RelationType.HAS_ONE.equals(relation.relationType())) {
                String rawForeignName = relationClass.getSimpleName();
                return WordUtil.camelize(rawForeignName, true);
            } else {
                String rawForeignName = baseClass.getSimpleName();
                return WordUtil.camelize(rawForeignName, true);
            }
        } else
            return relation.foreignFieldName();
    }

    public static final String getTableName(Class baseClass) {
        String tableName;
        DomainModel domainModel = (DomainModel) (baseClass == null
                ? null : baseClass.getAnnotation(DomainModel.class));

        if (domainModel != null)
            tableName = domainModel.tableName();
        else
            tableName = WordUtil.tableize(baseClass.getSimpleName());

        return tableName;
    }
}