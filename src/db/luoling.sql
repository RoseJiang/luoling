drop table if exists luoling_token;  
CREATE TABLE luoling_token(
  id int(11) NOT NULL AUTO_INCREMENT,
  access_token varchar(1024) NOT NULL,
  expires_in int(11) NOT NULL,
  createTime datetime NOT NULL,
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