<?php

$dbname='u450634718_db';
$dbserver='mysql.hostinger.ae';
$dbusername='u450634718_foush';
$dbpassword='easypassword8888';
$dbconnect=new mysqli($dbserver,$dbusername,$dbpassword,$dbname);

if($dbconnect->connect_error){
	die("connection failed: " .$dbconnect->connect_error);
}else{
	echo "connection success !";
}
