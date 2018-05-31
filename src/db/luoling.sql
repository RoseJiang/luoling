CREATE TABLE luoling_token(
  id int(11) NOT NULL AUTO_INCREMENT,
  access_token varchar(1024) NOT NULL,
  expires_in int(11) NOT NULL,
  createTime datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8