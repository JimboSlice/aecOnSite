package com.yenrof.onsite.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Area.class)
public abstract class Area_ {

	public static volatile ListAttribute<Area, Asset> assets;
	public static volatile SingularAttribute<Area, Date> timeStamp;
	public static volatile SingularAttribute<Area, byte[]> voiceData;
	public static volatile SingularAttribute<Area, String> name;
	public static volatile SingularAttribute<Area, Report> report;
	public static volatile SingularAttribute<Area, String> number;
	public static volatile ListAttribute<Area, Note> notes;
	public static volatile SingularAttribute<Area, String> comment;
	public static volatile SingularAttribute<Area, String> areaId;

}

