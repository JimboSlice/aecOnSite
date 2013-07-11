package com.yenrof.onsite.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Report.class)
public abstract class Report_ {

	public static volatile SingularAttribute<Report, Project> project;
	public static volatile SingularAttribute<Report, UserPreference> userPreference;
	public static volatile SingularAttribute<Report, Date> timeStamp;
	public static volatile SingularAttribute<Report, byte[]> voiceData;
	public static volatile SingularAttribute<Report, byte[]> weatherData;
	public static volatile SingularAttribute<Report, String> rtype;
	public static volatile ListAttribute<Report, Area> areas;
	public static volatile SingularAttribute<Report, String> rname;
	public static volatile SingularAttribute<Report, Integer> peopleOnSite;
	public static volatile SingularAttribute<Report, String> constructionphase;
	public static volatile SingularAttribute<Report, String> reportId;

}

