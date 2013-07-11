package com.yenrof.onsite.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Asset.class)
public abstract class Asset_ {

	public static volatile SingularAttribute<Asset, Double> initvalue;
	public static volatile SingularAttribute<Asset, Area> area;
	public static volatile SingularAttribute<Asset, Date> timeStamp;
	public static volatile SingularAttribute<Asset, String> assetId;
	public static volatile SingularAttribute<Asset, byte[]> voiceData;
	public static volatile SingularAttribute<Asset, String> description;
	public static volatile SingularAttribute<Asset, Date> purchaseDate;
	public static volatile SingularAttribute<Asset, String> name;
	public static volatile ListAttribute<Asset, Picture> pictures;
	public static volatile SingularAttribute<Asset, String> type;
	public static volatile SingularAttribute<Asset, Byte> appraisalFlag;

}

