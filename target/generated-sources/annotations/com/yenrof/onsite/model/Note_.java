package com.yenrof.onsite.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Note.class)
public abstract class Note_ {

	public static volatile SingularAttribute<Note, byte[]> thePic;
	public static volatile SingularAttribute<Note, Integer> picOrientation;
	public static volatile SingularAttribute<Note, Date> timeStamp;
	public static volatile ListAttribute<Note, Picture> pictures;
	public static volatile SingularAttribute<Note, Byte> itemResolved;
	public static volatile SingularAttribute<Note, String> noteId;
	public static volatile SingularAttribute<Note, Float> picScale;
	public static volatile SingularAttribute<Note, Area> area;
	public static volatile SingularAttribute<Note, byte[]> voiceData;
	public static volatile SingularAttribute<Note, Integer> picCounter;
	public static volatile SingularAttribute<Note, Double> longitude;
	public static volatile SingularAttribute<Note, Double> latitude;
	public static volatile SingularAttribute<Note, String> displayPic;
	public static volatile SingularAttribute<Note, String> note;
	public static volatile SingularAttribute<Note, Byte> trackableActionItem;

}

