drop table if exists luoling_token;  
CREATE TABLE luoling_token(
  id int(11) NOT NULL AUTO_INCREMENT,
  identifier varchar(1024) NOT NULL,
  identifiervalue varchar(1024) NOT NULL,
  expires_in int(11) NOT NULL,
  createTime varchar(1024) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists luoling_member;  
CREATE TABLE luoling_member(
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(1024) NOT NULL,
  phonenumber int(11) NOT NULL,
  birth varchar(1024),
  gender varchar(1024),
  createTime datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists luoling_weixinuserinfo;
CREATE TABLE luoling_weixinuserinfo(
  id int(11) NOT NULL AUTO_INCREMENT,
  openId varchar(1024) NOT NULL,
  subscribe int(11) NOT NULL,
  subscribeTime varchar(1024),
  nickname varchar(1024) NOT NULL,
  country varchar(1024),
  province varchar(1024),
  city varchar(1024),
  language varchar(1024),
  headImgUrl varchar(1024),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists luoling_snsuserinfo;
CREATE TABLE luoling_snsuserinfo(
   id int(11) NOT NULL AUTO_INCREMENT,
   openId varchar(1024) NOT NULL,
   nickname varchar(1024) NOT NULL,
   sex int(11) NOT NULL,
   country varchar(1024),
   province varchar(1024),
   city varchar(1024),
   headImgUrl varchar(1024),
   privilege varchar(1024),
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;