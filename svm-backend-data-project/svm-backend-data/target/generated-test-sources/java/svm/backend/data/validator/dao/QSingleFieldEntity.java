package svm.backend.data.validator.dao;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSingleFieldEntity is a Querydsl query type for SingleFieldEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSingleFieldEntity extends EntityPathBase<SingleFieldEntity> {

    private static final long serialVersionUID = 1898734807L;

    public static final QSingleFieldEntity singleFieldEntity = new QSingleFieldEntity("singleFieldEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath stringExact = createString("stringExact");

    public final StringPath stringIgnoreCase = createString("stringIgnoreCase");

    public final NumberPath<Integer> uniqueNumber = createNumber("uniqueNumber", Integer.class);

    public QSingleFieldEntity(String variable) {
        super(SingleFieldEntity.class, forVariable(variable));
    }

    public QSingleFieldEntity(Path<? extends SingleFieldEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSingleFieldEntity(PathMetadata metadata) {
        super(SingleFieldEntity.class, metadata);
    }

}

