package com.yenrof.onsite.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Project.class)
public abstract class Project_ {

	public static volatile ListAttribute<Project, Report> reports;
	public static volatile SingularAttribute<Project, Date> timeStamp;
	public static volatile SingularAttribute<Project, String> zipcode;
	public static volatile SingularAttribute<Project, String> state;
	public static volatile SingularAttribute<Project, String> countryCode;
	public static volatile SingularAttribute<Project, String> neighborhood;
	public static volatile SingularAttribute<Project, String> projectId;
	public static volatile SingularAttribute<Project, String> city;
	public static volatile SingularAttribute<Project, String> country;
	public static volatile SingularAttribute<Project, Float> longcoord;
	public static volatile SingularAttribute<Project, byte[]> voiceData;
	public static volatile SingularAttribute<Project, String> address;
	public static volatile SingularAttribute<Project, String> county;
	public static volatile SingularAttribute<Project, Float> latcoord;
	public static volatile SingularAttribute<Project, String> subAddress;
	public static volatile SingularAttribute<Project, String> displayPic;
	public static volatile SingularAttribute<Project, String> projectName;
	public static volatile SingularAttribute<Project, String> projectNumber;
	public static volatile SingularAttribute<Project, String> uniqueRoomName;

}

