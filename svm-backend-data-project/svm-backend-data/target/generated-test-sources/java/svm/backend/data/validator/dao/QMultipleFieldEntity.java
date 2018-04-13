package svm.backend.data.validator.dao;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMultipleFieldEntity is a Querydsl query type for MultipleFieldEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMultipleFieldEntity extends EntityPathBase<MultipleFieldEntity> {

    private static final long serialVersionUID = 558738831L;

    public static final QMultipleFieldEntity multipleFieldEntity = new QMultipleFieldEntity("multipleFieldEntity");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastName = createString("lastName");

    public QMultipleFieldEntity(String variable) {
        super(MultipleFieldEntity.class, forVariable(variable));
    }

    public QMultipleFieldEntity(Path<? extends MultipleFieldEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMultipleFieldEntity(PathMetadata metadata) {
        super(MultipleFieldEntity.class, metadata);
    }

}

