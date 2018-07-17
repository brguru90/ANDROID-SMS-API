<?php
require_once("post.php");
$data=array();
$data["uid"]="guru";
$data["pwd"]="9611";
$data["id"]="18";
$url='http://guruinfo.6te.net/sms_server/get_last_rec_sms.php';
echo "Last SMS Recieved is<br />".post($url,$data);
?>