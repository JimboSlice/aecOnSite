package com.yenrof.onsite.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Picture.class)
public abstract class Picture_ {

	public static volatile SingularAttribute<Picture, byte[]> thePic;
	public static volatile SingularAttribute<Picture, Asset> asset;
	public static volatile SingularAttribute<Picture, Date> timeStamp;
	public static volatile SingularAttribute<Picture, Double> longitude;
	public static volatile SingularAttribute<Picture, Double> latitude;
	public static volatile SingularAttribute<Picture, String> comment;
	public static volatile SingularAttribute<Picture, String> pictureId;
	public static volatile SingularAttribute<Picture, Note> note;

}

